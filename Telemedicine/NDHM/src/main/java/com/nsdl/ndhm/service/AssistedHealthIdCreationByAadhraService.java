package com.nsdl.ndhm.service;

import java.util.Map;

import javax.validation.Valid;

import com.nsdl.ndhm.dto.AadharOtpResendRespDTO;
import com.nsdl.ndhm.dto.ConfirmOtpAadhaarDTO;
import com.nsdl.ndhm.dto.ConfirmOtpDTO;
import com.nsdl.ndhm.dto.ConfirmOtpRespAadhaar;
import com.nsdl.ndhm.dto.CreateHealthIdPreverifiedRequestDTO;
import com.nsdl.ndhm.dto.CreateHealthIdPreverifiedRespDTO;
import com.nsdl.ndhm.dto.GenerateMobileOTP;
import com.nsdl.ndhm.dto.GenerateOtpAadhaarDTO;
import com.nsdl.ndhm.dto.GenerateOtpResp;
import com.nsdl.ndhm.dto.HealthIDAadhaarDTO;
import com.nsdl.ndhm.dto.HealthIDResp;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.ResendOtpDTO;

public interface AssistedHealthIdCreationByAadhraService {

	MainResponseDTO<GenerateOtpResp> generateOTPForAadharAssisted(Map<String, String> headers,
			GenerateOtpAadhaarDTO generateOtpAadhaarDTO);

	MainResponseDTO<AadharOtpResendRespDTO> resendOTPForAadharAssited(Map<String, String> headers,
			ResendOtpDTO resendOTPDTO);

	MainResponseDTO<ConfirmOtpRespAadhaar> verifyOTPForAadhaarAssisted(Map<String, String> headers,
			ConfirmOtpAadhaarDTO confirmOtpAadhaarDTO);

	MainResponseDTO<HealthIDResp> helathIDcreationForAadhaarAssisted(Map<String, String> headers,
			@Valid HealthIDAadhaarDTO healthIDDTO);

	MainResponseDTO<GenerateOtpResp> generateMobileOTPAssisted(Map<String, String> headers,
			GenerateMobileOTP generateMobileOTP);

	MainResponseDTO<CreateHealthIdPreverifiedRespDTO> helathIDcreationForAadhaarAssisted(Map<String, String> headers,
			@Valid CreateHealthIdPreverifiedRequestDTO createHealthIdPreverifiedRequestDTO);

	MainResponseDTO<ConfirmOtpRespAadhaar> verifyMobileOTPAssisted(Map<String, String> headers, ConfirmOtpDTO confirmOtpDTO);

}
