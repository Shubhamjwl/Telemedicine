package com.nsdl.verifier.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class VerifierConfig {

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/verifier/v1/loginVerifier/**")
						.filters(f -> f.rewritePath("/verifier/(?<remains>.*)", "/login/${remains}"))
						.uri("http://172.30.151.101:7064/").id("login-service"))
				.route(r -> r.path("/verifier/v1/sync/**")
						.filters(f -> f.rewritePath("/verifier/(?<remains>.*)", "/user/${remains}"))
						.uri("http://172.30.151.101:7062/").id("user-app-service"))
				.route(r -> r.path("/verifier/v1/getBioDetails/**")
						.filters(f -> f.rewritePath("/verifier/(?<remains>.*)", "/user/${remains}"))
						.uri("http://172.30.151.101:7062/").id("user-app-service"))
				.build();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
