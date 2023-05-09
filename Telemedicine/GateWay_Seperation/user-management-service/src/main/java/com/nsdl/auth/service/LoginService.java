package com.nsdl.auth.service;

import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.CreateUserRequest;
import com.nsdl.auth.dto.ForgotPasswordRequest;
import com.nsdl.auth.dto.LoginRequest;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.ResetPasswordRequest;
import com.nsdl.auth.dto.UpdateUserDetailsRequest;
import com.nsdl.auth.dto.UserActiveDeactiveRequestDTO;
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
}
