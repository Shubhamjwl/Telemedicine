import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { AppConstants } from 'src/app/app-constants';


@Injectable({
  providedIn: 'root'
})
export class HomePageService {

  constructor(
    private httpClient: HttpClient,

  ) { }
  
  getDoctorsCount(payload): Observable<any> {
    return this.httpClient.post(AppConstants.doctorRegistrationModuleURL + AppConstants.appVersion + AppConstants.getCountOfDoctors, payload).pipe(map(res=>res));
  }

  getSpecialistsCount(payload): Observable<any> {
    //AppConstants.masterManagementModuleURL + AppConstants.appVersion + 
    return this.httpClient.post(AppConstants.masterManagementModuleURL + AppConstants.getCountOfSpeciality, payload).pipe(map(res=>res));
  }

  getConsultationCount(payload): Observable<any> {
    return this.httpClient.post(AppConstants.consultationModuleURL + AppConstants.getCountOfConsultation, payload).pipe(map(res=>res));
  }

}
