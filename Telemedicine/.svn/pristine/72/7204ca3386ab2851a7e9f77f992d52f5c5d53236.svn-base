import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { CongratulationsModalComponent } from '../congratulations-modal/congratulations-modal.component';

@Component({
  selector: 'app-modal',
  templateUrl: './otp-modal.component.html',
  styleUrls: ['./otp-modal.component.scss']
})
export class OtpModalComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<OtpModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private userManagementService : AuthService,
    private dialog: MatDialog,
    private router: Router,
    private toastrMessage: ToastMessageService,
    private datapassingService: DataPassingService,
    private spinner: NgxSpinnerService
    ) { }

    /**
     * Used to store dynamic modal type
     */
    modalType: string = this.data ? this.data.modalType ? this.data.modalType : '': '';
    displayModal: string = "none";
    user
    /**
     * Used to store user entered OTP
     */
    userEnteredOtp: string;

  ngOnInit(): void {
  }

  verifyOtp() {
    this.spinner.show();
    this.userManagementService.verifyOtp(this.data.role, this.userEnteredOtp, this.data.userID).subscribe(result => {
      this.spinner.hide();
    }, error => {
      this.toastrMessage.showErrorMsg(error.message, "Error");
    })
  }

  onClickSubmit(event?): void {
    // this.verifyOtp();
    if(this.userEnteredOtp){
      this.userManagementService.verifyOtp(this.data.role, this.userEnteredOtp, this.data.userID).subscribe(result => {
        if(result['status']) {
          if(event && event.type && event.type == 'signin'){
            this.dialogRef.close();
            this.router.navigateByUrl('/home', { skipLocationChange: true });
          }
          else{
            this.dialogRef.close();
            let message = "Thank You for registering into Protean Clinic. Your application is pending for verification, you will be able to login once the verification process is completed. You will receive a notification of the same on your registered Email ID / Mobile no."
            const dialogRef = this.dialog.open(CongratulationsModalComponent, {
              disableClose: true ,
              width: '500px',
              data: {
                role: this.data ? this.data.role ? this.data.role : null : null ,
                message: message
              }
            });
      
            dialogRef.afterClosed().subscribe(result => {
              if(this.data && this.data.role === 'scribe'){
                this.router.navigate(['appointments']);
              }else {
                this.router.navigate(['sign-in']);
              }
            });
          }
        }else if (!result['status']) {
          this.userEnteredOtp = null;
          this.toastrMessage.showErrorMsg(result['response'].description, "Error");
        }
      },error => {
        this.toastrMessage.showErrorMsg(error.message, "Error");
      });
    }
  }

  onClickResend(event) {
    this.spinner.show();
    let userId = this.data.userID;
    let mobileNo = this.data.mobileNo;
    let emailID = this.data.emailID;
    this.userManagementService.generateOtp(userId,mobileNo,emailID).subscribe(result => {
      this.spinner.hide();
      if(result.response && result.status) {
        this.toastrMessage.showSuccessMsg(result.response.description, "Success");
      }else {
        this.toastrMessage.showErrorMsg( result['errors'][0].message, 'Error');
      }
    },error => {
      this.spinner.hide();
      this.toastrMessage.showErrorMsg(error.message, "Error");
    });
  }

  onClickCancel(){
    this.userManagementService.signOut().subscribe(result => {
      if(result && result.response) {
        if(this.data && this.data.role === 'scribe'){
          this.router.navigate(['appointments']);
        }else {
          sessionStorage.clear();
          // this.router.navigate(['']);
          this.router.navigate(['sign-in']);
        }
        this.datapassingService.scribeList = [];
      }else if(result && result.errors){
        alert(result.errors[0].message)
      }
    })
    // this.router.navigateByUrl('', { skipLocationChange: true });
  }
}
