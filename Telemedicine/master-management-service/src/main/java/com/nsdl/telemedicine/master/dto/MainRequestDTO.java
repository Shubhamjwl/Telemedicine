package com.nsdl.telemedicine.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nsdl.telemedicine.master.constant.MasterConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainRequestDTO<T> extends MasterConstant implements Serializable {
	private static final long serialVersionUID = -4966448852014107698L;

	
	private String id;
	
	private String version;
	
	private Date requesttime;
	@Valid
	@NotNull(message =REQUEST_NULL)
	private T request;
	

}
