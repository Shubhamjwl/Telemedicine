import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: 'coming-soon', 
    loadChildren: () => import('../../../comming-soon/comming-soon.module').then(m => m.CommingSoonModule)
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BreadcrumbRoutingModule { }
