import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbhaTmPatientVerifyComponent } from './abha-tm-patient-verify.component';

describe('AbhaTmPatientVerifyComponent', () => {
  let component: AbhaTmPatientVerifyComponent;
  let fixture: ComponentFixture<AbhaTmPatientVerifyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AbhaTmPatientVerifyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AbhaTmPatientVerifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
