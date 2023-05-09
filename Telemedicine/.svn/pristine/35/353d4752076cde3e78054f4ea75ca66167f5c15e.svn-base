import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { AskToLogoutModalComponent } from './ask-to-logout-modal/ask-to-logout-modal.component';
import { TmLoginComponent } from './tm-login/tm-login.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [LoginComponent, TmLoginComponent, AskToLogoutModalComponent],
  imports: [CommonModule, SharedModule, RouterModule],
  exports: [LoginComponent, TmLoginComponent],
})
export class LoginModule {}
