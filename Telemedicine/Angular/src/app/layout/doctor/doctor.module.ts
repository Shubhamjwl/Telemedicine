import { TmCreateSlotDashboardComponent } from './slots/tm-create-slot-dashboard/tm-create-slot-dashboard.component';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { DoctorRoutingModule } from './doctor-routing.module';
import { DoctorAppointmentsDashboardComponent } from './appointment/doctor-appointments-dashboard/doctor-appointments-dashboard.component';
import { CreateSlotComponent } from './slots/create-slot/create-slot.component';
import { SharedModulesModule } from '../shared-modules/shared-modules.module';
import { BlockUnblockHolidayComponent } from './slots/block-unblock-holiday/block-unblock-holiday.component';
import { ModifyTimeSlotComponent } from './slots/modify-time-slot/modify-time-slot.component';
import { ScribeStatusComponent } from './Scribe/scribe-status/scribe-status.component';
import { AssignScribeComponent } from './Scribe/assign-scribe/assign-scribe.component';
import { TmDoctorAppointmentsDashboardComponent } from './appointment/tm-doctor-appointments-dashboard/tm-doctor-appointments-dashboard.component';
import { TmCreateSlotComponent } from './slots/tm-create-slot/tm-create-slot.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { DiagnosticSupportComponent } from '../shared-modules/components/diagnostic-support/diagnostic-support.component';

@NgModule({
  declarations: [
    DoctorAppointmentsDashboardComponent,
    TmDoctorAppointmentsDashboardComponent,
    CreateSlotComponent,
    TmCreateSlotComponent,
    TmCreateSlotDashboardComponent,
    BlockUnblockHolidayComponent,
    ModifyTimeSlotComponent,
    ScribeStatusComponent,
    AssignScribeComponent,
    DiagnosticSupportComponent
  ],
  imports: [
    CommonModule,
    DoctorRoutingModule,
    SharedModulesModule,
    SharedModule,
  ],
  providers: [DatePipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class DoctorModule {}
