package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthPasswordRespDTO {
	private String token;
}
