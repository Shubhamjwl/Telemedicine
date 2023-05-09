import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultationInitiationComponent } from './consultation-initiation.component';

describe('ConsultationInitiationComponent', () => {
  let component: ConsultationInitiationComponent;
  let fixture: ComponentFixture<ConsultationInitiationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultationInitiationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultationInitiationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
