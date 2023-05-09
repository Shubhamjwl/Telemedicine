package com.nsdl.telemedicine.videoConference.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class VcAuthValidateDTO {
	
	private String token;
	@NotBlank(message = "Payload cannot be null or blank")
	private String encryptedPayload;

}
