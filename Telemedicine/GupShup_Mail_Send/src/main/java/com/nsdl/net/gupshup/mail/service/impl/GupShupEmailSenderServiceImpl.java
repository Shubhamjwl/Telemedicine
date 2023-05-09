package com.nsdl.net.gupshup.mail.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.nsdl.net.gupshup.mail.aop.LoggingClientInfo;
import com.nsdl.net.gupshup.mail.dto.SendMailDTO;
import com.nsdl.net.gupshup.mail.dto.StatusDTO;
import com.nsdl.net.gupshup.mail.service.GupShupEmailSenderService;


import lombok.extern.log4j.Log4j2;

@Service
//@Transactional(propagation = Propagation.REQUIRED)
@Log4j2
@LoggingClientInfo
public class GupShupEmailSenderServiceImpl implements GupShupEmailSenderService {

	@Value("${gupshup.send.email}")
	private String emailURL;

	@Value("${gupshup.mail.content}")
	private String content;
	
	@Value("${gupshup.mail.content_type}")
	private String contentType;
	
	@Value("${gupshup.mail.content_type.value}")
	private String contentTypeValue;

	@Value("${gupshup.mail.subject}")
	private String subject;

	@Value("${gupshup.mail.recipients}")
	private String recipients;

	@Value("${gupshup.mail.name.key}")
	private String nameKey;

	@Value("${gupshup.mail.name.value}")
	private String nameValue;

	@Value("${gupshup.mail.userId.key}")
	private String userIdKey;

	@Value("${gupshup.mail.userId.value}")
	private String userIdvalue;

	@Value("${gupshup.mail.password.key}")
	private String passwordKey;

	@Value("${gupshup.mail.password.value}")
	private String passwordValue;

	@Value("${gupshup.mail.version.key}")
	private String versionKey;

	@Value("${gupshup.mail.version.value}")
	private String versionValue;

	@Value("${gupshup.mail.fromEmailId.key}")
	private String fromEmailIdKey;

	@Value("${gupshup.mail.fromEmailId.value}")
	private String fromEmailIdValue;

	@Value("${gupshup.mail.method.key}")
	private String methodKey;

	@Value("${gupshup.mail.method.value}")
	private String methodValue;

	@Value("${gupshup.http.success.response.status}")
	private String successStatus;

	@Value("${gupshup.http.success.response.message}")
	private String successMessage;

	@Value("${gupshup.http.fail.response.status}")
	private String failStatus;

	@Value("${gupshup.http.fail.response.message}")
	private String failMessage;
	
	@Value("${spring.profiles.active}")
	private String env;
	
	@Value("${gupshup.mail.flag.vt}")
	private String flagForVTEnv;
	
	@Value("${gupshup.mail.flag.dev}")
	private String flagForDevEnv;

	/*
	 * @Autowired
	 * 
	 * @Qualifier("restTemplateProxy") private RestTemplate restTemplateProxy;
	 */
	
	private static final Logger logger = LoggerFactory.getLogger(GupShupEmailSenderServiceImpl.class);
	
	
	@Override
	public StatusDTO sendMailGupShup(SendMailDTO mailDTO) {
		
		String flag = "";
		if(env.equals("vt")) {
			flag = flagForVTEnv;
		}else if(env.equals("dev") || env.equals("prod") || env.equals("uat")){
			flag = flagForDevEnv;
		}
		if(flag == null || flag.equals("Y")) {
			StringBuilder builderRecipients = new StringBuilder();
			for (String eacheNumber : mailDTO.getRecipients()) {
				builderRecipients.append(eacheNumber + ",");
			}
	
			builderRecipients.replace(builderRecipients.lastIndexOf(","), builderRecipients.lastIndexOf(",") + 1, "");
	
			MultiValueMap<String, Object> map = buildMultiValueMap(mailDTO, builderRecipients);
	
			HttpHeaders headers = new HttpHeaders();	
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	
			HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(map,
					headers);
	
			ResponseEntity<String> responseStatus = null;
			logger.info("Mail Dto before sending request to gupshup:"+mailDTO);
			try {
				RestTemplate restTeplat=new RestTemplate();	
				logger.info("Calling gupshup api...");
				responseStatus = restTeplat.exchange(emailURL, HttpMethod.POST, httpEntity, String.class);
				logger.info("SUCCESSFULLY RECEIVED DATA FROM GUPSHUP API RESPONSE : "
							+ responseStatus.getBody());
				return getResponseStatus(responseStatus.getBody());
			} catch (Exception e) {
				logger.error("Exception occurred while sending email");
				e.printStackTrace();
				return new StatusDTO(failStatus, failMessage, null);
			}
		}else {
			return StatusDTO.builder().status(successStatus).message(successMessage).build();
			//			return new StatusDTO(failStatus, failMessage, null);
		}
	}

	private StatusDTO getResponseStatus(String body) {
		StringBuilder response = new StringBuilder(body);
		String status = response.substring(0, response.indexOf("|")).trim();
		// String message = response.substring(response.lastIndexOf("|") + 1,
		// response.length() - 1).trim();
		String code = response.substring(response.indexOf("|") + 1, response.lastIndexOf("|")).replaceAll("|", "")
				.trim();

		return status.equals(successStatus)
				? StatusDTO.builder().status(successStatus).message(successMessage).code(code).build()
				: StatusDTO.builder().status(failStatus).message(failMessage).code(code).build();
	}

	private MultiValueMap<String, Object> buildMultiValueMap(SendMailDTO mailDTO, StringBuilder builderRecipients) {
		logger.info("Inside buildMultiValueMap Method************"+mailDTO);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		
		map.add(methodKey, methodValue);
		map.add(userIdKey, userIdvalue);
		map.add(passwordKey, passwordValue);
		map.add(versionKey, versionValue);
		map.add(nameKey, nameValue);
		map.add(recipients, builderRecipients + "");
		map.add(fromEmailIdKey, fromEmailIdValue);
		map.add(subject, mailDTO.getSubject());
		map.add(content, mailDTO.getContent());
		map.add(contentType, mailDTO.getContent_type());
		logger.info("Request body from GupShup APIS************"+map);
		return map;
	}

}
