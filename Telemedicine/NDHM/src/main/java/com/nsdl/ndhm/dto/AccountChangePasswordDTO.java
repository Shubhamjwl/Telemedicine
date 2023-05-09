package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountChangePasswordDTO {
	private String newPassword;
	private String otp;
	private String txnId;
}
