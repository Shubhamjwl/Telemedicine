import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResendOTPComponent } from './resend-otp.component';

describe('ResendOTPComponent', () => {
  let component: ResendOTPComponent;
  let fixture: ComponentFixture<ResendOTPComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResendOTPComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResendOTPComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
