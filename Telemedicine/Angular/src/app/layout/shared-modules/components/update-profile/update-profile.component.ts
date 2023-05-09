import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { WebcamImage, WebcamInitError } from 'ngx-webcam';
import { Observable, Subject } from 'rxjs';
import { debounceTime, skip } from 'rxjs/operators';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import { DatePipe } from '@angular/common';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { DoctorService } from 'src/app/shared/commonBuildBlocks/services/doctorServices/doctor.service';
import { ScribeService } from 'src/app/shared/commonBuildBlocks/services/scribeServices/scribe.service';
import { PatientService } from 'src/app/shared/commonBuildBlocks/services/patientServices/patient.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { CheckerService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/checker.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { MasterTypes } from 'src/app/shared/commonBuildBlocks/enum/master-type.enum';
import { CustomValidators } from 'src/app/shared/commonBuildBlocks/services/Validators/customValidator.service';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { environment } from 'src/environments/environment';
import { ClipboardService } from 'ngx-clipboard';
import { DdateAdapter, MY_FORMATS } from 'src/app/shared/commonBuildBlocks/derectives/formatDatepickers';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { IAssociation } from 'src/app/shared/commonBuildBlocks/interfaces/association.interface';
import { FeaturesCategoryEnum, UpdateCategoryRequest } from 'src/app/shared/commonBuildBlocks';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: DdateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ],
})
export class UpdateProfileComponent implements OnInit {
  @ViewChild('abhaRef') abhaRef: TemplateRef<any>;
  @ViewChild('updateProfRef') updateProfRef: TemplateRef<any>;
  isDisabledFlag: boolean = true;
  form: FormGroup;
  ndhmFlag: boolean;
  hasDoctorWebsiteLink: boolean;
  hasPreAssessmentLink: boolean;
  hasAppointmentBookLink: boolean;
  /**
   * Gender Drop Down
   */

  genderList = [];

  /**
   * Blood Groups Drop Down
   */
  bloodGroupTypes: any = [
    {
      key: 'A+',
      value: 'A+',
    },
    {
      key: 'A-',
      value: 'A-',
    },
    {
      key: 'B+',
      value: 'B+',
    },
    {
      key: 'B-',
      value: 'B-',
    },
    {
      key: 'O+',
      value: 'O+',
    },
    {
      key: 'O-',
      value: 'O-',
    },
    {
      key: 'AB+',
      value: 'AB+',
    },
    {
      key: 'AB-',
      value: 'AB-',
    },
  ];

  /**
   * used to show progress bar for file upload
   */
  progress: number = 90;

  profilePhotoBase64: any;
  isSwitcheChecked = false;
  uploadedImage = 'assets/images/img_avatar.png';
  public webcamImage: WebcamImage = null;
  private trigger: Subject<void> = new Subject<void>();

  /**
   * Used for upload files
   */
  uploadedFileNames: any = [];
  uploadedFile: any;

  /**
   * Used to store specialization List
   */
  specializationList = [];

  /**
   * Used to store states List
   */
  states = [];

  /**
   * Used to store cities List
   */
  cities = [];

  /**
   * Used to store document type */
  documentTypes = [];

  /**
   * Used to check logged in user role
   */
  userRole = sessionStorage.getItem('ROLE');

  /**
   * Used to set maximum date to date of birth
   */
  maxDate = new Date();

  disableSelect = new FormControl(false);

  /**
   * Used to enable / disable date of birth, blood group, height, weight
   */
  disableDob: boolean = false;
  disableBloodGroup: boolean = false;
  disableHeight: boolean = false;
  disableWeight: boolean = false;

  alreadyUploadedFiles: any = [];
  associationList: IAssociation[];
  abhaId: any;

  public controlDetailsForExistenceCheck = [
    {
      controlName: 'email',
      type: 'email',
    },
    {
      controlName: 'mobileNo',
      type: 'mobile',
    },
    // {
    //   controlName: 'userName',
    //   type: 'userId'
    // },
  ];

