package com.nsdl.telemedicine.review.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nsdl.telemedicine.review.constant.ErrorConstants;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PatientReviewExceptionHandler {

	@ExceptionHandler(PatientReviewServiceException.class)
	public ResponseEntity<ErrorResponse<ServiceError>> customExceptionHandler(final HttpServletRequest httpServletRequest,
			final PatientReviewServiceException exception) throws IOException {
		ErrorResponse<ServiceError> errorResponse = new ErrorResponse<ServiceError>();
		//System.out.println(exception);
		errorResponse.setErrors(exception.getError());
		return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse<ServiceError>> globalExceptionHandler(Exception exception,
			final HttpServletRequest httpServletRequest) {
		ErrorResponse<ServiceError> errorResponse = new ErrorResponse<ServiceError>();
		ServiceError serviceErrors = new ServiceError(ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage());
		//System.out.println(serviceErrors);
		errorResponse.setErrors(serviceErrors);
		return new ResponseEntity<ErrorResponse<ServiceError>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
