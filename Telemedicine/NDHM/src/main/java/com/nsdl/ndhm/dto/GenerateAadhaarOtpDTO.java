package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenerateAadhaarOtpDTO {

	private String  aadhaar;
	private String  aadhaarName;
}
