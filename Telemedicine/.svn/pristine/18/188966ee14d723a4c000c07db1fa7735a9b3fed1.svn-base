import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'src/app/shared/autherization_Authentication/guards/auth.guard';
import { TmDoctorAppointmentsDashboardComponent } from '../doctor/appointment/tm-doctor-appointments-dashboard/tm-doctor-appointments-dashboard.component';
import { PatientAppointmentDashboardComponent } from '../patient/appointment/patient-appointment-dashboard/patient-appointment-dashboard.component';
import { PtDocumentManagementComponent } from '../patient/components/pt-document-management/pt-document-management.component';
import { AppointmentHistoryComponent } from './components/appointment-history/appointment-history.component';
import { BookAppointmentDashboardComponent } from './components/book-appointment-dashboard/book-appointment-dashboard.component';
import { BookDoctorsAppointmentComponent } from './components/book-doctor-appointment/book-doctor-appointment.component';
import { CanceledAppointmentComponent } from './components/canceled-appointment/canceled-appointment.component';
import { CompletedAppointmentsComponent } from './components/completed-appointments/completed-appointments.component';
import { DeActivateProfileComponent } from './components/de-activate-profile/de-activate-profile.component';
import { OrderHistoryComponent } from './components/order-history/order-history.component';
import { PrescribeServiceComponent } from './components/prescribe-service/prescribe-service.component';
import { RescheduleBookAppointmentComponent } from './components/reschedule-book-appointment/reschedule-book-appointment.component';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';
import { UploadHeaderFooterTemplateComponent } from './components/upload-header-footer-template/upload-header-footer-template.component';
import { ViewPatientListComponent } from './components/view-patient-list/view-patient-list.component';

const routes: Routes = [
  {
    path: 'doctor-appointments',
    component: BookDoctorsAppointmentComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'book-appointment-dashboard',
    component: BookAppointmentDashboardComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'appointments-history',
    component: AppointmentHistoryComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'completed-appointments',
    component: CompletedAppointmentsComponent,
    canActivate: [AuthGuard],
  },

  {
    path: 'canceled-appointments',
    component: CanceledAppointmentComponent,
    canActivate: [AuthGuard],
  },

  {
    path: 'reschedule-book-appointments',
    component: PatientAppointmentDashboardComponent,
    canActivate: [AuthGuard],
  },

  {
    path: 'doctor-reschedule-book-appointments',
    component: TmDoctorAppointmentsDashboardComponent,
    canActivate: [AuthGuard],
  },

  {
    path: 'patient-list',
    component: ViewPatientListComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'update-profile',
    component: UpdateProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'doctor-deregister',
    component: DeActivateProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'upload-header-footer-template',
    component: UploadHeaderFooterTemplateComponent,
    canActivate: [AuthGuard],
  },

  {
    path: 'orderHistory',
    component: OrderHistoryComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'document-management',
    component: PtDocumentManagementComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'prescribeService',
    component: PrescribeServiceComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SharedModulesRoutingModule { }
