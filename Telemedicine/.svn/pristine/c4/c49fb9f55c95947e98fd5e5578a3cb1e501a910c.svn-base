import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { UpdateCategoryRequest } from '../../model/admin/admin-config.model';
import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from '../ConstantsServices/constants.service';
import { ApiService } from '../api.service';
import { IAPIResponseWrapper } from '../../interfaces/api-response-wrapper.interface';
import { AppConstants } from 'src/app/app-constants';
import { IDoctorDetails } from '../../interfaces/doctor-details.interface';

@Injectable({
  providedIn: 'root',
})
export class DoctorService {
  private doctorProfile: IDoctorDetails;

  private categoryDtlsByDrId$: ReplaySubject<UpdateCategoryRequest>;

  get doctorDetails(): IDoctorDetails {
    return this.doctorProfile;
  }

  constructor(
    private httpClient: HttpClient,
    public constantsService: ConstantsService,
    public apiService: ApiService,
    private toastrMessage: ToastMessageService,
  ) {
    this.fetchDoctorConstants();
  }

  updateDoctorProfile(payload): Observable<any> {
    return this.httpClient
      .post<IAPIResponseWrapper<any>>(
        AppConstants.doctorRegistrationModuleURL + AppConstants.appVersion + AppConstants.updateDoctorProfile,
        payload,
      )
      .pipe(map((res) => res));
  }

  getAvailableSlotListByDoctor(request): Observable<any> {
    let payload = {
      request: request,
    };
    //AppConstants.bookAppointmentModuleURL + AppConstants.appVersion
    const url = AppConstants.bookAppointmentModuleURL + AppConstants.getAvailableSlotListByDoctor;
    return this.httpClient.post(url, payload).pipe(map((res) => res));
  }

  getDoctorProfile(userID: string): Observable<IAPIResponseWrapper<IDoctorDetails>> {
    const payload = {
      request: userID,
    };
    return this.httpClient
      .post<IAPIResponseWrapper<IDoctorDetails>>(
        AppConstants.doctorRegistrationModuleURL + AppConstants.appVersion + AppConstants.viewDoctorProfile,
        payload,
      )
      .pipe(
        map((res) => {
          console.log(res);
          this.doctorProfile = res?.response;
          return res;
        }),
      );
  }

  /**
   * @description call api on first time only
   */
  fetchCategoryDtlsByDrId(): Observable<UpdateCategoryRequest> {
    if (!this.categoryDtlsByDrId$) {
      this.categoryDtlsByDrId$ = new ReplaySubject(1);
      this.httpClient
        .get<IAPIResponseWrapper<UpdateCategoryRequest>>(
          AppConstants.doctorRegistrationModuleURL + AppConstants.appVersion + AppConstants.fetchCategoryDtlsByDrId,
        )
        .subscribe({
          next: (res) => {
            if (res.status) {
              this.categoryDtlsByDrId$.next(res.response);
            } else {
              this.categoryDtlsByDrId$ = null;
              this.toastrMessage.showErrorMsg(res.errors?.message, 'Error');
            }
          },
          error: (err) => {
            this.categoryDtlsByDrId$ = null;
            this.toastrMessage.showErrorMsg(err?.error?.errors[0]?.message, 'Error');
          },
        });
    }
    return this.categoryDtlsByDrId$;
  }

  uploadHeaderFooter(request) {
    let payload = {
      api: 'upload prescription template',
      id: 'upload prescription template',
      version: 'v1.0',
      requesttime: '2020-11-28T05:48:03.125Z',
      request: request,
    };
    return this.httpClient
      .post<IAPIResponseWrapper<any>>(
        AppConstants.consultationModuleURL + AppConstants.uploadPrescriptionTemplate,
        payload,
      )
      .pipe(map((res) => res));
  }

  deRegisterDoctor(data) {
    let payload = {
      id: 'appointment',
      request: data,
    };
    let url = AppConstants.doctorDeRegistrationModuleURL + AppConstants.appVersion + AppConstants.deRegisterDoctor;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  VerifyDoctor(data) {
    let payload = {
      id: 'appointment',
      request: data,
    };
    let url =
      AppConstants.doctorDeRegistrationModuleURL + AppConstants.appVersion + AppConstants.verifiedOTPForDocDeRegister;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  fetchDoctorConstants() {
    this.constantsService.getState();
    this.constantsService.getCity();
    this.constantsService.getDoctorSpecialization();
    this.constantsService.getDocumentTypes();
  }

  get(url, param) {
    return new Promise((resolve, reject) => {
      this.apiService.postApiCall(url, param).subscribe((resp: any) => {
        if (resp) {
          if (resp.status) {
            resolve(resp.response);
          } else if (resp.errors && resp.errors.length) {
            reject(resp.errors[0].message);
          } else {
            reject(this.apiService.commonStrings.http_error);
          }
        } else {
          reject(this.apiService.commonStrings.http_error);
        }
      });
    });
  }

  getLikesCountOfDoctor(request): Observable<any> {
    let payload = {
      request: request,
    };
    const url = AppConstants.doctorLikesModuleURL + AppConstants.appVersion + AppConstants.getNumberOfLikesToDoctor;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }

  getStoriesCountOfDoctor(request): Observable<any> {
    let payload = {
      request: request,
    };
    const url = AppConstants.doctorLikesModuleURL + AppConstants.appVersion + AppConstants.getNumberOfCommentsToDoctor;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload).pipe(map((res) => res));
  }
  getMappedDrListByPatientId(data) {
    let paylaod = {
      request: data,
    };
    const url = AppConstants.getMappedPatientListByDrId;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, paylaod).pipe(map((res) => res));
  }

  checkDoctorTagByPatientId(drId, ptRegId): Observable<IAPIResponseWrapper<any>> {
    let paylaod = {
      request: {
        drRegId: drId,
        ptRegId: ptRegId,
      },
    };

    const url =
      AppConstants.doctorRegistrationModuleURL + AppConstants.appVersion + AppConstants.checkDoctorTagByPatientId;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, paylaod);
  }
}
