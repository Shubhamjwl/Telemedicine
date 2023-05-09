import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { LayoutRoutingModule } from './layout-routing.module';
import { LayoutComponent } from './layout.component';
import { MatModuleModule } from './mat-module/mat-module.module';
import { SignInModule } from './sign-in/sign-in.module';
import { UserManagementModule } from '../user-management/user-management.module';

@NgModule({
  declarations: [LayoutComponent],
  imports: [
    CommonModule,
    LayoutRoutingModule,
    MatModuleModule,
    SignInModule,
    UserManagementModule
  ],
  providers: [DatePipe],
  exports: [MatModuleModule]
})
export class LayoutModule { }
