import { HostListener } from '@angular/core';
import { Directive, ElementRef } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
    selector: '[numberOnly]',
    inputs: ['length'],
})
export class NumberOnlyDirective {
    private length: number;
    constructor(
        public el: ElementRef,
        private control: NgControl
    ){ }
    @HostListener('blur', ['$event.target.value']) onBlur(value){
          var re  = new RegExp('[!\\$\\^<>]', 'g');
          value = value.replace(re,'');
          this.el.nativeElement.value = value;

          this.control.control.setValue(value, {
              emitEvent: false,
              emitModelToViewChnage: false,
              emitViewToModelChange: false
          });
    }
    @HostListener('input', ['$event']) onInputChange(event){
        const initialValue = this.el.nativeElement.value;
        let newValue = initialValue;
    
        var re  = new RegExp('[!\\$\\^<>]', 'g');
        newValue = newValue.replace(re,'');
    
        newValue = newValue.replace(/[^0-9]*/g,'');
    
        if(this.length && newValue.length > this.length){
            newValue = newValue.substring(0, this.length)
        }
        this.el.nativeElement.value = newValue;
        this.control.control.setValue(newValue);
    
        if(initialValue !== this.el.nativeElement.value){
            event.stopPropagation()
        }
    
    }
}
 


