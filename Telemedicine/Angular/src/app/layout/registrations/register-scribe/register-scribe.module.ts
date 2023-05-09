import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { RegisterScribeRoutingModule } from './register-scribe-routing.module';
import { RegisterScribeComponent } from './register-scribe/register-scribe.component';
import { SharedModulesModule } from '../../shared-modules/shared-modules.module';
import { NgxSpinnerModule } from 'ngx-spinner';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BreadcrumbModule } from 'src/app/shared/modules/breadcrumb/breadcrumb.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LangModule } from 'src/app/lang/lang.module';
import { WebcamModule } from 'ngx-webcam';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatStepperModule } from '@angular/material/stepper';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatTableModule } from '@angular/material/table';
import { MatRadioModule } from '@angular/material/radio';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDividerModule } from '@angular/material/divider';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { CalendarModule } from 'angular-calendar';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [RegisterScribeComponent],
  imports: [
    CommonModule,
    RegisterScribeRoutingModule,
    LangModule,WebcamModule,
    NgxSpinnerModule,
    FormsModule,
    ReactiveFormsModule,
    BreadcrumbModule,
    SharedModule,

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

    //other
    NgbModule,
    CalendarModule
  ],
  providers: [DatePipe],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA],
})
export class RegisterScribeModule { }
