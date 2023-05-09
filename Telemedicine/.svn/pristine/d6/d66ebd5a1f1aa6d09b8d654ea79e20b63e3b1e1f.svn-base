package com.nsdl.patientReport.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDTO {
	
	private String appointmentId;
	private String adApptSlotFk;
	private Date adApptDateFk;
	private String ad_appt_status;
	private String prdPtName;
	private String doctorName;
	
	public AppointmentDTO(String adApptId, String adApptSlotFk, Date adApptDateFk, String ad_appt_status,
			String prdPtName,String doctorName) {
		super();
		this.appointmentId = adApptId;
		this.adApptSlotFk = adApptSlotFk;
		this.adApptDateFk = adApptDateFk;
		this.ad_appt_status = ad_appt_status;
		this.prdPtName = prdPtName;
		this.doctorName=doctorName;
	}
	@Override
	public String toString() {
		return "AppointmentDTO [adApptId=" + appointmentId + ", adApptSlotFk=" + adApptSlotFk + ", adApptDateFk="
				+ adApptDateFk + ", ad_appt_status=" + ad_appt_status + ", prdPtName=" + prdPtName +",doctorName= "+doctorName +"]";
	}
	

}
