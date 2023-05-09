package com.nsdl.ndhm.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.nsdl.ndhm.dto.MainRequestDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.SearchHealthIdToLoginReqDTO;
import com.nsdl.ndhm.dto.SearchHealthIdToLoginRespDTO;
import com.nsdl.ndhm.dto.SearchPatientByHealthIdReqDTO;
import com.nsdl.ndhm.dto.SearchPatientByHealthIdRespDTO;

@Service
public interface VerifyAbhaIdService {

	MainResponseDTO<SearchHealthIdToLoginRespDTO> searchHealthIdToLogin(Map<String, String> headers,
			SearchHealthIdToLoginReqDTO searchHealthIdToLoginReqDTO);

	MainResponseDTO<SearchPatientByHealthIdRespDTO> searchPatientByHealthId(
			MainRequestDTO<SearchPatientByHealthIdReqDTO> searchPatientByHealthIdDTO);

}
