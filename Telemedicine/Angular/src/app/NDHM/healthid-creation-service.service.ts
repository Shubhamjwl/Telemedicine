import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ALL_CONSTANTS } from 'src/config/constant';
import { GenerateOtpResp } from '../models/healthidMobile/GenerateOtpResp';
import { MainResponseDTO } from '../models/common/MainResponseDTO';
import { GenerateOtpDTO } from '../models/healthidMobile/GenerateOtpDTO';
import { ResendOtpDTO } from '../models/healthidMobile/ResendOtpDTO';
import { ConfirmOtpDTO } from '../models/healthidMobile/ConfirmOtpDTO';
import { GenerateSessionRespDTO } from '../models/common/GenerateSessionRespDTO';
import { ResendOtpResp } from '../models/healthidMobile/ResendOtpResp';
import { ConfirmOtpResp } from '../models/healthidMobile/ConfirmOtpResp';
import { HealthIDResp } from '../models/healthidMobile/HealthIDResp';
import { HealthIDDTO } from '../models/healthidMobile/HealthIDDTO';
import { MainRequestDTO } from '../models/common/MainRequestDTO';
import { StateDtlsDTO } from '../models/common/StateDtlsDTO';
import { HealthCardDTO } from '../models/healthidMobile/HealthCardDTO';
import { GenerateOtpAadhaarDTO } from '../models/healthidAadhaar/GenerateOtpAadhaarDTO';
import { ConfirmOtpAadhaarDTO } from '../models/healthidAadhaar/ConfirmOtpAadhaarDTO';
import { ConfirmOtpRespAadhaar } from '../models/healthidAadhaar/ConfirmOtpRespAadhaar';
import { HealthIDAadhaarDTO } from '../models/healthidAadhaar/HealthIDAadhaarDTO';
import { UserAuthRespDTO } from '../models/common/UserAuthRespDTO';
import { UserAuthRequestDTO } from '../models/common/UserAuthRequestDTO';
import { SearchByMobDTO } from '../models/common/SearchByMobDTO';
import { SearchByMobRespDTO } from '../models/common/SearchByMobRespDTO';
import { AadharOtpResendRespDTO } from '../models/healthidAadhaar/AadharOtpResendRespDTO';
import { VerifyBioReqDTO } from '../models/healthidAadhaar/VerifyBioReqDTO';
import { AuthFetchModesReqDTO } from '../models/verifyHealthid/AuthFetchModesReqDTO';
import { SearchExistHealthIdRespDTO } from '../models/common/SearchExistHealthIdRespDTO';
import { SearchByHealthIdDTO } from '../models/common/SearchByHealthIdDTO';
import { CreateHealthIdPreverifiedRespDTO } from '../models/healthidAadhaar/CreateHealthIdPreverifiedRespDTO';
import { CreateHealthIdPreverifiedRequestDTO } from '../models/healthidAadhaar/CreateHealthIdPreverifiedRequestDTO';
import { GenerateMobileOTP } from '../models/common/GenerateMobileOTP';

@Injectable({
  providedIn: 'root',
})
export class HealthidCreationServiceService {

