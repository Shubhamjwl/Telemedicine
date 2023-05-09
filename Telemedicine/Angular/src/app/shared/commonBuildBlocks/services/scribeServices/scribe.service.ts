import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppConstants } from 'src/app/app-constants';
import { IAPIResponseWrapper } from '../../interfaces/api-response-wrapper.interface';
import { AuthService } from '../authSercies/auth.service';
import { ConsultationService } from '../ConsultationServices/consultation.service';
import { DataPassingService } from '../dataPassingServices/dataPassing.service';

@Injectable({
  providedIn: 'root'
})
export class ScribeService {

  constructor(
    private httpClient: HttpClient,
    private activatedRoute: ActivatedRoute,
    public authService: AuthService,
    public dataPassingService: DataPassingService,
    public consultationService: ConsultationService
  ) { }

  registerScribe(data) {
    let payload = {
      request: data
    }
    return this.httpClient.post('scribe-service/v1/scribeRegistration', payload);
  }

  selectedScribeByDoctor(data) {
    let payload = {
      version:"1.0",
      request: data
    }
    let url = AppConstants.bookModuleURL + AppConstants.assignScribeToApptByDoctor;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payload);

    // return this.httpClient.post('consultation/appointment/assignScribeToApptByDoctor', payload);
  }

  updateScribeProfile(payload):Observable<any> {

    return this.httpClient.post<IAPIResponseWrapper<any>>(AppConstants.scribeService + AppConstants.appVersion + AppConstants.updateScribeProfile, payload).pipe(map(res=>res));
  }

  getScribeProfileDetails():Observable<any> {

    return this.httpClient.get<IAPIResponseWrapper<any>>(AppConstants.scribeService + AppConstants.appVersion + AppConstants.getScribeDetails).pipe(map(res=>res));
  }
  changeScribeStatus(payload):Observable<any> {
    return this.httpClient.post<IAPIResponseWrapper<any>>(AppConstants.doctorRegistrationModuleURL + AppConstants.appVersion + AppConstants.activeDeactiveScribe, payload).pipe(map(res=>res));
  }
  getScribeList(payLoad):Observable<any>{
    let app = AppConstants;
    let url = app.doctorRegistrationModuleURL + app.appVersion + app.getScribeListByDoctorToActiveDeactive;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, payLoad).pipe(map(res=>res));
  }
  setAsDefaultScribe(data):Observable<any>{
    let app = AppConstants;
    let url = app.doctorRegistrationModuleURL + app.appVersion + app.changeDefaultScribe;
    return this.httpClient.post<IAPIResponseWrapper<any>>(url, data).pipe(map(res=>res));
  }

  getScribeListByDoctorID() {
    this.consultationService.getScribeList().subscribe(result => {
      if(result.response){
  
        result.response.scribeDtls.forEach(item => {
          item.scribeAdded = 'false'
        })
        let scribeList = result.response.scribeDtls;
        this.dataPassingService.docScribeList.next(scribeList);
      }else if(result.errors) {
        console.log('Error:', result.errors.message);
      }
    }, error =>{
      console.log('Error:', error);
    })
  }
  
}
