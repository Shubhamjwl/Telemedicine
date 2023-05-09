package com.nsdl.auth.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class GetDoctorDetailsRequestDTO {
	@NotEmpty(message = "Please provide valid registered Doctor User Id")
	private String doctorId;
}
