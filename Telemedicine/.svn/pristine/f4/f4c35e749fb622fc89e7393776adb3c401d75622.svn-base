package com.nsdl.telemedicine.videoConference.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.videoConference.dto.KarzaVCRequest;
import com.nsdl.telemedicine.videoConference.dto.KarzaVCResponse;
import com.nsdl.telemedicine.videoConference.dto.MainResponseDTO;
import com.nsdl.telemedicine.videoConference.logger.LoggingClientInfo;
import com.nsdl.telemedicine.videoConference.service.KarzaService;

@RestController
@LoggingClientInfo
public class KarzaVideoConfController {
	
	private static final Logger logger = LoggerFactory.getLogger(KarzaVideoConfController.class);
	
	@Autowired
	KarzaService karzaService;
	
	@PostMapping(value = "/generateKarzaMeetingUrl")
	public ResponseEntity<MainResponseDTO<KarzaVCResponse>> generateKarzaMeetingUrl(
			@RequestBody KarzaVCRequest createRequest) {
		logger.info("generateKarzaMeetingUrl excecution start time::" + LocalTime.now());
		MainResponseDTO<KarzaVCResponse> response = new MainResponseDTO<KarzaVCResponse>();
		KarzaVCResponse createMeeting = karzaService.createMeeting(createRequest);
		response.setId("user");
		response.setVersion("v1");
		response.setResponse(createMeeting);
		response.setResponseTime(LocalDateTime.now());
		response.setStatus(true);
		logger.info("generateKarzaMeetingUrl excecution end time::" + LocalTime.now());
		return new ResponseEntity<MainResponseDTO<KarzaVCResponse>>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getKarzaMeetingUrl", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<KarzaVCResponse>> getKarzaMeetingUrl(@RequestParam String patientId,
			@RequestParam String appointmentId) {
		logger.info("getKarzaMeetingUrl excecution start time::" + LocalTime.now());
		MainResponseDTO<KarzaVCResponse> response = new MainResponseDTO<KarzaVCResponse>();
		response.setResponse(karzaService.getKarzaMeetingUrl(patientId, appointmentId));
		response.setResponseTime(LocalDateTime.now());
		response.setStatus(true);
		logger.info("getKarzaMeetingUrl excecution end time::" + LocalTime.now());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
