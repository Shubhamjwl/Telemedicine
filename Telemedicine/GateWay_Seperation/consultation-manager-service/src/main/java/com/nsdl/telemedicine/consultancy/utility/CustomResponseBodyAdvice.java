package com.nsdl.telemedicine.consultancy.utility;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.nsdl.telemedicine.consultancy.controller.ConsultationController;
import com.nsdl.telemedicine.consultancy.dto.MainResponseDTO;

@RestControllerAdvice
@SuppressWarnings("rawtypes")
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<MainResponseDTO> {

	@Value("${consultation.manager.service.version}")
	private String version;

	@Value("${consultation.manager.service.id}")
	private String id;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getContainingClass() == ConsultationController.class
				&& returnType.getParameterType() == ResponseEntity.class;
	}

	@Override
	public MainResponseDTO beforeBodyWrite(MainResponseDTO body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {

		body.setResponseTime(LocalDateTime.now());
		body.setStatus(true);
		body.setVersion(version);
		body.setId(id);
		return body;
	}

}
