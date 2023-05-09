package com.nsdl.ndhm.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestCallUtil {

	private static final Logger logger = LoggerFactory.getLogger(RestCallUtil.class);

	private RestCallUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static ResponseEntity<?> postApiRequest(String url, Object body, Class<?> responseClass) throws Exception {
		logger.info("template creating {} {}" ,url ,body);
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<?> result = null;
		HttpEntity<?> entity = new HttpEntity<>(body, headers);
		try {
			result =  restTemplate.exchange(url, HttpMethod.POST, entity, responseClass);
		} catch (Exception e) {
			 logger.error("Error in postApiRequest {} "  ,e);
			 
		}
 
		return result;
	}

}