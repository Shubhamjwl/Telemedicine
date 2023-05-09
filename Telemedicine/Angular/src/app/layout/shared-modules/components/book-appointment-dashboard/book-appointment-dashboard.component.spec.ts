import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookAppointmentDashboardComponent } from './book-appointment-dashboard.component';

describe('BookAppointmentDashboardComponent', () => {
  let component: BookAppointmentDashboardComponent;
  let fixture: ComponentFixture<BookAppointmentDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookAppointmentDashboardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookAppointmentDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
