export interface SaveAppointmentDetail {
  drRegID: string;
  ptRegID: string;
  transactionID: string;
  bookForSomeoneElse: string;
  patientName: string;
  patientEmail: any;
  patientMNO: string;
  symptomsDetails: string;
  tncFlag: boolean;
  consultType: string;
  appointmentDetails: PtAppointmentDetail;

  // TODO: add wallet in backend
  totalWalletAmount?: number;
}

interface PtAppointmentDetail {
  appointmentSlot: string;
  appointmentDate: string;
}

export interface SaveAppointmentDetailResponse {
  appointmentID: string;
  info: string;
}
