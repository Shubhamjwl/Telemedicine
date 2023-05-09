package com.nsdl.login.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.login.dto.LoginRequest;
import com.nsdl.login.dto.LoginResponse;
import com.nsdl.login.dto.MainRequestDTO;
import com.nsdl.login.dto.MainResponseDTO;
import com.nsdl.login.service.LoginService;
import com.nsdl.login.util.AuthUtil;

@RestController
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService service;

	@SuppressWarnings("unchecked")
	@PostMapping("/loginVerifier")
	public ResponseEntity<MainResponseDTO<LoginResponse>> validateUser(
			@RequestBody @Valid MainRequestDTO<LoginRequest> request) {
		logger.info("login Request Received");
		MainResponseDTO<LoginResponse> response = (MainResponseDTO<LoginResponse>) AuthUtil.getMainResponseDto(request);
		response.setResponse(service.validateUser(request.getRequest()));
		response.setStatus(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/jwtVerify")
	public ResponseEntity<Boolean> jwtVerify(@RequestParam String jwtToken) {
		logger.info("jwt verify Request Received");
		boolean status = service.jwtVerify(jwtToken);
		return ResponseEntity.status(HttpStatus.OK).body(status);
	}

}
