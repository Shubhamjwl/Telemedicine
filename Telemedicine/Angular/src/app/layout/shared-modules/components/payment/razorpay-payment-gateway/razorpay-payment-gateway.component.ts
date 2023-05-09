import { Component, NgZone, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';
import { tap } from 'rxjs/operators';
import { PaymentDetail, RazorpayPaymentOption } from 'src/app/shared/commonBuildBlocks';
import {
  ConsultationPaymentRequest,
  PayResponseRequest,
} from 'src/app/shared/commonBuildBlocks/model/payment/consulation-payment-model';
import { RazorPayResponse } from 'src/app/shared/commonBuildBlocks/model/payment/razorpay-response.model';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
declare let Razorpay: any;

@Component({
  selector: 'app-razorpay-payment-gateway',
  templateUrl: './razorpay-payment-gateway.component.html',
  styleUrls: ['./razorpay-payment-gateway.component.scss'],
})
export class RazorpayPaymentGatewayComponent implements OnInit, OnDestroy {
  logo = 'assets/img/protean-clinic-logo.png';
  paymentDetails: PaymentDetail;

  options = {
    key: '', // Enter the Key ID generated from the Dashboard
    amount: '', // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
    currency: 'INR',
    name: 'Protean e-Gov Health', // NSDL's Arogya Saarathi
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

  private subscription = new Subscription();

  constructor(
    private dataPassingService: DataPassingService,
    private consultationService: ConsultationService,
    private toastrMessage: ToastMessageService,
    private spinner: NgxSpinnerService,
    private patientService: PatientService,
    private router: Router,
    public authService: AuthService,
    private zone: NgZone,
  ) { }

  ngOnInit(): void {
    this.subscription.add(
      this.dataPassingService.paymentDetails
        .pipe(
          tap((res) => {
            this.paymentDetails = res;
            const { patientName, patientMNO, patientEmail } = res.paitentDetail;
            this.options.prefill.name = patientName;
            this.options.prefill.contact = patientMNO;
            this.options.prefill.email = patientEmail;
          }),
        )
        .subscribe((res) => {
          this.confirmPayment();
        }),
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  private paymentHandller(response: RazorPayResponse) {
    const { razorpay_payment_id, razorpay_order_id, razorpay_signature } = response;
    if (razorpay_payment_id && razorpay_order_id && razorpay_signature) {
      this.bookOrderAppointment(response);
    }
  }
  private razorpay() {
    const rzp = new Razorpay(this.options);
    rzp.open();
  }

  private confirmPayment() {
    const { consultationFee, convenienceCharge, paitentDetail, apptId, usedWalletAmount } = this.paymentDetails;
    const { transactionID, totalWalletAmount } = paitentDetail;
    this.spinner.show();

    const payment = {
      convenienceCharge,
      consultationFee,
      totalWalletAmount: +totalWalletAmount,
      apptId,
      transId: transactionID,
      usedWalletAmount: +usedWalletAmount,
    } as ConsultationPaymentRequest;

    /* const payment = {
      amount: consultationFee + convenienceCharge,
      apptId: null,
      convenienceCharge: convenienceCharge,
      transId: transactionID,
    } as any; */
    this.dataPassingService.addPaymentLoader('start');
    this.consultationService.consultationPayment(payment).subscribe(
      (result) => {
        this.dataPassingService.addPaymentLoader('stop');
        this.spinner.hide();
        if (result?.response && result?.status) {
          const { secretKey, razorpayOrderId, applicationFee } = result.response.razorPay;
          this.options.key = secretKey;
          this.options.order_id = razorpayOrderId;
          this.options.amount = applicationFee;
          const { key, order_id, amount } = this.options;
          if (key && order_id && amount) {
            this.razorpay();
          }
        } else {
          this.toastrMessage.showErrorMsg(result?.errors[0]?.message, 'Error');
        }
      },
      (error) => {
        this.spinner.hide();
        this.dataPassingService.addPaymentLoader('stop');
        this.toastrMessage.showErrorMsg(error?.errors[0]?.message, 'Error');
      },
    );
  }

  private bookAppointment() {
    this.spinner.show();
    this.dataPassingService.addPaymentLoader('start');
    this.patientService.saveAppointmentDetails(this.paymentDetails.paitentDetail).subscribe(
      (result) => {
        this.dataPassingService.addPaymentLoader('stop');
        this.spinner.hide();
        if (result?.response && result?.status) {
          if (this.authService.isUserPatient()) {
            this.zone.run(() => {
              this.router.navigate(['patient-dashboard']);
            });
            this.toastrMessage.showSuccessMsg(result.response.info, 'Success');
          } else {
            this.toastrMessage.showSuccessMsg(result.response.info, 'Success');
            this.zone.run(() => {
              this.dataPassingService.callDocDashboard.next('payment Complete');
            });
          }
        } else {
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      },
      (err) => {
        this.spinner.hide();
        this.dataPassingService.addPaymentLoader('stop');
        this.toastrMessage.showErrorMsg(err?.errors[0].message, 'Error');
        this.zone.run(() => {
          this.dataPassingService.callDocDashboard.next('payment Failed');
        });
      },
    );
  }

  private bookOrderAppointment(response: RazorPayResponse) {
    this.spinner.show();
    const { razorpay_order_id, razorpay_payment_id, razorpay_signature, request } = response;
    const { usedWalletAmount, paitentDetail } = this.paymentDetails;
    const { totalWalletAmount } = paitentDetail;
    let paymentDetails = {
      orderId: razorpay_order_id,
      paymentId: razorpay_payment_id,
      signature: razorpay_signature,
      paymentMethod: request?.content?.method,
      usedWalletAmount: +usedWalletAmount || 0,
      totalWalletAmount: +totalWalletAmount || 0,
    } as PayResponseRequest;
    this.consultationService.orderPayment(paymentDetails).subscribe(
      (result) => {
        if (result?.response && result?.status) {
          // this.toastrMessage.showSuccessMsg(result.response?.message, 'Success');
          this.bookAppointment();
        } else {
          this.spinner.hide();
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      },
      (error) => {
        this.spinner.hide();
        this.toastrMessage.showErrorMsg(error.errors[0]?.message, 'Error');
      },
    );
  }

  private cancelPayment() {
    if (this.authService.isUserPatient()) {
      this.router.navigate(['/patient-dashboard']);
    } else {
      this.router.navigate(['/appointments']);
    }
  }
}