  displayStyle = 'none';
  abhaNumber: any;
  showMsg: boolean = false;
  drId: string;
  ptRegId: any;
  private abhaDialogRef: NgbModalRef;
  private updateDailogRef: NgbModalRef;
  selectedLink: string;
  isChecked;
  isChecked2: string;
  showOtpForNumber: boolean = false;
  isAadharCardIsValid: boolean = false;
  txnId: any;
  timeLeft: number = 60;
  interval;
  showTimer: boolean = false;
  showResendButton: boolean = false;
  showTimerForOtp: boolean = false;
  otp: any;
  adhaarOtp: any;
  adhaarNumber: string;
  accountProfileData: any;
  isUpdate: boolean = false;
  showPatientVerificationPage: boolean = false;
  fullNameOfPatient: any;
  firstName: any;
  middleName: any;
  lastName: any;
  selectedValue: any;
  aadharValidationForm: FormGroup;
  otpValidationForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private toastrMessage: ToastMessageService,
    private doctorServiceInstance: DoctorService,
    private scribeServiceInstance: ScribeService,
    private patientServiceInstance: PatientService,
    private consultationService: ConsultationService,
    private modalService: NgbModal,
    private router: Router,
    private routes: ActivatedRoute,
    private datePipe: DatePipe,
    public authService: AuthService,
    private checkerService: CheckerService,
    private documentService: DocumentService,
    public dataPassingService: DataPassingService,
    private _clipboardService: ClipboardService,
    private spinner: NgxSpinnerService,
    private patientService: PatientService,
  ) {}

  ngOnInit(): void {
    this.formValidation();
    this.getDoctorSpecialization();
    this.getGenderList();
    this.getState();
    // this.getDocumentTypes();
    this.getProfileData();
    this.duplicateCheck();
    this.getAssociationNameList();
    this.getNdhmFlag();
    this.uploadedFileNames = [];
    this.alreadyUploadedFiles = [];
    if (this.userRole === 'DOCTOR') {
       this.fetchCategoryDtlsByDrId();
    }

  }

  /**
   * On change of mobile number and email call existing API call
   */
  duplicateCheck() {
    this.controlDetailsForExistenceCheck.forEach((details) => {
      this.checkForEmailExistance(details.controlName, details.type);
    });
  }

  getGenderList() {
    this.consultationService.getMasterDetailsListByMasterName(MasterTypes.GENDER).subscribe(
      (result) => {
        if (result.status && result.response) {
          this.genderList = result.response;
        } else if (result.errors) {
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        // console.log(error["errors"]);
        this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
      },
    );
  }

  getAssociationNameList() {
    this.consultationService.getAssociationNameList().subscribe(
      (result) => {
        if (result.status && result.response) {
          this.associationList = result.response;
        } else if (result.errors) {
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error.message, 'Error');
      },
    );
  }

 
  /**
   * Used to check uplicate mobile number and email
   * @param controlName
   * @param type
   */
  checkForEmailExistance(controlName, type) {
    this.form
      .get(controlName)
      .statusChanges.pipe(skip(1), debounceTime(2000))
      .subscribe((status) => {
        if (status === 'VALID') {
          const data = {
            request: { [type]: this.form.get(controlName).value },
          };
          this.consultationService.checkDuplicate(data).subscribe(
            (result) => {
              if (result['response']) {
                if (!result['response'].isavailable) {
                  this.form.get(controlName).setValue('');
                  this.toastrMessage.showErrorMsg(result['response'].message, 'Error');
                } else if (result['errors']) {
                  this.toastrMessage.showErrorMsg(result['errors'][0].message, 'Error');
                }
              }
            },
            (error) => {
              this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
            },
          );
        }
      });
  }

  formValidation() {
    this.aadharValidationForm = this.fb.group({
      // adhaarNumber: ['',[Validators.required, CustomValidators.aadhaarValidator]],
      otp: ['', [Validators.required, CustomValidators.otpValidator]],
    });

    this.otpValidationForm =  this.fb.group({
      adhaarOtp: ['', [Validators.required, CustomValidators.otpValidator]],
    });
    this.form = this.fb.group({
      profilePhoto: [''],
      fullName: ['', [CustomValidators.fullNameValidator(30)]],
      mobileNo: ['', [CustomValidators.mobileValidator]],
      email: ['', [Validators.email, Validators.maxLength(250)]],
      gender: [''],
      dob: [''],
      bloodGroup: [''],
      height: [''],
      weight: [''],
      state: [''],
      city: [''],
      smcNo: [''],
      micNo: [''],
      consultationFee: [''],
      speciality: [''],
      address1: [''],
      address2: [''],
      address3: [''],
      healthId: [''],
      healthIdNumber: [''],
      link: [''],
      ptlink: [''],
      drProfileLink: [''],
      pre_assessment_link: [''],
      pre_assessment_flag: [false],
      associationName: [''],
      associationNumber: [{ value: '', disabled: true }, Validators.required],
    });

    var ptLink = this.form.get('ptlink').value;
    console.log(ptLink, 'pt link');
    if (this.userRole === 'DOCTOR') {
      this.form.get('smcNo').setValidators([Validators.required]);
      this.form.get('micNo').setValidators([Validators.required]);
      this.form.get('consultationFee').setValidators([Validators.required]);
      this.form.get('speciality').setValidators([Validators.required]);
      this.form.get('email').disable();
      this.form.updateValueAndValidity();
      // validation added in associationNo
      this.form.get('associationName').valueChanges.subscribe((res) => {
        const associationNo = this.form.get('associationNumber');
        if (res && res !== 'Not selected') {
          associationNo.enable();
        } else {
          associationNo.setValue('');
          associationNo.disable();
        }
      });
    } else if (this.userRole === 'SCRIBE') {
    } else if (this.userRole === 'PATIENT') {
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

  onClickSwitch() {
    this.isSwitcheChecked = !this.isSwitcheChecked;
  }

  public handleInitError(error: WebcamInitError): void {
    if (error.mediaStreamError && error.mediaStreamError.name === 'NotAllowedError') {
      this.toastrMessage.showWarningMsg('Camera access was not allowed by user!', 'Information');
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

  filesArray = [];
  /**
   * Used to show Selected Files
   */
  showSelectedFiles(event) {
    let hasImg = event.target.files[0].type.includes('image') ? true : false;
    let hasSize = event.target.files[0].size <= 1000000 ? true : false; //1000000
    if (hasImg && hasSize) {
      const files = event.target.files;
      const file = files[0];
      if (files && file) {
        for (let file of event.target.files) {
          this.uploadedFile = event.target.files[0];
          this.uploadedFileNames.push(file);
          const reader = new FileReader();
          reader.onload = (e) => {
            this.filesArray.push({ files: e.target.result });
          };
          reader.readAsDataURL(event.target.files[0]);
        }
      }
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
   * Used for Upload Documents
   */
  browseFile() {
    document.getElementById('browseFile').click();
  }

  /**
   * Used to get State
   */
  getState() {
    this.consultationService.getStateList().subscribe(
      (result) => {
        if (result['status'] && result['response']) {
          this.states = result['response'];
        } else if (result['errors']) {
          this.toastrMessage.showErrorMsg(result['errors'][0].message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
      },
    );
  }

  onSelectState(value) {
    if (value) {
      this.getCity(value);
    }
  }

  /**
   * Used to get State
   */
  getCity(value, city?) {
    this.consultationService.getCityList(value).subscribe(
      (result) => {
        if (result['status'] && result['response']) {
          this.cities = result['response'];
          if (city) {
            this.form.get('city').setValue(city ? city.trim() : null);
          }
        } else if (result['errors']) {
          this.toastrMessage.showErrorMsg(result['errors'][0].message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
      },
    );
  }

  /**
   * Used to get specialization list
   */
  getDoctorSpecialization() {
    if (this.dataPassingService.specializationList && this.dataPassingService.specializationList.length) {
      return;
    }

    this.consultationService.getMasterDetailsListByMasterName(MasterTypes.SPECIALIZATION).subscribe(
      (result) => {
        if (result.status && result.response) {
          this.dataPassingService.specializationList = result.response;
        } else if (result.errors) {
          this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
      },
    );
  }

  /**
   * Used to get documentType
   */
  // getDocumentTypes() {
  //   this.consultationService
  //     .getMasterDetailsListByMasterName(MasterTypes.DOCUMENTTYPE)
  //     .subscribe(
  //       (result) => {
  //         if (result.status && result.response) {
  //           this.documentTypes = result.response;
  //         } else if (result.errors) {
  //           this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
  //         }
  //       },
  //       (error) => {
  //         this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
  //       }
  //     );
  // }

  /**
   * Delete selected files
   */
  // removeFiles(index) {
  //   this.uploadedFileNames.splice(index, 1);
  // }

  /**
   * Used to save updated details of profile
   */
  updateProfileDetails() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    } else {
      if (this.userRole === 'DOCTOR') {
        let request = {
          api: 'update doctor profile',
          id: '1',
          version: 'v1.0',
          requesttime: '2020-12-01T05:48:03.125Z',
          request: {
            dmdUserId: sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : '',
            dmdDrName: this.form.get('fullName').value ? this.form.get('fullName').value : '',
            dmdMobileNo: this.form.get('mobileNo').value ? this.form.get('mobileNo').value : '',
            dmdEmail: this.form.get('email').value ? this.form.get('email').value : '',
            dmdSmcNumber: this.form.get('smcNo').value ? this.form.get('smcNo').value : '',
            dmdMciNumber: this.form.get('micNo').value ? this.form.get('micNo').value : '',
            dmdConsulFee: this.form.get('consultationFee').value ? this.form.get('consultationFee').value : '',
            dmdSpecialiazation: this.form.get('speciality').value ? this.form.get('speciality').value : '',
            profilePhoto: this.profilePhotoBase64 ? this.profilePhotoBase64 : '',
            // "dmdHeight": this.form.get('height').value ? this.form.get('height').value : '',
            // "dmdWeight": this.form.get('weight').value ? this.form.get('weight').value : '',
            // "dmdBloodgrp": this.form.get('bloodGroup').value ? this.form.get('bloodGroup').value : '',
            // "dmdDob": this.form.get('dob').value ? moment(this.form.get('dob').value).format('YYYY-MM-DD') : '',
            dmdAddress1: this.form.get('address1').value ? this.form.get('address1').value : '',
            dmdAddress2: this.form.get('address2').value ? this.form.get('address2').value : '',
            dmdAddress3: this.form.get('address3').value ? this.form.get('address3').value : '',
            dmdGender: this.form.get('gender').value ? this.form.get('gender').value : '',
            dmdCity: this.form.get('city').value ? this.form.get('city').value : '',
            dmdState: this.form.get('state').value ? this.form.get('state').value : '',
            dmdPreassessmentLink: this.form.get('pre_assessment_link').value
              ? this.form.get('pre_assessment_link').value
              : '',
            dmdPreassessmentFlag: this.form.get('pre_assessment_flag').value
              ? this.form.get('pre_assessment_flag').value
              : false,
            dmdAssociationName: this.form.get('associationName').value || '',
            dmdAssociationNumber: this.form.get('associationNumber').value || '',
            ptCountry: '',
          },
          mimeType: 'application/json',
        };

        this.updateDoctor(request);
      } else if (this.userRole === 'SCRIBE') {
        let request = {
          id: 'registration',
          version: '1.0',
          requestTime: '2020-11-06T11:49:28.688Z',
          request: {
            scrbFullName: this.form.get('fullName').value ? this.form.get('fullName').value : '',
            scrbMobNo: this.form.get('mobileNo').value ? this.form.get('mobileNo').value : '',
            scrbEmail: this.form.get('email').value ? this.form.get('email').value : '',
            scrbUserID: sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : '',
            isDefaultScribe: '',
            scrbAdd1: this.form.get('address1').value ? this.form.get('address1').value : '',
            scrbAdd2: this.form.get('address2').value ? this.form.get('address2').value : '',
            scrbAdd3: this.form.get('address3').value ? this.form.get('address3').value : '',

            scribeProfilePhoto: this.profilePhotoBase64 ? this.profilePhotoBase64 : '',
            scrbGender: this.form.get('gender').value ? this.form.get('gender').value : '',
            scrbCity: this.form.get('city').value ? this.form.get('city').value : '',
            scrbState: this.form.get('state').value ? this.form.get('state').value : '',
          },
        };

        this.updateScribe(request);
      } else if (this.userRole === 'PATIENT') {
        let request = {
          id: 'modification',
          version: '1.0',
          requestTime: '2020-10-28T10:35:48.620Z',
          method: 'post',
          request: {
            ptUserID: sessionStorage.getItem('USER_ID') ? sessionStorage.getItem('USER_ID') : '',
            ptFullName: this.form.get('fullName').value ? this.form.get('fullName').value : '',
            ptMobNo: this.form.get('mobileNo').value ? this.form.get('mobileNo').value : '',
            ptEmail: this.form.get('email').value ? this.form.get('email').value : '',
            height: this.form.get('height').value ? this.form.get('height').value : '',
            weight: this.form.get('weight').value ? this.form.get('weight').value : '',
            bloodgrp: this.form.get('bloodGroup').value ? this.form.get('bloodGroup').value : '',
            dob: this.form.get('dob').value ? moment(this.form.get('dob').value).format('YYYY-MM-DD') : '',
            ptProfilePhoto: this.profilePhotoBase64 ? this.profilePhotoBase64 : '',
            address: {
              address1: this.form.get('address1').value ? this.form.get('address1').value : '',
              address2: this.form.get('address2').value ? this.form.get('address2').value : '',
              address3: this.form.get('address3').value ? this.form.get('address3').value : '',
            },
            ptGender: this.form.get('gender').value ? this.form.get('gender').value : '',
            ptCity: this.form.get('city').value ? this.form.get('city').value : '',
            ptState: this.form.get('state').value ? this.form.get('state').value : '',
            healthID: this.form.get('healthId').value ? this.form.get('healthId').value : '',
            healthNumber: this.form.get('healthIdNumber').value ? this.form.get('healthIdNumber').value : '',
            // "ptCountry" : ''
          },
        };

        this.updatePatient(request, sessionStorage.getItem('USER_ID'));
      }
    }
  }

  updateDoctor(payload) {
    this.doctorServiceInstance.updateDoctorProfile(payload).subscribe(
      (data) => {
        if (data.response != null) {
          this.toastrMessage.showSuccessMsg(data.response, 'Success');
          this.router.navigate(['../appointments'], {
            relativeTo: this.routes,
          });
        } else {
          if (data.errors != null) {
            let errorMsg = ' ';
            if (data.errors.length > 0) {
              for (let msg of data.errors) {
                errorMsg = errorMsg + ' ' + msg.message;
              }
              this.toastrMessage.showErrorMsg(errorMsg, 'Error');
            } else {
              if (data.errors.message) {
                this.toastrMessage.showErrorMsg(data.errors.message, 'Error');
              } else {
                this.toastrMessage.showErrorMsg(ErrorSuccessMessage.INVALIDAPIRESPONSE, 'Error');
              }
            }
          }
        }
      },
      (error) => {
        this.technicalBackendErrorMsg();
      },
    );
  }

  updateScribe(payload) {
    this.scribeServiceInstance.updateScribeProfile(payload).subscribe(
      (data) => {
        if (data.response != null) {
          if (data.response != null) {
            this.toastrMessage.showSuccessMsg(data.response, 'Success');
            this.router.navigate(['../appointments'], {
              relativeTo: this.routes,
            });
          } else {
            this.toastrMessage.showErrorMsg(ErrorSuccessMessage.INVALIDAPIRESPONSE, 'Error');
          }
        } else {
          if (data.errors != null) {
            let errorMsg = ' ';
            if (data.errors.length > 0) {
              for (let msg of data.errors) {
                errorMsg = errorMsg + ' ' + msg.message;
              }
              this.toastrMessage.showErrorMsg(errorMsg, 'Error');
            } else {
              if (data.errors.message) {
                this.toastrMessage.showErrorMsg(data.errors.message, 'Error');
              } else {
                this.toastrMessage.showErrorMsg(ErrorSuccessMessage.INVALIDAPIRESPONSE, 'Error');
              }
            }
          }
        }
      },
      (error) => {
        this.technicalBackendErrorMsg();
      },
    );
  }

  updatePatient(payload, userId) {
    this.patientServiceInstance.updatePatientProfile(payload).subscribe(
      (data) => {
        if (data.response != null) {
          if (data.response.message != null) {
            this.toastrMessage.showSuccessMsg(data.response.message, 'Success');
            this.router.navigate(['../patient-dashboard'], {
              relativeTo: this.routes,
            });
          } else {
            this.toastrMessage.showErrorMsg(ErrorSuccessMessage.INVALIDAPIRESPONSE, 'Error');
          }
        } else {
          if (data.errors != null) {
            let errorMsg = ' ';
            if (data.errors.length > 0) {
              for (let msg of data.errors) {
                errorMsg = errorMsg + ' ' + msg.message;
              }
              this.toastrMessage.showErrorMsg(errorMsg, 'Error');
            } else {
              if (data.errors.message) {
                this.toastrMessage.showErrorMsg(data.errors.message, 'Error');
              } else {
                this.toastrMessage.showErrorMsg(ErrorSuccessMessage.INVALIDAPIRESPONSE, 'Error');
              }
            }
          }
        }
      },
      (error) => {
        this.technicalBackendErrorMsg();
      },
    );
  }

  /**
   * Used to set from validation
   */
  setValidationMannually(controlName) {
    this.form.get(controlName).setValidators([Validators.required]);
    this.form.get(controlName).updateValueAndValidity();
  }

  /**
   * Used to go back to dashboard page
   */
  cancelUpdateProfile() {
    if (this.userRole === 'PATIENT') {
      this.router.navigate(['../patient-dashboard'], {
        relativeTo: this.routes,
      });
    } else if (this.userRole === 'DOCTOR' || this.userRole === 'SCRIBE') {
      this.router.navigate(['../appointments'], { relativeTo: this.routes });
    } else {
    }
  }

  /**
   * Used to get profile details based on logged in user role
   */
  getProfileData() {
    if (this.userRole === 'DOCTOR') {
      this.disableDob = true;
      this.disableBloodGroup = true;
      this.disableHeight = true;
      this.disableWeight = true;
      this.getDoctorData();
    } else if (this.userRole === 'SCRIBE') {
      this.disableDob = true;
      this.disableBloodGroup = true;
      this.disableHeight = true;
      this.disableWeight = true;

      this.getScribeData();
    } else if (this.userRole === 'PATIENT') {
      this.getPatientData();
    } else {
      this.disableDob = false;
      this.disableBloodGroup = false;
      this.disableHeight = false;
      this.disableWeight = false;
    }
  }

   /**
   * used to fecth category details
   */
    private fetchCategoryDtlsByDrId() {
      this.doctorServiceInstance.fetchCategoryDtlsByDrId().subscribe((result) => {
        this.checkDoctorFlag(result);
      });
    }
  
    private checkDoctorFlag(res: UpdateCategoryRequest) {
      const categories = res.category;
      if (categories?.length) {
        categories.forEach((c) => {
          if (c.categoryName === FeaturesCategoryEnum.DOCTOR_WEBSITE_LINK) {
            this.hasDoctorWebsiteLink = c.categoryFlag;
          } else if (c.categoryName === FeaturesCategoryEnum.PRE_ASSESSMENT_LINK) {
            this.hasPreAssessmentLink = c.categoryFlag;
          } else if (c.categoryName === FeaturesCategoryEnum.APPOINTMENT_BOOK_LINK) {
            this.hasAppointmentBookLink = c.categoryFlag;
          }
        });
      }
    }
  

  getDoctorData() {
    this.doctorServiceInstance.getDoctorProfile(sessionStorage.getItem('USER_ID')).subscribe(
      (data) => {
        this.form.get('fullName').setValue(data.response.dmdDrName);
        this.form.get('mobileNo').setValue(data.response.dmdMobileNo);
        this.form.get('email').setValue(data.response.dmdEmail);
        this.form.get('gender').setValue(data.response.dmdGender ? data.response.dmdGender.trim() : '');
        this.form.get('smcNo').setValue(data.response.dmdSmcNumber);
        this.form.get('micNo').setValue(data.response.dmdMciNumber);
        this.form.get('consultationFee').setValue(data.response.dmdConsulFee);
        localStorage.setItem('city', data.response.dmdCity);
        localStorage.setItem('address', data.response.dmdAddress1);

        this.uploadedImage = data.response.profilePhoto ? data.response.profilePhoto : 'assets/images/img_avatar.png';
        this.form.get('address1').setValue(data.response.dmdAddress1 ? data.response.dmdAddress1.trim() : '');
        this.form.get('address2').setValue(data.response.dmdAddress2 ? data.response.dmdAddress2.trim() : '');
        this.form.get('address3').setValue(data.response.dmdAddress3 ? data.response.dmdAddress3.trim() : '');
        let docSpeciality = this.capitalize(data.response.dmdSpecialiazation.toString().trim());
        console.log(docSpeciality, 'speciality');
        this.form.get('speciality').setValue(docSpeciality);
        this.form.get('state').setValue(data.response.dmdState ? data.response.dmdState.trim() : '');
        this.form.get('link').setValue(data.response.dmdDrLink ? data.response.dmdDrLink : '');
        this.form
          .get('ptlink')
          .setValue(data.response.dmdPatientRegistrationLink ? data.response.dmdPatientRegistrationLink : '');
        this.form.get('drProfileLink').setValue(data.response.dmdDrProfileLink ? data.response.dmdDrProfileLink : '');
        this.form
          .get('pre_assessment_link')
          .setValue(data.response.dmdPreassessmentLink ? data.response.dmdPreassessmentLink : '');
        this.form
          .get('pre_assessment_flag')
          .setValue(data.response.dmdPreassessmentFlag ? data.response.dmdPreassessmentFlag : false);

        this.form.get('associationName').setValue(data?.response?.dmdAssociationName);
        this.form.get('associationNumber').setValue(data?.response?.dmdAssociationNumber);

        let city = data.response.dmdCity ? data.response.dmdCity.trim() : '';
        if (this.form.get('state').value) {
          this.getCity(this.form.get('state').value, city);
        }
        for (let file of data.response.drDocsDtls) {
          this.alreadyUploadedFiles.push(file);
        }
        // this.form.get('documentType').setValue(data.response.drDocsDtls[0].ddtDocsType);
      },
      (error) => {},
    );
  }
  capitalize(docSpeciality: string) {
    if (docSpeciality.includes(' ')) {
      let words = docSpeciality.toLowerCase().split(' ');
      let CapitalizedWords = [];
      words.forEach((element) => {
        CapitalizedWords.push(element[0].toUpperCase() + element.slice(1, element.length));
      });
      return CapitalizedWords.join(' ');
    } else {
      return docSpeciality[0].toUpperCase() + docSpeciality.slice(1, docSpeciality.length).toLowerCase();
    }
  }
  getPatientData() {
    this.patientServiceInstance.getPatientDetails(sessionStorage.getItem('USER_ID')).subscribe(
      (data) => {
        if (data.response != null) {
          let profilePhotoServerPath;
          this.form.get('fullName').setValue(data.response.ptFullName);
          this.fullNameOfPatient = this.form.get('fullName').value;
          const [first, middle, last] =  this.fullNameOfPatient.split(' ');
          this.firstName = first;
          this.middleName = middle;
          this.lastName = last;
          this.form.get('mobileNo').setValue(data.response.ptMobNo);
          this.form.get('email').setValue(data.response.ptEmail);
          this.form.get('healthId').setValue(data.response.healthID);
          this.form.get('healthIdNumber').setValue(data.response.healthNumber);
          profilePhotoServerPath = data.response.ptProfilePhoto ? data.response.ptProfilePhoto : null;
          this.uploadedImage = profilePhotoServerPath
            ? profilePhotoServerPath.replace('/var/telemedicine/', `${environment['baseUrl']}`)
            : this.uploadedImage;
          this.form.get('bloodGroup').setValue(data.response.bloodGroup);
          this.form.get('height').setValue(data.response.height);
          this.form.get('weight').setValue(data.response.weight);
          this.form.get('gender').setValue(data.response.gender ? data.response.gender.trim() : '');
          this.form.get('state').setValue(data.response.ptState ? data.response.ptState.trim() : '');
          let city = data.response.ptCity ? data.response.ptCity.trim() : '';
          if (this.form.get('state').value) {
            this.getCity(this.form.get('state').value, city);
          }
          this.form.get('address1').setValue(data.response.address1 ? data.response.address1.trim() : '');
          this.form.get('address2').setValue(data.response.address2 ? data.response.address2.trim() : '');
          this.form.get('address3').setValue(data.response.address3 ? data.response.address3.trim() : '');
          this.form
            .get('dob')
            .setValue(data.response.dob ? new Date(this.datePipe.transform(data.response.dob, 'yyyy-MM-dd')) : '');
          console.log(data.response.dob);
          console.log(new Date(this.datePipe.transform(data.response.dob, 'yyyy-MM-dd')));
          this.form.get('gender').setValue(data.response.gender ? data.response.gender.trim() : '');
        } else {
        }
      },
      (error) => {},
    );
  }

  getScribeData() {
    this.scribeServiceInstance.getScribeProfileDetails().subscribe(
      (data) => {
        if (data.response != null) {
          let profilePhotoServerPath;
          this.form.get('fullName').setValue(data.response.scrbFullName);
          this.form.get('mobileNo').setValue(data.response.scrbMobNo);
          this.form.get('email').setValue(data.response.scrbEmail);

          this.form.get('address1').setValue(data.response.scrbAdd1 ? data.response.scrbAdd1.trim() : '');
          this.form.get('address2').setValue(data.response.scrbAdd2 ? data.response.scrbAdd2.trim() : '');
          this.form.get('address3').setValue(data.response.scrbAdd3 ? data.response.scrbAdd3.trim() : '');
          this.form.get('state').setValue(data.response.scrbState ? data.response.scrbState.trim() : '');
          let city = data.response.scrbCity ? data.response.scrbCity.trim() : '';
          if (this.form.get('state').value) {
            this.getCity(this.form.get('state').value, city);
          }
          this.form.get('gender').setValue(data.response.scrbGender ? data.response.scrbGender.trim() : '');
          profilePhotoServerPath = data.response.scribeProfilePhoto ? data.response.scribeProfilePhoto : null;
          this.uploadedImage = profilePhotoServerPath
            ? profilePhotoServerPath.replace('/var/telemedicine/', `${environment['baseUrl']}`)
            : 'assets/images/img_avatar.png';
        }
      },
      (error) => {
        this.technicalBackendErrorMsg();
      },
    );
  }

  download(obj) {
    let data = {
      api: 'downoad document',
      request: {
        ddtDocsPath: obj.ddtDocsPath,
        ddtDocsType: null,
        drdUserId: this.authService.getUserId(),
      },
      mimeType: 'application/json',
    };

    this.checkerService.downloadDoc(data).subscribe((resp: any) => {
      if (resp) {
        if (resp.status) {
          const fileName = obj.ddtDocsPath.slice(obj.ddtDocsPath.lastIndexOf('/') + 1, obj.ddtDocsPath.length);
          this.documentService.downloadFileToBrowser(resp.response, resp.mimeType, fileName);
        } else {
          this.toastrMessage.showErrorMsg(
            resp.errors[0].message ? resp.errors[0].message : 'Internal Server Error',
            'Error',
          );
        }
      }
    });
  }

  /**
   * Used to convert string into title case
   */
  convertStringToTitleCase(value: string) {
    return value.charAt(0).toUpperCase() + value.slice(1);
  }

  technicalBackendErrorMsg() {
    this.toastrMessage.showErrorMsg(ErrorSuccessMessage.CODEFAILUIR, 'Error');
  }

  /* To copy Text from Text */
  clickToAppointmentReg() {
    var Link = this.form.get('link').value;
    console.log(Link);
    this._clipboardService.copyFromContent(Link);
    document.getElementById('custom-tooltip').style.display = 'inline';
    document.execCommand('copy');
    setTimeout(function () {
      document.getElementById('custom-tooltip').style.display = 'none';
    }, 1000);
  }

  clickToPatientReg() {
    var linkpt = this.form.get('ptlink').value;
    console.log(linkpt);
    this._clipboardService.copyFromContent(linkpt);
    document.getElementById('custom-tooltip1').style.display = 'inline';
    document.execCommand('copy');
    setTimeout(function () {
      document.getElementById('custom-tooltip1').style.display = 'none';
    }, 1000);
  }

  clickToDrProfile() {
    const linkpt = this.form.get('drProfileLink').value;
    this._clipboardService.copyFromContent(linkpt || 'Link Not Available');
    document.getElementById('custom-tooltip1').style.display = 'inline';
    document.execCommand('copy');
    setTimeout(function () {
      document.getElementById('custom-tooltip1').style.display = 'none';
    }, 1000);
  }

  /* To copy Text from Textbox */
  copyInputMessage(inputElement) {
    inputElement.select();
    document.execCommand('copy');
    inputElement.setSelectionRange(0, 0);
  }

  getNdhmFlag() {
    this.patientServiceInstance.getNdhmFlag().subscribe({
      next: (res) => {
        this.ndhmFlag = res.ndhmFlag;
      },
      error: (err) => {
        console.log('enter in error');
      },
    });
  }

  setradio(e: string): void {
    this.isChecked = '';
    this.isChecked2 = '';
    this.adhaarNumber = '';
    this.otp = '';
    this.adhaarOtp = '';
    if(!this.adhaarNumber) {
      this.showTimer = false;
    }
    if (!this.otp) {
      this.showOtpForNumber =  false;
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

  openPopup() {
    this.showMsg = false;
    this.spinner.show();
    // if(this.abhaNumber === this.form.get('healthId').value || this.form.get('healthId').value === '' || this.form.get('healthId').value === null) {
      let data = {
        request: {
          healthId: this.abhaNumber,
        }
      };
      this.patientServiceInstance.searchByHealthId(data).subscribe((patient: any) => {
        console.log('searchHealthIdToLogin ====>', patient);
        this.spinner.hide();
        if(patient?.status) {
          this.abhaDialogRef = this.modalService.open(this.abhaRef, {
            size: 'md',
            backdrop: 'static',
            centered: true
          });
        } else {
          this.toastrMessage.showErrorMsg(patient?.errors[0]?.message, 'Error');
        }
      },(error) => {
        this.spinner.hide();
        console.log(error);
      }
      );
    // } else {
    //   // this.spinner.hide();
    //   // this.toastrMessage.showErrorMsg('Invalid ABHA/ABHA Address', 'Error');
    // }
  }

  private sendOtp(adhaarNumber: any) {
    this.timeLeft = 60;
    this.spinner.show();
    console.log('adhaarNumber ===>', adhaarNumber);
    if (this.isChecked2 === 'Renewal') {
      let AdharData = {
        authMethod: 'MOBILE_OTP',
        healthid: this.abhaNumber //
      };
      this.patientService.authInit(AdharData).subscribe(
        (res: any) => {
          console.log('response', res.status);
          this.spinner.hide();
          this.startTimer();
          this.showOtpForNumber = false;
          if (res?.status) {
            this.txnId = res?.response?.txnId;
          } else {
            this.toastrMessage.showErrorMsg(res?.errors[0]?.message, 'Error');
            this.closePopupVerify();
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
          healthid: this.abhaNumber,
        };
        this.patientService.authInit(AdharData).subscribe(
          (res: any) => {
            this.isAadharCardIsValid = true;
            this.startTimer();
            this.spinner.hide();
            console.log('response', res);
            if (res?.status) {
              this.txnId = res?.response?.txnId;
            } else {
              this.toastrMessage.showErrorMsg(res?.errors[0]?.message, 'Error');
              this.closePopupVerify();
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
          this.showResendButton =  true;
        }
      }, 1000);
    } else {
      if(this.isChecked2 === 'Renewal') {
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
          this.updateDailogRef = this.modalService.open(this.updateProfRef, {
            size: 'lg',
            backdrop: 'static',
            centered: true
          });
          this.closePopupVerify();
        }
      },
      (error: any) => {
        this.spinner.hide();
        console.log(error);
      },
    );
  }

  closePopup() {
    this.updateDailogRef.close();
  }

  closePopupVerify() {
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

  onChange(e) {
    console.log(e.target.value);
    this.selectedValue = e.target.value;
  }

  UpdateAbdmDetails(accountProfileData) {
    this.spinner.show();
    if(this.selectedValue === 'abdm') {
      console.log(accountProfileData);
      let data = {
        request: {
          lastName: accountProfileData.lastName,
          middleName: accountProfileData.middleName,
          gender: accountProfileData.gender === 'F' ? 'Female' : accountProfileData.gender === 'M' ? 'Male' :  accountProfileData.gender === 'T' ? 'Transgender' : '',
          firstName: accountProfileData.firstName,
          address: accountProfileData?.address,
          mobileNo: this.form.get('mobileNo').value,
          healthNumber: accountProfileData?.healthIdNumber ? accountProfileData?.healthIdNumber : '',
          healthId: accountProfileData?.healthId,
        }
      }
      this.patientService.updateAbhaDetails(data).subscribe((ele: any) => {
          this.spinner.hide();
          console.log("ele", ele);
          if(ele?.status) {
            this.toastrMessage.showSuccessMsg(ele['response']['message'], 'Success');
            this.closePopup();
          } else {
            this.toastrMessage.showErrorMsg(ele?.errors[0]?.message, 'Error');
            this.closePopup();
          }
      },
      (error: any) => {
          this.spinner.hide();
          console.log(error);
      })
    } else {
      this.spinner.hide();
      this.closePopup();
    }
  }
}