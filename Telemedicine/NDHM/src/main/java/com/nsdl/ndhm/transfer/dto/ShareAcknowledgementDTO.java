package com.nsdl.ndhm.transfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShareAcknowledgementDTO {
	private String status;
	private String healthId;
}
