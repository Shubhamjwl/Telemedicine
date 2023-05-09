package com.nsdl.auth.service;

import java.util.List;

import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.DoctorRegDtlsDTO;
import com.nsdl.auth.dto.GetDoctorDetailsDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.VerifyDoctorRequestDTO;

public interface DoctorVerificationService {

	List<DoctorRegDtlsDTO> getDoctorListToVerify();

	MainResponseDTO<CommonResponseDTO> verifyDoctorByDocName(VerifyDoctorRequestDTO verifyDoctorRequestDTO);
	
	MainResponseDTO<GetDoctorDetailsDTO> getDoctorDetailsByDoctorId(String doctorId);

}
