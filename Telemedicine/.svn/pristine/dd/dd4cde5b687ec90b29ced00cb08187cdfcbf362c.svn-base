package com.nsdl.patientReport.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.nsdl.patientReport.dto.MainResponseDTO;





@ControllerAdvice
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler{
	
	 @ExceptionHandler(Exception.class) 
	  public ResponseEntity<MainResponseDTO<ApiError>> handleallException(Exception ex, WebRequest request) 
	  { 
		  MainResponseDTO<ApiError> mainResponseDTO = getResponseDto();
		  ApiError error = new ApiError(ex.getMessage(),ex.getLocalizedMessage());
		  mainResponseDTO.setResponse(error);
		  return new ResponseEntity<>(mainResponseDTO,HttpStatus.OK); 
	 
	  }
	
	private MainResponseDTO<ApiError> getResponseDto() {
			MainResponseDTO<ApiError> responseWrapper = new MainResponseDTO<>();
			responseWrapper.setVersion("v1");
			responseWrapper.setErrors(new ArrayList<>());
			responseWrapper.setResponsetime(LocalDateTime.now());
			responseWrapper.setStatus(false);
			return responseWrapper;
		}
	  
	  @ExceptionHandler(PatientReportException.class) 
	  public ResponseEntity<MainResponseDTO<ApiError>> handlePatientReportException(PatientReportException ex, WebRequest request) 
	  { 
		  MainResponseDTO<ApiError> mainResponseDTO = getResponseDto();
		  ApiError error = new ApiError(ex.getErrorMsg(),ex.getDescription());
		  mainResponseDTO.setResponse(error);
		  return new ResponseEntity<>(mainResponseDTO,HttpStatus.OK); 
	 
	  }

}
