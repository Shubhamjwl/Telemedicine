package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountChangePassByHealthIdReqDTO {
	private String newPassword;
	private String oldPassword;

}
