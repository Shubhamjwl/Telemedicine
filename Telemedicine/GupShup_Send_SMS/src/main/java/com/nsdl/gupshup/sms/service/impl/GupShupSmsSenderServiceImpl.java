package com.nsdl.gupshup.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nsdl.gupshup.sms.aop.LoggingClientInfo;
import com.nsdl.gupshup.sms.dto.SendSmsDTO;
import com.nsdl.gupshup.sms.dto.StatusDTO;
import com.nsdl.gupshup.sms.service.GupShupSmsSenderService;
import com.nsdl.gupshup.sms.utilities.SmsUtil;

@Service
@LoggingClientInfo
public class GupShupSmsSenderServiceImpl implements GupShupSmsSenderService {

	

	@Value("${gupshup.sendsms.smsurl}")
	private String smsURL;

	@Value("${gupshup.sendsms.method}")
	private String method;

	@Value("${gupshup.sendsms.msg-type}")
	private String msgType;

	@Value("${gupshup.sendsms.msg}")
	private String msg;

	@Value("${gupshup.sendsms.sendto}")
	private String sendTo;

	@Value("${gupshup.sendsms.password}")
	private String password;

	@Value("${gupshup.sendsms.userid}")
	private String userId;

	@Value("${gupshup.sendsms.auth-scheme}")
	private String authScheme;

	@Value("${gupshup.sendsms.version}")
	private String version;

	@Value("${gupshup.sendsms.format}")
	private String format;

	@Value("${gupshup.http.sendsms.status.success}")
	private String successStatus;

	@Value("${gupshup.http.sendsms.msg.success}")
	private String successStatusMsg;

	@Value("${gupshup.http.sendsms.status.fail}")
	private String failStatus;

	@Value("${gupshup.http.sendsms.msg.fail}")
	private String failStatusMsg;
	
	@Value("${spring.profiles.active}")
	private String env;
	
	@Value("${gupshup.sendsms.flag.vt}")
	private String flagForVTEnv;
	
	@Value("${gupshup.sendsms.flag.dev}")
	private String flagForDevEnv;
	
	@Autowired
	SmsUtil smsUtil;

	@Override
	public StatusDTO sendSmsGupShupAPI(SendSmsDTO sendSmsDTO) {
		int respCode=0;
		StatusDTO statusDTO=new StatusDTO();
		try {
			StringBuilder builderSendTo = new StringBuilder();

			for (String eachNumber : sendSmsDTO.getSendTo()) {
				builderSendTo.append(eachNumber + ",");
			}

			builderSendTo.replace(builderSendTo.lastIndexOf(","), builderSendTo.lastIndexOf(",") + 1, "&");
			respCode = smsUtil.SendSms(sendSmsDTO.getMessage(), builderSendTo.toString());
			if (respCode == 200) 
			{
				statusDTO.setMessage(successStatusMsg);
				statusDTO.setStatus(successStatus);		
			} 
			else
			{
				statusDTO.setMessage(failStatusMsg);
				statusDTO.setStatus(failStatus);
			}
		} catch (Exception e) {
			statusDTO.setMessage(failStatusMsg);
			statusDTO.setStatus(failStatus);
			e.printStackTrace();
		}
		return statusDTO;
			
		
	
	}

}
