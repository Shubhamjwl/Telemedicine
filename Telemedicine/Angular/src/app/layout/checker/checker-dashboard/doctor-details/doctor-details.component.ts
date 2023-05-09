import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { CheckerService } from 'src/app/shared/commonBuildBlocks/services/checkerServices/checker.service';
import { ConsultationService } from 'src/app/shared/commonBuildBlocks/services/ConsultationServices/consultation.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-doctor-details',
  templateUrl: './doctor-details.component.html',
  styleUrls: ['./doctor-details.component.scss'],
})
export class DoctorDetailsComponent implements OnInit {
  @Input() docId: any;
  @Output() refresh = new EventEmitter();
  dataSource: any = null;
  displayedColumns: any;
  reasonGroup: FormGroup;
  extensionArray = ['png', 'jpeg', 'jpg', 'bmp'];
  modelRef: any;
  constructor(
    public modelService: NgbModal,
    public fb: FormBuilder,
    public consultationService: ConsultationService,
    private toastMessage: ToastMessageService,
    public authService: AuthService,
    public documentService: DocumentService,
    public checkerService: CheckerService,
  ) {
    this.displayedColumns = ['Document Name', 'Status', 'Visibility', 'Upgrade'];
    this.reasonGroup = this.fb.group({
      reason: ['', [Validators.required, Validators.maxLength(200)]],
    });
  }
  ngOnChanges(changes: SimpleChanges) { }

  ngOnInit(): void {
    this.getDoctorDetails();
  }
  notVerify(content) {
    this.modelRef = this.modelService.open(content);
  }
  submit() {
    let demo = this.reasonGroup.get('reason').value;
  }
  getDoctorDetails() {
    this.checkerService.getDoctorProfile(this.docId).subscribe(
      (result) => {
        if (result.response) {
          this.dataSource = result.response;
        } else if (result.errors) {
        }
      },
      (error) => { },
    );
  }
  verifyDoc(response) {
    if (!this.reasonGroup.valid) {
      this.reasonGroup.get('reason').markAsTouched();
    }
    let payload = {
      request: {
        regDocUserName: this.dataSource.drdUserId,
        verificationStatusFlag: response,
        reason: this.reasonGroup.get('reason').value,
      },
    };

    this.checkerService.verifyDoctor(payload).subscribe(
      (resp: any) => {
        if (resp.status) {
          this.refresh.emit('refresh');
          if (this.modelRef) {
            this.modelRef.close();
          }
          this.toastMessage.showSuccessMsg(
            resp.response && resp.response.message ? resp.response.message : 'sucess',
            'Sucess',
          );
        } else if (resp.errors) {
          if (this.modelRef) {
            this.modelRef.close();
          }
          this.toastMessage.showErrorMsg(
            resp.errors[0].message ? resp.errors[0].message : 'Internal Server Error',
            'Error',
          );
        }
      },
      (error) => { },
    );
  }
  download(obj) {
    let data = {
      api: 'downoad document',
      request: {
        ddtDocsPath: obj.ddtDocsPath,
        ddtDocsType: obj.ddtDocsType,
        drdUserId: this.dataSource.drdUserId,
      },
      mimeType: 'application/json',
    };

    this.checkerService.downloadDoc(data).subscribe((resp: any) => {
      if (resp) {
        if (resp.status) {
          const fileName = obj.ddtDocsPath.slice(obj.paddtDocsPathth.lastIndexOf('/') + 1, obj.ddtDocsPath.length);
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
  downloadAndView(obj) {
    let data = {
      api: 'downoad document',
      request: {
        ddtDocsPath: obj.ddtDocsPath,
        ddtDocsType: obj.ddtDocsType,
        drdUserId: this.dataSource.drdUserId,
      },
      mimeType: 'application/json',
    };
    this.checkerService.downloadDoc(data).subscribe((resp: any) => {
      if (resp) {
        if (resp.status) {
          this.documentService.downloadFileAndView(resp.response, resp.mimeType);
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
