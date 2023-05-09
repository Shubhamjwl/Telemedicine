import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'src/app/shared/autherization_Authentication/guards/auth.guard';
import { ROUTE } from 'src/app/shared/constants/route.enum';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AdminComponent } from './admin.component';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: ROUTE.ADMIN_DASHBOARD,
        component: AdminDashboardComponent
      },
      {
        path: '',
        pathMatch: 'full',
        redirectTo: ROUTE.ADMIN_DASHBOARD
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
