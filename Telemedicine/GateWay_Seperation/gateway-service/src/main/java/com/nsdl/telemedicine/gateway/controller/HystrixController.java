package com.nsdl.telemedicine.gateway.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.gateway.dto.MainResponseDTO;
import com.nsdl.telemedicine.gateway.dto.ServiceError;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class HystrixController {

	@RequestMapping("/signin")
	public Mono<MainResponseDTO<?>> loginServiceFallback() {

		MainResponseDTO<String> response = new MainResponseDTO<>();
		ServiceError error = new ServiceError();
		error.setErrorCode("ERR-CSAT-500");
		error.setMessage("Login API is taking too long to respond or is down. Please try again later");

		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		response.setResponse(null);
		response.setStatus(false);
		response.setErrors(Arrays.asList(error));

		return Mono.just(response);
	}


}
