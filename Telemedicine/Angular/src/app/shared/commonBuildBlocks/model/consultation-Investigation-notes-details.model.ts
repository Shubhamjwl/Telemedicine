import { IInvestigationNotesDetails } from '../interface/investigation-notes-details.interface';

export class ConsultationInvestigationNotesDetails {
    allergiesHistory: string;
    diastolic: number;
    familyHistory: string;
    hb: number;
    height: number;
    note: string;
    ofc: number;
    pefr: number;
    physicalExam: string;
    pulse: number;
    reportName: string;
    reportType: string;
    respirationRate: number;
    spo2Level: string;
    systolic: number;
    weight: number;
    imgUrl?: string;
    constructor(details: IInvestigationNotesDetails) {
        this.allergiesHistory = details.allergiesHistory;
        this.diastolic = details.diastolic;
        this.familyHistory = details.familyHistory;
        this.hb = details.hb;
        this.height = details.height;
        this.note = details.note;
        this.ofc = details.ofc;
        this.pefr = details.pefr;
        this.physicalExam = details.physicalExam;
        this.pulse = details.pulse;
        this.reportName = details.reportName;
        this.reportType = details.reportType;
        this.respirationRate = details.respirationRate;
        this.spo2Level = details.spo2Level;
        this.systolic = details.systolic;
        this.weight = details.weight;
        this.imgUrl = details.imgUrl ? details.imgUrl : '';
    }
}