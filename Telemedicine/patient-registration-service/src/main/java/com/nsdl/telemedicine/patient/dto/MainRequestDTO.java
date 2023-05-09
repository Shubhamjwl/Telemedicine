package com.nsdl.telemedicine.patient.dto;

import java.io.Serializable;
import java.util.List;


import lombok.Data;

@Data
public class MainRequestDTO<T> implements Serializable {
	private static final long serialVersionUID = 3384945682672832638L;

	private String id;

	private String version;

	private String requestTime;

	//@Valid
	//@NotNull
	private T request;

	private boolean status;

	private List<ExceptionJSONInfoDTO> errors;

}
