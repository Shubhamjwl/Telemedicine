import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookDoctorsAppointmentComponent } from './book-doctor-appointment.component';

describe('BookDoctorsAppointmentComponent', () => {
  let component: BookDoctorsAppointmentComponent;
  let fixture: ComponentFixture<BookDoctorsAppointmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookDoctorsAppointmentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookDoctorsAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
