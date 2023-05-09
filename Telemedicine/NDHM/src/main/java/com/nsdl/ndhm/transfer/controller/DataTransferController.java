package com.nsdl.ndhm.transfer.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.ndhm.transfer.dto.HipNotifyReqDTO;
import com.nsdl.ndhm.transfer.dto.NotifyRequestDTO;
import com.nsdl.ndhm.transfer.service.DataTransferService;

@RestController
@CrossOrigin
@RequestMapping("/v0.5/consents/hip/")
public class DataTransferController {
	private static final Logger logger = LoggerFactory.getLogger(DataTransferController.class);

	@Autowired
	DataTransferService dataTransferService;

	@PostMapping(value = "notify", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ontify(@RequestHeader Map<String, String> headers,
			@RequestBody HipNotifyReqDTO notifyRequestDTO) {
		logger.info("Request Received For HIP ontify Starts " );
 
		return dataTransferService.notify(notifyRequestDTO);
	}

	@PostMapping(value = "notify1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ontifyDemo(@RequestHeader Map<String, String> headers,
			@RequestBody NotifyRequestDTO notifyRequestDTO) {

		logger.info("Request Received For ontify Starts");

		dataTransferService.notifyDemo(notifyRequestDTO);
		logger.info("Request Received For ontify ends");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
