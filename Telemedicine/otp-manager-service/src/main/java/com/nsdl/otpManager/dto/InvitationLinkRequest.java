package com.nsdl.otpManager.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data	
public class InvitationLinkRequest {
	@NotBlank
	private String inviteMode;
	@NotBlank
	private String inviteOn;
	@NotBlank
	private  String content;
	

}
