import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CommingSoonRoutingModule } from './comming-soon-routing.module';
import { CommingSoonComponent } from './comming-soon/comming-soon.component';
import { BreadcrumbModule } from '../shared/modules/breadcrumb/breadcrumb.module';


@NgModule({
  declarations: [CommingSoonComponent],
  imports: [
    CommonModule,
    CommingSoonRoutingModule,BreadcrumbModule
  ]
})
export class CommingSoonModule { }
