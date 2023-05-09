import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from 'src/app/app-constants';
import { Utility } from 'src/app/utility/Utility';
import { IAPIResponseWrapper } from '../../interfaces/api-response-wrapper.interface';
import { SuccessResponse, PtDocumentResponse, PtDocumentType } from '../../';
import { UploadDocument } from '../../model/document/upload-document.model';

@Injectable({
  providedIn: 'root',
})
export class PatientDocumentService {
  constructor(private httpClient: HttpClient) { }

  getDocumentTypes(): Observable<IAPIResponseWrapper<PtDocumentType[]>> {
    const url = AppConstants.masterManagementModuleURL + AppConstants.getAllDocumentList;
    return this.httpClient.get<IAPIResponseWrapper<PtDocumentType[]>>(url);
  }

  getAllDocuments(): Observable<IAPIResponseWrapper<PtDocumentResponse[]>> {
    let url = AppConstants.documentManagementModulleURL + AppConstants.getAllDocumentDtls;
    return this.httpClient.get<IAPIResponseWrapper<PtDocumentResponse[]>>(url);
  }

  uploadPatientDocument(request: UploadDocument): Observable<IAPIResponseWrapper<SuccessResponse>> {
    const payload = Utility.formatRequestPayload(request);
    const url = AppConstants.documentManagementModulleURL + AppConstants.uploadPatientDocument;
    return this.httpClient.post<IAPIResponseWrapper<SuccessResponse>>(url, payload);
  }

  deletePatientDocument(id: number): Observable<IAPIResponseWrapper<SuccessResponse>> {
    const url = `${AppConstants.documentManagementModulleURL}${AppConstants.deletePatientDocument}/${id}`;
    return this.httpClient.delete<IAPIResponseWrapper<SuccessResponse>>(url);
  }
}
