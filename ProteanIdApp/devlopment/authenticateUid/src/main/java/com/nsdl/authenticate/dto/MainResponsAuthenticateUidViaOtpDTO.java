package com.nsdl.authenticate.dto;

import lombok.Data;

@Data
public class MainResponsAuthenticateUidViaOtpDTO {
	
	private String userName;
	private String dob;
	private String gender;
	private byte[] photo;

}
