package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request {
	private String healthId;
	private String healthNumber;
	private String mobileNo;
	private String aadhaarNo;
	private String txnId;
	private String errorCode;
	private String errorMsg;
	private String healthIdVerificationStatus;
	private String ndhmToken;
}
