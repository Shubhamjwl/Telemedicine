import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TmPrescriptionDetailsComponent } from './tm-prescription-details.component';

describe('TmPrescriptionDetailsComponent', () => {
  let component: TmPrescriptionDetailsComponent;
  let fixture: ComponentFixture<TmPrescriptionDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TmPrescriptionDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TmPrescriptionDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
