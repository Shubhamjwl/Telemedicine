import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { environment } from 'src/environments/environment';
import { NgxSpinnerService } from 'ngx-spinner';
import { MatDialog } from '@angular/material/dialog';
import { AppointmentService } from 'src/app/shared/commonBuildBlocks/services/appointmentServices/appointment.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { CustomValidators } from 'src/app/shared/commonBuildBlocks/services/Validators/customValidator.service';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { BookAppointmentModalComponent } from 'src/app/layout/shared-modules/modals/book-appointment-modal/book-appointment-modal.component';
import { IPatient } from 'src/app/shared/commonBuildBlocks/interfaces/patient.interface';
import { ConsultationType, PaymentDetail } from 'src/app/shared/commonBuildBlocks';
import { DummyPaymentRequest } from 'src/app/shared/commonBuildBlocks/model/payment/dummy-payment-model';
import { AppConstants } from 'src/app/app-constants';
import { SaveAppointmentDetail } from 'src/app/shared/commonBuildBlocks/model/patient/save-appointment-detail.model';

@Component({
  selector: 'app-book-doctor-appointment',
  templateUrl: './book-doctor-appointment.component.html',
  styleUrls: ['./book-doctor-appointment.component.scss'],
})
export class BookDoctorsAppointmentComponent implements OnInit {
  appointmentFormGroup: FormGroup;
  bookAppointmentGroup: FormGroup;
  private selectedDoctordata: any;
  showInfo: boolean;
  doctorDetails: any;
  date: Date = new Date();
  profilePhoto: string = 'assets/images/img_avatar.png';
  patientDetails: IPatient;
  isRescedule: boolean;
  isBookAppointment: boolean;
  patientError: any = '';
  todayDate = this.datePipe.transform(this.date, 'yyyy-MM-dd');
  private tomorrowDate = new Date(this.date);
  selectedDate: string = this.datePipe.transform(this.date, 'yyyy-MM-dd');
  private slotTomorrowDate: any;
  selectedSelfOrElse: 'self' | 'else' = 'self';
  apiSlotTime: string;
  reportGroup: FormGroup;
  private rescheduleDetails: any;
  isChangeDate: boolean;
  slotsList = {
    today: [],
    tomorrow: [],
    otherDate: [],
  };
  private rescheduleModelRef: NgbModalRef;
  private patientDetailsLoader: boolean;
  role = this.authService.getUserRole();
  isOpenPayment: boolean;
  slotType: string;
  private reasonModelRef: NgbModalRef;
  private previousApptType: any;
  private patientId: any;
  netConsultationFees = 0;
  slotDate;
  isValid: boolean;
  walletError: string;

