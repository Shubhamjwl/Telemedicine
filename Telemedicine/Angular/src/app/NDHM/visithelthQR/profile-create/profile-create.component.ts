import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { GenerateOtpResp } from 'src/app/models/healthidMobile/GenerateOtpResp';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';

@Component({
  selector: 'app-profile-create',
  templateUrl: './profile-create.component.html',
  styleUrls: ['./profile-create.component.css'],
})
export class ProfileCreateComponent implements OnInit {
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  selectanyoption: any;
  patientName: any;
  mobileNoSel: any;
  email: any;
  address: any;
  gender: any;
  dateOfBirth: any;
  namesel: any;
  healthId: any;
  healthIdOne: any;
  txnId: any;
  refreshToken: any;
  responseTokenDTO: MainResponseDTO<GenerateSessionRespDTO>;
  responseOTP: MainResponseDTO<GenerateOtpResp>;
  constructor(private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      console.log(params);
      if (params && params.patientName) {
        this.patientName = params.patientName;
      }
      if (params && params.mobileNo) {
        this.mobileNoSel = params.mobileNo;
      }
      if (params && params.email) {
        this.email = params.email;
      }
    });
  }
  otp() {
    this.router.navigate(['/PatientKYCp'], {
      queryParams: { healthId: this.healthId },
    });
  }

}
