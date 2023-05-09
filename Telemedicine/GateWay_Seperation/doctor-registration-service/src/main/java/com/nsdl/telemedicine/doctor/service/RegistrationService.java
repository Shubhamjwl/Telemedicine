package com.nsdl.telemedicine.doctor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.nsdl.telemedicine.doctor.dto.AppointmentDetailsResponseDTO;
import com.nsdl.telemedicine.doctor.dto.AppontmentDetailsRequestDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorDocsDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorMstrDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorRegDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.MainRequestDTO;
import com.nsdl.telemedicine.doctor.dto.MainResponseDTO;
import com.nsdl.telemedicine.doctor.dto.ScribeRegDtlsDTO;
import com.nsdl.telemedicine.doctor.entity.DoctorDocsDtlEntity;
import com.nsdl.telemedicine.doctor.entity.DoctorRegDtlsEntity;

/*
 * @author:SayaliA
 */

public interface RegistrationService {

	public MainResponseDTO<?> doctorProfile(MainRequestDTO<String> profileRequest);
	
	public MainResponseDTO<?> getDoctorDocumentsDetails(MainRequestDTO<String> drDocDtlsRequest);
	
	@Transactional(rollbackFor = Exception.class) 
	public MainResponseDTO<?> updateDoctorProfile(MainRequestDTO<DoctorMstrDtlsDTO> profileUpdateRequest);
	
	public MainResponseDTO<?> viewScribeList(MainRequestDTO<ScribeRegDtlsDTO> scribeListRequest);
	
	public MainResponseDTO<?> activeDeactiveScribe(MainRequestDTO<ScribeRegDtlsDTO> scribeActivationRequest);

	public int deleteuploadedDocuments(String userid);

	public MainResponseDTO<String> saveuploadedDocuments(MainRequestDTO<DoctorRegDtlsDTO<DoctorDocsDtlsDTO>> registerRequest,DoctorRegDtlsEntity doctorrRegDtlsEntity);
	
	public DoctorDocsDtlEntity getDoctorDocDetailsByID(Integer docId);
	
	public MainResponseDTO<String> saveDoctorRegistrationDetails(
			MainRequestDTO<DoctorRegDtlsDTO<DoctorDocsDtlsDTO>> registerRequest) throws Exception;
	
	public Long getCountOfDoctors();
	
	public MainResponseDTO<?> getScribeListByDoctorToActiveDeactive(MainRequestDTO<ScribeRegDtlsDTO> scribeListRequest);
	
	public MainResponseDTO<?> changeDefaultScribe(MainRequestDTO<ScribeRegDtlsDTO> changeDefaultScribeRequest, String doctorUserId);
	
	MainResponseDTO<List<AppointmentDetailsResponseDTO>> searchCompletedAppointmentsForDoctor(MainRequestDTO<AppontmentDetailsRequestDTO> appointmentDetailsRequest);
}
