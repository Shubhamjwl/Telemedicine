package com.nsdl.gupshup.sms.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nsdl.gupshup.sms.aop.LoggingClientInfo;

import lombok.extern.log4j.Log4j2;

@Component
@LoggingClientInfo
@Log4j2
public class ProxyUtility {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${proxy.flag.active}")
	private String flagForProxy;
	
	@Value("${proxy.url}")
	private String proxyUrl;

	@Value("${proxy.port}")
	private Integer proxyPort;
	
	@Value("${spring.profiles.active}")
	private String env;
	
	@Value("${gupshup.sendsms.flag.vt}")
	private String flagForVTEnv;
	
	@Value("${gupshup.sendsms.flag.dev}")
	private String flagForDevEnv;
	
	public ResponseEntity<String> sendSmsByOverridingProxy(StringBuilder queryString) {
		
			ResponseEntity<String> responseEntity = restTemplate.exchange(queryString.toString(), HttpMethod.POST, null, String.class);
			log.info("RESPONSE AFTER SENDING SMS "+responseEntity.getBody());
			return responseEntity;
	}

}
