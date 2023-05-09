package com.nsdl.ndhm.transfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HipNotifyReqDTO {
	private String requestId;
	private String timestamp;
	private HipNotificationDTO notification;
}
