package com.nsdl.authenticate.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UinAuthenticationException extends RuntimeException {

	public UinAuthenticationException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;
	
	

}
