package com.nsdl.telemedicine.scribe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ScribeConfig {
	
	@Bean
	public RestTemplate getRestTemlate() {
		return new RestTemplate();
	}
	
}
