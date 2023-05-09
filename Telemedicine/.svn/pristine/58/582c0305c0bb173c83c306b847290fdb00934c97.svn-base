package com.nsdl.telemedicine.review.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse<T> {
	
	private String id;
	
	private String version;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime responsetime = LocalDateTime.now();
	
	private boolean status;
	
	private T response;
	
	private Object metaData;
	
	private ServiceError errors = new ServiceError();
	
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

	public ServiceError getErrors() {
		return errors;
	}

	public void setErrors(ServiceError errors) {
		this.errors = errors;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
