import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserManagementRoutingModule } from './user-management-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ChangePasswordComponent } from './change-password/change-password/change-password.component';
import { TmResetChangePasswordComponent } from './change-password/tm-reset-change-password/tm-reset-change-password.component';
import { ResetChangePasswordComponent } from './change-password/reset-change-password/reset-change-password.component';
import { SharedModulesModule } from '../layout/shared-modules/shared-modules.module';

@NgModule({
  declarations: [ChangePasswordComponent, TmResetChangePasswordComponent, ResetChangePasswordComponent],
  imports: [
    CommonModule,
    SharedModulesModule,
    UserManagementRoutingModule
  ]
})
export class UserManagementModule { }
