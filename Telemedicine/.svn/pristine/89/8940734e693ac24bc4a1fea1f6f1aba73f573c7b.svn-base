import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { Observable, Subject } from 'rxjs';
import { map, timeout } from 'rxjs/operators';
import { AppConstants } from 'src/app/app-constants';
import { Utility } from 'src/app/utility/Utility';
import { MasterTypes } from '../../enum/master-type.enum';
import { IAPIResponseWrapper } from '../../interfaces/api-response-wrapper.interface';
import { IAssociation } from '../../interfaces/association.interface';
import { IConsultationPatientDetails } from '../../interfaces/consultation-patient-details.interface';
import { IDownloadPatientreport } from '../../interfaces/downloadPatientreport.interface';
import { IInvestigationNotes } from '../../interfaces/investigation-notes.interface';
import { IRedflagDetail } from '../../interfaces/red-flag-detail.interface';
import { ISymptomsList } from '../../interfaces/symptoms-list.interface';
import {
  ConsultationPaymentRequest,
  ConsultationPaymentResponse,
  PayResponseRequest,
} from '../../model/payment/consulation-payment-model';
import { IPatientDetailsByAppointmentIDSaveSpec } from '../../saveSpecs/patient-details-by-appointment-id.savespec';
import { ISaveSymptomDetailsSaveSpec } from '../../saveSpecs/save-sympotm-details.saveSpec';
import { PatientConsulation } from './consulation.model';

@Injectable({
  providedIn: 'root',
})
export class ConsultationService {
  apptId: string;
  drRegId: string;
  ptRegId: string;
  patientConsulation: PatientConsulation = {};
  constructor(private httpClient: HttpClient, private activatedRoute: ActivatedRoute, private datePipe: DatePipe) {
    let appointmentDetails = this.activatedRoute.snapshot.queryParamMap;
    this.apptId = appointmentDetails.get('apptID');
    this.ptRegId = appointmentDetails.get('ptRegID');
    this.drRegId = appointmentDetails.get('drRegID');
  }
  emailId;
  doctorUserID;
  savSlotDetails;
  /**
   * Used to store current tab.
   */
  currentTab = new Subject();

  /**
   *getScribeList function is Used for API call to get scribe list for doctor
   */
  getScribeList() {
    let drRegID = sessionStorage.getItem('USER_ID');
    let params = new HttpParams();
    params = params.set('drRegID', drRegID);
    let payload = {
      api: 'get List Of Scribe By Doctor',
      id: '1',
      version: 'v1.0',
      requesttime: '2020-11-28T05:48:03.125Z',
      request: null,
      mimeType: 'application/json',
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.getListOfScribeByDoctor,
      payload,
    );
  }

  /**
   * Used to get a patient details by appointment ID.
   * @param appointmentId Used to store appointment ID.
   */
  getPatientDeailsByAppointmenttID(
    details: IPatientDetailsByAppointmentIDSaveSpec,
  ): Observable<IAPIResponseWrapper<IConsultationPatientDetails>> {
    let payload = {
      api: 'get Patient Details',
      id: '1',
      version: 'v1.0',
      requesttime: '2020-11-28T05:48:03.125Z',
      request: {
        appointmentId: details.apptID,
        drRegId: details.drRegID,
        ptRegId: details.ptRegID,
      },
      mimeType: 'application/json',
    };
    return this.httpClient.post<IAPIResponseWrapper<IConsultationPatientDetails>>(
      AppConstants.consultationModuleURL + AppConstants.getconsultationPatientDetails,
      payload,
    );
  }

  /**
   * Used to get Consultation Tabs DropDown List
   */
  getMasterDetailsListByMasterName(listType: MasterTypes) {
    const data = Utility.formatRequestPayload({ masterName: listType });
    //AppConstants.masterManagementModuleURL + AppConstants.appVersion +
    // return this.httpClient.post<IAPIResponseWrapper<ISymptomsList[]>>(AppConstants.masterManagementModuleURL + AppConstants.getMasterDetailsListByMasterName, data)
    return this.httpClient.post<IAPIResponseWrapper<ISymptomsList[]>>(
      AppConstants.masterManagementModuleURL + AppConstants.DetailsListByMasterName,
      data,
    );
  }

