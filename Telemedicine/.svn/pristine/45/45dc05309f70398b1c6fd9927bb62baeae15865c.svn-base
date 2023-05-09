package com.nsdl.telemedicine.videoConference.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.videoConference.dto.CreateMeetingRequest;
import com.nsdl.telemedicine.videoConference.dto.CreateMeetingResponse;
import com.nsdl.telemedicine.videoConference.dto.JoinMeetingRequest;
import com.nsdl.telemedicine.videoConference.dto.JoinMeetingResponse;
import com.nsdl.telemedicine.videoConference.dto.MainRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.MainResponseDTO;
import com.nsdl.telemedicine.videoConference.dto.VCAutRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.VCAuthResponseDTO;
import com.nsdl.telemedicine.videoConference.dto.VcTokenResponse;
import com.nsdl.telemedicine.videoConference.exception.BBBException;
import com.nsdl.telemedicine.videoConference.logger.LoggingClientInfo;
import com.nsdl.telemedicine.videoConference.repository.VcAuthRepository;
import com.nsdl.telemedicine.videoConference.service.BBBMeeting;
import com.nsdl.telemedicine.videoConference.service.KarzaService;
import com.nsdl.telemedicine.videoConference.service.VcAuthService;

@RestController
@CrossOrigin("*")
@LoggingClientInfo
public class VideoConfuthenticationController {

	@Autowired
	private VcAuthService vcAuthService;

	@Autowired
	VcAuthRepository authRepository;

	@Autowired
	BBBMeeting meetingService;
	
	@Autowired
	KarzaService karzaService;
	
	private static final Logger logger = LoggerFactory.getLogger(VideoConfuthenticationController.class);

	@PostMapping(value = "/generateVCToken")
	public ResponseEntity<MainResponseDTO<VCAuthResponseDTO>> generateVCToken(
			@RequestBody @Valid MainRequestDTO<VCAutRequestDTO> vcAuthRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(vcAuthService.generateAuthToken(vcAuthRequest));
	}

	@PostMapping(value = "/validateVCToken", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> validateToken(@RequestBody VCAutRequestDTO vCAutRequestDTO) {
		/*
		 * VcAuthToken token =
		 * authRepository.findByAuthToken(vCAutRequestDTO.getToken()); if (token !=
		 * null) { return ResponseEntity.status(HttpStatus.OK).body(true); } return
		 * ResponseEntity.status(HttpStatus.OK).body(false);
		 */
		return ResponseEntity.status(HttpStatus.OK).body(vcAuthService.validateToken(vCAutRequestDTO));

	}

	@PostMapping(value = "/validateServerToken")
	public ResponseEntity<MainResponseDTO<VcTokenResponse>> validateToken(HttpServletRequest req) {
		String authToken = req.getHeader("jwt");
		return ResponseEntity.status(HttpStatus.OK).body(vcAuthService.verifyAuthToken(authToken));

	}

	@PostMapping(value = "/createMeeting")
	public ResponseEntity<MainResponseDTO<CreateMeetingResponse>> createBBBMeeting(
			@RequestBody CreateMeetingRequest createRequest) throws BBBException {
		logger.info("createBBBMeeting excecution start time::" + LocalTime.now());
		MainResponseDTO<CreateMeetingResponse> response = new MainResponseDTO<CreateMeetingResponse>();
		CreateMeetingResponse createMeeting = meetingService.createMeeting(createRequest);
		response.setId("user");
		response.setVersion("v1");
		response.setResponse(createMeeting);
		response.setResponseTime(LocalDateTime.now());
		response.setStatus(true);
		logger.info("createBBBMeeting excecution end time::" + LocalTime.now());
		return new ResponseEntity<MainResponseDTO<CreateMeetingResponse>>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/joinMeeting")
	public ResponseEntity<MainResponseDTO<JoinMeetingResponse>> joinBBBMeeting(
			@RequestBody JoinMeetingRequest joinRequest) throws BBBException {
		logger.info("joinBBBMeeting excecution start time::" + LocalTime.now());
		MainResponseDTO<JoinMeetingResponse> response = new MainResponseDTO<JoinMeetingResponse>();
		JoinMeetingResponse joinMeetingResponse = meetingService.getJoinMeetingURL(joinRequest);
		response.setId("user");
		response.setVersion("v1");
		response.setResponse(joinMeetingResponse);
		response.setResponseTime(LocalDateTime.now());
		response.setStatus(true);
		logger.info("joinBBBMeeting excecution end time::" + LocalTime.now());
		return new ResponseEntity<MainResponseDTO<JoinMeetingResponse>>(response, HttpStatus.OK);
	}

}
