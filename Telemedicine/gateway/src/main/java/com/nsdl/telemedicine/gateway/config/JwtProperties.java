package com.nsdl.telemedicine.gateway.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtProperties {

	private String secret;
	private String base;
	private String expiry;
	private String latitudeDigits;
	private String longitudeDigits;
	
}
