import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-healthid-index',
  templateUrl: './healthid-index.component.html',
  styleUrls: ['./healthid-index.component.css'],
})
export class HealthidIndexComponent implements OnInit {
  name: any;
  gender: any;
  mobileNoSel: any;
  email: any;
  txnId: any;
  dob: any;
  hvStatus: any;
  isHelathIDExists: boolean = false;
  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      if (params && params.name) {
        this.name = params.name;
      }
      if (params && params.gender) {
        this.gender = params.gender;
      }
      if (params && params.mobileNoSel) {
        this.mobileNoSel = params.mobileNoSel;
      }
      if (params && params.emailId) {
        this.email = params.emailId;
      }
      if (params && params.dob) {
        this.dob = params.dob;
      }
      if (params && params.hvStatus) {
        this.hvStatus = params.hvStatus;
        if (this.hvStatus === 'Y') {
          this.isHelathIDExists = true;
        }
      }
      if (params && params.txnId) {
        this.txnId = params.txnId;
      }
    });
  }
  healthidYes() {
    ///healthidMobile
    this.router.navigate(['/otp'], {
      queryParams: {
        name: this.name,
        gender: this.gender,
        mobileNoSel: this.mobileNoSel,
        email: this.email,
        dob: this.dob,
        txnId: this.txnId,
      },
      skipLocationChange: true,
    });
  }
  healthidNo() {
    //this.router.navigate(['/']);
    // window.location.href = "https://uattele.cloudvoice.in/telemedicine/#/"
    window.location.href = 'https://uat.protean-health.com/telemedicine/#/';
  }
  haveHealthid() {
    this.router.navigate(['/AssistedHealthidCreationMobile'], {
      queryParams: {
        name: this.name,
        gender: this.gender,
        mobileNoSel: this.mobileNoSel,
        email: this.email,
        dob: this.dob,
        txnId: this.txnId,
      },
      skipLocationChange: true,
    });
  }
}
