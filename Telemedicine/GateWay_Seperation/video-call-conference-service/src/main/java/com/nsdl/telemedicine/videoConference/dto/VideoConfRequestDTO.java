package com.nsdl.telemedicine.videoConference.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class VideoConfRequestDTO {
	
	
	@NotBlank(message = "appointmentId cannot be null or blank")
	private String appointmentId;
	
	@NotBlank(message = "userType cannot be null or blank")
	private String userType;
	
	@NotBlank(message = "source cannot be null or blank")
	private String source;
}
