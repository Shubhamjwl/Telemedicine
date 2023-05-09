package com.nsdl.telemedicine.videoConference.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.videoConference.dto.InvitationRequest;
import com.nsdl.telemedicine.videoConference.dto.InvitationResponse;
import com.nsdl.telemedicine.videoConference.dto.MainRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.MainResponseDTO;
import com.nsdl.telemedicine.videoConference.dto.VideoConfRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.VideoConfResponse;
import com.nsdl.telemedicine.videoConference.logger.LoggingClientInfo;
import com.nsdl.telemedicine.videoConference.service.VideoConfService;
import com.nsdl.telemedicine.videoConference.utility.AuthUtil;

@RestController
@RequestMapping(value ="/videoconf")
//@CrossOrigin("*")
@LoggingClientInfo
public class VideoConfController {
	
	@Autowired
	private VideoConfService videoConfService;
	
	private static final Logger logger = LoggerFactory.getLogger(VideoConfController.class);
	
	@PostMapping(value ="/startconf")
	public ResponseEntity<MainResponseDTO<VideoConfResponse>> getEcryptedDataToStrtConf(
			@RequestBody @Valid MainRequestDTO<VideoConfRequestDTO> confRequest) {

		VideoConfRequestDTO  videoConfRequestDTO  = confRequest.getRequest();
		logger.info("Starting conference with the request of::::"+ videoConfRequestDTO.toString());

		@SuppressWarnings("unchecked")
		MainResponseDTO<VideoConfResponse> response = (MainResponseDTO<VideoConfResponse>) AuthUtil
				.getMainResponseDto(confRequest);

		response.setResponse(videoConfService.startConf(videoConfRequestDTO));
		response.setResponseTime(LocalDateTime.now());
		response.setStatus(true);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping(value ="/invite")
	public ResponseEntity<MainResponseDTO<InvitationResponse>> invite(
			@RequestBody @Valid MainRequestDTO<InvitationRequest> invitationRequest) {

		logger.info("Sending Invitation to join conference for Appointment Id : " + invitationRequest.getRequest().getAppointmentId()
				+ " on : " + invitationRequest.getRequest().getInviteOn());

		@SuppressWarnings("unchecked")
		MainResponseDTO<InvitationResponse> response = (MainResponseDTO<InvitationResponse>) AuthUtil
				.getMainResponseDto(invitationRequest);

		response.setResponse(videoConfService.invite(invitationRequest.getRequest()));
		response.setResponseTime(LocalDateTime.now());
		response.setStatus(true);

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	
	
	
}