  generateToken_url = 'generateToken';
  states_url = 'states';
  districts_url = 'districts';
  generateOTPForMobile_url = 'generateOTPForMobile';
  resendOTPForMobile_url = 'resendOTPForMobile';
  verifyOTPForMobile_url = 'verifyOTPForMobile';
  helathidCreationForMobile_url = 'helathIDcreationForMobile';
  generateOTPForAadhaar_url = 'generateOTPForAadhaar';
  resendOTPForAadhaar_url = 'resendOTPForAadhaar';
  verifyOTPForAadhaar_url = 'verifyOTPForAadhaar';
  generateMobileOTP_url = 'generateMobileOTP';
  verifyMobileOTP_url = 'verifyMobileOTP';
  helathidCreationForAadhaar_url = 'helathIDcreationForAadhaar';
  helathIDcreationForPreVerified_url = 'helathIDcreationForPreVerified';
  userAuthWithPassword_URL = 'userAuthWithPassword';
  getCardInPdf_url = 'getHealthCardInPdf';
  getCardInPng_url = 'getHealthCardInPng';
  searchExistsByHealthId_url = 'searchExistsByHealthId';
  searchHealthIdByMobile_url = 'searchByMobile';
  searchHealthIdByAadhaar_url = 'searchByAadhar';
  searchByHealthId_url = 'searchByHealthId';
  verifyBioForAadhaar_url = 'verifyBioForAadhaar';
  fetch_modes_url = 'v.05/users/auth/fetch_modes';
  init_url = 'v.05/users/auth/init';
  auth_confirm_url = 'v.05/users/auth/auth_confirm';
  generateOtpDTO = new GenerateOtpDTO();
  generateOtpAadhaarDTO = new GenerateOtpAadhaarDTO();
  resendOtpDTO = new ResendOtpDTO();
  confirmOtpDTO = new ConfirmOtpDTO();
  confirmOtpAadhaarDTO = new ConfirmOtpAadhaarDTO();
  generateMobileOTP = new GenerateMobileOTP();
  healthIDDTO = new HealthIDDTO();
  mainResponseDTO = new MainResponseDTO<HealthIDDTO>();
  mainRequestDTO = new MainRequestDTO<HealthIDDTO>();
  mainRequestAadhaarDTO = new MainRequestDTO<HealthIDAadhaarDTO>();
  userAuthRequestDTO = new UserAuthRequestDTO();
  mainRequestSearchByHealthIdDTO = new MainRequestDTO<SearchByHealthIdDTO>();
  mainRequestSearchDTO = new MainRequestDTO<SearchByMobDTO>();
  verifyBioReqDTO = new VerifyBioReqDTO();
  authFetchModesReqDTO = new AuthFetchModesReqDTO();
  createHealthIdPreverifiedRequestDTO = new CreateHealthIdPreverifiedRequestDTO();
  constructor(private httpClient: HttpClient) { }


  generateToken(): Observable<MainResponseDTO<GenerateSessionRespDTO>> {
    let headers_new = {
      'Content-Type': 'application/json',
    };
    console.log('generateToken');
    return this.httpClient.post<MainResponseDTO<GenerateSessionRespDTO>>(this.generateToken_url, { headers: headers_new });
  }

  generateOTPForMobile(mobileNo, refreshToken): Observable<MainResponseDTO<GenerateOtpResp>> {
    console.log('generateOTPForMobile refreshToken::' + refreshToken);
    this.generateOtpDTO = new GenerateOtpDTO();
    this.generateOtpDTO.mobile = mobileNo;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    console.log('generateOTPForMobile mobileNo', mobileNo);
    return this.httpClient.post<MainResponseDTO<GenerateOtpResp>>(this.generateOTPForMobile_url, this.generateOtpDTO, { headers: headers_new });
  }
  resendOTPForMobile(txnId, refreshToken): Observable<MainResponseDTO<ResendOtpResp>> {
    this.resendOtpDTO = new ResendOtpDTO();
    this.resendOtpDTO.authMethod = 'MOBILE_OTP';
    this.resendOtpDTO.txnId = txnId;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    console.log('resendOTPForMobile', txnId);
    return this.httpClient.post<MainResponseDTO<ResendOtpResp>>(this.resendOTPForMobile_url, this.resendOtpDTO, { headers: headers_new });
  }
  verifyOTPForMobile(otp, txnid, refreshToken): Observable<MainResponseDTO<ConfirmOtpResp>> {
    this.confirmOtpDTO = new ConfirmOtpDTO();
    this.confirmOtpDTO.otp = otp;
    this.confirmOtpDTO.txnId = txnid;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    console.log('verifyOTPForMobile', otp);
    return this.httpClient.post<MainResponseDTO<ConfirmOtpResp>>(this.verifyOTPForMobile_url, this.confirmOtpDTO, { headers: headers_new });
  }
  getStates(refreshToken): Observable<MainResponseDTO<StateDtlsDTO[]>> {
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };

