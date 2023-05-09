package com.nsdl.payment.dto;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MainRequestDTO<T> {

	private String id;
		
	private String version;
	
	private LocalDateTime requestTime;

	@NotNull(message = "Request cannot be empty")
	@Valid
	private T request;
	
}
