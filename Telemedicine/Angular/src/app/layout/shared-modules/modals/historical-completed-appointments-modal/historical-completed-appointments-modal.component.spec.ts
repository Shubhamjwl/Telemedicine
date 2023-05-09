import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoricalCompletedAppointmentsModalComponent } from './historical-completed-appointments-modal.component';

describe('HistoricalCompletedAppointmentsModalComponent', () => {
  let component: HistoricalCompletedAppointmentsModalComponent;
  let fixture: ComponentFixture<HistoricalCompletedAppointmentsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HistoricalCompletedAppointmentsModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoricalCompletedAppointmentsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
