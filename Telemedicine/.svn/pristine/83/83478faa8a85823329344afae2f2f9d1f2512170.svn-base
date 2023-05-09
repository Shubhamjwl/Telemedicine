import { NotificationComponent } from './notification/notification.component';
import { MatDialog } from '@angular/material/dialog';
import { Notification } from '../../../commonBuildBlocks/model/notification.model';
import { NotificationService } from '../../../commonBuildBlocks/services/notification/notification.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss'],
})
export class NotificationListComponent implements OnInit {

  notifications: Notification[];

  constructor(private notificationService: NotificationService, private dialogRef: MatDialog) { }

  readNotification(data: Notification) {
    this.notificationService.readNotification(data.notificationId);
    this.dialogRef.open(NotificationComponent, {
      width: '500px',
      minHeight: '200px',
      maxHeight: '700px',
      data
    });
  }

  ngOnInit(): void {
    this.notificationService.notifications$.subscribe({
      next: (res) => {
        this.notifications = res?.notificationList;
      },
      error: (err) => console.log(err),
    });
  }
}
