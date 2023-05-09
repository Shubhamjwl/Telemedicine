package com.nsdl.payment.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.nsdl.payment.exception.ServiceError;

import lombok.Data;





@Data
public class MainResponseDTO<T> {

	private String id;

	private String version;

	private LocalDateTime responseTime;

	private boolean status;
	
	private T response;
	
	private List<ServiceError> errors;
	
}

