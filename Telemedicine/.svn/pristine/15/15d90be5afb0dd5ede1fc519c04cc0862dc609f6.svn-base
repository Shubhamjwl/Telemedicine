package com.nsdl.telemedicine.videoConference.service;

import javax.validation.Valid;

import com.nsdl.telemedicine.videoConference.dto.MainRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.MainResponseDTO;
import com.nsdl.telemedicine.videoConference.dto.VCAutRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.VCAuthResponseDTO;
import com.nsdl.telemedicine.videoConference.dto.VcTokenResponse;

public interface VcAuthService {
	
	public MainResponseDTO<VCAuthResponseDTO>  generateAuthToken(@Valid MainRequestDTO<VCAutRequestDTO> vcAuthRequest);
	public MainResponseDTO<VcTokenResponse>  verifyAuthToken(String authToken);
	public boolean validateToken(VCAutRequestDTO vCAutRequestDTO);
	
	//public MainResponseDTO<VcTokenResponse>  verifyAuthToken(MainRequestDTO<VcAuthValidateDTO> vcAuthRequest, String authToken);
	
	
}
