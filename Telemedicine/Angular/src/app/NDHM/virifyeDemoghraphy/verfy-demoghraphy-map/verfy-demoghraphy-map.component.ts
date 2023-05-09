import { Component, OnInit } from '@angular/core';
import { ERR_MSGS, REGEX } from 'src/config/constant';
@Component({
  selector: 'app-verfy-demoghraphy-map',
  templateUrl: './verfy-demoghraphy-map.component.html',
  styleUrls: ['./verfy-demoghraphy-map.component.scss'],
})
export class VerfyDemoghraphyMapComponent implements OnInit {
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  mobileNoSel: any;
  address: any;
  gender: any;
  dateOfBirth: any;
  namesel: any;
  healthId: any;
  healthIdOne: any;
  checkboxSel: any;
  constructor() {}

  ngOnInit(): void {}
}
