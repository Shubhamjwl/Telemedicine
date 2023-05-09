package com.nsdl.telemedicine.patient.dto;



import javax.validation.Valid;
import javax.validation.constraints.NotNull;



import lombok.Data;
@Data
public class CareContextDtls {

	
	@NotNull(message = "careContextId must not be null")
	@Valid
	private String careContextId;
	
	
}
