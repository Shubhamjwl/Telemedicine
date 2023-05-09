import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientKYCpComponent } from './patient-kycp.component';

describe('PatientKYCpComponent', () => {
  let component: PatientKYCpComponent;
  let fixture: ComponentFixture<PatientKYCpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientKYCpComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientKYCpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
