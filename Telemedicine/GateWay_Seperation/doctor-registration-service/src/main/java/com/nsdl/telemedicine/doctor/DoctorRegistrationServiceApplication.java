package com.nsdl.telemedicine.doctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.nsdl.telemedicine.gateway", "com.nsdl.telemedicine.doctor" })
public class DoctorRegistrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorRegistrationServiceApplication.class, args);
		System.out.println("hiii");
	}

}
