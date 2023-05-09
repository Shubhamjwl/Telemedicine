package com.nsdl.telemedicine.doctor.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainRequestDTO<T> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4966448852014107698L;
	/**
	 * version
	 */
	private String version;
	
	private String id;
	/**
	 * Request Date Time
	 */
	private Date requesttime;
	/**
	 * Request Object
	 */
	private T request;
	
	private String mimeType;
	
	private String API;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getAPI() {
		return API;
	}

	public void setAPI(String aPI) {
		API = aPI;
	}

	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getRequesttime() {
		return requesttime;
	}

	public void setRequesttime(Date requesttime) {
		this.requesttime = requesttime;
	}

	public T getRequest() {
		return request;
	}

	public void setRequest(T request) {
		this.request = request;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
