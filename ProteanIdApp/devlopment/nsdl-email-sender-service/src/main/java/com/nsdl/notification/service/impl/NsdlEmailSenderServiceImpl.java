package com.nsdl.notification.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nsdl.notification.constant.ErrorConstant;
import com.nsdl.notification.constant.NsdlEmailConstant;
import com.nsdl.notification.dto.SendEmailDTO;
import com.nsdl.notification.dto.StatusDTO;
import com.nsdl.notification.exception.NsdlSendEmailException;
import com.nsdl.notification.service.NsdlEmailSenderService;

@Service
public class NsdlEmailSenderServiceImpl implements NsdlEmailSenderService {

	private static final Logger logger = LoggerFactory.getLogger(NsdlEmailSenderServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public StatusDTO sendMailNotification(SendEmailDTO mailDTO, MultipartFile file) {
		StatusDTO statusDTO = new StatusDTO();
		try {
			logger.info("inside sendMailNotification method");
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(mailDTO.getToEmailId());
			helper.setFrom(mailDTO.getFromEmailId());
			helper.setText(mailDTO.getEmailBody(), true);
			helper.setSubject(mailDTO.getSubject());

			if (mailDTO.getBcc() != null && !mailDTO.getBcc().isEmpty()) {
				helper.setBcc(mailDTO.getBcc());
			}
			if (mailDTO.getCc() != null && !mailDTO.getCc().isEmpty()) {
				helper.setCc(mailDTO.getCc());
			}

			if (file != null && !file.isEmpty()) {
				helper.addAttachment(file.getOriginalFilename(), file);
			}

			logger.info("calling send method with request");
			mailSender.send(helper.getMimeMessage());
		} catch (MessagingException e) {
			logger.info("returning error status");
			throw new NsdlSendEmailException(ErrorConstant.TRY_AGAIN.getCode(), ErrorConstant.TRY_AGAIN.getMessage());
		}
		logger.info("returning success status");
		statusDTO.setMessage(NsdlEmailConstant.NSDL_EMAIL_SENT_SUCCESS);
		statusDTO.setStatus(HttpStatus.OK.toString());
		return statusDTO;
	}

}
