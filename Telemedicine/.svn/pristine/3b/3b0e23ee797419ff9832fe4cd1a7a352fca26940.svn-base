import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../../healthid-creation-service.service';


@Component({
  selector: 'app-patien-auther',
  templateUrl: './patien-auther.component.html',
  styleUrls: ['./patien-auther.component.css'],
})
export class PatienAutherComponent implements OnInit {
  healthId: any;
  constructor(private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      console.log(params);
      if (params && params.healthId) {
        this.healthId = params.healthId;
      }
    });
  }
  generateOTP() {
    console.log('generateOTP');
  }
}
