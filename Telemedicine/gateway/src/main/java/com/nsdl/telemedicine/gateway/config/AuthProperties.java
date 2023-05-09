package com.nsdl.telemedicine.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "auth")
@Data
@NoArgsConstructor
public class AuthProperties {

	private JwtProperties jwtProperties;
	private Token token;
	private Encryption encryption;
	private Captcha captcha;
}
