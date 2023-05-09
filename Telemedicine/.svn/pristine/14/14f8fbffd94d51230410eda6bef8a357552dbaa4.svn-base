import { Component, ElementRef, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { WebcamImage, WebcamInitError } from 'ngx-webcam';
import { Observable, Subject } from 'rxjs';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { DoctorService } from 'src/app/shared/commonBuildBlocks/services/doctorServices/doctor.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { CustomValidators } from 'src/app/shared/commonBuildBlocks/services/Validators/customValidator.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-tm-patient-registration-by-doc-sc',
  templateUrl: './tm-patient-registration-by-doc-sc.component.html',
  styleUrls: ['./tm-patient-registration-by-doc-sc.component.scss'],
})
export class TmPatientRegistrationByDocSCComponent implements OnInit {
  @ViewChild('abhaRef') abhaRef: TemplateRef<any>;
  ptRegId: any;
  data: any;
  drId: string;
  AADHAAR_OTP: any;
  MOBILE_OTP: any;
  uploadedFile: any;
  txnId: any;
  accountProfileData: any;
  isUpdate: boolean = false;
  isVisible: any;
  showMsg: boolean = false;
  selectedLink: string;
  isChecked;
  isChecked2: string;
  otp: any;
  adhaarNumber: any;
  adhaarOtp: any;
  showPatientVerificationPage: boolean = false;
  ndhmFlag: boolean;
  timeLeft: number = 60;
  interval;

