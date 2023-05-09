import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'src/app/shared/autherization_Authentication/guards/auth.guard';
import { ConsultationStep } from 'src/app/shared/commonBuildBlocks/enum/consultation-steps.enum';
import { RoutesConstant } from 'src/app/shared/commonBuildBlocks/enum/routes.enum';
import { TmConsultationComponent } from './tm-consultation/tm-consultation.component';
import { TmPrescriptionDetailsComponent } from './tm-prescription-details/tm-prescription-details.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: TmConsultationComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TmConsultationRoutingModule { }
