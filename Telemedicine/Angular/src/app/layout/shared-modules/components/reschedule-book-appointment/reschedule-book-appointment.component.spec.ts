import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RescheduleBookAppointmentComponent } from './reschedule-book-appointment.component';

describe('RescheduleBookAppointmentComponent', () => {
  let component: RescheduleBookAppointmentComponent;
  let fixture: ComponentFixture<RescheduleBookAppointmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RescheduleBookAppointmentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RescheduleBookAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