  constructor(
    private appointmentService: AppointmentService,
    private router: Router,
    private datapassingService: DataPassingService,
    private consultationService: ConsultationService,
    private toastrMessage: ToastMessageService,
    private fb: FormBuilder,
    private modelService: NgbModal,
    private patientService: PatientService,
    public authService: AuthService,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    private spinner: NgxSpinnerService,
    private dialog: MatDialog,
    private activateRoute: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe((param) => {
      if (param) {
        if (param.isReschedule) {
          this.isRescedule = param.isReschedule;
          this.isBookAppointment = false;
        } else if (param.isBookAppointment) {
          this.isRescedule = false;
          this.isBookAppointment = true;
        }
      } else {
        this.isRescedule = false;
        this.isBookAppointment = false;
      }
    });

    if (this.role === 'PATIENT') {
      this.getPetientDetails();
    }

    this.createForm();
    this.showInfo = false;
    this.doctorDetails = this.datapassingService.getDoctorDetails();
    this.profilePhoto =
      this.doctorDetails?.drProfilePhoto?.replace('/var/telemedicine/', `${environment['baseUrl']}`) ||
      this.profilePhoto;
    this.datapassingService.paymentObservable.subscribe((data) => {
      if (data == 'start') {
        this.spinner.show();
      } else {
        this.spinner.hide();
      }
    });

    this.tomorrowDate.setDate(this.date.getDate() + 1);
    this.slotTomorrowDate = this.datePipe.transform(this.tomorrowDate, 'yyyy-MM-dd');
    this.rescheduleDetails = this.datapassingService.rescheduleApptDetails;
    if (this.rescheduleDetails) {
      this.patientId = this.rescheduleDetails.patientRegId;
    }

    if (this.isRescedule) {
      if (!this.rescheduleDetails?.doctorName) {
        if (this.authService.isUserPatient()) {
          this.router.navigate(['patient-dashboard']);
        } else {
          this.router.navigate(['appointments']);
        }
      }
      this.doctorDetails = this.datapassingService.rescheduleApptDetails;
    } else if (this.isBookAppointment) {
      if (!this.datapassingService.docName) {
        this.router.navigate(['appointments']);
      }
    }
    this.getDoctorSlotDetailsByName();
    this.bookAppointmentGroup.get('patientMobileNo').valueChanges.subscribe((value) => {
      if (this.patientDetailsLoader) {
        return;
      }
      this.patientError = '';
      this.patientDetailsLoader = true;
      if (!this.bookAppointmentGroup.get('patientMobileNo').valid) {
        this.patientDetailsLoader = false;
        return;
      } else {
        this.spinner.show();
        this.patientService.getPatientDetailsFromMobileNo(value).subscribe(
          (resp: any) => {
            this.spinner.hide();
            this.patientDetailsLoader = false;
            if (resp) {
              if (resp.status) {
                if (resp.response) {
                  const { ptUserId, ptFullName, ptEmail } = resp.response;
                  this.patientId = ptUserId;
                  this.bookAppointmentGroup.get('patientUserId').patchValue(ptUserId);
                  this.bookAppointmentGroup.get('patientName').patchValue(ptFullName);
                  this.bookAppointmentGroup.get('patientEmail').patchValue(ptEmail);
                }
              } else {
                this.patientError = resp?.errors[0]?.message || 'Patient Does not Exist.';
              }
            } else {
              this.patientError = 'Patient Does not Exist';
            }
          },
          (error) => {
            this.spinner.hide();
            this.patientError = 'Something Went Wrong';
          },
        );
      }
    });
  }

  private getPetientDetails() {
    this.patientService.getPatientDetails().subscribe({
      next: (res) => {
        const wallet = res.response.wallet || 0;
        res.response.wallet = wallet;
        this.patientDetails = res.response;
        if (wallet) {
          this.reportGroup.get('usedWallet').enable();
        } else {
          this.reportGroup.get('usedWallet').disable();
        }
      },
    });
  }

  private createForm() {
    this.appointmentFormGroup = this.fb.group({
      date: [''],
      name: [''],
      image: [''],
      speciality: [''],
      address: [''],
      fees: [''],
      likes: [''],
      patientStories: [''],
    });
    this.reportGroup = this.fb.group({
      reportType: ['', Validators.required],
      patientName: ['', [Validators.required, CustomValidators.fullNameValidator(30)]],
      patientMobileNo: ['', [Validators.required, CustomValidators.mobileValidator]],
      patientEmail: ['', [Validators.email, Validators.maxLength(250)]], //Validators.required,
      symptomsDetails: ['', [Validators.required, Validators.maxLength(160)]],
      usedWallet: [0],
      terms: ['', [Validators.required]],
    });

    this.bookAppointmentGroup = this.fb.group({
      patientUserId: [''],
      patientName: ['', Validators.required],
      patientMobileNo: ['', [Validators.required, CustomValidators.mobileValidator]],
      patientEmail: [''],
      symptomsDetails: ['', [Validators.required, Validators.maxLength(160)]],
      terms: ['', [Validators.required]],
    });

    this.reportGroup.valueChanges.subscribe(() => {
      let { patientName, symptomsDetails, patientMobileNo, terms } = this.reportGroup.value;
      if (this.selectedSelfOrElse == 'else' && patientName && patientMobileNo && symptomsDetails) {
        this.isValid = true;
      } else if (this.selectedSelfOrElse == 'self' && symptomsDetails && this.walletValidation() && terms) {
        this.isValid = true;
      } else {
        this.isValid = false;
      }
    });
  }

