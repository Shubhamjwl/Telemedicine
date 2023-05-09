package com.nsdl.ndhm.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
/**
 * 
 * @author Neosoft_JineshB
 *
 */
public class InputException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;

	public InputException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
