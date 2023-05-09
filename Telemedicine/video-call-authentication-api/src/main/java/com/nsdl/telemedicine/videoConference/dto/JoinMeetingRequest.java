package com.nsdl.telemedicine.videoConference.dto;

import lombok.Data;

@Data
public class JoinMeetingRequest {
	
	private String meetingID;
	private String password;
	private String userDisplayName;
	private String userId;
	private boolean redirect;

}
