package com.nsdl.user.dto;

import lombok.Data;

@Data
public class SubmitUidRequest {

	private String androidId;
	private String uid;
	private Boolean whitelistOverrideFlag;

}
