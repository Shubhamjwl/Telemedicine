import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BulkUploadDoctorComponent } from './bulk-upload-doctor.component';

describe('BulkUploadDoctorComponent', () => {
  let component: BulkUploadDoctorComponent;
  let fixture: ComponentFixture<BulkUploadDoctorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BulkUploadDoctorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BulkUploadDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
