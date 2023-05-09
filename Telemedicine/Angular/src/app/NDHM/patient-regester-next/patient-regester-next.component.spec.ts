import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientRegesterNextComponent } from './patient-regester-next.component';

describe('PatientRegesterNextComponent', () => {
  let component: PatientRegesterNextComponent;
  let fixture: ComponentFixture<PatientRegesterNextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientRegesterNextComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientRegesterNextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
