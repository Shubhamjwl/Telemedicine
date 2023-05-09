import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TmLoginComponent } from 'src/app/login/tm-login/tm-login.component';
import { PrivacyPolicyComponent } from './privacy-policy/privacy-policy.component';
import { TermsOfUseComponent } from './terms-of-use/terms-of-use.component';
import { TmProgramOverviewComponent } from './tm-program-overview/tm-program-overview.component';
import { TmResourceComponent } from './tm-resource/tm-resource.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: TmLoginComponent
  },
  {
    path: 'privacyPolicy',
    component: PrivacyPolicyComponent,
  },
  {
    path: 'termsofUse',
    component: TermsOfUseComponent,
  },
  {
    path: 'reSource',
    component: TmResourceComponent,
  },
  {
    path: 'programOverview',
    component: TmProgramOverviewComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SignInRoutingModule { }
