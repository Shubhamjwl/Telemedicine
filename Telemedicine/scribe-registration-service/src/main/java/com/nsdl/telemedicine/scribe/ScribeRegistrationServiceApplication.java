package com.nsdl.telemedicine.scribe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.nsdl.telemedicine.scribe.*", "com.nsdl.telemedicine.doctor.*", "com.nsdl.telelmedicine.patient.*"})
public class ScribeRegistrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScribeRegistrationServiceApplication.class, args);
	}
}
