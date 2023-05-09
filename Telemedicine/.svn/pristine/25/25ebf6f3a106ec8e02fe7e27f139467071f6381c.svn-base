import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { CustomValidators } from 'src/app/shared/commonBuildBlocks/services/Validators/customValidator.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-abha-tm-patient-verify',
  templateUrl: './abha-tm-patient-verify.component.html',
  styleUrls: ['./abha-tm-patient-verify.component.scss'],
})
export class AbhaTmPatientVerifyComponent implements OnInit {
  @Input() profileData: any;
  @Input() isUpdate: any;
  @Output() goToPatientPage = new EventEmitter<string>();
  verifyForm: FormGroup;
  changeMonth: string;
  drId: any;
  yearOfBirth: any;
  monthOfBirth: any;
  dayOfBirth: any;
  selectedDate: string;

  constructor(
    private fb: FormBuilder,
    private spinner: NgxSpinnerService,
    private toastrMessage: ToastMessageService,
    private patientService: PatientService,
    private datePipe: DatePipe,
  ) {}

  ngOnInit() {
    this.verifyForm = this.fb.group({
      firstName: ['', [Validators.required, CustomValidators.fullNameValidator(30)]],
      middleName: [''],
      lastName: [''],
      mobile: ['', [Validators.required, CustomValidators.mobileValidator]],
      email: ['', [Validators.email]],
      gender: ['', [Validators.required]],
      dob: [''],
      address: [''],
      healthIdNumber: ['', [Validators.required]],
      healthId: ['', [Validators.required]],
    });
    console.log(this.profileData);
    // this.verifyForm.patchValue(this.profileData);
    // this.verifyForm.controls.dob.setValue(this.profileData?.dayOfBirth+'/'+this.profileData?.monthOfBirth+'/'+this.profileData?.yearOfBirth);
    if (this.profileData?.monthOfBirth?.length < 2) {
      this.changeMonth = '0' + this.profileData?.monthOfBirth;
      console.log('month', this.changeMonth);
    } else {
      this.changeMonth = this.profileData?.monthOfBirth;
    }
    const date = this.changeMonth + '/' + this.profileData?.dayOfBirth + '/' + this.profileData?.yearOfBirth;
    this.selectedDate = this.datePipe.transform(date, 'yyyy-MM-dd');
    console.log(this.selectedDate);

    // this.verifyForm.get('dob').setValue(dateFormat);
    this.verifyForm.setValue({
      firstName: this.profileData.firstName,
      middleName: this.profileData.middleName,
      lastName: this.profileData.lastName,
      mobile: this.profileData.mobile,
      email: this.profileData.email,
      gender: this.profileData.gender,
      dob: this.selectedDate,
      address: this.profileData.address,
      healthIdNumber: this.profileData.healthIdNumber,
      healthId: this.profileData.healthId,
    });
    console.log('date', date);
    console.log('dateFormat', this.selectedDate);
    console.log('date ===>', this.verifyForm.get('dob').setValue(this.selectedDate));
    this.verifyForm.disable();
  }

  changeDate(event) {
    this.selectedDate = this.datePipe.transform(event.target.value, 'yyyy-MM-dd');
    console.log('this.selectedDate ====>', this.selectedDate);
  }

