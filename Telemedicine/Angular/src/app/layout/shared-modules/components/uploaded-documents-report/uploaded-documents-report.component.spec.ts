import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadedDocumentsReportComponent } from './uploaded-documents-report.component';

describe('UploadedDocumentsReportComponent', () => {
  let component: UploadedDocumentsReportComponent;
  let fixture: ComponentFixture<UploadedDocumentsReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadedDocumentsReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadedDocumentsReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
