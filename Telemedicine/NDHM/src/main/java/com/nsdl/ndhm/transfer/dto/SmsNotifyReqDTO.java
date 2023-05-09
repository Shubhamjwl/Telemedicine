package com.nsdl.ndhm.transfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsNotifyReqDTO {

	private String requestId;
	private String timestamp;
	private SmsNotificationDTO notification;
}
