package com.nsdl.payment.dto;

import lombok.Data;

@Data
public class NotifyRequestDTO {
	
	private String userId;
	private String emailId;
	private String templateType;
	private Long mobileNo;
	private String sendType;
	private int amount;
}
