import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Docfilter } from './commonBuildBlocks/pipes/search.pipe';
import { CustomValidators } from './commonBuildBlocks/services/Validators/customValidator.service';
import { NumberOnlyDirective } from './commonBuildBlocks/derectives/common.directive';



@NgModule({
  declarations: [Docfilter, NumberOnlyDirective],
  imports: [
    CommonModule,
  ], 
  exports: [Docfilter, NumberOnlyDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SharedModule { }
