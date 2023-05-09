package com.nsdl.telemedicine.videoConference.dto;

import lombok.Data;

@Data
public class InviteServiceRequest {

	private String inviteMode;

	private String inviteOn;

	private String content;

}
