package com.nsdl.patientReport.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MainRequestDTO<T> implements Serializable {
	private static final long serialVersionUID = -4966448852014107698L;

	
	private String id;
	
	private String version;
	
	private Date requesttime;
	
	//@Valid
	private T request;

}