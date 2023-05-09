package com.nsdl.telemedicine.videoConference.service;

import com.nsdl.telemedicine.videoConference.dto.CreateMeetingRequest;
import com.nsdl.telemedicine.videoConference.dto.CreateMeetingResponse;
import com.nsdl.telemedicine.videoConference.dto.JoinMeetingRequest;
import com.nsdl.telemedicine.videoConference.dto.JoinMeetingResponse;
import com.nsdl.telemedicine.videoConference.exception.BBBException;

public interface BBBMeeting {
	
	
	public CreateMeetingResponse createMeeting(CreateMeetingRequest createRequest)throws BBBException;
	
	public JoinMeetingResponse getJoinMeetingURL(JoinMeetingRequest joinRequest)throws BBBException;
	

}
