package com.nsdl.telemedicine.patient.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.patient.dto.PatientDetailsWithHealthIdDTO;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.PersonalDetailDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.dto.SearchPatientRequestDTO;
import com.nsdl.telemedicine.patient.dto.SearchPatientResponseDTO;
import com.nsdl.telemedicine.patient.dto.UpdateAbhaDetailsDTO;

@Service
public interface PatientVerificationService {
	ResponseWrapper<SearchPatientResponseDTO> searchPatientByHealthId(
			@Valid RequestWrapper<SearchPatientRequestDTO> searchPatientRequestDTO);

	ResponseWrapper<PatientResponseDto> saveRegistrationDetailsWithHealthId(
			@Valid RequestWrapper<PatientDetailsWithHealthIdDTO> patientDetailsDTO);

	ResponseWrapper<PatientResponseDto> updateAbhaDetails(
			@Valid RequestWrapper<UpdateAbhaDetailsDTO> updateAbhaDetailsDTO);

	ResponseWrapper<List<PersonalDetailDto>> getPatientDetails(RequestWrapper<PersonalDetailDto> request);
}
