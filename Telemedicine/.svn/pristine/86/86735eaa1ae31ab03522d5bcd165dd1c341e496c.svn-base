package com.nsdl.telemedicine.doctor.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.doctor.constant.DoctorRegConstant;
import com.nsdl.telemedicine.doctor.dto.AppointmentDetailsResponseDTO;
import com.nsdl.telemedicine.doctor.dto.AppontmentDetailsRequestDTO;
import com.nsdl.telemedicine.doctor.dto.BulkDoctorRegistrationDTO;
import com.nsdl.telemedicine.doctor.dto.BulkDoctorResponseDTO;
import com.nsdl.telemedicine.doctor.dto.CategoryDtlsResponse;
import com.nsdl.telemedicine.doctor.dto.DoctorActiveDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorDocsDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorDocumentDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorMstrDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorRegDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.MainRequestDTO;
import com.nsdl.telemedicine.doctor.dto.MainResponseDTO;
import com.nsdl.telemedicine.doctor.dto.PatientDetailsRequestDTO;
import com.nsdl.telemedicine.doctor.dto.ScribeRegDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.UserDTO;
import com.nsdl.telemedicine.doctor.entity.DoctorDocsDtlEntity;
import com.nsdl.telemedicine.doctor.entity.DoctorRegDtlsEntity;
import com.nsdl.telemedicine.doctor.exception.DoctorRegistrationException;
import com.nsdl.telemedicine.doctor.exception.DrRegErrorConstant;
import com.nsdl.telemedicine.doctor.exception.DrRegErrorMessage;
import com.nsdl.telemedicine.doctor.exception.ServiceErrors;
import com.nsdl.telemedicine.doctor.loggers.DoctorLoggingClientInfo;
import com.nsdl.telemedicine.doctor.service.RegistrationService;
import com.nsdl.telemedicine.doctor.utility.DateUtils;
import com.nsdl.telemedicine.doctor.utility.DoctorAuthUtil;

/**
 * @author SayaliA
 *
 */
@RestController("doctorController")
@RequestMapping("${doctor.registration.service-version}")
//@CrossOrigin("*")
@DoctorLoggingClientInfo
//@EnableWebMvc
public class RegistrationController {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	private UserDTO userDetails;
	

	/**
	 * This API would be used for Doctor Registration  </br>
	 * @author SayaliA
	 * @param MainRequestDTO<DoctorRegDtlsDTO> this is request body
	 * @return MainResponseDTO<RegistrationResponseDTO> this is the response
	 */
	
