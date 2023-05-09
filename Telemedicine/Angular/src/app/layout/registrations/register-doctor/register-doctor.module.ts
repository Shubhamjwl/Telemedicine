import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterDoctorRoutingModule } from './register-doctor-routing.module';
import { TmRegisterDoctorComponent } from './tm-register-doctor/tm-register-doctor.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { RegisterDoctorComponent } from './register-doctor/register-doctor.component';

@NgModule({
  declarations: [RegisterDoctorComponent, TmRegisterDoctorComponent],

  imports: [CommonModule, RegisterDoctorRoutingModule, SharedModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  entryComponents: [RegisterDoctorComponent, TmRegisterDoctorComponent],
})
export class RegisterDoctorModule {}
