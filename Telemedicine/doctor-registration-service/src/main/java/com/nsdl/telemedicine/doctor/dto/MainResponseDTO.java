package com.nsdl.telemedicine.doctor.dto;

import java.io.Serializable;
import java.util.List;

public class MainResponseDTO<T> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3384945682672832638L;
	/**
	 * Id
	 */

	private String id;
	/**
	 * version
	 */

	private String version;

	private String responsetime;
	
	private String mimeType;

	private T response;
	
	private boolean status;

	/** The error details. */
	private List<ExceptionJSONInfoDTO> errors;

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

	public String getResponsetime() {
		return responsetime;
	}

	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public List<ExceptionJSONInfoDTO> getErrors() {
		return errors;
	}

	public void setErrors(List<ExceptionJSONInfoDTO> errors) {
		this.errors = errors;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
