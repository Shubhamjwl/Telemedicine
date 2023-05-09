package com.nsdl.telemedicine.scribe.service;
import com.nsdl.telemedicine.scribe.dto.MainRequestDTO;
import com.nsdl.telemedicine.scribe.dto.MainResponseDTO;
import com.nsdl.telemedicine.scribe.dto.ScribeRegDTO;


public interface ScribeRegistrationService {
	
	public MainResponseDTO<String> saveScribeDetails(MainRequestDTO<ScribeRegDTO> scribeRegDTO);

	public MainResponseDTO<String> updateScribeProfile(MainRequestDTO<ScribeRegDTO> profileUpdateRequest);

	public MainResponseDTO<?> getScribeDetails();

}
