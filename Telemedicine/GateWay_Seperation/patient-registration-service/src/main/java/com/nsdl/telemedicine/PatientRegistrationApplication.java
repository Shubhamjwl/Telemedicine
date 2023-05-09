package com.nsdl.telemedicine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = { "com.nsdl.telemedicine.gateway", "com.nsdl.telemedicine.patient" })
public class PatientRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientRegistrationApplication.class, args);
	}
}
