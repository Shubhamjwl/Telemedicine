package com.nsdl.telemedicine.consultancy.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DrAppointmentDTO {

	@NotBlank(message = "drRegID cannot be empty or null")
	private String drRegID;
	
	private Map<String, Map<String, List<?>>> apptDtls;
	
}
