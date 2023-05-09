package com.nsdl.telemedicine.slot.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nsdl.telemedicine.slot.dto.AvailableSlotReqDto;
import com.nsdl.telemedicine.slot.dto.AvailableSlotResDto;
import com.nsdl.telemedicine.slot.dto.DeleteSlotRequestDTO;
import com.nsdl.telemedicine.slot.dto.HolidayReqtDto;
import com.nsdl.telemedicine.slot.dto.MainRequestDTO;
import com.nsdl.telemedicine.slot.dto.MainResponseDTO;
import com.nsdl.telemedicine.slot.dto.MonthHistoryDTO;
import com.nsdl.telemedicine.slot.dto.SaveSlotRequestDTO;
import com.nsdl.telemedicine.slot.dto.ScribeDTO;
import com.nsdl.telemedicine.slot.dto.SlotDetailsDto;
import com.nsdl.telemedicine.slot.dto.SlotReqtDto;
import com.nsdl.telemedicine.slot.dto.ViewSlotRequestDTO;

public interface SlotManagementService {


	MainResponseDTO<List<SaveSlotRequestDTO>> viewSlot(MainRequestDTO<ViewSlotRequestDTO> masterRequest);

	MainResponseDTO<String> modifySlot(MainRequestDTO<List<SaveSlotRequestDTO>> masterRequest);

	MainResponseDTO<String> getDocRegId(MainRequestDTO<ScribeDTO> masterRequest);

	MainResponseDTO<String> saveSlot(MainRequestDTO<SlotReqtDto> masterRequest);

	MainResponseDTO<List<String>> getSlotCreatedDays(MainRequestDTO<?> masterRequest);

	MainResponseDTO<List<AvailableSlotResDto>> getAvailableSlotByMonth(
			MainRequestDTO<AvailableSlotReqDto> masterRequest);

	MainResponseDTO<String> holidayManagement(MainRequestDTO<HolidayReqtDto> masterRequest);

	MainResponseDTO<String> getSlotCreatedMonth( MainRequestDTO<?> masterRequest);

	MainResponseDTO<Map<String, MonthHistoryDTO>> getApptHistory(MainRequestDTO<?> masterRequest);

	MainResponseDTO<List<AvailableSlotResDto>> getHistoricalSlotDetailsByMonth(
			 MainRequestDTO<AvailableSlotReqDto> masterRequest);

	MainResponseDTO<String>  getstaicdetails();

	MainResponseDTO<String> saveSlotDetails(MainRequestDTO<SlotDetailsDto> masterRequest);

	String deleteSlot(DeleteSlotRequestDTO masterRequest);

}
