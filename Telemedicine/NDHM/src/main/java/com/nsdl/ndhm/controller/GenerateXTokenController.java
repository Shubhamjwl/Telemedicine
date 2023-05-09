package com.nsdl.ndhm.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.ndhm.dto.AuthInitDTO;
import com.nsdl.ndhm.dto.ConfirmAadhaarBioDTO;
import com.nsdl.ndhm.dto.ConfirmDemograpicsDTO;
import com.nsdl.ndhm.dto.ConfirmOtpDTO;
import com.nsdl.ndhm.dto.ConfirmOtpResp;
import com.nsdl.ndhm.dto.ConfirmPasswordDTO;
import com.nsdl.ndhm.dto.GenerateOtpResp;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.SearchExistHealthIdRespDTO;
import com.nsdl.ndhm.service.GenerateXTService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class GenerateXTokenController {
	private static final Logger logger = LoggerFactory.getLogger(GenerateXTokenController.class);

	@Autowired
	GenerateXTService generateXTService;

	@PostMapping(value = "/init", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<GenerateOtpResp>> authInitHealthId(@RequestHeader Map<String, String> headers,
			@RequestBody AuthInitDTO authInitDTO) {
		MainResponseDTO<GenerateOtpResp> mainResponse = null;
		logger.info("Auth Init Request Received");
		try {
			mainResponse = generateXTService.authInit(headers, authInitDTO);
		} catch (Exception e) {
			logger.error("Error in Auth Init {}", e);
		}

		return ResponseEntity.status(HttpStatus.OK).body(mainResponse);
	}

	@PostMapping(value = "/confirm-aadhaarOTP", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<ConfirmOtpResp>> confirmWithAadhaarOTP(
			@RequestHeader Map<String, String> headers, @RequestBody ConfirmOtpDTO confirmOtpDTO) {
		MainResponseDTO<ConfirmOtpResp> mainResponse = null;
		logger.info("Confirm with AadhaarOTP Request Received");
		try {
			mainResponse = generateXTService.confirmAadhaarOTP(headers, confirmOtpDTO);
		} catch (Exception e) {
			logger.error("Error in confirm AadhaarOTP {}", e);
		}

		return ResponseEntity.status(HttpStatus.OK).body(mainResponse);
	}

	@PostMapping(value = "/confirm-mobileOTP", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<ConfirmOtpResp>> confirmWithMobileOTP(
			@RequestHeader Map<String, String> headers, @RequestBody ConfirmOtpDTO confirmOtpDTO) {
		MainResponseDTO<ConfirmOtpResp> mainResponse = null;
		logger.info("Confirm with MobileOTP Request Received");
		try {
			mainResponse = generateXTService.confirmMobileOTP(headers, confirmOtpDTO);
		} catch (Exception e) {
			logger.error("Error in confirm MobileOTP {} ", e);
		}

		return ResponseEntity.status(HttpStatus.OK).body(mainResponse);
	}

	@PostMapping(value = "/confirm-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<ConfirmOtpResp>> confirmWithPassword(
			@RequestHeader Map<String, String> headers, @RequestBody ConfirmPasswordDTO confirmPasswordDTO) {
		MainResponseDTO<ConfirmOtpResp> mainResponse = null;
		logger.info("Confirm with Password Request Received");
		try {
			mainResponse = generateXTService.confirmByPassword(headers, confirmPasswordDTO);
		} catch (Exception e) {
			logger.error("Error in confirm Password ", e);
		}

		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	@PostMapping(value = "/confirm-aadhaarBio", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<ConfirmOtpResp>> confirmWithAadhaarBio(
			@RequestHeader Map<String, String> headers, @RequestBody ConfirmAadhaarBioDTO confirmAadhaarBioDTO) {
		MainResponseDTO<ConfirmOtpResp> mainResponse = null;
		logger.info("Confirm with AadhaarBio Request Received");
		try {
			mainResponse = generateXTService.confirmAadhaarBio(headers, confirmAadhaarBioDTO);
		} catch (Exception e) {
			logger.error("Error in confirm AadhaarBio {}", e);
		}

		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}

	@PostMapping(value = "/confirm-Demographics", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<SearchExistHealthIdRespDTO>> confirmWithDemographics(
			@RequestHeader Map<String, String> headers, @RequestBody ConfirmDemograpicsDTO confirmDemograpicsDTO) {
		MainResponseDTO<SearchExistHealthIdRespDTO> mainResponse = null;
		logger.info("Confirm with Demographics Request Received");
		try {
			mainResponse = generateXTService.confirmDemograpics(headers, confirmDemograpicsDTO);
		} catch (Exception e) {
			logger.error("Error in confirm with Demographics {}", e);
		}

		return null != mainResponse && mainResponse.isStatus() && mainResponse.getErrors() == null
				? ResponseEntity.status(HttpStatus.OK).body(mainResponse)
				: ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}
}
