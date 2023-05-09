package com.nsdl.telemedicine.consultancy.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nsdl.telemedicine.consultancy.constant.ErrorConstants;
import com.nsdl.telemedicine.consultancy.dto.MainResponseDTO;
import com.nsdl.telemedicine.consultancy.logger.LoggingClientInfo;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@LoggingClientInfo
public class ConsultationExceptionHandler {

	@Value("${consultation.manager.service.version}")
	private String version;

	@Value("${consultation.manager.service.id}")
	private String id;

	private static final Logger logger = LoggerFactory.getLogger(ConsultationExceptionHandler.class);

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> validationException(HttpServletRequest httpServletRequest,
			final HttpMessageNotReadableException exception) throws IOException {
		MainResponseDTO<ServiceError> errorResponse = getResponseDto();
		ServiceError serviceErrors = new ServiceError("Data Parse Exception", exception.getRootCause().getMessage());
		errorResponse.getErrors().add(serviceErrors);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> validationException(HttpServletRequest httpServletRequest,
			final MethodArgumentNotValidException exception) throws IOException {
		MainResponseDTO<ServiceError> errorResponse = getResponseDto();
		List<ServiceError> serviceErrors = new ArrayList<ServiceError>();
		for (ObjectError error : exception.getBindingResult().getAllErrors()) {
			serviceErrors.add(new ServiceError("Data Parse Exception", error.getDefaultMessage()));
		}
		errorResponse.getErrors().addAll(serviceErrors);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> validationException(HttpServletRequest httpServletRequest,
			final MissingServletRequestParameterException exception) throws IOException {
		MainResponseDTO<ServiceError> errorResponse = getResponseDto();
		ServiceError serviceError = new ServiceError();
		serviceError.setCode("Data Parse Exception");
		serviceError.setMessage(exception.getMessage());
		errorResponse.getErrors().add(serviceError);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConsultationServiceException.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> validationException(HttpServletRequest httpServletRequest,
			final ConsultationServiceException exception) throws IOException {
		MainResponseDTO<ServiceError> errorResponse = getResponseDto();
		errorResponse.getErrors().addAll(exception.getError());
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	}

	private MainResponseDTO<ServiceError> getResponseDto() throws IOException {
		MainResponseDTO<ServiceError> responseWrapper = new MainResponseDTO<>();
		responseWrapper.setErrors(new ArrayList<>());
		responseWrapper.setVersion(version);
		responseWrapper.setId(id);
		responseWrapper.setResponseTime(LocalDateTime.now());
		responseWrapper.setStatus(false);
		return responseWrapper;
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> globalExceptionHandler(Exception exception,
			final HttpServletRequest httpServletRequest) throws IOException {
		MainResponseDTO<ServiceError> errorResponse = getResponseDto();
		List<ServiceError> serviceErrors = new ArrayList<ServiceError>();
		StringWriter errors = new StringWriter();
		exception.printStackTrace(new PrintWriter(errors));
		logger.error(errors.toString());
		ServiceError error = new ServiceError(ErrorConstants.SOMETHING_WENT_WRONG.getCode(),
				ErrorConstants.SOMETHING_WENT_WRONG.getMessage());
		serviceErrors.add(error);
		errorResponse.setErrors(serviceErrors);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
