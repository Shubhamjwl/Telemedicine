package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AadharOtpResendRespDTO {
	private String txnId;
}
