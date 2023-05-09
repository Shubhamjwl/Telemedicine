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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.ndhm.dto.AadharOtpResendRespDTO;
import com.nsdl.ndhm.dto.ConfirmOtpAadhaarDTO;
import com.nsdl.ndhm.dto.ConfirmOtpDTO;
import com.nsdl.ndhm.dto.ConfirmOtpRespAadhaar;
import com.nsdl.ndhm.dto.CreateHealthIdPreverifiedRequestDTO;
import com.nsdl.ndhm.dto.CreateHealthIdPreverifiedRespDTO;
import com.nsdl.ndhm.dto.GenerateMobileOTP;
import com.nsdl.ndhm.dto.GenerateOtpAadhaarDTO;
import com.nsdl.ndhm.dto.GenerateOtpResp;
import com.nsdl.ndhm.dto.HealthIDAadhaarDTO;
import com.nsdl.ndhm.dto.HealthIDResp;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.ResendOtpDTO;
import com.nsdl.ndhm.dto.VerifyBioReqDTO;
import com.nsdl.ndhm.service.HealthIdCreationByAdharService;

@RestController
@CrossOrigin
public class HealthIdCreationByAdharController {
	private static final Logger logger = LoggerFactory.getLogger(HealthIdCreationByAdharController.class);

	@Autowired
	HealthIdCreationByAdharService healthIdCreationByAdharService;

