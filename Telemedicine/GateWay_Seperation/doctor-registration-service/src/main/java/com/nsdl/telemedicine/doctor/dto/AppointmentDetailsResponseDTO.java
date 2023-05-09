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
public class AppointmentDetailsResponseDTO {
	
	private String appointmentId;
	
	private String appointmentDate;
	
	private String doctorId;
	
	private String patientId;
	
	private String patientName;
}
