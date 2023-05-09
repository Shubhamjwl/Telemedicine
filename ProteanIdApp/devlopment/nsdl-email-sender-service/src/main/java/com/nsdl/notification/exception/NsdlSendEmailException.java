package com.nsdl.notification.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NsdlSendEmailException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMessage;
	
	public NsdlSendEmailException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	
	
	

}
