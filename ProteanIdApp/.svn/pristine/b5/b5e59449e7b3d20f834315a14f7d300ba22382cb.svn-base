package com.nsdl.authenticate.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.authenticate.dto.BioRequest;
import com.nsdl.authenticate.dto.BioResponse;
import com.nsdl.authenticate.dto.MainResponsAuthenticateUidViaOtpDTO;
import com.nsdl.authenticate.dto.ResponseWrapper;
import com.nsdl.authenticate.dto.VerifyUidOtpRequest;
import com.nsdl.authenticate.dto.VerifyUidOtpResponse;
import com.nsdl.authenticate.dto.VerifyUidViaOtpRequest;
import com.nsdl.authenticate.service.UinAuthenticateService;


@RestController
@CrossOrigin("*")
public class ProteanIdAppController {

	@Autowired
	private UinAuthenticateService authenticateService;

	private static final Logger logger = LoggerFactory.getLogger(ProteanIdAppController.class);

	
	@PostMapping("/verifyUidOtp")
	public ResponseEntity<ResponseWrapper<VerifyUidOtpResponse>> verifyUidOtpBased(@RequestBody VerifyUidOtpRequest verifyUidRequest) {
		logger.info("Get request to verifyUidOtpBased api");
		ResponseWrapper<VerifyUidOtpResponse> response=new ResponseWrapper<VerifyUidOtpResponse>();
		VerifyUidOtpResponse verifyUidOtpResponse = authenticateService.verifyUidOtp(verifyUidRequest);
		response.setId("Uid authentication");
		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		response.setResponse(verifyUidOtpResponse);
		logger.info("Returning response from verifyUidOtpBased api");
		return new ResponseEntity<ResponseWrapper<VerifyUidOtpResponse>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/verifyUidVerifyOtp")
	public ResponseEntity<ResponseWrapper<MainResponsAuthenticateUidViaOtpDTO>> verifyUidViaOtpResidentService(@RequestBody VerifyUidViaOtpRequest  verifyUidViaOtpRequest)throws Exception {
		logger.info("Get request to verifyUidViaOtpResidentService controller");
		ResponseWrapper<MainResponsAuthenticateUidViaOtpDTO> response=new ResponseWrapper<MainResponsAuthenticateUidViaOtpDTO>();
		MainResponsAuthenticateUidViaOtpDTO verifyUidViaOtpResidentService = authenticateService.verifyUidViaOtpResidentService(verifyUidViaOtpRequest);
		response.setId("Uid authentication");
		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		response.setResponse(verifyUidViaOtpResidentService);
		logger.info("Returning response from verifyUidViaOtpResidentService controller");
		return new ResponseEntity<ResponseWrapper<MainResponsAuthenticateUidViaOtpDTO>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/getBioDetails")
	public ResponseEntity<ResponseWrapper<BioResponse>> getBioDetails(@RequestBody BioRequest bioRequest)
			throws Exception {
		logger.info("Get request to getBioDetails controller");
		ResponseWrapper<BioResponse> response = new ResponseWrapper<BioResponse>();
		BioResponse bioResponse = authenticateService.getBioDetails(bioRequest);
		response.setId("Uid authentication");
		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		response.setResponse(bioResponse);
		logger.info("Returning response from getBioDetails controller");
		return new ResponseEntity<ResponseWrapper<BioResponse>>(response, HttpStatus.OK);
	}

}
