package com.nsdl.ndhm.transfer.dto;

import com.nsdl.ndhm.dto.RespDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnRequestRespDTO {
	private String requestId;
	private String timestamp;
	private HiRequestRespDTo hiRequest;
	private ErrorDTO error;
	private RespDTO resp;
}
