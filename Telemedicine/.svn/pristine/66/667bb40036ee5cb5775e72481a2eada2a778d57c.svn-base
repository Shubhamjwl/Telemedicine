import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { AppConstants } from 'src/app/app-constants';
import { MasterTypes } from '../../enum/master-type.enum';
import { ToastMessageService } from '../../toaster/toast-message.service';
import { ApiService } from '../api.service';
import { ConsultationService } from '../ConsultationServices/consultation.service';

@Injectable({
  providedIn: 'root'
})
export class ConstantsService {
  constants: any;
  constructor(public apiService: ApiService,
			  public toastrMessage: ToastMessageService,
			  public consultationService: ConsultationService) {
		this.constants = {
			genders:[{"key": "Male","value": "M"}, {"key": "Female", "value": "F"}],
			bloodGroupTypes : [
				{
				  "key": "A+",
				  "value": "A+"
				},
				{
				  "key": "A-",
				  "value": "A-"
				},
				{
				  "key": "B+",
				  "value": "B+"
				},
				{
				  "key": "B-",
				  "value": "B-"
				},
				{
				  "key": "O+",
				  "value": "O+"
				},
				{
				  "key": "O-",
				  "value": "O-"
				},
				{
				  "key": "AB+",
				  "value": "AB+"
				},
				{
				  "key": "AB-",
				  "value": "AB-"
				},
			],
			cities: [],
			states:[],
			specializationList:[],
			documentTypes:[]
		}
  }
  getState(){
	let payload = {
		request: {
		  countryName: 'INDIA'
		}
	}
	//masterManagementModuleURL
	this.apiService.lookupCall(AppConstants.masterManagementModuleURL + AppConstants.getStateList, payload).subscribe(result => {
	  if(result['status'] && result['response']){
		this.constants.states = result['response']
	  }else if(result['errors']) {
		this.toastrMessage.showErrorMsg( result['errors'][0].message, 'Error');
	  }
	},error => {
	  this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
	})
  }
  
  /**
   * Used to get State
  */
  getCity(){
	let payload = {
		request: {
		  stateName: 'MAHARASHTRA',
		}
	  }
	  //AppConstants.masterManagementModuleURL + AppConstants.appVersion +
	this.apiService.lookupCall(AppConstants.masterManagementModuleURL + AppConstants.getCityList, payload).subscribe(result => {
	  if(result['status'] && result['response']){
		this.constants.cities = result['response'];
	  }
	},error => {
	  console.log(error["errors"]);
	 // this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
	})
  }
  getDoctorSpecialization() {
    
    this.consultationService.getMasterDetailsListByMasterName(MasterTypes.SPECIALIZATION).subscribe(result => {
      if(result.status && result.response){
        this.constants.specializationList = result.response
      }else if(result.errors) {
        this.toastrMessage.showErrorMsg( result.errors[0].message, 'Error');
      }
    },error => {
      this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
    })
  }
  getDocumentTypes() {
    this.consultationService.getMasterDetailsListByMasterName(MasterTypes.DOCUMENTTYPE).subscribe(result => {
      if(result.status && result.response){
        this.constants.documentTypes = result.response
      }else if(result.errors) {
        this.toastrMessage.showErrorMsg( result.errors[0].message, 'Error');
      }
    },error => {
      this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
    })
  }
}
