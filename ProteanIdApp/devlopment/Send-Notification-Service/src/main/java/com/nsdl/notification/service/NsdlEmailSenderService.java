package com.nsdl.notification.service;

import org.springframework.web.multipart.MultipartFile;

import com.nsdl.notification.dto.SendMailDTO;
import com.nsdl.notification.dto.StatusDTO;

public interface NsdlEmailSenderService {
	
	public StatusDTO sendMailNotification(SendMailDTO  mailDTO);

}
