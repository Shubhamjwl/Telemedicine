package com.nsdl.gupshup.sms.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SendSmsDTO {
	
	@JsonProperty(value = "sendTo")
	@NotBlank
	private String[] sendTo;
	
	@JsonProperty(value = "message")
	@NotBlank
	private String message;
	
	@JsonProperty(value = "flag")
	private String flag;

}
