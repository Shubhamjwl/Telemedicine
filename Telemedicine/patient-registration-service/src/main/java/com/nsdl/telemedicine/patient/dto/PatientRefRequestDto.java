package com.nsdl.telemedicine.patient.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRefRequestDto {

	
	@NotBlank(message = "Please enter valid health Id")
	private String healthId;
}
