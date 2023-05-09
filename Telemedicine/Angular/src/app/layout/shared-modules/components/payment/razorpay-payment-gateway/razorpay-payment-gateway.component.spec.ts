import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RazorpayPaymentGatewayComponent } from './razorpay-payment-gateway.component';

describe('RazorpayPaymentGatewayComponent', () => {
  let component: RazorpayPaymentGatewayComponent;
  let fixture: ComponentFixture<RazorpayPaymentGatewayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RazorpayPaymentGatewayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RazorpayPaymentGatewayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