	/*
	 * for generate opt for Aadhaar
	 */
	@PostMapping(value = "/generateOTPForAadhaar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<GenerateOtpResp>> generateOTPForAadhar(
			@RequestHeader Map<String, String> headers, @RequestBody GenerateOtpAadhaarDTO generateOtpAadhaarDTO) {
		MainResponseDTO<GenerateOtpResp> mainResponse = null;
		logger.info("Generate OTP Request Received For Aaadhaar Starts");
		try {
			mainResponse = healthIdCreationByAdharService.generateOTPForAadhar(headers, generateOtpAadhaarDTO);
		} catch (Exception e) {
			logger.error("Error in generateOTPForAadhar {} ", e);
		}
		logger.info("Generate OTP Request Received For Aaadhaar Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * for resend Aadhaar opt
	 */
	@PostMapping(value = "/resendOTPForAadhaar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<AadharOtpResendRespDTO>> resendOTPForAadhar(
			@RequestHeader Map<String, String> headers, @RequestBody ResendOtpDTO resendOTPDTO) {
		MainResponseDTO<AadharOtpResendRespDTO> mainResponse = null;
		logger.info("Resend OTP Request Starts");
		try {
			mainResponse = healthIdCreationByAdharService.resendOTPForAadhar(headers, resendOTPDTO);
		} catch (Exception e) {
			logger.error("Error in resendOTPForAadhar {} ", e);
		}
		logger.info("Resend OTP Request Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * for verify Aadhaar opt
	 */
	@PostMapping(value = "/verifyOTPForAadhaar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<ConfirmOtpRespAadhaar>> verifyOTPForAadhaar(
			@RequestHeader Map<String, String> headers, @RequestBody ConfirmOtpAadhaarDTO confirmOtpAadhaarDTO) {
		MainResponseDTO<ConfirmOtpRespAadhaar> mainResponse = null;
		logger.info("Verify OTP Request Starts");
		try {
			mainResponse = healthIdCreationByAdharService.confirmOTP(headers, confirmOtpAadhaarDTO);
		} catch (Exception e) {
			logger.error("Error in verifyOTPForAadhaar {} ", e);
		}
		logger.info("Verify OTP Request Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * create healthid by Aadhaar number
	 */
	@PostMapping(value = "/helathIDcreationForAadhaar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<HealthIDResp>> saveHealthIdDetails(@RequestHeader Map<String, String> headers,
			@Valid @RequestBody HealthIDAadhaarDTO healthIDDTO) {
		MainResponseDTO<HealthIDResp> mainResponse = null;
		logger.info("Registration-HealthId Request Starts");
		try {
			mainResponse = healthIdCreationByAdharService.healthIdCreationByAdharService(headers, healthIDDTO);
		} catch (Exception e) {
			logger.error("Error in saveHealthIdDetails {} ", e);
		}
		logger.info("Registration-HealthId Request Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * for generate Mobile opt for Aadhaar
	 */
	@PostMapping(value = "/generateMobileOTP", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<GenerateOtpResp>> generateMobileOTP(
			@RequestHeader Map<String, String> headers, @RequestBody GenerateMobileOTP generateMobileOTP) {
		MainResponseDTO<GenerateOtpResp> mainResponse = null;
		logger.info("Generate OTP Request Starts For Aaadhaar Mobile");
		try {
			mainResponse = healthIdCreationByAdharService.generateMobileOTP(headers, generateMobileOTP);
		} catch (Exception e) {
			logger.error("Error in generateMobileOTP {} ", e);
		}
		logger.info("Generate OTP Request Ends For Aaadhaar Mobile");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * for verify mobile opt
	 */
	@PostMapping(value = "/verifyMobileOTP", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<ConfirmOtpRespAadhaar>> verifyMobileOTP(
			@RequestHeader Map<String, String> headers, @RequestBody ConfirmOtpDTO confirmOtpDTO) {
		MainResponseDTO<ConfirmOtpRespAadhaar> mainResponse = null;
		logger.info("Verify Mobile OTP Request Starts");
		try {
			mainResponse = healthIdCreationByAdharService.verifyMobileOTP(headers, confirmOtpDTO);
		} catch (Exception e) {
			logger.error("Error in verifyMobileOTP {} ", e);
		}
		logger.info("Verify Mobile OTP Request Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * create healthid by preverify
	 */
	@PostMapping(value = "/helathIDcreationForPreVerified", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<CreateHealthIdPreverifiedRespDTO>> helathIDcreationForPreVerified(
			@RequestHeader Map<String, String> headers,
			@Valid @RequestBody CreateHealthIdPreverifiedRequestDTO createHealthIdPreverifiedRequestDTO) {
		MainResponseDTO<CreateHealthIdPreverifiedRespDTO> mainResponse = null;
		logger.info("Registration-HealthId for Preverified Request Starts");
		try {
			mainResponse = healthIdCreationByAdharService.helathIDcreationForPreVerified(headers,
					createHealthIdPreverifiedRequestDTO);
		} catch (Exception e) {
			logger.error("Error in helathIDcreationForPreVerified {} ", e);
		}
		logger.info("Registration-HealthId for Preverified Request Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * for verify Bio
	 */
	@PostMapping(value = "/verifyBioForAadhaar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<AadharOtpResendRespDTO>> verifyBioForAadhaar(
			@RequestHeader Map<String, String> headers, @RequestBody VerifyBioReqDTO verifyBioReqDTO) {
		MainResponseDTO<AadharOtpResendRespDTO> mainResponse = null;
		logger.info("Verify Bio Request Received");
		try {
			mainResponse = healthIdCreationByAdharService.verifyBioForAadhaar(headers, verifyBioReqDTO);
		} catch (Exception e) {
			logger.error("Error in verifyBioForAadhaar {} ", e);
		}

		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	/*
	 * create healthid by preverify
	 */
	@PostMapping(value = "/helathIDcreationForPreVerifiedDemo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<CreateHealthIdPreverifiedRespDTO>> helathIDcreationForPreVerifiedDemo(
			@RequestHeader Map<String, String> headers,
			@Valid @RequestBody CreateHealthIdPreverifiedRequestDTO createHealthIdPreverifiedRequestDTO) {
		MainResponseDTO<CreateHealthIdPreverifiedRespDTO> mainResponse = null;
		logger.info("Registration-HealthId for Preverified Request Starts");
		try {
			mainResponse = healthIdCreationByAdharService.helathIDcreationForPreVerifiedDemo(headers,
					createHealthIdPreverifiedRequestDTO);
		} catch (Exception e) {
			logger.error("Error in helathIDcreationForPreVerified {} ", e);
		}
		logger.info("Registration-HealthId for Preverified Request Ends");
		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}
}
