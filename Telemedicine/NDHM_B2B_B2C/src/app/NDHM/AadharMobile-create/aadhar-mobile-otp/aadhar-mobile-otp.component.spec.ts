import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AadharMobileOtpComponent } from './aadhar-mobile-otp.component';

describe('AadharMobileOtpComponent', () => {
  let component: AadharMobileOtpComponent;
  let fixture: ComponentFixture<AadharMobileOtpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AadharMobileOtpComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AadharMobileOtpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
