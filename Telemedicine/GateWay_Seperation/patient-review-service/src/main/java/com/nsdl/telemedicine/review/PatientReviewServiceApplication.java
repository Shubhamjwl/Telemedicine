package com.nsdl.telemedicine.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Pegasus_girishk
 *
 */
@SpringBootApplication(scanBasePackages = { "com.nsdl.telemedicine.review", "com.nsdl.telemedicine.gateway" })
public class PatientReviewServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientReviewServiceApplication.class, args);
	}
}
