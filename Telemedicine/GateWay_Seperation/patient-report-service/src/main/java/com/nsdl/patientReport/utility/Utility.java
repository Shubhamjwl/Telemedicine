package com.nsdl.patientReport.utility;

import java.time.LocalDateTime;

import com.nsdl.patientReport.dto.MainRequestDTO;
import com.nsdl.patientReport.dto.MainResponseDTO;



public class Utility {
	
	public static LocalDateTime getCurrentLocalDateTime() {
		return LocalDateTime.now();
	}
	
	public static MainResponseDTO<?> getMainResponseDto(MainRequestDTO<?> mainRequestDto ){
		MainResponseDTO<?> response=new MainResponseDTO<>();
		if(mainRequestDto.getRequest()==null) {
			return response;
		}
		response.setId(mainRequestDto.getId());
		response.setVersion(mainRequestDto.getVersion());
		response.setResponsetime(getCurrentLocalDateTime());		
		return response;
	}
	
	public static boolean stringIsNullOrEmpty(String str) {

		if (str == null|| str.equals(null)) {
			return true;
		} else {
			str = str.trim().replaceAll("\\s+", " ");
			if (str.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	

}
