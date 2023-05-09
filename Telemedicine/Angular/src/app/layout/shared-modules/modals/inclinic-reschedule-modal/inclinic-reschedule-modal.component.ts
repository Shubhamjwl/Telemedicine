import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable } from 'rxjs';
import { debounceTime, map, startWith } from 'rxjs/operators';
import { TmBookAppointmentComponent } from 'src/app/layout/doctor/appointment/tm-book-appointment/tm-book-appointment.component';
import { PaymentDetail } from 'src/app/shared/commonBuildBlocks';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { IPatient } from 'src/app/shared/commonBuildBlocks/interfaces/patient.interface';
import { SaveAppointmentDetail } from 'src/app/shared/commonBuildBlocks/model/patient/save-appointment-detail.model';
import { AppointmentService } from 'src/app/shared/commonBuildBlocks/services/appointmentServices/appointment.service';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-inclinic-reschedule-modal',
  templateUrl: './inclinic-reschedule-modal.component.html',
  styleUrls: ['./inclinic-reschedule-modal.component.scss'],
})
export class InclinicRescheduleModalComponent implements OnInit {
  // searchPtByName = new FormControl();
  patientDetailsList;
  userID: any;
  selectedDate;
  filteredOptions: Observable<IPatient[]>;
  patientDetails: FormGroup;
  patientInfo;
  isPatientSelected = false;
  isBookSuccess = false;
  customStyle = {
    backgroundColor: '#faf1d4',
    //  border: "1px solid #7e7e7e",
    //  borderRadius: "50%",
    //  color: "#7e7e7e",
    //  cursor: "pointer"
  };
  role = sessionStorage.getItem('ROLE') ? sessionStorage.getItem('ROLE') : null;
  isPayLater = false;
  isDiscountChecked = true;
  netConsultationFees = 0;
  slotType: any;
  slotTypeLabel = this.data.slotType === 'Teleconsultation' ? 'Tele\nConsultation' : 'In-Clinic';
  previousApptType: any;
  rescheduleDetails: any;
  doctorDetails: any;
  isOpenPayment = false;
  doctorId: any;
  termCondFlag: boolean;
  netConvenienceCharge = 0;
  reasonModelRef: NgbModalRef;
  modelRef: NgbModalRef;
  modelref: any;
  drRegID: string;
  drConsultFee: number = 0;
  drconvenienceCharge: number = 0;
  constructor(
    public dialogRef: MatDialogRef<TmBookAppointmentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private patientService: PatientService,
    private datapassingService: DataPassingService,
    private fb: FormBuilder,
    private toastrMessage: ToastMessageService,
    private spinner: NgxSpinnerService,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private dialog: MatDialog,
    private modelService: NgbModal,
    private authService: AuthService,
    private appointmentService: AppointmentService,
  ) {
    this.patientDetails = this.fb.group({
      searchPtByName: [null],
      discountAmount: [null],
    });
    this.netConsultationFees = data.fees + data.charge;
    this.netConvenienceCharge = data.charge;
    this.slotType = data.slotType;
    this.doctorId = data.drId;
    this.patientDetails.get('discountAmount').enable();
  }

  ngOnInit(): void {
    this.patientDetails
      .get('searchPtByName')
      .valueChanges.pipe(debounceTime(1000))
      .subscribe((value) => {
        if (typeof value === 'string') {
          this.getPatientDetails(value);
        }
      });

    this.patientDetails
      .get('discountAmount')
      .valueChanges.pipe(debounceTime(1000))
      .subscribe((value) => {
        if (this.isDiscountChecked) {
          this.onChangeDiscount(value);
        }
      });
  }

