/**
 * 
 */
package com.nsdl.telemedicine.consultancy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Pegasus_girishk
 *
 */
@Configuration
public class ConsultationConfig {
	@Bean("template")
	public RestTemplate getRestTemlate() {
		return new RestTemplate();
	}
}
