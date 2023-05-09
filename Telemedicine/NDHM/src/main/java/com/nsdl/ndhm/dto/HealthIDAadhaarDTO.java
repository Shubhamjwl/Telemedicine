package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthIDAadhaarDTO {
	public String email;
	public String firstName;
	public String middleName;
	public String lastName;
	public String mobile;
	public String otp;
	public String password;
	public String profilePhoto;
	public String restrictions;
	public String txnId;
	public String username;
}
