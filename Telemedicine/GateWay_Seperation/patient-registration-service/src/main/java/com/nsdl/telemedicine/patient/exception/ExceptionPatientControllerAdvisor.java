package com.nsdl.telemedicine.patient.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nsdl.telemedicine.patient.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.utility.EmptyCheckUtils;

@RestControllerAdvice
public class ExceptionPatientControllerAdvisor {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionPatientControllerAdvisor.class);

	@Autowired
	private ObjectMapper objectMapper;

	@ExceptionHandler(DateParsingException.class)
	public ResponseEntity<ResponseWrapper<ExceptionJSONInfoDTO>> getUserValidity(final HttpServletRequest httpServletRequest,
			final DateParsingException exception) throws IOException {
		ResponseWrapper<ExceptionJSONInfoDTO> mainResponseDTO = getResponseDto();
		mainResponseDTO.getErrors().add(exception.getError());
		return new ResponseEntity<>(mainResponseDTO, HttpStatus.OK);
	}
	
	@ExceptionHandler(PatientRegistrationException.class)
	public ResponseEntity<ResponseWrapper<ExceptionJSONInfoDTO>> registrationException(HttpServletRequest httpServletRequest,
			final PatientRegistrationException reqException) throws IOException {
		ResponseWrapper<ExceptionJSONInfoDTO> errorResponse = setErrors(httpServletRequest);
		ExceptionJSONInfoDTO exceptionJSONInfoDTO = new ExceptionJSONInfoDTO(reqException.getErrorCode(),reqException.getMessage());
		errorResponse.getErrors().add(exceptionJSONInfoDTO);
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	}

	@ExceptionHandler(value = { Exception.class, RuntimeException.class })
	public ResponseEntity<ResponseWrapper<ExceptionJSONInfoDTO>> defaultErrorHandler(
			final HttpServletRequest httpServletRequest, Exception e) throws IOException {
		ResponseWrapper<ExceptionJSONInfoDTO> errorResponse = setErrors(httpServletRequest);
		ExceptionJSONInfoDTO error = new ExceptionJSONInfoDTO("PT-0001","Internal server error");
		errorResponse.getErrors().add(error);
		errorResponse.setVersion("1.0");
		e.printStackTrace();
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseWrapper<ExceptionJSONInfoDTO> setErrors(HttpServletRequest httpServletRequest) throws IOException {
		ResponseWrapper<ExceptionJSONInfoDTO> responseWrapper = new ResponseWrapper<>();
		responseWrapper.setResponsetime(LocalDateTime.now());
		responseWrapper.setVersion("1.0");
		String requestBody = null;
		if (httpServletRequest instanceof ContentCachingRequestWrapper) {
			requestBody = new String(((ContentCachingRequestWrapper) httpServletRequest).getContentAsByteArray());
		}
		if (EmptyCheckUtils.isNullEmpty(requestBody)) {
			return responseWrapper;
		}
		objectMapper.registerModule(new JavaTimeModule());
		return responseWrapper;
	}

	private ResponseWrapper<ExceptionJSONInfoDTO> getResponseDto() {
		ResponseWrapper<ExceptionJSONInfoDTO> responseWrapper = new ResponseWrapper<>();
		responseWrapper.setId("Patient-Registration");
		responseWrapper.setVersion("1.0");
		responseWrapper.setErrors(new ArrayList<>());
		responseWrapper.setStatus(false);
		System.out.println(" in ExceptionControllerAdvisor");
		return responseWrapper;
	}
	
	@ExceptionHandler(PatientRegException.class)
	public ResponseEntity<ResponseWrapper<ExceptionJSONInfoDTO>> getisNull(final HttpServletRequest httpServletRequest,
			final PatientRegException exception) throws IOException {
		System.out.println(exception.getError().getErrorCode()+" "+exception.getError().getMessage()+" in ExceptionControllerAdvisor1");
		ResponseWrapper<ExceptionJSONInfoDTO> mainResponseDTO = getResponseDto();
		System.out.println(exception);
		mainResponseDTO.getErrors().add(exception.getError());
		System.out.println(exception.getError().getErrorCode()+" "+exception.getError().getMessage()+" in ExceptionControllerAdvisor2");
		return new ResponseEntity<>(mainResponseDTO, HttpStatus.OK);
	}
}
