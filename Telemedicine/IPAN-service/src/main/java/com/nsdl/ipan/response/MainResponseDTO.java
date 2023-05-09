package com.nsdl.ipan.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class MainResponseDTO<T> implements Serializable {
	private static final long serialVersionUID = 3384945682672832638L;

	private String id;
	private String version;
	private LocalDateTime responsetime;
	private List<T> response;
	private boolean status;
	private List<ServiceError> errors;

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

	public List<ServiceError> getErrors() {
		return errors;
	}

	public void setErrors(List<ServiceError> errors) {
		this.errors = errors;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<T> getResponse() {
		return response;
	}

	public void setResponse(List<T> response) {
		this.response = response;
	}

}
