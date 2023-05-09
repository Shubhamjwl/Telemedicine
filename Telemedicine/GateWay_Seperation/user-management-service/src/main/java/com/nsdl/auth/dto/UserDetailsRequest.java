package com.nsdl.auth.dto;


import lombok.Data;


@Data
public class UserDetailsRequest 	{
	private String userId;
	private String otpFor;
}
