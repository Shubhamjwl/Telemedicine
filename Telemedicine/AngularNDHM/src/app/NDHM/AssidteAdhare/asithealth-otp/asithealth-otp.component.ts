import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { ConfirmOtpResp } from 'src/app/models/healthidMobile/ConfirmOtpResp';
import { ResendOtpResp } from 'src/app/models/healthidMobile/ResendOtpResp';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';

@Component({
  selector: 'app-asithealth-otp',
  templateUrl: './asithealth-otp.component.html',
  styleUrls: ['./asithealth-otp.component.css'],
})
export class AsithealthOtpComponent implements OnInit {
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  mobileNoSel: any;
  txnId: any;
  otp: any;
  refreshToken: any;
  accessToken: any;
  responseToken: MainResponseDTO<GenerateSessionRespDTO>;
  resendOtpResp: MainResponseDTO<ResendOtpResp>;
  confirmOtpResp: MainResponseDTO<ConfirmOtpResp>;
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
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      //console.log(params);
      if (params && params.mobileNoSel) {
        this.mobileNoSel = params.mobileNoSel;
      }
      if (params && params.txnId) {
        this.txnId = params.txnId;
      }
    });
    this.startTimer();
  }
  resendOTP() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.timeLeft = 60;
        this.responseToken = result;
        this.refreshToken = this.responseToken.response.refreshToken;
        this.accessToken = this.responseToken.response.accessToken;
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
        this.toastrMessage.showErrorMsg('Unable to generate token', 'Error');
      }
    );
  }
  resendOTPForMobile(accessToken) {
    this._servises.resendOTPForMobile(this.txnId, accessToken).subscribe(
      (result) => {
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
        this.accessToken = result.response.accessToken;
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.accessToken
        ) {
          this.verifyOTPForMobile(this.accessToken);
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
  verifyOTPForMobile(accessToken) {
    this._servises
      .verifyOTPForMobile(this.otp, this.txnId, accessToken)
      .subscribe(
        (result) => {
          this.confirmOtpResp = result;
          //console.log('token', result.response.token);
          this.router.navigate(['/AsithealthRegester'], {
            queryParams: {
              mobileNoSel: this.mobileNoSel,
              token: result.response.token,
              txnId: this.txnId,
            },
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
    this.router.navigate(['/']);
  }
}
