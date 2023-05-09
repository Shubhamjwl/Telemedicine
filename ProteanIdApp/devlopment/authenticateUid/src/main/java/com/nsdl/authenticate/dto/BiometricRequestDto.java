package com.nsdl.authenticate.dto;


import lombok.Data;

@Data
public class BiometricRequestDto {

	private String id;
	private String person;
	private String source;
	private String process;
	private Boolean bypassCache;
}
