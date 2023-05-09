package com.nsdl.ndhm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nsdl.ndhm.constant.HealthIdCreationConstands;
import com.nsdl.ndhm.dto.*;
import com.nsdl.ndhm.dto.error.UnprocessableErrorDTO;
import com.nsdl.ndhm.logger.LoggingClientInfo;
import com.nsdl.ndhm.service.SearchHealthIdService;
import com.nsdl.ndhm.utility.CommonHeadersUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@LoggingClientInfo
public class SearchHealthIdServiceImpl implements SearchHealthIdService {
	private static final Logger logger = LoggerFactory.getLogger(SearchHealthIdServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Value("${ndhm.searchHealthIdByMobNo}")
	String searchHealthIdByMobNo;

	@Value("${ndhm.generateRespSearchByHealthId}")
	String generateRespSearchByHealthId;

	@Value("${ndhm.searchHealthIdByAadhar}")
	String searchHealthIdByAadhar;

	@Value("${ndhm.existHealthId}")
	String checkExistHealthId;

	List<ExceptionJSONInfoDTO> listOfExceptions = null;
	MainResponseDTO<HealthIDResp> mainResponse = null;

	/*
	 * search by mobile number
	 */
	@Override
	public MainResponseDTO<SearchByMobRespDTO> searchByMobile(Map<String, String> headers,
			MainRequestDTO<SearchByMobDTO> searchByMobDTO) {
		logger.info("Request Receives for Search HealthId By Mobile No ");
		String url = searchHealthIdByMobNo;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<SearchByMobRespDTO> resp = new MainResponseDTO<>();
		SearchByMobRespDTO respData = null;
		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(searchByMobDTO.getRequest());
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), SearchByMobRespDTO.class); // Json to response mapping
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException e) {
			logger.info("Error in searchByMobile ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (HttpServerErrorException e) {
			logger.info("Error in searchByMobile ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		}

		catch (Exception e) {
			logger.info("Error in searchByMobile ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
					HealthIdCreationConstands.OTP_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		}
		logger.info("Search HealthId By Mobile No Ends", resp);
		return resp;
	}

	/*
	 * search by health id
	 */
	@Override
	public MainResponseDTO<SearchByMobRespDTO> searchByHealthId(Map<String, String> headers,
			MainRequestDTO<SearchByHealthIdDTO> searchByHealthIdDTO) {
		logger.info("Request Receives for Search HealthId By Health id  ");
		String url = generateRespSearchByHealthId;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<SearchByMobRespDTO> resp = new MainResponseDTO<>();
		SearchByMobRespDTO respData = null;
		try {

			uri = new URI(url);
			String encryptedString = new Gson().toJson(searchByHealthIdDTO.getRequest());
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
					commonHeadersUtil.getHeadersWithAccessTokenFromServer());
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), SearchByMobRespDTO.class); // Json to response mapping
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("Error in SearchByHealthId Exception Handling  {} ", e);
			ExceptionJSONInfoDTO error = new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()),
					e.getStatusCode().name());
			String responseString = e.getResponseBodyAsString();
			ObjectMapper mapper = new ObjectMapper();

			try {
				if (e.getStatusCode() == HttpStatus.BAD_REQUEST
						|| e.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
					logger.error("Inside the Exception Handling If Condition ");
					UnprocessableErrorDTO errorResult = mapper.readValue(responseString, UnprocessableErrorDTO.class);
					if (errorResult.getDetails() != null && !errorResult.getDetails().isEmpty()) {
						error.setReason(errorResult.getDetails().get(0).getMessage());
						error.setMessage(errorResult.getDetails().get(0).getMessage());
					}

				}

			} catch (Exception ed) {
				logger.error("Error While Doing Conversion");
			}

			List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();
			errorList.add(error);
			resp.setErrors(errorList);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		} catch (Exception e) {
			logger.info("Error in searchByHealthId Global {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.SEARCH_FAILURE.getCode(),
					HealthIdCreationConstands.SEARCH_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		}
		logger.info("Search HealthId By Health Id Ends", resp);
		return resp;
	}

	@Override
	public MainResponseDTO<SearchByMobRespDTO> searchByAadhar(Map<String, String> headers,
			MainRequestDTO<SearchByAadharDTO> searchByAadharDTO) {

		logger.info("Request Receives for Search HealthId By Aadhar No ");
		String url = searchHealthIdByAadhar;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<SearchByMobRespDTO> resp = new MainResponseDTO<SearchByMobRespDTO>();
		SearchByMobRespDTO respData = null;
		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(searchByAadharDTO.getRequest());
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), SearchByMobRespDTO.class); // Json to response mapping
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException e) {
			logger.info("Error in searchByAadhar ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (HttpServerErrorException e) {
			logger.info("Error in searchByAadhar ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (Exception e) {
			logger.info("Error in searchByAadhar ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
					HealthIdCreationConstands.OTP_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		}
		logger.info("Search HealthId By Aadhar No Ends", resp);
		return resp;
	}

	@Override
	public MainResponseDTO<SearchExistHealthIdRespDTO> checkExistsByHealthId(Map<String, String> headers,
			MainRequestDTO<SearchByHealthIdDTO> searchByHealthIdDTO) {
		logger.info("Request Receives for check Exists HealthId");
		String url = checkExistHealthId;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<SearchExistHealthIdRespDTO> resp = new MainResponseDTO<>();
		SearchExistHealthIdRespDTO respData = null;
		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(searchByHealthIdDTO.getRequest());
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), SearchExistHealthIdRespDTO.class); // Json to response
																								// mapping
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException e) {
			logger.info("Error in checkExistsByHealthId ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (HttpServerErrorException e) {
			logger.info("Error in checkExistsByHealthId ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (Exception e) {
			logger.info("Error in checkExistsByHealthId ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
					HealthIdCreationConstands.OTP_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		}
		logger.info("Requesting for check Exists HealthId");
		return resp;
	}
}
