import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../authSercies/auth.service';
import { AppConstants } from 'src/app/app-constants';
import { IAPIResponseWrapper } from '../../interfaces/api-response-wrapper.interface';
import { IPatient } from '../../interfaces/patient.interface';
import { DummyPaymentRequest } from '../../model/payment/dummy-payment-model';
import { SaveAppointmentDetail, SaveAppointmentDetailResponse } from '../../model/patient/save-appointment-detail.model';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  appointmentDetails = new Subject();
  action = this.appointmentDetails.asObservable();

  constructor(private httpClient: HttpClient, public authService: AuthService) { }

  currentAction(data) {
    this.appointmentDetails.next(data);
  }

  updatePatientProfile(payload): Observable<any> {
    const url = AppConstants.updatePatientProfile; // AppConstants.patientModuleURL + AppConstants.appVersion +
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  getPatientDetails(userID?: string): Observable<IAPIResponseWrapper<IPatient>> {
    return this.httpClient.post<IAPIResponseWrapper<IPatient>>(AppConstants.getPatientDetails, null);
  }

  getPatientcompletedAppointments(): Observable<any> {
    const url = AppConstants.listOfCompletedAppointmentsForPatient; //AppConstants.patientModuleURL + AppConstants.appVersion +
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, null).pipe(map((res) => res));
  }

  getListOfDoctorBySearch(request): Observable<any> {
    let payload = {
      request: request,
    };
    //AppConstants.bookAppointmentModuleURL + AppConstants.appVersion
    const url = AppConstants.bookAppointmentModuleURL + AppConstants.getListOfDoctorBySearchURL;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  getPatientAllDetails(payload): Observable<any> {
    return this.httpClient
      .post<IAPIResponseWrapper<any>>(
        AppConstants.patientModuleURL + AppConstants.appVersion + AppConstants.getPatientAllDetails,
        null,
      )
      .pipe(map((res) => res));
  }

  dummyPaymentApi(payload: DummyPaymentRequest): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      responseType: 'text',
    } as any;
    const request = {
      request: payload,
    };
    const url = AppConstants.bookAppointmentModuleURL + AppConstants.dummyPaymentApi;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, request, httpOptions);
  }

  dummyPaymentApIForExternalLink(payload?): Observable<any> {
    let httpOptions: any = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      responseType: 'text',
    };
    let request = {
      request: payload,
    };
    const url = AppConstants.bookAppointmentModuleURL + AppConstants.dummyPaymentApIForExternalLink;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, request, httpOptions).pipe(map((res) => res));
  }

  saveAppointmentDetails(request: SaveAppointmentDetail): Observable<IAPIResponseWrapper<SaveAppointmentDetailResponse>> {
    let payload = {
      request: request,
    };
    const url = AppConstants.bookAppointmentModuleURL + AppConstants.saveAppointmentDetails;
    return this.httpClient.post<IAPIResponseWrapper<SaveAppointmentDetailResponse>>(url, payload);
  }
  saveAppointmentDetailsUrl(request) {
    let payload = {
      request: request,
    };
    const url = AppConstants.bookAppointmentModuleURL + AppConstants.saveAppointmentDetailsForPatientByLink;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  uploadPatientReport(request): Observable<any> {
    let payload = {
      request: request,
    };
    const url = AppConstants.consultationModuleURL + AppConstants.uploadReport;
    return this.httpClient.post(url, payload).pipe(map((res) => res));
  }
  searchCompletedAppointments(request): Observable<any> {
    let payload = {
      request: request,
    };
    let url;
    if (this.authService.isUserPatient()) {
      url = AppConstants.searchCompltedAppointments; //AppConstants.patientModuleURL + AppConstants.appVersion +
    } else {
      url =
        AppConstants.doctorRegistrationModuleURL +
        AppConstants.appVersion +
        AppConstants.seachCompletedAppointmentsforDoctor;
    }

    return this.httpClient.post(url, payload).pipe(map((res) => res));
  }
  getPrescriptionDetails(data) {
    const url = AppConstants.consultationModuleURL + AppConstants.getPrescriptionDetails;
    return this.httpClient.post(url, data).pipe(map((res) => res));
  }

  savePatientReviews(request): Observable<any> {
    let payload = {
      request: request,
    };
    const url = AppConstants.doctorLikesModuleURL + AppConstants.appVersion + AppConstants.savePatientReviewDtls;
    return this.httpClient.post(url, payload).pipe(map((res) => res));
  }

  getPatientDetailsFromMobileNo(mobile) {
    let payload = {
      request: {
        ptMobNo: mobile,
      },
    };
    const url = AppConstants.patientModuleURL + AppConstants.appVersion + AppConstants.getPatientDetailsByMobNo;
    return this.httpClient.post(url, payload).pipe(map((res) => res));
  }

  patientRegistartionByScribeOrDoctor(request): Observable<any> {
    let payload = {
      id: 'registration',
      version: '1.0',
      requestTime: '2020-10-28T10:35:48.620Z',
      method: 'post',
      request: request,
    };
    const url = AppConstants.patientRegistartionByScribeOrDoctor; //AppConstants.patientModuleURL + AppConstants.appVersion +

    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  patientRegistartionByLink(request): Observable<any> {
    let payload = {
      id: 'registration',
      version: '1.0',
      requestTime: '2020-10-28T10:35:48.620Z',
      method: 'post',
      request: request,
    };
    const url = AppConstants.patientRegistartionByLink; //AppConstants.patientModuleURL + AppConstants.appVersion +

    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  patientRegistrationByDocumentUpload(request) {
    let payload = {
      id: '1',
      version: 'v1.0',
      request: request,
    };
    const url = AppConstants.bulkPatientRegistration; //AppConstants.patientModuleURL + AppConstants.appVersion +

    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }
  getMappedDrListByPatientId(data) {
    let paylaod = {
      request: data,
    };
    const url =
      AppConstants.doctorRegistrationModuleURL + AppConstants.appVersion + AppConstants.getMappedDrListByPatientId;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, paylaod).pipe(map((res) => res));
  }
  unMappedPatientOrDrById(id) {
    let payload;
    if (this.authService.isUserDoctor()) {
      payload = {
        api: 'view doctor profile',
        request: {
          ptRegId: id,
        },
      };
    } else {
      payload = {
        api: 'view doctor profile',
        request: {
          drRegId: id,
        },
      };
    }

    let url = AppConstants.unMappedPatientOrDrById;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  getPatientByName(nameOrMob: string) {
    let payload = {
      request: {
        ptFullName: nameOrMob,
      },
    };
    const url = AppConstants.getPatientDetailsByMobNo; //AppConstants.patientModuleURL + AppConstants.appVersion +
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));

    // if (!nameOrMob) {
    // 	return of([]);
    // }

    // const filteredData = MockResponse.patientList.response.filter(patientDetails => {
    // 	const { ptFullName: fullName } = patientDetails
    // 	return fullName.toLowerCase().includes(nameOrMob.toLowerCase())
    // });

    // return of(filteredData);
  }
  bookAppointmentOnline(payload) {
    let url = AppConstants.bookAppointmentModuleURL + AppConstants.bookAppointmentOnline;

    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }
  patientRegistartionByExternalLink(payload) {
    let url = AppConstants.patientRegistartionByExternalLink;
    console.log('patient external link');
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  getNdhmFlag(): Observable<any> {
    return this.httpClient.get<any>(AppConstants.getNdhmFlag);
  }

  getCancelAppointListDocorPatient() {
    return this.httpClient.get<any>(AppConstants.bookModuleURL + AppConstants.getCancelAppointList);
  }

  getCancelAppointListByFilter(request) {
    let payload = {
      request: request,
    };
    let url = AppConstants.bookModuleURL + AppConstants.getCancelAppointListByFilter;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  searchPatient(request): Observable<IAPIResponseWrapper<any>> {
    let payload = {
      request: {
        healthId: request,
      },
    };
    let url = AppConstants.patientSearchURL;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload);
  }

  checkDoctorTagByPatientId(data) {
    let url = AppConstants.doctorByPatientId + AppConstants.checkDoctorTagByPatientId;
    return this.httpClient.post(url, data);
  }

  getPatientDetailsByMobNoForDoc(data) {
    let url = AppConstants.getPatientDetailsByMobNoForDoc;
    return this.httpClient.post(url, data);
  }

  getPatientDetailsByMobNoForDocVerification(data) {
    let url = AppConstants.getPatientDetailsByMobNoForDocVerification;
    return this.httpClient.post(url, data);
  }

  updateAbhaDetails(data) {
    let url = AppConstants.updateAbhaDetails;
    console.log('Url updateAbhaDetails====>', url);
    return this.httpClient.post(url, data);
  }

  patientRegistartionByScribeOrDoctorWithHealthId(data) {
    console.log('data service ===>', data);
    console.log('data', data);
    let url = AppConstants.patientRegistartionByScribeOrDoctorWithHealthId;
    return this.httpClient.post(url, data);
  }

  searchHealthIdToLogin(data): Observable<IAPIResponseWrapper<any>> {
    let payload = {
      request: {
        healthId: data.healthId,
      },
    };
    let url = AppConstants.searchHealthIdToLogin;
    // let url = 'https://uat.protean-health.com/clinic/ndhm/searchHealthIdToLogin';
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload);
  }

  searchByHealthId(data) {
    let url = AppConstants.searchByHealthId;
    return this.httpClient.post(url, data);
  }

  authInit(data) {
    console.log('authInit_DATA', data);
    let payload = {
      request: {
        authMethod: data.authMethod,
        healthid: data.healthid,
      },
    };
    let url = AppConstants.authInit;
    return this.httpClient.post(url, payload);
  }

  confirmAadhaarOtp(adharData) {
    console.log('DATA', adharData);

    let url = AppConstants.confirmAadhaarOtp;
    return this.httpClient.post(url, adharData);
  }

  confirmMobileOtp(mobileData) {
    console.log('DATA', mobileData);

    let url = AppConstants.confirmMobileOtp;
    return this.httpClient.post(url, mobileData);
  }

  getAccountProfile(data) {
    console.log('DATA', data);
    let url = AppConstants.getAccountProfile;
    return this.httpClient.post(url, data);
  }
}
