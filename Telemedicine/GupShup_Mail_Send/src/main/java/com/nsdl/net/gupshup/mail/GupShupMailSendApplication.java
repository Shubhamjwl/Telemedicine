package com.nsdl.net.gupshup.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class GupShupMailSendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GupShupMailSendApplication.class, args);
	}

}
