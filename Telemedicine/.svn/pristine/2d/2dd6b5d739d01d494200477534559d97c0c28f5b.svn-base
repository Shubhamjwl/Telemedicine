import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DistrictDTO } from 'src/app/models/common/DistrictDTO';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { SearchByHealthIdDTO } from 'src/app/models/common/SearchByHealthIdDTO';
import { StateDtlsDTO } from 'src/app/models/common/StateDtlsDTO';
import { UserAuthRespDTO } from 'src/app/models/common/UserAuthRespDTO';
import { ConfirmOtpRespAadhaar } from 'src/app/models/healthidAadhaar/ConfirmOtpRespAadhaar';
import { CreateHealthIdPreverifiedRequestDTO } from 'src/app/models/healthidAadhaar/CreateHealthIdPreverifiedRequestDTO';
import { CreateHealthIdPreverifiedRespDTO } from 'src/app/models/healthidAadhaar/CreateHealthIdPreverifiedRespDTO';
import { HealthIDAadhaarDTO } from 'src/app/models/healthidAadhaar/HealthIDAadhaarDTO';
import { GenerateOtpResp } from 'src/app/models/healthidMobile/GenerateOtpResp';
import { HealthCardDTO } from 'src/app/models/healthidMobile/HealthCardDTO';
import { HealthIDDTO } from 'src/app/models/healthidMobile/HealthIDDTO';
import { HealthIDResp } from 'src/app/models/healthidMobile/HealthIDResp';
import { ResendOtpResp } from 'src/app/models/healthidMobile/ResendOtpResp';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-aadhar-mobile-regester',
  templateUrl: './aadhar-mobile-regester.component.html',
  styleUrls: ['./aadhar-mobile-regester.component.css'],
})
export class AadharMobileRegesterComponent implements OnInit {
  timeLeft: number = 60;
  interval;
  startTimer() {
    this.interval = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
      }
      // else {
      //   this.timeLeft = 60;
      // }
    }, 1000);
  }
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  token: any;
  txnId: any;
  otp: any;
  mobileNoSel: any;
  aadhaar: any;
  firstName: any;
  middleName: any;
  lastName: any;
  dateOfBirth: any;
  gender: any;
  email: any;
  address: any;
  pinCode: any;
  stateCode: any;
  districtCode: any;
  subDistrict: any;
  town: any;
  ward: any;
  village: any;
  photoFile: any;
  photoFileString: any;
  dayOfBirth: any;
  monthOfBirth: any;
  yearOfBirth: any;
  healthId: any;
  healthId1: any;
  healthId2: any;
  healthIdNumber: any;
  password: any;
  confirmPassword: any;
  name: any;
  restrictions: any;
  refreshToken: any;
  accessToken: any;
  userAuthToken: any;
  isShownRegistration: boolean = true;
  isShownAfterRegistration: boolean = false;
  isShowPopup: boolean = false;
  isShowPopup2: boolean = false;
  isChecked: boolean = false;
  isChecked2: boolean = true;
  healthIdFlag: boolean = false;
  isDisabledFlag: boolean = true;
  responseToken: MainResponseDTO<GenerateSessionRespDTO>;
  mainGenerateOtpResp: MainResponseDTO<GenerateOtpResp>;
  healthIDResp: MainResponseDTO<HealthIDResp>;
  statesMainDTO: MainResponseDTO<StateDtlsDTO[]>;
  healthCardDTO: MainResponseDTO<HealthCardDTO>;
  userAuthRespDTO: MainResponseDTO<UserAuthRespDTO>;
  states: StateDtlsDTO[];
  districtsMainDTO: MainResponseDTO<DistrictDTO[]>;
  districts: DistrictDTO[];
  healthIDDTO = new HealthIDDTO();
  searchByHealthIdDTO = new SearchByHealthIdDTO();
  healthIDAadhaarDTO = new HealthIDAadhaarDTO();
  mainConfirmOtpRespAadhaar: MainResponseDTO<ConfirmOtpRespAadhaar>;
  createHealthIdPreverifiedRequestDTO = new CreateHealthIdPreverifiedRequestDTO();
  mainCreateHealthIdPreverifiedRespDTO: MainResponseDTO<
    CreateHealthIdPreverifiedRespDTO
  >;
  hidePassword: boolean = true;
  hideReEnterPassword: boolean = true;
  showPopupButtonFlag: boolean = false;
  displayStyle = 'none';
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService,
    private _location: Location
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      //console.log(params);
      if (params && params.aadhaar) {
        this.aadhaar = params.aadhaar;
        //console.log('this.aadhaar::' + this.aadhaar);
      }
      if (params && params.mobileNoSel) {
        this.mobileNoSel = params.mobileNoSel;
        //console.log('this.mobileNoSel::' + this.mobileNoSel);
      }
      if (params && params.txnId) {
        this.txnId = params.txnId;
        //console.log('this.txnId::' + this.txnId);
      }
    });
    this.generateToken();
    this.startTimer();
  }
  public imagePath: any;
  imgURL: any;
  public message: string | undefined;

  preview(files: any) {
    if (files.length === 0) return;

    var mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = 'Only images are supported.';
      return;
    }

    var reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.imgURL = reader.result;
    };
  }
  next() {
    this.isShownRegistration = false;
    this.isShownAfterRegistration = false;
    this.isShowPopup = true;
    this.isShowPopup2 = false;
    this.resendOTP();
  }
  generateToken() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseToken = result;
        this.refreshToken = this.responseToken.response.refreshToken;
        this.accessToken = this.responseToken.response.accessToken;
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.accessToken
        ) {
          this.getStates(this.accessToken);
        }
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg(
          'Unable to generate accessToken',
          'Error'
        );
      }
    );
  }

  resendOTP() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseToken = result;
        this.refreshToken = this.responseToken.response.refreshToken;
        this.accessToken = this.responseToken.response.accessToken;
        this.timeLeft = 60;
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.accessToken
        ) {
          this.generateAaadhaarMobileOTP(this.accessToken);
        }
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Something whent wrong', 'Error');
      }
    );
    this.isShownRegistration = false;
    this.isShownAfterRegistration = false;
    this.isShowPopup = true;
    this.isShowPopup2 = false;
  }
  generateAaadhaarMobileOTP(accessToken) {
    this._servises
      .generateAaadhaarMobileOTP(this.mobileNoSel, this.txnId, accessToken)
      .subscribe(
        (result) => {
          this.mainGenerateOtpResp = result;
          this.txnId = result.response.txnId;
          //console.log('txnId', result.response.txnId);
        },
        (error) => {
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg('Something whent wrong', 'Error');
        }
      );
  }
  verifyAaadhaarMobileOTP(accessToken) {
    this._servises
      .verifyAaadhaarMobileOTP(this.otp, this.txnId, accessToken)
      .subscribe(
        (result) => {
          this.mainConfirmOtpRespAadhaar = result;
          this.txnId = result.response.txnId;
          //console.log('txnId::', this.txnId);
          this.helathIDcreationForPreVerified(this.accessToken);
        },
        (error) => {
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg(
            'Unable to verify OTP for Aadhaar',
            'Error'
          );
        }
      );
  }
  getStates(accessToken) {
    this._servises.getStates(accessToken).subscribe(
      (result) => {
        this.statesMainDTO = result;
        this.states = result.response;
        //console.log('states:', this.states);
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Something whent wrong', 'Error');
      }
    );
  }
  getDistricts() {
    if (this.stateCode == '') {
      this.districts = [];
    }
    for (let index = 0; index < this.states.length; index++) {
      const element = this.states[index].code;
      if (this.stateCode == element) {
        this.districts = this.states[index].districts;
      }
    }
  }
  dateOfBirthSplit() {
    var dateOfBirth = this.dateOfBirth;
    if (dateOfBirth) {
      var splitted = dateOfBirth.split('/', 3);
      this.dayOfBirth = splitted[0];
      this.monthOfBirth = splitted[1];
      if (this.monthOfBirth === '10') {
      } else {
        this.monthOfBirth = this.monthOfBirth.replace('0', '');
      }
      this.yearOfBirth = splitted[2];
      //console.log(splitted);
      //console.log('this.dayOfBirth', this.dayOfBirth);
      //console.log('this.monthOfBirth', this.monthOfBirth);
      //console.log('this.yearOfBirth', this.yearOfBirth);
    }
  }
  checkHealthId() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseToken = result;
        this.refreshToken = this.responseToken.response.refreshToken;
        this.accessToken = this.responseToken.response.accessToken;
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.accessToken
        ) {
          this.searchHealthId(this.accessToken);
        }
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg(
          'Unable to generate refresh token',
          'Error'
        );
      }
    );
  }
  searchHealthId(accessToken) {
    this.searchByHealthIdDTO = new SearchByHealthIdDTO();
    this.searchByHealthIdDTO.healthId = this.healthId;
    this._servises
      .searchExistsByHealthId(accessToken, this.searchByHealthIdDTO)
      .subscribe(
        (result) => {
          this.healthIdFlag = result.response.status;
          //console.log('healthIdFlag::', this.healthIdFlag);
          if (this.healthIdFlag) {
            this.toastrMessage.showWarningMsg(
              'Health id already exist,please choose different one',
              'Warning'
            );
          }
        },
        (error) => {
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg('Unable to Search healthId', 'Error');
        }
      );
  }
  submit() {
    this.verifyAaadhaarMobileOTP(this.accessToken);
  }
  helathidCreationForAadhaar(accessToken) {
    this.healthIDAadhaarDTO = new HealthIDAadhaarDTO();
    this.healthIDAadhaarDTO.email = this.email;
    this.healthIDAadhaarDTO.firstName = this.firstName;
    this.healthIDAadhaarDTO.middleName = this.middleName;
    this.healthIDAadhaarDTO.lastName = this.lastName;
    this.healthIDAadhaarDTO.firstName = this.firstName;
    this.healthIDAadhaarDTO.mobile = this.mobileNoSel;
    this.healthIDAadhaarDTO.otp = this.otp;
    this.healthIDAadhaarDTO.password = this.password;
    this.healthIDAadhaarDTO.profilePhoto = this.photoFile;
    this.healthIDAadhaarDTO.restrictions = this.restrictions;
    this.healthIDAadhaarDTO.txnId = this.txnId;
    this.healthIDAadhaarDTO.username = this.name;
    this._servises
      .helathidCreationForAadhaar(accessToken, this.healthIDAadhaarDTO)
      .subscribe(
        (result) => {
          this.healthIDResp = result;
          this.healthIdNumber = result.response.healthIdNumber;
          this.isShownRegistration = true;
          this.isShowPopup = false;
          this.isShownAfterRegistration = true;
        },
        (error) => {
          this.isShownRegistration = true;
          this.isShowPopup = false;
          this.isShownAfterRegistration = true;
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg(
            'Unable to create Health id for Aadhaar',
            'Error'
          );
        }
      );
  }
  helathIDcreationForPreVerified(accessToken) {
    this.createHealthIdPreverifiedRequestDTO = new CreateHealthIdPreverifiedRequestDTO();
    this.createHealthIdPreverifiedRequestDTO.email = this.email;
    this.createHealthIdPreverifiedRequestDTO.firstName = this.firstName;
    this.createHealthIdPreverifiedRequestDTO.healthId = this.healthId;
    this.createHealthIdPreverifiedRequestDTO.lastName = this.lastName;
    this.createHealthIdPreverifiedRequestDTO.middleName = this.middleName;
    this.createHealthIdPreverifiedRequestDTO.password = this.password;
    this.createHealthIdPreverifiedRequestDTO.txnId = this.txnId;
    this._servises
      .helathIDcreationForPreVerified(
        accessToken,
        this.createHealthIdPreverifiedRequestDTO
      )
      .subscribe(
        (result) => {
          this.mainCreateHealthIdPreverifiedRespDTO = result;
          console.log(
            'result_Adhar_mobile_register::',
            result.response.healthIdNumber
          );
          this.healthIdNumber = result.response.healthIdNumber;
          //console.log('healthIdNumber::', this.healthIdNumber);
          this.healthId = this.mainCreateHealthIdPreverifiedRespDTO.response.healthId;
          this.healthId = this.mainCreateHealthIdPreverifiedRespDTO.response.healthId.substring(
            0,
            this.mainCreateHealthIdPreverifiedRespDTO.response.healthId.length -
              4
          );
          this.firstName = this.mainCreateHealthIdPreverifiedRespDTO.response.firstName;
          this.middleName = this.mainCreateHealthIdPreverifiedRespDTO.response.middleName;
          this.lastName = this.mainCreateHealthIdPreverifiedRespDTO.response.lastName;
          this.email = this.mainCreateHealthIdPreverifiedRespDTO.response.email;
          this.gender = this.mainCreateHealthIdPreverifiedRespDTO.response.gender;
          this.stateCode = this.mainCreateHealthIdPreverifiedRespDTO.response.stateCode;
          this.photoFileString =
            'data:image/jpg;base64,' +
            this.mainCreateHealthIdPreverifiedRespDTO.response.profilePhoto;
          this.getDistricts();
          this.districtCode = this.mainCreateHealthIdPreverifiedRespDTO.response.districtCode;
          this.email = this.mainCreateHealthIdPreverifiedRespDTO.response.email;
          this.dateOfBirth =
            this.mainCreateHealthIdPreverifiedRespDTO.response.dayOfBirth +
            '/' +
            this.mainCreateHealthIdPreverifiedRespDTO.response.monthOfBirth +
            '/' +
            this.mainCreateHealthIdPreverifiedRespDTO.response.yearOfBirth;
          this.isShownRegistration = true;
          this.isShowPopup = false;
          this.isShownAfterRegistration = true;
        },
        (error) => {
          this.isShownRegistration = true;
          this.isShowPopup = false;
          this.isShownAfterRegistration = false;
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg(
            'Unable to create Health id for Aadhaar',
            'Error'
          );
        }
      );
  }
  downloadHealthIdCard() {
    this._servises
      .userAuthWithPassword(
        this.healthIdNumber,
        this.password,
        this.accessToken
      )
      .subscribe(
        (result) => {
          this.userAuthRespDTO = result;
          this.userAuthToken = result.response.token;
          console.log('this.userAuthToken::', this.userAuthToken);
          this.downloadPDFCard(this.userAuthToken);
        },
        (error) => {
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg(
            'Unable to generate user Auth Token',
            'Error'
          );
        }
      );
  }

  downloadPDFCard(userAuthToken) {
    this._servises.getCardInPdf(this.accessToken, userAuthToken).subscribe(
      (result) => {
        this.isShownRegistration = true;
        this.isShowPopup = false;
        this.isShownAfterRegistration = true;
        this.isShowPopup2 = false;

        let file = new Blob([result], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(file);
        link.download = 'Healthid.pdf';
        link.click();
        window.URL.revokeObjectURL(link.href);
      },
      (error) => {
        this.isShownRegistration = true;
        this.isShowPopup = false;
        this.isShownAfterRegistration = true;
        this.isShowPopup2 = false;
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Something whent wrong', 'Error');
      }
    );
  }
  downloadHealthIdCardQR() {
    this._servises
      .userAuthWithPassword(
        this.healthIdNumber,
        this.password,
        this.accessToken
      )
      .subscribe(
        (result) => {
          this.userAuthRespDTO = result;
          this.userAuthToken = result.response.token;
          //console.log('this.userAuthToken::', this.userAuthToken);
          this.downloadHealthidQR(this.userAuthToken);
        },
        (error) => {
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg(
            'Unable to generate user Auth Token',
            'Error'
          );
        }
      );
  }
  downloadHealthidQR(userAuthToken) {
    this._servises.getCardInPng(this.accessToken, userAuthToken).subscribe(
      (result) => {
        this.isShownRegistration = true;
        this.isShowPopup = false;
        this.isShownAfterRegistration = true;
        this.isShowPopup2 = false;

        let file = new Blob([result], { type: 'application/png' });
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);

        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(file);
        link.download = 'Healthid.png';
        link.click();
        window.URL.revokeObjectURL(link.href);
      },
      (error) => {
        this.isShownRegistration = true;
        this.isShowPopup = false;
        this.isShownAfterRegistration = true;
        this.isShowPopup2 = false;
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Something whent wrong', 'Error');
      }
    );
  }

  nextPopup() {
    this.isShownRegistration = false;
    this.isShowPopup = false;
    this.isShownAfterRegistration = false;
    this.isShowPopup2 = true;
  }
  back() {
    //this._location.back();
    window.history.back();
  }
  backPopup() {
    this.isShownRegistration = true;
    this.isShownAfterRegistration = false;
    this.isShowPopup = false;
    this.isShowPopup2 = false;
    // this.resendOTP();
    window.history.back();
  }
  showPopup() {
    this.showPopupButtonFlag = true;
    this.toastrMessage.showSuccessMsg(
      'Health ID created and mapped to Profile',
      'Success'
    );
  }
  openPopup() {
    this.displayStyle = 'block';
  }
  closePopup() {
    this.displayStyle = 'none';
  }
  clearFields() {
    // this.mobileNoSel = '';
    this.healthId = '';
    this.password = '';
    this.confirmPassword = '';
  }
}
