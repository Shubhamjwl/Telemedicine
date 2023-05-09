import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { SharedModulesModule } from '../shared-modules/shared-modules.module';
import { AdminComponent } from './admin.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';

@NgModule({
  declarations: [AdminComponent, AdminDashboardComponent],
  imports: [CommonModule, AdminRoutingModule, SharedModulesModule],
})
export class AdminModule { }
