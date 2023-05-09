package com.nsdl.auth.service;

import java.time.LocalDateTime;
import java.util.List;

import com.nsdl.auth.dto.AboutUsPdfResponse;
import com.nsdl.auth.dto.ChangePasswordRequest;
import com.nsdl.auth.dto.AdminConfigRequest;
import com.nsdl.auth.dto.AdminConfigResponse;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.CreateUserRequest;
import com.nsdl.auth.dto.ForgotPasswordRequest;
import com.nsdl.auth.dto.LoginRequest;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.OTPResponse;
import com.nsdl.auth.dto.ResetPasswordRequest;
import com.nsdl.auth.dto.UpdateUserDetailsRequest;
import com.nsdl.auth.dto.UserActiveDeactiveRequestDTO;
import com.nsdl.auth.dto.UserDetailsRequest;
import com.nsdl.auth.dto.UserResponse;

public interface LoginService {
	MainResponseDTO<UserResponse> createUserService(MainRequestDTO<CreateUserRequest> request);
	MainResponseDTO<UserResponse> userLoginService(MainRequestDTO<LoginRequest> request);
	MainResponseDTO<CommonResponseDTO> userLogoutService(String userId);
	MainResponseDTO<CommonResponseDTO> userPasswordResetService(MainRequestDTO<ResetPasswordRequest> request);
	MainResponseDTO<CommonResponseDTO> userForgotPasswordService(MainRequestDTO<ForgotPasswordRequest> request);
	MainResponseDTO<CommonResponseDTO> userActiveDeactiveService(MainRequestDTO<UserActiveDeactiveRequestDTO> request);
	MainResponseDTO<CommonResponseDTO> updateUserDetailsRequest(MainRequestDTO<UpdateUserDetailsRequest> request);
	boolean verifyCaptcha(String captchaValue, String sessionId);
	MainResponseDTO<OTPResponse> getUserDetailsAndSendOTP(UserDetailsRequest userDetailsRequest);
	public void saveSessionHistory(String userId, String sessionId, LocalDateTime modifiedTime);
	public void saveLogOutTime(Long id,String userId,String sessionId);
	MainResponseDTO<AboutUsPdfResponse> downloadAboutUsPdf(String pdfName);
	MainResponseDTO<CommonResponseDTO> userChangePasswordService(MainRequestDTO<ChangePasswordRequest> request);
	
}


