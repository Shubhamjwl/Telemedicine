package com.nsdl.otpManager.service;

import com.nsdl.otpManager.dto.MainRequestDTO;
import com.nsdl.otpManager.dto.MainResponseDTO;
import com.nsdl.otpManager.dto.RegistrationRequest;
import com.nsdl.otpManager.dto.RegistrationResponse;

public interface RegistrationService {

	MainResponseDTO<RegistrationResponse> checkUniqueValue(MainRequestDTO<RegistrationRequest> regRequest);
}
