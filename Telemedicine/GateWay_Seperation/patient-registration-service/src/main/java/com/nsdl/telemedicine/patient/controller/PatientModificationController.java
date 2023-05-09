package com.nsdl.telemedicine.patient.controller;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nsdl.telemedicine.patient.dto.AllPatientDetailDto;
import com.nsdl.telemedicine.patient.dto.LifeStyleDetailDto;
import com.nsdl.telemedicine.patient.dto.MedicalDetailDto;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.PersonalDetailDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.loggers.PatientLoggingClientInfo;
import com.nsdl.telemedicine.patient.service.PatientModificationService;


@RestController("patientController")
@RequestMapping("/patientModification")
@CrossOrigin("*")
@PatientLoggingClientInfo
public class PatientModificationController {

	@Autowired
	private PatientModificationService patientModificationService; 

	private static final Logger logger = LoggerFactory.getLogger(PatientModificationController.class);

	//Changes from get to Post as per requirment.
	@PostMapping(path = "/getPatientAllDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<AllPatientDetailDto>> getPatientAllDetails() {
		logger.info("Get Patient Details Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(patientModificationService.getPatientAllDetails());
	}

	@PostMapping(path = "/modifyPersonalDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseWrapper<PatientResponseDto>> updatePatientPersonalDetails(@Valid @RequestBody RequestWrapper<PersonalDetailDto> 
	updateRequest) {
		logger.info("Update Patient Personal Details Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(patientModificationService.updatePatientPersonalDetails(updateRequest));
	}

	@PostMapping(path = "/modifyMedicalDetails" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseWrapper<PatientResponseDto>> updatePatientMedicalDetails(@Valid
			@RequestBody RequestWrapper<MedicalDetailDto> updateRequest) {
		logger.info("Update Patient Medical Details Request Received ");
		return ResponseEntity.status(HttpStatus.OK).body(patientModificationService.updatePatientMedicalDetails(updateRequest));
	}

	@PostMapping(path = "/modifyLifeStyleDetails" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseWrapper<PatientResponseDto>> updatePatientLifeStyleDetails(@Valid
			@RequestBody RequestWrapper<LifeStyleDetailDto> updateRequest) {
		logger.info("Update Patient LifeStyle Details Request Received ");
		return ResponseEntity.status(HttpStatus.OK).body(patientModificationService.updatePatientLifeStyleDetails( updateRequest));
	}

	@PostMapping(path = "/modifyAllDetails" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseWrapper<PatientResponseDto>> updatePatientAllDetails(@Valid
			@RequestBody RequestWrapper<AllPatientDetailDto> updateRequest) {
		logger.info("Update Patient All Details Request Received ");
		return ResponseEntity.status(HttpStatus.OK).body(patientModificationService.updateAllPatientDetails(updateRequest));
	}

}
