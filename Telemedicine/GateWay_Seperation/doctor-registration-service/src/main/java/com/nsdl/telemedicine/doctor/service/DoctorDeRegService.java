package com.nsdl.telemedicine.doctor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.nsdl.telemedicine.doctor.dto.DeRegisterDoctorReqDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorActiveDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorDeregistrationDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorDetailsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorRegDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.MainRequestDTO;
import com.nsdl.telemedicine.doctor.dto.MainResponseDTO;
import com.nsdl.telemedicine.doctor.dto.RegistrationResponseDTO;
import com.nsdl.telemedicine.doctor.dto.VerifyOTPForDocToDeReg;

public interface DoctorDeRegService {

	@SuppressWarnings("rawtypes")
	MainResponseDTO<DoctorRegDtlsDTO> getDoctorByName(String docName);

	MainResponseDTO<RegistrationResponseDTO> deRegisterDoctor(DeRegisterDoctorReqDTO deRegisterDoctorReqDTO);

	@Transactional(rollbackFor = Exception.class) 
	MainResponseDTO<RegistrationResponseDTO> verifiedOTPForDoctorToDeRegister(
			VerifyOTPForDocToDeReg verifyOTPForDocToDeReg);

	public MainResponseDTO<List<DoctorActiveDTO>> getListOfDoctorTodeRegister();

	@Transactional(rollbackFor = Exception.class) 
	MainResponseDTO<RegistrationResponseDTO> listOfDoctorTodeRegister(
			MainRequestDTO<DoctorDeregistrationDTO<DoctorDetailsDTO>> request);


}
