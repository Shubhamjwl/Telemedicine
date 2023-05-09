import { SharedModulesModule } from './../../shared-modules/shared-modules.module';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { RegisterPatientRoutingModule } from './register-patient-routing.module';
import { PatientRegistrationByDocSCComponent } from './patient-registration-by-doc-sc/patient-registration-by-doc-sc.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { TmPatientRegistrationByDocSCComponent } from './tm-patient-registration-by-doc-sc/tm-patient-registration-by-doc-sc.component';
import { AbhaTmPatientVerifyComponent } from './tm-patient-registration-by-doc-sc/abha-tm-patient-verify/abha-tm-patient-verify.component';
import { FormatTimePipePipe } from './tm-patient-registration-by-doc-sc/format-time-pipe.pipe';

@NgModule({
  declarations: [
    PatientRegistrationByDocSCComponent,
    TmPatientRegistrationByDocSCComponent,
    AbhaTmPatientVerifyComponent,
    FormatTimePipePipe,
  ],
  imports: [
    CommonModule,
    RegisterPatientRoutingModule,
    SharedModule,
    SharedModulesModule,
  ],
  providers: [DatePipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class RegisterPatientModule {}
