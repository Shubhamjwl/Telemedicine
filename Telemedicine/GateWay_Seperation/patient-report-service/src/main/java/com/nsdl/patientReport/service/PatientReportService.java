package com.nsdl.patientReport.service;

import java.io.IOException;
import java.util.List;

import com.nsdl.patientReport.dto.AppointmentDTO;
import com.nsdl.patientReport.dto.AppointmentRequestDTO;
import com.nsdl.patientReport.dto.MainRequestDTO;
import com.nsdl.patientReport.dto.MainResponseDTO;
import com.nsdl.patientReport.entity.ConsultPriscpDtl;

public interface PatientReportService {
	
	MainResponseDTO<List<AppointmentDTO>> getPatientAppointmentDetails(MainRequestDTO<AppointmentRequestDTO> payLoad)  ;

	MainResponseDTO<List<AppointmentDTO>> getDoctorAppointmentDetails(MainRequestDTO<AppointmentRequestDTO> payLoad) throws Exception  ;

	ConsultPriscpDtl downloadPrescription(MainRequestDTO<ConsultPriscpDtl> doctorDocDtlsRequest) throws IOException;
}
