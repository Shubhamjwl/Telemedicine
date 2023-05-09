package com.nsdl.telemedicine.videoConference.dto;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class InviteServiceMainRequestDTO<T> {

	private String id;
		
	private String version;
	
	private LocalDateTime requestTime;

	private String method;
	
	@NotNull(message = "Request cannot be empty")
	@Valid
	private T request;
	
}


