package com.nsdl.ndhm.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nsdl.ndhm.constant.HealthIdCreationConstands;
import com.nsdl.ndhm.dto.DistrictDTO;
import com.nsdl.ndhm.dto.ExceptionJSONInfoDTO;
import com.nsdl.ndhm.dto.GenerateSessionDTO;
import com.nsdl.ndhm.dto.GenerateSessionRespDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.StateDtlsDTO;
import com.nsdl.ndhm.exception.BadRequestErrorDTO;
import com.nsdl.ndhm.exception.UnAuthorizedErrorDTO;
import com.nsdl.ndhm.logger.LoggingClientInfo;
import com.nsdl.ndhm.service.CommonApiService;
import com.nsdl.ndhm.utility.CommonHeadersUtil;

@Service
@LoggingClientInfo
public class CommonApiServiceImpl implements CommonApiService {
	private static final Logger logger = LoggerFactory.getLogger(CommonApiServiceImpl.class);
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Value("${ndhm.generateToken}")
	String generateToken;

	@Value("${ndhm.clientId}")
	String clientId;

	@Value("${ndhm.clientSecret}")
	String clientSecret;

	@Value("${ndhm.states}")
	String states;

	@Value("${ndhm.getDistricts}")
	String getDistricts;

	@Value("${ndhm.telemedicine.getPatientDtls}")
	String getPatientDtls;

	@Override
	public MainResponseDTO<GenerateSessionRespDTO> generateToken() {

		logger.info("Request Received For Generate Token");
		String url = generateToken;
		URI uri;
		MainResponseDTO<GenerateSessionRespDTO> response = new MainResponseDTO<>();
		ResponseEntity<String> result = null;
		GenerateSessionRespDTO resp = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept", "application/json");
		headers.set("Content-Type", "application/json");
		try {
			GenerateSessionDTO generateSession = GenerateSessionDTO.builder().clientId(clientId).clientSecret(clientSecret)
					.build();
			uri = new URI(url);
			String encryptedString = new Gson().toJson(generateSession);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, headers);
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			resp = new Gson().fromJson(result.getBody(), GenerateSessionRespDTO.class); // Json to response mapping
			response.setResponse(resp);
			response.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {

			List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();
			ExceptionJSONInfoDTO error = new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()),
					e.getStatusCode().name());
			String responseString = e.getResponseBodyAsString();
			ObjectMapper mapper = new ObjectMapper();

			try {
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					UnAuthorizedErrorDTO errorResult = mapper.readValue(responseString, UnAuthorizedErrorDTO.class);
					error.setReason(errorResult.getMessage());
				} else if (e.getStatusCode() == HttpStatus.BAD_REQUEST){
					BadRequestErrorDTO errorResult = mapper.readValue(responseString, BadRequestErrorDTO.class);
					error.setReason(errorResult.getError().getMessage());
				}

			} catch (Exception ed) {
				ed.printStackTrace();
			}

			errorList.add(error);
			response.setErrors(errorList);
			response.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());

		} catch (Exception e) {
			logger.error("Error In generateToken {} ", e);
			List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();
			errorList.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.TOKEN_GEN_FAIL.getCode(),
					HealthIdCreationConstands.TOKEN_GEN_FAIL.getMsg()));
			response.setErrors(errorList);
			response.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
		}
		return response;
	}

	@Override
	public MainResponseDTO<List<StateDtlsDTO>> getStates(Map<String, String> headers) {
		logger.info("Request Received for get state");
		MainResponseDTO<List<StateDtlsDTO>> resp = new MainResponseDTO<>();
		String url = states;
		URI uri;
		ResponseEntity<String> result = null;
		List<StateDtlsDTO> stateList = new ArrayList<>();

		try {
			uri = new URI(url);
			HttpEntity<String> requestEntity = new HttpEntity<>("", commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
			StateDtlsDTO[] stateDtlsDTOs = new Gson().fromJson(result.getBody(), StateDtlsDTO[].class);
			stateList = Arrays.asList(stateDtlsDTOs);
			resp.setResponse(stateList);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();

			ExceptionJSONInfoDTO error = new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()),
					e.getStatusCode().name());
			String responseString = e.getResponseBodyAsString();

			ObjectMapper mapper = new ObjectMapper();
			try {
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					UnAuthorizedErrorDTO errorResult = mapper.readValue(responseString, UnAuthorizedErrorDTO.class);
					error.setReason(errorResult.getMessage());
				} else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
					BadRequestErrorDTO errorResult = mapper.readValue(responseString, BadRequestErrorDTO.class);
					error.setReason(errorResult.getError().getMessage());
				}

			} catch (Exception ed) {
				ed.printStackTrace();
			}
			errorList.add(error);
			resp.setErrors(errorList);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());

		} catch (Exception e) {

			List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();
			errorList.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.FETCH_STATE_FAIL.getCode(),
					HealthIdCreationConstands.FETCH_STATE_FAIL.getMsg()));
			resp.setErrors(errorList);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());

		}

		logger.info("Requested State List ");
		return resp;
	}

	@Override
	public MainResponseDTO<List<DistrictDTO>> getDistricts(Map<String, String> headers, String stateCode) {
		logger.info("Request Received for get Dist for State Code");
		String url = getDistricts;
		URI uri;
		ResponseEntity<String> result = null;
		List<DistrictDTO> distictList = new ArrayList<>();
		MainResponseDTO<List<DistrictDTO>> resp = new MainResponseDTO<>();

		try {
			url = getDistricts + stateCode;
			uri = new URI(url);
			HttpEntity<String> requestEntity = new HttpEntity<>("", commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
			DistrictDTO[] distictDTO = new Gson().fromJson(result.getBody(), DistrictDTO[].class);
			distictList = Arrays.asList(distictDTO);
			resp.setResponse(distictList);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();

			String responseString = e.getResponseBodyAsString();
			ObjectMapper mapper = new ObjectMapper();
			try {
				BadRequestErrorDTO errorResult = mapper.readValue(responseString, BadRequestErrorDTO.class);
				errorList.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()),
						e.getStatusCode().name(), errorResult.getError().getMessage()));
			} catch (Exception ed) {
				ed.printStackTrace();
			}

			resp.setErrors(errorList);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());

		} catch (Exception e) {
			logger.error("Error In getDistricts {} ", e);
			List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();
			errorList.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.FETCH_STATE_FAIL.getCode(),
					HealthIdCreationConstands.FETCH_DIST_FAIL.getMsg()));
			resp.setErrors(errorList);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
		}
		logger.info("Requested Get Dist List Ends");
		return resp;
	}

	 
}
