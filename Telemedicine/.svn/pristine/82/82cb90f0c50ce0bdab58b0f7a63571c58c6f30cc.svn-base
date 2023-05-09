package com.nsdl.auth.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.RoleDeleteRequestDto;
import com.nsdl.auth.dto.RoleDetailsDto;
import com.nsdl.auth.dto.RoleRequestDTO;
import com.nsdl.auth.dto.RoleUpdateRequestDto;
import com.nsdl.auth.logger.LoggingClientInfo;
import com.nsdl.auth.service.RoleManagementService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/role")
@LoggingClientInfo
@Api(tags = { "Role Management : Tele Medicine Infra Provider controller" })
public class RoleManagerController {

	@Autowired
	private RoleManagementService roleManagementService;

	private static final Logger logger = LoggerFactory.getLogger(RoleManagerController.class);

	/**
	 * This API would be used to create ROLE for given roleID. </br>
	 * 
	 * @param RoleRequestDTO this is request body
	 * @return RoleResposeDTO this is the response
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> addRole(
			@RequestBody @Valid MainRequestDTO<RoleRequestDTO> request) {
		logger.info("Received Create Role request from admin");
		MainResponseDTO<CommonResponseDTO> response = new MainResponseDTO<>();
		response.setResponse(roleManagementService.saveRole(request.getRequest()));
		logger.info("Role creation sucessful");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * This API would be used to update ROLE for given roleID. </br>
	 * 
	 * @param roleUpdateRequestDto this is request body
	 * @param roleId               this is roleId. This value coming as path
	 *                             variable.
	 * @return RoleUpdateResponseDto this is the response
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> modifyRole(
			@RequestBody @Valid MainRequestDTO<RoleUpdateRequestDto> roleUpdateRequestDto) {
		logger.info("Received Modify Role request from Admin");
		return ResponseEntity.status(HttpStatus.OK)
				.body(roleManagementService.update(roleUpdateRequestDto.getRequest()));
	}

	/**
	 * This API would be used to retrieve all roles details from database. </br>
	 * 
	 * @return RoleDetailsDto list of role's
	 */
	@GetMapping(value = "/getRoles")
	public ResponseEntity<MainResponseDTO<List<RoleDetailsDto>>> getAllRoles() {
		logger.info("Received request for getting all roles");
		MainResponseDTO<List<RoleDetailsDto>> response = new MainResponseDTO<List<RoleDetailsDto>>();
		response.setResponse(roleManagementService.getRoles());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * This API would be used to delete role based on role id from database. </br>
	 * 
	 * @return RoleDetailsDto list of role's
	 */

	@DeleteMapping(value = "/delete")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> deleteRole(
			@RequestBody @Valid MainRequestDTO<RoleDeleteRequestDto> roleDeleteRequestDto) {
		logger.info("Received request for deleting role by admin");
		return new ResponseEntity<>(roleManagementService.deleteRole(roleDeleteRequestDto), HttpStatus.OK);
	}

}
