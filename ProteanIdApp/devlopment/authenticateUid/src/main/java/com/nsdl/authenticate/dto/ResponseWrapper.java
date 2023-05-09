package com.nsdl.authenticate.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsdl.authenticate.exception.ServiceError;

import lombok.Data;

@Data
public class ResponseWrapper<T> {

	private String id;
	private String version;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime responsetime = LocalDateTime.now(ZoneId.of("UTC"));
	private T response;
	private List<ServiceError> errors = new ArrayList<>();
}
