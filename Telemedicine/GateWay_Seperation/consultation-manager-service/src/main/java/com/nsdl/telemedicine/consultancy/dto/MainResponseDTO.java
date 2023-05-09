package com.nsdl.telemedicine.consultancy.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.nsdl.telemedicine.consultancy.exception.ServiceError;

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
