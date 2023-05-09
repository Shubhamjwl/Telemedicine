package com.nsdl.telemedicine.gateway.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Token {

	private String header;
	private String customUrl;
	private String roleFunctionUrl;
	
}
