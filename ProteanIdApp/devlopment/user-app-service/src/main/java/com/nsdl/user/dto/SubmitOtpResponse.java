package com.nsdl.user.dto;

import lombok.Data;

@Data
public class SubmitOtpResponse {

	private String qrCode;
	private byte[] photo;
	private String gender;
	private String userName;
	private String dob;
	private String address;

}
