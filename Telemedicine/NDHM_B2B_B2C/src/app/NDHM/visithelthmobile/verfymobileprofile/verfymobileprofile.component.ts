import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { ERR_MSGS, REGEX } from 'src/config/constant';
@Component({
  selector: 'app-verfymobileprofile',
  templateUrl: './verfymobileprofile.component.html',
  styleUrls: ['./verfymobileprofile.component.scss'],
})
export class VerfymobileprofileComponent implements OnInit {
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  selectanyoption: any;
  mobileNoSel: any;
  address: any;
  gender: any;
  dateOfBirth: any;
  namesel: any;
  healthId: any;
  healthIdOne: any;
  constructor(private router: Router) {}

  ngOnInit(): void {}
  otp() {
    this.router.navigate(['/AsithealthOtp']);
  }
}
