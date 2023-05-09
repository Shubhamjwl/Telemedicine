package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthConfirmReqDTO {
	private String requestId;
	private String timestamp;
	private String transactionId;
	private CredentialDTO credential;
}
