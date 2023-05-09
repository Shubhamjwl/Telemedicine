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
  mobileNoSel: any;
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
        console.log('this.token::' + this.token);
      }
      if (params && params.aadhaar) {
        this.aadhaar = params.aadhaar;
        console.log('this.aadhaar::' + this.aadhaar);
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
  }
  proceedWithoutAadhaar() {
    this.router.navigate(['/patientregister'], {
      queryParams: {
        mobileNoSel: this.mobileNoSel,
        token: this.token,
        txnId: this.txnId,
      },
    });
  }
  proceedWithAadhaar() {
    this.router.navigate(['/AadharmobileEnterMobile'], {
      queryParams: { aadhaar: this.aadhaar },
    });
  }
  back() {
    this.router.navigate(['/']);
  }
}