  private walletValidation(): boolean {
    const usedWallet = +this.reportGroup.value?.usedWallet;
    const wallet = +this.patientDetails.wallet || 0;
    const consultFee = +this.doctorDetails.consultFee || 0;
    if (usedWallet) {
      if (usedWallet < 0) {
        this.walletError = `Wallet can't be negative`;
        return false;
      } else if (usedWallet > wallet) {
        this.walletError = 'You cannot use more than your wallet amount';
        return false;
      } else if (usedWallet > consultFee) {
        this.walletError = 'You cannot use wallet amount more than consulation fees';
        return false;
      }
      this.walletError = '';
      this.calculateNetAmount();
      return true;
    }
    this.walletError = '';
    this.calculateNetAmount();
    return true;
  }

  private calculateNetAmount() {
    const usedWallet = +this.reportGroup.value?.usedWallet || 0;
    this.netConsultationFees = +this.doctorDetails.convenienceCharge + +this.doctorDetails.consultFee - usedWallet;
  }

  openModel(content: any, data: any, day: 'today' | 'tomorrow' | 'other') {
    this.selectedDoctordata = data;
    this.slotType = this.selectedDoctordata.slotType;
    this.apiSlotTime = this.selectedDoctordata.slotTime;
    this.isOpenPayment = false;
    this.calculateNetAmount();
    if (day == 'tomorrow') {
      this.slotDate = this.slotTomorrowDate;
    } else {
      this.slotDate = this.selectedDate;
    }

    if (this.role == 'CALLCENTRE' || (this.authService.isUserDoctor() && this.isBookAppointment)) {
      this.openDialog(this.apiSlotTime, this.slotDate);
    } else {
      this.bookAppointmentGroup.reset({ emitEvent: false });
      this.rescheduleModelRef = this.modelService.open(content, { backdrop: 'static' });
    }
  }

  openModelTC(content: any) {
    this.modelService.open(content, { size: 'lg', backdrop: 'static' });
  }

  changeDate(event: any) {
    this.selectedDate = this.datePipe.transform(event.target.value, 'yyyy-MM-dd');
    this.isChangeDate = true;
    this.slotsList['otherDate'] = [];
    this.getDoctorSlotDetailsByName(this.selectedDate);
  }

