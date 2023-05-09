import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';
import { MatTableDataSource } from '@angular/material/table';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { CheckerService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/checker.service';
import { ViewReportModalComponent } from '../../modals/view-report-modal/view-report-modal.component';

@Component({
  selector: 'app-uploaded-documents-report',
  templateUrl: './uploaded-documents-report.component.html',
  styleUrls: ['./uploaded-documents-report.component.scss']
})
export class UploadedDocumentsReportComponent implements OnInit {

  constructor(
    private dialog: MatDialog,
    private consultationService: ConsultationService,
    private activatedRoute: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private toastMessage: ToastMessageService,
    public authService: AuthService,
    public documentService: DocumentService,
    public checkerService:CheckerService,
  ) { }

  /**
   * Used to show | hide password.
   */
  hide = true;
  // 'download', 'delete'
  // 'documentName', 'view'
  displayedColumns: string[] = ['reportname', 'view', 'download'];
  dataSource;
  documentList = [];
  imagePath
  ngOnInit(): void {
    this.getReportList();
    this.getPatientUploadedReport();
  }

  convertToReport(element) {
    this.imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:' + element.mimetype + ';base64,' + element.report);
  }
  getReportList() {
    let apptID = this.activatedRoute.snapshot.queryParamMap.get('apptID');
    this.consultationService.getInvestigationNotesDetails(apptID).subscribe(result => {{
      this.documentList = result ? result.response ? result.response['patientReportsData'] ?result.response['patientReportsData'] : [] : [] : [];
    }})
  }
  
  getPatientUploadedReport() {
    
    this.consultationService.getPatientUploadedReport().subscribe(result => {{
      this.documentList = result ? result.response : [];
      this.dataSource = new MatTableDataSource(this.documentList);
    }})
  }

  reloadCurrentRoute(){
    this.getPatientUploadedReport();
  }

  openDialog(element) {
    this.imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:' + element.mimetype + ';base64,' + element.report);

   let reportPath =  element.reportpath.replace('/var/telemedicine/', `${environment["baseUrl"]}`)
  //  let path = `${environment["baseUrl"]}${reportPath}`
  // "documents/report/2061aba9-f141-485e-b38a-2d01187e82ef/report.jp"
    let reportPathUrl = this.sanitizer.bypassSecurityTrustResourceUrl(reportPath);
    let data = {
      imgPath: this.imagePath,
      imgUrl : reportPathUrl, //reportPathUrl
      reportName: 'Dummy'
    }
   const dialogRef = this.dialog.open(ViewReportModalComponent, {
     height: '100%',
     width: '100%',
     data: data
   });

   dialogRef.afterClosed().subscribe(result => {
   // this.gotoPage();
   });
   // this.dialog.open(ViewReportModalComponent);
 }

 

 download(obj){
  let data = {
    api:"downoad document",
    request:{
      ddtDocsPath: obj.reportpath,
      ddtDocsType: obj.mimetype,
      drdUserId: this.authService.getUserId(),
    },
    mimeType:"application/json"
  }

  this.checkerService.downloadDoc(data).subscribe((resp:any) => {
    if(resp){
        if(resp.status){
          const fileName = obj.reportpath.slice(obj.reportpath.lastIndexOf('/')+1,obj.reportpath.length);
            this.documentService.downloadFileToBrowser(resp.response, resp.mimeType,fileName);
        }else {
            this.toastMessage.showErrorMsg(resp.errors[0].message ? resp.errors[0].message: 'Internal Server Error', "Error");
        }
    }
  })
}
}
