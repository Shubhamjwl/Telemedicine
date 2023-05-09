package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmOtpAadhaarDTO {
	private String otp;
	private String txnId;
	private String restrictions;
}
