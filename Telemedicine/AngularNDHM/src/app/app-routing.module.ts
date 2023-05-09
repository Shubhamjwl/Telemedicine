import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AadharMobileOtpComponent } from './NDHM/AadharMobile-create/aadhar-mobile-otp/aadhar-mobile-otp.component';
import { AadharMobileRegesterNextComponent } from './NDHM/AadharMobile-create/aadhar-mobile-regester-next/aadhar-mobile-regester-next.component';
import { AadharMobileRegesterComponent } from './NDHM/AadharMobile-create/aadhar-mobile-regester/aadhar-mobile-regester.component';
import { AadharMobileYesNoMessageComponent } from './NDHM/AadharMobile-create/aadhar-mobile-yes-no-message/aadhar-mobile-yes-no-message.component';
import { AadharmobileEnterMobileComponent } from './NDHM/AadharMobile-create/aadharmobile-enter-mobile/aadharmobile-enter-mobile.component';
import { AsithealthEnterMobileComponent } from './NDHM/AssidteAdhare/asithealth-enter-mobile/asithealth-enter-mobile.component';
import { AsithealthOtpComponent } from './NDHM/AssidteAdhare/asithealth-otp/asithealth-otp.component';
import { AsithealthRegesterNextComponent } from './NDHM/AssidteAdhare/asithealth-regester-next/asithealth-regester-next.component';
import { AsithealthRegesterComponent } from './NDHM/AssidteAdhare/asithealth-regester/asithealth-regester.component';
import { AsithealthYesNoMessageComponent } from './NDHM/AssidteAdhare/asithealth-yes-no-message/asithealth-yes-no-message.component';
import { AssidteEnterMobileComponent } from './NDHM/AssidteMobile/assidte-enter-mobile/assidte-enter-mobile.component';
import { AssidteMobileOTPComponent } from './NDHM/AssidteMobile/assidte-mobile-otp/assidte-mobile-otp.component';
import { AssidteMobileRegistrationComponent } from './NDHM/AssidteMobile/assidte-mobile-registration/assidte-mobile-registration.component';
import { AssidteMobileResendComponent } from './NDHM/AssidteMobile/assidte-mobile-resend/assidte-mobile-resend.component';
import { AssidteMobileVerifyComponent } from './NDHM/AssidteMobile/assidte-mobile-verify/assidte-mobile-verify.component';
import { HealthidCreationMobileAssistedComponent } from './NDHM/AssidteMobile/healthid-creation-mobile-assisted/healthid-creation-mobile-assisted.component';
import { EnterMobileComponent } from './NDHM/enter-mobile/enter-mobile.component';
import { HealthidIndexComponent } from './NDHM/healthid-index/healthid-index.component';
import { OTPComponent } from './NDHM/otp/otp.component';
import { PatientRegesterNextComponent } from './NDHM/patient-regester-next/patient-regester-next.component';
import { PatientRegisteredComponent } from './NDHM/patient-registered/patient-registered.component';
import { ResendOTPComponent } from './NDHM/resend-otp/resend-otp.component';

import { SelfEnterMobileComponent } from './NDHM/Self-create/self-enter-mobile/self-enter-mobile.component';
import { SelfOtpComponent } from './NDHM/Self-create/self-otp/self-otp.component';
import { SelfPatientRegisteredNextComponent } from './NDHM/Self-create/self-patient-registered-next/self-patient-registered-next.component';
import { SelfPatientRegisteredComponent } from './NDHM/Self-create/self-patient-registered/self-patient-registered.component';
import { SelfResendOtpComponent } from './NDHM/Self-create/self-resend-otp/self-resend-otp.component';
import { SelfVerifyMobileComponent } from './NDHM/Self-create/self-verify-mobile/self-verify-mobile.component';
import { VerifyMobileComponent } from './NDHM/verify-mobile/verify-mobile.component';
import { VerfyAadharAutherComponent } from './NDHM/virifyAadharOtpe/verfy-aadhar-auther/verfy-aadhar-auther.component';
import { VerfyAadhareKYCComponent } from './NDHM/virifyAadharOtpe/verfy-aadhare-kyc/verfy-aadhare-kyc.component';
import { VerfyAadhareOTPComponent } from './NDHM/virifyAadharOtpe/verfy-aadhare-otp/verfy-aadhare-otp.component';
import { VerfyAadhareProfileComponent } from './NDHM/virifyAadharOtpe/verfy-aadhare-profile/verfy-aadhare-profile.component';
import { VerfyDemoghraphyMapComponent } from './NDHM/virifyeDemoghraphy/verfy-demoghraphy-map/verfy-demoghraphy-map.component';
import { VerfyDemoghraphyProfilerComponent } from './NDHM/virifyeDemoghraphy/verfy-demoghraphy-profiler/verfy-demoghraphy-profiler.component';
import { PatienAutherComponent } from './NDHM/visithelthmobile/patien-auther/patien-auther.component';
import { PatientKYCpComponent } from './NDHM/visithelthmobile/patient-kycp/patient-kycp.component';
import { VerfymobileOTPComponent } from './NDHM/visithelthmobile/verfymobile-otp/verfymobile-otp.component';
import { VerfymobileprofileComponent } from './NDHM/visithelthmobile/verfymobileprofile/verfymobileprofile.component';
import { FatchprofileMapComponent } from './NDHM/visithelthQR/fatchprofile-map/fatchprofile-map.component';
import { ProfileCreateComponent } from './NDHM/visithelthQR/profile-create/profile-create.component';
import { YesNoMessaeComponent } from './NDHM/yes-no-messae/yes-no-messae.component';
//import { HealthidDeleteComponent } from './NDHM/healthId-delete/healthId-delete.component';

