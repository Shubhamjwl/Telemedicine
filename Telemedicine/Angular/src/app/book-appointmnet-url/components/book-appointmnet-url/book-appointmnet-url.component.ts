import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { TCbookAppointmentModalComponent } from 'src/app/layout/shared-modules/modals/tcbook-appointment-modal/tcbook-appointment-modal.component';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { CustomValidators } from 'src/app/shared/commonBuildBlocks/services/Validators/customValidator.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { environment } from 'src/environments/environment';
import { MatDialog } from '@angular/material/dialog';
import { PaymentDetail } from 'src/app/shared/commonBuildBlocks';
import { SaveAppointmentDetail } from 'src/app/shared/commonBuildBlocks/model/patient/save-appointment-detail.model';

@Component({
  selector: 'app-book-appointmnet-url',
  templateUrl: './book-appointmnet-url.component.html',
  styleUrls: ['./book-appointmnet-url.component.scss'],
})
export class BookAppointmnetUrlComponent implements OnInit {
  doctorDetails: any;
  appointmentFormGroup: FormGroup;
  patientGroup: FormGroup;
  slotsList = {
    today: [],
    tomarrow: [],
    otherDate: [],
  };
  selectedDate: any;
  show: boolean = false;
  isChangeDate: boolean = false;
  date: Date = new Date();
  todayDate: any;
  tomorrowDate: any;
  token: any;
  data: any;
  modelref: any;
  loader: boolean = false;
  slotType: any;
  consultType: any;
  doctorListBySpeciality: any;
  Locality: string;
  address: string;
  termcondModelRef: any;
  termCondFlag: boolean;
  terms: boolean;
  reasonModelRef: NgbModalRef;

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private modelService: NgbModal,
    public route: ActivatedRoute,
    public router: Router,
    private toastrMessage: ToastMessageService,
    public patientService: PatientService,
    private dataPassingService: DataPassingService,
    private spinner: NgxSpinnerService,
    private dialog: MatDialog,
  ) {
    this.route.queryParams.subscribe((params) => {
      if (params && params.jwt) {
        this.token = params.jwt;
        this.bookAppointmentOnline(params.jwt);
      } else {
        this.toastrMessage.showErrorMsg('This is invalid link, Please visit valid link', 'Error');
        this.router.navigateByUrl('/sign-in', { relativeTo: this.route });
      }
    });
    this.appointmentFormGroup = this.fb.group({
      date: [],
    });
    this.patientGroup = this.fb.group({
      ptUserId: [''],
      name: ['', [Validators.required, CustomValidators.fullNameValidator(30)]],
      phone: ['', [Validators.required, CustomValidators.mobileValidator]],
      email: ['', [Validators.email, Validators.maxLength(250)]],
      symptomsDetails: ['', [Validators.required, Validators.maxLength(160)]],
      dob: [''],
      terms: ['', Validators.required],
    });
    this.selectedDate = null;
    this.todayDate = this.datePipe.transform(this.date, 'yyyy-MM-dd');
    let tdate = new Date(this.date);
    tdate.setDate(this.date.getDate() + 1);
    this.tomorrowDate = this.datePipe.transform(tdate, 'yyyy-MM-dd');
    this.show = false;
    this.loader = false;
  }

  ngOnInit(): void {
    this.Locality = localStorage.getItem('city');
    this.address = localStorage.getItem('address');
    this.dataPassingService.callDocDashboard.subscribe((result) => {
      this.show = false;
      this.selectedDate = null;
      this.appointmentFormGroup.reset();
      this.patientGroup.reset();
      this.isChangeDate = false;
      if (this.modelref) {
        this.modelref.close();
      }
      this.bookAppointmentOnline(this.token);
    });
    this.dataPassingService.paymentObservable.subscribe((data) => {
      if (data == 'start') {
        this.spinner.show();
      } else {
        this.spinner.hide();
      }
    });

    this.doctorDetails = {
      doctorId: '',
      doctorName: '',
      speciality: '',
      consultFee: '',
      drProfilePhoto: '',
      convenienceCharge: '',
    };
  }
  changeDate(event) {
    this.selectedDate = this.datePipe.transform(event.target.value, 'yyyy-MM-dd');
    this.isChangeDate = true;
    this.slotsList['otherDate'] = [];
    this.bookAppointmentOnline(this.token);
  }

  bookAppointmentOnline(token) {
    this.spinner.show();
    let payload = {
      id: 'appointment',
      request: {
        username: token,
        availabilityStartDate: this.selectedDate ? this.selectedDate : null,
        availabilityEndDate: this.selectedDate ? this.selectedDate : null,
      },
    };
    this.patientService.bookAppointmentOnline(payload).subscribe(
      (result: any) => {
        this.spinner.hide();
        this.slotsList.today = [];
        this.slotsList.otherDate = [];
        if (result.status && result.response) {
          this.doctorDetails.doctorId = result.response[0].doctorId ? result.response[0].doctorId : '';
          this.doctorDetails.doctorName = result.response[0].doctorName ? result.response[0].doctorName : '';
          this.doctorDetails.speciality = result.response[0].speciality ? result.response[0].speciality : '';
          this.doctorDetails.consultFee = result.response[0].consultFee ? result.response[0].consultFee : '';
          this.doctorDetails.convenienceCharge = result.response[0].convenienceCharge
            ? result.response[0].convenienceCharge
            : '';
          //this.doctorDetails.drProfilePhoto = this.doctorDetails.drProfilePhoto ? this.doctorDetails.drProfilePhoto.replace('/var/telemedicine/', `${environment["baseUrl"]}`) : '';
          result.response.forEach((element) => {
            if (element) {
              let updatedProfileURL;
              if (element.drProfilePhoto) {
                updatedProfileURL = element.drProfilePhoto.replace('/var/telemedicine/', `${environment['baseUrl']}`);
              } else {
                updatedProfileURL = 'assets/images/img_avatar.png';
              }
              // .split('\\')
              //     .filter(x=> x)
              //     .join('/')
              element.drProfilePhoto = updatedProfileURL;
              // element.drProfilePhoto =
              // element.drProfilePhoto =  element.drProfilePhoto ? element.drProfilePhoto.replace('\\', '/') : 'assets/images/img_avatar.png';
              // element.drProfilePhoto =  element.drProfilePhoto ? element.drProfilePhoto.replace('/var/telemedicine/', `${environment["baseUrl"]}`) :
              //
            }
          });
          this.doctorListBySpeciality = result.response;
          let slotDetails = result.response[0].slotDetails;

          slotDetails.forEach((element) => {
            if (!this.isChangeDate && element.slotDate === this.todayDate) {
              this.slotsList.today.push(element);
              this.selectedDate = this.todayDate;
            } else if (this.isChangeDate) {
              this.slotsList['otherDate'].push(element);
            }
          });
        } else if (!result.status || result.errors) {
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.spinner.hide();
      },
    );
  }

  openModel(model, data, day) {
    if (day == 'today') {
      this.selectedDate = this.todayDate;
    }
    this.data = data;
    this.slotType = data.slotType;

    this.show = false;
    this.patientGroup.reset();
    this.modelref = this.modelService.open(model, { backdrop: 'static' });
  }

  openReasonModel(content) {
    this.reasonModelRef = this.modelService.open(content, {
      backdrop: 'static',
      centered: true,
      size: 'md',
    });
  }

  patientRegistartionByExternalLink() {
    if (this.patientGroup.valid) {
      this.loader = true;
      let patientDetails = this.patientGroup.value;
      let payload = {
        id: 'registration',
        request: {
          patientName: patientDetails.name,
          ptMobileNo: patientDetails.phone,
          ptEmailID: patientDetails.email,
          ptDateOfBirth: patientDetails.dob,
          symptomsDetails: patientDetails.symptomsDetails,
          tncFlag: patientDetails.terms,
          doctorUserID: this.doctorDetails.doctorId,
        },
      };
      this.patientService.patientRegistartionByExternalLink(payload).subscribe(
        (resp: any) => {
          this.loader = false;
          if (resp.status) {
            this.patientGroup
              .get('ptUserId')
              .patchValue(resp && resp.response && resp.response.ptUserID ? resp.response.ptUserID : '');
            this.paymentApi();
          } else {
            if (resp.errors) {
              this.toastrMessage.showErrorMsg(resp.errors[0].message, 'Error');
            }
          }
        },
        (error) => {
          this.spinner.hide();
          this.loader = false;
          this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
        },
      );
    } else {
      this.loader = false;
      this.patientGroup.markAllAsTouched();
    }
  }
  bookAppointment() {
    this.paymentApi();
  }
  paymentApi() {
    let dummyPayload = {
      docUserID: this.doctorDetails.doctorId,
      ptUserID: this.patientGroup.get('ptUserId').value,
      bookedFor: 'n',
      appointmentSlot: this.data.slotTime,
      appointmentDate: this.selectedDate,
    };
    this.patientService.dummyPaymentApIForExternalLink(dummyPayload).subscribe(
      (result) => {
        let transactionID = result;
        if (result) {
          if (this.slotType == 'In-Clinic') {
            this.saveAppointmentinclinic(transactionID);
          } else {
            this.saveAppointment(transactionID);
          }
        } else {
          this.toastrMessage.showErrorMsg(ErrorSuccessMessage.TRANSACTIONID, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(ErrorSuccessMessage.APIFAILD, 'Error');
      },
    );
  }

  saveAppointmentinclinic(transactionID) {
    let patientData = this.patientGroup.value;
    let payload = {
      drRegID: this.doctorDetails.doctorId,
      ptRegID: patientData.ptUserId,
      transactionID: transactionID ? transactionID : null,
      bookForSomeoneElse: 'n',
      patientName: patientData.name,
      patientEmail: patientData.email,
      patientDob: patientData.dob,
      patientMNO: patientData.phone,
      symptomsDetails: patientData.symptomsDetails,
      tncFlag: patientData.terms,
      consultType: this.slotType,
      appointmentDetails: {
        appointmentSlot: this.data.slotTime ? this.data.slotTime : null,
        appointmentDate: this.selectedDate ? this.selectedDate : null,
      },
    };
    this.spinner.show();
    this.bookApponitment(payload);

    //   this.patientService.saveAppointmentDetails(payload).subscribe(result => {
    // 	if (result && result.response && result.status) {
    // 	  this.spinner.hide();
    // 	  // this.getSlotByDoctor();
    // 	//   this.dialogRef.close({ data: result.status });
    // 	//   if (this.role == 'CALLCENTRE') {
    // 	// 	this.router.navigate(['../book-appointment-dashboard'], { relativeTo: this.activateRoute });
    // 	//   } else {
    // 	// 	this.router.navigate(['/appointments']);
    // 	//   }
    // 	  this.toastrMessage.showSuccessMsg(result.response.info, "Success");
    // 	} else if (result && result.errors && !result.status) {
    // 	  this.spinner.hide();
    // 	  this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
    // 	}
    //   }
    //   , error => {
    // 	this.spinner.hide();
    // 	this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
    //   })

    // }else {
    //   this.spinner.hide();
    //   this.toastrMessage.showWarningMsg(ErrorSuccessMessage.PATIENTDETAILS, 'Warning');
    // }
  }

  saveAppointment(transactionID) {
    this.spinner.show();
    let patientData = this.patientGroup.value;
    if (transactionID) {
      const payload = {
        drRegID: this.doctorDetails.doctorId,
        ptRegID: patientData.ptUserId,
        transactionID: transactionID ? transactionID : null,
        bookForSomeoneElse: 'n',
        patientName: patientData.name,
        patientEmail: patientData.email,
        patientDOB: patientData.dob,
        patientMNO: patientData.phone,
        symptomsDetails: patientData.symptomsDetails,
        tncFlag: patientData.terms,
        consultType: this.slotType,
        appointmentDetails: {
          appointmentSlot: this.data.slotTime ? this.data.slotTime : null,
          appointmentDate: this.selectedDate ? this.selectedDate : null,
        },
      } as SaveAppointmentDetail;

      const paymentDetails = {
        paitentDetail: payload,
        consultationFee: this.doctorDetails?.consultFee || 0,
        convenienceCharge: this.doctorDetails?.convenienceCharge || 0,
        ptUserId: this.patientGroup.get('ptUserId').value,
      } as PaymentDetail;
      //-----uncomment when you need to add payment
      this.dataPassingService.paymentDetails.next(paymentDetails);
      this.spinner.hide();
      this.modelref.close();
      this.show = true;
    } else {
      this.spinner.hide();
      this.toastrMessage.showWarningMsg(ErrorSuccessMessage.PATIENTDETAILS, 'Warning');
    }
  }
  bookApponitment(payload) {
    this.patientService.saveAppointmentDetailsUrl(payload).subscribe(
      (result) => {
        this.spinner.hide();
        if (result && result.response && result.status) {
          // if(this.authService.isUserPatient()){
          //this.router.navigate(['/patient-dashboard']);
          // }else {
          //   this.router.navigate(['/appointments']);
          // }
          this.reasonModelRef.close();
          this.modelref.close();
          this.bookAppointmentOnline(this.token);
          this.toastrMessage.showSuccessMsg(result.response.info, 'Success');
        } else if (result && result.errors && !result.status) {
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.spinner.hide();
        this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
      },
    );
  }

  openDialogTC(content) {
    this.termcondModelRef = this.modelService.open(content, { size: 'lg', backdrop: 'static' });
  }

  backToHome() {
    this.reasonModelRef.close();
    this.modelref.close();
    this.bookAppointmentOnline(this.token);
  }
}