import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { ConfirmOtpResp } from 'src/app/models/healthidMobile/ConfirmOtpResp';
import { ResendOtpResp } from 'src/app/models/healthidMobile/ResendOtpResp';
import { CredentialDTO } from 'src/app/models/verifyHealthid/CredentialDTO';
import { DemographicDTO } from 'src/app/models/verifyHealthid/DemographicDTO';
import { IdentifierDTO } from 'src/app/models/verifyHealthid/IdentifierDTO';
import { OnConfirmRequestDTO } from 'src/app/models/verifyHealthid/OnConfirmRequestDTO';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';
import * as uuid from 'uuid';
@Component({
  selector: 'app-verfymobile-otp',
  templateUrl: './verfymobile-otp.component.html',
  styleUrls: ['./verfymobile-otp.component.scss'],
})
export class VerfymobileOTPComponent implements OnInit {
  timeLeft: number = 60;
  interval;
  healthId: any;
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  mobileNoSel: any;
  name: any;
  gender: any;
  dateOfBirth: any;
  txnId: any;
  otp: any;
  requestId: any;
  timestamp: any;
  transactionId: any;
  accessToken: any;
  getOnConfirmFlag: boolean = false;
  credentialDTO: CredentialDTO;
  demographic: DemographicDTO;
  identifier: IdentifierDTO;
  responseToken: MainResponseDTO<GenerateSessionRespDTO>;
  resendOtpResp: MainResponseDTO<ResendOtpResp>;
  confirmOtpResp: MainResponseDTO<ConfirmOtpResp>;
  mainOnConfirmRequestDTO: MainResponseDTO<OnConfirmRequestDTO>;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      //console.log(params);
      if (params && params.mobileNoSel) {
        this.mobileNoSel = params.mobileNoSel;
      }
      if (params && params.namesel) {
        this.name = params.namesel;
      }
      if (params && params.gender) {
        this.gender = params.gender;
      }
      if (params && params.dateOfBirth) {
        this.dateOfBirth = params.dateOfBirth;
      }
      if (params && params.txnId) {
        this.txnId = params.txnId;
        this.transactionId = params.txnId;
        //console.log('this.transactionId::', this.transactionId);
      }
    });
    this.startTimer();
  }
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
  resendOTP() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.timeLeft = 60;
        this.responseToken = result;
        this.accessToken = this.responseToken.response.accessToken;
        //console.log('this.accessToken::', this.accessToken);
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.accessToken
        ) {
          this.resendOTPForMobile(this.accessToken);
        }
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Unable to send OTP', 'Error');
      }
    );
  }
  resendOTPForMobile(accessToken) {
    //console.log('this.mobileNoSel', this.mobileNoSel);
    //console.log('this.txnId', this.txnId);
    //console.log('this.accessToken', accessToken);
    this._servises.resendOTPForMobile(this.txnId, accessToken).subscribe(
      (result) => {
        this.resendOtpResp = result;
        //console.log('txnId', result.response.txnId);
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Unable to send OTP', 'Error');
      }
    );
  }
  submit() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseToken = result;
        this.accessToken = result.response.accessToken;
        //console.log('accessToken::', this.accessToken);
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.accessToken
        ) {
          this.authConfirm(this.accessToken);
        }
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Unable to verify OTP', 'Error');
      }
    );
  }
  authConfirm(accessToken) {
    var myUUID = uuid.v4();
    //console.log('myUUID::' + myUUID);
    this.requestId = myUUID;
    //console.log('this.requestId::', this.requestId);
    this.timestamp = this.currentTimestamp();
    //console.log('this.timestamp::', this.timestamp);
    this.credentialDTO = new CredentialDTO();
    //console.log('this.otp::' + this.otp);
    this.credentialDTO.authCode = this.otp;
    this.demographic = new DemographicDTO();
    this.demographic.name = this.name;
    //console.log('this.name::' + this.name);
    this.demographic.gender = this.gender;
    //console.log('this.gender::' + this.gender);
    this.demographic.dateOfBirth = this.dateOfBirth;
    this.identifier = new IdentifierDTO();
    this.identifier.type = 'MOBILE';
    this.identifier.value = this.mobileNoSel;
    //console.log('this.mobileNoSel::' + this.mobileNoSel);
    this.demographic.identifier = this.identifier;
    this.credentialDTO.demographic = this.demographic;
    this._servises
      .authConfirm(this.requestId, this.timestamp, this.transactionId, this.credentialDTO, accessToken)
      .subscribe(
        (result) => {
          //console.log('result::', result);
          //this.getOnConfirm();
          this.toastrMessage.showSuccessMsg('Health ID ' + this.healthId + 'has been linked to your Patient Profile for Telemedicine.', 'Success');
        },
        (error) => {
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg('Health ID has not been linked to your Patient Profile for Telemedicine, please try again.', 'Error');
        }
      );
  }
  getOnConfirm() {
    this._servises.getOnConfirm(this.requestId, this.timestamp, this.accessToken).subscribe(
      (result) => {
        if (result) {
          //console.log('result::', result);
          //console.log('result.status::', result.status);
          this.getOnConfirmFlag = result.status;
          if (this.getOnConfirmFlag) {
            this.mainOnConfirmRequestDTO = result;
            //console.log('result inside::', result);
          } else {
            this.getOnConfirm();
          }
        }
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Unable to get On Confirm data', 'Error');

      }
    );
  }
  back() {
    this.router.navigate(['/']);
  }

  currentTimestamp() {
    const event = new Date();

    if (!Date.prototype.toISOString) {
      (function () {

        function pad(number) {
          if (number < 10) {
            return '0' + number;
          }
          return number;
        }

        Date.prototype.toISOString = function () {
          return this.getUTCFullYear() +
            '-' + pad(this.getUTCMonth() + 1) +
            '-' + pad(this.getUTCDate()) +
            'T' + pad(this.getUTCHours()) +
            ':' + pad(this.getUTCMinutes()) +
            ':' + pad(this.getUTCSeconds()) +
            '.' + (this.getUTCMilliseconds() / 1000).toFixed(3).slice(2, 5) +
            'Z';
        };

      }());
    }
    return event.toISOString();
  }
}
