export interface IAppointmentDetails{
    Today : IAppointmentSlot,
    Tomorrow: IAppointmentSlot
}

export interface IAppointmentSlot{
    slots : [],
    slotsArray: IAppointmentDtls[]
}
export interface IAppointmentDtls {
    slot: string;
    appointmentDate: string;
    appointmentID: string;
    appointmentTime: string;
    doctorName: string;
    patientName: string;
    status: string;
}