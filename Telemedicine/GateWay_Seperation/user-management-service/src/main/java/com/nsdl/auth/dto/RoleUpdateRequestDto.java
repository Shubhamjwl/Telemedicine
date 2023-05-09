package com.nsdl.auth.dto;



import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author Sudip Banerjee
 * @version 1.0
 * 
 * Defines an object to hold the Role update request details.
 *
 */

@Data
@ApiModel(value = "RoleUpdateRequestDto", description = "Role update request representation")
public class RoleUpdateRequestDto {
	
//	@ApiModelProperty(value ="ID", dataType = "java.lang.Integer")	
//	private int roleId;
	@ApiModelProperty(value ="roleName", dataType = "java.lang.String")	
	@NotEmpty(message = "Please provide Existing RoleName")
	private String roleName;	
	@NotEmpty(message = "Please provide New RoleName")
	private String newRoleName;
}
