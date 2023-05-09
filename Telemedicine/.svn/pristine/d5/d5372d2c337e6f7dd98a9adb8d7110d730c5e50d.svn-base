package com.nsdl.auth.service;

import java.util.List;

import com.nsdl.auth.dto.ActiveDeactiveResponseDTO;
import com.nsdl.auth.dto.FunctionActiveDeactiveDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.RoleFunctionFetchRequestDTO;
import com.nsdl.auth.dto.GetMenuResponseDTO;
import com.nsdl.auth.dto.RoleFunctionRequestDTO;
import com.nsdl.auth.dto.RoleFunctionResponseDTO;

public interface RoleFunctionManagementService {

	RoleFunctionResponseDTO saveFunction(RoleFunctionRequestDTO roleFunctionRequestDTO);

	GetMenuResponseDTO getFunctionListByRoleName(
			RoleFunctionFetchRequestDTO roleFunctionFetchRequestDTO);

	String getAllRoleFunctionMapping();

	MainResponseDTO<ActiveDeactiveResponseDTO> activeDeactiveFunctionRoleService(
			MainRequestDTO<FunctionActiveDeactiveDTO> request);

}
