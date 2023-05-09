package com.nsdl.auth.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.DoctorRegDtlsDTO;
import com.nsdl.auth.dto.GetDoctorDetailsDTO;
import com.nsdl.auth.dto.GetDoctorDetailsRequestDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.VerifyDoctorRequestDTO;
import com.nsdl.auth.logger.LoggingClientInfo;
import com.nsdl.auth.service.DoctorVerificationService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/verifyDoctor")
@LoggingClientInfo
@Api(tags = { "Doctor verification : Tele Medicine Infra Provider controller" })
public class DoctorVerificationController {

	@Autowired
	private DoctorVerificationService doctorVerificationService;

	private static final Logger logger = LoggerFactory.getLogger(DoctorVerificationController.class);

	/**
	 * This API would be used to get all list of doctors to verify. </br>
	 * 
	 * @return List<DoctorRegDtlsDTO> this is the response
	 */
	@PostMapping("/getDoctorListToVerify")
	public ResponseEntity<MainResponseDTO<List<DoctorRegDtlsDTO>>> getDoctorListToVerify(
			@RequestBody MainRequestDTO<String> request) {
		logger.info("Doctor list to verify request is received");
		MainResponseDTO<List<DoctorRegDtlsDTO>> response = new MainResponseDTO<>();
		List<DoctorRegDtlsDTO> doctorRegDtlsDTOList = null;
		doctorRegDtlsDTOList = doctorVerificationService.getDoctorListToVerify();
		// System.out.println(doctorRegDtlsDTOList.isEmpty());
		logger.info("Reg Doctor list to verify is:" + doctorRegDtlsDTOList);
		response.setResponse(doctorRegDtlsDTOList);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * This API would be used verify doctor by maker or checker user. </br>
	 * 
	 * @param VerifyDoctorRequestDTO this is request body
	 * @return VerifyDoctorResponseDTO this is the response
	 */
	@PostMapping("/verifyDoctorByDocName")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> verifyDoctorByDocName(
			@RequestBody @Valid MainRequestDTO<VerifyDoctorRequestDTO> request) {
		logger.info("Request received for verify doctor by doctor name");
		VerifyDoctorRequestDTO verifyDoctorRequestDTO = request.getRequest();
		return ResponseEntity.status(HttpStatus.OK)
				.body(doctorVerificationService.verifyDoctorByDocName(verifyDoctorRequestDTO));
	}

	@PostMapping(value = "/getDoctorDetails")
	public ResponseEntity<MainResponseDTO<GetDoctorDetailsDTO>> getDoctorDetailsByDoctorId(
			@RequestBody @Valid MainRequestDTO<GetDoctorDetailsRequestDTO> request) {
		logger.info("Request received for verify doctor by doctor name");
		return ResponseEntity.status(HttpStatus.OK).body(
				doctorVerificationService.getDoctorDetailsByDoctorId(request.getRequest().getDoctorId().toUpperCase()));
	}
}
