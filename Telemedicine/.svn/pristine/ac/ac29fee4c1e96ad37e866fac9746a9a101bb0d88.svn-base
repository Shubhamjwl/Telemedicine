package com.nsdl.ndhm.controller;

import com.nsdl.ndhm.dto.DiscoverRequestDTO;
import com.nsdl.ndhm.service.DiscoverCareContextService;
import com.nsdl.ndhm.utility.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/v0.5/care-contexts/")
public class DiscoverCareContextController {

	private static final Logger logger = LoggerFactory.getLogger(DiscoverCareContextController.class);

	@Autowired
	DiscoverCareContextService discoverCareContextService;

	@Value("${discover.flag}")
	String flag;

	@PostMapping(value = "discover", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> discover(@RequestHeader Map<String, String> headers,
			@RequestBody DiscoverRequestDTO discoverRequestDTO) {
		logger.info("Request Received For Discovery Starts {} Start Time: {}", discoverRequestDTO, CommonUtils.getTime());
		/*
		 * if (flag.contentEquals("dummy")) {
		 * discoverCareContextService.discoverDemo(discoverRequestDTO); /* } else {
		 * discoverCareContextService.discover(discoverRequestDTO); }
		 */

//		discoverCareContextService.discoverDemo(discoverRequestDTO);
		discoverCareContextService.discover(discoverRequestDTO);
		logger.info("Request Received For Discovery ends!!! End Time: {}", CommonUtils.getTime());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
