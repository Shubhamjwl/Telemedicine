package com.nsdl.otpManager.service;

import com.nsdl.otpManager.dto.InvitationLinkRequest;
import com.nsdl.otpManager.dto.InvitationLinkResponse;
import com.nsdl.otpManager.dto.MainRequestDTO;
import com.nsdl.otpManager.dto.MainResponseDTO;
import com.nsdl.otpManager.dto.OTPRequest;
import com.nsdl.otpManager.dto.OTPResponse;
import com.nsdl.otpManager.dto.VerifyOTPRequest;

public interface OtpService {
	
	
	
	MainResponseDTO<OTPResponse> generateOTP(MainRequestDTO<OTPRequest> payLoad) throws Exception ;
		
	MainResponseDTO<OTPResponse> verifyOTP(MainRequestDTO<VerifyOTPRequest> payLoad) throws Exception ;

	MainResponseDTO<InvitationLinkResponse> sendInvitationLink(MainRequestDTO<InvitationLinkRequest> invitationLinkRequest);

	//MainResponseDTO<OTPResponse> sendEmailForUserServices(MainRequestDTO<UsrMgmtRequest> usrMgmtRequest);

	
}
