package com.nsdl.login.util;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nsdl.login.dto.MainRequestDTO;
import com.nsdl.login.dto.MainResponseDTO;

@Component
public class AuthUtil {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	public static MainResponseDTO<?> getMainResponseDto(MainRequestDTO<?> mainRequestDto) {
		MainResponseDTO<?> response = new MainResponseDTO<>();
		if (mainRequestDto.getRequest() == null) {
			return response;
		}
		response.setId(mainRequestDto.getId());
		response.setVersion(mainRequestDto.getVersion());
		response.setResponsetime(LocalDateTime.now());
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

}
