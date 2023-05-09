package com.nsdl.authenticate.dto;

import lombok.Data;

@Data
public class ClientSecretRequest {
	
	private String clientId;
	private String secretKey;
	private String appId;

}