  getPatientDetails(nameOrMob) {
    this.patientService.getPatientByName(nameOrMob).subscribe(
      (result: any) => {
        if (result.status && result.response) {
          this.patientDetailsList = result ? (result.response ? result.response : null) : null;
          this.filterOptions();
        } else {
          // this.patientDetailsList = MockResponse.patientList.response;
          // this.filterOptions();
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        // this.patientDetailsList = MockResponse.patientList.response;
        // this.filterOptions();
        this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
      },
    );
  }

  filterOptions() {
    this.filteredOptions = this.patientDetails.get('searchPtByName').valueChanges.pipe(
      startWith(''),
      map((value: any) => (typeof value === 'string' ? value : value.name)),
      map((name) => (name ? this._filter(name) : this.patientDetailsList.slice())),
    );
  }

  displayFn(user): string {
    return user && user.ptFullName ? user.ptFullName : '';
  }

  private _filter(name): IPatient[] {
    const filterValue = name.toLowerCase();
    if (this.hasNumbers(filterValue)) {
      let mobNo: number = +filterValue;
      return this.patientDetailsList.filter((option) => option.ptMobNo.indexOf(mobNo) === 0);
    } else {
      return this.patientDetailsList.filter((option) => option.ptFullName.toLowerCase().indexOf(filterValue) === 0);
    }
  }

  hasNumbers(t) {
    var regex = /\d/g;
    return regex.test(t);
  }

  onSelectPtOption(event) {
    this.isPatientSelected = false;
    this.patientInfo = undefined;
    setTimeout(() => {
      if (event.isUserInput) {
        this.patientInfo = event.source.value;
        this.isPatientSelected = true;
      }
    }, 1000);
    return;
  }

  bookPatientAppointment() {
    if (!this.authService.isUserPatient()) {
      this.docPaymentApi();
    } else {
      let payload = {
        drRegID: this.doctorId,
        // drRegID: sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null,
        ptRegID: this.patientInfo.ptUserId
          ? this.patientInfo.ptUserId
          : this.role !== 'DOCTOR'
            ? this.patientInfo.ptUserId
              ? this.patientInfo.ptUserId
              : null
            : null,
        transactionID: null,
        bookForSomeoneElse: 'n',
        patientName: this.patientInfo.ptFullName,
        patientEmail: this.patientInfo.ptEmail ? this.patientInfo.ptEmail : null,
        patientMNO: this.patientInfo.ptMobNo,
        consultType: this.slotType,

        appointmentDetails: {
          appointmentSlot: this.data.slotTime ? this.data.slotTime : null,
          appointmentDate: this.data.slotDate ? this.data.slotDate : null,
        },
      };
      this.paymentApi(payload);
    }
  }

  openReasonModel(content) {
    this.reasonModelRef = this.modelService.open(content, {
      backdrop: 'static',
      size: 'md',
    });
  }

  bookPatientAppointmentInclinic() {
    let payload = {
      drRegID: this.doctorId,
      ptRegID: this.patientInfo.ptUserId
        ? this.patientInfo.ptUserId
        : this.role !== 'DOCTOR'
          ? this.patientInfo.ptUserId
            ? this.patientInfo.ptUserId
            : null
          : null,
      transactionID: null,
      bookForSomeoneElse: 'n',
      patientName: this.patientInfo.ptFullName,
      patientEmail: this.patientInfo.ptEmail ? this.patientInfo.ptEmail : null,
      patientMNO: this.patientInfo.ptMobNo,
      consultType: this.slotType,
      appointmentDetails: {
        appointmentSlot: this.data.slotTime ? this.data.slotTime : null,
        appointmentDate: this.data.slotDate ? this.data.slotDate : null,
      },
    };
    this.paymentApi(payload);
  }

  docPaymentApi() {
    let drId = sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null;
    let dummyPayload = {
      docUserID: drId === 'DOCTOR' ? drId : this.data.drId ? this.data.drId : this.data.drId,
      ptUserID: this.patientInfo.ptUserId
        ? this.patientInfo.ptUserId
        : this.role !== 'DOCTOR'
          ? this.patientInfo.ptUserId
            ? this.patientInfo.ptUserId
            : null
          : null,
      bookedFor: 'n',
      appointmentType: this.data.slotType,
      // appointmentType:this.data.slotType ? this.data.slotType : null;
      // appointmentDetails: {
      appointmentSlot: this.data.slotTime ? this.data.slotTime : null,
      appointmentDate: this.data.slotDate ? this.data.slotDate : null,
      // }
    };
    this.patientService.dummyPaymentApi(dummyPayload).subscribe(
      (result) => {
        let transactionID = result;
        if (result) {
          if (transactionID && this.patientInfo.ptFullName && this.patientInfo.ptMobNo) {
            let payload = {
              drRegID: drId === 'DOCTOR' ? drId : this.data.drId ? this.data.drId : null,
              ptRegID: this.patientInfo.ptUserId
                ? this.patientInfo.ptUserId
                : this.role !== 'DOCTOR'
                  ? this.patientInfo.ptUserId
                    ? this.patientInfo.ptUserId
                    : null
                  : null,
              transactionID: transactionID ? transactionID : null,
              bookForSomeoneElse: 'n',
              patientName: this.patientInfo.ptFullName,
              patientEmail: this.patientInfo.ptEmail ? this.patientInfo.ptEmail : null,
              patientMNO: this.patientInfo.ptMobNo,
              consultType: this.slotType,
              appointmentDetails: {
                appointmentSlot: this.data.slotTime ? this.data.slotTime : null,
                appointmentDate: this.data.slotDate ? this.data.slotDate : null,
              },
            };

            let paymentDetails = {
              bookApptPayload: payload,
              // fees : this.data ? this.data.fees ? this.data.fees : 0 : 0,
              fees: this.netConsultationFees,
              charge: this.netConvenienceCharge,
              discount: this.patientDetails.get('discountAmount').value,
            };

            // this is for payment
            this.datapassingService.paymentOptionData.next(paymentDetails);
            this.isPayLater = true;
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

  paymentApi(payload) {
    let dummyPayload = {
      docUserID: payload.drRegID,
      ptUserID: payload.ptRegID,
      bookedFor: payload.bookForSomeoneElse,
      appointmentType: this.slotType,
      appointmentSlot: payload.appointmentDetails.appointmentSlot,
      appointmentDate: payload.appointmentDetails.appointmentDate,
    };
    this.patientService.dummyPaymentApi(dummyPayload).subscribe(
      (result) => {
        payload.transactionID = result;
        if (result) {
          this.saveAppointment(payload);
        } else {
          this.toastrMessage.showErrorMsg(ErrorSuccessMessage.TRANSACTIONID, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(ErrorSuccessMessage.APIFAILD, 'Error');
      },
    );
  }

  saveAppointment(payload1) {
    const payload = {
      drRegID: this.doctorId,
      ptRegID: payload1.ptRegID,
      transactionID: payload1.transactionID,
      bookForSomeoneElse: 'n',
      patientName: this.patientInfo.ptFullName,
      patientEmail: this.patientInfo.ptEmail ? this.patientInfo.ptEmail : null,
      patientMNO: this.patientInfo.ptMobNo,
      tncFlag: this.termCondFlag,
      consultType: this.slotType,
      appointmentDetails: {
        appointmentSlot: this.data.slotTime ? this.data.slotTime : null,
        appointmentDate: this.data.slotDate ? this.data.slotDate : null,
      },
    } as SaveAppointmentDetail;
    this.spinner.show();

    const paymentDetails = {
      paitentDetail: payload,
      consultationFee: this.doctorDetails?.consultFee || 0,
      convenienceCharge: this.doctorDetails?.convenienceCharge || 0,
      discount: this.patientDetails.get('discountAmount').value,
    } as PaymentDetail;
    this.previousApptType = this.slotType;
    if (this.slotType === 'Teleconsultation') {
      this.datapassingService.paymentDetails.next(paymentDetails);
      this.spinner.hide();
      this.isOpenPayment = true;
    } else {
      this.patientService.saveAppointmentDetails(payload).subscribe(
        (result) => {
          if (result && result.response && result.status) {
            this.spinner.hide();
            // this.getSlotByDoctor();
            this.dialogRef.close({ data: result.status });
            this.reasonModelRef.close();
            if (this.role == 'CALLCENTRE') {
              this.router.navigate(['../book-appointment-dashboard'], { relativeTo: this.activateRoute });
            } else {
              this.router.navigate(['/appointments']);
            }
            this.toastrMessage.showSuccessMsg(result.response.info, 'Success');
          } else if (result && result.errors && !result.status) {
            this.spinner.hide();
            this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
          }
        },
        (error) => {
          this.spinner.hide();
          this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
        },
      );
    }

    // }else {
    //   this.spinner.hide();
    //   this.toastrMessage.showWarningMsg(ErrorSuccessMessage.PATIENTDETAILS, 'Warning');
    // }
  }

  //   rescheduleAppt(){
  //     this.previousApptType = this.rescheduleDetails.slotType;
  //     if(this.slotType === "In-Clinic" || this.slotType === null){
  //           this.rescedule();
  //     }
  //     else{
  //      if(this.previousApptType === "Teleconsultation" || this.previousApptType === null) {
  //        this.rescedule();
  //      }

  //       this.cancelAppointment();

  //     }

  //    //  || this.previousApptType === null
  // }

  // rescedule(){
  //   let data = {
  //     // drRegID: this.patientDetails.doctorUserId,
  //     // ptRegID: this.patientDetails.patientRegId,
  //     appID: this.rescheduleDetails.appointmentID,
  //     // userRole: this.authService.getUserRole(),
  //     appointmentDetails: {
  //       reschduleappSlot:   this.data.slotTime ? this.data.slotTime : null,
  //       reschduleappDate: this.data.slotDate ? this.data.slotDate : null,
  //       slotType:this.slotType ? this.slotType : null,

  //     }
  //   }
  //   this.appointmentService.rescheduleAppt(data).subscribe((result:any) => {
  //     if (result.response != null) {
  //       this.toastrMessage.showSuccessMsg(result.response.info, "Congratulation");
  //         if(this.authService.isUserPatient()){
  //           this.router.navigate(['patient-dashboard']);
  //         } else {
  //           this.router.navigate(['appointments']);
  //         }
  //       } else if (result.errors) {
  //       this.toastrMessage.showInfoMsg(result.errors[0].message, "Information");
  //       }
  //     }, error => {
  //       this.toastrMessage.showErrorMsg("Error occured at the backend!", "Error");
  //     });
  // }

  // cancelAppointment(){

  //   let data = {
  //     apptId: this.rescheduleDetails.appointmentID
  //   }
  //   console.log(data);
  // this.appointmentService.cancelAppointment(data).subscribe((resp:any) => {

  //   if(resp){

  //   }
  // });
  // }

  onClickClose() {
    this.dialogRef.close();
  }

  // onClickDiscount(checked: boolean) {
  //   this.isDiscountChecked = checked
  //   if (checked) {
  //     this.patientDetails.get('discountAmount').enable();
  //     this.patientDetails.get('discountAmount').setValidators([Validators.required]);
  //     this.patientDetails.get("discountAmount").setErrors({ 'required': true });
  //     this.patientDetails.get('discountAmount').updateValueAndValidity();
  //   } else {
  //     this.netConsultationFees = this.data.fees + this.data.charge;
  //     this.patientDetails.get('discountAmount').disable();
  //     this.patientDetails.get('discountAmount').setValue(null);
  //     this.patientDetails.get('discountAmount').setValidators(null);
  //     this.patientDetails.get('discountAmount').updateValueAndValidity();
  //   }
  // }

  onChangeDiscount(value) {
    let val = value;
    if (value && this.data && this.data.fees && val < this.data.fees && val >= 0) {
      this.netConsultationFees = this.data.fees - value + this.data.charge;
    } else {
      if (value > this.data.fees) {
        this.patientDetails.controls.discountAmount.invalid;
        this.patientDetails.get('discountAmount').updateValueAndValidity();
        this.patientDetails.get('discountAmount').setValue(null);
        this.netConsultationFees = this.data.fees + this.data.charge;
      } else {
        this.patientDetails.controls.discountAmount.invalid;
        this.patientDetails.get('discountAmount').updateValueAndValidity();
        this.patientDetails.get('discountAmount').setValue(null);
        this.netConsultationFees = this.data.fees + this.data.charge;
      }
    }
  }

  openDialog(slotTime, slotDate, slotType) {
    const dialogRef = this.dialog.open(TmBookAppointmentComponent, {
      disableClose: true,
      // width: '900Px',
      // height: '600px',
      data: {
        slotTime: slotTime,
        slotDate: slotDate,
        slotType: slotType,
        drId: this.drRegID,
        fees: this.drConsultFee,
        charge: slotType === 'Teleconsultation' ? this.drconvenienceCharge : 0,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result && result.data) {
        this.router.navigate(['/doctor-appointments'], {
          queryParams: { isReschedule: true },
        });
      }
    });
  }

  openDialogInClinic(slotTime, slotDate, slotType) {
    const dialogRef = this.dialog.open(InclinicRescheduleModalComponent, {
      disableClose: true,
      width: '500Px',
      height: '180px',
      data: {
        slotTime: slotTime,
        slotDate: slotDate,
        slotType: slotType,
        drId: this.drRegID,
        fees: this.drConsultFee,
        ptRegID: this.patientInfo.ptUserId
          ? this.patientInfo.ptUserId
          : this.role !== 'DOCTOR'
            ? this.patientInfo.ptUserId
              ? this.patientInfo.ptUserId
              : null
            : null,
        patientName: this.patientInfo.ptFullName,
        patientEmail: this.patientInfo.ptEmail ? this.patientInfo.ptEmail : null,
        patientMNO: this.patientInfo.ptMobNo,
        consultType: this.slotType,
        charge: slotType === 'Teleconsultation' ? this.drconvenienceCharge : 0,
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      this.router.navigate(['/appointments']);
    });
  }

  backToHome() {
    this.reasonModelRef.close();
    this.dialogRef.close();
    this.router.navigate(['/appointments']);
  }
}
