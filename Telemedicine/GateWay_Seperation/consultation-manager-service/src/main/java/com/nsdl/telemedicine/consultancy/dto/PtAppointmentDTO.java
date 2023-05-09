package com.nsdl.telemedicine.consultancy.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PtAppointmentDTO {

	@NotBlank(message = "ptRegID cannot be empty or null")
	private String ptRegID;
	
	private List<AppointmentDTO> apptDtls;
	
}
