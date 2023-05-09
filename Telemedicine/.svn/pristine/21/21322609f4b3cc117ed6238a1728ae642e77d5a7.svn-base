package com.nsdl.telemedicine.videoConference.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.nsdl.telemedicine.videoConference.exception.ServiceError;

import lombok.Data;

@Data
public class InviteServiceMainResponseDTO<T> {

	private String id;

	private String version;

	private LocalDateTime responsetime;

	private boolean status;
	
	private T response;
	
	private List<ServiceError> errors;
	
}
