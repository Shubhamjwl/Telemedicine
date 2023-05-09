import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';

@Component({
  selector: 'app-patient-kycp',
  templateUrl: './patient-kycp.component.html',
  styleUrls: ['./patient-kycp.component.css'],
})
export class PatientKYCpComponent implements OnInit {
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  mobileNoSel: any;
  namesel: any;
  healthId: any;
  verifyWithMobileOTPFlag: boolean = false;
  verifyWithMobileIVRFlag: boolean = false;
  verifyWithAadhaarOTPFlag: boolean = false;
  verifyWithAadhaarBioMetricFlag: boolean = false;
  txnId: any;
  refreshToken: any;
  activecss: any;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      console.log(params);
      if (params && params.healthId) {
        this.healthId = params.healthId;
      }
    });
  }
  verifyWithMobileOTP(first) {
    console.log('verifyWithMobileOTP');
    console.log('', first);
    this.activecss = first;
    // this.router.navigate(['/PatienAuther'], {
    //   queryParams: { healthId: this.healthId },
    // });
  }
  verifyWithMobileIVR(first) {
    console.log('verifyWithMobileIVR');
    this.activecss = first;
    this.router.navigate(['/PatienAuther'], {
      queryParams: { healthId: this.healthId },
    });
  }
  verifyWithAadhaarOTP(first) {
    console.log('verifyWithAadhaarOTP');
    this.activecss = first;
    // this.router.navigate(['/PatienAuther'], {
    //   queryParams: { healthId: this.healthId },
    // });
  }
  verifyWithAadhaarBioMetric(first) {
    this.activecss = first;
    console.log('verifyWithAadhaarBioMetric');
    // this.router.navigate(['/PatienAuther'], {
    //   queryParams: { healthId: this.healthId },
    // });
  }
  verify() {

  }
}
