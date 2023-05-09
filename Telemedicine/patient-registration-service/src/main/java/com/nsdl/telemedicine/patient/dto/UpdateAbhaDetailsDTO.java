package com.nsdl.telemedicine.patient.dto;

import lombok.Data;

@Data
public class UpdateAbhaDetailsDTO {
	private String healthId;
	private String healthNumber;
	private String mobileNo;
	private String aadhaarNo;
	private String address;
	private String firstName;
	private String lastName;
	private String middleName;
	private String gender;
	private String token;
	private String errorCode;
	private String errorMsg;
}
