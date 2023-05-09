package com.nsdl.authenticate.dto;

import lombok.Data;

@Data
public class ClientSecretMainRequest {

	private String id;
    private String version;
    private String requesttime;
    private ClientSecretRequest request;
    
}
