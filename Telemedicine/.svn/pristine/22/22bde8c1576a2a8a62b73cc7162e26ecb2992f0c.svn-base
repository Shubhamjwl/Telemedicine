import { NgxSpinnerService } from 'ngx-spinner';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { DomSanitizer } from '@angular/platform-browser';
import { ViewReportModalComponent } from 'src/app/layout/shared-modules/modals/view-report-modal/view-report-modal.component';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { CheckerService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/checker.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-tm-uploaded-documents-report',
  templateUrl: './tm-uploaded-documents-report.component.html',
  styleUrls: ['./tm-uploaded-documents-report.component.scss'],
})
export class TmUploadedDocumentsReportComponent implements OnInit {
  displayedColumns: string[] = ['reportname', 'view', 'download'];
  dataSource = new MatTableDataSource<any>();

  constructor(
    private dialog: MatDialog,
    private consultationService: ConsultationService,
    private sanitizer: DomSanitizer,
    private toastMessage: ToastMessageService,
    public authService: AuthService,
    public documentService: DocumentService,
    public checkerService: CheckerService,
    private spinner: NgxSpinnerService,
  ) {}

  ngOnInit(): void {
    this.getPatientUploadedReport();
  }

  getPatientUploadedReport() {
    this.spinner.show();
    this.consultationService.getPatientUploadedReport().subscribe({
      next: (result) => {
        this.spinner.hide();
        if (result.status) {
          this.dataSource = new MatTableDataSource(result.response);
        } else {
          this.toastMessage.showErrorMsg(result?.errors[0]?.message, 'Error');
        }
      },
      error: (err) => {
        this.spinner.hide();
        this.toastMessage.showErrorMsg(err?.errors?.message, 'Error');
      },
    });
  }

  openDialog(element) {
    const imagePath = this.sanitizer.bypassSecurityTrustResourceUrl(
      'data:' + element.mimetype + ';base64,' + element.report,
    );

    let reportPath = element.reportpath.replace('/var/telemedicine/', `${environment['baseUrl']}`);
    let reportPathUrl = this.sanitizer.bypassSecurityTrustResourceUrl(reportPath);
    let data = {
      imgPath: imagePath,
      imgUrl: reportPathUrl,
      reportName: 'Dummy',
    };
    const dialogRef = this.dialog.open(ViewReportModalComponent, {
      height: '100%',
      width: '100%',
      data: data,
    });
  }

  download(obj) {
    let data = {
      api: 'downoad document',
      request: {
        ddtDocsPath: obj.reportpath,
        ddtDocsType: obj.mimetype,
        drdUserId: this.authService.getUserId(),
      },
      mimeType: 'application/json',
    };

    this.checkerService.downloadDoc(data).subscribe((resp: any) => {
      if (resp) {
        if (resp.status) {
          const fileName = obj.reportpath.slice(obj.reportpath.lastIndexOf('/') + 1, obj.reportpath.length);
          this.documentService.downloadFileToBrowser(resp.response, resp.mimeType, fileName);
        } else {
          this.toastMessage.showErrorMsg(
            resp.errors[0].message ? resp.errors[0].message : 'Internal Server Error',
            'Error',
          );
        }
      }
    });
  }
}
