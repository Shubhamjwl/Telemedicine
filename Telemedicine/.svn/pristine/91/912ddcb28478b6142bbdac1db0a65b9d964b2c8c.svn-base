export interface Notification {
  notificationId: number;
  notification: string;
  notificationType: NotificationTypeEnum;
  notificationColor: string;
  createdTmstp: string;
  unReadFlag: boolean;
  template: string;
  attachmentPath: string;
}

export interface NotificationList {
  count: number;
  notificationList: Notification[];
}

export enum NotificationTypeEnum {
  PasswordExpiry10Days = 'PasswordExpiry10Days',
  PasswordExpiry1Day = 'PasswordExpiry1Day',
  PatientAppointment = 'PatientAppointment',
  DoctorAppointment = 'DoctorAppointment',
  GeneralInformation = 'GeneralInformation',
  Offer = 'Offer',
  Wallet = 'Wallet'
}

/* export const NotificationColor = {
  PasswordExpiry10Days: '#92d050',
  PasswordExpiry1Day: '#92d050',
  PatientAppointment: '#0070c0',
  DoctorAppointment: '#0070c0',
  GeneralInformation: '#FF0000'
}; */

export interface SendNotificationRequest {
  userIds: string[];
  userType: 'DOCTOR' | 'PATIENT',
  notificationType: 'General',
  template: string;
  attachmentFile: FileReader;
  attachmentFileName: string;
  amount: number;
}
