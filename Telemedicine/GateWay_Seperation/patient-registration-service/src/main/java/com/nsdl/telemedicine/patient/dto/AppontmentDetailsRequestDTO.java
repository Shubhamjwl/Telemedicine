package com.nsdl.telemedicine.patient.dto;

import lombok.Data;
/**
 * @author Pegasus_girishk
 *
 */
@Data
public class AppontmentDetailsRequestDTO {

	private String apptId;
	
	private String doctorName;
	
	private String fromDate;
	
	private String toDate;
}
