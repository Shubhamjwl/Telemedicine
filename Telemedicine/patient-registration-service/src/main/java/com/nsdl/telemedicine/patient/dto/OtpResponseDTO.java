package com.nsdl.telemedicine.patient.dto;

import lombok.Data;

@Data
public class OtpResponseDTO {

	 private String message;
     private String description;
     
     public OtpResponseDTO() {}
     public OtpResponseDTO(String message, String description) {
		this.message = message;
		this.description = description;
	}
	
}
