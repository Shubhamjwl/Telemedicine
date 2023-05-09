import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { ISymptomDetails } from '../../interfaces/symptom-details.interface';
import { IPatientDetailsByAppointmentIDSaveSpec } from '../../saveSpecs/patient-details-by-appointment-id.savespec';
@Injectable({
    providedIn: "root"
})

export class DataPassingService {

    userId;
    symptomDetail: ISymptomDetails[] = [];
    routeDetails: IPatientDetailsByAppointmentIDSaveSpec;
    scribeList = {};
    userName = '';
    loggingUserName: string;
    medicineList = [];
    scribeDetails = [];
    doctorDetails:any = null;
    patientDetails = {};
    scribeListForDoctor = [];
    profilePhoto: string = 'assets/images/img_avatar.png';
    rescheduleApptDetails:any = null;
    docName: any; //to book appointment for role doctor and scribe  
    doctorId: any;
    doctorListMappedWithPatientId = [];
    docId: string = null;
    setProfileDetails = new BehaviorSubject(null);
    docScribeList = new BehaviorSubject(null);
    paymentDetails = new BehaviorSubject(null);
    doctorScribeList = []
    paymentStatus = new BehaviorSubject(null);
    callDocDashboard = new BehaviorSubject(null);
    paymentOptionData = new BehaviorSubject(null);
    paymentLoader = new Subject();
    paymentObservable = this.paymentLoader.asObservable();
    specializationList = [];
    resetData(){
       // let EMPTY = new Object();
        this.doctorListMappedWithPatientId = [];
        this.scribeListForDoctor = [];
        this.doctorScribeList = [];

        //console.log("Reset",  this.setProfileDetails,  this.docScribeList, this.paymentDetails);
    }
    setSymptomDetails(data) {
        this.symptomDetail = data;
    }

    getSymptomDetails() {
        return this.symptomDetail;
    }

    setRouteDetails(data) {
        this.routeDetails = data;
    }

    getRouteDetails() {
        return this.routeDetails;
    }

    setScribeList(data) {
        this.scribeList = data;
        this.scribeListForDoctor = data;
        sessionStorage.setItem('SCRIBE', data.scrbUserId);
    }

    getScribeList() {
        return this.scribeList;
    }

    setMedicineList(data) {
        this.medicineList = data;
    }
    
    getmedicineList() {
        return this.medicineList
    }

    setDoctorDetails(data) {
        this.doctorDetails = data
    }

    getDoctorDetails(){
        return this.doctorDetails;
    }

    setPatientDetails(data) {
        this.patientDetails = data
    }

    getPatientDetails(){
        return this.patientDetails;
    }
    // setScribeDetails(data) {
    //     this.scribeDetails = data;
    // }

    // getScribeDetails() {
    //     return this.scribeDetails
    // }
    addPaymentLoader(message){
        this.paymentLoader.next(message);
    }
}