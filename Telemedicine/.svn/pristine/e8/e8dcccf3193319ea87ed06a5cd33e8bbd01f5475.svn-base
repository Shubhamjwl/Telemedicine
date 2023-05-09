package com.nsdl.ndhm.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.ndhm.dto.DistrictDTO;
import com.nsdl.ndhm.dto.GenerateSessionRespDTO;
import com.nsdl.ndhm.dto.MainRequestDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.OtpRequestDTO;
import com.nsdl.ndhm.dto.StateDtlsDTO;
import com.nsdl.ndhm.dto.StatusDTO;
import com.nsdl.ndhm.service.CommonApiService;
import com.nsdl.ndhm.utility.PublicKeyUtils;
import com.nsdl.ndhm.utility.RestCallUtil;

@RestController
@CrossOrigin
public class CommonApiController {
	private static final Logger logger = LoggerFactory.getLogger(CommonApiController.class);

	@Autowired
	CommonApiService commonApiService;

	@Autowired
	PublicKeyUtils publicKeyUtils;

	@Value("${OTP_GENERATE_URL}")
	private String generateOtpURL;

	@PostMapping(path = "/generateToken", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<GenerateSessionRespDTO>> generateToken() {
		logger.info("Generate Token Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(commonApiService.generateToken());
	}

	@GetMapping(path = "/states", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<List<StateDtlsDTO>>> getStates(@RequestHeader Map<String, String> headers) {
		logger.info("Get State Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(commonApiService.getStates(headers));
	}

	@GetMapping(path = "/districts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<List<DistrictDTO>>> getDistricts(@RequestHeader Map<String, String> headers,
			@RequestParam String stateCode) {
		logger.info("Get Distict Request Received ");
		return ResponseEntity.status(HttpStatus.OK).body(commonApiService.getDistricts(headers, stateCode));
	}

	@GetMapping(path = "/generatePublicKey")
	public ResponseEntity<String> generatePublicKey() {
		logger.info("Get  Public Key Request Received ");
		return ResponseEntity.status(HttpStatus.OK).body(publicKeyUtils.generatePublicKey());
	}

	@SuppressWarnings("unchecked")
	@PostMapping(path = "/test-otp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<StatusDTO>> testTM(
			@RequestBody MainRequestDTO<OtpRequestDTO> careContextTMRequset)  {
		logger.info("Test Method For test OTP started {} " ,careContextTMRequset);
		logger.info("generateOtpURL {}" ,generateOtpURL);
		ResponseEntity<MainResponseDTO<StatusDTO>> response = null;
		try {
			response = (ResponseEntity<MainResponseDTO<StatusDTO>>) RestCallUtil
					.postApiRequest(generateOtpURL, careContextTMRequset, MainResponseDTO.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		

		logger.info("Test Method For test OTP ended {} " ,response);
		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
	}
}
