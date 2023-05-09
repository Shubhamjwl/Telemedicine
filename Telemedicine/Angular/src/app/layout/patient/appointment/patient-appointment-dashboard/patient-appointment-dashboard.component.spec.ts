import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientAppointmentDashboardComponent } from './patient-appointment-dashboard.component';

describe('PatientAppointmentDashboardComponent', () => {
  let component: PatientAppointmentDashboardComponent;
  let fixture: ComponentFixture<PatientAppointmentDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientAppointmentDashboardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientAppointmentDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
