package com.nsdl.telemedicine.consultancy.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ConsultPresDTO {

	@NotBlank(message = "AppointID cannot be null or blank")
	private String appointID;

	@NotBlank(message = "File path cannot be null or blank")
	private String filePath;
	
}
