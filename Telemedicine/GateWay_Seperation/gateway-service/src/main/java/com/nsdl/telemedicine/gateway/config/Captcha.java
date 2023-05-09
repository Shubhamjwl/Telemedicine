package com.nsdl.telemedicine.gateway.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Captcha {

	private String verifyUrl;
	private String sessionId;
	private String captchaValue;
	
}