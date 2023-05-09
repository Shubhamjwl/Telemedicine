package com.nsdl.user.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.user.dto.BioRequest;
import com.nsdl.user.dto.BioResponse;
import com.nsdl.user.dto.ConsentChangeRequest;
import com.nsdl.user.dto.ConsentChangeResponse;
import com.nsdl.user.dto.MainRequestDTO;
import com.nsdl.user.dto.MainResponseDTO;
import com.nsdl.user.dto.SubmitOtpRequest;
import com.nsdl.user.dto.SubmitOtpResponse;
import com.nsdl.user.dto.SubmitUidRequest;
import com.nsdl.user.dto.SubmitUidResponse;
import com.nsdl.user.dto.SyncRequest;
import com.nsdl.user.dto.SyncResponse;
import com.nsdl.user.service.UserService;
import com.nsdl.user.service.VerifierService;
import com.nsdl.user.util.AuthUtil;

@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;

	@Autowired
	private VerifierService verifierService;

	@SuppressWarnings("unchecked")
	@PostMapping("/submitUid")
	public ResponseEntity<MainResponseDTO<SubmitUidResponse>> submitUid(
			@RequestBody @Valid MainRequestDTO<SubmitUidRequest> request) {
		logger.info("Submit Uid Request Received");
		MainResponseDTO<SubmitUidResponse> response = (MainResponseDTO<SubmitUidResponse>) AuthUtil
				.getMainResponseDto(request);
		response.setResponse(service.submitUid(request.getRequest()));
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/submitOtp")
	public ResponseEntity<MainResponseDTO<SubmitOtpResponse>> submitOtp(
			@RequestBody @Valid MainRequestDTO<SubmitOtpRequest> request) {
		logger.info("Submit otp Request Received");
		MainResponseDTO<SubmitOtpResponse> response = (MainResponseDTO<SubmitOtpResponse>) AuthUtil
				.getMainResponseDto(request);
		response.setResponse(service.submitOtp(request.getRequest()));
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/consentQrCodeVerification")
	public ResponseEntity<MainResponseDTO<ConsentChangeResponse>> consentQrCodeVerification(
			@RequestBody @Valid MainRequestDTO<ConsentChangeRequest> request) {
		logger.info("consent change Request Received");
		MainResponseDTO<ConsentChangeResponse> response = (MainResponseDTO<ConsentChangeResponse>) AuthUtil
				.getMainResponseDto(request);
		response.setResponse(service.consentQrCodeVerification(request.getRequest()));
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/sync")
	public ResponseEntity<MainResponseDTO<SyncResponse>> sync(@RequestBody @Valid MainRequestDTO<SyncRequest> request) {
		logger.info("Sync Request Received");
		MainResponseDTO<SyncResponse> response = (MainResponseDTO<SyncResponse>) AuthUtil.getMainResponseDto(request);
		response.setResponse(verifierService.sync(request.getRequest()));
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/getBioDetails")
	public ResponseEntity<MainResponseDTO<BioResponse>> getBioDetails(@RequestBody MainRequestDTO<BioRequest> bioRequest)
			throws Exception {
		logger.info("Get request to getBioDetails controller");
		MainResponseDTO<BioResponse> response = new MainResponseDTO<BioResponse>();
		BioResponse bioResponse = service.getBioDetails(bioRequest.getRequest());
		response.setId("Uid authentication");
		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		response.setResponse(bioResponse);
		logger.info("Returning response from getBioDetails controller");
		return new ResponseEntity<MainResponseDTO<BioResponse>>(response, HttpStatus.OK);
	}

}
