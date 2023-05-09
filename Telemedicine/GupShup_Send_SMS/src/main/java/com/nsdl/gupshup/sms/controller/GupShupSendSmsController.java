package com.nsdl.gupshup.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsdl.gupshup.sms.aop.LoggingClientInfo;
import com.nsdl.gupshup.sms.dto.SendSmsDTO;
import com.nsdl.gupshup.sms.dto.StatusDTO;
import com.nsdl.gupshup.sms.service.GupShupSmsSenderService;

@RestController
@CrossOrigin("*")
@LoggingClientInfo
@RefreshScope
public class GupShupSendSmsController {
	
	@Value("${gupshup.http.sendsms.status.fail}")
	private String failStatus;
	
	@Value("${gupshup.http.sendsms.msg.fail}")
	private String failStatusMsg;
	
	
	@Autowired
	private GupShupSmsSenderService gupshupSmsSenderService;
	
	@PostMapping(value = "/sendSmsGupShupAPI")
	public StatusDTO sendSmsGupShupAPI(@RequestBody SendSmsDTO sendSmsDTO) throws JsonProcessingException {

		StatusDTO statusDTO = new StatusDTO();
		 statusDTO = gupshupSmsSenderService.sendSmsGupShupAPI(sendSmsDTO);		
		return statusDTO;
	}

}
