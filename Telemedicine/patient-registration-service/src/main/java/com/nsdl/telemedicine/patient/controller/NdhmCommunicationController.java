package com.nsdl.telemedicine.patient.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.nsdl.telemedicine.patient.loggers.PatientLoggingClientInfo;
import com.nsdl.telemedicine.patient.service.NdhmCommunicationService;

@RestController
@RequestMapping("/ndhm")
@PatientLoggingClientInfo
public class NdhmCommunicationController {
	private static final Logger logger = LoggerFactory.getLogger(NdhmCommunicationController.class);

	@Autowired
	private NdhmCommunicationService ndhmCommunicationService;

	/**
	 * added by jinesh for search abha details in ABDM
	 * 
	 * @param searchHealthIdReq
	 * @return
	 */
	@PostMapping(path = "/searchHealthIdToLogin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<SearchHealthIdToLoginRespDTO>> searchHealthIdToLogin(
			@Valid @RequestBody RequestWrapper<SearchHealthIdToLoginReqDTO> searchHealthIdReq) {
		logger.info("searchHealthIdToLogin Method Starts");
		return ResponseEntity.status(HttpStatus.OK)
				.body(ndhmCommunicationService.searchHealthIdToLogin(searchHealthIdReq));
	}

	/**
	 * added by jinesh for auth Init
	 * 
	 * @param authInitReq
	 * @return transaction Id
	 */
	@PostMapping(path = "/authInit", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<AuthInitRespDTO>> authInit(
			@Valid @RequestBody RequestWrapper<AuthInitReqDTO> authInitReq) {
		logger.info("authInit Method Starts");
		return ResponseEntity.status(HttpStatus.OK).body(ndhmCommunicationService.authInit(authInitReq));
	}

	/**
	 * added by jinesh for confirm aadhaar otp
	 * 
	 * @param authConfirmOtpReq
	 * @return token
	 */
	@PostMapping(path = "/confirm-aadhaar-otp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<AuthConfirmOtpRespDTO>> confirmAadhaarOTP(
			@Valid @RequestBody RequestWrapper<AuthConfirmOtpReqDTO> authConfirmOtpReq) {
		logger.info("confirmAadhaarOTP Method Starts");
		return ResponseEntity.status(HttpStatus.OK).body(ndhmCommunicationService.confirmAadhaarOTP(authConfirmOtpReq));
	}

	/**
	 * added by jinesh for confirm mobile otp
	 * 
	 * @param authConfirmOtpReq
	 * @return token
	 */
	@PostMapping(path = "/confirm-mobile-otp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<AuthConfirmOtpRespDTO>> confirmMobileOTP(
			@Valid @RequestBody RequestWrapper<AuthConfirmOtpReqDTO> authConfirmOtpReq) {
		logger.info("confirmMobileOTP Method Starts");
		return ResponseEntity.status(HttpStatus.OK).body(ndhmCommunicationService.confirmMobileOTP(authConfirmOtpReq));
	}

	/**
	 * added by jinesh for confirm mobile otp
	 * 
	 * @param getProfileReq
	 * @return token
	 */
	@PostMapping(path = "/getAccountProfile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<GetProfileRespDTO>> getAccountProfile(
			@Valid @RequestBody RequestWrapper<GetProfileReqDTO> getProfileReq) {
		logger.info("getAccountProfile Method Starts");
		return ResponseEntity.status(HttpStatus.OK).body(ndhmCommunicationService.getAccountProfile(getProfileReq));
	}

	/**
	 * added by jinesh for search by healthid for abha details in ABDM
	 * 
	 * @param searchByHealthIdReq
	 * @return
	 */
	@PostMapping(path = "/searchByHealthId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<SearchHealthIdToLoginRespDTO>> searchByHealthId(
			@Valid @RequestBody RequestWrapper<SearchByHealthIdReqDTO> searchByHealthIdReq) {
		logger.info("searchByHealthId Method Starts");
		return ResponseEntity.status(HttpStatus.OK)
				.body(ndhmCommunicationService.searchByHealthId(searchByHealthIdReq));
	}
}
