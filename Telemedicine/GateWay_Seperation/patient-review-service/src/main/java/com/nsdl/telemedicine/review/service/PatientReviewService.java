package com.nsdl.telemedicine.review.service;

import com.nsdl.telemedicine.review.dto.MainRequestDTO;
import com.nsdl.telemedicine.review.dto.MainResponseDTO;
import com.nsdl.telemedicine.review.dto.PatientRevDtlsDTO;

public interface PatientReviewService {

	public MainResponseDTO<?> savePatientReviewDtls(MainRequestDTO<PatientRevDtlsDTO> reviewRequest);
	
	public MainResponseDTO<?> viewPatientReviewForDoctor(MainRequestDTO<PatientRevDtlsDTO> viewPtReviewRequest);
	
	public Long getNumberOfLikesToDoctor(String doctorUserId);
	
	public Long getNumberOfCommentsToDoctor(String doctorUserId);
}
