package com.nsdl.ndhm.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.nsdl.ndhm.constant.HealthIdCreationConstands;
import com.nsdl.ndhm.dto.ConfirmOtpDTO;
import com.nsdl.ndhm.dto.ExceptionJSONInfoDTO;
import com.nsdl.ndhm.dto.ForgotHealthByMobileReqDTO;
import com.nsdl.ndhm.dto.GenerateHealthIdAndNumRespDTO;
import com.nsdl.ndhm.dto.GenerateOtpDTO;
import com.nsdl.ndhm.dto.GenerateOtpResp;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.logger.LoggingClientInfo;
import com.nsdl.ndhm.service.ForgotHealthIdAndNumService;
import com.nsdl.ndhm.utility.CommonHeadersUtil;

@Service
@LoggingClientInfo
public class ForgotHealthIdAndNumServiceImpl implements ForgotHealthIdAndNumService {
	private static final Logger logger = LoggerFactory.getLogger(ForgotHealthIdAndNumServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Value("${ndhm.forgotHealthIdAadhaarOTP}")
	String healthIDForgotForAadhaarOTP;

	@Value("${ndhm.forgotHealthIdAadhaar}")
	String healthIDForgotForAadhaar;

	@Value("${ndhm.forgotHealthIdMobileOTP}")
	String healthIDForgotForMobileOTP;

	@Value("${ndhm.forgotHealthIdMobile}")
	String healthIDForgotForMobile;

	List<ExceptionJSONInfoDTO> listOfExceptions = null;
	ExceptionJSONInfoDTO exceptionJSONInfoDTO = null;

	/*
	 * generate aadhaar OTP for forgot healthId
	 */
	@Override
	public MainResponseDTO<GenerateOtpResp> generateAadhaarOTP(Map<String, String> headers,
			GenerateOtpDTO generateAadhaarOtpDTO) {
		logger.info("Request Receives to generate Aadhaar OTP");
		String url = healthIDForgotForAadhaarOTP;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<GenerateOtpResp> resp = new MainResponseDTO<>();
		GenerateOtpResp respData = null;

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(generateAadhaarOtpDTO);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), GenerateOtpResp.class); // Json to response mapping
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("Error In generateAadhaarOTP {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (Exception e) {
			logger.error("Error In generateAadhaarOTP {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
					HealthIdCreationConstands.OTP_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		}
		logger.info("Generated aadhaar OTP & txnId is ");
		return resp;
	}

	/*
	 * get healthId and number by aadhaar
	 */
	@Override
	public MainResponseDTO<GenerateHealthIdAndNumRespDTO> getHealthIdByAadhaar(Map<String, String> headers,
			ConfirmOtpDTO confirmOtpDTO) {
		logger.info("Request receives to get HealthId and Number by aadhaar");
		String url = healthIDForgotForAadhaar;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<GenerateHealthIdAndNumRespDTO> resp = new MainResponseDTO<>();
		GenerateHealthIdAndNumRespDTO respData = null;

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(confirmOtpDTO);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), GenerateHealthIdAndNumRespDTO.class); // Json to response
																									// mapping
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("Error In getHealthIdByAadhaar {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (Exception e) {
			logger.error("Error In getHealthIdByAadhaar {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
					HealthIdCreationConstands.OTP_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		}
		logger.info("Requesting to get HealthId and Number using aadhaar");
		return resp;
	}

	/*
	 * generate mobile OTP for forgot healthId
	 */
	@Override
	public MainResponseDTO<GenerateOtpResp> generateMobileOTP(Map<String, String> headers,
			GenerateOtpDTO generateMobileOtpDTO) {
		logger.info("Request Receives to generate Mobile OTP");
		String url = healthIDForgotForMobileOTP;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<GenerateOtpResp> resp = new MainResponseDTO<>();
		GenerateOtpResp respData = null;

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(generateMobileOtpDTO);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), GenerateOtpResp.class); // Json to response mapping
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("Error In generateMobileOTP {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (Exception e) {
			logger.error("Error In generateMobileOTP {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
					HealthIdCreationConstands.OTP_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		}
		logger.info("Generated mobile OTP & txnId is: {}", resp.getResponse().getTxnId());
		return resp;
	}

	/*
	 * get healthId and number by mobile
	 */
	@Override
	public MainResponseDTO<GenerateHealthIdAndNumRespDTO> getHealthIdByMobile(Map<String, String> headers,
			ForgotHealthByMobileReqDTO healthByMobileReqDTO) {
		logger.info("Request receives to get HealthId and Number by mobile");
		String url = healthIDForgotForMobile;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<GenerateHealthIdAndNumRespDTO> resp = new MainResponseDTO<>();
		GenerateHealthIdAndNumRespDTO respData = null;

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(healthByMobileReqDTO);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), GenerateHealthIdAndNumRespDTO.class); // Json to response
																									// mapping
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("Error In getHealthIdByMobile {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (Exception e) {
			logger.error("Error In getHealthIdByMobile {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
					HealthIdCreationConstands.OTP_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		}
		logger.info("Requesting to get HealthId and Number using mobile");
		return resp;
	}
}
