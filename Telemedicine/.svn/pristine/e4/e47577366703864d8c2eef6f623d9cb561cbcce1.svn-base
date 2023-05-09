package com.nsdl.telemedicine.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nsdl.telemedicine.dto.ResponseWrapperDTO;

@RestControllerAdvice
public class ExceptionControllerAdvisor {

	@Autowired
	private ObjectMapper objectMapper;

	@ExceptionHandler(DateParsingException.class)
	public ResponseEntity<ErrorResponse<ServiceErrors>> getUserValidity(final HttpServletRequest httpServletRequest,
			final DateParsingException exception) throws IOException {
		// ExceptionUtility.logRootCause(exception);
		ErrorResponse<ServiceErrors> errorResponse = setErrors(httpServletRequest);
		// System.out.println(exception);
		errorResponse.setErrors(exception.getList());
		return new ResponseEntity<ErrorResponse<ServiceErrors>>(errorResponse, HttpStatus.OK);
	}

	@ExceptionHandler(MarketPlaceDetailsException.class)
	public ResponseEntity<ResponseWrapperDTO<ServiceErrors>> marketDetailsException(MarketPlaceDetailsException except)
			throws IOException {
		ResponseWrapperDTO<ServiceErrors> errorResponse = new ResponseWrapperDTO<ServiceErrors>();
		errorResponse.setResponsetime(OffsetDateTime.now().toInstant().toString());
		errorResponse.setStatus(false);
		ServiceErrors serviceError = new ServiceErrors();
		serviceError.setErrorCode(except.getErrorCode());
		serviceError.setMessage(except.getErrorMessage());
		errorResponse.setError(serviceError);
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	}

	private ErrorResponse<ServiceErrors> setErrors(HttpServletRequest httpServletRequest) throws IOException {
		ErrorResponse<ServiceErrors> responseWrapper = new ErrorResponse<ServiceErrors>();
		responseWrapper.setResponsetime(LocalDateTime.now(ZoneId.of("UTC")));

		objectMapper.registerModule(new JavaTimeModule());
		return responseWrapper;
	}
}
