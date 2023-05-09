import { Notification, NotificationList, SendNotificationRequest } from './../../';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/messaging';
import { IAPIResponseWrapper } from '../../interfaces/api-response-wrapper.interface';
import { tap } from 'rxjs/operators';
import { ToastMessageService } from '../../toaster/toast-message.service';
import { Utility } from 'src/app/utility/Utility';
@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private baseUrl = `alert`;

  notifications$ = new BehaviorSubject<NotificationList>({} as NotificationList);
  constructor(
    private http: HttpClient,
    private angularFireMessaging: AngularFireMessaging,
    private toastMessageService: ToastMessageService,
  ) { }

  getAllNotifications(updateLastSyncFlag: boolean): void {
    this.http.get(`${this.baseUrl}/getNotifications?updateLastSyncFlag=${updateLastSyncFlag}`).subscribe({
      next: (res: IAPIResponseWrapper<NotificationList>) => {
        if (res.status) {
          this.notifications$.next(res.response);
        } else {
          this.notifications$.next({} as NotificationList);
        }
      },
      error: (err) => {
        this.notifications$.next({} as NotificationList);
      },
    });
  }

  readNotification(id: number): void {
    this.http.put(`${this.baseUrl}/updateNotificationReadFlag?notificationId=${id}`, {}).subscribe({
      next: (res: IAPIResponseWrapper<any>) => {
        if (res.status) {
          this.getAllNotifications(false);
        } else {
          this.toastMessageService.showErrorMsg(res.errors[0]?.message, 'Error');
        }
      },
      error: (err) => {
        this.toastMessageService.showErrorMsg(err.error?.errors[0]?.message, 'Error');
      },
    });
  }

  requestPermission(): void {
    this.angularFireMessaging.requestToken
      .pipe(
        tap((token) => {
          console.log('firebase token', token);
          this.http.put(`${this.baseUrl}/updateRegistrationTokenForUser?token=${token}`, {}).subscribe();
        }),
      )
      .subscribe(() => this.receiveMessage());
  }

  private receiveMessage() {
    this.angularFireMessaging.messages.subscribe((msg: any) => {
      console.log('new message', msg);
      this.getAllNotifications(false);
    });
  }

  sendNotification(sendNotification: SendNotificationRequest): Observable<IAPIResponseWrapper<string>> {
    const request = Utility.formatRequestPayload(sendNotification);
    return this.http.post<IAPIResponseWrapper<string>>(`${this.baseUrl}/sendNotification`, request);
  }

  getNotificationById(notificationId: number): Observable<IAPIResponseWrapper<Notification>> {
    return this.http.get<IAPIResponseWrapper<Notification>>(
      `${this.baseUrl}/getTemplateByNotificationId?notificationId=${notificationId}`,
    );
  }
}
