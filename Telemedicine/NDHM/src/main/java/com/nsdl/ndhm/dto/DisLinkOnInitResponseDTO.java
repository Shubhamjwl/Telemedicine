package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisLinkOnInitResponseDTO {

	private String requestId;
	private String timestamp;
	private String transactionId;
	private DisLinkLinkDTO link;
	private ErrorDTO error;
	private RespDTO resp;
}
