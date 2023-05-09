import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CanceledAppointmentComponent } from './canceled-appointment.component';

describe('CanceledAppointmentComponent', () => {
  let component: CanceledAppointmentComponent;
  let fixture: ComponentFixture<CanceledAppointmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CanceledAppointmentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CanceledAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
