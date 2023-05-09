import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { WebcamImage, WebcamInitError } from 'ngx-webcam';
import { Observable, Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { CustomValidators } from 'src/app/shared/commonBuildBlocks/services/Validators/customValidator.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { Location } from '@angular/common';
@Component({
  selector: 'app-healthid-creation-mobile-assisted',
  templateUrl: './healthid-creation-mobile-assisted.component.html',
  styleUrls: ['./healthid-creation-mobile-assisted.component.scss'],
})
export class HealthidCreationMobileAssistedComponent implements OnInit {
  createHealthIDChecked: boolean = false;
  mapHealthIDChecked: boolean = true;
  name: any;
  gender: any;
  mobileNoSel: any;
  email: any;
  dob: any;
  txnId: any;
  isNameDisabled: boolean = true;
  isMobileNoDisabled: boolean = true;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private consultationService: ConsultationService,
    private spinner: NgxSpinnerService,
    private _location: Location
  ) { }

  isSwitcheChecked = false;
  /**
   * Used to store SignIn Form.
   */
  form: FormGroup;

  disableSelect = new FormControl(false);

  /**
   * used to show progress bar for file upload
   */
  progress = 90;

  /**
   * personal details of patient
   */
  personalDataList = [
    {
      stepName: 'Patient Full Name',
      stepValue: '',
    },
    {
      stepName: 'Mobile No',
      stepValue: '',
    },
    {
      stepName: 'Email',
      stepValue: '',
    },
  ];

  profilePhotoBase64;
  uploadedFile;

  uploadedImage = 'assets/images/img_avatar.png';

  verifyDoc = '';
  public webcamImage: WebcamImage = null;
  private trigger: Subject<void> = new Subject<void>();
  public controlDetailsForExistenceCheck = [
    {
      controlName: 'patientName',
      type: 'userId',
    },
    {
      controlName: 'mobileNo',
      type: 'mobile',
    },
    {
      controlName: 'email',
      type: 'email',
    },
  ];

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      if (params && params.name) {
        this.name = params.name;
      }
      if (params && params.gender) {
        this.gender = params.gender;
      }
      if (params && params.mobileNoSel) {
        this.mobileNoSel = params.mobileNoSel;
      }
      if (params && params.emailId) {
        this.email = params.emailId;
      }
      if (params && params.dob) {
        this.dob = params.dob;
      }
      if (params && params.txnId) {
        this.txnId = params.txnId;
      }
    });
    this.formValidation();
    this.controlDetailsForExistenceCheck.forEach((details) => {
      this.checkForEmailExistance(details.controlName, details.type);
    });
  }

  public handleInitError(error: WebcamInitError): void {
    if (
      error.mediaStreamError &&
      error.mediaStreamError.name === 'NotAllowedError'
    ) {
      this.toastrMessage.showWarningMsg(
        'Camera access was not allowed by user!',
        'Information'
      );
    }
  }

  triggerSnapshot(): void {
    this.isSwitcheChecked = !this.isSwitcheChecked;
    this.trigger.next();
  }

  handleImage(webcamImage: WebcamImage): void {
    this.profilePhotoBase64 = webcamImage['_imageAsDataUrl'];
    this.uploadedImage = this.profilePhotoBase64;
  }

  public get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
  }

  onClickSwitch() {
    this.isSwitcheChecked = !this.isSwitcheChecked;
  }

  formValidation() {
    this.form = this.fb.group(
      {
        profilePhoto: [''],
        patientName: [
          '',
          [Validators.required, CustomValidators.fullNameValidator(30)],
        ],
        mobileNo: ['', [Validators.required, CustomValidators.mobileValidator]],
        email: [
          '',
          [Validators.email, Validators.maxLength(250)],
        ],
      },
      {}
    );
    this.setPersonalDetails();
  }

  setPersonalDetails() {
    this.form.valueChanges.subscribe((value) => {
      this.personalDataList[0].stepValue = value.patientName;
      this.personalDataList[1].stepValue = value.mobileNo;
      this.personalDataList[2].stepValue = value.email;
    });
  }

  verifyFormData() {
    this.spinner.show();
    if (this.form.invalid) {
      this.spinner.hide();
      this.form.markAllAsTouched();
      this.toastrMessage.showErrorMsg(
        ErrorSuccessMessage.PATIENTDETAILS,
        'Warning'
      );
      return;
    } else {
      if (this.createHealthIDChecked) {
        this.spinner.hide();
        ///AssistedEnterMobile
        this.router.navigate(['/healthidMobile'], {
          queryParams: {
            patientName: this.form.get('patientName').value,
            mobileNo: this.form.get('mobileNo').value,
            email: this.form.get('email').value,
            gender: this.gender,
            dob: this.dob,
          }, skipLocationChange: true
        });
      } else if (this.mapHealthIDChecked) {
        this.spinner.hide();
        this.router.navigate(['/ProfileCreate'], {
          queryParams: {
            patientName: this.form.get('patientName').value,
            mobileNo: this.form.get('mobileNo').value,
            email: this.form.get('email').value,
            gender: this.gender,
            dob: this.dob,
          }, skipLocationChange: true
        });
      } else {
        this.spinner.hide();
        this.toastrMessage.showErrorMsg(
          "Please choose any option",
          'Warning'
        );
      }
    }
  }

  /**
   * Used to Login User
   */
  registerDoctor() {
    this.spinner.show();
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.spinner.hide();
      return;
    } else if (
      !this.form.get('patientName').value &&
      !this.form.get('patientName').value
    ) {
      this.spinner.hide();
      this.toastrMessage.showErrorMsg(
        'Please enter Patient Name',
        'Warning'
      );
    } else if (
      this.form.get('mobileNo').value === this.form.get('mobileNo').value
    ) {
      this.spinner.hide();
      this.toastrMessage.showErrorMsg(
        'Please enter Mobile Number',
        'Warning'
      );
    } else if (this.form.get('email').value === this.form.get('email').value) {
      this.spinner.hide();
      this.toastrMessage.showErrorMsg(
        'Please enter email',
        'Warning'
      );
    } else {
      let { profilePhoto, patientName, mobileNo, email } = this.form.value;
      let payload = {
        drFullName: patientName,
        drMobNo: mobileNo,
        drEmail: email,

        drProfilePhoto: this.profilePhotoBase64 ? this.profilePhotoBase64 : '',
      };
      // this.userManagementService.registerDoctor(payload, this.uploadedFile).subscribe((result) => {
      //   this.spinner.hide();
      //     if (result['response'] && result['status'] ) {
      //       let message = "Thank You for registering into TeleMedicine. Your application is pending for verification, you will be able to login once the verification process is completed. You will receive a notification of the same on your registered Email ID / Mobile no."
      //       let role = 'doctor';
      //       const dialogRef = this.dialog.open(CongratulationsModalComponent, {
      //         disableClose: true ,
      //         width: '500px',
      //         data: {
      //           role: role ? role : null,
      //           message: message
      //         }
      //       });

      //         if(role === 'scribe'){
      //           this.router.navigate(['appointments']);
      //         }else {
      //           this.router.navigate(['sign-in']);
      //         }
      //     }
      //     if (result['status'] == false) {

      //       this.toastrMessage.showErrorMsg( result['errors'][0].message, 'Error');
      //     }
      //   },error => {
      //     this.spinner.hide();

      //     this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
      //   });
    }
  }

  /**
   * Used to set selected profile photo
   */
  uploadProfilePhoto(event) {
    let hasImg = event.target.files[0].type.includes('image') ? true : false;
    let hasSize = event.target.files[0].size <= 1000000 ? true : false; //1000000
    if (hasImg && hasSize) {
      const files = event.target.files;
      const file = files[0];
      if (files && file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.profilePhotoBase64 = e.target.result;
        };
        reader.readAsDataURL(event.target.files[0]);
        //  this._handleReaderLoaded(reader);
      }
      document
        .getElementById('ProfilePhoto')
        .setAttribute('src', window.URL.createObjectURL(event.target.files[0]));
    } else {
      if (!hasImg) {
        this.toastrMessage.showWarningMsg(
          ErrorSuccessMessage.IMGFORMATE,
          'Warning'
        );
        event.target.files = '';
      }
      if (!hasSize) {
        this.toastrMessage.showWarningMsg(
          ErrorSuccessMessage.IMGSIZE,
          'Warning'
        );
        event.target.files = '';
      }
    }
  }

  /**
   * Used to select profile photo
   */
  browse() {
    document.getElementById('browse').click();
  }

  _handleReaderLoaded(readerEvt) {
    const binaryString = readerEvt.result;
    this.profilePhotoBase64 = btoa(binaryString);
    return this.profilePhotoBase64;
  }

  verify() {
    this.consultationService
      .verifyDoctor(this.verifyDoc)
      .subscribe((result) => { });
  }

  onClickedOutside(e: Event, type) {
    let value;
    if (type === 'mobile') {
      value = this.form.get('mobileNo').value;
    } else if (type === 'email') {
      value = this.form.get('email').value;
    }
    if (value) {
      this.consultationService.checkDuplicate(value).subscribe(
        (result) => {
          if (result['response']) {
            if (!result['response'].isavailable) {
              let value;
              if (type === 'mobile') {
                this.form.controls['mobileNo'].setValue('');
              } else if (type === 'email') {
                this.form.controls['email'].setValue('');
              }
              this.toastrMessage.showErrorMsg(
                result['response'].message,
                'Error'
              );
            }
          } else if (result['errors']) {
            if (type === 'mobile') {
              this.form.controls['mobileNo'].setValue('');
            } else if (type === 'email') {
              this.form.controls['email'].setValue('');
            }
            this.toastrMessage.showErrorMsg(
              result['errors'][0].message,
              'Error'
            );
          }
        },
        (error) => {
          if (type === 'mobile') {
            this.form.controls['mobileNo'].setValue('');
          } else if (type === 'email') {
            this.form.controls['email'].setValue('');
          }
          this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
        }
      );
    }
  }

  checkForEmailExistance(controlName, type) {
    this.form
      .get(controlName)
      .statusChanges.pipe(debounceTime(2000))
      .subscribe((status) => {
        if (status === 'VALID') {
          const data = {
            request: { [type]: this.form.get(controlName).value },
          };
          this.consultationService.checkDuplicate(data).subscribe(
            (result) => {
              if (!result['response'].isavailable) {
                this.form.get(controlName).setValue('');
                this.toastrMessage.showErrorMsg(
                  result['response'].message,
                  'Error'
                );
              } else if (result['errors']) {
                this.toastrMessage.showErrorMsg(
                  result['errors'][0].message,
                  'Error'
                );
              }
            },
            (error) => {
              this.toastrMessage.showErrorMsg(
                error['errors'][0].message,
                'Error'
              );
            }
          );
        }
      });
  }

  reset() {
    this.form.reset();
    this.form.markAsPristine();
  }

  onClickCancel() {
    // this.router.navigate(['']);
    this.router.navigate(['sign-in']);
  }
  createHealthIDCheck() {
    this.createHealthIDChecked = true;
    this.mapHealthIDChecked = false;
  }
  mapHealthIDCheck() {
    this.createHealthIDChecked = false;
    this.mapHealthIDChecked = true;
  }
  back() {
    this._location.back();
  }
}
