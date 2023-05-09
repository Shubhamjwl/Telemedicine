package com.nsdl.notification.constant;

import lombok.Getter;

@Getter
public enum ErrorConstant {
	
	TRY_AGAIN("ERR-code-001", "Try again later");
	
	private String code;
	private String message;

	private ErrorConstant(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
