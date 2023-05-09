import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { IPatientPersonalDetails } from 'src/app/shared/commonBuildBlocks/interfaces/patient-personal-details.interface';

@Injectable({
    providedIn: 'root',
})
export class PrescriptionDataSharingService {

    symptomDetailsChanged: Subject<string[]> = new Subject<string[]>();
    diagnosisDetailsChanged: Subject<string[]> = new Subject<string[]>();
    medicineDetailsChanged: Subject<string[]> = new Subject<string[]>();
    adviceDetailsChanged: Subject<string[]> = new Subject<string[]>();
    ptPersonalDetails: IPatientPersonalDetails;

    prescriptionDataFilled = new BehaviorSubject<boolean>(false);

    symptomDetailsChangedEvent(data: any) {
        this.symptomDetailsChanged.next(data);
    }
    diagnosisDetailsChangedEvent(data: any) {
        this.diagnosisDetailsChanged.next(data);
    }
    medicineDetailsChangedEvent(data: any) {
        this.medicineDetailsChanged.next(data);
    }
    adviceDetailsChangedEvent(data: any) {
        this.adviceDetailsChanged.next(data);
    }

}