const routes: Routes = [
  // {
  //   path: '',
  //   loadChildren: () =>
  //     import('./layout/layout.module').then((m) => m.LayoutModule),
  // },
  // {
  //   path: 'doctor-details',
  //   loadChildren: () =>
  //     import('./book-appointmnet-url/book-appointmnet-url.module').then(
  //       (m) => m.BookAppointmnetUrlModule
  //     ),
  // },
  // { path: '**', redirectTo:'', pathMatch: 'full' },
  { path: '', component: HealthidIndexComponent },
  { path: 'healthidMobile', component: EnterMobileComponent },
  // { path: 'healthidDelete', component: HealthidDeleteComponent },
  { path: 'otp', component: OTPComponent },
  { path: 'verfymobile', component: VerifyMobileComponent },
  { path: 'patientregister', component: PatientRegisteredComponent },
  { path: 'resendotp', component: ResendOTPComponent },
  { path: 'Nextpatientregister', component: PatientRegesterNextComponent },
  { path: 'yesNomessage', component: YesNoMessaeComponent },
  { path: 'selfentermobile', component: SelfEnterMobileComponent },
  { path: 'selfotp', component: SelfOtpComponent },
  { path: 'selfpatientreg', component: SelfPatientRegisteredComponent },
  { path: 'selfpatientregnxt', component: SelfPatientRegisteredNextComponent },
  { path: 'selfresentotp', component: SelfResendOtpComponent },
  { path: 'selfvarifymobile', component: SelfVerifyMobileComponent },
  {
    path: 'AadharmobileEnterMobile',
    component: AadharmobileEnterMobileComponent,
  },
  { path: 'AadharMobileOtp', component: AadharMobileOtpComponent },
  { path: 'AadharMobileRegester', component: AadharMobileRegesterComponent },
  {
    path: 'AadharMobileRegesterNext',
    component: AadharMobileRegesterNextComponent,
  },
  {
    path: 'AadharMobileYesNoMessage',
    component: AadharMobileYesNoMessageComponent,
  },

  {
    path: 'AssistedHealthidCreationMobile',
    component: HealthidCreationMobileAssistedComponent,
  },
  {
    path: 'AssistedEnterMobile',
    component: AssidteEnterMobileComponent,
  },
  {
    path: 'AssistedMobileOTP',
    component: AssidteMobileOTPComponent,
  },
  {
    path: 'AssistedMobileVerify',
    component: AssidteMobileVerifyComponent,
  },
  {
    path: 'AssidteMobileResend',
    component: AssidteMobileResendComponent,
  },
  {
    path: 'AssistedMobileRegistration',
    component: AssidteMobileRegistrationComponent,
  },

  {
    path: 'AsithealthEnterMobile',
    component: AsithealthEnterMobileComponent,
  },
  {
    path: 'AsithealthOtp',
    component: AsithealthOtpComponent,
  },
  {
    path: 'AsithealthRegester',
    component: AsithealthRegesterComponent,
  },
  {
    path: 'AsithealthRegesterNext',
    component: AsithealthRegesterNextComponent,
  },
  {
    path: 'AsithealthYesNoMessage',
    component: AsithealthYesNoMessageComponent,
  },
  {
    path: 'ProfileCreate',
    component: ProfileCreateComponent,
  },
  {
    path: 'FatchprofileMap',
    component: FatchprofileMapComponent,
  },
  {
    path: 'PatienAuther',
    component: PatienAutherComponent,
  },
  {
    path: 'PatientKYCp',
    component: PatientKYCpComponent,
  },
  {
    path: 'Verfymobileprofile',
    component: VerfymobileprofileComponent,
  },
  {
    path: 'VerfymobileOtp',
    component: VerfymobileOTPComponent,
  },
  {
    path: 'VerfyAadharAuther',
    component: VerfyAadharAutherComponent,
  },
  {
    path: 'VerfyAadhareKYC',
    component: VerfyAadhareKYCComponent,
  },
  {
    path: 'VerfyAadhareProfile',
    component: VerfyAadhareProfileComponent,
  },
  {
    path: 'VerfyAadhareOTP',
    component: VerfyAadhareOTPComponent,
  },
  {
    path: 'VerfyDemoghraphyProfile',
    component: VerfyDemoghraphyProfilerComponent,
  },
  {
    path: 'VerfyDemoghraphyMap',
    component: VerfyDemoghraphyMapComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
