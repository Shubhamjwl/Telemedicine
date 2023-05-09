package com.nsdl.telemedicine.patient.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.patient.dto.AppointmentDetailsResponseDTO;
import com.nsdl.telemedicine.patient.dto.AppontmentDetailsRequestDTO;
import com.nsdl.telemedicine.patient.dto.PatientRegistrationDto;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
@Service
public interface PatientRegistrationService {

	ResponseWrapper<PatientResponseDto> savePatientDetails(RequestWrapper<PatientRegistrationDto> patientRegistrationDto);

	ResponseWrapper<PatientRegistrationDto> getPatientDetailsFromIPAN();
	
	ResponseWrapper<List<AppointmentDetailsResponseDTO>> searchCompletedAppointmentsForPatient(RequestWrapper<AppontmentDetailsRequestDTO> appontmentDetailsRequest);

	ResponseWrapper<List<AppointmentDetailsResponseDTO>> listOfCompletedAppointmentsForPatient();
	
	ResponseWrapper<PatientResponseDto> bulkPatientRegistration(RequestWrapper<BulkPatientRegistrationDTO> bulkRegistrationRequest);
}
