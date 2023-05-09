package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmOtpDTO {
	private String otp;
	private String txnId;
	
}
