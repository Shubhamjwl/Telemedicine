package com.nsdl.telemedicine.doctor.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nsdl.telemedicine.doctor.constant.DoctorRegConstant;
import com.nsdl.telemedicine.doctor.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.doctor.dto.MainRequestDTO;
import com.nsdl.telemedicine.doctor.dto.MainResponseDTO;
import com.nsdl.telemedicine.doctor.exception.DoctorRegistrationException;
import com.nsdl.telemedicine.doctor.exception.DrRegErrorConstant;
import com.nsdl.telemedicine.doctor.exception.DrRegErrorMessage;

/*
 * @author SayaliA
 */

@Component("doctorAuthUtil")
public class DoctorAuthUtil {
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
		
	public static MainResponseDTO<?> getMainResponseDto(MainRequestDTO<?> mainRequestDto ){
		MainResponseDTO<?> response=new MainResponseDTO<>();
		if(mainRequestDto.getRequest()==null) {
			return response;
		}
		response.setId("registration");
		response.setVersion(mainRequestDto.getVersion());
		response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		return response;
	}
	
	public ResponseEntity<?> postApiRequest(String url, HttpMethod httpMethodType, MediaType mediaType, Object body,
			Class<?> responseClass) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		HttpEntity<?> request = new HttpEntity<>(body, headers);
		return restTemplate.exchange(url, httpMethodType, request, responseClass);
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
	
	/**
	 * @param e
	 * @return
	 * Added by girishk for exception handling.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static MainResponseDTO getExceptionDetails(Exception e) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		if(e instanceof DoctorRegistrationException) {
			DoctorRegistrationException exception = (DoctorRegistrationException) e;
			mainResponseDTO.setErrors(DoctorAuthUtil.getExceptionList(null, exception.getList().getErrorCode(), exception.getList().getMessage()));
		}else {
			mainResponseDTO.setErrors(DoctorAuthUtil.getExceptionList(null, DrRegErrorConstant.SERVER_ERROR, DrRegErrorMessage.SERVER_ERROR));
		}
		mainResponseDTO.setResponse(null);
		mainResponseDTO.setStatus(DoctorRegConstant.STATUS_FAIL.isStatus());
		return mainResponseDTO;
	}
}
