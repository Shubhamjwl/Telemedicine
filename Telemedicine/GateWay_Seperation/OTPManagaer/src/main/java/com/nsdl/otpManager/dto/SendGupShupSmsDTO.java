package com.nsdl.otpManager.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendGupShupSmsDTO {
	
	@JsonProperty(value = "sendTo")
	@NotBlank
	private String[] sendTo;
	
	@JsonProperty(value = "message")
	@NotBlank
	private String message;
	
	@JsonProperty(value = "flag")
	private String flag;

}
