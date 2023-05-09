package com.nsdl.telemedicine.patient.dto;

import lombok.Data;

@Data
public class CaptchaResponseDto {

	private String responseMsg;

	public CaptchaResponseDto() {}
	public CaptchaResponseDto(String responseMsg) {
		super();
		this.responseMsg = responseMsg;
	}
}
