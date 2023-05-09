import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';
import { IAPIResponseWrapper, PtDocumentResponse, PtDocumentType, UploadDocument } from 'src/app/shared/commonBuildBlocks';
import { PatientDocumentService } from 'src/app/shared/commonBuildBlocks/services';
import { CheckerService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/checker.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-pt-document-management',
  templateUrl: './pt-document-management.component.html',
  styleUrls: ['./pt-document-management.component.scss'],
})
export class PtDocumentManagementComponent implements OnInit, OnDestroy {
  selectedFile: File;
  form: FormGroup;
  fileBase64: any;
  documentNameList: PtDocumentType[];
  uploadedDataList = new MatTableDataSource<PtDocumentResponse>();
  displayedColumns: string[] = ['documentName', 'documentType', 'dateofUpload', 'uploadedBy', 'action'];
  

  private subscriptions = new Subscription();

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private fb: FormBuilder,
    private spinner: NgxSpinnerService,
    private toastrMessage: ToastMessageService,
    private patientDocumentService: PatientDocumentService,
    private documentService: DocumentService,
  ) { }



  ngOnInit(): void {
    this.initForm();
    this.getDocumentTypes();
    this.getAllDocuments();
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  ngAfterViewInit() {
    this.uploadedDataList.paginator = this.paginator;
  }

  private initForm() {
    this.form = this.fb.group({
      documentName: [''],
      filesToUpload: [''],
    });
  }

  private getDocumentTypes() {
    this.patientDocumentService.getDocumentTypes().subscribe(
      (result) => {
        if (result.status && result.response) {
          this.documentNameList = result.response;
        } else if (result.errors) {
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      },
      (error) => {
        this.toastrMessage.showErrorMsg(error.error?.errors[0]?.message, 'Error');
      },
    );
  }

  uploadDocuments() {
    this.spinner.show();
    const { documentName } = this.form.value;
    const data = {
      docType: documentName,
      docName: this.selectedFile?.name,
      docData: this.fileBase64,
    } as UploadDocument;
    this.patientDocumentService.uploadPatientDocument(data).subscribe(
      (result) => {
        this.spinner.hide();
        if (result.status) {
          this.getAllDocuments();
          this.form.reset();
          this.form.markAsPristine();
          this.selectedFile = null;
          this.toastrMessage.showSuccessMsg(result.response?.msg, 'Success');
        } else {
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      },
      (error) => {
        this.spinner.hide();
        this.toastrMessage.showErrorMsg(error.error?.errors[0]?.message, 'Error');
      },
    );
  }

  private getAllDocuments() {
    this.spinner.show();
    this.patientDocumentService.getAllDocuments().subscribe(
      (result : IAPIResponseWrapper<PtDocumentResponse[]>) => {
        this.spinner.hide();
        if (result.response) {
          console.log("success in");
          this.uploadedDataList.data = result.response;
          setTimeout(() => {
            this.uploadedDataList.paginator = this.paginator;
          });
        } else if (result.errors) {
          console.log("eror in");
          this.uploadedDataList.data = [];
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      },
      (error) => {
        this.spinner.hide();
        console.log("eror in");
        this.toastrMessage.showErrorMsg(error.error?.errors[0]?.message, 'Error');
      },
    );
  }

  onFileSelected(event: any): void {
    this.selectedFile = event?.target?.files[0] ?? null;
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.fileBase64 = e.target.result;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  removeFile() {
    this.selectedFile = null;
    this.fileBase64 = null;
  }

  deletePatientDocument(id: number) {
    this.patientDocumentService.deletePatientDocument(id).subscribe({
      next: (result => {
        if (result.status) {
          this.getAllDocuments();
          this.toastrMessage.showSuccessMsg(result.response.msg, 'Success');
        } else {
          this.toastrMessage.showErrorMsg(result.errors[0]?.message, 'Error');
        }
      }),
      error: (err => {
        this.toastrMessage.showErrorMsg(err.error?.errors[0]?.message, 'Error');
      })
    });
  }

  download(path: string) {
    this.documentService.downloadDocumentFromServer(path);
  }
}
