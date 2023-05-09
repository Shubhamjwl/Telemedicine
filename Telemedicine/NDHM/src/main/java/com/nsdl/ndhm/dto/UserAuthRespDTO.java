package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthRespDTO {
	private String token;
	private String errorCode;
	private String error;
}
