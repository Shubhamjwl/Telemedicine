package com.nsdl.net.gupshup.mail.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class SendMailDTO {
	
	@JsonProperty(value = "name")
	private String name;
	
	@JsonProperty(value = "recipients")
	@NotBlank(message = "Recipient is mandatory")
	private String[] recipients;
	
	@JsonProperty(value = "subject")
	@NotBlank(message = "Subject is mandatory")
	private String subject;
	
	@JsonProperty(value = "content")
	@NotBlank(message = "Content is mandatory")
	private String content;
	
	@JsonProperty(value = "flag")
	private String flag;
	
	@JsonProperty(value = "content_type")
	private String content_type;
	
}
