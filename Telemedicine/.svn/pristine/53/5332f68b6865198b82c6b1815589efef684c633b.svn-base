import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { PatientRoutingModule } from './patient-routing.module';
import { PatientAppointmentDashboardComponent } from './appointment/patient-appointment-dashboard/patient-appointment-dashboard.component';
import { LangModule } from 'src/app/lang/lang.module';
import { SharedModulesModule } from '../shared-modules/shared-modules.module';
import { NgxSpinnerModule } from 'ngx-spinner';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BreadcrumbModule } from 'src/app/shared/modules/breadcrumb/breadcrumb.module';
import { CommingSoonModule } from 'src/app/comming-soon/comming-soon.module';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatStepperModule } from '@angular/material/stepper';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatTableModule } from '@angular/material/table';
import { MatRadioModule } from '@angular/material/radio';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDividerModule } from '@angular/material/divider';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { CalendarModule } from 'angular-calendar';
import { AvatarModule } from 'ngx-avatar';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { AutocompleteLibModule } from 'angular-ng-autocomplete';
import { NgbModule  } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [PatientAppointmentDashboardComponent,],
  imports: [
      CommonModule,
      PatientRoutingModule,
      LangModule,
      SharedModulesModule,
      NgxSpinnerModule,
      FormsModule,
      ReactiveFormsModule,
      BreadcrumbModule,
      CommingSoonModule,

      // Mat
      MatTabsModule,
      MatCardModule,
      MatInputModule,
      MatButtonModule,
      MatIconModule,
      MatDialogModule,
      MatSelectModule,
      MatFormFieldModule,
      MatStepperModule,
      MatExpansionModule,
      MatCheckboxModule,
      MatChipsModule,
      MatAutocompleteModule,
      MatTableModule,
      MatRadioModule,
      MatPaginatorModule,
      MatDividerModule,
      MatDatepickerModule,
      // MatNativeDateModule

      //other
      NgbModule,
      CalendarModule,
      AvatarModule,
      AutocompleteLibModule,
      CarouselModule.forRoot()
  ],
  providers: [DatePipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PatientModule { }
