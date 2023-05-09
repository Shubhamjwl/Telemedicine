package com.nsdl.telemedicine.patient.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CareContextResponse {

	@NotNull(message="careContextId can not be null")
	@Valid
	private String careContextId;	
	
	private LocalDateTime careContextCreationTimestamp;
	
	private DoctorDetailsDto doctorDetails;
	
	private List<ReportResponseDtls> reportResponseDtls;
	
}
