package com.nsdl.auth.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CategoryStatusRequest {

	
	@NotBlank(message = "Please select drUserId")
	private String drUserId;
	
	@NotBlank(message = "Please select drEmailId")
	private String drEmailId;
	
	public List<CategoryDto> category;
	
}
