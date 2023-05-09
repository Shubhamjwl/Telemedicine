import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorAppointmentsDashboardComponent } from './doctor-appointments-dashboard.component';

describe('DoctorAppointmentsDashboardComponent', () => {
  let component: DoctorAppointmentsDashboardComponent;
  let fixture: ComponentFixture<DoctorAppointmentsDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorAppointmentsDashboardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorAppointmentsDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
