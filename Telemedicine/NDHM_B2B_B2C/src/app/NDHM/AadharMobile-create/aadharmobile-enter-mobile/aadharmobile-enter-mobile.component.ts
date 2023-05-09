import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { AadharOtpResendRespDTO } from 'src/app/models/healthidAadhaar/AadharOtpResendRespDTO';
import { VerifyBioReqDTO } from 'src/app/models/healthidAadhaar/VerifyBioReqDTO';
import { GenerateOtpResp } from 'src/app/models/healthidMobile/GenerateOtpResp';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';
declare const setBiometricFunction: any;
import '../../../../assets/js/bio/common.js';
import { Location } from '@angular/common';
import { ResendOtpResp } from 'src/app/models/healthidMobile/ResendOtpResp';

@Component({
  selector: 'app-aadharmobile-enter-mobile',
  templateUrl: './aadharmobile-enter-mobile.component.html',
  styleUrls: ['./aadharmobile-enter-mobile.component.css'],
})
export class AadharmobileEnterMobileComponent implements OnInit {
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  mobileNoSel: any;
  aadhaar: any;
  txnId: any;
  fingerprintDeviceID: any;
  bioType: any;
  pid: any;
  refreshToken: any;
  accessToken: any;
  isChecked: boolean = false;
  isOTPChecked: boolean = false;
  isBioChecked: boolean = false;
  isFingerprintChecked: boolean = false;
  isIRISChecked: boolean = false;
  responseTokenDTO: MainResponseDTO<GenerateSessionRespDTO>;
  responseOTP: MainResponseDTO<GenerateOtpResp>;
  aadharOtpResendRespDTO: MainResponseDTO<AadharOtpResendRespDTO>;
  verifyBioReqDTO: VerifyBioReqDTO;
  resendOtpResp: MainResponseDTO<ResendOtpResp>;
  responseToken: MainResponseDTO<GenerateSessionRespDTO>;
  timeLeft: number = 60;
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
        //console.log('this.aadhaar' + this.aadhaar);
      }
      if (params && params.mobileNoSel) {
        this.mobileNoSel = params.mobileNoSel;
      }
    });
  }

  otpAadhaar() {
    if (this.isOTPChecked) {
      this._servises.generateToken().subscribe(
        (result) => {
          this.responseTokenDTO = result;
          this.refreshToken = result.response.refreshToken;
          this.accessToken = result.response.accessToken;
          if (
            this.responseTokenDTO &&
            this.responseTokenDTO.response &&
            this.responseTokenDTO.response.accessToken
          ) {
            if (this.isOTPChecked) {
              this.generateOTPAadhaar();
            } else if (this.isBioChecked) {
              if (this.isFingerprintChecked) {
                this.verifyFingerpriint();
              } else if (this.isIRISChecked) {
                this.verifyIRIS();
              }
            }
          }
        },
        (error) => {
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg('Unable to generate token', 'Error');
        }
      );
    } else {
      this.toastrMessage.showErrorMsg('Please select OTP', 'Error');
    }
  }
  generateOTPAadhaar() {
    this._servises
      .generateOTPForAadhaar(this.mobileNoSel, this.aadhaar, this.accessToken)
      .subscribe(
        (result) => {
          this.responseOTP = result;
          this.txnId = result.response.txnId;
          //console.log('this.txnId', this.txnId);
          //console.log('this.aadhaar', this.aadhaar);
          this.router.navigate(['/AadharMobileOtp'], {
            queryParams: {
              aadhaar: this.aadhaar,
              mobileNoSel: this.mobileNoSel,
              txnId: this.txnId,
            },
            skipLocationChange: true,
          });
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
  onClickOTP() {
    this.isOTPChecked = true;
    this.isBioChecked = false;
    this.isFingerprintChecked = false;
    this.isIRISChecked = false;
  }
  onClickBIO() {
    this.isOTPChecked = false;
    this.isBioChecked = true;
    this.isFingerprintChecked = false;
    this.isIRISChecked = false;
  }
  onClickFingerprint() {
    this.isOTPChecked = false;
    this.isBioChecked = true;
    this.isFingerprintChecked = true;
    this.isIRISChecked = false;
  }
  onClickIRIS() {
    this.isOTPChecked = false;
    this.isBioChecked = true;
    this.isFingerprintChecked = false;
    this.isIRISChecked = true;
  }
  verifyFingerpriint() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseTokenDTO = result;
        this.refreshToken = result.response.refreshToken;
        this.accessToken = result.response.accessToken;
        if (
          this.responseTokenDTO &&
          this.responseTokenDTO.response &&
          this.responseTokenDTO.response.accessToken
        ) {
          this.bioType = '';
          this.pid = '';
          this.verifyBioWithFingerpriint(this.accessToken);
        }
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Unable to generate token', 'Error');
      }
    );
  }
  verifyBioWithFingerpriint(accessToken) {
    this._servises
      .verifyBioForAadhaar(this.aadhaar, this.bioType, this.pid, accessToken)
      .subscribe(
        (result) => {
          this.aadharOtpResendRespDTO = result;
          this.txnId = result.response.txnId;
          //console.log('this.txnId', this.txnId);

          // this.router.navigate(['/AadharMobileOtp'], {
          //   queryParams: { aadhaar: this.aadhaar, txnId: this.txnId },
          // });
        },
        (error) => {
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg(
            'Unable to verify fingerprint For Aadhaar',
            'Error'
          );
        }
      );
  }
  verifyIRIS() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseTokenDTO = result;
        this.refreshToken = result.response.refreshToken;
        this.accessToken = result.response.accessToken;
        if (
          this.responseTokenDTO &&
          this.responseTokenDTO.response &&
          this.responseTokenDTO.response.accessToken
        ) {
          this.bioType = '';
          this.pid = '';
          this.verifyBioWithIRIS(this.accessToken);
        }
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Unable to generate token', 'Error');
      }
    );
  }
  verifyBioWithIRIS(accessToken) {
    this._servises
      .verifyBioForAadhaar(this.aadhaar, this.bioType, this.pid, accessToken)
      .subscribe(
        (result) => {
          this.aadharOtpResendRespDTO = result;
          this.txnId = result.response.txnId;
          //console.log('this.txnId', this.txnId);
          //console.log('this.aadhaar', this.aadhaar);
          // this.router.navigate(['/AadharMobileOtp'], {
          //   queryParams: { aadhaar: this.aadhaar, txnId: this.txnId },
          // });
        },
        (error) => {
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg(
            'Unable to verify fingerprint For Aadhaar',
            'Error'
          );
        }
      );
  }
  setBiometricFunctionNew() {
    //console.log(' this.fingerprintDeviceID:: ', this.fingerprintDeviceID)
    new setBiometricFunction(this.fingerprintDeviceID);
  }
  back() {
    //this._location.back();
    window.history.back();
  }
  // resendOTP() {
  //   this._servises.generateToken().subscribe(
  //     (result) => {
  //       this.responseToken = result;
  //       this.refreshToken = this.responseToken.response.refreshToken;
  //       this.accessToken = this.responseToken.response.accessToken;
  //       this.timeLeft = 60;
  //       //console.log('this.refreshToken::', this.refreshToken);
  //       if (
  //         this.responseToken &&
  //         this.responseToken.response &&
  //         this.responseToken.response.accessToken
  //       ) {
  //         this.resendOTPForMobile(this.accessToken);
  //       }
  //     },
  //     (error) => {
  //       //console.log(error.message, 'Error');
  //       this.toastrMessage.showErrorMsg('Unable to generate token', 'Error');
  //     }
  //   );
  // }
  // resendOTPForMobile(accessToken) {
  //   this._servises.resendOTPForMobile(this.txnId, accessToken).subscribe(
  //     (result) => {
  //       this.resendOtpResp = result;
  //       //console.log('txnId', result.response.txnId);
  //     },
  //     (error) => {
  //       //console.log(error.message, 'Error');
  //       this.toastrMessage.showErrorMsg('Unable to send OTP', 'Error');
  //     }
  //   );
  // }
}
