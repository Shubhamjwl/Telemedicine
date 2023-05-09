package com.nsdl.otpManager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.otpManager.dto.InvitationLinkRequest;
import com.nsdl.otpManager.dto.InvitationLinkResponse;
import com.nsdl.otpManager.dto.MainRequestDTO;
import com.nsdl.otpManager.dto.MainResponseDTO;
import com.nsdl.otpManager.dto.OTPRequest;
import com.nsdl.otpManager.dto.OTPResponse;
import com.nsdl.otpManager.dto.RegistrationRequest;
import com.nsdl.otpManager.dto.RegistrationResponse;
import com.nsdl.otpManager.dto.TemplateDtls;
import com.nsdl.otpManager.dto.VerifyOTPRequest;
import com.nsdl.otpManager.logger.LoggingClientInfo;
import com.nsdl.otpManager.service.EmailService;
import com.nsdl.otpManager.service.NotificationService;
import com.nsdl.otpManager.service.OtpService;
import com.nsdl.otpManager.service.RegistrationService;
import com.nsdl.otpManager.service.SmsService;

@RestController
@CrossOrigin("*")
@LoggingClientInfo
public class OTPController {

	@Autowired
	public OtpService otpService;

	@Autowired
	public EmailService emailService;
	
	@Autowired
	public RegistrationService regService;

	@Autowired
	public SmsService smsService;
	
	@Autowired
	public NotificationService notificationService;

	@PostMapping(value = "/generateOtp", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<OTPResponse>> generateOTP(@RequestBody @Valid MainRequestDTO<OTPRequest> otpRequest) throws Exception 
	{
			return ResponseEntity.status(HttpStatus.OK).body(otpService.generateOTP(otpRequest));

	}
	
	@PostMapping(value = "/verifyOTP", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<OTPResponse>> verifyOTP(@RequestBody @Valid MainRequestDTO<VerifyOTPRequest> verifyOTPRequest) throws Exception  
	{
			
		return ResponseEntity.status(HttpStatus.OK).body(otpService.verifyOTP(verifyOTPRequest));
		
	}
	
	@PostMapping(value = "/sendInvitationLink", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<InvitationLinkResponse>> sendInvitationLink(@RequestBody @Valid MainRequestDTO<InvitationLinkRequest> invitationLinkRequest) throws Exception 
	{
			return ResponseEntity.status(HttpStatus.OK).body(otpService.sendInvitationLink(invitationLinkRequest));

	}
	
	/*@PostMapping(value = "/sendEmailForUserServices", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<OTPResponse>> sendEmailForUserServices(@RequestBody @Valid MainRequestDTO<UsrMgmtRequest> usrMgmtRequest) throws Exception 
	{
			return ResponseEntity.status(HttpStatus.OK).body(otpService.sendEmailForUserServices(usrMgmtRequest));

	}*/
	
	@PostMapping(value = "/checkUniqueValue", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<RegistrationResponse>> checkUniqueValue(@RequestBody @Valid MainRequestDTO<RegistrationRequest> regRequest) throws Exception 
	{
			return ResponseEntity.status(HttpStatus.OK).body(regService.checkUniqueValue(regRequest));
	}
	
	@PostMapping(value = "/sendNotification", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<OTPResponse>> sendNotification(@RequestBody @Valid MainRequestDTO<TemplateDtls> request)  
	{
			return ResponseEntity.status(HttpStatus.OK).body(notificationService.sendNotification(request));
	}
}