package com.nsdl.telemedicine.patient.exception;

import com.nsdl.telemedicine.patient.dto.ExceptionJSONInfoDTO;

import lombok.Getter;

@Getter
public class PatientRegException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ExceptionJSONInfoDTO error;

	public PatientRegException(ExceptionJSONInfoDTO error) {
		System.out.println(error.getErrorCode()+" "+error.getMessage()+" in PatientRegException");
		this.error = error;
		System.out.println(this.error.getErrorCode()+" "+this.error.getMessage()+" in PatientRegException in this");
	}

}