package com.nsdl.auth.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.exception.ExceptionControllerAdvisor;

@RestControllerAdvice
@SuppressWarnings("rawtypes")
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<MainResponseDTO> {

	@Value("${usrmgmt.api.version}")
	private String version;

	@Value("${usrmgmt.api.id}")
	private String id;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		if (returnType.getMethod().getName().equals("getrolefunctionmapping") || returnType.getMethod().getName().equals("validateToken")) {
			return false;
		}else if (returnType.getContainingClass() != ExceptionControllerAdvisor.class
				&& returnType.getContainingClass().getName().contains("Controller")
				&& returnType.getParameterType() == ResponseEntity.class) {
			return true;
		}
		return false;
	}

	@Override
	public MainResponseDTO beforeBodyWrite(MainResponseDTO body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		//System.out.println(body == null);
		body.setResponsetime(DateUtils.getCurrentLocalDateTime());
		body.setStatus(true);
		body.setVersion(version);
		body.setId(id);
		body.setStatusCode("200");
		return body;
	}

}
