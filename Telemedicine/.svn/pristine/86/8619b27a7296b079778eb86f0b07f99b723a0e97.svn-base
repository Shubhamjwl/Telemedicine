package com.nsdl.telemedicine.review.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.nsdl.telemedicine.review.exception.ServiceError;

import lombok.Data;

@Data
public class MainResponseDTO<T> {

	private String id;

	private String version;

	private LocalDateTime responseTime;
	
	private T response;

	private boolean status;
	
	private List<ServiceError> errors;
	
}
