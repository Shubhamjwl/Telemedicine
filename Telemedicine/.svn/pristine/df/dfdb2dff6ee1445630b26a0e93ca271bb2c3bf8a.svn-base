import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { IAPIResponseWrapper } from '../interfaces/api-response-wrapper.interface';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
   commonStrings: any;
  constructor(public http: HttpClient) {
	this.commonStrings = {
		http_error: 'something went wrong'
	}
   }
   
	getApiCall(url, param, headers) {

		if(!headers || !headers['Content-Type']){
			headers = new HttpHeaders({'Content-Type': 'application/json'});
		}

		return new Promise((resolve,reject) => {
			this.http.get(url,{params:param, headers:headers})
			.pipe(map(res => res as {}), catchError(this.handleError))
			.subscribe(resp => {
				resolve(resp);
			}, (error) => {
				reject(this.commonStrings.http_error);
			})
		})
	}

	postApi(url, param, headers){
		if(!headers || !headers['Content-Type']){
			headers = new HttpHeaders({'Content-Type': 'application/json'});
		}

		return new Promise((resolve,reject) => {
			this.http.post( url, param, { headers : headers })
			.pipe(map(res => res as {}), catchError(this.handleError))
			.subscribe(resp => {
				resolve(resp);
			}, (error) => {
				reject(this.commonStrings.http_error);
			})
		})
	}

	handleError(): Observable<Response> {
			return Observable.throw(this.commonStrings.http_error);
	}

	lookupCall(url, payload){
		return this.http.post<IAPIResponseWrapper<any>>(url, payload)
		.pipe(
		map(res => res)
		);
	}
	postApiCall(url, payload){
		return this.http.post(url, payload).pipe(map(res=>res));
	}
}
