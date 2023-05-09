package com.nsdl.telemedicine.review.dto;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MainRequestDTO<T> {

	private String API;
		
	private String version;
	
	private LocalDateTime requestTime;
		
	private String mimeType;
	
	private String method;

	@NotNull(message = "Request cannot be empty")
	@Valid
	private T request;
	
}