  private abhaDialogRef: NgbModalRef;
  showTimer: boolean;
  showResendButton: boolean = false;
  formVerifyOtp: FormGroup;
  showPatientVerify: boolean;
  isAadharCardIsValid: boolean = false;
  showOtpForNumber: boolean = false;
  showTimerForOtp: boolean = false;
  aadharValidationForm: FormGroup;
  otpValidationForm: FormGroup;
  // healthId: any;
  // username: string = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private spinner: NgxSpinnerService,
    private toastrMessage: ToastMessageService,
    private patientService: PatientService,
    private documentService: DocumentService,
    private doctorService: DoctorService,
    private modelService: NgbModal,
  ) {}

  /**
   * Used to store SignIn Form.
   */
  form: FormGroup = this.fb.group({
    profilePhoto: [''],
    patientName: ['', [Validators.required, CustomValidators.fullNameValidator(30)]],
    mobileNo: ['', [Validators.required, CustomValidators.mobileValidator]],
    email: ['', [Validators.email]],
    dob: [''],
    healthId: ['', [Validators.required, CustomValidators.abhaIdValidators]],
  });

  isSwitcheChecked = false;
  private profilePhotoBase64;
  private selectedXlsxFile: File;
  uploadedFiles = [];
  private trigger: Subject<void> = new Subject<void>();
  uploadedImage = 'assets/img/photo.png';
  private selectedFile: string;
  selectedFileName: string = '';

  @ViewChild('inputFile') xlsxInputFIle: ElementRef;

  selectedRadio: string;

  radioButtonLList = [
    {
      name: 'Use ABHA',
      value: 'abha',
    },
    {
      name: 'Single',
      value: 'single',
    },
    {
      name: 'File Upload',
      value: 'multiple',
    },
  ];

  ngOnInit(): void {
    this.aadharValidationForm = this.fb.group({
      // adhaarNumber: ['',[Validators.required, CustomValidators.aadhaarValidator]],
      otp: ['', [Validators.required, CustomValidators.otpValidator]],
    });

    this.otpValidationForm = this.fb.group({
      adhaarOtp: ['', [Validators.required, CustomValidators.otpValidator]],
    });
    this.getNdhmFlag();
  }

  onClickSwitch() {
    this.isSwitcheChecked = !this.isSwitcheChecked;
    this.uploadedImage = this.isSwitcheChecked ? 'assets/img/take-photo.svg' : 'assets/img/photo.png';
  }

  triggerSnapshot(): void {
    this.isSwitcheChecked = !this.isSwitcheChecked;
    this.trigger.next();
  }

  handleImage(webcamImage: WebcamImage): void {
    this.profilePhotoBase64 = webcamImage['_imageAsDataUrl'];
    this.uploadedImage = this.profilePhotoBase64;
  }

  handleInitError(error: WebcamInitError): void {
    if (error.mediaStreamError && error.mediaStreamError.name === 'NotAllowedError') {
      this.toastrMessage.showWarningMsg('Camera access was not allowed by user!', 'Information');
    }
  }

  get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
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
      }
      document.getElementById('ProfilePhoto').setAttribute('src', window.URL.createObjectURL(event.target.files[0]));
    } else {
      if (!hasImg) {
        this.toastrMessage.showWarningMsg(ErrorSuccessMessage.IMGFORMATE, 'Warning');
        event.target.files = '';
      }
      if (!hasSize) {
        this.toastrMessage.showWarningMsg(ErrorSuccessMessage.IMGSIZE, 'Warning');
        event.target.files = '';
      }
    }
  }

  /**
   * Used for upload profile photo
   */
  browse() {
    document.getElementById('browse').click();
  }

  selectFiles(event?: any) {
    if (event) {
      const fileName = event.target?.files[0]?.name;
      if (fileName === 'sample.xlsx') {
        this.selectedXlsxFile = event.target.files[0];
        this.selectedFileName = fileName;
        const reader = new FileReader();
        reader.onload = (e) => {
          let file = JSON.stringify(e.target.result);
          this.selectedFile = file;
        };
        reader.readAsDataURL(event.target.files[0]);
      } else {
        this.xlsxInputFIle.nativeElement.value = '';
        this.selectedFileName = '';
        this.selectedXlsxFile = undefined;
        this.toastrMessage.showErrorMsg(ErrorSuccessMessage.GIVENXLSXFILE, 'Error');
      }
    }
  }

  uploadFiles() {
    if (this.selectedXlsxFile) {
      this.uploadedFiles = [];
      this.uploadedFiles.push(this.selectedXlsxFile);
      this.xlsxInputFIle.nativeElement.value = '';
      this.selectedXlsxFile = undefined;
      this.selectedFileName = '';
    } else {
      this.xlsxInputFIle.nativeElement.value = '';
      this.toastrMessage.showWarningMsg(ErrorSuccessMessage.XLFILEFORMATE, 'Warning');
    }
  }

  removeFile(index: number) {
    this.uploadedFiles.splice(index, 1);
    this.selectedFileName = '';
  }

  download() {
    const file = `${environment.baseUrl}/assets/files/sample.xlsx`;
    window.open(file);
  }

  convertAndDownloadFile(fileData: string, fileName: string) {
    //const file = new Blob([fileData], { type: 'application/vnd.ms-excel' });
    const file = this.documentService.b64toBlob(fileData, 'application/vnd.ms-excel');
    const fileURL = URL.createObjectURL(file);
    const fileLink = document.createElement('a');
    fileLink.href = fileURL;
    fileLink.download = fileName;
    fileLink.click();
    //window.open(fileURL);
  }

  onClickSubmit() {
    this.spinner.show();
    let { patientName, mobileNo, email, dob } = this.form.value;
    if (this.selectedRadio === 'single') {
      if (patientName && mobileNo) {
        let payload = {
          patientName: patientName ? patientName : null,
          doctorUserID: sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null,
          ptMobileNo: mobileNo ? mobileNo : null,
          ptProfilePhoto: this.profilePhotoBase64 ? this.profilePhotoBase64 : null,
          ptEmailID: email ? email : null,
          ptDateOfBirth: dob ? dob : null,
        };
        this.patientService.patientRegistartionByScribeOrDoctor(payload).subscribe(
          (result) => {
            this.spinner.hide();
            if (result.status) {
              this.router.navigate(['appointments']);
              this.toastrMessage.showSuccessMsg(result.response.message, 'Success');
            } else {
              if (result.errors) {
                this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
              }
            }
          },
          (error) => {
            this.spinner.hide();
            this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
          },
        );
      } else {
        this.spinner.hide();
        this.toastrMessage.showWarningMsg('Please select all required fields', 'Warning');
      }
    } else {
      if (this.uploadedFiles?.length) {
        const payload = {
          doctorUserID: sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null,
          excelFileOfBulkPatientDtls: this.selectedFile ? this.selectedFile : null,
          fileName: this.uploadedFiles[0]?.name,
        };
        this.patientService.patientRegistrationByDocumentUpload(payload).subscribe(
          (result) => {
            this.spinner.hide();
            if (result.status) {
              this.convertAndDownloadFile(result.response.fileData, result.response.fileName);
              this.router.navigate(['appointments']);
              this.toastrMessage.showSuccessMsg(result.response.message, 'Success');
            } else {
              if (result.errors) {
                this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
              }
            }
          },
          (error) => {
            this.spinner.hide();
            this.toastrMessage.showErrorMsg(error.error?.errors[0]?.message, 'Error');
          },
        );
      } else {
        this.spinner.hide();
        this.toastrMessage.showWarningMsg('Please select file', 'Warning');
      }
    }
  }

  patientData(user) {
    console.log(user);
    this.showPatientVerificationPage = false;
    this.isAadharCardIsValid = false;
    this.showTimer = false;
    this.showTimerForOtp = false;
    this.showResendButton = false;
    this.form.get('healthId').setValue('');
    this.router.navigate(['appointments']);
  }

  reset() {
    this.uploadedFiles = [];
    this.form.reset();
    this.form.markAsPristine();
    document.getElementById('ProfilePhoto').setAttribute('src', this.uploadedImage);
  }

  onClickCancel() {
    this.router.navigate(['appointments']);
  }

  private getNdhmFlag() {
    this.patientService.getNdhmFlag().subscribe({
      next: (res) => {
        this.ndhmFlag = res.ndhmFlag;
        if (this.ndhmFlag) {
          this.selectedRadio = 'abha';
        } else {
          this.selectedRadio = 'single';
        }
      },
      error: (err) => {
        console.log('enter in error');
      },
    });
  }

  openAbhaPopup() {
    this.showMsg = false;
    this.spinner.show();
    this.drId = sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null;
    const abhaId = this.form.get('healthId').value;
    this.patientService.searchPatient(abhaId).subscribe(
      (res) => {
        //  if (res.status ) {
        if (res.status) {
          console.log('>>>>:', res.response);
          const ptDateOfBirth = '1973-01-01'; // res.ptDateOfBirth
          const pdate = new Date(ptDateOfBirth);
          const year = pdate.getFullYear();
          this.ptRegId = res.response.userId;
        }
        // let { abhaAddress, name, mobileNo, abhaNo } = res.response;
        const abhaAddress = res.response ? res.response.abhaAddress : undefined;
        const name = res.response ? res.response.name : undefined;
        const mobileNo = res.response ? res.response.mobileNo : undefined;
        const abhaNo = res.response ? res.response.abhaNo : undefined;

        if (abhaId === abhaAddress) {
          this.doctorService.checkDoctorTagByPatientId(this.drId, this.ptRegId).subscribe(
            (tagDetail) => {
              const data = {
                request: {
                  patientName: name,
                  ptMobileNo: mobileNo,
                  doctorUserID: this.drId,
                  ptDateOfBirth: '',
                  healthNo: abhaNo,
                  healthId: abhaId,
                },
              };
              if (tagDetail?.response) {
                this.spinner.hide();
                this.showMsg = true;
              } else {
                this.patientService.patientRegistartionByScribeOrDoctorWithHealthId(data).subscribe(
                  (ele) => {
                    this.spinner.hide();
                    this.toastrMessage.showSuccessMsg(ele['response']['message'], 'Success');
                  },
                  (error) => {
                    this.spinner.hide();
                    console.log(error);
                  },
                );
              }
            },
            (error) => {
              console.log(error);
            },
          );
        } else {
          let data = {
            request: {
              healthId: abhaId,
            },
          };
          this.patientService.searchByHealthId(data).subscribe(
            (loginData: any) => {
              console.log(loginData);
              this.spinner.hide();
              if (loginData?.status) {
                this.abhaDialogRef = this.modelService.open(this.abhaRef, {
                  size: 'md',
                  backdrop: 'static',
                  centered: true,
                });
              } else {
                this.toastrMessage.showErrorMsg(loginData?.errors[0]?.message, 'Error');
              }
            },
            (error) => {
              this.spinner.hide();
              console.log(error);
            },
          );
        }
        // } else {
        //   this.spinner.hide();
        //   this.toastrMessage.showErrorMsg('Invalid HealthId', 'Error');
        // }
      },
      (error) => {
        this.spinner.hide();
      },
    );
  }

  private sendOtp(adhaarNumber: any) {
    this.otp = '';
    this.adhaarOtp = '';
    console.log('this.timeLeft =====>', this.timeLeft);
    this.spinner.show();
    console.log('adhaarNumber ====>', adhaarNumber);
    console.log('this.isChecked2 ====>', this.isChecked2);
    console.log('this.isChecked ===>', this.isChecked);
    if (this.isChecked2 === 'Renewal') {
      let AdharData = {
        authMethod: 'MOBILE_OTP',
        healthid: this.form.get('healthId').value, //
      };
      this.patientService.authInit(AdharData).subscribe(
        (res: any) => {
          this.timeLeft = 60;
          console.log('response', res.status);
          this.spinner.hide();
          this.startTimer();
          this.showOtpForNumber = false;
          if (res?.status) {
            this.txnId = res?.response?.txnId;
          } else {
            this.toastrMessage.showErrorMsg(res?.errors[0]?.message, 'Error');
            this.closePopup();
            this.spinner.hide();
          }
        },
        (error: any) => {
          this.spinner.hide();
          console.log(error);
        },
      );
    } else {
      if (this.isChecked === 'New' || adhaarNumber) {
        sessionStorage.setItem('adhaarCard', adhaarNumber);
        let AdharData = {
          authMethod: 'AADHAAR_OTP',
          healthid: this.form.get('healthId').value,
        };
        this.patientService.authInit(AdharData).subscribe(
          (res: any) => {
            this.timeLeft = 60;
            this.isAadharCardIsValid = true;
            this.startTimer();
            this.spinner.hide();
            console.log('response', res);
            if (res?.status) {
              this.txnId = res?.response?.txnId;
            } else {
              this.toastrMessage.showErrorMsg(res?.errors[0]?.message, 'Error');
              this.closePopup();
              this.spinner.hide();
            }
          },
          (error: any) => {
            this.spinner.hide();
            console.log(error);
          },
        );
      }
    }
  }

  startTimer() {
    if (this.isChecked === 'New') {
      this.showTimer = true;
      this.interval = setInterval(() => {
        if (this.timeLeft > 0) {
          this.timeLeft--;
        } else {
          this.clearInterval();
          this.showResendButton = true;
        }
      }, 1000);
    } else {
      if (this.isChecked2 === 'Renewal') {
        this.showTimerForOtp = true;
        this.interval = setInterval(() => {
          if (this.timeLeft > 0) {
            this.timeLeft--;
          } else {
            this.clearInterval();
            this.showOtpForNumber = true;
          }
        }, 1000);
      }
    }
  }

  clearInterval() {
    clearInterval(this.interval);
  }

  submitMobileOtp() {
    this.spinner.show();
    let Data = {
      request: {
        otp: this.adhaarOtp,
        txnId: this.txnId,
      },
    };
    this.patientService.confirmMobileOtp(Data).subscribe(
      (ele: any) => {
        this.spinner.hide();
        console.log('ele', ele);
        if (ele?.status) {
          this.getAccountProfile(ele);
        } else {
          this.toastrMessage.showErrorMsg(ele?.errors[0]?.message, 'Error');
        }
      },
      (error: any) => {
        this.spinner.hide();
        console.log(error);
      },
    );
  }

  closePopup() {
    this.adhaarNumber = '';
    this.otp = '';
    this.adhaarOtp = '';
    this.isAadharCardIsValid = false;
    this.showTimer = false;
    this.showTimerForOtp = false;
    this.selectedLink = '';
    this.isChecked = '';
    this.isChecked2 = '';
    this.abhaDialogRef.close();
  }

  setradio(e: string): void {
    this.isChecked = '';
    this.isChecked2 = '';
    this.adhaarNumber = '';
    this.otp = '';
    this.adhaarOtp = '';
    if (!this.adhaarNumber) {
      this.showTimer = false;
    }
    if (!this.otp) {
      this.showOtpForNumber = false;
    }
    this.timeLeft = 60;
    this.selectedLink = e;
    if (this.selectedLink === 'New') {
      this.isChecked = 'New';
      this.sendOtp(this.selectedLink);
    } else {
      this.isChecked2 = 'Renewal';
      this.sendOtp(this.selectedLink);
    }
  }

  isSelected(name: string): boolean {
    if (!this.selectedLink) {
      // if no radio button is selected, always return false so every nothing is shown
      return false;
    }
    return this.selectedLink === name; // if current radio button is selected, return true, else return false
  }

  submitAdharOtp() {
    this.spinner.show();
    let Data = {
      request: {
        otp: this.otp,
        txnId: this.txnId,
      },
    };
    this.patientService.confirmAadhaarOtp(Data).subscribe(
      (ele: any) => {
        this.spinner.hide();
        console.log('ele confirmAadhaarOtp ===>', ele);
        if (ele?.status) {
          this.getAccountProfile(ele);
        } else {
          this.toastrMessage.showErrorMsg(ele?.errors[0]?.message, 'Error');
        }
      },
      (error: any) => {
        this.spinner.hide();
        console.log(error);
      },
    );
  }

  getAccountProfile(ele) {
    this.spinner.show();
    let requestData = {
      request: {
        xToken: ele?.response?.token,
      },
    };
    this.patientService.getAccountProfile(requestData).subscribe(
      (data: any) => {
        this.spinner.hide();
        console.log('data getAccountProfile====>', data);
        if (data?.status) {
          this.accountProfileData = data?.response;
          this.getPatientDetailsByMobNoForDoc(this.accountProfileData?.mobile);
        }
      },
      (error: any) => {
        this.spinner.hide();
        console.log(error);
      },
    );
  }

  getPatientDetailsByMobNoForDoc(mobile) {
    this.spinner.show();
    let requestData = {
      request: {
        ptFullName: mobile,
      },
    };
    this.patientService.getPatientDetailsByMobNoForDocVerification(requestData).subscribe(
      (data: any) => {
        this.spinner.hide();
        console.log('data getPatientDetailsFromMobileNo====>', data);
        if (!data?.status) {
          this.isUpdate = false;
          this.showPatientVerificationPage = true;
          this.closePopup();
        } else {
          this.isUpdate = true;
          this.checkDoctorTagByPatientId(data?.response[0]?.ptUserId);
        }
      },
      (error: any) => {
        this.spinner.hide();
        console.log(error);
      },
    );
  }

  checkDoctorTagByPatientId(ptUserId) {
    this.spinner.show();
    this.drId = sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : null;
    let data = {
      request: {
        drRegId: this.drId,
        ptRegId: this.ptRegId ? this.ptRegId : ptUserId ? ptUserId : '',
      },
    };
    this.patientService.checkDoctorTagByPatientId(data).subscribe(
      (ele: any) => {
        this.spinner.hide();
        console.log('ele', ele);
        this.isUpdate = false;
        if (ele?.status) {
          // this.showMsg = true;
          this.isUpdate = true;
          this.showPatientVerificationPage = true;
          this.closePopup();
        } else {
          this.isUpdate = true;
          this.showPatientVerificationPage = true;
          this.closePopup();
        }
      },
      (error: any) => {
        this.spinner.hide();
        console.log(error);
      },
    );
  }
}
