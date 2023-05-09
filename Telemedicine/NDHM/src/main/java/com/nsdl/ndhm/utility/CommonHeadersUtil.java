package com.nsdl.ndhm.utility;

import com.nsdl.ndhm.dto.GenerateSessionRespDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.service.CommonApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class CommonHeadersUtil {

	@Autowired
	CommonApiService commonApiService;

	private static final String NDHMTOKEN_KEY = "ndhmtoken";
	private static final String CONTENTTYPE_KEY = "content-type";
	private static final String XCMID_KEY = "x-cm-id";
	private static final String XTOKEN_KEY = "x-token";

	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String HEADER_ACCEPT = "Accept";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final String HEADER_X_CM_ID = "X-CM-ID";
	private static final String HEADER_X_TOKEN = "X-Token";
	private static final String HEADER_ACCEPT_VALUE = "application/json";

	private static final String BEARER = "Bearer ";

	/* for extracting simple headers with authorization */
	public HttpHeaders getHeaderAll(Map<String, String> reqeaders) {
		HttpHeaders headers = new HttpHeaders();
		reqeaders.values().removeIf(Objects::isNull);
		reqeaders.forEach(headers::set);
		return headers;
	}

	/* for extracting simple headers with authorization */
	public HttpHeaders getHeaders(Map<String, String> reqeaders) {
		String header = BEARER + reqeaders.get(NDHMTOKEN_KEY);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_ACCEPT, HEADER_ACCEPT_VALUE);
		headers.set(HEADER_CONTENT_TYPE, reqeaders.get(CONTENTTYPE_KEY));
		headers.set(HEADER_AUTHORIZATION, header);
		return headers;
	}

	/* for extracting headers with x-cm-id */
	public HttpHeaders getHeadersWithXCmId(Map<String, String> reqeaders) {
		String header = BEARER + reqeaders.get(NDHMTOKEN_KEY);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_ACCEPT, HEADER_ACCEPT_VALUE);
		headers.set(HEADER_CONTENT_TYPE, reqeaders.get(CONTENTTYPE_KEY));
		headers.set(HEADER_AUTHORIZATION, header);
		headers.set(HEADER_X_CM_ID, reqeaders.get(XCMID_KEY));
		return headers;
	}

	/* for extracting headers with x-token */
	public HttpHeaders getHeadersWithXtoken(Map<String, String> reqeaders) {
		String header = BEARER + reqeaders.get(NDHMTOKEN_KEY);
		String xToken = BEARER + reqeaders.get(XTOKEN_KEY);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_ACCEPT, HEADER_ACCEPT_VALUE);
		headers.set(HEADER_CONTENT_TYPE, reqeaders.get(CONTENTTYPE_KEY));
		headers.set(HEADER_AUTHORIZATION, header);
		headers.set(HEADER_X_TOKEN, xToken);
		return headers;
	}

	/* for extracting headers only with content type */
	public HttpHeaders getHeadersContentType(Map<String, String> reqeaders) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_ACCEPT, HEADER_ACCEPT_VALUE);
		headers.set(HEADER_CONTENT_TYPE, reqeaders.get(CONTENTTYPE_KEY));
		return headers;
	}

	/* for extracting headers only with content type Only */
	public HttpHeaders getHeadersContentTypeOnly(Map<String, String> reqeaders) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_CONTENT_TYPE, reqeaders.get(CONTENTTYPE_KEY));
		return headers;
	}

	/* for extracting headers with x-cm-id */
	public HttpHeaders getHeadersWithXCmIdFromServer() {
		MainResponseDTO<GenerateSessionRespDTO> response = commonApiService.generateToken();
		if (response != null && response.getResponse() != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.set(HEADER_ACCEPT, HEADER_ACCEPT_VALUE);
			headers.set(HEADER_CONTENT_TYPE, HEADER_ACCEPT_VALUE);
			headers.set(HEADER_AUTHORIZATION, BEARER + response.getResponse().getAccessToken());
			headers.set(HEADER_X_CM_ID, "sbx");
			return headers;
		} else {
			return HttpHeaders.EMPTY;
		}

	}

	/* for extracting headers with auth Token */
	public HttpHeaders getHeadersWithRefreshTokenFromServer() {

		MainResponseDTO<GenerateSessionRespDTO> response = commonApiService.generateToken();
		if (response != null && response.getResponse() != null) {
			HttpHeaders headers = new HttpHeaders();
			String header = BEARER + response.getResponse().getRefreshToken();
			headers.set(HEADER_AUTHORIZATION, header);
			headers.set(HEADER_ACCEPT, HEADER_ACCEPT_VALUE);
			headers.set(HEADER_CONTENT_TYPE, HEADER_ACCEPT_VALUE);
			return headers;
		} else {
			return HttpHeaders.EMPTY;
		}

	}

	/* for extracting headers with auth access Token */
	public HttpHeaders getHeadersWithAccessTokenFromServer() {

		MainResponseDTO<GenerateSessionRespDTO> response = commonApiService.generateToken();
		if (response != null && response.getResponse() != null) {
			HttpHeaders headers = new HttpHeaders();
			String header = BEARER + response.getResponse().getAccessToken();
			headers.set(HEADER_AUTHORIZATION, header);
			headers.set(HEADER_ACCEPT, HEADER_ACCEPT_VALUE);
			headers.set(HEADER_CONTENT_TYPE, HEADER_ACCEPT_VALUE);
			return headers;
		} else {
			return HttpHeaders.EMPTY;
		}

	}

	/* for extracting headers with x-token */
	public HttpHeaders getHeadersWithXtokenAndAccessTokenFromServer(Map<String, String> reqeaders) {

		MainResponseDTO<GenerateSessionRespDTO> response = commonApiService.generateToken();
		if (response != null && response.getResponse() != null) {
			String xToken = BEARER + reqeaders.get(XTOKEN_KEY);
			HttpHeaders headers = new HttpHeaders();
			String header = BEARER + response.getResponse().getAccessToken();
			headers.set(HEADER_AUTHORIZATION, header);
			headers.set(HEADER_ACCEPT, HEADER_ACCEPT_VALUE);
			headers.set(HEADER_CONTENT_TYPE, reqeaders.get(CONTENTTYPE_KEY));
			headers.set(HEADER_X_TOKEN, xToken);
			return headers;
		} else {
			return HttpHeaders.EMPTY;
		}

	}
}
