/**
 * 
 */
package com.nsdl.telemedicine.doctor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Pegasus_girishk
 *
 */
@Configuration
public class SecurityConfig {
	
	@Bean
	public RestTemplate getRestTemlate() {
		return new RestTemplate();
	}
}
