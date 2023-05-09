package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResendOtpResp {
	private boolean resendStatus;
	private String txnId;
}
