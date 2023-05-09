package com.nsdl.telemedicine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = { "com.nsdl.telemedicine" })
public class TeleMarketplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeleMarketplaceApplication.class, args);
	}

}
