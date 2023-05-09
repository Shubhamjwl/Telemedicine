package com.nsdl.telemedicine.doctor.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
/**
 * @author Pegasus_girishk
 *
 */
@RestControllerAdvice
public class ExceptionControllerAdvisor {
	
	@Autowired
	private ObjectMapper objectMapper;

	@ExceptionHandler(DoctorRegistrationException.class)
	public ResponseEntity<ErrorResponse<ServiceErrors>> customExceptionHandler(final HttpServletRequest httpServletRequest,
			final DoctorRegistrationException exception) throws IOException {
		ErrorResponse<ServiceErrors> errorResponse = setErrors(httpServletRequest);
		//System.out.println(exception);
		errorResponse.setErrors(exception.getList());
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	}
	
	private ErrorResponse<ServiceErrors> setErrors(HttpServletRequest httpServletRequest) throws IOException {
		ErrorResponse<ServiceErrors> responseWrapper = new ErrorResponse<>();
		responseWrapper.setResponsetime(LocalDateTime.now(ZoneId.of("UTC")));
		objectMapper.registerModule(new JavaTimeModule());
		return responseWrapper;
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse<ServiceErrors>> globalExceptionHandler(Exception exception,
			final HttpServletRequest httpServletRequest) {
		ErrorResponse<ServiceErrors> errorResponse = new ErrorResponse<ServiceErrors>();
		ServiceErrors serviceErrors = new ServiceErrors(DrRegErrorConstant.SERVER_ERROR, DrRegErrorMessage.SERVER_ERROR);
		//System.out.println(serviceErrors);
		errorResponse.setErrors(serviceErrors);
		return new ResponseEntity<ErrorResponse<ServiceErrors>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
