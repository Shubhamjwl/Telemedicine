package com.nsdl.payment.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nsdl.payment.dto.MainResponseDTO;



@RestControllerAdvice
public class ExceptionControllerAdvisor {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvisor.class);

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> validationException(HttpServletRequest httpServletRequest,
			final HttpMessageNotReadableException exception) throws IOException {
		MainResponseDTO<ServiceError> errorResponse = getResponseDto();
		ServiceError serviceErrors = new ServiceError("Data Parse Exception", exception.getRootCause().getMessage());
		errorResponse.getErrors().add(serviceErrors);
		logger.error("Exception occured : "+exception.getRootCause().getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> validationException(HttpServletRequest httpServletRequest,
			final MethodArgumentNotValidException exception) throws IOException {
		MainResponseDTO<ServiceError> errorResponse = getResponseDto();
		List<ServiceError> serviceErrors = new ArrayList<ServiceError>();
		for (ObjectError error : exception.getBindingResult().getAllErrors()) {
			serviceErrors.add(new ServiceError(error.getCode(), error.getDefaultMessage()));
			logger.error("Exception occured : "+error.getDefaultMessage());
		}
		errorResponse.getErrors().addAll(serviceErrors);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DateParsingException.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> getUserValidity(final HttpServletRequest httpServletRequest,
			final DateParsingException exception) throws IOException {
		// ExceptionUtility.logRootCause(exception);

		MainResponseDTO<ServiceError> MainResponseDTO = getResponseDto();
		MainResponseDTO.getErrors().add(exception.getError());
		logger.error("Exception occured : "+exception.getError().getMessage());
		return new ResponseEntity<>(MainResponseDTO, HttpStatus.OK);
	}

	
	

	private MainResponseDTO<ServiceError> getResponseDto() {
		MainResponseDTO<ServiceError> responseWrapper = new MainResponseDTO<>();
		responseWrapper.setId("user");
		responseWrapper.setVersion("v1");
		responseWrapper.setErrors(new ArrayList<>());
		responseWrapper.setResponseTime(LocalDateTime.now());
		responseWrapper.setStatus(false);
		return responseWrapper;
	}

}
