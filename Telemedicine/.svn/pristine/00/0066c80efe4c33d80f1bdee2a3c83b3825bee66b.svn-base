package com.nsdl.auth.service;

import java.util.List;

import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.RoleDeleteRequestDto;
import com.nsdl.auth.dto.RoleDetailsDto;
import com.nsdl.auth.dto.RoleRequestDTO;
import com.nsdl.auth.dto.RoleUpdateRequestDto;

public interface RoleManagementService {

	CommonResponseDTO saveRole(RoleRequestDTO roleRequestDTO);

	MainResponseDTO<CommonResponseDTO> update(RoleUpdateRequestDto updateRequest);

	List<RoleDetailsDto> getRoles();

	MainResponseDTO<CommonResponseDTO> deleteRole(MainRequestDTO<RoleDeleteRequestDto> roleDeleteRequestDto);
	
	
}
