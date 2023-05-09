package com.nsdl.appointment.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SaveAppDetailsRequest {
	
	@NotNull(message = "doctorId must not be null")
	@Valid
	private String drRegID;
	
	private String ptRegID;
	
	@NotNull(message = "transactionId must not be null")
	@Valid
	private String transactionID;
	
	@NotNull(message = "bookForSomeoneElse flag must not be null")
	@Valid
	private String bookForSomeoneElse;
	
	@NotNull(message = "appointmentDetails must not be null")
	@Valid
	private AppointmentDetails appointmentDetails;
	
	@NotNull(message = "patientName must not be null")
	@Valid
	private String patientName;
	
	//@NotNull(message = "patientName must not be null")
	//@Valid
	private String patientEmail;
	
	@NotNull(message = "patientMNO must not be null")
	@Valid
	private String patientMNO;

}
