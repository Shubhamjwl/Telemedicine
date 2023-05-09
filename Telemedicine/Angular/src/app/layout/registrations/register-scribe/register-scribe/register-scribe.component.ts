import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { WebcamImage, WebcamInitError } from 'ngx-webcam';
import { Observable, Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { OtpModalComponent } from 'src/app/layout/shared-modules/modals/otp-modal/otp-modal.component';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { MasterTypes } from 'src/app/shared/commonBuildBlocks/enum/master-type.enum';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { ScribeService } from 'src/app/shared/commonBuildBlocks/services/scribeServices/scribe.service';
import { CustomValidators } from 'src/app/shared/commonBuildBlocks/services/Validators/customValidator.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { passwordMatchValidator } from 'src/app/shared/commonBuildBlocks/validators/match-password.validator';
import { MobileNoValidator } from 'src/app/shared/commonBuildBlocks/validators/mobile-no.validator';
import { PasswordPatternValidator } from 'src/app/shared/commonBuildBlocks/validators/password-pattern.validator';

@Component({
  selector: 'app-register-scribe',
  templateUrl: './register-scribe.component.html',
  styleUrls: ['./register-scribe.component.scss']
})
export class RegisterScribeComponent implements OnInit  {

  constructor(
    private dialog: MatDialog,
    private fb: FormBuilder,
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private toastrMessage: ToastMessageService,
    private scribeService: ScribeService,
    private consultationService: ConsultationService,
    private spinner: NgxSpinnerService
    ) { 
      this.randomStringForCaptcha = this.randomString(10, '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ');
      this.activatedRoute.data
        .subscribe(v => {
          this.user = v?.user ? v?.user : 'patient';
        });
        this.getGenderList();
    }

    /**
     * Used to store SignIn Form.
     */
    form: FormGroup ;
  
    user;
    userID = sessionStorage.getItem('USER_ID');
    /**
     * Used to show | hide password.
     */
    hidePassword = true;
    hideReEnterPassword = true;
  
    genderList = [];
    disableSelect = new FormControl(false);

    /**
     * personal details of patient
     */
    personalDataList = [
      {
        "stepName":"Full Name",
        "stepValue":"",
      },
      {
        "stepName":"Mobile No",
        "stepValue":"",
      },
      {
        "stepName":"Email",
        "stepValue":"",
      },
      {
        "stepName":"Username",
        "stepValue":"",
      },
    ];
  
    profilePhotoBase64;
    randomStringForCaptcha;
    captcha;
    uploadedImage = 'assets/images/img_avatar.png';

    isSwitcheChecked = false;
    public webcamImage: WebcamImage = null;
    private trigger: Subject<void> = new Subject<void>();
    confirmPassword = false;

    public controlDetailsForExistenceCheck = [
      {
        controlName: 'email',
        type: 'email'
      },
      {
        controlName: 'mobileNo',
        type: 'mobile'
      },
      {
        controlName: 'userName',
        type: 'userId'
      },
    ]

    ngOnInit() {
      this.formValidation();
      this.getCpachaImage();
      this.getState();
      this.controlDetailsForExistenceCheck.forEach(details => {
        this.checkForEmailExistance(details.controlName, details.type);
      });

    }

    randomString(length, chars) {
      var result = '';
      for (var i = length; i > 0; --i) result += chars[Math.floor(Math.random() * chars.length)];
      return result;
    }

    public handleInitError(error: WebcamInitError): void {
      if (error.mediaStreamError && error.mediaStreamError.name === "NotAllowedError") {
        this.toastrMessage.showWarningMsg("Camera access was not allowed by user!", "Information");
      }
    }

    triggerSnapshot(): void {
      this.isSwitcheChecked = !this.isSwitcheChecked;
      this.trigger.next();
    }

    handleImage(webcamImage: WebcamImage): void {
    this.profilePhotoBase64 = webcamImage['_imageAsDataUrl']
    this.uploadedImage = this.profilePhotoBase64;
    }
    public get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
    }

    onClickSwitch() {
      this.isSwitcheChecked = !this.isSwitcheChecked; 
    }
    patientForm = this.fb.group({
      profilePhoto: [''],
      patientName: ['', [Validators.required, CustomValidators.fullNameValidator(30)]],
      mobileNo: ['', [Validators.required, CustomValidators.mobileValidator]],
      email: ['',[Validators.required, Validators.email, Validators.maxLength(250)]],
      userName: ['', [Validators.required,CustomValidators.userNameValidator]],
      password: ['', [Validators.required, PasswordPatternValidator.strong]],
      reEnterPassword: ['',Validators.required],
      cpacha: ['',Validators.required]
    }, {validator: passwordMatchValidator});
    
    scribeForm = this.fb.group({
      profilePhoto: [''],
      patientName: ['', [Validators.required, CustomValidators.fullNameValidator(30)]],
      mobileNo: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10), MobileNoValidator.strong]],
      email: ['',[Validators.required, Validators.email, Validators.maxLength(250)]],
      userName: ['', [Validators.required, CustomValidators.userNameValidator]],
      password: ['', [Validators.required, PasswordPatternValidator.strong]],
      reEnterPassword: ['',Validators.required],
      cpacha: ['',Validators.required],
      address1: ['', Validators.required],
      address2: [''],
      address3: [''],
      isDefaultScribe: [''],
      city: ['', Validators.required],
      state: ['', Validators.required],
      gender:['', Validators.required],
    }, {validator: passwordMatchValidator});

    formValidation() {
      if(this.user !== 'scribe') {
        this.form = this.patientForm
      }else if(this.user === 'scribe') {
        this.form = this.scribeForm
      }
      this.setPersonalDetails();
    }

    /* Called on each input in either password field */
    onPasswordInput() {
      if (this.form.hasError('passwordMismatch'))
        this.form.get('reEnterPassword').setErrors([{'passwordMismatch': true}]);
      else
        this.form.get('reEnterPassword').setErrors(null);
    }

    getGenderList() {
      this.consultationService.getMasterDetailsListByMasterName(MasterTypes.GENDER).subscribe(result => {
        if(result.status && result.response){
          this.genderList = result.response
        }else if(result.errors) {
          this.toastrMessage.showErrorMsg( result.errors[0].message, 'Error');
        }
      },error => {
        // console.log(error["errors"]);
        this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
      })
    }

    setPersonalDetails() {
      this.form.valueChanges.subscribe(value => {
          this.personalDataList[0]['stepValue'] = value.patientName;

          this.personalDataList[1]['stepValue'] = value.mobileNo;

          this.personalDataList[2]['stepValue'] = value.email;

          this.personalDataList[3]['stepValue'] = value.userName;

      });
    }
    
    generateCaptcha() {
      this.authService.generateCaptcha(this.randomStringForCaptcha).subscribe(result => {
        this.createImageFromBlob(result);
      }),error => {
        alert(error);
      }
    }

    createImageFromBlob(image: Blob) {
      let reader = new FileReader();
      reader.addEventListener('load', () => {
        this.captcha = reader.result
      }, false);
      if(image) {
        reader.readAsDataURL(image);
      }
    }
  
    verifyCaptcha() {
      this.spinner.show();
      if(this.form.invalid) {
        this.spinner.hide();
        this.form.markAllAsTouched();
        return;
      }else {
        if(this.user === 'scribe'){
          this.spinner.hide();
          this.registerScribe();
        }else {
          let captcha = this.form.get('cpacha').value;
          this.authService.verifyCaptcha(this.randomStringForCaptcha,captcha).subscribe(result => {
            this.spinner.hide();
            if(result['status']) {
                this.registerPatient();
            }else{
              this.refreshCpacha();
              this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
            }
          },error => {
            this.spinner.hide();
            this.refreshCpacha();
            this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
          })
        }
      }
    }

    /**
     * Used to Login User
     */
    registerPatient() {
      this.spinner.show();
      if(this.form.invalid) {
        this.spinner.hide();
        this.form.markAllAsTouched();
        return;
      }else {
        const {profilePhoto, patientName, mobileNo, email, userName, password} = this.form.value;
        let payload = {
          ptProfilePhoto: this.profilePhotoBase64 ? this.profilePhotoBase64 : '',
          ptFullName: patientName,
          ptMobNo: mobileNo,
          ptEmail: email,
          ptUserID: userName,
          ptPassword: password,
          captchaCode: this.form.get('cpacha').value,
          sessionId: this.randomStringForCaptcha,
        }
        this.authService.registerPatient(payload).subscribe(result => {
          this.spinner.hide();
          if(result['response']) {
            this.openDialog(userName, result['response'].message);
          }else {
            if(result['errors'][0]) {
              this.refreshCpacha();
              this.toastrMessage.showErrorMsg( result['errors'][0].message, 'Error');
            }
          }
        },error => {
          this.spinner.hide();
          this.refreshCpacha();
          this.toastrMessage.showErrorMsg( error['errors'][0].message, 'Error');
        })
      }
    }

    
    registerScribe() {
      this.spinner.show();
      if(this.form.invalid) {
        this.spinner.hide();
        this.form.markAllAsTouched();
        return;
      }else {
        const {profilePhoto, patientName, mobileNo, email, userName, password,
          address1, address2, address3, isDefaultScribe, gender, state, city} = this.form.value;

        let payload =   {
          scrbFullName : patientName,
          scrbMobNo : mobileNo,
          scrbEmail : email,
          scrbUserID : userName,
          scrbdrUserIDfk : this.userID,
          scrbPassword : password,
          scrbGender: gender ? gender : '',
          scrbAdd1 : address1,
          scrbAdd2 : address2,
          scrbAdd3 : address3,
          scrbAdd4 : "",
          isDefaultScribe : isDefaultScribe ? 'Y' : 'N',
          captchaCode: this.form.get('cpacha').value,
          sessionId: this.randomStringForCaptcha,
          scribeProfilePhoto : this.profilePhotoBase64 ? this.profilePhotoBase64 : '',
          scrbState: state,
          scrbCity: city
        }
        
        this.scribeService.registerScribe(payload).subscribe(result => {
          this.spinner.hide();
          if(result['response']) {
            if(result['status']) {
              // this.openDialog(userName);
              this.router.navigate(['appointments']);
              this.toastrMessage.showSuccessMsg( ErrorSuccessMessage.SCRIBEREGISTER, 'Success')
            }else {
              if(result['errors'][0]) {
                this.refreshCpacha();
                this.toastrMessage.showErrorMsg( result['errors'][0].message, 'Error');
              }
            }
          }
          },error => {
            this.spinner.hide();
            this.refreshCpacha();
            this.toastrMessage.showErrorMsg( error['errors'][0].message, 'Error');
          });
        }
      }


  /**
   * Used to Navigate to OTP Dialog Modal
   */
  openDialog(userName: string, message): void {
    let role ;
    if(this.user !== 'scribe') {
      role = 'patient'
    }else if(this.user === 'scribe') {
      role = 'scribe'
    }
    const dialogRef = this.dialog.open(OtpModalComponent,{
      disableClose: true ,
      width: '500px',
      data: {type: 'doctorRegister',
      userID : userName,
      role : role, formValue: this.form, message}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.form.reset();
      this.form.markAsPristine();
      this.refreshCpacha();
      this.router.navigate(['sign-in']);
    });
  }
  
   /**
   * Used to set selected profile photo
   */
  uploadProfilePhoto(event) {
    let hasImg = event.target.files[0].type.includes('image') ? true : false;
    let hasSize = event.target.files[0].size <= 1000000 ? true : false; //1000000
    if(hasImg && hasSize) {
      const files = event.target.files;
      const file = files[0];
      if (files && file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.profilePhotoBase64 = e.target.result;
        };
        reader.readAsDataURL(event.target.files[0]);
      }
      document
        .getElementById('ProfilePhoto')
        .setAttribute('src', window.URL.createObjectURL(event.target.files[0]));
    }else{
      if(!hasImg){
        this.toastrMessage.showWarningMsg(ErrorSuccessMessage.IMGFORMATE, 'Warning');
        event.target.files = ''; 
      }
      if(!hasSize){
        this.toastrMessage.showWarningMsg(ErrorSuccessMessage.IMGSIZE, 'Warning');
        event.target.files = ''; 
      }
    }
  }

  /**
   * Used for upload profile photo
   */
  browse() {
    document.getElementById("browse").click();
  }

  _handleReaderLoaded(readerEvt) {
    const binaryString = readerEvt.result;
           this.profilePhotoBase64 = btoa(binaryString);
           return this.profilePhotoBase64;
   }

  /**
   * Used to get the CPACHA image from API response
   */
  getCpachaImage() {
    this.generateCaptcha();
  }

  /**
   * Used to get New CPACHA image from API response
   */
  refreshCpacha() {
    this.randomStringForCaptcha = this.randomString(
      10,
      '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
    );
    this.form.controls['cpacha'].setValue(null);
    this.getCpachaImage();
    
  }

  
  states = []
  /**
   * Used to get State
  */
  getState(){
    this.consultationService.getStateList().subscribe(result => {
      if(result['status'] && result['response']){
        this.states = result['response']
      }else if(result['errors']) {
        this.toastrMessage.showErrorMsg( result['errors'][0].message, 'Error');
      }
    },error => {
      this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
    })
  }

  cities = []
  
  onSelectState(value) {
    if(value) {
      this.getCity(value);
    }
  }

  /**
   * Used to get State
  */
 getCity(state){
  if(state) {
    this.consultationService.getCityList(state).subscribe(result => {
      if(result['status'] && result['response']){
        this.cities = result['response']
      }else if(result['errors']) {
        this.toastrMessage.showErrorMsg( result['errors'][0].message, 'Error');
      }
    },error => {
      this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
    })
  }
}

  
  checkForEmailExistance(controlName, type) {
    this.form.get(controlName).statusChanges
    .pipe(debounceTime(2000))
    .subscribe(
      status => {
        if(status === 'VALID') {
          const data = { 
            request : {[type]: this.form.get(controlName).value}
          };
          this.consultationService.checkDuplicate(data).subscribe(result => {
            if (!result['response'].isavailable) {
              this.form.get(controlName).setValue('');
              this.toastrMessage.showErrorMsg( result['response'].message, 'Error');
            } else if(result['errors']) {
              this.toastrMessage.showErrorMsg( result['errors'][0].message, 'Error');
            }
          },error => {
            this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
          })
        }
      }
    )
  }

  reset() {
    this.form.reset();
    this.form.markAsPristine();
    this.refreshCpacha();
  }

  onClickCancel(){
    if(this.user === 'scribe'){
      this.router.navigate(['appointments']); 
    }else {
      this.router.navigate(['sign-in']);
    }
  }
}