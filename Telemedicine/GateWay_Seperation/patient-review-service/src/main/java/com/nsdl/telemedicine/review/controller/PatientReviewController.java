package com.nsdl.telemedicine.review.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.review.dto.MainRequestDTO;
import com.nsdl.telemedicine.review.dto.MainResponseDTO;
import com.nsdl.telemedicine.review.dto.PatientRevDtlsDTO;
import com.nsdl.telemedicine.review.loggers.PatientReviewLoggingClientInfo;
import com.nsdl.telemedicine.review.service.PatientReviewService;
/**
 * @author Pegasus_girishk
 *
 */
@RestController
@RequestMapping("${patient.review.service-version}")
@CrossOrigin("*")
@PatientReviewLoggingClientInfo
public class PatientReviewController {

	private static final Logger logger = LoggerFactory.getLogger(PatientReviewController.class);
	
	@Autowired
	private PatientReviewService patientReviewService;

	/**
	 * @param medicationRequest
	 * @return 
	 * Added by girishk to save patient review details.
	 */
	@PostMapping(value = "/savePatientReviewDtls", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<?>> savePatientReviewDtls(@RequestBody MainRequestDTO<PatientRevDtlsDTO> reviewRequest) {
		logger.info("Request received to save patient review details");
		return ResponseEntity.status(HttpStatus.OK).body(patientReviewService.savePatientReviewDtls(reviewRequest));
	}
	
	/**
	 * @param registerRequest
	 * @param res
	 * @return
	 *Added by girishk to view patient reviews.
	 */
	@PostMapping(value = "/viewPatientReviewForDoctor", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> viewPatientReviewForDoctor(@RequestBody MainRequestDTO<PatientRevDtlsDTO> viewPtReviewRequest,HttpServletResponse res) 
	{ 
		logger.info("Request received to view patient review details");
		return ResponseEntity.status(HttpStatus.OK).body(patientReviewService.viewPatientReviewForDoctor(viewPtReviewRequest));
	}
	
	
	/**
	 * @param request
	 * @param res
	 * @return
	 * 
	 * Added by girishk to show number of likes to doctor given by patient.
	 */
	@PostMapping(value = "/getNumberOfLikesToDoctor", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> getNumberOfLikesToDoctor(@RequestBody MainRequestDTO<PatientRevDtlsDTO> request,HttpServletResponse res) 
	{ 
		logger.info("Request received to get number of likes given by patient to doctor.");
		MainResponseDTO<Long> responseWrapper = new MainResponseDTO<Long>();
		responseWrapper.setResponse(patientReviewService.getNumberOfLikesToDoctor(request.getRequest().getPrdDrUserIdFk()));
		responseWrapper.setStatus(true);
		logger.info("Returning response");
		return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
	}
	
	/**
	 * @param request
	 * @param res
	 * @return
	 * Added by girishk to show number of commnets to doctor given by patient.
	 */
	@PostMapping(value = "/getNumberOfCommentsToDoctor", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> getNumberOfCommentsToDoctor(@RequestBody MainRequestDTO<PatientRevDtlsDTO> request,HttpServletResponse res) 
	{ 
		logger.info("Request received to get number of comments given by patient to doctor.");
		MainResponseDTO<Long> responseWrapper = new MainResponseDTO<Long>();
		responseWrapper.setResponse(patientReviewService.getNumberOfCommentsToDoctor(request.getRequest().getPrdDrUserIdFk()));
		responseWrapper.setStatus(true);
		logger.info("Returning response");
		return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
	}
	
}
