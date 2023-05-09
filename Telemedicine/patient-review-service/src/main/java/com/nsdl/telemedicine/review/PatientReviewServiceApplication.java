package com.nsdl.telemedicine.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Pegasus_girishk
 *
 */
@SpringBootApplication(scanBasePackages = { "com.nsdl.telemedicine.review" })
public class PatientReviewServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientReviewServiceApplication.class, args);
	}
}
