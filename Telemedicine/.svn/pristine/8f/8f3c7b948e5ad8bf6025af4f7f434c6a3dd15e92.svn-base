package com.nsdl.otpManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nsdl.otpManager.dto.SendGupShupSmsDTO;
import com.nsdl.otpManager.dto.StatusDTO;



@Service
public class SmsService {
	
	
	 
    @Value("${smsRequestUrl}")
	private String smsRequestUrl;
       
    @Value("${PasswordExpireTimeInMin}")
	private String passwordExpireTime;
    
    @Autowired
    private RestTemplate restTemplate;
	
	
	public StatusDTO sendGupShupSms(String msg, String mobile) {
		SendGupShupSmsDTO sendGupShupSmsDTO = new SendGupShupSmsDTO();
		String[] listOfMobileNumbers = {mobile};
		sendGupShupSmsDTO.setMessage(msg);
		sendGupShupSmsDTO.setSendTo(listOfMobileNumbers);
		ResponseEntity<StatusDTO> response = restTemplate.postForEntity(smsRequestUrl, sendGupShupSmsDTO, StatusDTO.class);
		return response.getBody();
     
	}

}
