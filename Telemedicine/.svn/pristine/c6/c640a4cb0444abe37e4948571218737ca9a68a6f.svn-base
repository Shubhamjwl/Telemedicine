import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DistrictDTO } from 'src/app/models/common/DistrictDTO';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { StateDtlsDTO } from 'src/app/models/common/StateDtlsDTO';
import { UserAuthRespDTO } from 'src/app/models/common/UserAuthRespDTO';
import { HealthCardDTO } from 'src/app/models/healthidMobile/HealthCardDTO';
import { HealthIDDTO } from 'src/app/models/healthidMobile/HealthIDDTO';
import { HealthIDResp } from 'src/app/models/healthidMobile/HealthIDResp';
import { ResendOtpResp } from 'src/app/models/healthidMobile/ResendOtpResp';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';

@Component({
  selector: 'app-assidte-mobile-registration',
  templateUrl: './assidte-mobile-registration.component.html',
  styleUrls: ['./assidte-mobile-registration.component.scss'],
})
export class AssidteMobileRegistrationComponent implements OnInit {
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
  userAuthToken: any;
  isShownRegistration: boolean = true;
  isShownAfterRegistration: boolean = false;
  isShowPopup: boolean = false;
  isShowPopup2: boolean = false;
  isChecked: boolean = true;
  responseToken: MainResponseDTO<GenerateSessionRespDTO>;
  resendOtpResp: MainResponseDTO<ResendOtpResp>;
  healthIDResp: MainResponseDTO<HealthIDResp>;
  statesMainDTO: MainResponseDTO<StateDtlsDTO[]>;
  healthCardDTO: MainResponseDTO<HealthCardDTO>;
  userAuthRespDTO: MainResponseDTO<UserAuthRespDTO>;
  states: StateDtlsDTO[];
  districtsMainDTO: MainResponseDTO<DistrictDTO[]>;
  districts: DistrictDTO[];
  healthIDDTO = new HealthIDDTO();
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      console.log(params);
      if (params && params.token) {
        this.token = params.token;
        console.log('this.token::' + this.token);
      }
      if (params && params.mobileNoSel) {
        this.mobileNoSel = params.mobileNoSel;
        console.log('this.mobileNoSel::' + this.mobileNoSel);
      }
      if (params && params.txnId) {
        this.txnId = params.txnId;
        console.log('this.txnId::' + this.txnId);
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
        console.log('this.refreshToken::', this.refreshToken);
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.refreshToken
        ) {
          this.getStates(this.refreshToken);
        }
      },
      (error) => {
        console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg(
          'Unable to generate refreshToken',
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
        console.log('this.refreshToken::', this.refreshToken);
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.refreshToken
        ) {
          this.resendOTPForMobile(this.refreshToken);
        }
      },
      (error) => {
        console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Something whent wrong', 'Error');
      }
    );
    this.isShownRegistration = false;
    this.isShownAfterRegistration = false;
    this.isShowPopup = true;
    this.isShowPopup2 = false;
  }
  resendOTPForMobile(refreshToken) {
    console.log('this.mobileNoSel', this.mobileNoSel);
    console.log('this.txnId', this.txnId);
    console.log('this.refreshToken', refreshToken);
    this._servises.resendOTPForMobile(this.txnId, refreshToken).subscribe(
      (result) => {
        this.resendOtpResp = result;
        console.log('txnId', result.response.txnId);
      },
      (error) => {
        console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Something whent wrong', 'Error');
      }
    );
  }
  getStates(refreshToken) {
    console.log('this.refreshToken::', refreshToken);
    this._servises.getStates(refreshToken).subscribe(
      (result) => {
        this.statesMainDTO = result;
        this.states = result.response;
        console.log('states:', this.states);
      },
      (error) => {
        console.log(error.message, 'Error');
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
      this.yearOfBirth = splitted[2];
      console.log(splitted);
      console.log('this.dayOfBirth', this.dayOfBirth);
      console.log('this.monthOfBirth', this.monthOfBirth);
      console.log('this.yearOfBirth', this.yearOfBirth);
    }
  }
  submit() {
    this.healthIDDTO = new HealthIDDTO();
    this.healthIDDTO.address = this.address;
    this.healthIDDTO.dayOfBirth = this.dayOfBirth;
    this.healthIDDTO.districtCode = this.districtCode;
    this.healthIDDTO.email = this.email;
    this.healthIDDTO.firstName = this.firstName;
    this.healthIDDTO.gender = this.gender;
    this.healthIDDTO.healthId = this.healthId + '@sbx';
    this.healthIDDTO.lastName = this.lastName;
    this.healthIDDTO.middleName = this.middleName;
    this.healthIDDTO.monthOfBirth = this.monthOfBirth;
    this.healthIDDTO.name = this.name;
    this.healthIDDTO.password = this.password;
    this.healthIDDTO.pincode = this.pinCode;
    this.healthIDDTO.profilePhoto = this.photoFile;
    this.healthIDDTO.restrictions = this.restrictions;
    this.healthIDDTO.stateCode = this.stateCode;
    this.healthIDDTO.subdistrictCode = this.subDistrict;
    this.healthIDDTO.token = this.token;
    this.healthIDDTO.townCode = this.town;
    this.healthIDDTO.txnId = this.txnId;
    this.healthIDDTO.villageCode = this.village;
    this.healthIDDTO.wardCode = this.ward;
    this.healthIDDTO.yearOfBirth = this.yearOfBirth;
    this.healthIDDTO.mobileNo = this.mobileNoSel;

    this._servises
      .helathidCreationForMobile(this.refreshToken, this.healthIDDTO)
      .subscribe(
        (result: any) => {
          this.healthIDResp = result;
          this.healthIdNumber = result.response.healthIdNumber;
          this.isShownRegistration = false;
          this.isShowPopup = false;
          this.isShownAfterRegistration = true;
          this.isShowPopup2 = false;
        },
        (error) => {
          this.isShownRegistration = true;
          this.isShowPopup = false;
          this.isShownAfterRegistration = false;
          this.isShowPopup2 = false;
          console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg('Something whent wrong', 'Error');
        }
      );
  }
  downloadHealthIdCard() {
    this.userAuthWithPassword();
    console.log('this.userAuthToken::', this.userAuthToken);
    this._servises
      .getCardInPdf(this.refreshToken, this.userAuthToken)
      .subscribe(
        (result) => {
          this.isShownRegistration = false;
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
          this.isShownRegistration = false;
          this.isShowPopup = false;
          this.isShownAfterRegistration = true;
          this.isShowPopup2 = false;
          console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg('Something whent wrong', 'Error');
        }
      );
  }
  userAuthWithPassword() {
    this._servises
      .userAuthWithPassword(this.healthId, this.password, this.refreshToken)
      .subscribe(
        (result) => {
          this.userAuthRespDTO = result;
          this.userAuthToken = result.response.token;
          console.log('this.userAuthToken::', this.userAuthToken);
        },
        (error) => {
          console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg('Something whent wrong', 'Error');
        }
      );
  }

  downloadHealthIdCardQR() {
    this.userAuthWithPassword();
    console.log('this.userAuthToken::', this.userAuthToken);
    this._servises
      .getCardInPng(this.refreshToken, this.userAuthToken)
      .subscribe(
        (result) => {
          this.isShownRegistration = false;
          this.isShowPopup = false;
          this.isShownAfterRegistration = true;
          this.isShowPopup2 = false;

          let file = new Blob([result], { type: 'application/png' });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL);

          var link = document.createElement('a');
          link.href = window.URL.createObjectURL(file);
          link.download = 'Healthid.pdf';
          link.click();
          window.URL.revokeObjectURL(link.href);
        },
        (error) => {
          this.isShownRegistration = false;
          this.isShowPopup = false;
          this.isShownAfterRegistration = true;
          this.isShowPopup2 = false;
          console.log(error.message, 'Error');
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
    this.router.navigate(['/']);
  }
  backPopup() {
    this.isShownRegistration = true;
    this.isShownAfterRegistration = false;
    this.isShowPopup = false;
    this.isShowPopup2 = false;
  }
}
