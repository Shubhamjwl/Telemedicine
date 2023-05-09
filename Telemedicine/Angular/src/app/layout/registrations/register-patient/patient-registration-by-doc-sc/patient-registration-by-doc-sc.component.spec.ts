import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientRegistrationByDocSCComponent } from './patient-registration-by-doc-sc.component';

describe('PatientRegistrationByDocSCComponent', () => {
  let component: PatientRegistrationByDocSCComponent;
  let fixture: ComponentFixture<PatientRegistrationByDocSCComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientRegistrationByDocSCComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientRegistrationByDocSCComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
