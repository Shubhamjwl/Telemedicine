package com.nsdl.auth.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
/**
 * @author sudip
 *
 */
@Data
public class FunctionRequestDTO {

	
	@NotEmpty(message = "Please provide FunctionName")
	public String functionName;
}
