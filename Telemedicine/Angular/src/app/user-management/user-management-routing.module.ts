import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChangePasswordComponent } from './change-password/change-password/change-password.component';
import { TmResetChangePasswordComponent } from './change-password/tm-reset-change-password/tm-reset-change-password.component';

const routes: Routes = [
  {
    path: 'forgot-password',
    component: TmResetChangePasswordComponent
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserManagementRoutingModule { }
