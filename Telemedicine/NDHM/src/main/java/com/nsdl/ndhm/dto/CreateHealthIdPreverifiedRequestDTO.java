package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateHealthIdPreverifiedRequestDTO {
	public String email;
	public String firstName;
	public String healthId;
	public String lastName;
	public String middleName;
	public String password;
	public String profilePhoto;
	public String txnId;
}
