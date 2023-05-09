package com.nsdl.ndhm.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionJSONInfoDTO implements Serializable {
	private static final long serialVersionUID = 3999014525078508265L;

	private String errorCode;

	private String message;

	private String reason;

	public ExceptionJSONInfoDTO() {

	}

	public ExceptionJSONInfoDTO(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public ExceptionJSONInfoDTO(String errorCode, String message, String reason) {
		this.errorCode = errorCode;
		this.message = message;
		this.reason = reason;
	}
}
