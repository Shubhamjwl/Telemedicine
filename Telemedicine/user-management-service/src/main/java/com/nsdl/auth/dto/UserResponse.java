package com.nsdl.auth.dto;

import lombok.Data;

@Data
public class UserResponse {
	
	private String message;

	private String userId;
	
	private String token;

	private String role;
	
	private boolean isPasswordChanged;
	
	private String userName;
	
	private boolean closeDrGrp;
}
