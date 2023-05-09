import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Docfilter } from './commonBuildBlocks/pipes/search.pipe';
import { NumberOnlyDirective } from './commonBuildBlocks/derectives/common.directive';
import { NgxSpinnerModule } from 'ngx-spinner';
import { BreadcrumbModule } from './modules/breadcrumb/breadcrumb.module';
import { MatModuleModule } from '../layout/mat-module/mat-module.module';
import { LangModule } from '../lang/lang.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WebcamModule } from 'ngx-webcam';
import { CalendarModule } from 'angular-calendar';
import { AvatarModule } from 'ngx-avatar';
import { ClipboardModule } from '@angular/cdk/clipboard';
import { QuillModule } from 'ngx-quill';

const modules = [
  NgxSpinnerModule,
  BreadcrumbModule,
  MatModuleModule,
  LangModule,
  FormsModule,
  ReactiveFormsModule,
  WebcamModule,
  CalendarModule,
  AvatarModule,
  NgbModule,
  ClipboardModule,
  QuillModule,
];

@NgModule({
  declarations: [Docfilter, NumberOnlyDirective],
  imports: [CommonModule, ...modules],
  exports: [...modules, Docfilter, NumberOnlyDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SharedModule { }
