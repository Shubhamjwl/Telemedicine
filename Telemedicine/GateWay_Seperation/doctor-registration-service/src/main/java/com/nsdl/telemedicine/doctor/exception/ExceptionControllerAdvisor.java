package com.nsdl.telemedicine.doctor.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * @author Pegasus_girishk
 *
 */
@RestControllerAdvice(value = "doctorAdvisor")
public class ExceptionControllerAdvisor {

	@ExceptionHandler(DoctorRegistrationException.class)
	public ResponseEntity<ErrorResponse<ServiceErrors>> customExceptionHandler(final HttpServletRequest httpServletRequest,
			final DoctorRegistrationException exception) throws IOException {
		ErrorResponse<ServiceErrors> errorResponse = new ErrorResponse<ServiceErrors>();
		System.out.println(exception);
		errorResponse.setErrors(exception.getList());
		return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse<ServiceErrors>> globalExceptionHandler(Exception exception,
			final HttpServletRequest httpServletRequest) {
		ErrorResponse<ServiceErrors> errorResponse = new ErrorResponse<ServiceErrors>();
		ServiceErrors serviceErrors = new ServiceErrors(DrRegErrorConstant.SERVER_ERROR, DrRegErrorMessage.SERVER_ERROR);
		System.out.println(serviceErrors);
		errorResponse.setErrors(serviceErrors);
		return new ResponseEntity<ErrorResponse<ServiceErrors>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
