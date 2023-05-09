package com.nsdl.ipan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class IpanIntegrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpanIntegrationServiceApplication.class, args);
		
	}

}
