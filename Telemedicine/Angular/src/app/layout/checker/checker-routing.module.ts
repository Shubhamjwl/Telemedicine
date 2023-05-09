import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'src/app/shared/autherization_Authentication/guards/auth.guard';
import { BulkUploadDoctorComponent } from './bulk-upload-doctor/bulk-upload-doctor.component';
import { CheckerDashboardComponent } from './checker-dashboard/checker-dashboard.component';
import { CheckerDocCheckListComponent } from './checker-doc-check-list/checker-doc-check-list.component';
import { CheckerDocDeRegisterComponent } from './checker-doc-deregister/checker-doc-deregister.component';
import { ConfigDoctorListComponent } from './config-doctor-list/config-doctor-list.component';

const routes: Routes = [
	{
		path: '',
		component: CheckerDashboardComponent,
		canActivate: [AuthGuard]
	},
	{
		path: 'doc-de-register',
		component: CheckerDocDeRegisterComponent,
		canActivate: [AuthGuard]
	},
	{
		path: 'bulk-upload-doc',
		component: BulkUploadDoctorComponent,
		canActivate: [AuthGuard]
	},
	{
		path: 'check-list-doc',
		component: CheckerDocCheckListComponent,
		canActivate: [AuthGuard]
	},
	{
		path: 'config-doc-list',
		component: ConfigDoctorListComponent,
		canActivate: [AuthGuard]
	},
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule]
})
export class CheckerRoutingModule { }
