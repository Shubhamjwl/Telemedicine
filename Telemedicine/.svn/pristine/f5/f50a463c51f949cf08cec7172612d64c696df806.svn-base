package com.nsdl.otpManager.service;

import java.util.List;

import com.nsdl.otpManager.dto.MainRequestDTO;
import com.nsdl.otpManager.dto.MainResponseDTO;
import com.nsdl.otpManager.dto.OTPResponse;
import com.nsdl.otpManager.dto.TemplateDtls;

public interface NotificationService {
	
	MainResponseDTO<OTPResponse> sendNotification(MainRequestDTO<TemplateDtls> template);
	public List<String> getSMSAndEmailDetails(TemplateDtls templateModel, String templateName);
	public void prepareEmailRequestAndSendEmail(TemplateDtls request, List<String> notification_dtls,String role);
	public void prepareSMSRequestAndSendMsg(TemplateDtls request, List<String> notification_dtls, String role) ;

}
