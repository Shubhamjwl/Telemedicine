import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientAppointmentUrlRoutingModule } from './patient-appointment-url-routing.module';
import { RegPatientUrlComponent } from './reg-patient-url/reg-patient-url.component';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { SharedModulesModule } from '../layout/shared-modules/shared-modules.module';
import { LangModule } from '../lang/lang.module';
import { NgxSpinnerModule } from 'ngx-spinner';

@NgModule({
  declarations: [RegPatientUrlComponent],
  imports: [
    CommonModule,
    PatientAppointmentUrlRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    SharedModulesModule,
    LangModule,
    NgxSpinnerModule,
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
      MatNativeDateModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatientAppointmentUrlModule { }
