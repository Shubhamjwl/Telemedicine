import { Directive, HostListener, Renderer2, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[expand-width]',
})
export class ExpandWidthDirective {
  constructor(private renderer: Renderer2,public elementRef: ElementRef) {}
  @Input('expand-width') isExpanded: any;

  @HostListener('mouseenter') onMouseEnter() {
      if(this.isExpanded === false) {
            this.renderer.setStyle(
                this.elementRef.nativeElement,
                'width',
                '259px'
            );
        }
        else {
            this.renderer.setStyle(
                this.elementRef.nativeElement,
                'width',
                'auto'
            );
        }
  }

  @HostListener('mouseleave') onMouseLeave() {
      if(this.isExpanded === false) {
            this.renderer.setStyle(
                this.elementRef.nativeElement,
                'width',
                'auto'
            );
    } else {
        this.renderer.setStyle(
            this.elementRef.nativeElement,
            'width',
            'auto'
        );
    }
  }
}
