package com.nsdl.auth.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.auth.dto.ActiveDeactiveResponseDTO;
import com.nsdl.auth.dto.FunctionActiveDeactiveDTO;
import com.nsdl.auth.dto.GetMenuResponseDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.RoleFunctionFetchRequestDTO;
import com.nsdl.auth.dto.RoleFunctionRequestDTO;
import com.nsdl.auth.dto.RoleFunctionResponseDTO;
import com.nsdl.auth.logger.LoggingClientInfo;
import com.nsdl.auth.service.RoleFunctionManagementService;

import io.swagger.annotations.Api;

@RestController
//@CrossOrigin("*")
@RequestMapping(value = "/roleFunction")
@LoggingClientInfo
@Api(tags = { "RoleFunction Management : Tele Medicine Infra Provider controller" })
public class RoleFunctionManagerController {

	@Autowired
	RoleFunctionManagementService roleFunctionManagementService;

	private static final Logger logger = LoggerFactory.getLogger(RoleFunctionManagerController.class);

	/**
	 * This API would be used to create ROLE-FUNCTION List for given role(Doctor,
	 * patient or scribe). </br>
	 * 
	 * @param RoleFunctionRequestDTO this is request body
	 * @return RoleFunctionResposeDTO this is the response
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<RoleFunctionResponseDTO>> addFunction(
			@RequestBody @Valid MainRequestDTO<RoleFunctionRequestDTO> request) {
		logger.info("Request received for RoleFunction creation API");
		MainResponseDTO<RoleFunctionResponseDTO> response = new MainResponseDTO<>();
		RoleFunctionResponseDTO roleFunctionResposeDTO = roleFunctionManagementService
				.saveFunction(request.getRequest());
		logger.info("Role function creation sucessfully done");
		response.setResponse(roleFunctionResposeDTO);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * This API would be used to get list of functions based on given role(Doctor,
	 * patient or scribe). </br>
	 * 
	 * @param RoleFunctionRequestDTO this is request body
	 * @return RoleFunctionResposeDTO this is the response
	 */
	@PostMapping("/getFunctions")
	public ResponseEntity<MainResponseDTO<GetMenuResponseDTO>> getFunctionListByRoleName(
			@RequestBody @Valid MainRequestDTO<RoleFunctionFetchRequestDTO> request) {
		logger.info("Request received for getting list of functions for role name");
		MainResponseDTO<GetMenuResponseDTO> response = new MainResponseDTO<>();
		GetMenuResponseDTO roleFunctionList = roleFunctionManagementService
				.getFunctionListByRoleName(request.getRequest());
		logger.info("getting list of functions by role name");
		response.setResponse(roleFunctionList);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping(value = "/activeDeactiveRoleFunctionMapping")
	public ResponseEntity<MainResponseDTO<ActiveDeactiveResponseDTO>> activateDeactivateRoleFunctionMapping(
			@RequestBody MainRequestDTO<FunctionActiveDeactiveDTO> request) {
		logger.info("RoleFunction mapping Active/Deactive request Received");
		return ResponseEntity.status(HttpStatus.OK)
				.body(roleFunctionManagementService.activeDeactiveFunctionRoleService(request));
	}

	@GetMapping(value = "/getAllRoleFunctionsMapping")
	public ResponseEntity<String> getrolefunctionmapping() {
		logger.info("Get all RoleFunction Mapping Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(roleFunctionManagementService.getAllRoleFunctionMapping());
	}

}
