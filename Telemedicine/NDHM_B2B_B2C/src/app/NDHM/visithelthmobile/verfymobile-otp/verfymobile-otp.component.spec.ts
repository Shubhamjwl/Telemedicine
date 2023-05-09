import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerfymobileOTPComponent } from './verfymobile-otp.component';

describe('VerfymobileOTPComponent', () => {
  let component: VerfymobileOTPComponent;
  let fixture: ComponentFixture<VerfymobileOTPComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VerfymobileOTPComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VerfymobileOTPComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
