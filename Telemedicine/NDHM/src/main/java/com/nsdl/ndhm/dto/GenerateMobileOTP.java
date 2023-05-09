package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenerateMobileOTP {

	private String mobile;
	private String txnId;
}
