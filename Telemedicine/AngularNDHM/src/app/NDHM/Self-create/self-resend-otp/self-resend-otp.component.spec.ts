import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelfResendOtpComponent } from './self-resend-otp.component';

describe('SelfResendOtpComponent', () => {
  let component: SelfResendOtpComponent;
  let fixture: ComponentFixture<SelfResendOtpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelfResendOtpComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelfResendOtpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
