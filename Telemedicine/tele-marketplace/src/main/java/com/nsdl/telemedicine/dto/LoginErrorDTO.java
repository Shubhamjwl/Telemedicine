package com.nsdl.telemedicine.dto;

import lombok.Data;

@Data
public class LoginErrorDTO {
	
	private String ip;
	private String key;
	private String message;
}
