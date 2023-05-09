package com.nsdl.auth.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.MainResponseDTO;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
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

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> validationException(HttpServletRequest httpServletRequest,
			final MissingServletRequestParameterException exception) throws IOException {
		MainResponseDTO<ServiceError> errorResponse = getResponseDto();
		ServiceError serviceError = new ServiceError();
		serviceError.setErrorCode("Missing Request Parameter");
		serviceError.setMessage(exception.getMessage());
		errorResponse.getErrors().add(serviceError);
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

	@ExceptionHandler(RequestEntityException.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> validationException(HttpServletRequest httpServletRequest,
			final RequestEntityException reqException) throws IOException {
		// ExceptionUtils.logRootCause(reqException);
		MainResponseDTO<ServiceError> MainResponseDTO = getResponseDto();
		if (reqException.getErrors() != null && reqException.getErrors().hasErrors()) {
			MainResponseDTO.getErrors().addAll(getListOfFieldErrors(reqException.getErrors()));
		}
		logger.error("Exception occured : "+reqException.getErrors().getAllErrors());
		return new ResponseEntity<>(MainResponseDTO, HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MainResponseDTO<ServiceError>> globalExceptionHandler(Exception exception,
			final HttpServletRequest httpServletRequest) throws IOException {
		MainResponseDTO<ServiceError> errorResponse = getResponseDto();
		List<ServiceError> serviceErrors = new ArrayList<ServiceError>();
		StringWriter errors = new StringWriter();
		exception.printStackTrace(new PrintWriter(errors));
		logger.error("++++++++"+errors.toString());
		logger.error("+++++++++"+exception.getStackTrace().toString());
		ServiceError error = new ServiceError(AuthErrorConstant.SOMETHING_WENT_WRONG,
				AuthConstant.SOMETHING_WENT_WRONG);
		serviceErrors.add(error);
		errorResponse.setErrors(serviceErrors);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private List<ServiceError> getListOfFieldErrors(Errors errors) {
		List<ServiceError> ServiceError = errors.getFieldErrors().stream()
				.map(error -> new ServiceError(error.getCode(), error.getDefaultMessage()))
				.collect(Collectors.toList());
		return ServiceError;
	}

	private MainResponseDTO<ServiceError> getResponseDto() {
		MainResponseDTO<ServiceError> responseWrapper = new MainResponseDTO<>();
		responseWrapper.setId("user");
		responseWrapper.setVersion("v1");
		responseWrapper.setErrors(new ArrayList<>());
		responseWrapper.setResponsetime(LocalDateTime.now());
		responseWrapper.setStatusCode("200");
		responseWrapper.setStatus(false);
		return responseWrapper;
	}

}