  getAssociationNameList(): Observable<IAPIResponseWrapper<IAssociation[]>> {
    return this.httpClient.get<IAPIResponseWrapper<IAssociation[]>>(
      AppConstants.masterManagementModuleURL + AppConstants.associationNameList,
    );
  }

  getdiagnosis() {
    let params = new HttpParams();
    let apptID = this.activatedRoute.snapshot.queryParamMap.get('apptID');
    params = params.set('appointID', apptID);
    let payload = {
      api: 'get Consultation Diagnosis Dtls',
      id: '1',
      version: 'v1.0',
      requesttime: '2020-11-28T05:48:03.125Z',
      request: {
        ctApptId: apptID,
      },
      mimeType: 'application/json',
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.getConsultationDiagnosisDtls,
      payload,
    );
  }

  saveDiagnosis(data) {
    let payload = {
      API: 'consultation',
      version: 'v1',
      request: {
        appointID: this.activatedRoute.snapshot.queryParamMap.get('apptID'),
        tabID: 'DIAGNOSIS',
        data: data,
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.saveConsultationDiagnosis,
      payload,
    );
  }
  /**
   *
   * @param payload Used to save symptom details
   */
  saveSymptomDetails(symptomData) {
    let payload = {
      version: '1',
      request: symptomData,
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.saveConsultationChiefComplaint,
      payload,
    );
  }

  /**
   * Used to get Patient Symptoms list
   * @param appointmentId used to store appointment ID
   */ //Not In Use
  getSymptomsList(appointmentId: string) {
    let params = new HttpParams();
    let apptID = this.activatedRoute.snapshot.queryParamMap.get('apptID');
    params = params.set('apptID', apptID);
    let payload = {
      api: 'get Consultation Chief Complaint',
      id: '1',
      version: 'v1.0',
      requesttime: '2020-11-28T05:48:03.125Z',
      request: {
        ctApptId: apptID,
      },
      mimeType: 'application/json',
    };
    return this.httpClient.get<IAPIResponseWrapper<ISaveSymptomDetailsSaveSpec>>(
      AppConstants.consultationModuleURL + AppConstants.getConsultationChiefComplaint,
      { params },
    );
  }

  /**
   * Used to get Investigation Notes Details
   * @param appointmentId is used to store appointment ID
   */
  getInvestigationNotesDetails(appointmentId: string) {
    let apptID = this.activatedRoute.snapshot.queryParamMap.get('apptID');
    let params = new HttpParams();
    params = params.set('apptID', apptID);
    let payload = {
      api: 'get Consultation Investigation Dtls',
      id: '1',
      version: 'v1.0',
      requesttime: '2020-11-28T05:48:03.125Z',
      request: {
        ctApptId: apptID,
      },
      mimeType: 'application/json',
    };
    return this.httpClient.post<IAPIResponseWrapper<IInvestigationNotes>>(
      AppConstants.consultationModuleURL + AppConstants.getConsultationInvestigationDtls,
      payload,
    );
  }

  saveInvestigationNotesDetails(details) {
    let payload = {
      version: '1',
      id: 'telemedicine-usermgmt',
      request: {
        appointmentId: this.activatedRoute.snapshot.queryParamMap.get('apptID'),
        tableId: 'investigte',
        investigationDetailsDTOList: [details],
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.saveConsultationInvestigation,
      payload,
    );
  }

  getMedicineList() {
    let payload = {
      version: 'v1.0',
      request: {
        cmdApptIdFk: this.activatedRoute.snapshot.queryParamMap.get('apptID'),
        cmdDrUserIdFk: this.activatedRoute.snapshot.queryParamMap.get('drRegId'),
        cmdPtUserIdFk: this.activatedRoute.snapshot.queryParamMap.get('ptRegID'),
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.getPatientMedicationDtls,
      payload,
    );
  }

  saveMedicineDetails(payload) {
    return this.httpClient.post(
      AppConstants.consultationModuleURL + AppConstants.saveConsultationMedicationDtls,
      payload,
    );
  }

  getConsultationAdviceDetails() {
    let payload = {
      version: '1.0',
      request: {
        appointID: this.activatedRoute.snapshot.queryParamMap.get('apptID'),
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.getConsultationAdvice,
      payload,
    );
  }

  saveAdviceDetails(details) {
    let payload = {
      version: '1.0',
      request: {
        appointID: this.activatedRoute.snapshot.queryParamMap.get('apptID'),
        tabID: 'consultadv',
        data: details,
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.saveConsultationAdvice,
      payload,
    );
  }

  getPrescriptionDetailsOnPrevious(request) {
    request.ctApptId = this.activatedRoute.snapshot.queryParamMap.get('apptID');
    request.ctDoctorId = this.activatedRoute.snapshot.queryParamMap.get('drRegID');
    request.ctPatientId = this.activatedRoute.snapshot.queryParamMap.get('ptRegID');
    let payload = {
      version: 'v1.0',
      request: request,
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.getConsultationDetails,
      payload,
    );
  }

  getSymptomsDetails() {
    return this.httpClient.get<IAPIResponseWrapper<any>>(AppConstants.iPANModuleURL + AppConstants.getSymptomsDetails);
  }

  getAdviceDetails() {
    return this.httpClient.get<IAPIResponseWrapper<any>>(AppConstants.iPANModuleURL + AppConstants.getAdviceDetails);
  }

  getMedicinesDetails() {
    return this.httpClient.get<IAPIResponseWrapper<any>>(AppConstants.iPANModuleURL + AppConstants.getMedicinesDetails);
  }

  getDiagnosisDetails() {
    return this.httpClient.get<IAPIResponseWrapper<any>>(AppConstants.iPANModuleURL + AppConstants.getDiagnosisDetails);
  }

  getSearchEngineDetailsBySymtpoms(id: string) {
    let params = new HttpParams();
    params = params.set('id', id);
    return this.httpClient.get<IAPIResponseWrapper<any>>(
      AppConstants.iPANModuleURL + AppConstants.getSearchEngineDetailsBySymtpoms,
      {
        params,
      },
    );
  }

  savePrescriptionDetails(request) {
    request.ctApptId = this.activatedRoute.snapshot.queryParamMap.get('apptID');
    request.ctDoctorId = this.activatedRoute.snapshot.queryParamMap.get('drRegID');
    request.ctPatientId = this.activatedRoute.snapshot.queryParamMap.get('ptRegID');
    let payload = {
      version: 'v1.0',
      request: request,
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.saveConsultationDtls,
      payload,
    );
  }

  saveAndUploadPrescription(request) {
    request.ctApptId = this.activatedRoute.snapshot.queryParamMap.get('apptID');
    request.ctDoctorId = this.activatedRoute.snapshot.queryParamMap.get('drRegID');
    request.ctPatientId = this.activatedRoute.snapshot.queryParamMap.get('ptRegID');
    let payload = {
      // "api":"save consultation details"
      version: 'v1.0',
      request: request,
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.updateConsultationStatus,
      payload,
    );
  }

  getPrescriptionMedicationDetails() {
    let request = {
      ctApptId: this.activatedRoute.snapshot.queryParamMap.get('apptID'),
      ctDoctorId: this.activatedRoute.snapshot.queryParamMap.get('drRegID'),
      ctPatientId: this.activatedRoute.snapshot.queryParamMap.get('ptRegID'),
    };

    let payload = {
      version: 'v1.0',
      requesttime: '2020-11-29T05:48:03.125Z',
      request: request,
      mimeType: 'application/json',
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.getPrescriptionDetails,
      payload,
    );
  }

  getPrescriptionDetails() {
    const httpOptions = {
      headers: new HttpHeaders({
        Accept: 'application/json',
        responseType: 'arraybuffer',
      }),
    };

    let payload = {
      version: '1.0',
      request: {
        appointID: this.activatedRoute.snapshot.queryParamMap.get('apptID'),
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.getDetailsForPrescription,
      payload,
      httpOptions,
    );
  }

  isScribeAssigned(apptId) {
    let apptID = apptId;
    let params = new HttpParams();
    params = params.set('apptId', apptID);
    let payload = {
      request: {
        appointmentID: apptID,
      },
    };
    //+ AppConstants.appVersion
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.bookModuleURL + AppConstants.isScribeAssignedToAppt,
      payload,
    );
  }

  getPatientUploadedReport() {
    let payload = {
      // "api":"downoad document",
      // "id":"1",
      // "version":"v1.0",
      // "requesttime":"2020-09-09T05:48:03.125Z",
      request: {
        appointmentID: this.activatedRoute.snapshot.queryParamMap.get('apptID'), //"2021012212334664"
      },
      mimeType: 'application/json',
    };
    return this.httpClient.post<IAPIResponseWrapper<IDownloadPatientreport[]>>(
      AppConstants.consultationModuleURL + AppConstants.downloadPatientReport,
      payload,
    );
  }

  getPatientAppoinments(ptRegID) {
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.consultationModuleURL + AppConstants.getAppointmentListByPatientID,
      null,
    );
  }

  getPatientDetails(userID) {
    let payload = {
      request: userID,
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.patientModuleURL + AppConstants.appVersion + AppConstants.getPatientDetails,
      payload,
    );
  }

  getStateList() {
    let payload = {
      request: {
        countryName: 'INDIA',
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.masterManagementModuleURL + AppConstants.getStateList,
      payload,
    );
  }

  getCityList(state: string) {
    const payload = {
      request: {
        stateName: state,
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.masterManagementModuleURL + AppConstants.getCityList,
      payload,
    );
  }

  verifyDoctor(name) {
    let payload = {
      request: {
        regDocUserName: name,
        verificationStatusFlag: 'Y',
        reason: '',
      },
    };
    //(AppConstants.masterManagementModuleURL + AppConstants.appVersion +
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.masterManagementModuleURL + AppConstants.verifyDoctor,
      payload,
    );
  }

  checkDuplicate(data) {
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.otpManagerModuleURL + AppConstants.checkUniqueValue,
      data,
    );
  }

  getPatientAppointmentDatails(data) {
    let url = AppConstants.getAppointmentListByPatientID;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, null);
  }

  saveSlotDetails(payload): Observable<any> {
    return this.httpClient.post(AppConstants.slotManagement + AppConstants.saveSlotDetails, payload).pipe(
      timeout(120000), //1minute
      map((res) => res),
    );
  }

  saveSlotDetailsForDoctorRegistration(payload): Observable<any> {
    return this.httpClient
      .post(AppConstants.slotManagement + AppConstants.saveSlotDetailsForDoctorRegistration, payload)
      .pipe(
        timeout(120000), //1minute
        map((res) => res),
      );
  }

  getSlotCreatedDays(payload): Observable<any> {
    return this.httpClient.post(AppConstants.slotManagement + AppConstants.getSlotCreatedDays, payload);
  }

  getAvailableSlotByMonth(payload): Observable<any> {
    return this.httpClient.post(AppConstants.slotManagement + AppConstants.getAvailableSlotByMonth, payload);
  }

  holidayManagement(payload): Observable<any> {
    return this.httpClient.post(AppConstants.slotManagement + AppConstants.holidayManagement, payload);
  }

  isValidConsultation(appointmenttTime, appointmentDate, selectedDate, isBook?) {
    if (appointmenttTime && appointmentDate) {
      let date = new Date(); // for now
      let todaysDate = this.datePipe.transform(appointmentDate, 'yyyy-MM-dd');
      let hrs = date.getHours() < 10 ? '0' + date.getHours() : date.getHours(); // => 9
      let minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes(); // =>  30
      let recentTime = hrs + ':' + minute;
      let apptTime = appointmenttTime;
      var index = apptTime.indexOf('-'); // Gets the first index where a space occours
      var startTime = apptTime.substr(0, index); // Gets the first part
      var endTime = apptTime.substr(index + 1);
      let isValid = false;
      if (isBook) {
        if (todaysDate && selectedDate && todaysDate > selectedDate) {
          isValid = true;
        } else {
          isValid = recentTime <= startTime && recentTime <= endTime ? true : false;
        }
      } else {
        isValid = recentTime >= startTime && recentTime <= endTime ? true : false;
      }
      let isValidDate = false;
      if (todaysDate && selectedDate) {
        isValidDate = todaysDate >= selectedDate ? true : false;
      }
      if (isValidDate && isValid) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
  convertedTimein24HourFormat(time) {
    return moment(time, 'hh:mm A').format('HH:mm');
  }

  isSlotAvalableAfter30(date, time) {
    const timeArray = time.split('-');
    const startTime: string = timeArray[0];
    const endTime: string = timeArray[1];
    const startDateTime = new Date(
      new Date(date).setHours(parseInt(startTime.split(':')[0]), parseInt(startTime.split(':')[1]), 0),
    );
    const endDateTime = new Date(
      new Date(date).setHours(parseInt(endTime.split(':')[0]), parseInt(endTime.split(':')[1]), 0),
    );
    return new Date().getTime() < startDateTime.getTime() - 30 * 60000;
  }

  validateConsultation(appointmenttTime: string, appointmentDate: string): boolean {
    if (appointmenttTime && appointmentDate) {
      const currentTime = moment();
      const apptStartTime = appointmenttTime.split('-')[0];
      const apptEndTime = appointmenttTime.split('-')[1];
      const apptStart = moment(appointmentDate + ' ' + apptStartTime).subtract(30, 'minutes');
      const apptEnd = moment(appointmentDate + ' ' + apptEndTime).add(30, 'minutes');
      return currentTime.isSameOrAfter(apptStart) && currentTime.isSameOrBefore(apptEnd);
    }
    return false;
  }

  validateDate(appointmentTime, appointmentDate) {
    console.log(appointmentTime, appointmentDate, 'enter in fucntion date and time ');
    if (appointmentTime && appointmentDate) {
      console.log('enter in fucntion if in service ');

      // todays date
      let todaysDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      //to calculate time before 1 hr and after 1 hour
      const d = todaysDate;
      const apptStartTime = appointmentTime.split('-')[0];
      const apptEndTime = appointmentTime.split('-')[1];
      const apptStart = moment(d + ' ' + apptStartTime);
      const apptEnd = moment(d + ' ' + apptEndTime);
      console.log('Timings', apptStartTime, apptEndTime, apptStart, apptEnd);

      let recentStartTime = this.convertedTimein24HourFormat(
        moment(apptStart).subtract(30, 'minutes').format('hh:mm A'),
      );
      let recentEndTime = this.convertedTimein24HourFormat(moment(apptEnd).add(30, 'minutes').format('hh:mm A'));
      console.log('recentStartTime', recentStartTime, 'recentEndTime', recentEndTime);

      if (apptStartTime >= recentStartTime && apptStartTime <= recentEndTime) {
        console.log('enter in if of of validate date conditionssssssssss');
        return true;
      } else {
        return false;
      }
    } else {
      console.log('enter in full else ');
      return false;
    }
  }
  /**
   * Used to get upcoming appointment Date and Time
   */
  getNextAppointmentTime(appdate, time) {
    if (appdate && time) {
      var d = new Date(appdate);
      let p = d.toDateString();
      let arr = p.split(' ');
      let rtime = time.split('-');
      console.log(arr, rtime);

      let start = new Date(arr[1] + ' ' + arr[2] + ',' + ' ' + arr[3] + ' ' + rtime[0]);
      let end = new Date(arr[1] + ' ' + arr[2] + ',' + ' ' + arr[3] + ' ' + rtime[1]);

      console.log('start,endand rtime time', start, end, rtime);

      //   old code
      //  let startDate = start.getTime();
      //  let endDate = end.getTime()

      let startDate = moment(start).subtract(30, 'minutes').format();
      let endDate = moment(end).add(30, 'minutes').format();
      console.log(new Date(startDate).getTime(), new Date(endDate).getTime(), 'consultation');

      //let slotStartTime = new Date(startDate).getHours()  + new Date(startDate).getMinutes()  + new Date(startDate).getSeconds();
      //let slotEndTime = new Date(endDate).getHours()  + new Date(endDate).getMinutes()  + new Date(endDate).getSeconds();

      //console.log(slotStartTime,slotEndTime,"timiiiii");
      console.log(new Date(startDate).getDate(), new Date(endDate).getMinutes(), new Date(endDate).getHours());
      return [new Date(startDate).getTime(), new Date(endDate).getTime()];

      //return[slotStartTime,slotEndTime];
    }
    return null;
  }

  startVideoConsultation(appID) {
    let request = {
      id: '1',
      version: 'v1',
      requesttime: '',
      method: 'POST',
      request: {
        apptId: appID, //"2021020405502519"
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(AppConstants.startConsultation, request);
  }

  createMeetingBBB(request) {
    return this.httpClient.post<IAPIResponseWrapper<any>>(AppConstants.createMeeting, request);
  }

  joinMeetingBBB(request) {
    return this.httpClient.post<IAPIResponseWrapper<any>>(AppConstants.joinMeeting, request);
  }

  getKarzaMeetingUrl(request) {
    return this.httpClient.get<IAPIResponseWrapper<any>>(AppConstants.getKarzaMeetingUrl + request);
  }

  generateKarzaMeetingUrl(request) {
    return this.httpClient.post<IAPIResponseWrapper<any>>(AppConstants.generateKarzaMeetingUrl, request);
  }

  consultationPayment(
    request: ConsultationPaymentRequest,
  ): Observable<IAPIResponseWrapper<ConsultationPaymentResponse>> {
    return this.httpClient.post<IAPIResponseWrapper<ConsultationPaymentResponse>>(
      AppConstants.paymentModuleURL + AppConstants.payment,
      request,
    );
  }

  orderPayment(request: PayResponseRequest): Observable<IAPIResponseWrapper<any>> {
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.paymentModuleURL + AppConstants.orderPayment,
      request,
    );
  }

  payLater(request) {
    return this.httpClient.post<any>(AppConstants.paymentModuleURL + AppConstants.payLater, request);
  }

  // ---book appointment using link payment----

  consultationPaymentUrl(request) {
    // let request = {
    //   amount: paymentDtls.fees,
    //   apptId: paymentDtls.apptId,
    //   transId: paymentDtls.transId,
    //   ptUserID : paymentDtls.ptUserID
    // }
    return this.httpClient.post<any>(AppConstants.paymentModuleURL + AppConstants.payUsingLink, request);
  }
  OrderPaymentUrl(request) {
    return this.httpClient.post<any>(AppConstants.paymentModuleURL + AppConstants.payResponseForBookingLink, request);
  }

  getDrRedflagDetails(emailid: string, ptMobileNumber: string): Observable<IAPIResponseWrapper<IRedflagDetail[]>> {
    return this.httpClient.get<IAPIResponseWrapper<IRedflagDetail[]>>(
      `${AppConstants.marketPlaceModuleURL}${AppConstants.getDrRedflagDetails}?drEmailID=${emailid}&ptMobileNo=${ptMobileNumber}`,
    );
  }

  getDrRedflagMapURL(details): Observable<IAPIResponseWrapper<any>> {
    let drRegID = sessionStorage.getItem('USER_ID');
    let request = {
      id: '1',
      version: 'v1',
      requesttime: '',
      method: 'POST',
      request: {
        drID: drRegID,
        redflag: details.redflag,
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.masterManagementModuleURL + AppConstants.getDrRedflagMapURL,
      request,
    );
  }

  saveOrderDetails(payload) {
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.marketPlaceModuleURL + AppConstants.saveOrderDetails,
      payload,
    );
  }

  saveMarketPlaceDetails(request) {
    return this.httpClient.post<IAPIResponseWrapper<any>>(
      AppConstants.marketPlaceModuleURL + AppConstants.saveMarketPlaceDetails,
      request,
    );
  }

  getTransactionId(): Observable<any> {
    return this.httpClient.get<any>(AppConstants.marketPlaceModuleURL + AppConstants.getTransactionId);
  }

  clearConsulationData() {
    this.patientConsulation = {
      selectedAdvice: null,
      selectedDiagnosis: null,
      selectedMedicines: null,
      selectedSymptoms: null,
    };
  }
}
