package com.nsdl.ndhm;

import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@EnableAutoConfiguration
@SpringBootApplication
@EnableAsync
@EnableCaching
public class NdhmHealthidServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NdhmHealthidServiceApplication.class, args);
	}

	/*
	 * for rest template bean
	 */
	@Bean
	public RestTemplate restTemplate() throws UnknownHostException {
		return new RestTemplate();
	}
}
