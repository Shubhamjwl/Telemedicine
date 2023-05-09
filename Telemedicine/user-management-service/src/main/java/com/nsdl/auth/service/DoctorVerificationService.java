package com.nsdl.auth.service;

import java.util.List;

import javax.validation.Valid;

import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.DoctorRegDtlsDTO;
import com.nsdl.auth.dto.GetDoctorDetailsDTO;
import com.nsdl.auth.dto.GetDoctorDetailsRequestDTO;
import com.nsdl.auth.dto.HealthIdDetailsToTelemedicineDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.UpdateLinkDTO;
import com.nsdl.auth.dto.VerifyDoctorRequestDTO;

public interface DoctorVerificationService {

	List<DoctorRegDtlsDTO> getDoctorListToVerify();

	MainResponseDTO<CommonResponseDTO> verifyDoctorByDocName(VerifyDoctorRequestDTO verifyDoctorRequestDTO);
	
	MainResponseDTO<GetDoctorDetailsDTO> getDoctorDetailsByDoctorId(String doctorId);

	MainResponseDTO<CommonResponseDTO> updateLinkForPatient(
			@Valid MainRequestDTO<UpdateLinkDTO<GetDoctorDetailsRequestDTO>> request);
	
	CommonResponseDTO updateLinkForDoctor(@Valid UpdateLinkDTO<GetDoctorDetailsRequestDTO> updateLinkDTO);

	MainResponseDTO<CommonResponseDTO> updateHealthIDDetails(@Valid MainRequestDTO<HealthIdDetailsToTelemedicineDTO> healthIdDetailsDTO);

	String updateLoginAllowedFlagForDoctor(List<String> doctorUserIds);

	List<DoctorRegDtlsDTO> getListOfDoctorToAllowLogin();

}
