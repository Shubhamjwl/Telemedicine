import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelfOtpComponent } from './self-otp.component';

describe('SelfOtpComponent', () => {
  let component: SelfOtpComponent;
  let fixture: ComponentFixture<SelfOtpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelfOtpComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelfOtpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
