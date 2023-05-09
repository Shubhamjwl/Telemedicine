package com.nsdl.auth.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * @author sudip
 *
 */
@Data
public class RoleRequestDTO {

	@NotEmpty(message = "Please provide RoleName")
	public String roleName;
	
}
