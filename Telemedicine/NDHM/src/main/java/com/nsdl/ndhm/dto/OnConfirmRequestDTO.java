package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnConfirmRequestDTO {

	private String requestId;

	private String timestamp;

	private OnConfirmAuthDTO auth;

	private ErrorDTO Error;
 	
	private RespDTO resp;

}