package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmOtpRespAadhaar {
	private String txnId;
	private String errorCode;
	private String error;
}
