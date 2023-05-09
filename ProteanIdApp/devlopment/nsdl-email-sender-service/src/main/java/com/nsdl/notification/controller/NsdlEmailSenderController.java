package com.nsdl.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.notification.dto.SendEmailDTO;
import com.nsdl.notification.dto.StatusDTO;
import com.nsdl.notification.service.NsdlEmailSenderService;

@RestController
@RequestMapping("/nsdlnotification")
public class NsdlEmailSenderController {

	private static final Logger logger = LoggerFactory.getLogger(NsdlEmailSenderController.class);

	@Autowired
	private NsdlEmailSenderService emailSenderService;
	
	@Autowired
	ObjectMapper objectMapper;

	@PostMapping(value = "/sendEmail", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public StatusDTO sendNsdlEmail(@RequestParam("emailContent") String emailContent, @RequestParam(required = false,name ="file") MultipartFile file) throws JsonMappingException, JsonProcessingException {
		logger.info("inside sendNsdlEmail method");
		SendEmailDTO sendMailDTO = objectMapper.readValue(emailContent, SendEmailDTO.class);
		StatusDTO sendMailNotification = emailSenderService.sendMailNotification(sendMailDTO,file);
		logger.info("returning success response");

		return sendMailNotification;

	}
}
