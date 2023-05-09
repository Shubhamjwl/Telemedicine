package com.nsdl.telemedicine.consultancy.dto;

import org.apache.commons.lang.builder.CompareToBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO implements Comparable<AppointmentDTO> {

	private String appointmentID;
	
	private String appointmentDate;
	
	private String appointmentTime;
	
	private String doctorName;
	
	private String doctorUserId;
	
	private String doctorSpeciality;
	
	private String patientName;
	
	private String patientRegId; 
	
	private String status;
	
	private String profilePhoto;
	
	private String slot;
	
	private String path;
	
	private String chiefComplaints;
	
	private Integer doctorConsulFee;
	
	private String slotType;
	
	 @Override
	    public int compareTo(AppointmentDTO other) {
	        return new CompareToBuilder()
	            .append(getAppointmentDate(), other.getAppointmentDate())
	            .append(getAppointmentTime(), other.getAppointmentTime())
	            .toComparison();
	    }
}
