package com.nsdl.ndhm.service;

import com.nsdl.ndhm.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface HealthIdProfileService {

	MainResponseDTO<AadharOtpResendRespDTO> generateAadhaarOTP(Map<String, String> headers,
			GenerateAadhaarOtpDTO generateAadhaarOtpDTO);

	MainResponseDTO<Boolean> verifyAadhaarOTP(Map<String, String> headers, ConfirmOtpDTO confirmOtpDTO);

	MainResponseDTO<AccountBenefitsRespDTO> accountBenefits(Map<String, String> headers);

	MainResponseDTO<String> changePasswordByAadhaar(Map<String, String> headers,
			AccountChangePasswordDTO accountChangePasswordDTO);

	MainResponseDTO<String> changePasswordByMobile(Map<String, String> headers,
			AccountChangePasswordDTO accountChangePasswordDTO);

	MainResponseDTO<AadharOtpResendRespDTO> changePasswdGenerateMobileOTP(Map<String, String> headers);

	MainResponseDTO<AadharOtpResendRespDTO> changePasswdGenerateAadhaarOTP(Map<String, String> headers);

	MainResponseDTO<String> changePasswordByHealthId(Map<String, String> headers,
			AccountChangePassByHealthIdReqDTO accountChangePassByHealthIdReqDTO);

	ResponseEntity<String> logoutProfile(Map<String, String> headers);

	MainResponseDTO<AccountProfileRespDTO> getProfile(Map<String, String> headers);

	MainResponseDTO<AccountProfileRespDTO> updateProfile(Map<String, String> headers,
			AccountProfileUpdateDTO accountProfileUpdateDTO);

	Map<String, String> deleteProfile(Map<String, String> headers);

	ResponseEntity<byte[]> getQrCode(Map<String, String> headers);

	MainResponseDTO<Boolean> checkValidateAuthToken(Map<String, String> headers, AuthTokenReqDTO authTokenReqDTO);
}
