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

import com.nsdl.ndhm.dto.MainRequestDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.SearchHealthIdToLoginReqDTO;
import com.nsdl.ndhm.dto.SearchHealthIdToLoginRespDTO;
import com.nsdl.ndhm.dto.SearchPatientByHealthIdReqDTO;
import com.nsdl.ndhm.dto.SearchPatientByHealthIdRespDTO;
import com.nsdl.ndhm.service.VerifyAbhaIdService;

@RestController
@CrossOrigin
@RequestMapping("/verify")
public class VerifyAbhaIdController {

	private static final Logger logger = LoggerFactory.getLogger(VerifyAbhaIdController.class);

	@Autowired
	VerifyAbhaIdService verifyAbhaIdService;

	/* for fetch modes */
	@PostMapping(value = "/searchHealthIdToLogin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<SearchHealthIdToLoginRespDTO>> searchHealthIdToLogin(
			@RequestHeader Map<String, String> headers,
			@RequestBody SearchHealthIdToLoginReqDTO searchHealthIdToLoginReqDTO) {

		logger.info("searchHealthIdToLogin method Starts");
		MainResponseDTO<SearchHealthIdToLoginRespDTO> mainResponse = null;
		try {
			mainResponse = verifyAbhaIdService.searchHealthIdToLogin(headers, searchHealthIdToLoginReqDTO);
		} catch (Exception e) {
			logger.error("Error in searchHealthIdToLogin {} ", e);
		}

		return ResponseEntity.status(HttpStatus.OK).body(mainResponse);
	}

	@PostMapping(value = "/searchPatientByHealthId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<SearchPatientByHealthIdRespDTO>> searchPatientByHealthId(
			@RequestHeader Map<String, String> headers,
			@RequestBody MainRequestDTO<SearchPatientByHealthIdReqDTO> searchPatientByHealthIdDTO) {

		logger.info("searchPatientByHealthId method Starts");
		return ResponseEntity.status(HttpStatus.OK)
				.body(verifyAbhaIdService.searchPatientByHealthId(searchPatientByHealthIdDTO));
	}

}
