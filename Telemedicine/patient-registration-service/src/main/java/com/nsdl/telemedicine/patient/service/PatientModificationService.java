package com.nsdl.telemedicine.patient.service;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsdl.telemedicine.patient.dto.AllPatientDetailDto;
import com.nsdl.telemedicine.patient.dto.PatientDetailsRequestDTO;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.PersonalDetailDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;

@Service
public interface PatientModificationService {

	ResponseWrapper<AllPatientDetailDto> getPatientAllDetails();

	ResponseWrapper<PatientResponseDto> updatePatientMedicalDetails(RequestWrapper<?> medicalDetailDto);

	ResponseWrapper<PatientResponseDto> updatePatientLifeStyleDetails( RequestWrapper<?> request);

	ResponseWrapper<PatientResponseDto> updateAllPatientDetails(RequestWrapper<AllPatientDetailDto> request);

	@Transactional(rollbackFor = Exception.class)
	ResponseWrapper<PatientResponseDto> updatePatientPersonalDetails(RequestWrapper<?> request);

	ResponseWrapper<List<PersonalDetailDto>> getPatientDetails(RequestWrapper<PersonalDetailDto> request);

	ResponseWrapper<List<PersonalDetailDto>> getMappedPatientListByDrId(@Valid RequestWrapper<PatientDetailsRequestDTO> request);

	ResponseWrapper<PatientResponseDto> unMappedPatientOrDrById(@Valid RequestWrapper<PatientDetailsRequestDTO> request);

}
