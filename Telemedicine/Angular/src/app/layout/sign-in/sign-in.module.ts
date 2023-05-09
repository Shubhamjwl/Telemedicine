import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignInRoutingModule } from './sign-in-routing.module';
import { SignInComponent } from './sign-in/sign-in.component';
import { LoginModule } from 'src/app/login/login.module';
import { PrivacyPolicyComponent } from './privacy-policy/privacy-policy.component';
import { TmResourceComponent } from './tm-resource/tm-resource.component';
import { TmProgramOverviewComponent } from './tm-program-overview/tm-program-overview.component';

@NgModule({
  declarations: [SignInComponent, PrivacyPolicyComponent, TmResourceComponent, TmProgramOverviewComponent],
  imports: [CommonModule, SignInRoutingModule, LoginModule],
})
export class SignInModule {}
