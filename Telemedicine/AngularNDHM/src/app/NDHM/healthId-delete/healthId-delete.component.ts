import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { GenerateSessionRespDTO } from 'src/app/models/common/GenerateSessionRespDTO';
import { MainResponseDTO } from 'src/app/models/common/MainResponseDTO';
import { GenerateOtpResp } from 'src/app/models/healthidMobile/GenerateOtpResp';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ERR_MSGS, REGEX } from 'src/config/constant';
import { HealthidCreationServiceService } from '../healthid-creation-service.service';
import { Location } from '@angular/common';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { UserAuthRespDTO } from 'src/app/models/common/UserAuthRespDTO';

@Component({
  selector: 'app-healthid-delete',
  templateUrl: './healthid-delete.component.html',
  styleUrls: ['./healthid-delete.component.css'],
})
export class HealthidDeleteComponent implements OnInit {
  hidePassword: boolean = true;
  closeResult: string;
  password: any;
  healthIdOne: any;
  healthidDeleteForm: any;
  responseToken: MainResponseDTO<GenerateSessionRespDTO>;
  refreshToken: any;
  accessToken: any;
  userAuthRespDTO: MainResponseDTO<UserAuthRespDTO>;
  userAuthToken: any;
  constructor(
    private modalService: NgbModal,
    private router: Router,
    private route: ActivatedRoute,
    private toastrMessage: ToastMessageService,
    private _servises: HealthidCreationServiceService,
    private _location: Location
  ) {}

  ngOnInit() {
    this.generateToken();
  }

  open(content) {
    this.modalService
      .open(content, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          this.healthidDeleteForm.clear();
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }
  generateToken() {
    this._servises.generateToken().subscribe(
      (result) => {
        this.responseToken = result;
        this.refreshToken = this.responseToken.response.refreshToken;
        this.accessToken = this.responseToken.response.accessToken;
      },
      (error) => {
        this.toastrMessage.showErrorMsg(
          'Unable to generate accessToken',
          'Error'
        );
      }
    );
  }

  delete() {
    this.userAuthWithPassword();
  }
  userAuthWithPassword() {
    this._servises
      .userAuthWithPassword(this.healthIdOne, this.password, this.accessToken)
      .subscribe(
        (result) => {
          this.userAuthRespDTO = result;
          this.userAuthToken = result.response.token;
          console.log('this.userAuthToken::', this.userAuthToken);
          this._servises
            .deleteHealthId(this.accessToken, this.userAuthToken)
            .subscribe(
              (result) => {
                console.log('result' + result);
                this.toastrMessage.showSuccessMsg(
                  'Health id Deleted Successfully',
                  'Success'
                );
              },
              (error) => {
                this.toastrMessage.showErrorMsg(
                  'Something whent wrong',
                  'Error'
                );
              }
            );
        },
        (error) => {
          this.toastrMessage.showErrorMsg(
            'Unable to generate user Auth Token',
            'Error'
          );
        }
      );
  }
}
