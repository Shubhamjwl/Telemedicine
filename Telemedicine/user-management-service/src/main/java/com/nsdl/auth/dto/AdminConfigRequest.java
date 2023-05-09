package com.nsdl.auth.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class AdminConfigRequest {
	
	
	private String city;
	
	private String specialization;
	
	private String associationName;
	
	private String criteriaType;

	private String fromDate;
	
	private String toDate;
	
	private List<String> categoryType;
}
