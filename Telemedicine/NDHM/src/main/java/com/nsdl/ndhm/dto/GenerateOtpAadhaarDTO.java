package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenerateOtpAadhaarDTO {
	
	private String aadhaar;
	private String authMethod;
}
