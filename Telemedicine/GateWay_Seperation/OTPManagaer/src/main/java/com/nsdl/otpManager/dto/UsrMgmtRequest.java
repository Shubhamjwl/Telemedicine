
package com.nsdl.otpManager.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsrMgmtRequest {
	@NotBlank
	private String userId;
//	@NotBlank
	private String emailId;
	@NotBlank
	private String templateType;
	@NotBlank
	private String password;
	private String mobile;
	private String sendType;
	private String rejectReason;
	private String drName;
	private String drEmailId;
	private String drMobileNo;
	private String ptName;
	private String ptMobileNo;
	private String appTime;
	private String addDate;
	
	

}
