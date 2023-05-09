package com.nsdl.telemedicine.scribe.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nsdl.telemedicine.scribe.dto.MainRequestDTO;
import com.nsdl.telemedicine.scribe.dto.MainResponseDTO;
import com.nsdl.telemedicine.scribe.dto.ScribeRegDTO;
import com.nsdl.telemedicine.scribe.exception.ScribeRegException;
import com.nsdl.telemedicine.scribe.loggers.LoggingClientInfo;
import com.nsdl.telemedicine.scribe.service.ScribeRegistrationService;

@RestController("scribeRegistrationController")
@RequestMapping("/")
//@CrossOrigin("*")
@LoggingClientInfo
//@Validated
public class ScribeRegistrationController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScribeRegistrationController.class);
	
	@Autowired
	private ScribeRegistrationService scribeRegistrationService;
	
	@PostMapping(value = "/scribeRegistration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<String>> saveScribeDetails(@Valid @RequestBody MainRequestDTO<ScribeRegDTO> scribeRegDTO) throws ScribeRegException{
		MainResponseDTO<String> mainResponse = null;
		logger.info("Registration-Scribe Request Received");
		try {
			 mainResponse = scribeRegistrationService.saveScribeDetails(scribeRegDTO);
		}catch (Exception e) {
			e.printStackTrace();
		}
		mainResponse.setId(scribeRegDTO.getId());
		mainResponse.setVersion(scribeRegDTO.getVersion());
		mainResponse.setResponsetime(scribeRegDTO.getRequestTime());
		return mainResponse.isStatus() && mainResponse.getErrors() == null? ResponseEntity.status(HttpStatus.OK).body(mainResponse): 
			ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}
	
	@PostMapping(value = "/updateScribeProfile", produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateScribeProfile(@RequestBody MainRequestDTO<ScribeRegDTO> profileUpdateRequest,
			HttpServletResponse res) 
	{ 
		MainResponseDTO<String> mainResponse = null;
		logger.info("Request Received to update doctor profile.");
		try {
			mainResponse = scribeRegistrationService.updateScribeProfile(profileUpdateRequest);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		mainResponse.setId(profileUpdateRequest.getId());
		mainResponse.setVersion(profileUpdateRequest.getVersion());
		mainResponse.setResponsetime(profileUpdateRequest.getRequestTime());
		return mainResponse.isStatus() && mainResponse.getErrors() == null? ResponseEntity.status(HttpStatus.OK).body(mainResponse): 
			ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
	}
	
	@GetMapping(path = "/getScribeDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<?>> getScribeDetails() {

		logger.info("Get Scribe Details Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(scribeRegistrationService.getScribeDetails());
	}

}
