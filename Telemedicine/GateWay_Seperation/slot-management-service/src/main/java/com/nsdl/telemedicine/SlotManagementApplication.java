package com.nsdl.telemedicine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.nsdl.telemedicine" })
public class SlotManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlotManagementApplication.class, args);
	}

	 
}
