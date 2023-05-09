package com.nsdl.ndhm.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchHealthIdToLoginRespDTO {
	private String healthId;
	private String healthIdNumber;
	private List<String> authMethods = new ArrayList<String>();
	private Object tags;
	private List<String> blockedAuthMethods = new ArrayList<String>();
	private String status;
	private String verificationStatus;
}