    console.log('generateToken');
    return this.httpClient.get<MainResponseDTO<StateDtlsDTO[]>>(this.states_url, { headers: headers_new });
  }

  helathidCreationForMobile(refreshToken, healthIDDTO): Observable<MainResponseDTO<HealthIDResp>> {
    this.mainRequestDTO = new MainRequestDTO<HealthIDDTO>();
    this.mainRequestDTO.request = healthIDDTO;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    return this.httpClient.post<MainResponseDTO<HealthIDResp>>(this.helathidCreationForMobile_url, this.mainRequestDTO, { headers: headers_new });
  }

  getCardInPdf(refreshToken, userAuthToken): Observable<any> {
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
      'X-Token': 'Bearer ' + userAuthToken,
    };
    return this.httpClient.get<any>(this.getCardInPdf_url, { headers: headers_new, 'responseType': 'blob' as 'json' });
  }
  getCardInPng(refreshToken, userAuthToken): Observable<any> {
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
      'X-Token': 'Bearer ' + userAuthToken,
    };
    return this.httpClient.get<any>(this.getCardInPng_url, { headers: headers_new, 'responseType': 'blob' as 'json' });
  }
  generateOTPForAadhaar(mobile, aadhaar, refreshToken): Observable<MainResponseDTO<GenerateOtpResp>> {
    //console.log('refreshToken::'+refreshToken);
    this.generateOtpAadhaarDTO = new GenerateOtpAadhaarDTO();;
    this.generateOtpAadhaarDTO.aadhaar = aadhaar;

    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    console.log('generateOTPForAadhaar', aadhaar);
    return this.httpClient.post<MainResponseDTO<GenerateOtpResp>>(this.generateOTPForAadhaar_url, this.generateOtpAadhaarDTO, { headers: headers_new });
  }
  resendOTPForAadhaar(txnId, refreshToken): Observable<MainResponseDTO<ResendOtpResp>> {
    this.resendOtpDTO = new ResendOtpDTO();
    this.resendOtpDTO.authMethod = '';
    this.resendOtpDTO.txnId = txnId;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    console.log('resendOTPForAadhaar', txnId);
    return this.httpClient.post<MainResponseDTO<ResendOtpResp>>(this.resendOTPForAadhaar_url, this.resendOtpDTO, { headers: headers_new });
  }
  verifyOTPForAadhaar(otp, txnid, refreshToken): Observable<MainResponseDTO<ConfirmOtpRespAadhaar>> {
    this.confirmOtpAadhaarDTO = new ConfirmOtpAadhaarDTO();
    this.confirmOtpAadhaarDTO.otp = otp;
    this.confirmOtpAadhaarDTO.txnId = txnid;
    this.confirmOtpAadhaarDTO.restrictions = '';
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    console.log('verifyOTPForAadhaar', otp);
    return this.httpClient.post<MainResponseDTO<ConfirmOtpRespAadhaar>>(this.verifyOTPForAadhaar_url, this.confirmOtpAadhaarDTO, { headers: headers_new });
  }
  generateAaadhaarMobileOTP(mobile, txnId, refreshToken): Observable<MainResponseDTO<GenerateOtpResp>> {
    //console.log('refreshToken::'+refreshToken);
    this.generateMobileOTP = new GenerateMobileOTP();
    this.generateMobileOTP.mobile = mobile;
    this.generateMobileOTP.txnId = txnId;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    console.log('generateAaadhaarMobileOTP', txnId);
    return this.httpClient.post<MainResponseDTO<GenerateOtpResp>>(this.generateMobileOTP_url, this.generateMobileOTP, { headers: headers_new });
  }
  verifyAaadhaarMobileOTP(otp, txnid, refreshToken): Observable<MainResponseDTO<ConfirmOtpRespAadhaar>> {
    this.confirmOtpDTO = new ConfirmOtpDTO();
    this.confirmOtpDTO.otp = otp;
    this.confirmOtpDTO.txnId = txnid;
    
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    console.log('verifyAaadhaarMobileOTP', otp);
    return this.httpClient.post<MainResponseDTO<ConfirmOtpRespAadhaar>>(this.verifyMobileOTP_url, this.confirmOtpDTO, { headers: headers_new });
  }
  helathidCreationForAadhaar(refreshToken, healthIDDTO): Observable<MainResponseDTO<HealthIDResp>> {
    this.mainRequestAadhaarDTO = new MainRequestDTO<HealthIDAadhaarDTO>();
    this.mainRequestAadhaarDTO.request = healthIDDTO;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    return this.httpClient.post<MainResponseDTO<HealthIDResp>>(this.helathidCreationForAadhaar_url, this.mainRequestDTO, { headers: headers_new });
  }

  userAuthWithPassword(healthId, password, refreshToken): Observable<MainResponseDTO<UserAuthRespDTO>> {
    this.userAuthRequestDTO = new UserAuthRequestDTO();
    this.userAuthRequestDTO.healthId = healthId;
    this.userAuthRequestDTO.password = password;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    console.log('userAuthWithPassword', healthId);
    console.log('userAuthWithPassword', password);
    return this.httpClient.post<MainResponseDTO<UserAuthRespDTO>>(this.userAuthWithPassword_URL, this.userAuthRequestDTO, { headers: headers_new });
  }
  searchExistsByHealthId(refreshToken, searchByHealthIdDTO): Observable<MainResponseDTO<SearchExistHealthIdRespDTO>> {
    this.mainRequestSearchByHealthIdDTO = new MainRequestDTO<SearchByHealthIdDTO>();
    this.mainRequestSearchByHealthIdDTO.request = searchByHealthIdDTO;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    return this.httpClient.post<MainResponseDTO<SearchExistHealthIdRespDTO>>(this.searchExistsByHealthId_url, this.mainRequestSearchByHealthIdDTO, { headers: headers_new });
  }
  searchHealthIdByMobile(refreshToken, searchByMobDTO): Observable<MainResponseDTO<SearchByMobRespDTO>> {
    this.mainRequestSearchDTO = new MainRequestDTO<SearchByMobDTO>();
    this.mainRequestSearchDTO.request = searchByMobDTO;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    return this.httpClient.post<MainResponseDTO<SearchByMobRespDTO>>(this.searchHealthIdByMobile_url, this.mainRequestSearchDTO, { headers: headers_new });
  }
  searchHealthIdByAadhaar(refreshToken, searchByMobDTO): Observable<MainResponseDTO<SearchByMobRespDTO>> {
    this.mainRequestSearchDTO = new MainRequestDTO<SearchByMobDTO>();
    this.mainRequestSearchDTO.request = searchByMobDTO;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    return this.httpClient.post<MainResponseDTO<SearchByMobRespDTO>>(this.searchHealthIdByAadhaar_url, this.mainRequestSearchDTO, { headers: headers_new });
  }
  searchByHealthId(refreshToken, searchByHealthIdDTO): Observable<MainResponseDTO<SearchByMobRespDTO>> {
    this.mainRequestSearchByHealthIdDTO = new MainRequestDTO<SearchByHealthIdDTO>();
    this.mainRequestSearchByHealthIdDTO.request = searchByHealthIdDTO;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    return this.httpClient.post<MainResponseDTO<SearchByMobRespDTO>>(this.searchByHealthId_url, this.mainRequestSearchByHealthIdDTO, { headers: headers_new });
  }
  verifyBioForAadhaar(aadhaar, bioType, pid, refreshToken): Observable<MainResponseDTO<AadharOtpResendRespDTO>> {
    this.verifyBioReqDTO = new VerifyBioReqDTO();
    this.verifyBioReqDTO.aadhaar = aadhaar;
    this.verifyBioReqDTO.bioType = bioType;
    this.verifyBioReqDTO.pid = pid;
    this.verifyBioReqDTO.restrictions = '';
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    console.log('verifyBioForAadhaar aadhaar::', aadhaar);
    console.log('verifyBioForAadhaar bioType::', bioType);
    console.log('verifyBioForAadhaar pid::', pid);
    return this.httpClient.post<MainResponseDTO<AadharOtpResendRespDTO>>(this.verifyBioForAadhaar_url, this.verifyBioReqDTO, { headers: headers_new });
  }
  helathIDcreationForPreVerified(refreshToken, createHealthIdPreverifiedRequestDTO: CreateHealthIdPreverifiedRequestDTO): Observable<MainResponseDTO<CreateHealthIdPreverifiedRespDTO>> {
    this.createHealthIdPreverifiedRequestDTO = new CreateHealthIdPreverifiedRequestDTO();
    this.createHealthIdPreverifiedRequestDTO = createHealthIdPreverifiedRequestDTO;
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
    };
    return this.httpClient.post<MainResponseDTO<CreateHealthIdPreverifiedRespDTO>>(this.helathIDcreationForPreVerified_url, this.createHealthIdPreverifiedRequestDTO, { headers: headers_new });
  }
  getFetchModes(requestId, timestamp, query, refreshToken): Observable<MainResponseDTO<any>> {
    this.authFetchModesReqDTO = new AuthFetchModesReqDTO();
    this.authFetchModesReqDTO.requestId = requestId;
    this.authFetchModesReqDTO.timestamp = timestamp;
    this.authFetchModesReqDTO.query = query;
    this.authFetchModesReqDTO.query.id = '';
    this.authFetchModesReqDTO.query.purpose = '';
    this.authFetchModesReqDTO.query.authMode = '';
    this.authFetchModesReqDTO.query.requester.id = '';
    this.authFetchModesReqDTO.query.requester.type = '';
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
      'X-CM-ID': 'sbx',
    };
    console.log('verifyBioForAadhaar requestId::', requestId);
    console.log('verifyBioForAadhaar timestamp::', timestamp);
    console.log('verifyBioForAadhaar query::', query);
    return this.httpClient.post<MainResponseDTO<any>>(this.fetch_modes_url, this.authFetchModesReqDTO, { headers: headers_new });
  }
  init(requestId, timestamp, query, refreshToken): Observable<MainResponseDTO<any>> {
    this.authFetchModesReqDTO = new AuthFetchModesReqDTO();
    this.authFetchModesReqDTO.requestId = requestId;
    this.authFetchModesReqDTO.timestamp = timestamp;
    this.authFetchModesReqDTO.query = query;
    this.authFetchModesReqDTO.query.id = '';
    this.authFetchModesReqDTO.query.purpose = '';
    this.authFetchModesReqDTO.query.authMode = '';
    this.authFetchModesReqDTO.query.requester.id = '';
    this.authFetchModesReqDTO.query.requester.type = '';
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
      'X-CM-ID': 'sbx',
    };
    console.log('verifyBioForAadhaar requestId::', requestId);
    console.log('verifyBioForAadhaar timestamp::', timestamp);
    console.log('verifyBioForAadhaar query::', query);
    return this.httpClient.post<MainResponseDTO<any>>(this.init_url, this.authFetchModesReqDTO, { headers: headers_new });
  }
  authConfirm(requestId, timestamp, query, refreshToken): Observable<MainResponseDTO<any>> {
    this.authFetchModesReqDTO = new AuthFetchModesReqDTO();
    this.authFetchModesReqDTO.requestId = requestId;
    this.authFetchModesReqDTO.timestamp = timestamp;
    this.authFetchModesReqDTO.query = query;
    this.authFetchModesReqDTO.query.id = '';
    this.authFetchModesReqDTO.query.purpose = '';
    this.authFetchModesReqDTO.query.authMode = '';
    this.authFetchModesReqDTO.query.requester.id = '';
    this.authFetchModesReqDTO.query.requester.type = '';
    let headers_new = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + refreshToken,
      'X-CM-ID': 'sbx',
    };
    console.log('verifyBioForAadhaar requestId::', requestId);
    console.log('verifyBioForAadhaar timestamp::', timestamp);
    console.log('verifyBioForAadhaar query::', query);
    return this.httpClient.post<MainResponseDTO<any>>(this.auth_confirm_url, this.authFetchModesReqDTO, { headers: headers_new });
  }
}
