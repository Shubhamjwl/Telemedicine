package com.nsdl.ndhm.service;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

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
import com.nsdl.ndhm.dto.UserAuthRequestDTO;
import com.nsdl.ndhm.dto.UserAuthRespDTO;

public interface HealthIdCreationByMobileService {

	public MainResponseDTO<GenerateOtpResp> generateOTP(Map<String, String> headers, GenerateOtpDTO generateOtpDTO);

	public MainResponseDTO<ResendOtpResp> resendOTP(Map<String, String> headers, ResendOtpDTO generateOtpDTO);

	public MainResponseDTO<ConfirmOtpResp> confirmOTP(Map<String, String> headers, ConfirmOtpDTO confirmOtpDTO);

	public MainResponseDTO<HealthIDResp> saveHealthIdByMobile(Map<String, String> headers,
			MainRequestDTO<HealthIDDTO> healthIDDTO);

	public MainResponseDTO<UserAuthRespDTO> userAuthWithPassword(Map<String, String> headers,
			UserAuthRequestDTO userAuthRequestDTO);

	public ResponseEntity<byte[]> getPdfCard(Map<String, String> headers);

	public ResponseEntity<byte[]> getPngCard(Map<String, String> headers);

	public MainResponseDTO<HealthIDResp> saveHealthIdDetailsDemo(Map<String, String> headers,
			@Valid MainRequestDTO<HealthIDDTO> healthIDDTO);

}
