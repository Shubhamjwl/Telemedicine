import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { CheckerRoutingModule } from './checker-routing.module';
import { LangModule } from 'src/app/lang/lang.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxSpinnerModule } from 'ngx-spinner';
import { BreadcrumbModule } from 'src/app/shared/modules/breadcrumb/breadcrumb.module';
import { CommingSoonModule } from 'src/app/comming-soon/comming-soon.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatTableModule } from '@angular/material/table';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatExpansionModule } from '@angular/material/expansion';
import { Docfilter } from 'src/app/shared/commonBuildBlocks/pipes/search.pipe';
import { DoctorDetailsComponent } from './checker-dashboard/doctor-details/doctor-details.component';
import { CheckerDashboardComponent } from './checker-dashboard/checker-dashboard.component';
import { CheckerDocDeRegisterComponent } from './checker-doc-deregister/checker-doc-deregister.component';
import { BulkUploadDoctorComponent } from './bulk-upload-doctor/bulk-upload-doctor.component';
import { CheckerDocCheckListComponent } from './checker-doc-check-list/checker-doc-check-list.component';
import { ConfigDoctorListComponent } from './config-doctor-list/config-doctor-list.component';


@NgModule({
	declarations: [
		CheckerDashboardComponent,
		DoctorDetailsComponent,
		CheckerDocDeRegisterComponent,
		BulkUploadDoctorComponent,
		CheckerDocCheckListComponent,
		ConfigDoctorListComponent,
	],
	imports: [
		CommonModule,
		CheckerRoutingModule,
		LangModule,
		NgxSpinnerModule,
		FormsModule,
		ReactiveFormsModule,
		BreadcrumbModule,
		CommingSoonModule,
		SharedModule,

		// Mat
		MatCardModule,
		MatInputModule,
		MatButtonModule,
		MatDialogModule,
		MatSelectModule,
		MatFormFieldModule,
		MatCheckboxModule,
		MatAutocompleteModule,
		MatTableModule,
		MatDatepickerModule,
		MatExpansionModule
		// MatNativeDateModule
	],
	providers: [DatePipe, Docfilter],
	schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class CheckerModule { }
