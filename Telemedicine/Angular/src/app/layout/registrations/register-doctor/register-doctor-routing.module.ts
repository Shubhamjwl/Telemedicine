import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterDoctorComponent } from './register-doctor/register-doctor.component';
import { TmRegisterDoctorComponent } from './tm-register-doctor/tm-register-doctor.component';

const routes: Routes = [
  {
    path: '',
    component: TmRegisterDoctorComponent,
    
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RegisterDoctorRoutingModule { }
