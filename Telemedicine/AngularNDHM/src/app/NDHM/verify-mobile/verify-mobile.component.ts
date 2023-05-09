import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../healthid-creation-service.service';

@Component({
  selector: 'app-verify-mobile',
  templateUrl: './verify-mobile.component.html',
  styleUrls: ['./verify-mobile.component.css'],
})
export class VerifyMobileComponent implements OnInit {
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  token: any;
  aadhaar: any;
  name: any;
  gender: any;
  mobileNoSel: any;
  email: any;
  dob: any;
  address: any;
  txnId: any;
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
        //console.log('this.token::' + this.token);
      }
      if (params && params.aadhaar) {
        this.aadhaar = params.aadhaar;
        //console.log('this.aadhaar::' + this.aadhaar);
      }
      if (params && params.name) {
        this.name = params.name;
      }
      if (params && params.gender) {
        this.gender = params.gender;
      }
      if (params && params.mobileNoSel) {
        this.mobileNoSel = params.mobileNoSel;
        //console.log('this.mobileNoSel::' + this.mobileNoSel);
      }
      if (params && params.email) {
        this.email = params.email;
      }
      if (params && params.dob) {
        this.dob = params.dob;
      }
      if (params && params.address) {
        this.address = params.address;
      }
      if (params && params.txnId) {
        this.txnId = params.txnId;
        console.log('this.txnId::' + this.txnId);
      }
    });
  }
  proceedWithoutAadhaar() {
    this.router.navigate(['/otp'], {
      queryParams: {
        name: this.name,
        gender: this.gender,
        mobileNoSel: this.mobileNoSel,
        email: this.email,
        dob: this.dob,
        address: this.address,
        token: this.token,
        txnId: this.txnId,
      },
      skipLocationChange: true,
    });
  }
  proceedWithAadhaar() {
    this.router.navigate(['/AadharmobileEnterMobile'], {
      queryParams: {
        aadhaar: this.aadhaar,
        name: this.name,
        gender: this.gender,
        mobileNoSel: this.mobileNoSel,
        email: this.email,
      },
      skipLocationChange: true,
    });
  }
  back() {
    this.router.navigate(['/']);
  }
}
