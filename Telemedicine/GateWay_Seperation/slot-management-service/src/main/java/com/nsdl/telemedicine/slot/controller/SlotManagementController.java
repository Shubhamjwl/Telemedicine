package com.nsdl.telemedicine.slot.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.slot.constant.ErrorConstants;
import com.nsdl.telemedicine.slot.dto.AvailableSlotReqDto;
import com.nsdl.telemedicine.slot.dto.AvailableSlotResDto;
import com.nsdl.telemedicine.slot.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.slot.dto.HolidayReqtDto;
import com.nsdl.telemedicine.slot.dto.MainRequestDTO;
import com.nsdl.telemedicine.slot.dto.MainResponseDTO;
import com.nsdl.telemedicine.slot.dto.SaveSlotRequestDTO;
import com.nsdl.telemedicine.slot.dto.ScribeDTO;
import com.nsdl.telemedicine.slot.dto.SlotReqtDto;
import com.nsdl.telemedicine.slot.dto.ViewSlotRequestDTO;
import com.nsdl.telemedicine.slot.logger.LoggingClientInfo;
import com.nsdl.telemedicine.slot.service.SlotManagementService;
import com.nsdl.telemedicine.slot.utility.SlotUtility;

@RestController
@LoggingClientInfo
@CrossOrigin("*")
public class SlotManagementController {
	@Autowired
	private SlotManagementService service;
	
	private static final Logger logger = LoggerFactory.getLogger(SlotManagementController.class);
	 
	@SuppressWarnings("unchecked")
	@PostMapping("/viewSlotDetails")
	public MainResponseDTO<List<SaveSlotRequestDTO>> viewSlotDetails(@Valid @RequestBody MainRequestDTO<ViewSlotRequestDTO> masterRequest) {
		MainResponseDTO<List<SaveSlotRequestDTO>> responseDTO=null;
		try {
			logger.info("Receive request"); 
			responseDTO= service.viewSlot(masterRequest);
			logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<List<SaveSlotRequestDTO>>) SlotUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(SlotUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/modifySlotDetails")
	public MainResponseDTO<String> modifySlotDetails(@Valid @RequestBody MainRequestDTO<List<SaveSlotRequestDTO>> masterRequest) {
		MainResponseDTO<String> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.modifySlot(masterRequest);
			 logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<String>) SlotUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(SlotUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/getDocRegId")
	public MainResponseDTO<String> getDocRegId(@Valid @RequestBody MainRequestDTO<ScribeDTO> masterRequest) {
		MainResponseDTO<String> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.getDocRegId(masterRequest);
			logger.info("Return response");
		} catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<String>) SlotUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(SlotUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/saveSlotDetails")
	public MainResponseDTO<String> saveSlotDetail(@Valid @RequestBody MainRequestDTO<SlotReqtDto> masterRequest) {
		MainResponseDTO<String> responseDTO=null;
		List<ExceptionJSONInfoDTO> errorList=null;
		try {
			logger.info("Receive request");
			responseDTO= service.saveSlot(masterRequest);
			logger.info("Return response");
		} 
		catch (DataIntegrityViolationException e) {
			if(e.getMessage().contains("fk8oaysmi662xnm1bne4g724roj"))
			{
				errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_DOC_DETAILS.getCode(), ErrorConstants.INVALID_DOC_DETAILS.getMessage());
				logger.error(ErrorConstants.INVALID_DOC_DETAILS.getMessage());
			}
			else
			{
				errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_SLOT_TIME_AVAILABLE.getCode(), ErrorConstants.INVALID_SLOT_TIME_AVAILABLE.getMessage());
				logger.error(ErrorConstants.INVALID_SLOT_TIME_AVAILABLE.getMessage());
			}
			responseDTO = (MainResponseDTO<String>) SlotUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setResponse(ErrorConstants.FAIL_TO_SAVE_RECORD.getMessage());
			responseDTO.setErrors(errorList);
			return responseDTO;
		}			 
		catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<String>) SlotUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(SlotUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/getSlotCreatedDays")
	public MainResponseDTO<List<String>> getSlotCreatedDays(@Valid @RequestBody MainRequestDTO<?> masterRequest) {
		MainResponseDTO<List<String>> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.getSlotCreatedDays(masterRequest);
			 logger.info("Return response");
		} 
		catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<List<String>>) SlotUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(SlotUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/getAvailableSlotByMonth")
	public MainResponseDTO<List<AvailableSlotResDto>> getAvailableSlotByMonth(@Valid @RequestBody MainRequestDTO<AvailableSlotReqDto> masterRequest) {
		MainResponseDTO<List<AvailableSlotResDto>> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.getAvailableSlotByMonth(masterRequest);
			 logger.info("Return response");
		} 
		catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<List<AvailableSlotResDto>>) SlotUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(SlotUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/holidayManagement")
	public MainResponseDTO<String> holidayManagement(@Valid @RequestBody MainRequestDTO<HolidayReqtDto> masterRequest) {
		MainResponseDTO<String> responseDTO=null;
		try {
			logger.info("Receive request");
			responseDTO= service.holidayManagement(masterRequest);
			 logger.info("Return response");
		} 
		catch (Exception e) {
			logger.error("Error occured while processing request :",e);
			responseDTO = (MainResponseDTO<String>) SlotUtility.getMainResponseDto(masterRequest);
			responseDTO.setStatus(false);
			responseDTO.setErrors(SlotUtility.getExceptionList(null, ErrorConstants.SOMETHING_WENT_WRONG.getCode(), ErrorConstants.SOMETHING_WENT_WRONG.getMessage()));
			e.printStackTrace();
		}
		return responseDTO;
	}

}
