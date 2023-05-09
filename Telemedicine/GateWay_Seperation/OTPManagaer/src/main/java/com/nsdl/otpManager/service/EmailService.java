package com.nsdl.otpManager.service;



import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.nsdl.otpManager.constant.AppConstant;
import com.nsdl.otpManager.dto.SendMailDTO;
import com.nsdl.otpManager.dto.TemplateDtls;



@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    
    @Value("${adminEmail}")
   	private String adminEmail;
	
	/*@Autowired
	private RestTemplate restTemplate;
    
   
    @Value("${emailRequestUrl}")
    private String emailRequestUrl;*/
    
	

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
	
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
		 	/*SendMailDTO sendMailDTO = new SendMailDTO();
			String[] recipients = {templateDtl.getEmailId()};
			sendMailDTO.setContent(templateDtl.getTemplateContent());
			sendMailDTO.setName(templateDtl.getUserId());
			sendMailDTO.setRecipients(recipients);
			sendMailDTO.setSubject(templateDtl.getSubjectLine());
			restTemplate.postForEntity(emailRequestUrl, sendMailDTO, StatusDTO.class);*/
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
	 }
}
