import { Component, OnInit } from '@angular/core';
import { ERR_MSGS, REGEX } from 'src/config/constant';

@Component({
  selector: 'app-fatchprofile-map',
  templateUrl: './fatchprofile-map.component.html',
  styleUrls: ['./fatchprofile-map.component.css'],
})
export class FatchprofileMapComponent implements OnInit {
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
