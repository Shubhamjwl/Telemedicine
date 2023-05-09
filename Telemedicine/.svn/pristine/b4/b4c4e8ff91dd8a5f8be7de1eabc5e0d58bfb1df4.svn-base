import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HealthidIndexComponent } from 'src/app/NDHM/healthid-index/healthid-index.component';
import { AuthGuard } from 'src/app/shared/autherization_Authentication/guards/auth.guard';
import { PatientAppointmentDashboardComponent } from './appointment/patient-appointment-dashboard/patient-appointment-dashboard.component';

const routes: Routes = [
  {
      path: '', 
      component: PatientAppointmentDashboardComponent,
      canActivate: [AuthGuard],
  },
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule { }
