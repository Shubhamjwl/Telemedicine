package com.nsdl.telemedicine.master.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.master.constant.ErrorConstants;
import com.nsdl.telemedicine.master.dto.CityDto;
import com.nsdl.telemedicine.master.dto.CountryDto;
import com.nsdl.telemedicine.master.dto.MainRequestDTO;
import com.nsdl.telemedicine.master.dto.MainResponseDTO;
import com.nsdl.telemedicine.master.dto.MasterDetailsDTO;
import com.nsdl.telemedicine.master.dto.MasterRequestDto;
import com.nsdl.telemedicine.master.dto.StateDto;
import com.nsdl.telemedicine.master.logger.LoggingClientInfo;
import com.nsdl.telemedicine.master.service.MasterManagementService;
import com.nsdl.telemedicine.master.utility.MasterUtility;

@RestController
@CrossOrigin("*")
@LoggingClientInfo
public class MasterManagementController {
	@Autowired
	MasterManagementService service;

	private static final Logger logger = LoggerFactory.getLogger(MasterManagementController.class);
	
	@SuppressWarnings("unchecked")
	@PostMapping("/getMasterDetailsListByMasterName")
	public MainResponseDTO<List<MasterDetailsDTO>> getMasterDetailsListByMasterName(@Valid @RequestBody MainRequestDTO<MasterRequestDto> masterRequest) {
		MainResponseDTO<List<MasterDetailsDTO>> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.getMasterDetailsListByMasterName(masterRequest);
			logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<List<MasterDetailsDTO>>) MasterUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(MasterUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/getMasterList")
	public MainResponseDTO<List<MasterDetailsDTO>> getMasterList(@Valid @RequestBody MainRequestDTO<?> masterRequest) {
		MainResponseDTO<List<MasterDetailsDTO>> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.getMasterList(masterRequest);
			logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<List<MasterDetailsDTO>>) MasterUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(MasterUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/saveMasterDetails")
	public MainResponseDTO<String> saveMasterDetails(@Valid @RequestBody MainRequestDTO<List<MasterDetailsDTO>> masterRequest) {
		MainResponseDTO<String> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.saveMasterDetails(masterRequest);
			logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<String>) MasterUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(MasterUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/modifyMasterDetails")
	public MainResponseDTO<String> modifyMasterDetails(@Valid @RequestBody MainRequestDTO<List<MasterDetailsDTO>> masterRequest) {
		MainResponseDTO<String> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.modifyMasterDetails(masterRequest);
			logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<String>) MasterUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(MasterUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}
	@PostMapping("/getStateList")
	public MainResponseDTO<List<StateDto>> getStateList(@Valid @RequestBody MainRequestDTO<CountryDto> masterRequest) {
		MainResponseDTO<List<StateDto>> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.getStateList(masterRequest);
			logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<List<StateDto>>) MasterUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(MasterUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}
	@PostMapping("/getCityList")
	public MainResponseDTO<List<CityDto>> getCityList(@Valid @RequestBody MainRequestDTO<StateDto> masterRequest) {
		MainResponseDTO<List<CityDto>> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.getCityList(masterRequest);
			logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<List<CityDto>>) MasterUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(MasterUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}
	@PostMapping("/getCountryList")
	public MainResponseDTO<List<CountryDto>> getCountryList(@Valid @RequestBody MainRequestDTO<?> masterRequest) {
		MainResponseDTO<List<CountryDto>> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.getCountryList(masterRequest);
			logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<List<CountryDto>>) MasterUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(MasterUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}

	/**
	 * @param specialityCountRequest
	 * @return
	 * @throws IOException
	 * 
	 * Added by girishk to get count of doctor specialization.
	 */
	@PostMapping(value = "/getCountOfSpeciality", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<MainResponseDTO<Long>> getCountOfSpeciality(@RequestBody MainRequestDTO<String> specialityCountRequest,HttpServletResponse res) 
	{ 
		logger.info("Request Received to get count of doctors specialization.");
		MainResponseDTO<Long> responseWrapper = new MainResponseDTO<Long>();
		responseWrapper.setResponse(service.getCountOfSpeciality());
		responseWrapper.setStatus(true);
		logger.info("Returning response");
		return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
	}
}
