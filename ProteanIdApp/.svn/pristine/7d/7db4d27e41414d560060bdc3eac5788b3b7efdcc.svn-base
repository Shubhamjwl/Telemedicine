package com.nsdl.notification.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class NsdlEmailAdviceController {
	
	@ExceptionHandler(NsdlSendEmailException.class)
	public ResponseEntity<ServiceError> nsdlEmailCustomException(NsdlSendEmailException exception){		
		ServiceError serviceErrors = new ServiceError(exception.getErrorCode(), exception.getErrorMessage());
		return new ResponseEntity<ServiceError>(serviceErrors, HttpStatus.NOT_FOUND);		
	}

}
