package com.nsdl.ndhm.service;

import com.nsdl.ndhm.dto.*;

import java.util.Map;

public interface SearchHealthIdService {

	MainResponseDTO<SearchExistHealthIdRespDTO> checkExistsByHealthId(Map<String, String> headers,
													   MainRequestDTO<SearchByHealthIdDTO> searchByHealthIdDTO);

	MainResponseDTO<SearchByMobRespDTO> searchByMobile(Map<String, String> headers,
			MainRequestDTO<SearchByMobDTO> searchByMobDTO);

	MainResponseDTO<SearchByMobRespDTO> searchByHealthId(Map<String, String> headers,
			MainRequestDTO<SearchByHealthIdDTO> searchByHealthIdDTO);

	MainResponseDTO<SearchByMobRespDTO> searchByAadhar(Map<String, String> headers,
			MainRequestDTO<SearchByAadharDTO> searchByAadharDTO);

}
