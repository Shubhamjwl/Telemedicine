package com.nsdl.telemedicine.patient.dto;

import lombok.Data;

@Data
public class HealthIdDetailsToTelemedicineDTO {

	private String healthId;
	private String healthNumber;
	private String mobileNo;
	private String aadhaarNo;
	private String txnId;
	private String errorCode;
	private String errorMsg;
}
