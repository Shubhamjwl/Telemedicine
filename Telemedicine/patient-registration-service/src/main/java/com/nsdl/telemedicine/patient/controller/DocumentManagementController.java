package com.nsdl.telemedicine.patient.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.patient.constant.AuthConstant;
import com.nsdl.telemedicine.patient.constant.AuthErrorConstant;
import com.nsdl.telemedicine.patient.dto.DeleteDocumentResponse;
import com.nsdl.telemedicine.patient.dto.DocumentDtlsResponse;
import com.nsdl.telemedicine.patient.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.dto.UploadDocumentRequest;
import com.nsdl.telemedicine.patient.dto.UploadDocumentResponse;
import com.nsdl.telemedicine.patient.exception.DateParsingException;
import com.nsdl.telemedicine.patient.loggers.PatientLoggingClientInfo;
import com.nsdl.telemedicine.patient.service.DocumentManagementService;

@RestController("documentController")
@RequestMapping("/documentManagement")
@PatientLoggingClientInfo
public class DocumentManagementController {
	
	@Autowired
	private DocumentManagementService documentManagementService;

	private static final Logger logger = LoggerFactory.getLogger(DocumentManagementController.class);
	
	@PostMapping(path = "/uploadPatientDocument",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<UploadDocumentResponse>> uploadPatientDocument(
			 @Valid @RequestBody RequestWrapper<UploadDocumentRequest> request) {
		logger.info("Request received for upload patient document");
		return ResponseEntity.status(HttpStatus.OK).body(documentManagementService.savePatientReports(request));
	}
	
	@GetMapping(path = "/getAllDocumentDtls", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<List<DocumentDtlsResponse>>> uploadPatientDocument() {
		logger.info("Request received for get all document dtls");
		return ResponseEntity.status(HttpStatus.OK).body(documentManagementService.fetchAllDocumentDtls());
	}
	
	
	@DeleteMapping(path = "/deletePatientDocument/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<DeleteDocumentResponse>> deletePatientDocument(@PathVariable("id") Integer id) {
		logger.info("Request received for delete patient document");
		ResponseWrapper<DeleteDocumentResponse> responseWrapper = new ResponseWrapper<DeleteDocumentResponse>();
		DeleteDocumentResponse response = new DeleteDocumentResponse();
		int i = 0;
		try {
			if(id == null) {
				throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_USERID,
						AuthConstant.DELETED_ID));
			}else {
			i = documentManagementService.deletePatientDocument(id);
			if (i == 0) {
				response.setMsg(AuthConstant.SOMETHING_WENT_WRONG);
				responseWrapper.setResponse(response);
				responseWrapper.setStatus(false);
			}else
			{
				response.setMsg(AuthConstant.DELETED_SUCCESSFULLY);
				responseWrapper.setResponse(response);
				responseWrapper.setStatus(true);
			}
		}
		} catch (Exception e) {
			logger.error("Error occured while processing request :", e);
			e.printStackTrace();
		}
		logger.debug("Returning from delete Patient Document of Controller");
		return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
	}
	
	
	
}
