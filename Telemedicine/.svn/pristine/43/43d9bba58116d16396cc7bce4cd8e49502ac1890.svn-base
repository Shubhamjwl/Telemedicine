import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { ConfirmOtpRespAadhaar } from 'src/app/models/healthidAadhaar/ConfirmOtpRespAadhaar';
import { ConfirmOtpResp } from 'src/app/models/healthidMobile/ConfirmOtpResp';
import { ResendOtpResp } from 'src/app/models/healthidMobile/ResendOtpResp';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-aadhar-mobile-otp',
  templateUrl: './aadhar-mobile-otp.component.html',
  styleUrls: ['./aadhar-mobile-otp.component.css'],
})
export class AadharMobileOtpComponent implements OnInit {
  timeLeft: number = 60;
  interval;
  name: string;
  MobileNo: any;
  shortMobileNo: any;
  countNum: string;
  CodedMobileNo: string;
  dob: string;
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
  mobileNoSel: any;
  aadhaar: any;
  txnId: any;
  otp: any;
  refreshToken: any;
  accessToken: any;
  responseToken: MainResponseDTO<GenerateSessionRespDTO>;
  resendOtpResp: MainResponseDTO<ResendOtpResp>;
  confirmOtpResp: MainResponseDTO<ConfirmOtpRespAadhaar>;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService,
    private _location: Location
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      console.log(params);
      if (params && params.aadhaar) {
        this.aadhaar = params.aadhaar;
      }
      if (params && params.mobileNoSel) {
        this.mobileNoSel = params.mobileNoSel;
        (this.MobileNo = this.mobileNoSel),
          (this.shortMobileNo = this.MobileNo.slice(-2)),
          (this.countNum = '');

        for (var i = this.MobileNo.length - 2; i > 0; i--) {
          this.countNum += '*';
        }
        this.CodedMobileNo = '+91' + this.countNum + this.shortMobileNo;
      }
      if (params && params.txnId) {
        this.txnId = params.txnId;
      }
    });
    this.startTimer();
  }

  resendOTPAadhaar() {
    this._servises.generateToken().subscribe(
      (result) => {
        // console.log('123_RESEND_OTP', result);
        this.responseToken = result;
        this.timeLeft = 60;
        this.refreshToken = this.responseToken.response.refreshToken;
        this.accessToken = this.responseToken.response.accessToken;
        console.log('123_this.accessToken::', this.accessToken);
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.accessToken
        ) {
          this.resendOTPForAadhaar(this.accessToken);
        }
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg(
          'Unable to send OTP for Aadhaar',
          'Error'
        );
      }
    );
  }
  resendOTPForAadhaar(accessToken) {
    console.log('123_IN_resendOTPForAadhaar::', accessToken);
    //console.log('this.aadhaar', this.aadhaar);
    //console.log('this.txnId', this.txnId);
    //console.log('this.refreshToken', refreshToken);
    this._servises.resendOTPForAadhaar(this.txnId, accessToken).subscribe(
      (result) => {
        console.log('123_resendOTPForAadhaar', result);
        this.resendOtpResp = result;
        //console.log('txnId', result.response.txnId);
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg(
          'Unable to send OTP for Aadhaar',
          'Error'
        );
      }
    );
  }
  submit() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseToken = result;
        this.refreshToken = result.response.refreshToken;
        this.accessToken = this.responseToken.response.accessToken;
        //console.log('refreshToken::', this.refreshToken);
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.accessToken
        ) {
          this.verifyOTPForAadhaar(this.accessToken);
        }
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
  verifyOTPForAadhaar(accessToken) {
    //console.log('this.otp', this.otp);
    //console.log('this.txnId', this.txnId);
    //console.log('this.refreshToken', refreshToken);
    this._servises
      .verifyOTPForAadhaar(this.otp, this.txnId, accessToken)
      .subscribe(
        (result) => {
          this.confirmOtpResp = result;
          this.txnId = result.response.txnId;
          //console.log('txnId::', this.txnId);
          this.router.navigate(['/AadharMobileRegester'], {
            queryParams: { mobileNoSel: this.mobileNoSel, txnId: this.txnId },
            skipLocationChange: true,
          });
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
  back() {
    //this._location.back();
    window.history.back();
  }
}
