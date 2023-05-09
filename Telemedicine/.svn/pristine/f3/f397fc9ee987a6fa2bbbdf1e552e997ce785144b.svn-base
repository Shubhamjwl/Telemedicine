import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { Utility } from 'src/app/utility/Utility';
import { IAPIResponseWrapper } from '../../interfaces/api-response-wrapper.interface';
import {
  AdminConfig,
  AdminConfigRequest,
  UpdateCategoryRequest,
  UpdateCategoryResponse,
  AdminFilterResponse,
  AdminFilterRequest,
  AdminDoctorList
} from '../../';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private baseUrl = 'admin';

  constructor(private httpClient: HttpClient) { }

  getAdminConfig(config: AdminConfigRequest): Observable<IAPIResponseWrapper<AdminConfig[]>> {
    const payload = {
      id: 1,
      version: 'v1.0',
      request: config,
    };
    return this.httpClient.post<IAPIResponseWrapper<AdminConfig[]>>(`${this.baseUrl}/adminConfig`, payload);
  }

  updateCategoryStatus(request: UpdateCategoryRequest[]): Observable<IAPIResponseWrapper<UpdateCategoryResponse>> {
    const payload = {
      id: 1,
      version: 'v1.0',
      request: request,
    };
    return this.httpClient.put<IAPIResponseWrapper<UpdateCategoryResponse>>(
      `${this.baseUrl}/updateCategoryStatus`,
      payload,
    );
  }

  getAllDoctorList(): Observable<IAPIResponseWrapper<AdminDoctorList[]>> {
    return this.httpClient.get<IAPIResponseWrapper<AdminDoctorList[]>>(`${this.baseUrl}/getAllDoctorList`).pipe(shareReplay(1));
  }

  getUsersByFilter(data: AdminFilterRequest): Observable<IAPIResponseWrapper<AdminFilterResponse[]>> {
    const request = Utility.formatRequestPayload(data);
    return this.httpClient.post<IAPIResponseWrapper<AdminFilterResponse[]>>(`${this.baseUrl}/adminSendAlert`, request);
  }
}
