package com.nsdl.auth.dto;

import lombok.Data;

@Data
public class UserActiveDeactiveRequestDTO {
	private String userId;
	private String operationType;
	private String reason;
}
