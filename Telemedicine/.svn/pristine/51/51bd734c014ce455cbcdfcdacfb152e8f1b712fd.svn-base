import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { AskToLogoutModalComponent } from '../ask-to-logout-modal/ask-to-logout-modal.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent  implements OnInit {
  loader: boolean;
  forgotLoader: boolean;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private toastMessage: ToastMessageService,
    public authService: AuthService,
    private dataPassingService: DataPassingService,
    private dialog : MatDialog,
  ) {
      this.randomStringForCaptcha = this.randomString(
        10,
        '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
      );
      this.loader = false;
	  this.forgotLoader = false;
   }

  /**
   * Used to store SignIn Form.
   */
  form: FormGroup;
  randomStringForCaptcha: string;
  captcha;
  /**
   * Used to show | hide password.
   */
  hide = false;

  emailID: any = new FormControl();

  forgetPasswordFlag: boolean = false;

  /**
   * Used to show | hide password.
   */
  hideReEnterPassword = true;
  ngOnInit() {
    this.form = this.fb.group({
      email: ['',Validators.required],
      password: ['',Validators.required],
      captcha: ['', Validators.required]
    })
    this.generateCaptcha();
  }

  randomString(length, chars) {
    var result = '';
    for (var i = length; i > 0; --i)
      result += chars[Math.floor(Math.random() * chars.length)];
    return result;
  }

  
  generateCaptcha() {
    this.authService
      .generateCaptcha(this.randomStringForCaptcha)
      .subscribe((result) => {
        this.createImageFromBlob(result);
      },(error) => {
        this.toastMessage.showErrorMsg(error.message, 'Error');
      });
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener(
      'load',
      () => {
        this.captcha = reader.result;
      },
      false
    );
    if (image) {
      reader.readAsDataURL(image);
    }
  }

    /**
   * Used to get New CPACHA image from API response
   */
  refreshCpacha() {
    this.randomStringForCaptcha = this.randomString(
      10,
      '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
    );
    this.form.controls['captcha'].setValue(null);
    this.generateCaptcha();
  }

  
  verifyCaptcha() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    } 
    else {
      this.loader = true;
      let captcha = this.form.get('captcha').value;
      this.authService
        .verifyCaptcha(this.randomStringForCaptcha, captcha)
        .subscribe((result) => {
          if (result['status']) {
            this.signIn(this.randomStringForCaptcha, captcha);
          } else {
            if (result['errors']) {
              this.toastMessage.showErrorMsg( result['errors'][0].message, 'Error');
            }
            this.loader = false;
          }
        },error => {
          this.loader = false;
          this.toastMessage.showErrorMsg(error.errors[0].message, 'Error');
        });
    }
  }

  /**
   * Used to submit login user form
   */
  signIn(randomStringForCaptcha, captcha) {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.loader = false;
      return;
    }
    else {
      this.userSignIn(randomStringForCaptcha, captcha);
    }
  }

  /**
   * API call of SignIn user on click submit
   */
  userSignIn(randomStringForCaptcha, captcha) {
    
    const { email: userId, password } = this.form.value;
    this.dataPassingService.userId = userId;
    const data ={
      userId,
      password
    };

    this.authService.signIn(data,randomStringForCaptcha, captcha).subscribe((result) => {
      this.loader = false;
      if(result && result.response) {
        if(result.response.passwordChanged) {
          this.router.navigate(['/change-password']);
        }else {
        let userID = result ? result.response ? result.response.userId ? result.response.userId : null : null : null;
        let token = result ? result.response ? result.response.token ? result.response.token : null : null : null;
        let role = result ? result.response ? result.response.role ? result.response.role : null : null : null;
        this.dataPassingService.loggingUserName = result ? result.response ? result.response.userName ? result.response.userName : null : null : null;
        let closeDrGrp = result ? result.response ? result.response.closeDrGrp ? result.response.closeDrGrp : false : false : false;
        sessionStorage.setItem('CLOSE_DR_GRP', closeDrGrp);
        this.authService.doLoginUser(userID,token,role);

          	if(role == 'PATIENT'){
            	this.router.navigate(['../patient-dashboard'], { relativeTo: this.activateRoute });
          	} else if(role == 'ADMIN'){
				      this.router.navigate(['../doc-de-register'], { relativeTo: this.activateRoute });
          	} else if(role == 'SYSTEMUSER'){
                this.router.navigate(['../checker'], { relativeTo: this.activateRoute });
            } else if(role == 'CALLCENTRE') {
              this.router.navigate(['../book-appointment-dashboard'], { relativeTo: this.activateRoute });
            }
             else {
            	this.router.navigate(['appointments']);
          	}
          
      }
        // this.router.navigate(['../doctorRegistrationProcess'], { relativeTo: this.activateRoute });
      }else if(result && result.errors){
        if(result.loggedInFlag){
          this.refreshCpacha();
          this.openDialog(userId);
          // this.userSignOut(userId);
        }else{
          this.refreshCpacha();
          this.loader = false;
          this.toastMessage.showErrorMsg(result.errors[0].message, "Error");
        }
      // let userID  = "NSDLUSER1";
      // let token = "BearereyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJOU0RMVVNFUjEiLCJyb2xlIjoiRE9DVE9SIiwiaWF0IjoxNjA2NTU3Nzg5LCJleHAiOjE2MDY2NDQxODl9.O35A-q38iU61SUB7bcM26Lu3KpuehtO-Tj-X3VCspL5RPjzo398YKcuFB6ZXFAQkTrjOyr8kJ8V85VYqIDPwyw";
      // this.authService.doLoginUser(userID,token);        
      // this.router.navigate(['../appointments'], { relativeTo: this.activateRoute });
      }
      this.loader = false;
    }, error => {
      this.loader = false;
      this.refreshCpacha();
      this.technicalBackendErrorMsg();
    });

  }


  userSignOut(userID) {
    this.authService.signOutWithOutToken(userID).subscribe(result => {
      if(result && result.response && result.status) {
        this.verifyCaptcha();
      }else if(result && result.errors){
        this.refreshCpacha();
        this.toastMessage.showErrorMsg(result.errors[0].message, "Error");
      }
    },error => {
      this.toastMessage.showErrorMsg(error.errors[0].message, 'Error');
    })
  }

  /**
   * Used to Navigate to OTP Dialog Modal
   */
  openDialog(userId): void {
    const dialogRef = this.dialog.open(AskToLogoutModalComponent, {              
      disableClose: true ,
      width: '500px',
      data: {type: 'signin'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result.data == 'yes') {
        this.userSignOut(userId);
      }else {

      }
    });
  }

  /**
   * Used for forgot password info message
   */
  forgetPassword() {
    if (this.form.get('email').invalid) {
      this.form.get('email').markAllAsTouched();
      return;
    }
    else {
      this.getOtp();
     // this.apiCallForgotPassword();
    }

    // this.toastMessage.showInfoMsg(ErrorSuccessMessage.FORGOTPASSWORD, 'Information');
    // this.forgetPasswordFlag = true;

  }

  /**
   * API call of forgot API Call
   */
  apiCallForgotPassword() {
    // Forget Password API call
    let request = {
      "id":"user",
      "version":"v1",
      "requesttime":"2020-01-02T10:01:21.331Z",
      "request": {
        "userId": this.form.get('email').value? this.form.get('email').value : '',
      }

    };
    this.authService.forgotPassword(request).subscribe(result => {
        if (result.response != null) {
          this.toastMessage.showSuccessMsg(result.response.message, 'Success');          
        }
        else {
          if (result.errors != null) {
            let errorMsg = ' ';
            if (result.errors.length > 0) {
              for(let msg of result.errors) {
                errorMsg = errorMsg + ' ' + msg.message;
              }
              this.toastMessage.showErrorMsg(errorMsg, 'Error');
            } else {
              if (result.errors.message) {
                this.toastMessage.showErrorMsg(result.errors.message, 'Error');
              } else {
                this.toastMessage.showErrorMsg(ErrorSuccessMessage.INVALIDAPIRESPONSE, "Error");
              }
            }
          }
          
        }

    }, error => {
      this.technicalBackendErrorMsg();
    });

  }

  /**
   * Used for email ID popup
   */
  closeForgotPassword() {
    this.emailID = new FormControl();
  }

  technicalBackendErrorMsg() {
    this.toastMessage.showErrorMsg(ErrorSuccessMessage.CODEFAILUIR, "Error");
  }

  getOtp(){
    if(this.forgotLoader){
      return;
    }

    this.forgotLoader = true;
    let userId =  this.form.get('email').value;
    let fromWhere = "forgot";
    this.authService.getUserDetailsAndSendOTP(userId, fromWhere).subscribe((result:any) => {
		this.forgotLoader = false;
		if(result.status && result.response){              
			this.toastMessage.showSuccessMsg(result.response.description, "Success");
			this.router.navigate(['/change-password'],{queryParams: {fromWhere: 'forgot'}});
			this.dataPassingService.userId = this.form.get('email').value;
		}else if(result.errors && result.status == false) {
			this.toastMessage.showErrorMsg( result.errors[0].message, 'Error');
		}
    },error => {
      this.forgotLoader = false;
      this.toastMessage.showErrorMsg(error.errors[0].message, 'Error');
    })
    

  }

}