	@PostMapping(value = "/saveDoctorDetailsTele", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<String>> saveDoctorRegistrationDetails(@RequestBody @Valid MainRequestDTO<DoctorRegDtlsDTO<DoctorDocsDtlsDTO>> registerRequest) {
		MainResponseDTO<String> mainResponse = null;
		logger.info("Request Receive for doctor registartion,Inside save doctor details method");
		try {
			mainResponse = registrationService.saveDoctorRegistrationDetails(registerRequest);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mainResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(mainResponse);
   }
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/bulkDoctorRegistration", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<BulkDoctorResponseDTO>> saveBulkDoctorRegistrationDetails(
			@RequestBody @Valid MainRequestDTO<BulkDoctorRegistrationDTO> registerRequest) {
		MainResponseDTO<BulkDoctorResponseDTO> mainResponse = null;
		logger.info("Request Receive for bulk doctor registartion,Inside save doctor details method");

		mainResponse = (MainResponseDTO<BulkDoctorResponseDTO>) DoctorAuthUtil.getMainResponseDto(registerRequest);
		mainResponse.setResponse(registrationService.saveBulkDoctorRegistrationDetails(registerRequest.getRequest()));
		mainResponse.setStatus(true);

		return ResponseEntity.status(HttpStatus.OK).body(mainResponse);
	}

	/**
	 * This API would be used for deleting uploaded files  </br>
	 * @author SayaliA
	 * @param MainRequestDTO<DoctorDocumentDtlsDTO> this is request body
	 * @return MainResponseDTO<DoctorDocumentDtlsDTO> this is the response
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/deleteDocuments", produces = MediaType.APPLICATION_JSON_VALUE)
	public MainResponseDTO<DoctorDocumentDtlsDTO> deleteuploadedDocuments(
			@RequestBody @Valid MainRequestDTO<DoctorDocumentDtlsDTO> documentDeleterequest) {
		MainResponseDTO<DoctorDocumentDtlsDTO> response = new MainResponseDTO<>();
		int i = 0;
		try {
			DoctorDocumentDtlsDTO documentDtlsDTO = documentDeleterequest.getRequest();
			i = registrationService.deleteuploadedDocuments(documentDtlsDTO.getDocumentUserID());
			if (i == 0) {
				response.setStatus(DoctorRegConstant.DELETEDOCUMENTFAIL.isStatus());
				documentDtlsDTO.setMesssage(DoctorRegConstant.FAIL_MSG.getMsg());
			}else
			{
				response.setStatus(DoctorRegConstant.DELETEDOCUMENT.isStatus());
				documentDtlsDTO.setMesssage(DoctorRegConstant.SUCCESS_DELETE_MSG.getMsg());
			}
			response = (MainResponseDTO<DoctorDocumentDtlsDTO>) DoctorAuthUtil.getMainResponseDto(documentDeleterequest);
			response.setId(documentDeleterequest.getId());
			response.setResponse(documentDtlsDTO);
		} catch (Exception e) {
			e.printStackTrace();
			response = (MainResponseDTO<DoctorDocumentDtlsDTO>) DoctorAuthUtil.getMainResponseDto(documentDeleterequest);
			response.setStatus(DoctorRegConstant.STATUS_FAIL.isStatus());
			response.setErrors(DoctorAuthUtil.getExceptionList(null, "DRREG-005", "Something went wrong, please try again"));
		}
		return response;
	}

	/**
	 * @param registerRequest
	 * @param res
	 * @return
	 *Added by girishk for view doctor profile. Parameter :- userId
	 */
	@PostMapping(value = "/viewDoctorProfile", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> doctorProfile(@RequestBody MainRequestDTO<String> profileRequest,HttpServletResponse res) 
	{ 
		logger.info("Request Received to view doctor profile.");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(registrationService.doctorProfile(profileRequest));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DoctorAuthUtil.getExceptionDetails(e));
		}
	}

