package com.nsdl.otpManager.Exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.nsdl.otpManager.dto.MainResponseDTO;
import com.nsdl.otpManager.enumeration.ErrorConstant;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler  {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	 @ExceptionHandler(Exception.class)
	  public final ResponseEntity<Object>handleAllExceptions(Exception ex, WebRequest request) 
	  {
		MainResponseDTO<ServiceErrors> errorResponse = getResponseDto();
		List<ServiceErrors> serviceErrors = new ArrayList<ServiceErrors>();
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		logger.error(errors.toString());
		ServiceErrors error = new ServiceErrors(ErrorConstant.SOMETHING_WENT_WRONG.getCode(),ErrorConstant.SOMETHING_WENT_WRONG.getMessage());
		serviceErrors.add(error);
		errorResponse.setErrors(serviceErrors);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	  
	  }
	 	
	 
	 @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,  HttpServletRequest httpServletRequest) {
		MainResponseDTO<ServiceErrors> errorResponse = getResponseDto();
		List<ServiceErrors> serviceErrors = new ArrayList<ServiceErrors>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			serviceErrors.add(new ServiceErrors(error.getCode(), error.getDefaultMessage()));
			logger.error("Exception occured : "+error.getDefaultMessage());
		}
		errorResponse.getErrors().addAll(serviceErrors);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
	
	
	
	  @ExceptionHandler(OTPException.class) 
	  public ResponseEntity<MainResponseDTO<ServiceErrors>>handleOTPExceptions(OTPException ex, WebRequest request) 
	  { 
		  MainResponseDTO<ServiceErrors> mainResponseDTO = getResponseDto();
		 	mainResponseDTO.getErrors().add(ex.getError());
			logger.error("Exception occured : "+ex.getMessage());
			return new ResponseEntity<>(mainResponseDTO, HttpStatus.OK);
	 
	  }

	  private MainResponseDTO<ServiceErrors> getResponseDto() {
			MainResponseDTO<ServiceErrors> responseWrapper = new MainResponseDTO<>();
			responseWrapper.setVersion("v1");
			responseWrapper.setErrors(new ArrayList<>());
			responseWrapper.setResponsetime(LocalDateTime.now());
			responseWrapper.setStatus(false);
			responseWrapper.setStatusCode("400");
			return responseWrapper;
		}
}
