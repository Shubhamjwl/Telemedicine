package com.nsdl.telemedicine.doctor.dto;

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
