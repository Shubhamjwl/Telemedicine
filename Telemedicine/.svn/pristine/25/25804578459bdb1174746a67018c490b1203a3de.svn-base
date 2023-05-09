import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { SearchByHealthIdDTO } from 'src/app/models/common/SearchByHealthIdDTO';
import { SearchByMobDTO } from 'src/app/models/common/SearchByMobDTO';
import { SearchByMobRespDTO } from 'src/app/models/common/SearchByMobRespDTO';

import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../healthid-creation-service.service';

@Component({
  selector: 'app-healthid-search',
  templateUrl: './healthid-search.component.html',
  styleUrls: ['./healthid-search.component.scss'],
})
export class HealthidSearchComponent implements OnInit {
  ERR = ERR_MSGS;
  PATTERN = REGEX;
  healthId: any;
  healthIdNumber: any;
  healthIdNumberFlag: boolean = false;
  refreshToken: any;
  accessToken: any;
  responseToken: MainResponseDTO<GenerateSessionRespDTO>;
  searchByHealthIdDTO: SearchByHealthIdDTO;
  mainSearchByMobRespDTO: MainResponseDTO<SearchByMobRespDTO>;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService
  ) {}

  ngOnInit(): void {}

  submit() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseToken = result;
        this.refreshToken = result.response.refreshToken;
        this.accessToken = result.response.accessToken;
        if (
          this.responseToken &&
          this.responseToken.response &&
          this.responseToken.response.accessToken
        ) {
          this.searchHealthId(this.accessToken);
        }
      },
      (error) => {
        //console.log(error.message, 'Error');
        this.toastrMessage.showErrorMsg('Unable to verify OTP', 'Error');
      }
    );
  }
  searchHealthId(accessToken) {
    this.searchByHealthIdDTO = new SearchByHealthIdDTO();
    this.searchByHealthIdDTO.healthId = this.healthId;
    this._servises
      .searchByHealthId(accessToken, this.searchByHealthIdDTO)
      .subscribe(
        (result) => {
          this.healthIdNumberFlag = true;
          this.mainSearchByMobRespDTO = result;
          this.healthIdNumber = result.response.healthIdNumber;
          //console.log('healthIdNumber', this.healthIdNumber);
        },
        (error) => {
          //console.log(error.message, 'Error');
          this.toastrMessage.showErrorMsg('Unable to Search healthId', 'Error');
        }
      );
  }
  back() {
    this.router.navigate(['/']);
  }
}
