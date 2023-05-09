package com.nsdl.telemedicine.patient.dto;



import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReportRequestDto {
	
	@NotNull(message="abhaId can not be null")
	@Valid
	private String abhaId;
	
	private List<CareContextDtls> careContextIds;

}
