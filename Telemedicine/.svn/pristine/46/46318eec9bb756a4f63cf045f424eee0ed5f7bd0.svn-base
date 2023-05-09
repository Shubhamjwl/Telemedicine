package com.nsdl.telemedicine.doctor.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse<T> {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime responsetime = LocalDateTime.now();
	
	private boolean status;
	
	private Object metaData;
	
	private T response;
	
	private ServiceErrors errors = new ServiceErrors();
	
	public ErrorResponse() {
	}

	public LocalDateTime getResponsetime() {
		return responsetime;
	}

	public void setResponsetime(LocalDateTime responsetime) {
		this.responsetime = responsetime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Object getMetaData() {
		return metaData;
	}

	public void setMetaData(Object metaData) {
		this.metaData = metaData;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public ServiceErrors getErrors() {
		return errors;
	}

	public void setErrors(ServiceErrors errors) {
		this.errors = errors;
	}

}
