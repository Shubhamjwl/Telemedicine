package com.nsdl.ndhm.transfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataRequestDTO {
	private String requestId;
	private String timestamp;
	private String transactionId;
	private HiRequestDTO hiRequest;
}
