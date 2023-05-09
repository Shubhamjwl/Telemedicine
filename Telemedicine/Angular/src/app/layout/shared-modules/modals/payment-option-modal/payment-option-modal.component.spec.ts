import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentOptionModalComponent } from './payment-option-modal.component';

describe('PaymentOptionModalComponent', () => {
  let component: PaymentOptionModalComponent;
  let fixture: ComponentFixture<PaymentOptionModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaymentOptionModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentOptionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
