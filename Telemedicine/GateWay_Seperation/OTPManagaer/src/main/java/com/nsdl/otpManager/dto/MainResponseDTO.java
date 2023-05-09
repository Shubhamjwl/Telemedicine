package com.nsdl.otpManager.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.nsdl.otpManager.Exception.ServiceErrors;

import lombok.Data;

@Data
public class MainResponseDTO<T> implements Serializable {
	private static final long serialVersionUID = 3384945682672832638L;

	private String id;

	private String version;

	private LocalDateTime responsetime;

	private T response;

	private boolean status;
	private String statusCode;

	private List<ServiceErrors> errors;
	
}