import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelfPatientRegisteredComponent } from './self-patient-registered.component';

describe('SelfPatientRegisteredComponent', () => {
  let component: SelfPatientRegisteredComponent;
  let fixture: ComponentFixture<SelfPatientRegisteredComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelfPatientRegisteredComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelfPatientRegisteredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
