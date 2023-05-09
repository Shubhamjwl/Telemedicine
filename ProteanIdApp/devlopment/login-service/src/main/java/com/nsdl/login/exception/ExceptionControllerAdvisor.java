package com.nsdl.login.exception;

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
import com.nsdl.login.dto.ServiceError;

@RestControllerAdvice
public class ExceptionControllerAdvisor {

	@Autowired
	private ObjectMapper objectMapper;

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ErrorResponse<ServiceError>> customExceptionHandler(
			final HttpServletRequest httpServletRequest, final LoginException exception) throws IOException {
		ErrorResponse<ServiceError> errorResponse = setErrors(httpServletRequest);
		errorResponse.setErrors(exception.getList());
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	}

	private ErrorResponse<ServiceError> setErrors(HttpServletRequest httpServletRequest) throws IOException {
		ErrorResponse<ServiceError> responseWrapper = new ErrorResponse<>();
		responseWrapper.setResponsetime(LocalDateTime.now(ZoneId.of("UTC")));
		objectMapper.registerModule(new JavaTimeModule());
		return responseWrapper;
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse<ServiceError>> globalExceptionHandler(Exception exception,
			final HttpServletRequest httpServletRequest) {
		ErrorResponse<ServiceError> errorResponse = new ErrorResponse<ServiceError>();
		ServiceError serviceErrors = new ServiceError(LoginErrorConstant.INTERNAL_SERVER_ERROR.getErrorCode(),
				LoginErrorConstant.INTERNAL_SERVER_ERROR.getErrorMessage());
		errorResponse.setErrors(serviceErrors);
		return new ResponseEntity<ErrorResponse<ServiceError>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
