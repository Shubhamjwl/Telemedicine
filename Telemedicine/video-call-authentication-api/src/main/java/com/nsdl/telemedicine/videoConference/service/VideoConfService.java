package com.nsdl.telemedicine.videoConference.service;

import com.nsdl.telemedicine.videoConference.dto.InvitationRequest;
import com.nsdl.telemedicine.videoConference.dto.InvitationResponse;
import com.nsdl.telemedicine.videoConference.dto.VideoConfRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.VideoConfResponse;

public interface VideoConfService {

	public VideoConfResponse startConf(VideoConfRequestDTO request);

	public InvitationResponse invite(InvitationRequest invitationRequest);

	
	
	
	
}
