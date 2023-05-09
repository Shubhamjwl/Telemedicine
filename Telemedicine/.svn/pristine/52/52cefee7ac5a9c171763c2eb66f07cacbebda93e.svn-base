package com.nsdl.telemedicine.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
		System.out.println("hiiiiii");
	}
	@Bean
	public BootListener getBootListener() {
		return new BootListener();
	}

}
