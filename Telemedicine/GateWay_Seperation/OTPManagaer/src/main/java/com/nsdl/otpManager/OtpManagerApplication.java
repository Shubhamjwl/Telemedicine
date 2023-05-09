package com.nsdl.otpManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@EntityScan("com.nsdl.otpManager.entity")
@EnableAutoConfiguration(exclude= {SecurityAutoConfiguration.class})
@SpringBootApplication(scanBasePackages = "com.nsdl.otpManager")
public class OtpManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpManagerApplication.class, args);
	}

	
}
