import { MatDialog } from '@angular/material/dialog';
import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { debounceTime } from 'rxjs/operators';
import { ChangePasswordRequest } from 'src/app/shared/commonBuildBlocks';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { PasswordPatternValidator } from 'src/app/shared/commonBuildBlocks/validators/password-pattern.validator';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
})
export class ChangePasswordComponent implements OnInit {
  @ViewChild('changePasswordRef') changePasswordRef: TemplateRef<any>;
  form: FormGroup;
  hideOldPassword = true;
  hidePassword = true;
  hideReEnterPassword = true;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private toastarMessage: ToastMessageService,
    private router: Router,
    private dataPassingService: DataPassingService,
    private spinner: NgxSpinnerService,
    private dialog: MatDialog,
  ) {}

  ngOnInit(): void {
    this.formValidation();
  }

  formValidation() {
    this.form = this.fb.group({
      oldPassword: ['', [Validators.required]],
      newPassword: [
        '',
        [Validators.required, Validators.minLength(8), PasswordPatternValidator.strong, Validators.maxLength(25)],
      ],
      confirmPassword: ['', [Validators.required, this.reEnterPasswordValidator.bind(this)]],
    });

    this.form
      .get('newPassword')
      .valueChanges.pipe(debounceTime(500))
      .subscribe((value) => {
        this.form.get('confirmPassword').updateValueAndValidity();
      });
  }

  updatePassword() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.toastarMessage.showErrorMsg('Fill Correct Details', 'Error');
    } else {
      const { oldPassword, newPassword, confirmPassword } = this.form.value;
      const payload = {
        userId: this.dataPassingService.userId,
        oldPwd: oldPassword,
        newPwd: newPassword,
        confirmPwd: confirmPassword,
      } as ChangePasswordRequest;
      // this.changePasswordRedirect('result.response?.message');
      this.authService.changePassword(payload).subscribe(
        (result) => {
          this.spinner.hide();
          if (result.status) {
            this.changePasswordRedirect(result.response?.message);
          } else {
            this.toastarMessage.showErrorMsg(result.errors[0].message, 'Error');
          }
        },
        (error) => {
          this.spinner.hide();
          this.toastarMessage.showErrorMsg(error.errors[0].message, 'Error');
        },
      );
    }
  }

  private changePasswordRedirect(message: any) {
    this.dialog
      .open(this.changePasswordRef, {
        width: '500px',
        data: message,
      })
      .afterClosed()
      .subscribe(() => {
        this.logout();
      });
  }
  reset() {
    this.form.reset();
    this.form.markAsPristine();
  }

  private reEnterPasswordValidator(contorl: FormControl) {
    if (!contorl || !contorl.parent) {
      return null;
    }
    let group = contorl.parent;
    let newPass = group.get('newPassword').value;
    if (newPass && contorl.value) {
      if (newPass != contorl.value) {
        return { passwordMismatch: true };
      }
    }
  }

  private logout() {
    this.authService.signOut().subscribe();
    sessionStorage.clear();
    this.router.navigate(['sign-in']);
  }
}
