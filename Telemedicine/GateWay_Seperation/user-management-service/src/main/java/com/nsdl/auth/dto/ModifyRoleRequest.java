package com.nsdl.auth.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ModifyRoleRequest {
	@NotBlank(message = "User id cannot be Empty")
	private String userId;
	@NotBlank(message = "Role cannot be Empty")
	private String role;
}


