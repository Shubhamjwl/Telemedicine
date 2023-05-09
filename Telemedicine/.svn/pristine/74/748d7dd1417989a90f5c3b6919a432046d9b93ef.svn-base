package com.nsdl.telemedicine.videoConference.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.videoConference.dto.MainRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.MainResponseDTO;
import com.nsdl.telemedicine.videoConference.dto.VCAutRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.VCAuthResponseDTO;
import com.nsdl.telemedicine.videoConference.dto.VcTokenResponse;
import com.nsdl.telemedicine.videoConference.logger.LoggingClientInfo;
import com.nsdl.telemedicine.videoConference.repository.VcAuthRepository;
import com.nsdl.telemedicine.videoConference.service.VcAuthService;

@RestController
//@CrossOrigin("*")
@LoggingClientInfo
public class VideoConfuthenticationController {
	
	@Autowired
	private VcAuthService vcAuthService;
	
	@Autowired
	VcAuthRepository authRepository;
	
	@PostMapping(value ="/generateVCToken")
	public ResponseEntity<MainResponseDTO<VCAuthResponseDTO>> generateVCToken(@RequestBody @Valid MainRequestDTO<VCAutRequestDTO> vcAuthRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(vcAuthService.generateAuthToken(vcAuthRequest));	
	}
	
	@PostMapping(value = "/validateVCToken", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> validateToken(@RequestBody VCAutRequestDTO vCAutRequestDTO) {
		/*VcAuthToken token = authRepository.findByAuthToken(vCAutRequestDTO.getToken());
		if (token != null) {
			return ResponseEntity.status(HttpStatus.OK).body(true);
		}
		return ResponseEntity.status(HttpStatus.OK).body(false);*/
		return ResponseEntity.status(HttpStatus.OK).body(vcAuthService.validateToken(vCAutRequestDTO));	
		
		
	}
	
	@PostMapping(value = "/validateServerToken")
	public ResponseEntity<MainResponseDTO<VcTokenResponse>> validateToken(HttpServletRequest req) {
	String authToken = req.getHeader("jwt");
	return ResponseEntity.status(HttpStatus.OK).body(vcAuthService.verifyAuthToken(authToken));	
			
	}


}
