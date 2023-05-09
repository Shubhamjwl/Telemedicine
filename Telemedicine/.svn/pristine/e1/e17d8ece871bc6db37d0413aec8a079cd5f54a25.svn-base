package com.nsdl.auth.utility;

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

import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.exception.ServiceError;


@Component
public class AuthUtil {
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
		
	public static MainResponseDTO<?> getMainResponseDto(MainRequestDTO<?> mainRequestDto ){
		MainResponseDTO<?> response=new MainResponseDTO<>();
		if(mainRequestDto.getRequest()==null) {
			return response;
		}
		response.setId(mainRequestDto.getId());
		response.setVersion(mainRequestDto.getVersion());
		response.setResponsetime(DateUtils.getCurrentLocalDateTime());
		//response.setStatus(false);
		
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
	public static List<ServiceError> getExceptionList(List<ServiceError> list,String errorCode,String errorMsg){
		if(list==null)
			list=new ArrayList<ServiceError>();
		ServiceError error=new ServiceError(errorCode,errorMsg);
		error.setErrorCode(errorCode);
		error.setMessage(errorMsg);
		list.add(error);
		return list;
	}
}
