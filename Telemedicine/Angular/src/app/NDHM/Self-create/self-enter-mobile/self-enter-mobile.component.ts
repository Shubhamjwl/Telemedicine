import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { GenerateOtpResp } from 'src/app/models/healthidMobile/GenerateOtpResp';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';

@Component({
  selector: 'app-self-enter-mobile',
  templateUrl: './self-enter-mobile.component.html',
  styleUrls: ['./self-enter-mobile.component.css'],
})
export class SelfEnterMobileComponent implements OnInit {
  mobileNoSel: any;
  patientName:any;
  email:any;
  txnId: any;
  refreshToken: any;
  responseTokenDTO: MainResponseDTO<GenerateSessionRespDTO>;
  responseOTP: MainResponseDTO<GenerateOtpResp>;
  // mobNumberPattern = '^[6-9]\\d{9}$';
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService
  ) {}

  ngOnInit(): void {
    }
  otp() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseTokenDTO = result;
        this.refreshToken = result.response.refreshToken;
        console.log('this.refreshToken::', this.refreshToken);
        if (
          this.responseTokenDTO &&
          this.responseTokenDTO.response &&
          this.responseTokenDTO.response.refreshToken
        ) {
          this.generateOTP();
        }
      },
      (error) => {
        console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Unable to generate token', 'Error');
      }
    );
  }
  generateOTP() {
    this._servises
      .generateOTPForMobile(this.mobileNoSel, this.refreshToken)
      .subscribe(
        (result) => {
          this.responseOTP = result;
          this.txnId = result.response.txnId;
          console.log('this.txnId', this.txnId);
          console.log('this.mobileNoSel', this.mobileNoSel);
          this.router.navigate(['/selfotp'], {
          queryParams: { mobileNoSel: this.mobileNoSel ,txnId:this.txnId},
          });
        },
        (error) => {
          console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg('Unable to send OTP', 'Error');
        }
      );
  }
}
