import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppConstants } from 'src/app/app-constants';
import { IAPIResponseWrapper } from '../../interfaces/api-response-wrapper.interface';
import { IAppointmentDetails, IConsultationList } from '../../index';
import { Utility } from 'src/app/utility/Utility';
@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  constructor(private httpClient: HttpClient) { }

  /**
   * getAppointmentList function is used to get appointment list for doctor
   * @param doctorId used to store doctor ID
   */
  getAppointmentList(doctorId, selectedDate) {
    let params = new HttpParams();
    params = params.set('apptDate', selectedDate);

    let payload = {
      api: 'get Consultation List By DoctorID',
      id: '1',
      version: 'v1.0',
      requesttime: '2020-11-28T05:48:03.125Z',
      request: {
        appointmentDate: selectedDate ? selectedDate : null,
      },
      mimeType: 'application/json',
    };
    // return this.httpClient.get<IAPIResponseWrapper<{drRegID: string, apptDtls: IAppointmentDetails}>>('consultation/appointment/getConsultationListByDoctorID',{params})
    return this.httpClient
      .post<IAPIResponseWrapper<IConsultationList>>(
        AppConstants.consultationModuleURL + AppConstants.getConsultationListByDoctorID,
        payload,
      )
      .pipe(map((res) => res.response));
  }

  getScheduledAndRescheduledConsultationList(selectedDate: string): Observable<IAPIResponseWrapper<IConsultationList>> {
    const payload = {
      api: 'get Consultation List By DoctorID',
      id: '1',
      version: 'v1.0',
      requesttime: new Date(),
      request: {
        appointmentDate: selectedDate || null,
      },
      mimeType: 'application/json',
    };
    return this.httpClient.post<IAPIResponseWrapper<IConsultationList>>(AppConstants.consultationModuleURL + AppConstants.getScheduledAndRescheduledConsultationList, payload);
  }


  getScheduledAndRescheduledConsultationListByPatientId(ptRegID) {
    return this.httpClient.post<IAPIResponseWrapper<IConsultationList>>(AppConstants.consultationModuleURL + AppConstants.getScheduledAndRescheduledConsultationListByPatientId, null);
  }


  /**
   * getAppointmentList function is used to get appointment list for doctor
   * @param doctorId used to store doctor ID
   */
  getAppointmentListOfDoc(doctorId, selectedDate) {
    let params = new HttpParams();
    params = params.set('apptDate', selectedDate);

    let payload = {
      api: 'get Consultation List By DoctorID',
      id: '1',
      version: 'v1.0',
      requesttime: '2020-11-28T05:48:03.125Z',
      request: {
        appointmentDate: selectedDate ? selectedDate : null,
      },
      mimeType: 'application/json',
    };
    // return this.httpClient.get<IAPIResponseWrapper<{drRegID: string, apptDtls: IAppointmentDetails}>>('consultation/appointment/getConsultationListByDoctorID',{params})
    return this.httpClient
      .post<
        IAPIResponseWrapper<{
          drRegID: string;
          apptDtls: IAppointmentDetails;
          doctorConsultFee;
        }>
      >(AppConstants.consultationModuleURL + AppConstants.getConsultationListByDoctorID, payload)
      .pipe(map((res) => res));
  }
  /**
   * getAppointmentListForScribe function is used to get list of appointments assigned by doctor to scribe
   */
  getAppointmentListForScribe() {
    //AppConstants.bookModuleURL + AppConstants.appVersion +
    let url = AppConstants.bookModuleURL + AppConstants.assignedApptListToScribe;
    let payload = {
      version: '1.0',
      request: {
        scribeID: sessionStorage.getItem('USER_ID'),
      },
    };
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res.response));
  }

  /**
   * cancelAppointment used to cancel patient's own booked appointment
   * @param data is used to store data required to cancel appointment
   */
  cancelAppointment(data): Observable<IAPIResponseWrapper<any>> {
    const paylaod = {
      version: '1.0',
      request: data,
    };
    const url = AppConstants.bookModuleURL + AppConstants.cancelAppt;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, paylaod);
  }

  rescheduleAppt(data: any): Observable<IAPIResponseWrapper<any>> {
    const request = Utility.formatRequestPayload(data);
    const url = AppConstants.bookModuleURL + AppConstants.saveRescheduleApi;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, request);
  }

  getMenusList() {
    let paylaod = {
      id: 'user',
      version: '1.0',
      requesttime: '2020-01-02T10:01:21.331Z',
      request: {
        roleName: sessionStorage.getItem('ROLE') ? sessionStorage.getItem('ROLE').toLowerCase() : null,
      },
    };
    let url = AppConstants.menuList;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, paylaod).pipe(map((res) => res));
  }

  getAppointmentHistory() {
    let payload = {
      id: 'slot',
      request: '',
    };
    let url = AppConstants.slotManagement + AppConstants.getApptHistory;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }
  getHistoricalSlotDetailsByMonth(payload): Observable<any> {
    let url = AppConstants.slotManagement + AppConstants.getHistoricalSlotDetailsByMonth;

    return this.httpClient.post(url, payload).pipe(map((res) => res));
  }

  deleteSlotDetails(params): Observable<any> {
    let httpOptions: any = {
      observe: 'response',
      body: params,
    };
    let url = AppConstants.slotManagement + AppConstants.deleteSlotDetails;

    return this.httpClient.delete(url, httpOptions).pipe(map((res) => res));
  }

  updateStartTimeByAppointmentId(appointmentId: string) {
    const params = { appointmentId };
    const url = AppConstants.bookModuleURL + AppConstants.updateStartTimeByAppointmentId;
    this.httpClient.put(url, {}, { params }).subscribe();
  }
}
