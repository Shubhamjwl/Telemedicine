package com.nsdl.telemedicine.doctor.dto;

import lombok.Data;
/**
 * @author Pegasus_girishk
 *
 */
@Data
public class AppontmentDetailsRequestDTO {

	private String apptId;
	
	private String patientName;
	
	private String fromDate;
	
	private String toDate;
}