  private getDoctorSlotDetailsByName(date?: any) {
    this.spinner.show();
    let docName;
    let speciality;
    let doctorId;
    if (this.isRescedule) {
      docName = this.rescheduleDetails?.doctorName || null;
      speciality = this.rescheduleDetails?.doctorSpeciality || null;
      doctorId = this.rescheduleDetails?.doctorUserId || null;
    } else if (this.isBookAppointment) {
      docName = this.datapassingService?.docName || null;
      speciality = null;
      doctorId = null;
    } else {
      docName = this.doctorDetails?.doctorName || null;
      doctorId = this.doctorDetails?.doctorId || null;
      speciality = this.doctorDetails?.speciality || null;
    }

    const payload = {
      doctorName: docName,
      slotDate: null,
      doctorId: doctorId,
      speciality: speciality,
      gender: null,
      availabilityStartDate: date ? date : null,
      availabilityEndDate: date ? date : null,
      slotType: this.slotType,
    };

    this.patientService.getListOfDoctorBySearch(payload).subscribe(
      (result) => {
        this.spinner.hide();
        if (result.status && result.response) {
          this.doctorDetails = result.response[0];
          this.doctorDetails.drProfilePhoto =
            this.doctorDetails?.drProfilePhoto?.replace('/var/telemedicine/', `${environment['baseUrl']}`) ||
            'assets/images/img_avatar.png';
          const slotDetails = result.response[0].slotDetails;
          slotDetails.forEach((element) => {
            if (!this.isChangeDate && element.slotDate === this.todayDate) {
              this.slotsList.today.push(element);
            } else if (!this.isChangeDate && element.slotDate === this.slotTomorrowDate) {
              this.slotsList.tomorrow.push(element);
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
        this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
      },
    );
  }

  changeRadio(data: 'self' | 'else') {
    this.selectedSelfOrElse = data;
    if (data === 'else') {
      this.isValid = false;
      this.showInfo = true;
    } else {
      this.isValid = true;
      this.reportGroup.reset();
      this.reportGroup.markAsPristine();
    }
  }

  bookPatientAppointment() {
    this.paymentApi('book');
  }

  private paymentApi(type: string) {
    const dummyPayload = {
      docUserID: this.doctorDetails.doctorId,
      ptUserID: this.patientId,
      bookedFor: 'n',
      appointmentSlot: this.apiSlotTime,
      appointmentDate: this.slotDate,
      appointmentType: this.slotType,
    } as DummyPaymentRequest;
    this.patientService.dummyPaymentApi(dummyPayload).subscribe(
      (result) => {
        const transactionID = result;
        if (this.role == AppConstants.patient) {
          this.saveAppointment(transactionID);
        } else {
          this.payLater(transactionID);
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(ErrorSuccessMessage.APIFAILD, 'Error');
      },
    );
  }

  private payLater(transactionID: string) {
    this.spinner.show();
    this.netConsultationFees = this.rescheduleDetails.doctorConsulFee + 35;
    let payload = {
      transId: transactionID,
      amount: this.netConsultationFees, //this.rescheduleDetails.doctorConsulFee,
      charge: 35,
      discount: this.rescheduleDetails.discount,
      custName: this.rescheduleDetails.patientName,
      custMobile: this.rescheduleDetails.patientRegId.slice(1),
      custEmail: this.datapassingService.rescheduleApptDetails.patientEmail,
      doctorId: this.doctorDetails.doctorId,
    };
    this.consultationService.payLater(payload).subscribe(
      (result) => {
        if (result.status) {
          this.spinner.hide();
          this.router.navigate(['/appointments']);
          this.toastrMessage.showSuccessMsg(result.response.message, 'Success');
        } else {
          this.spinner.hide();
          if (result.errors) {
            this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
          } else {
            this.toastrMessage.showErrorMsg(result.response.message, 'Error');
          }
        }
      },
      (error) => {
        this.spinner.hide();
        this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
      },
    );
  }

  private saveAppointment(transactionID: string) {
    this.spinner.show();

    let formPatientName;
    let formPatientEmail;
    let formPatientMobileNo;
    let formPatientSymptoms;
    if (this.isBookAppointment) {
      formPatientName = this.bookAppointmentGroup.get('patientName').value;
      formPatientEmail = this.bookAppointmentGroup.get('patientEmail').value;
      formPatientMobileNo = this.bookAppointmentGroup.get('patientMobileNo').value;
      formPatientSymptoms = this.bookAppointmentGroup.get('symptomsDetails').value;
      formPatientSymptoms = this.bookAppointmentGroup.get('terms').value;
    } else if (this.selectedSelfOrElse === 'self') {
      const { ptFullName, ptEmail, ptMobNo } = this.patientDetails;
      formPatientName = ptFullName;
      formPatientEmail = ptEmail;
      formPatientMobileNo = ptMobNo;
    } else {
      const { patientName, patientEmail, patientMobileNo, symptomsDetails, terms } = this.reportGroup.value;
      if (patientName && patientMobileNo) {
        formPatientName = patientName;
        formPatientEmail = patientEmail;
        formPatientMobileNo = patientMobileNo;
        formPatientSymptoms = symptomsDetails;
        formPatientSymptoms = terms;
      }
    }
    if (transactionID && this.selectedSelfOrElse && formPatientName && formPatientMobileNo) {
      this.isValid = true;
      let drId;
      if (this.doctorDetails?.doctorId) {
        drId = this.doctorDetails.doctorId;
      } else if (this.authService.isUserScribe() && this.isBookAppointment) {
        drId = this.datapassingService.doctorId;
      } else {
        drId = '';
      }
      const { symptomsDetails, terms, usedWallet } = this.reportGroup.value;
      const payload = {
        drRegID: drId,
        ptRegID: this.isBookAppointment
          ? this.bookAppointmentGroup.get('patientUserId').value
          : sessionStorage.getItem('USER_ID'),
        transactionID,
        bookForSomeoneElse: this.selectedSelfOrElse === 'self' ? 'n' : 'y',
        patientName: formPatientName,
        patientEmail: formPatientEmail || null,
        patientMNO: formPatientMobileNo,
        symptomsDetails: symptomsDetails,
        tncFlag: terms,
        consultType: this.slotType,
        appointmentDetails: {
          appointmentSlot: this.apiSlotTime || null,
          appointmentDate: this.slotDate || null,
        },
        totalWalletAmount: this.patientDetails?.wallet || 0,
      } as SaveAppointmentDetail;

      const paymentDetails = {
        paitentDetail: payload,
        consultationFee: this.doctorDetails?.consultFee || 0,
        convenienceCharge: this.doctorDetails?.convenienceCharge || 0,
        usedWalletAmount: usedWallet,
      } as PaymentDetail;

      if (this.slotType === ConsultationType.TELECONSULTATION) {
        this.datapassingService.paymentDetails.next(paymentDetails);
        this.spinner.hide();
        this.isOpenPayment = true;
      } else {
        this.bookApponitment(payload);
      }
    } else {
      this.spinner.hide();
      this.toastrMessage.showWarningMsg(ErrorSuccessMessage.PATIENTDETAILS, 'Error');
    }
  }

  private bookApponitment(payload: any) {
    this.patientService.saveAppointmentDetails(payload).subscribe(
      (result) => {
        this.spinner.hide();
        if (result && result.response && result.status) {
          if (this.authService.isUserPatient()) {
            this.router.navigate(['/patient-dashboard']);
          } else {
            this.router.navigate(['/appointments']);
          }
          this.closeModel();
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

  rescheduleAppt() {
    this.previousApptType = this.rescheduleDetails.slotType;
    if (this.slotType === 'In-Clinic') {
      this.rescedule();
    } else if (
      (this.slotType && (this.previousApptType !== null ? this.previousApptType : 'Teleconsultation')) ==
      'Teleconsultation'
    ) {
      this.rescedule();
    } else {
      this.cancelAppointmentforReschedule();
    }
  }

  private rescedule() {
    const data = {
      appID: this.rescheduleDetails.appointmentID,
      appointmentDetails: {
        reschduleappSlot: this.apiSlotTime || null,
        reschduleappDate: this.slotDate || null,
        slotType: this.slotType || null,
      },
    };
    this.appointmentService.rescheduleAppt(data).subscribe(
      (result) => {
        if (result.response) {
          if (this.slotType === 'In-Clinic') {
            this.closeModel();
          }
          this.toastrMessage.showSuccessMsg(result.response.info, 'Congratulation');
          if (this.authService.isUserPatient()) {
            this.router.navigate(['patient-dashboard']);
          } else {
            this.router.navigate(['appointments']);
          }
        } else if (result.errors) {
          this.toastrMessage.showInfoMsg(result.errors[0].message, 'Information');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg('Error occured at the backend!', 'Error');
      },
    );
  }

  private cancelAppointmentforReschedule() {
    const data = {
      apptId: this.rescheduleDetails.appointmentID,
    };
    this.appointmentService.cancelAppointment(data).subscribe((resp) => {
      if (resp) {
        this.paymentApi('reschedule');
      }
    });
  }

  openDialog(slotTime: string, slotDate: string) {
    const drId = this.doctorDetails?.doctorId || '';
    const dialogRef = this.dialog.open(BookAppointmentModalComponent, {
      disableClose: true,
      width: '500Px',
      data: {
        slotTime: slotTime,
        slotDate: slotDate,
        drId,
        fees: this.doctorDetails.consultFee,
        charge: this.doctorDetails.convenienceCharge,
        slotType: this.selectedDoctordata.slotType,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        if (this.role == 'CALLCENTRE') {
          this.router.navigate(['../book-appointment-dashboard'], { relativeTo: this.activateRoute });
        } else {
          this.router.navigate(['../appointments']);
        }
      }
    });
  }

  openReasonModel(content: any) {
    this.reasonModelRef = this.modelService.open(content, {
      backdrop: 'static',
      centered: true,
      size: 'md',
    });
  }

  backToHomeRe() {
    this.closeModel();
    if (this.role == AppConstants.patient) {
      this.router.navigate(['../patient-dashboard'], { relativeTo: this.activateRoute });
    } else {
      this.router.navigate(['../appointments']);
    }
  }

  private closeModel() {
    this.reasonModelRef?.close();
    this.rescheduleModelRef?.close();
  }

  backToHome() {
    this.closeModel();
    this.router.navigate(['patient-dashboard']);
  }
}
