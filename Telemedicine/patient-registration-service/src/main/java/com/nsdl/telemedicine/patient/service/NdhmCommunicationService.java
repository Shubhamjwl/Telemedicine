package com.nsdl.telemedicine.patient.service;

import javax.validation.Valid;

import com.nsdl.telemedicine.patient.dto.AuthConfirmOtpReqDTO;
import com.nsdl.telemedicine.patient.dto.AuthConfirmOtpRespDTO;
import com.nsdl.telemedicine.patient.dto.AuthInitReqDTO;
import com.nsdl.telemedicine.patient.dto.AuthInitRespDTO;
import com.nsdl.telemedicine.patient.dto.GetProfileReqDTO;
import com.nsdl.telemedicine.patient.dto.GetProfileRespDTO;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.dto.SearchByHealthIdReqDTO;
import com.nsdl.telemedicine.patient.dto.SearchHealthIdToLoginReqDTO;
import com.nsdl.telemedicine.patient.dto.SearchHealthIdToLoginRespDTO;

public interface NdhmCommunicationService {

	ResponseWrapper<SearchHealthIdToLoginRespDTO> searchHealthIdToLogin(
			@Valid RequestWrapper<SearchHealthIdToLoginReqDTO> searchHealthIdReq);

	ResponseWrapper<AuthInitRespDTO> authInit(@Valid RequestWrapper<AuthInitReqDTO> authInitReq);

	ResponseWrapper<AuthConfirmOtpRespDTO> confirmAadhaarOTP(
			@Valid RequestWrapper<AuthConfirmOtpReqDTO> authConfirmOtpReq);

	ResponseWrapper<AuthConfirmOtpRespDTO> confirmMobileOTP(
			@Valid RequestWrapper<AuthConfirmOtpReqDTO> authConfirmOtpReq);

	ResponseWrapper<GetProfileRespDTO> getAccountProfile(@Valid RequestWrapper<GetProfileReqDTO> getProfileReq);

	ResponseWrapper<SearchHealthIdToLoginRespDTO> searchByHealthId(
			@Valid RequestWrapper<SearchByHealthIdReqDTO> searchByHealthIdReq);

}
