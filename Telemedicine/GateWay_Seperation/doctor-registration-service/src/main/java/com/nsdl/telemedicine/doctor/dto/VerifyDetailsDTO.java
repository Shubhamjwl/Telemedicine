package com.nsdl.telemedicine.doctor.dto;

public class VerifyDetailsDTO {
	
	private String docName;
	
	private String otp;

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
}
