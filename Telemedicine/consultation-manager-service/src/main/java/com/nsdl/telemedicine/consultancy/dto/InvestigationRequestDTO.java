package com.nsdl.telemedicine.consultancy.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class InvestigationRequestDTO {
	
	@NotBlank(message = "appointmentId cannot be null or blank")
	private String appointmentId;
	
	@NotBlank(message = "tableId cannot be null or blank")
	private String tableId;
	
	private List<InvestigationDetailsDTO> investigationDetailsDTOList;
}
