import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { GenerateOtpResp } from 'src/app/models/healthidMobile/GenerateOtpResp';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';
declare const setBiometricFunction: any;
import '../../../../assets/js/bio/common.js';
import { VerifyBioReqDTO } from 'src/app/models/healthidAadhaar/VerifyBioReqDTO';
import { AadharOtpResendRespDTO } from 'src/app/models/healthidAadhaar/AadharOtpResendRespDTO';

@Component({
  selector: 'app-asithealth-enter-mobile',
  templateUrl: './asithealth-enter-mobile.component.html',
  styleUrls: ['./asithealth-enter-mobile.component.css'],
})
export class AsithealthEnterMobileComponent implements OnInit {
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
  isChecked: boolean = true;
  isOTPChecked: boolean = true;
  isBioChecked: boolean = false;
  isFingerprintChecked: boolean = false;
  isIRISChecked: boolean = false;
  responseTokenDTO: MainResponseDTO<GenerateSessionRespDTO>;
  responseOTP: MainResponseDTO<GenerateOtpResp>;
  aadharOtpResendRespDTO: MainResponseDTO<AadharOtpResendRespDTO>;
  verifyBioReqDTO: VerifyBioReqDTO;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      //console.log(params);

      if (params && params.aadhaar) {
        this.aadhaar = params.aadhaar;
        //console.log('this.aadhaar' + this.aadhaar);
      }
    });
  }

  otpAadhaar() {
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
            queryParams: { aadhaar: this.aadhaar, txnId: this.txnId },
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
}