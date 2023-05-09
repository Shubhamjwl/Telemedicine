package com.nsdl.telemedicine.exception;

public class DateParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ServiceErrors list;

	public DateParsingException(ServiceErrors list) {
		this.list = list;
	}

	public ServiceErrors getList() {
		return list;
	}
}
