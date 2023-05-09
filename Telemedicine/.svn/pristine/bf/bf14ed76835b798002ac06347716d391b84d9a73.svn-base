import { IConsultationPatientDetails } from "../interfaces/consultation-patient-details.interface";


export class ConsultationPatientDetails {
    name:string;
    apptId: string;
    gender: string;
    dob: string;
    emailId: string;
    mobileNo: string;
    address1: string;
    address2: string;
    address3: string;
    
    constructor(details: IConsultationPatientDetails){
        this.name = details.ptPersonalDetals.name;
        this.apptId = details.apptId;
        this.gender = details.ptPersonalDetals.gender;
        this.dob = details.ptPersonalDetals.dob;
        this.emailId = details.ptPersonalDetals.emailId;
        this.mobileNo = details.ptPersonalDetals.mobileNo;
        this.address1 = details.address.address1;
        this.address2 = details.address.address2;
        this.address3 = details.address.address3;
       
    }
}