  // onClickSubmit() {
  //   console.log('form data ===>', this.verifyForm.value);
  //   this.spinner.show();
  //   if (this.isUpdate) {
  //     this.spinner.show();
  //     console.log('form data ===>', this.verifyForm.value);
  //     let data = {
  //       request: {
  //         healthId: this.verifyForm?.value?.healthId,
  //         healthNumber: this.verifyForm?.value?.healthIdNumber,
  //         mobileNo: this.verifyForm?.value?.mobile,
  //         aadhaarNo: sessionStorage.getItem('adhaarCard'),
  //         token: '',
  //       },
  //     };
  //     this.patientService.updateAbhaDetails(data).subscribe(
  //       (ele: any) => {
  //         this.spinner.hide();
  //         console.log('ele', ele);
  //         if(ele?.status) {
  //           this.toastrMessage.showSuccessMsg(ele['response']['message'], 'Success');
  //           this.goToPatientPage.emit();
  //         } else {
  //           this.toastrMessage.showErrorMsg(ele?.errors[0]?.message, 'Error');
  //         }
  //       },
  //       (error: any) => {
  //         this.spinner.hide();
  //         console.log(error);
  //       },
  //     );
  //   } else {
  //     this.drId = sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null;
  //     console.log('form data ===>', this.verifyForm.value);
  //     // return;
  //     let data = {
  //       request: {
  //         patientName:
  //           this.verifyForm?.value?.firstName +
  //           ' ' +
  //           this.verifyForm?.value?.middleName +
  //           ' ' +
  //           this.verifyForm?.value?.lastName,
  //         ptMobileNo: this.verifyForm?.value?.mobile,
  //         doctorUserID: this.drId,
  //         ptDateOfBirth: this.selectedDate,
  //         healthNo: this.verifyForm?.value?.healthNo,
  //         healthId: this.verifyForm?.value?.healthId ? this.verifyForm?.value?.healthId : '',
  //       },
  //     };
  //     this.patientService.patientRegistartionByScribeOrDoctorWithHealthId(data).subscribe(
  //       (ele: any) => {
  //         this.spinner.hide();
  //         console.log('ele', ele);
  //         if(ele?.status) {
  //           this.toastrMessage.showSuccessMsg(ele['response']['message'], 'Success');
  //           this.goToPatientPage.emit();
  //         } else {
  //           this.toastrMessage.showErrorMsg(ele?.errors[0]?.message, 'Error');
  //         }

  //       },
  //       (error: any) => {
  //         this.spinner.hide();
  //         console.log(error);
  //       },
  //     );
  //   }
  // }
  onClickSubmit() {
    console.log('form data ===>', this.verifyForm.value);
    this.spinner.show();
    // if (this.isUpdate) {
    //   this.spinner.show();
    //   console.log('form data ===>', this.verifyForm.value);
    //   let data = {
    //     request: {
    //       healthId: this.verifyForm?.value?.healthId,
    //       healthNumber: this.verifyForm?.value?.healthIdNumber,
    //       mobileNo: this.verifyForm?.value?.mobile,
    //       aadhaarNo: sessionStorage.getItem('adhaarCard'),
    //       token: '',
    //     },
    //   };
    //   this.patientService.updateAbhaDetails(data).subscribe(
    //     (ele: any) => {
    //       this.spinner.hide();
    //       console.log('ele', ele);
    //       if(ele?.status) {
    //         this.toastrMessage.showSuccessMsg(ele['response']['message'], 'Success');
    //         this.goToPatientPage.emit();
    //       } else {
    //         this.toastrMessage.showErrorMsg(ele?.errors[0]?.message, 'Error');
    //       }
    //     },
    //     (error: any) => {
    //       this.spinner.hide();
    //       console.log(error);
    //     },
    //   );
    // } else {
    this.drId = sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null;
    console.log('form data ===>', this.verifyForm.value);
    // return;
    let data = {
      request: {
        patientName:
          this.verifyForm?.value?.firstName +
          ' ' +
          this.verifyForm?.value?.middleName +
          ' ' +
          this.verifyForm?.value?.lastName,
        ptMobileNo: this.verifyForm?.value?.mobile,
        doctorUserID: this.drId,
        ptDateOfBirth: this.selectedDate,
        healthNo: this.verifyForm?.value?.healthNo,
        healthId: this.verifyForm?.value?.healthId ? this.verifyForm?.value?.healthId : '',
      },
    };
    this.patientService.patientRegistartionByScribeOrDoctorWithHealthId(data).subscribe(
      (ele: any) => {
        this.spinner.hide();
        console.log('ele', ele);
        if (ele?.status) {
          this.toastrMessage.showSuccessMsg(ele['response']['message'], 'Success');
          this.goToPatientPage.emit();
        } else {
          this.toastrMessage.showErrorMsg(ele?.errors[0]?.message, 'Error');
        }
      },
      (error: any) => {
        this.spinner.hide();
        console.log(error);
      },
    );
    // }
  }

  onClickCancel() {
    this.goToPatientPage.emit();
  }
}
