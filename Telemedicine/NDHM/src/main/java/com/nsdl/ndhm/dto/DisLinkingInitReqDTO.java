package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisLinkingInitReqDTO {

	private String requestId;
	private String timestamp;
	private String transactionId;
	private DisLinkingPatientDTO patient;
}
