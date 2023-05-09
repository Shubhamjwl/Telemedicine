package com.nsdl.ndhm.controller;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.ndhm.dto.ConfirmOtpDTO;
import com.nsdl.ndhm.dto.ConfirmOtpResp;
import com.nsdl.ndhm.dto.GenerateOtpDTO;
import com.nsdl.ndhm.dto.GenerateOtpResp;
import com.nsdl.ndhm.dto.HealthIDDTO;
import com.nsdl.ndhm.dto.HealthIDResp;
import com.nsdl.ndhm.dto.MainRequestDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.ResendOtpDTO;
import com.nsdl.ndhm.dto.ResendOtpResp;
import com.nsdl.ndhm.dto.UserAuthRequestDTO;
import com.nsdl.ndhm.dto.UserAuthRespDTO;
import com.nsdl.ndhm.service.HealthIdCreationByMobileService;

@RestController
@CrossOrigin
public class HealthIdCreationByMobileNoController {

	private static final Logger logger = LoggerFactory.getLogger(HealthIdCreationByMobileNoController.class);

	@Autowired
	HealthIdCreationByMobileService healthIdCreationByMobileService;

	/*
	 * for generate mobile opt
	 */
	@PostMapping(value = "/generateOTPForMobile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<GenerateOtpResp>> generateOTP(@RequestHeader Map<String, String> headers,
			@RequestBody GenerateOtpDTO generateOTPDTO) {
		MainResponseDTO<GenerateOtpResp> mainResponse = null;
		logger.info("Generate OTP Request Received");
		try {
			mainResponse = healthIdCreationByMobileService.generateOTP(headers, generateOTPDTO);
		} catch (Exception e) {
			logger.error("Error in generateOTP {} ", e);
		}
		logger.info("Generate OTP Request Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * for resend mobile opt
	 */
	@PostMapping(value = "/resendOTPForMobile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<ResendOtpResp>> resendOTPForMobile(@RequestHeader Map<String, String> headers,
			@RequestBody ResendOtpDTO resendOTPDTO) {
		MainResponseDTO<ResendOtpResp> mainResponse = null;
		logger.info("Resend OTP Request Received");
		try {
			mainResponse = healthIdCreationByMobileService.resendOTP(headers, resendOTPDTO);
		} catch (Exception e) {
			logger.error("Error in resendOTPForMobile {} ", e);
		}
		logger.info("Resend OTP Request Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * for verify mobile opt
	 */
	@PostMapping(value = "/verifyOTPForMobile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<ConfirmOtpResp>> confirmOTP(@RequestHeader Map<String, String> headers,
			@RequestBody ConfirmOtpDTO confirmOtpDTO) {
		MainResponseDTO<ConfirmOtpResp> mainResponse = null;
		logger.info("Verify OTP Request Received");
		try {
			mainResponse = healthIdCreationByMobileService.confirmOTP(headers, confirmOtpDTO);
		} catch (Exception e) {
			logger.error("Error in confirmOTP {} ", e);
		}
		logger.info("Verify OTP Request Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * create healthid by mobile number
	 */
	@PostMapping(value = "/helathIDcreationForMobile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<HealthIDResp>> saveHealthIdDetails(@RequestHeader Map<String, String> headers,
			@Valid @RequestBody MainRequestDTO<HealthIDDTO> healthIDDTO) {
		MainResponseDTO<HealthIDResp> mainResponse = null;
		logger.info("Registration-HealthId Request Received");
		try {
			mainResponse = healthIdCreationByMobileService.saveHealthIdByMobile(headers, healthIDDTO);
			mainResponse.setId(healthIDDTO.getId());
			mainResponse.setVersion(healthIDDTO.getVersion());
			mainResponse.setResponsetime(healthIDDTO.getRequestTime());
		} catch (Exception e) {
			logger.error("Error in saveHealthIdDetails {} ", e);
		}
		logger.info("Registration-HealthId Request Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * for verify mobile opt
	 */
	@PostMapping(value = "/userAuthWithPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<UserAuthRespDTO>> userAuthWithPassword(
			@RequestHeader Map<String, String> headers, @RequestBody UserAuthRequestDTO userAuthRequestDTO) {

		MainResponseDTO<UserAuthRespDTO> mainResponse = null;
		logger.info("User Auth With Password Request Received");
		try {
			mainResponse = healthIdCreationByMobileService.userAuthWithPassword(headers, userAuthRequestDTO);
		} catch (Exception e) {
			logger.error("Error in userAuthWithPassword {} ", e);
		}

		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * for get health card in pdf format
	 */
	@GetMapping(path = "/getHealthCardInPdf", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> getHealthCardInPdf(@RequestHeader Map<String, String> headers) {
		logger.info("Get Card in Pdf Request Received");

		return healthIdCreationByMobileService.getPdfCard(headers);

	}

	/*
	 * for get health card in png format
	 */
	@GetMapping(path = "/getHealthCardInPng", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getHealthCardInPng(@RequestHeader Map<String, String> headers) {
		logger.info("Get Card in PNG Request Received");
		return healthIdCreationByMobileService.getPngCard(headers);
	}

	/*
	 * create healthid by mobile number
	 */
	@PostMapping(value = "/helathIDcreationForMobileDemo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<HealthIDResp>> saveHealthIdDetailsDemo(
			@RequestHeader Map<String, String> headers, @Valid @RequestBody MainRequestDTO<HealthIDDTO> healthIDDTO) {
		MainResponseDTO<HealthIDResp> mainResponse = null;
		logger.info("Registration-HealthId Request Demo Received");
		try {
			mainResponse = healthIdCreationByMobileService.saveHealthIdDetailsDemo(headers, healthIDDTO);
			mainResponse.setId(healthIDDTO.getId());
			mainResponse.setVersion(healthIDDTO.getVersion());
			mainResponse.setResponsetime(healthIDDTO.getRequestTime());
		} catch (Exception e) {
			logger.error("Error in saveHealthIdDetails {} ", e);
		}
		logger.info("Registration-HealthId Request Demo Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}
}
