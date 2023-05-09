package com.nsdl.auth.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class RestCallUtil {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static ResponseEntity<?> postApiRequest(String url, Object body,Class<?> responseClass)throws Exception {
		System.out.println("template creating");
		RestTemplate restTemplate = new RestTemplate();
		//RestTemplate restTemplate = restTemplateBuilder.build();
		System.out.println("template created");
		/*
		 * RestTemplate restTemplate =
		 * restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(30))
		 * .setReadTimeout(Duration.ofSeconds(30)).build();
		 */
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//ParameterizedTypeReference<MainResponseDTO<response>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>>() {
			
		HttpEntity<?> entity = new HttpEntity<>(body, headers);
		System.out.println("Calling");
		return restTemplate.exchange(url, HttpMethod.POST, entity, responseClass);
	}

}
