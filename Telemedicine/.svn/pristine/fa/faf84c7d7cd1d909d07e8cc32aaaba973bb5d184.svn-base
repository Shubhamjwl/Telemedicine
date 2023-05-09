package com.nsdl.telemedicine.videoConference.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class InvitationRequest {

	@NotBlank(message = "appointmentId cannot be null or blank")
	private String appointmentId;
	
	@NotBlank(message = "Invite Mode cannot be null or blank, it should be MOBILE or EMAIL.")
	private String inviteMode;
	
	@NotBlank(message = "Invite On Value cannot be null or blank")
	private String inviteOn;
	
}
