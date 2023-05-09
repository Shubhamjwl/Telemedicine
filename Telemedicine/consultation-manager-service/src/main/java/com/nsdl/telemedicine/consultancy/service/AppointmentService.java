package com.nsdl.telemedicine.consultancy.service;

import com.nsdl.telemedicine.consultancy.dto.DrAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.MainRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.PtAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.TokenDTO;

public interface AppointmentService {

	public PtAppointmentDTO getAppointmentListByPatientID();
	
	public PtAppointmentDTO getScheduledAndRescheduledAppointmentByPatientId();


	public DrAppointmentDTO getAppointmentListByDrID(String apptDate);

	public DrAppointmentDTO getAppointmentListForDrID(MainRequestDTO<TokenDTO> request);
	
	public DrAppointmentDTO getScheduledAndRescheduledAppointments(String apptDate);


}
