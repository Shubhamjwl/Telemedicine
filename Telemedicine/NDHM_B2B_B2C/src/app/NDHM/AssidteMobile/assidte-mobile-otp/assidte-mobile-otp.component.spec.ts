import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssidteMobileOTPComponent } from './assidte-mobile-otp.component';

describe('AssidteMobileOTPComponent', () => {
  let component: AssidteMobileOTPComponent;
  let fixture: ComponentFixture<AssidteMobileOTPComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssidteMobileOTPComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssidteMobileOTPComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
