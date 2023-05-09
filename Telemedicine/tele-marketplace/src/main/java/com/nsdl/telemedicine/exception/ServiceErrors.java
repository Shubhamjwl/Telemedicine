package com.nsdl.telemedicine.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceErrors {
	
	private String errorCode;
	
	private String message;

	
	public ServiceErrors(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.message = errorMessage;
	}

	public ServiceErrors() {

	}

}
