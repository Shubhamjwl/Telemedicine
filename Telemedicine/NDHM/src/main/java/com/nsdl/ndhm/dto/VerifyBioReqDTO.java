package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyBioReqDTO {
	private String aadhaar;
	private String bioType;
	private String pid;
	private String restrictions;
}
