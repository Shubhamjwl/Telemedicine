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
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.ndhm.dto.AddContextRequestDTO;
import com.nsdl.ndhm.dto.AuthConfirmReqDTO;
import com.nsdl.ndhm.dto.AuthFetchModesReqDTO;
import com.nsdl.ndhm.dto.AuthInitReqDTO;
import com.nsdl.ndhm.dto.GetOnFetchModesReqDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.OnConfirmRequestDTO;
import com.nsdl.ndhm.dto.OnFetchModesRequestDTO;
import com.nsdl.ndhm.service.HealthIdVerificationService;

@RestController
@CrossOrigin
public class HealthIdVerificationController {

	private static final Logger logger = LoggerFactory.getLogger(HealthIdVerificationController.class);

	@Autowired
	HealthIdVerificationService healthIdVerificationService;

	/* for fetch modes */
	@PostMapping(value = "/fetch_modes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> getFetchModes(@RequestHeader Map<String, String> headers,
			@RequestBody AuthFetchModesReqDTO authFetchModesReqDTO) {

		logger.info("Generate OTP Request Received");
		Map<String, String> mainResponse = null;
		try {
			mainResponse = healthIdVerificationService.getFetchModes(headers, authFetchModesReqDTO);
		} catch (Exception e) {
			logger.error("Error in getFetchModes {} ", e);
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(mainResponse);
	}

	/* for init */
	@PostMapping(value = "/init", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> init(@RequestHeader Map<String, String> headers,
			@RequestBody AuthFetchModesReqDTO authFetchModesReqDTO) {

		Map<String, String> mainResponse = null;
		try {
			mainResponse = healthIdVerificationService.init(headers, authFetchModesReqDTO);
		} catch (Exception e) {
			logger.error("Error in init {} ", e);
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(mainResponse);
	}

	/*
	 * for auth Confirm
	 */
	@PostMapping(value = "/auth_confirm", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> authConfirm(@RequestHeader Map<String, String> headers,
			@RequestBody AuthConfirmReqDTO authConfirmReqDTO) {

		logger.info("Auth Confirm Request Received");
		Map<String, String> mainResponse = null;
		try {
			mainResponse = healthIdVerificationService.authConfirm(headers, authConfirmReqDTO);
		} catch (Exception e) {
			logger.error("Error in authConfirm {} ", e);
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(mainResponse);
	}

	/* get on fetch modes data */
	@PostMapping("/get-on-fetch-modes")
	public ResponseEntity<MainResponseDTO<OnFetchModesRequestDTO>> getOnFetchModes(
			@RequestHeader Map<String, String> headers, @RequestBody GetOnFetchModesReqDTO getOnFetchModesReqDTO) {
		MainResponseDTO<OnFetchModesRequestDTO> mainResponse = null;
		try {
			mainResponse = healthIdVerificationService.getOnFetchModes(getOnFetchModesReqDTO);
		} catch (Exception e) {
			mainResponse = null;
			logger.error("Error in getOnFetchModes {} ", e);
		}

		return ResponseEntity.status(HttpStatus.OK).body(mainResponse);

	}

	/* get on fetch modes data */
	@PostMapping("/get-on-init")
	public ResponseEntity<MainResponseDTO<AuthInitReqDTO>> getOnInit(@RequestHeader Map<String, String> headers,
			@RequestBody GetOnFetchModesReqDTO getOnFetchModesReqDTO) {
		MainResponseDTO<AuthInitReqDTO> mainResponse = null;
		try {
			mainResponse = healthIdVerificationService.getOnInit(getOnFetchModesReqDTO);
		} catch (Exception e) {
			mainResponse = null;
			logger.error("Error in getOnInit {} ", e);
		}

		return ResponseEntity.status(HttpStatus.OK).body(mainResponse);

	}

	/* get on fetch modes data */
	@PostMapping("/get-on-confirm")
	public ResponseEntity<MainResponseDTO<OnConfirmRequestDTO>> getOnConfirm(@RequestHeader Map<String, String> headers,
			@RequestBody GetOnFetchModesReqDTO getOnFetchModesReqDTO) {
		MainResponseDTO<OnConfirmRequestDTO> mainResponse = null;
		try {
			mainResponse = healthIdVerificationService.getOnConfirm(getOnFetchModesReqDTO);
		} catch (Exception e) {
			mainResponse = null;
			logger.error("Error in getOnConfirm {} ", e);
		}

		return ResponseEntity.status(HttpStatus.OK).body(mainResponse);

	}

	/*
	 * for generate and add care contexts
	 */
	@PostMapping(value = "/add-contexts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> addContext(@RequestHeader Map<String, String> headers,
			@RequestBody AddContextRequestDTO addContextRequestDTO) {

		logger.info("Add Context Request Starts");
		Map<String, String> mainResponse = null;
		try {
			mainResponse = healthIdVerificationService.addContext(headers, addContextRequestDTO);
		} catch (Exception e) {
			logger.error("Error in authConfirm {} ", e);
		}
		logger.info("Add Context Request Ends");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(mainResponse);
	}

}
