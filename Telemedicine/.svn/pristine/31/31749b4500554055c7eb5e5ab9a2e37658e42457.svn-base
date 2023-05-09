package com.nsdl.ndhm.service;

import java.util.Map;

import com.nsdl.ndhm.dto.ConfirmOtpDTO;
import com.nsdl.ndhm.dto.ConfirmOtpResp;
import com.nsdl.ndhm.dto.GenerateOtpDTO;
import com.nsdl.ndhm.dto.GenerateOtpResp;
import com.nsdl.ndhm.dto.HealthIDDTO;
import com.nsdl.ndhm.dto.HealthIDResp;
import com.nsdl.ndhm.dto.MainRequestDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.ResendOtpDTO;
import com.nsdl.ndhm.dto.ResendOtpResp;

public interface AssistedHealthIdCreationService {

	public MainResponseDTO<GenerateOtpResp> generateOTPForAssisted(Map<String, String> headers,
			GenerateOtpDTO generateOtpDTO);

	public MainResponseDTO<ResendOtpResp> resendOTPForAssisted(Map<String, String> headers,
			ResendOtpDTO generateOtpDTO);

	public MainResponseDTO<ConfirmOtpResp> confirmOTPForAssisted(Map<String, String> headers,
			ConfirmOtpDTO confirmOtpDTO);

	public MainResponseDTO<HealthIDResp> saveHealthIdByMobileForAssisted(Map<String, String> headers,
			MainRequestDTO<HealthIDDTO> healthIDDTO);
}
