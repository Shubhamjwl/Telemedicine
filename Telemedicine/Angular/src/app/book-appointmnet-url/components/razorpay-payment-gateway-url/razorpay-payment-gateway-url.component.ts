import { Component, NgZone, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { PaymentDetail, RazorpayPaymentOption } from 'src/app/shared/commonBuildBlocks';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
declare let Razorpay: any;

@Component({
  selector: 'app-razorpay-payment-gateway-url',
  templateUrl: './razorpay-payment-gateway-url.component.html',
  styleUrls: ['./razorpay-payment-gateway-url.component.scss'],
})
export class RazorpayPaymentGatewayUrlComponent implements OnInit {
  logo = 'assets/img/protean-clinic-logo.png';
  paymentDetails: PaymentDetail;
  rzp;
  isOpenPayment = false;

  options = {
    key: '', // Enter the Key ID generated from the Dashboard
    amount: '', // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
    currency: 'INR',
    name: 'Protean e-Gov Health', //NSDL's Arogya Saarathi
    description: 'Consultation Fees (incl. of taxes / fin. charges)',
    image: this.logo, //https://example.com/your_logo
    order_id: '', //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
    handler: this.paymentHandller.bind(this),
    prefill: {
      name: '',
      email: '',
      contact: '',
    },
  } as RazorpayPaymentOption;

  constructor(
    private dataPassingService: DataPassingService,
    private consultationService: ConsultationService,
    private toastrMessage: ToastMessageService,
    private spinner: NgxSpinnerService,
    private patientService: PatientService,
    public authService: AuthService,
    private zone: NgZone,
  ) {
    this.dataPassingService.paymentDetails.subscribe((res: PaymentDetail) => {
      this.paymentDetails = res;
      const { patientName, patientMNO, patientEmail } = res.paitentDetail;
      this.options.prefill.name = patientName || null;
      this.options.prefill.contact = patientMNO || this.options.prefill.contact;
      this.options.prefill.email = patientEmail || this.options.prefill.email;
    });
  }

  ngOnInit(): void {
    this.confirmPayment();
  }

  private paymentHandller(response: any) {
    if (response.razorpay_payment_id && response.razorpay_order_id && response.razorpay_signature) {
      this.isOpenPayment = true;
      this.bookOrderAppointment(response);
    }
  }

  private razorpay() {
    this.rzp = new Razorpay(this.options);
    this.rzp.open();
    this.rzp.on('payment.failed', function (response) { });
  }

  private confirmPayment() {
    const { consultationFee, convenienceCharge } = this.paymentDetails;
    this.isOpenPayment = true;
    this.dataPassingService.addPaymentLoader('start');
    let paymentDetails = {
      amount: consultationFee + convenienceCharge,
      convenienceCharge: convenienceCharge || 0,
      apptId: null,
      transId: this.paymentDetails?.paitentDetail?.transactionID || null,
      ptUserID: this.paymentDetails?.ptUserId || null,
    };
    this.consultationService.consultationPaymentUrl(paymentDetails).subscribe(
      (result) => {
        this.dataPassingService.addPaymentLoader('stop');
        if (result && result.response && result.status) {
          let razorpayResponse = result.response.razorPay;
          this.options.key = razorpayResponse.secretKey;
          this.options.order_id = razorpayResponse.razorpayOrderId;
          this.options.amount = razorpayResponse.applicationFee;
          if (this.options.key && this.options.order_id && this.options.amount) {
            this.spinner.show();
            this.razorpay();
          }
        } else if (result && result.errors && !result.status) {
          this.toastrMessage.showErrorMsg('Something Went Wrong', 'Error');
          this.zone.run(() => {
            this.dataPassingService.callDocDashboard.next('payment Failed');
          });
        } else if (!result.status) {
          this.toastrMessage.showErrorMsg('Something Went Wrong', 'Error');
          this.zone.run(() => {
            this.dataPassingService.callDocDashboard.next('payment Failed');
          });
        }
      },
      (error) => {
        this.dataPassingService.addPaymentLoader('stop');
        this.spinner.hide();
        this.toastrMessage.showErrorMsg('Something Went Wrong', 'Error');
        this.zone.run(() => {
          this.dataPassingService.callDocDashboard.next('payment Failed');
        });
      },
    );
  }

  private bookApponitment() {
    this.spinner.show();
    this.isOpenPayment = true;
    this.dataPassingService.addPaymentLoader('start');
    this.patientService.saveAppointmentDetailsUrl(this.paymentDetails.paitentDetail).subscribe(
      (result) => {
        this.dataPassingService.addPaymentLoader('stop');
        if (result && result.response && result.status) {
          if (this.authService.isUserPatient()) {
            this.toastrMessage.showSuccessMsg(result.response.info, 'Success');
            this.zone.run(() => {
              this.dataPassingService.callDocDashboard.next('payment Complete');
            });
          } else {
            this.toastrMessage.showSuccessMsg(result.response.info, 'Success');
            this.zone.run(() => {
              this.dataPassingService.callDocDashboard.next('payment Complete');
            });
          }
        } else if (result && result.errors && !result.status) {
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
          this.zone.run(() => {
            this.dataPassingService.callDocDashboard.next('payment Failed');
          });
        }
      },
      (error) => {
        this.dataPassingService.addPaymentLoader('stop');
        this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
        this.zone.run(() => {
          this.dataPassingService.callDocDashboard.next('payment Failed');
        });
      },
    );
  }

  private bookOrderAppointment(response) {
    this.spinner.show();
    this.isOpenPayment = true;
    this.dataPassingService.addPaymentLoader('start');
    let paymentDetails = {
      orderId: response ? (response.razorpay_order_id ? response.razorpay_order_id : null) : null,
      paymentId: response ? (response.razorpay_payment_id ? response.razorpay_payment_id : null) : null,
      signature: response ? (response.razorpay_signature ? response.razorpay_signature : null) : null,
      paymentMethod: response?.request?.content?.method ? response.request.content.method : null,
      ptUserID: this.paymentDetails.ptUserId,
    };
    this.consultationService.OrderPaymentUrl(paymentDetails).subscribe(
      (result) => {
        this.dataPassingService.addPaymentLoader('stop');
        if (result && result.response && result.status) {
          this.bookApponitment();
        } else if (result && result.errors && !result.status) {
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
      },
    );
  }

  private cancelPayment() {
    this.dataPassingService.callDocDashboard.next('payment Canceled');
  }
}
