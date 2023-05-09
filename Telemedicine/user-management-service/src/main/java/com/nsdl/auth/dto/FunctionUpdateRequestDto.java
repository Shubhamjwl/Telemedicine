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
 * Defines an object to hold the function update request details.
 *
 */
@Data
@ApiModel(value = "FunctionUpdateRequestDto", description = "Function update request representation")
public class FunctionUpdateRequestDto {

	
	@ApiModelProperty(value ="functionName", dataType = "java.lang.String")	
	@NotEmpty(message = "Please provide functionName to be updated")
	private  String functionName;	
	
	@ApiModelProperty(value ="newFunctionName", dataType = "java.lang.String")	
	@NotEmpty(message = "Please provide New functionName")
	private String newFunctionName;
}
