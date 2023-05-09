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
import com.nsdl.auth.dto.FunctionDeleteRequestDto;
import com.nsdl.auth.dto.FunctionDetailsDto;
import com.nsdl.auth.dto.FunctionRequestDTO;
import com.nsdl.auth.dto.FunctionResposeDTO;
import com.nsdl.auth.dto.FunctionUpdateRequestDto;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.logger.LoggingClientInfo;
import com.nsdl.auth.service.FunctionManagementService;

import io.swagger.annotations.Api;

@RestController
//@CrossOrigin("*")
@RequestMapping(value = "/function")
@LoggingClientInfo
@Api(tags = { "Function Management : Tele Medicine Infra Provider controller" })
public class FunctionManagerController {

	@Autowired
	FunctionManagementService functionManagementService;

	private static final Logger logger = LoggerFactory.getLogger(FunctionManagerController.class);

	/**
	 * This API would be used to create FUNCTION List for given role(Doctor, patient
	 * or scribe). </br>
	 * 
	 * @param FunctionRequestDTO this is request body
	 * @return FunctionResposeDTO this is the response
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<MainResponseDTO<FunctionResposeDTO>> addFunction(
			@RequestBody @Valid MainRequestDTO<FunctionRequestDTO> request) {
		logger.info("Request received for Add function API");
		MainResponseDTO<FunctionResposeDTO> response = new MainResponseDTO<>();
		FunctionResposeDTO functionResposeDTO = functionManagementService.saveFunction(request.getRequest());
		logger.info("Function saved sucessfully");
		response.setResponse(functionResposeDTO);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * This API would be used to update FUNCTION for given functionID. </br>
	 * 
	 * @param functionUpdateRequestDto this is request body
	 * @param functionId               this is functionId. This value coming as path
	 *                                 variable.
	 * @return FunctionUpdateResponseDto this is the response
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> modifyFunction(
			@RequestBody @Valid MainRequestDTO<FunctionUpdateRequestDto> functionUpdateRequestDto) {
		logger.info("Received request for update function request API");
		FunctionUpdateRequestDto updateRequest = functionUpdateRequestDto.getRequest();
		logger.info("function updated sucessfully");
		return ResponseEntity.status(HttpStatus.OK).body(functionManagementService.update(updateRequest));
	}

	/**
	 * This API would be used to retrieve all functions details from database. </br>
	 * 
	 * @return FunctionDetailsDto list of role's
	 */
	@GetMapping(value = "/getFunctions")
	public ResponseEntity<MainResponseDTO<List<FunctionDetailsDto>>> getAllFunctions() {
		logger.info("Request received for get function API");
		MainResponseDTO<List<FunctionDetailsDto>> response = new MainResponseDTO<List<FunctionDetailsDto>>();
		response.setResponse(functionManagementService.getFunctions());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * This API would be used to delete function based on function id from database.
	 * </br>
	 * 
	 * @return FunctionDetailsDto list of function's
	 */

	@DeleteMapping(value = "/delete")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> deleteFunction(
			@RequestBody @Valid MainRequestDTO<FunctionDeleteRequestDto> functionDeleteRequestDto) {
		logger.info("Request received for Delete function API");
		return ResponseEntity.status(HttpStatus.OK)
				.body(functionManagementService.deleteFunction(functionDeleteRequestDto));
	}
}
