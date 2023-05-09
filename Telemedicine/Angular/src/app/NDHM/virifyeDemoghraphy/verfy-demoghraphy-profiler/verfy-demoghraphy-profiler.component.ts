import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { ERR_MSGS, REGEX } from 'src/config/constant';
@Component({
  selector: 'app-verfy-demoghraphy-profiler',
  templateUrl: './verfy-demoghraphy-profiler.component.html',
  styleUrls: ['./verfy-demoghraphy-profiler.component.scss'],
})
export class VerfyDemoghraphyProfilerComponent implements OnInit {
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
