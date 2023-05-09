package com.nsdl.payment.utility;

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
		//System.out.println("template creating");
		RestTemplate restTemplate = new RestTemplate();
		//System.out.println("template created");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> entity = new HttpEntity<>(body, headers);
		//System.out.println("Calling..");
		return restTemplate.exchange(url, HttpMethod.POST, entity, responseClass);
	}

}
