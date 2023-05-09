package com.nsdl.net.gupshup.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.net.gupshup.mail.aop.LoggingClientInfo;
import com.nsdl.net.gupshup.mail.dto.SendMailDTO;
import com.nsdl.net.gupshup.mail.dto.StatusDTO;
import com.nsdl.net.gupshup.mail.service.GupShupEmailSenderService;

@RestController
@CrossOrigin("*")
@LoggingClientInfo
@RefreshScope
public class GupShupController {

	@Autowired
	private GupShupEmailSenderService gupShupEmailSenderService;

	@PostMapping(value = "/sendMailGupShupAPI", consumes = "application/json")
	public StatusDTO sendMailGupShupAPI(@RequestBody SendMailDTO sendMailDTO) {

		return gupShupEmailSenderService.sendMailGupShup(sendMailDTO);
	}
}
