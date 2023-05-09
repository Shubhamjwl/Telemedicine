import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { AppConstants } from 'src/app/app-constants';

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {

  constructor(
    private httpClient: HttpClient
  ) { }

  	/**
	 * ordersearch used for API call of order history search
	 * @param data used to search data required to  search order history details 
	 */
    ordersearch(data){
		let url = AppConstants.marketPlaceModuleURL + AppConstants.orderHistorySearchModuleURL;
		return this.httpClient.post(url, data);
	}
}
