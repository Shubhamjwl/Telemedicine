package com.nsdl.auth.service;

import java.util.List;

import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.FunctionDeleteRequestDto;
import com.nsdl.auth.dto.FunctionDetailsDto;
import com.nsdl.auth.dto.FunctionRequestDTO;
import com.nsdl.auth.dto.FunctionResposeDTO;
import com.nsdl.auth.dto.FunctionUpdateRequestDto;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;

public interface FunctionManagementService {
	
	FunctionResposeDTO saveFunction(FunctionRequestDTO functionRequestDTO);

	MainResponseDTO<CommonResponseDTO> update(FunctionUpdateRequestDto functionUpdateRequest);

	List<FunctionDetailsDto> getFunctions();

	MainResponseDTO<CommonResponseDTO> deleteFunction(MainRequestDTO<FunctionDeleteRequestDto> functionDeleteRequestDto);

}
