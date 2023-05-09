package com.nsdl.telemedicine.slot.utility;

import java.util.ArrayList;
import java.util.List;

import com.nsdl.telemedicine.slot.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.slot.dto.MainRequestDTO;
import com.nsdl.telemedicine.slot.dto.MainResponseDTO;

public class SlotUtility {
	public static MainResponseDTO<?> getMainResponseDto(MainRequestDTO<?> mainRequestDto ){
		MainResponseDTO<?> response=new MainResponseDTO<>();
		if(mainRequestDto.getRequest()==null) {
			return response;
		}
		response.setId(mainRequestDto.getId());
		response.setVersion(mainRequestDto.getVersion());
		response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		
		return response;
	}
	
	public static List<ExceptionJSONInfoDTO> getExceptionList(List<ExceptionJSONInfoDTO> list,String errorCode,String errorMsg){
		if(list==null)
			list=new ArrayList<ExceptionJSONInfoDTO>();
		ExceptionJSONInfoDTO error=new ExceptionJSONInfoDTO();
		error.setErrorCode(errorCode);
		error.setMessage(errorMsg);
		list.add(error);
		return list;
	}
}
