package com.nsdl.notification.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.nsdl.notification.dto.SendMailDTO;
import com.nsdl.notification.dto.StatusDTO;
import com.nsdl.notification.service.NsdlEmailSenderService;

@Service
public class NsdlEmailSenderServiceImpl implements NsdlEmailSenderService {

	@Value("${gupshup.send.email}")
	private String emailURL;

	@Value("${notification.mail.content}")
	private String content;

	@Value("${notification.mail.content_type}")
	private String contentType;

	@Value("${notification.mail.content_type.value}")
	private String contentTypeValue;

	@Value("${notification.mail.subject}")
	private String subject;

	@Value("${notification.mail.recipients}")
	private String recipients;

	@Value("${notification.mail.name.key}")
	private String nameKey;

	@Value("${notification.mail.name.value}")
	private String nameValue;

	@Value("${notification.mail.userId.key}")
	private String userIdKey;

	@Value("${notification.mail.userId.value}")
	private String userIdvalue;

	@Value("${notification.mail.password.key}")
	private String passwordKey;

	@Value("${notification.mail.password.value}")
	private String passwordValue;

	@Value("${notification.mail.version.key}")
	private String versionKey;

	@Value("${notification.mail.version.value}")
	private String versionValue;

	@Value("${notification.mail.fromEmailId.key}")
	private String fromEmailIdKey;

	@Value("${notification.mail.fromEmailId.value}")
	private String fromEmailIdValue;

	@Value("${notification.mail.method.key}")
	private String methodKey;

	@Value("${notification.mail.method.value}")
	private String methodValue;

	@Value("${notification.http.success.response.status}")
	private String successStatus;

	@Value("${notification.http.success.response.message}")
	private String successMessage;

	@Value("${notification.http.fail.response.status}")
	private String failStatus;

	@Value("${notification.http.fail.response.message}")
	private String failMessage;

	@Value("${notification.mail.flag.vt}")
	private String flagForVTEnv;

	@Value("${notification.mail.flag.dev}")
	private String flagForDevEnv;

	private static final Logger logger = LoggerFactory.getLogger(NsdlEmailSenderServiceImpl.class);

	@Override
	public StatusDTO sendMailNotification(SendMailDTO mailDTO) {
		ResponseEntity<StatusDTO> mailResponse=null;
			logger.info("Mail Dto before sending request to notification:" + mailDTO);
			try {
				RestTemplate restTeplat = new RestTemplate();
				logger.info("Calling notification api...");
				logger.info("Email Url::"+emailURL);
			    mailResponse = restTeplat.postForEntity(emailURL,mailDTO, StatusDTO.class);
				logger.info("SUCCESSFULLY RECEIVED DATA FROM notification API RESPONSE : " + mailResponse.getBody());
				return mailResponse.getBody();
			} catch (Exception e) {
				logger.error("Exception occurred while sending email");
				e.printStackTrace();
				return mailResponse.getBody();
			}
	}
	private MultiValueMap<String, Object> buildMultiValueMap(SendMailDTO mailDTO, StringBuilder builderRecipients) {
		logger.info("Inside buildMultiValueMap Method************" + mailDTO);
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
		logger.info("Request body from notification APIS************" + map);
		return map;
	}

}
