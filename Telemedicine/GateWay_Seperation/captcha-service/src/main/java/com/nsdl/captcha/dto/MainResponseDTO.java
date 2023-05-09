package com.nsdl.captcha.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.nsdl.captcha.exception.ServiceError;

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

	private List<ServiceError> errors;
	
}
