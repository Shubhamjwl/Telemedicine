package com.nsdl.telemedicine.patient.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;

import lombok.Data;

@Data
public class MainRequestsDTO<T> implements Serializable {
	private static final long serialVersionUID = -4966448852014107698L;

	
	private String id;
	
	private String version;
	
	private Date requesttime;
	
	private String method;
	
	private String Username;
	
	private String Password;
	
	@Valid
	private T request;

}
