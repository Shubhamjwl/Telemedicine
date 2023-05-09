package com.nsdl.ndhm.service.impl;

import com.nsdl.ndhm.dto.SendGupShupSmsDTO;
import com.nsdl.ndhm.dto.StatusDTO;
import com.nsdl.ndhm.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsServiceImpl implements SmsService {

    @Value("${smsRequestUrl}")
    private String smsRequestUrl;

    @Value("${PasswordExpireTimeInMin}")
    private String passwordExpireTime;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public StatusDTO sendGupShupSms(String msg, String mobile) {
        SendGupShupSmsDTO sendGupShupSmsDTO = new SendGupShupSmsDTO();
        String[] listOfMobileNumbers = {mobile};
        sendGupShupSmsDTO.setMessage(msg);
        sendGupShupSmsDTO.setSendTo(listOfMobileNumbers);
        ResponseEntity<StatusDTO> response = restTemplate.postForEntity(smsRequestUrl, sendGupShupSmsDTO, StatusDTO.class);
        return response.getBody();
    }
}
