package com.nsdl.login.exception;

import com.nsdl.login.dto.ServiceError;

public class LoginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ServiceError list;

	public LoginException(ServiceError list) {
		this.list = list;
	}

	public ServiceError getList() {
		return list;
	}
}
