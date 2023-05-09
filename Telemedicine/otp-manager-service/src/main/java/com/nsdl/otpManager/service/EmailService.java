package com.nsdl.otpManager.service;



import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nsdl.otpManager.constant.AppConstant;
import com.nsdl.otpManager.dto.SendMailDTO;
import com.nsdl.otpManager.dto.StatusDTO;
import com.nsdl.otpManager.dto.TemplateDtls;



@Service
public class EmailService {

  //  private JavaMailSender javaMailSender;
    
    @Value("${adminEmail}")
   	private String adminEmail;
	
	@Autowired
	private RestTemplate restTemplate;
    
   
    @Value("${emailRequestUrl}")
    private String emailRequestUrl;
    
    @Value("${spring.mail.host}")
    private String smtpHost;
    
    @Value("${spring.mail.port}")
    private String smtpPort;
	

   /* public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    } */
	
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	

     
   /* public void sendMail(TemplateDtls templateDtl) throws MessagingException  {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		try {
			
			helper.setText(templateDtl.getTemplateContent(), true);
			helper.setSubject(templateDtl.getSubjectLine());
			helper.setTo(templateDtl.getEmailId());
			helper.setFrom(adminEmail);
			//add Attachment
			if(templateDtl.getAttachmentPath()!=null)
			{
				FileSystemResource file = new FileSystemResource(new File(templateDtl.getAttachmentPath()));
				if(file.exists())
				{
					helper.addAttachment(file.getFilename(), file);
					logger.info("File attached successfully!!");
				}
				else
				{
					logger.info("File not exist on given path:"+templateDtl.getAttachmentPath());
				}
				
			}
			javaMailSender.send(mimeMessage);
			logger.info("Email sent Successfully");
		} catch (MessagingException e) {
			e.printStackTrace();
			throw e;
			
		}
   } */

    
	public void sendeMailLink(String toEmail, String content) throws UnsupportedEncodingException  
    {
    	 String subject = AppConstant.INVITATION_SUBJECT;
    	 SendMailDTO sendMailDTO = new SendMailDTO();
 		String[] recipients = {toEmail};
 		sendMailDTO.setContent(URLEncoder.encode(content,"UTF-8"));
 		sendMailDTO.setName("Admin");
 		sendMailDTO.setRecipients(recipients);
 		sendMailDTO.setSubject(subject);
 		sendMailDTO.setContentType("html");
 		//restTemplate.postForEntity(emailRequestUrl, sendMailDTO, StatusDTO.class);
    }
	
		 
	 public void sendNotification(TemplateDtls templateDtl) throws MessagingException
	 {
		 //Through Gupshup
		 	SendMailDTO sendMailDTO = new SendMailDTO();
			String[] recipients = {templateDtl.getEmailId()};
			sendMailDTO.setContent(templateDtl.getTemplateContent());
			sendMailDTO.setName(templateDtl.getUserId());
			sendMailDTO.setRecipients(recipients);
			sendMailDTO.setSubject(templateDtl.getSubjectLine());
			sendMailDTO.setContentType("text/html");
			logger.info("Request body from OTP manager service is********************:"+sendMailDTO);
			restTemplate.postForEntity(emailRequestUrl, sendMailDTO, StatusDTO.class);
		
		 //Through MailSender
		/* MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			try {
				
				helper.setText(templateDtl.getTemplateContent(), true);
				helper.setSubject(templateDtl.getSubjectLine());
				helper.setTo(templateDtl.getEmailId());
				helper.setFrom(adminEmail);
				//add Attachment
				if(templateDtl.getAttachmentPath()!=null)
				{
					FileSystemResource file = new FileSystemResource(new File(templateDtl.getAttachmentPath()));
					if(file.exists())
					{
						helper.addAttachment(file.getFilename(), file);
						logger.info("File attached successfully!!");
					}
					else
					{
						logger.info("File not exist on given path:"+templateDtl.getAttachmentPath());
					}
					
				}
				javaMailSender.send(mimeMessage);
				logger.info("Email sent Successfully");
			} catch (MessagingException e) {
				e.printStackTrace();
				throw e;
				
			} 
			*/
		 //commented by sayalia
		/* try {
				

				// sets SMTP server properties
				Properties properties = new Properties();
				properties.put("mail.smtp.host",smtpHost);
				properties.put("mail.smtp.port",smtpPort);
				properties.put("mail.smtp.auth", "false");
				Session session = Session.getInstance(properties);

				// creates a new e-mail message
				Message msg = new MimeMessage(session);

				msg.setFrom(new InternetAddress(adminEmail));
				
				InternetAddress[] toAddresses = InternetAddress.parse(templateDtl.getEmailId());
				msg.setRecipients(Message.RecipientType.TO, toAddresses);

				msg.setSubject(templateDtl.getSubjectLine());
				msg.setSentDate(new Date());

				// creates message part
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(templateDtl.getTemplateContent(), "text/html;charset=UTF-8");

				// creates multi-part
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);

				//Attachement
				if (templateDtl.getAttachmentPath()!=null) 
				{
					File attachFile=new File(templateDtl.getAttachmentPath());
					if(attachFile.exists())
					{
						MimeBodyPart attachPart = new MimeBodyPart();
						try 
						{
							attachPart.attachFile(attachFile);
						} catch (IOException ex) {
							logger.error("Exception occurred while file attachment");

						}
						multipart.addBodyPart(attachPart);
					}
				
				}
			// sets the multi-part as e-mail's content
				msg.setContent(multipart);
				 // sends the e-mail
				// sets the multi-part as e-mail's content
				msg.setContent(multipart);
				// sends the e-mail
				logger.info("Trying to send email...");
				Transport.send(msg);
				logger.info("Email Sent successfully");
		} catch (Exception e) {
				logger.info("Exception occurred while sending email");
				e.printStackTrace();
				
			}*/
	 }
	 
	 
}
