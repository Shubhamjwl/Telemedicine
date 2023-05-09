package com.nsdl.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminSendNotificationResponse {

	private String userId;
	private String fullName;
	private String emailId;
	private Long mobileNo;
	
}
