package com.nsdl.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nsdl.notification.dto.SendMailDTO;
import com.nsdl.notification.dto.StatusDTO;


@RestController
@CrossOrigin("*")
public class NsdlMailController {

	@Autowired
	private NsdlEmailSenderService emailSenderService;

	@PostMapping(value = "/sendEmailNotification", consumes = "application/json")
	public StatusDTO sendMailGupShupAPI(@RequestBody SendMailDTO sendMailDTO,@RequestParam("file") MultipartFile file) {

		return emailSenderService.sendMailNotification(sendMailDTO,file);
	}
}