	/**
	 * @param drDocDtlsRequest
	 * @param res
	 * @return Added by girishk to get doctor uploaded documents. Parameter :
	 *         userId
	 */
	@PostMapping(value = "/getDoctorDocumentDetails", produces =MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> getDoctorDocumentsDetails(@RequestBody MainRequestDTO<String> drDocDtlsRequest, HttpServletResponse res) 
	{ 
		logger.info("Request Received to get doctor document.");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(registrationService.getDoctorDocumentsDetails(drDocDtlsRequest));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DoctorAuthUtil.getExceptionDetails(e));
		}
	}
	
	@GetMapping(value = "/getDoctorProfilePath", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<String>> getDoctorProfilePath(
			@RequestParam(required = true) String drEmailID) {
		logger.info("Request Received to update terms and condition flag for doctor id.");

		MainResponseDTO<String> response = new MainResponseDTO<>();
		response.setResponse(registrationService.getDoctorProfilePath(drEmailID));
		response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * @param drDocDtlsRequest
	 * @param res
	 * @return
	 * Added by girishk to update details of doctor.
	 */
	@PostMapping(value = "/updateDoctorProfile", produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateDoctorProfile(@RequestBody MainRequestDTO<DoctorMstrDtlsDTO> profileUpdateRequest,
			HttpServletResponse res) 
	{ logger.info("Request Received to update doctor profile.");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(registrationService.updateDoctorProfile(profileUpdateRequest));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DoctorAuthUtil.getExceptionDetails(e));
		}
	}

	/**
	 * @param profileRequest
	 * @param res
	 * @return
	 * Method added by girishk to view(Active/De-Active) scribe list.
	 *         Parameter : doctor UserId
	 */
	@PostMapping(value = "/viewScribeList", produces =MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> viewScribeList(@RequestBody MainRequestDTO<ScribeRegDtlsDTO> scribeListRequest,HttpServletResponse res) { 
		logger.info("Request Received to view scribe list.");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(registrationService.viewScribeList(scribeListRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DoctorAuthUtil.getExceptionDetails(e));
		}
	}

	/**
	 * @param profileUpdateRequest
	 * @param res
	 * @return
	 * Added by girishk to activate/de-activate scribe. Parameter :
	 * srd_user_id, srd_dr_user_id_fk, srd_isactive
	 */
	@PostMapping(value = "/activeDeactiveScribe", produces =MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> activeDeactiveScribe(@RequestBody MainRequestDTO<ScribeRegDtlsDTO> scribeActivationRequest,
			HttpServletResponse res) 
	{ 
		logger.info("Request Received to activate/deactivate scribe.");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(registrationService.activeDeactiveScribe(scribeActivationRequest));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DoctorAuthUtil.getExceptionDetails(e));
		}
	}

	/**
	 * Method added by SayaliA to upload documents.
	 */
	@PostMapping(value = "/uploadDocuments",  produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<MainResponseDTO<String>> uploadDocuments(@RequestBody @Valid MainRequestDTO<DoctorRegDtlsDTO<DoctorDocsDtlsDTO>> uploadrequest) {
		MainResponseDTO<String> response = new MainResponseDTO<>();
		try{
			DoctorRegDtlsEntity doctorrRegDtlsEntity=new DoctorRegDtlsEntity();
			response=registrationService.saveuploadedDocuments(uploadrequest,doctorrRegDtlsEntity);
		}catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	

	/**
	 * @param filename
	 * @return
	 * 
	 * Added by girishk to download(view) document
	 */
	@PostMapping(value = "/downloadDocument", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> downloadDocument(@RequestBody MainRequestDTO<DoctorDocsDtlEntity> doctorDocDtlsRequest,HttpServletResponse res) {
		String finalResponse = null;
		/*
		 * String username = userDetails.getUserName().toUpperCase(); String docName=
		 * doctorDocDtlsRequest.getRequest().getDdtDocsPath(); String pdfReadServerPath
		 * = doctorDocumentPath+username.concat("/")+docName;
		 */
		String pdfReadServerPath = doctorDocDtlsRequest.getRequest().getDdtDocsPath();
		MainResponseDTO<String> responseWrapper = new MainResponseDTO<String>();
		try 
		{
			File file = new File(pdfReadServerPath);
			finalResponse = new String(Base64.encodeBase64(Files.readAllBytes(Paths.get(pdfReadServerPath))));
			responseWrapper.setResponse(finalResponse);
			responseWrapper.setStatus(true);
			responseWrapper.setMimeType(URLConnection.guessContentTypeFromName(file.getName()));
		} catch (Throwable e) {
			e.printStackTrace();
			finalResponse = e.getMessage();
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.FILE_NOT_FOUND,DrRegErrorMessage.FILE_NOT_FOUND));
		}
		//System.out.println("Response " + finalResponse);
		return new ResponseEntity<MainResponseDTO<String>>(responseWrapper, HttpStatus.OK);
	}
	
	/**
	 * @param doctorCountRequest
	 * @return
	 * @throws IOException
	 * 
	 * Added by girishk to get count of doctors.
	 */
	@PostMapping(value = "/getCountOfDoctors", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<MainResponseDTO<Long>> getCountOfDoctors(@RequestBody MainRequestDTO<String> doctorCountRequest,HttpServletResponse res) 
	{ 
		logger.info("Request Received to get count of doctor.");
		MainResponseDTO<Long> responseWrapper = new MainResponseDTO<Long>();
		responseWrapper.setResponse(registrationService.getCountOfDoctors());
		responseWrapper.setStatus(true);
		logger.info("Returning response");
		return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
	}
	
	/**
	 * @param scribeListRequest
	 * @param res
	 * @return
	 * Added by girishk to get all active and deactive scribe of doctor.
	 */
	@PostMapping(value = "/getScribeListByDoctorToActiveDeactive", produces =MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> getScribeListByDoctorToActiveDeactive(@RequestBody MainRequestDTO<ScribeRegDtlsDTO> scribeListRequest,HttpServletResponse res) { 
		logger.info("Request Received to get all active and deactive scribe list of doctor.");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(registrationService.getScribeListByDoctorToActiveDeactive(scribeListRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DoctorAuthUtil.getExceptionDetails(e));
		}
	}
	
	/**
	 * @param changeDefaultScribeRequest
	 * @param res
	 * @return
	 * Added by girishk to change scribe to default by doctor.
	 */
	@PostMapping(value = "/changeDefaultScribe", produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changeDefaultScribe(@RequestBody MainRequestDTO<ScribeRegDtlsDTO> changeDefaultScribeRequest, HttpServletResponse res) 
	{ 
		logger.info("Request Received to change default scribe.");
		try {
			String loggedInUser = userDetails.getUserName().toUpperCase();
//			logger.info(loggedInUser);
			return ResponseEntity.status(HttpStatus.OK).body(registrationService.changeDefaultScribe(changeDefaultScribeRequest, loggedInUser));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DoctorAuthUtil.getExceptionDetails(e));
		}
	}
	
	/**
	 * @param appontmentDetailsRequest
	 * @param res
	 * @return
	 * Added by girishk to search completed appointment using different search criteria.
	 */
	@PostMapping(value = "/searchCompletedAppointmentsForDoctor", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<MainResponseDTO<List<AppointmentDetailsResponseDTO>>> searchCompletedAppointmentsForDoctor(@RequestBody MainRequestDTO<AppontmentDetailsRequestDTO> appontmentDetailsRequest,HttpServletResponse res) 
	{ 
		logger.info("Request Received to search completed appointments for doctor.");
		return ResponseEntity.status(HttpStatus.OK).body(registrationService.searchCompletedAppointmentsForDoctor(appontmentDetailsRequest));
	}
	
	/**
	  
	 * Method added by Sandesh to get mapped Dr by patient ID
	 */
	@PostMapping(value = "/getMappedDrListByPatientId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<List<DoctorActiveDTO>>> getMappedDrListByPatientId(@RequestBody MainRequestDTO<PatientDetailsRequestDTO> requestDto) {
		logger.info("Request Received to get mapped Dr by patient id.");
		return ResponseEntity.status(HttpStatus.OK).body(registrationService.getMappedDrListByPatientId(requestDto));
	}
	
	@GetMapping(value = "/updateTermsAndConditionForDoctor", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<String>> updateTermsAndConditionForDoctor() {
		logger.info("Request Received to update terms and condition flag for doctor id.");
		
		MainResponseDTO<String> response = new MainResponseDTO<>();
		response.setResponse(registrationService.updateTermsAndConditionForDoctor());
		response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@GetMapping(value="/fetchCategoryDtlsByDrId",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<CategoryDtlsResponse>> fetchCategoryDtlsByDrId() {
		logger.info("Category Details By Dr Id Request Received");
		
		MainResponseDTO<CategoryDtlsResponse> response = new MainResponseDTO<>();
		String drUserName = userDetails.getUserName().toUpperCase();
		response.setResponse(registrationService.fetchCategoryDtlsByDrId(drUserName));
		response.setResponsetime(LocalDateTime.now().toString());
		response.setStatus(true);
		 return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping(value = "/checkDoctorTagByPatientId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<Boolean>> checkDoctorTaggedByPatientId(@RequestBody MainRequestDTO<PatientDetailsRequestDTO> requestDto) {
		logger.info("Request Received to check mapped Dr by patientID");
		return ResponseEntity.status(HttpStatus.OK).body(registrationService.checkDoctorTagByPatientId(requestDto));
	}
}
