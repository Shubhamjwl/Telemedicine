package com.nsdl.authenticate.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nsdl.authenticate.dto.ResponseWrapper;

@ControllerAdvice
public class UinAuthenticateAdvisorController {
	
	
	@ExceptionHandler(UinAuthenticationException.class)
	public ResponseEntity<ResponseWrapper<ServiceError>> uinAuthenticateCustomException(UinAuthenticationException exception){
		ResponseWrapper<ServiceError> errorResponse = new ResponseWrapper<ServiceError>();
		errorResponse.setResponsetime(LocalDateTime.now());
		errorResponse.setId("Uid authentication");
		errorResponse.setVersion("1.0");
		ServiceError serviceErrors = new ServiceError(exception.getErrorCode(), exception.getErrorMessage());
		errorResponse.getErrors().add(serviceErrors);
		return new ResponseEntity<ResponseWrapper<ServiceError>>(errorResponse, HttpStatus.NOT_FOUND);	
	}

}
