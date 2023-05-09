import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DistrictDTO } from 'src/app/models/common/DistrictDTO';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { StateDtlsDTO } from 'src/app/models/common/StateDtlsDTO';
import { HealthCardDTO } from 'src/app/models/healthidMobile/HealthCardDTO';
import { HealthIDDTO } from 'src/app/models/healthidMobile/HealthIDDTO';
import { HealthIDResp } from 'src/app/models/healthidMobile/HealthIDResp';
import { ResendOtpResp } from 'src/app/models/healthidMobile/ResendOtpResp';

@Component({
  selector: 'app-patient-regester-next',
  templateUrl: './patient-regester-next.component.html',
  styleUrls: ['./patient-regester-next.component.css'],
})
export class PatientRegesterNextComponent implements OnInit {
  timeLeft: number = 60;
  interval;
  startTimer() {
    this.interval = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
      }
      // else {
      //   this.timeLeft = 60;
      // }
    }, 1000);
  }
  token: any;
  txnId: any;
  otp: any;
  mobileNoSel: any;
  firstName: any;
  middleName: any;
  lastName: any;
  dateOfBirth: any;
  gender: any;
  email: any;
  address: any;
  pinCode: any;
  stateCode: any;
  districtCode: any;
  subDistrict: any;
  town: any;
  ward: any;
  village: any;
  photoFile: any;
  dayOfBirth: any;
  monthOfBirth: any;
  yearOfBirth: any;
  healthId: any;
  healthId1: any;
  healthId2: any;
  healthIdNumber: any;
  password: any;
  confirmPassword: any;
  name: any;
  restrictions: any;
  refreshToken: any;
  isShownRegistration: boolean = true;
  isShownAfterRegistration: boolean = false;
  isShowPopup: boolean = false;
  isShowPopup2: boolean = false;
  isChecked: boolean = true;
  responseToken: MainResponseDTO<GenerateSessionRespDTO>;
  resendOtpResp: MainResponseDTO<ResendOtpResp>;
  healthIDResp: MainResponseDTO<HealthIDResp>;
  statesMainDTO: MainResponseDTO<StateDtlsDTO[]>;
  healthCardDTO: MainResponseDTO<HealthCardDTO>;
  states: StateDtlsDTO[];
  districtsMainDTO: MainResponseDTO<DistrictDTO[]>;
  districts: DistrictDTO[];
  healthIDDTO = new HealthIDDTO();
  constructor(private router: Router) {}
  ngOnInit(): void {
    this.startTimer();
  }
  public imagePath: any;
  imgURL: any;
  public message: string | undefined;

  preview(files: any) {
    if (files.length === 0) return;

    var mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = 'Only images are supported.';
      return;
    }

    var reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.imgURL = reader.result;
    };
  }

  submit() {
    this.router.navigate(['/yesNomessage']);
  }
}
