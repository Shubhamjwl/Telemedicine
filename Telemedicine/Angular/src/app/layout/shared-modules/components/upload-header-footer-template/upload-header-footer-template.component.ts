import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { DoctorService } from 'src/app/shared/commonBuildBlocks/services/doctorServices/doctor.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-upload-header-footer-template',
  templateUrl: './upload-header-footer-template.component.html',
  styleUrls: ['./upload-header-footer-template.component.scss']
})
export class UploadHeaderFooterTemplateComponent implements OnInit {
  
  profilePhoto: string = 'assets/images/img_avatar.png';
  headerBase64;
  footerBase64;

  constructor(
    private toastrMessage: ToastMessageService,
    private doctorService: DoctorService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  
  /**
   * Used to select profile photo
   */
  browse(type) {
    if(type === 'header') {
      document.getElementById("browseHeader").click();
    }else if(type === 'footer') {
      document.getElementById("browseFooter").click();
    }
  }
  
  /**
   * Used to set selected profile photo
   */
  uploadProfilePhoto(event, type) {
    let hasImg = event.target.files[0].type.includes('image') ? true : false;
    let hasSize = event.target.files[0].size <= 1000000 ? true : false; //1000000
    if(hasImg && hasSize) {
      const files = event.target.files;
      const file = files[0];
      if (files && file) {
        const reader = new FileReader();
        reader.onload = (e) => {
        if(type === 'header') {
              this.headerBase64 = e.target.result;
        }else if(type === 'footer') {
          this.footerBase64 = e.target.result;
        }
        };
        reader.readAsDataURL(event.target.files[0]);
        //  this._handleReaderLoaded(reader);
      }
      this.displayHeaderAndFooter(event, type);
    }else{
      if(!hasImg){
        this.toastrMessage.showWarningMsg(ErrorSuccessMessage.IMGFORMATE, 'Warning');
        event.target.files = ''; 
      }
      if(!hasSize){
        this.toastrMessage.showWarningMsg(ErrorSuccessMessage.IMGSIZE, 'Warning');
        event.target.files = ''; 
      }
    }
  }

  displayHeaderAndFooter(event, type) {
    if(type === 'header') {
      document
        .getElementById('HeaderPhoto')
        .setAttribute('src', window.URL.createObjectURL(event.target.files[0]));
    }else if(type === 'footer') {
      document
        .getElementById('FooterPhoto')
        .setAttribute('src', window.URL.createObjectURL(event.target.files[0]));
    }
  }

  submitHeaderFooter(){
    let payload = {
      ctDoctorId: sessionStorage.getItem('USER_ID'),
      templateHeader: this.headerBase64,
      templateFooter: this.footerBase64 
    }
    this.doctorService.uploadHeaderFooter(payload).subscribe(result => {
      if(result.response){
        this.toastrMessage.showSuccessMsg(result.response.message, "Success");
      }else if(result.errors) {
        this.toastrMessage.showErrorMsg( result.errors[0].message, 'Error');
      }
    },error => {
        // console.log(error["errors"]);
        this.toastrMessage.showErrorMsg(error.errors[0].message, 'Error');
    })
  }

  cancel() {
    this.router.navigate(['appointments']);
  }
}
