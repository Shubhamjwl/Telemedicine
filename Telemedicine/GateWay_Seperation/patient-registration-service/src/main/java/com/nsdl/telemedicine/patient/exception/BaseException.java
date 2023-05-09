package com.nsdl.telemedicine.patient.exception;

import java.util.ArrayList;
import java.util.List;

import com.nsdl.telemedicine.patient.dto.ExceptionJSONInfoDTO;


public class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = 6785003872780128394L;
	public static final String EMPTY_SPACE = " ";
	private List<ExceptionJSONInfoDTO> exceptionJSONInfoDTO = new ArrayList<>();

	/**
	 * Constructs a new unchecked exception
	 */
	public BaseException() {
		super();
	}

	/**
	 * Constructs a new checked exception with errorMessage
	 * 
	 * @param errorMessage the detail message.
	 */
	public BaseException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructs a new unchecked exception with the specified detail message and
	 * error code.
	 *
	 * @param errorMessage the detail message.
	 * @param errorCode    the error code.
	 * 
	 */
	public BaseException(String errorCode, String errorMessage) {
		super(errorCode + " --> " + errorMessage);
		addServiceError(errorCode, errorMessage);
	}

	/**
	 * Constructs a new unchecked exception with the specified detail message and
	 * error code and error cause.
	 *
	 * 
	 * @param errorCode    the error code
	 * @param errorMessage the detail message.
	 * @param rootCause    the specified cause
	 */
	public BaseException(String errorCode, String errorMessage, Throwable rootCause) {
		super(errorCode + " --> " + errorMessage, rootCause);
		addServiceError(errorCode, errorMessage);
		if (rootCause instanceof BaseException) {
			BaseException exception = (BaseException) rootCause;
			exceptionJSONInfoDTO.addAll(exception.exceptionJSONInfoDTO);
		}
	}

	public BaseException(List<ExceptionJSONInfoDTO> serviceErrors) {		
		this.exceptionJSONInfoDTO = serviceErrors;
	}

	public BaseException(List<ExceptionJSONInfoDTO> serviceError, Throwable rootCause) {
		
		if (rootCause instanceof BaseException) {
			BaseException exception = (BaseException) rootCause;
			exceptionJSONInfoDTO.addAll(exception.exceptionJSONInfoDTO);
		}
	}

	/*
	 * Returns a String object that can be used to get the exception message.
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	

	/**
	 * This method add the information of error code and error message.
	 * 
	 * @param errorCode the error code
	 * @param errorText the detail message.
	 * @return the instance of current BaseCheckedException
	 */
	public BaseException addServiceError(String errorCode, String errorText) {
		this.exceptionJSONInfoDTO.add(new ExceptionJSONInfoDTO(errorCode, errorText));
		return this;
	}
	
	public BaseException addServiceError(List<ExceptionJSONInfoDTO> serviceError) {
		this.exceptionJSONInfoDTO.addAll(serviceError);
		return this;
	}

	/**
	 * Returns the list of error codes.
	 * 
	 * @return the list of error codes
	 */
	public List<String> getCodes() {
		List<String> codes = new ArrayList<>();

		for (int i = this.exceptionJSONInfoDTO.size() - 1; i >= 0; i--) {
			codes.add(this.exceptionJSONInfoDTO.get(i).getErrorCode());
		}

		return codes;
	}

	/**
	 * Returns the list of exception messages.
	 * 
	 * @return the list of exception messages
	 */
	public List<String> getErrorMessages() {
		List<String> errorMessages = new ArrayList<>();

		for (int i = this.exceptionJSONInfoDTO.size() - 1; i >= 0; i--) {
			errorMessages.add(this.exceptionJSONInfoDTO.get(i).getMessage());
		}

		return errorMessages;
	}

	/**
	 * Return the last error code.
	 * 
	 * @return the last error code
	 */
	public String getErrorCode() {
		return exceptionJSONInfoDTO.get(0).getErrorCode();
	}

	/**
	 * Return the last exception message.
	 * 
	 * @return the last exception message
	 */
	public String getErrorMessage() {
		return exceptionJSONInfoDTO.get(0).getMessage();
	}
	
	public List<ExceptionJSONInfoDTO> getList() {
		return exceptionJSONInfoDTO;
	}

}
