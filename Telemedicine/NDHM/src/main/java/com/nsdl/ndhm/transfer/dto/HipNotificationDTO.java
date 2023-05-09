package com.nsdl.ndhm.transfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HipNotificationDTO {
	private String status;	
	private String consentId;
	private ConsentDetailDTO consentDetail;
	private String signature;
	private boolean grantAcknowledgement;
}
