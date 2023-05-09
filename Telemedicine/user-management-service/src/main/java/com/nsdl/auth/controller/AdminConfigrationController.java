package com.nsdl.auth.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.AdminConfigRequest;
import com.nsdl.auth.dto.AdminConfigResponse;
import com.nsdl.auth.dto.AdminSendNotificationRequest;
import com.nsdl.auth.dto.AdminSendNotificationResponse;
import com.nsdl.auth.dto.CategoryStatusRequest;
import com.nsdl.auth.dto.CategoryStatusResponse;
import com.nsdl.auth.dto.DoctorDtoResponse;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.exception.AuthErrorConstant;
import com.nsdl.auth.logger.LoggingClientInfo;
import com.nsdl.auth.service.AdminConfigrationService;
import com.nsdl.auth.utility.AuthUtil;

@RestController
@RequestMapping("/admin")
@LoggingClientInfo
public class AdminConfigrationController {
	
	@Autowired
	AdminConfigrationService adminConfigrationService;

	private static final Logger logger = LoggerFactory.getLogger(AdminConfigrationController.class);
	
	@PostMapping(value="/adminConfig",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<List<AdminConfigResponse>>> adminConfig(
			@RequestBody @Valid MainRequestDTO<AdminConfigRequest> adminConfigRequest) {
		logger.info("Admin Config Request Received");
		
		MainResponseDTO<List<AdminConfigResponse>> response = new MainResponseDTO<>();
		response.setResponse(adminConfigrationService.adminConfig(adminConfigRequest));
		response.setResponsetime(LocalDateTime.now());
		response.setStatus(true);
		 return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping(value="/updateCategoryStatus",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<CategoryStatusResponse>> updateCategoryStatus(
			@RequestBody @Valid MainRequestDTO<List<CategoryStatusRequest>> categoryStatusRequest) {
		logger.info("Category Status Request Received");
		
		//String drUserId = userDetails.getUserName().toUpperCase();
		 return ResponseEntity.status(HttpStatus.OK).body(adminConfigrationService.updateCategoryStatus(categoryStatusRequest));
	}
	
	@PostMapping(value="/adminSendAlert",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<List<AdminSendNotificationResponse>>> adminSendAlert(
			@RequestBody @Valid MainRequestDTO<AdminSendNotificationRequest> adminSendAlert) {
		logger.info("Admin Alert Request Received");
		
		MainResponseDTO<List<AdminSendNotificationResponse>> response = new MainResponseDTO<>();
		response.setResponse(adminConfigrationService.adminSendAlert(adminSendAlert));
		response.setResponsetime(LocalDateTime.now());
		response.setStatus(true);
		 return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/getAllDoctorList")
	public MainResponseDTO<List<DoctorDtoResponse>> getAllDoctorList() {
		MainResponseDTO<List<DoctorDtoResponse>> responseDTO = new MainResponseDTO<List<DoctorDtoResponse>>();
		try {
			logger.info("Receive request");
			List<DoctorDtoResponse> doctorNameList = adminConfigrationService.getAllDoctorList();
			responseDTO.setResponse(doctorNameList);
			responseDTO.setVersion("1.0");
			responseDTO.setStatus(true);
			logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :", e);
			responseDTO.setVersion("1.0");
			responseDTO.setResponse(null);
			responseDTO.setStatus(false);
			responseDTO.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.SOMETHING_WENT_WRONG,
					AuthConstant.SOMETHING_WENT_WRONG));
			e.printStackTrace();
		}
		return responseDTO;
	}
	
}
