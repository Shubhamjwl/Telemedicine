package com.nsdl.notification.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailDTO {

	@JsonProperty(value = "toEmailId")
	private String toEmailId;
	
	@JsonProperty(value = "fromEmailId")
	private String fromEmailId;
	
	@JsonProperty(value = "cc")
	private String cc;
	
	@JsonProperty(value = "bcc")
	private String bcc;
	
	@JsonProperty(value = "subject")
	private String subject;
	
	@JsonProperty(value = "emailBody")
	private String emailBody;
	
	@JsonProperty(value = "file")
	private MultipartFile file;
}
