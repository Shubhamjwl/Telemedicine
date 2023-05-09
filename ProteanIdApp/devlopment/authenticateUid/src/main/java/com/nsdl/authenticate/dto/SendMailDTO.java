package com.nsdl.authenticate.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMailDTO {
	
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
	
	
	
}
