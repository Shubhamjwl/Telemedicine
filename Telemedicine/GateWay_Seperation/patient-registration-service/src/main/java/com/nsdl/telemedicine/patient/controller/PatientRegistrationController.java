package com.nsdl.telemedicine.patient.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
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

import com.nsdl.telemedicine.patient.dto.AppointmentDetailsResponseDTO;
import com.nsdl.telemedicine.patient.dto.AppontmentDetailsRequestDTO;
import com.nsdl.telemedicine.patient.dto.PatientRegistrationDto;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.loggers.PatientLoggingClientInfo;
import com.nsdl.telemedicine.patient.service.PatientRegistrationService;


@RestController("patientModiController")
@RequestMapping("/patientRegistration")
@CrossOrigin("*")
@PatientLoggingClientInfo
public class PatientRegistrationController {

	private static final Logger logger = LoggerFactory.getLogger(PatientRegistrationController.class);

	@Autowired
	private PatientRegistrationService patientRegistrationService; 

	@PostMapping(path = "/registration" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<PatientResponseDto>> savePatientDetails( @Valid
			@RequestBody RequestWrapper<PatientRegistrationDto> patientRegistrationDto)
	{
		logger.info("Registration-Patient Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(patientRegistrationService.savePatientDetails(patientRegistrationDto));
	}

	//Changes from Get to Post request
	@PostMapping(path = "/getpatientdetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<PatientRegistrationDto>> getPatientDetailsFromIPAN() {

		logger.info("Get Patient Details Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(patientRegistrationService.getPatientDetailsFromIPAN());
	}
	
	/**
	 * @param appontmentDetailsRequest
	 * @param res
	 * @return
	 * Added by girishk to search completed appointment using different search criteria.
	 */
	@PostMapping(value = "/searchCompletedAppointmentsForPatient", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<ResponseWrapper<List<AppointmentDetailsResponseDTO>>> searchCompletedAppointmentsForPatient(@RequestBody RequestWrapper<AppontmentDetailsRequestDTO> appontmentDetailsRequest,HttpServletResponse res) 
	{ 
		logger.info("Request Received to search completed appointments for patient.");
		return ResponseEntity.status(HttpStatus.OK).body(patientRegistrationService.searchCompletedAppointmentsForPatient(appontmentDetailsRequest));
	}
	
	/**
	 * @param appontmentDetailsRequest
	 * @param res
	 * @return
	 * Added by SayaliA to search completed appointment of latest 10.
	 */
	@PostMapping(value = "/listOfCompletedAppointmentsForPatient", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<ResponseWrapper<List<AppointmentDetailsResponseDTO>>> listOfCompletedAppointmentsForPatient() 
	{ 
		logger.info("Request Received to search completed appointments for patient.");
		return ResponseEntity.status(HttpStatus.OK).body(patientRegistrationService.listOfCompletedAppointmentsForPatient());
	}
	
	/**
	 * @param bulkRegistrationRequest
	 * @return
	 * Method added by girishk to register patients in bulk.
	 */
	@PostMapping(value = "/bulkPatientRegistration", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<?>> bulkPatientRegistration(@RequestBody RequestWrapper<BulkPatientRegistrationDTO> bulkRegistrationRequest) {
		logger.info("Request Received for bulk registration of patients.");
		return ResponseEntity.status(HttpStatus.OK).body(patientRegistrationService.bulkPatientRegistration(bulkRegistrationRequest));
	}
}
