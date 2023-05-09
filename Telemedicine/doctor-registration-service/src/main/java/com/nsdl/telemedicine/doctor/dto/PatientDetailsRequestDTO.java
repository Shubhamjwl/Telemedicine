/**
 * 
 */
package com.nsdl.telemedicine.doctor.dto;

import lombok.Data;

/**
 * @author Pegasus_girishk
 *
 */
@Data
public class PatientDetailsRequestDTO {
	
	private String appointmentId;
	
	private String drRegId;
	
	private String ptRegId;
}
