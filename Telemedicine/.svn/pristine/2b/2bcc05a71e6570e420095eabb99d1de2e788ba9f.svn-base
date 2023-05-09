package com.nsdl.telemedicine.patient.exception;

import com.nsdl.telemedicine.patient.dto.ExceptionJSONInfoDTO;

import lombok.Getter;

@Getter
public class DateParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ExceptionJSONInfoDTO error;

	public DateParsingException(ExceptionJSONInfoDTO error) {
		//System.out.println(error.getErrorCode()+" "+error.getMessage()+" in DateParsingException");
		this.error = error;
		//System.out.println(this.error.getErrorCode()+" "+this.error.getMessage()+" in DateParsingException in this");
	}

}