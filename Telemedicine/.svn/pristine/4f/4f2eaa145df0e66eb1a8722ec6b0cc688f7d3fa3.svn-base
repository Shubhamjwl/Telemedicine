import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'src/app/shared/autherization_Authentication/guards/auth.guard';
import { ConsultationStep } from 'src/app/shared/commonBuildBlocks/enum/consultation-steps.enum';
import { RoutesConstant } from 'src/app/shared/commonBuildBlocks/enum/routes.enum';
import { ConsultationPriscriptionComponent } from './consultation-priscription/consultation-priscription.component';
import { ConsultationComponent } from './consultation/consultation.component';
import { PrescriptionDetailsComponent } from './prescription-details/prescription-details.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultationComponent,
    canActivate: [AuthGuard],
    children: [
      {
          path: RoutesConstant.PRESCRIPTIONDETAILS,
          component: PrescriptionDetailsComponent,
          data: {type: ConsultationStep.PRESCRIPTIONDETAILS },
          canActivate: [AuthGuard],
      },
      {
          path: RoutesConstant.PRESCRIPTION,
          component: ConsultationPriscriptionComponent,
          data: { type: ConsultationStep.PRESCRIPTION },
          canActivate: [AuthGuard],
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConsultationRoutingModule { }
