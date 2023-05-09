import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable } from 'rxjs';
import { debounceTime, map, startWith } from 'rxjs/operators';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { IConsultationPatientDetails } from 'src/app/shared/commonBuildBlocks/interfaces/consultation-patient-details.interface';
import { IPatient } from 'src/app/shared/commonBuildBlocks/interfaces/patient.interface';
import { IPatientDetailsByAppointmentIDSaveSpec } from 'src/app/shared/commonBuildBlocks/saveSpecs/patient-details-by-appointment-id.savespec';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { CustomValidators } from 'src/app/shared/commonBuildBlocks/services/Validators/customValidator.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-prescribe-service',
  templateUrl: './prescribe-service.component.html',
  styleUrls: ['./prescribe-service.component.scss'],
})
export class PrescribeServiceComponent implements OnInit {
  private patientDetailsList;
  filteredOptions: Observable<IPatient[]>;
  form: FormGroup;
  ptForm: FormGroup;
  doctorDetails: any;
  @ViewChild("formS") formPr: ElementRef;
  actionURL = environment.prescribedUrl;
  mobileNo = sessionStorage.getItem('mobileNo');
  patient: any;

  isValid = false;
  clientSecret: any;
  transactionID: any;
  ptName: any;
  ptMobile: any;
  dremail: any;
  emailid: string;
  constructor(
    private patientService: PatientService,
    private dataPassingService: DataPassingService,
    private fb: FormBuilder,
    private toastrMessage: ToastMessageService,
    private spinner: NgxSpinnerService,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private dialog: MatDialog,
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private consultationService: ConsultationService
  ) {}

  ngOnInit(): void {
    this.doctorDetails = this.dataPassingService.getDoctorDetails();
    this.getPatientDetails();
    this.initForm();
  }

  initForm() {
    this.form = this.fb.group({
      searchPtByName: [null, Validators.required],
    });

    this.ptForm = this.fb.group({
      patientName: [
        '',
        [Validators.required],
      ],
      patientMobileNo: [
        '',
        [Validators.required, CustomValidators.mobileValidator],
      ],
    });
    this.form
      .get('searchPtByName')
      .valueChanges.pipe(debounceTime(1000))
      .subscribe((value) => {
        if (value) {
          if (typeof value === 'string') {
            this.getPatientDetailS(value);
            setTimeout(() => {
              this.isValid = false;
            });
            this.ptForm.enable({ emitEvent: false });
          } else {
            setTimeout(() => {
              this.isValid = true;
            });
            this.ptForm.reset();
            this.ptForm.disable({ emitEvent: false });
          }
        } else {
          this.isValid = true;
          this.ptForm.enable({ emitEvent: false });
        }
      });

    this.ptForm.valueChanges.subscribe((value) => {
      let { patientName, patientMobileNo } = this.ptForm.controls;
      if (
        patientName.value &&
        patientName.valid &&
        patientMobileNo.value &&
        patientMobileNo.valid
      ) {
        this.isValid = true;
        this.form.reset();
        this.form.disable({ emitEvent: false });
      } else {
        this.isValid = false;
        this.form.enable({ emitEvent: false });
      }
    });
  }

  getPatientDetailS(nameOrMob) {
    this.patientService.getPatientByName(nameOrMob).subscribe(
      (result: any) => {
        if (result.status && result.response) {
          this.patientDetailsList = result
            ? result.response
              ? result.response
              : null
            : null;
          this.filterOptions();
        } else {
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
      }
    );
  }

  filterOptions() {
    this.filteredOptions = this.form.get('searchPtByName').valueChanges.pipe(
      startWith(''),
      map((value: any) => (typeof value === 'string' ? value : null)),
      map((name) =>
        name ? this._filter(name) : this.patientDetailsList.slice()
      )
    );
  }
  displayFn(user): string {
    return user && user.ptFullName ? user.ptFullName : '';
  }
  private _filter(name): IPatient[] {
    const filterValue = name.toLowerCase();
    if (this.hasNumbers(filterValue)) {
      let mobNo: number = +filterValue;
      return this.patientDetailsList.filter(
        (option) => option.ptMobNo.indexOf(mobNo) === 0
      );
    } else {
      return this.patientDetailsList.filter(
        (option) => option.ptFullName.toLowerCase().indexOf(filterValue) === 0
      );
    }
  }

  hasNumbers(t) {
    var regex = /\d/g;
    return regex.test(t);
  }

  getPatientDetails() {
    this.consultationService
      .getPatientDeailsByAppointmenttID(
        this.activatedRoute.snapshot
          .queryParams as IPatientDetailsByAppointmentIDSaveSpec
      )
      .subscribe(
        (res) => {
          if (res.response) {
            this.saveMarketPlaceDetails(this.transactionID);
          } else if (res.errors) {
          }
        },
        (error) => {}
      );
  }

  /* for prescribe button onclick */
  onClickPrescribed() {
    this.patientRegistration();
  }


  patientRegistration(){
    const { patientName, patientMobileNo } = this.ptForm.value;
     if(patientName && patientMobileNo){
      let payload = {
        patientName: patientName ? patientName : null,
        doctorUserID: sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null, 
        ptMobileNo: patientMobileNo ? patientMobileNo : null,
     }
     this.patientService.patientRegistartionByScribeOrDoctor(payload).subscribe(result => {  this.spinner.hide();
      if (result.status) {
          this.getTransactionId();
      } else {
        if (result.errors) {
          this.toastrMessage.showErrorMsg( result.errors[0].message, 'Error');
        }
      }
    },error => {
      this.spinner.hide();
      this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
    })
  } else{
    this.getTransactionId();
  }
  }

  getTransactionId(){
    this.consultationService.getTransactionId().subscribe(
      {
        next:(res)=>{
            this.transactionID= res.transactionId;
            this.saveMarketPlaceDetails(this.transactionID);
        },
        error:(err)=>{
     console.log("enter in error");
        }
      }
  
    )
  }

  saveMarketPlaceDetails(transactionID) {
    this.emailid = sessionStorage.getItem('emailID');
    const drUserId = sessionStorage.getItem('USER_ID');
    let fullName = '';
    let mobNo = '';
    const { patientName, patientMobileNo } = this.ptForm.value;
    if (patientName && patientMobileNo) {
      fullName = patientName;
      mobNo = patientMobileNo;
    } else {
      const {
        ptFullName,
        ptMobNo,
      } = this.form?.controls?.searchPtByName?.value;
      (fullName = ptFullName), (mobNo = ptMobNo);
    }
    let data = {
      request: {
        transtId:transactionID,
        drEmail:this.emailid,
        ptNname: fullName,
        ptMobile:mobNo,
      },
    };
    this.spinner.show();
    this.consultationService
      .saveMarketPlaceDetails(data)
      .subscribe((result) => {
        this.spinner.hide();
        this.clientSecret = result.response.clientSecret;
        this.ptName = result.response.patientName; 
        this.ptMobile = result.response.patientContact;
        setTimeout(()=>{
          this.formPr.nativeElement.submit();
        },1000)
      });
  }
}
