package com.nsdl.ndhm.transfer.controller;

import com.nsdl.ndhm.transfer.dto.DataRequestDTO;
import com.nsdl.ndhm.transfer.service.DataTranferRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v0.5/health-information/hip")
@CrossOrigin
public class DataTranferRequestController {
	private static final Logger logger = LoggerFactory.getLogger(DataTranferRequestController.class);

	@Autowired
	DataTranferRequestService dataTranferRequestService;

	@PostMapping(value = "/request", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> requestDemo(@RequestHeader Map<String, String> headers,
			@RequestBody DataRequestDTO dataRequestDTO) {
		logger.info("Request Received For request Starts");
		dataTranferRequestService.requestDemo(dataRequestDTO);
		logger.info("Request Received For request ends");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
