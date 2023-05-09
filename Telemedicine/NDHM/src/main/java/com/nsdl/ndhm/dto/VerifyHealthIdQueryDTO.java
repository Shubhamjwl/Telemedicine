package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyHealthIdQueryDTO {

	private String id;
	private String purpose;
	private String authMode;
	private VerifyHealthIdRequesterDTO requester;
}
