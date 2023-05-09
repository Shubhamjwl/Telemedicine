export interface IAppointmentDetails {
  Today: { [key: string]: IAppointmentDtls[] };
  Tomorrow: IAppointmentSlot;
}

export interface IAppointmentSlot {
  slots: [];
  slotsArray: IAppointmentDtls[];
}
export interface IAppointmentDtls {
  appointmentDate: string;
  appointmentID: string;
  appointmentTime: string;
  chiefComplaints: string;
  doctorConsulFee: string;
  doctorName: string;
  doctorSpeciality: string;
  doctorUserId: string;
  path: string;
  patientName: string;
  patientRegId: string;
  profilePhoto: string;
  slot: string;
  slotType: string;
  status: string;

  // formatted
  appointmentTimeLabel: string;
}
