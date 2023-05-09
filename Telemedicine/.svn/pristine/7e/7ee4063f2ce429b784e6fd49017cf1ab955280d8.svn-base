import { Router, ActivatedRoute } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit, ViewEncapsulation } from '@angular/core';
import { Notification } from 'src/app/shared/commonBuildBlocks';
import { NotificationTypeEnum } from 'src/app/shared/commonBuildBlocks/model/notification.model';
import { NotificationService } from 'src/app/shared/commonBuildBlocks/services/notification/notification.service';
import { DocumentService } from 'src/app/shared/commonBuildBlocks/services/documentServices/document.service';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class NotificationComponent implements OnInit {
  NotificationTypeEnum = NotificationTypeEnum;
  constructor(
    public dialogRef: MatDialogRef<NotificationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Notification,
    private router: Router,
    private routes: ActivatedRoute,
    private notificationSerice: NotificationService,
    private documentService: DocumentService,
    private toastMessageService: ToastMessageService,
    private spinner: NgxSpinnerService,
  ) { }

  ngOnInit(): void {
    if (this.data.notificationType === NotificationTypeEnum.GeneralInformation) {
      this.spinner.show();
      this.notificationSerice.getNotificationById(this.data?.notificationId).subscribe({
        next: (res) => {
          this.spinner.hide();
          if (res.status) {
            const { template, attachmentPath } = res.response;
            this.data = {
              ...this.data,
              template,
              attachmentPath,
            };
          }
        },
        error: (err => {
          this.spinner.hide();
        })
      });
    }
  }

  onClickChangePass() {
    this.dialogRef.close();
    this.router.navigate(['../change-password'], { relativeTo: this.routes });
  }

  downloadFile() {
    this.documentService.downloadDocumentFromServer(this.data.attachmentPath);
  }
}
