import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelfPatientRegisteredNextComponent } from './self-patient-registered-next.component';

describe('SelfPatientRegisteredNextComponent', () => {
  let component: SelfPatientRegisteredNextComponent;
  let fixture: ComponentFixture<SelfPatientRegisteredNextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelfPatientRegisteredNextComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelfPatientRegisteredNextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
