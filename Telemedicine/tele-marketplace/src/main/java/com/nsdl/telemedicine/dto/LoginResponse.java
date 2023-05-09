package com.nsdl.telemedicine.dto;

import lombok.Data;

@Data
public class LoginResponse {
	
	private String success;
	private String api_token;
	private LoginErrorDTO error;
}
