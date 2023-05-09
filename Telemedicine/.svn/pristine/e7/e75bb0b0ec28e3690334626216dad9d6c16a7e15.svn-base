package com.nsdl.auth.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;

import lombok.Data;

@Data
public class MainRequestDTO<T> implements Serializable {
	private static final long serialVersionUID = -4966448852014107698L;

	
	private String id;
	
	private String version;
	
	private Date requesttime;
	
	private String method;
	
	@Valid
	private T request;

}
