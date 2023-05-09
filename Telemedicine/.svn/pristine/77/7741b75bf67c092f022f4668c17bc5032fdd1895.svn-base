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
  selector: 'app-verfy-aadhare-otp',
  templateUrl: './verfy-aadhare-otp.component.html',
  styleUrls: ['./verfy-aadhare-otp.component.scss'],
})
export class VerfyAadhareOTPComponent implements OnInit {
  timeLeft: number = 60;
  interval;
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  mobileNoSel: any;
  txnId: any;
  otp: any;
  refreshToken: any;
  responseToken: MainResponseDTO<GenerateSessionRespDTO>;
  resendOtpResp: MainResponseDTO<ResendOtpResp>;
  confirmOtpResp: MainResponseDTO<ConfirmOtpResp>;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      console.log(params);
      if (params && params.mobileNoSel) {
        this.mobileNoSel = params.mobileNoSel;
      }
      if (params && params.txnId) {
        this.txnId = params.txnId;
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
        this.toastrMessage.showErrorMsg('Unable to send OTP', 'Error');
      }
    );
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
        this.toastrMessage.showErrorMsg('Unable to send OTP', 'Error');
      }
    );
  }
  submit() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseToken = result;
        this.refreshToken = result.response.refreshToken;
        console.log('refreshToken::', this.refreshToken);
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.refreshToken
        ) {
          this.verifyOTPForMobile(this.refreshToken);
        }
      },
      (error) => {
        console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Unable to verify OTP', 'Error');
      }
    );
  }
  verifyOTPForMobile(refreshToken) {
    console.log('this.otp', this.otp);
    console.log('this.txnId', this.txnId);
    console.log('this.refreshToken', refreshToken);
    this._servises
      .verifyOTPForMobile(this.otp, this.txnId, refreshToken)
      .subscribe(
        (result) => {
          this.confirmOtpResp = result;
          console.log('token', result.response.token);
          this.router.navigate(['/verfymobile'], {
            queryParams: {
              mobileNoSel: this.mobileNoSel,
              token: result.response.token,
              txnId: this.txnId,
            },
          });
        },
        (error) => {
          console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg('Unable to verify OTP', 'Error');
        }
      );
  }
  back() {
    this.router.navigate(['/']);
  }
}
