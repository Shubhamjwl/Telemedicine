package com.nsdl.telemedicine.slot.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorResponse<T> {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime responsetime = LocalDateTime.now();
	
	private boolean status;
	
	private Object metaData;
	
	private T response;
	
	private ServiceErrors errors = new ServiceErrors();

}
