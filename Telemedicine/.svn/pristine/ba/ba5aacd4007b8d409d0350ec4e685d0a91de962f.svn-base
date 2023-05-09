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
import com.google.gson.reflect.TypeToken;
import com.nsdl.ndhm.constant.HealthIdCreationConstands;
import com.nsdl.ndhm.dto.ExceptionJSONInfoDTO;
import com.nsdl.ndhm.dto.HealthIDResp;
import com.nsdl.ndhm.dto.MainRequestDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.SearchHealthIdToLoginReqDTO;
import com.nsdl.ndhm.dto.SearchHealthIdToLoginRespDTO;
import com.nsdl.ndhm.dto.SearchPatientByHealthIdReqDTO;
import com.nsdl.ndhm.dto.SearchPatientByHealthIdRespDTO;
import com.nsdl.ndhm.dto.error.UnprocessableErrorDTO;
import com.nsdl.ndhm.exception.CustomErrorUtils;
import com.nsdl.ndhm.service.VerifyAbhaIdService;
import com.nsdl.ndhm.utility.CommonHeadersUtil;

@Service
public class VerifyAbhaIdServiceImpl implements VerifyAbhaIdService {

	private static final Logger logger = LoggerFactory.getLogger(VerifyAbhaIdServiceImpl.class);
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Value("${ndhm.searchHealthIdToLogin}")
	String searchHealthIdToLogin;

	@Value("${ndhm.telemedicine.searchPatientByHealthId}")
	String searchPatientByHealthId;

	List<ExceptionJSONInfoDTO> listOfExceptions = null;
	ExceptionJSONInfoDTO exceptionJSONInfoDTO = null;
	MainResponseDTO<HealthIDResp> mainResponse = null;

	@Override
	public MainResponseDTO<SearchHealthIdToLoginRespDTO> searchHealthIdToLogin(Map<String, String> headers,
			SearchHealthIdToLoginReqDTO searchHealthIdToLoginReqDTO) {
		logger.info("searchHealthIdToLogin method Starts");
		String url = searchHealthIdToLogin;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<SearchHealthIdToLoginRespDTO> resp = new MainResponseDTO<>();
		SearchHealthIdToLoginRespDTO respData = null;
		if (searchHealthIdToLoginReqDTO != null && searchHealthIdToLoginReqDTO.getHealthId() != null) {

			try {
				uri = new URI(url);
				String encryptedString = new Gson().toJson(searchHealthIdToLoginReqDTO);
				HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
						commonHeadersUtil.getHeadersWithAccessTokenFromServer());
				result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
				respData = new Gson().fromJson(result.getBody(), SearchHealthIdToLoginRespDTO.class); // Json to
																										// response
																										// mapping
				resp.setResponse(respData);
				resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
			} catch (HttpClientErrorException | HttpServerErrorException e) {
				logger.error("Error in generateOTP {} ", e);
				ExceptionJSONInfoDTO error = new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()),
						e.getStatusCode().name());
				String responseString = e.getResponseBodyAsString();
				ObjectMapper mapper = new ObjectMapper();

				try {
					if (e.getStatusCode() == HttpStatus.BAD_REQUEST
							|| e.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
						UnprocessableErrorDTO errorResult = mapper.readValue(responseString,
								UnprocessableErrorDTO.class);
						if (errorResult.getDetails() != null && !errorResult.getDetails().isEmpty()) {
							error.setReason(errorResult.getDetails().get(0).getMessage());
							error.setMessage(errorResult.getDetails().get(0).getMessage());
						}

					}

				} catch (Exception ed) {
					logger.error("Error While Doing Conversion");
				}
				logger.error("Error in createHealthId {} ", e);
				List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();

				errorList.add(error);
				resp.setErrors(errorList);
				resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
				return resp;

			} catch (Exception e) {
				logger.error("Error in searchHealthIdToLogin {} ", e);
				listOfExceptions = new ArrayList<>();
				listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
						HealthIdCreationConstands.OTP_FAILURE.getMsg()));
				resp.setErrors(listOfExceptions);
				resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());

			}

		} else {
			logger.error(" HealthId can't be null");
			resp.setErrors(CustomErrorUtils.throwExceptionForHealthIdMobileNo());
		}

		logger.info("searchHealthIdToLogin method Ends");
		return resp;
	}

	@Override
	public MainResponseDTO<SearchPatientByHealthIdRespDTO> searchPatientByHealthId(
			MainRequestDTO<SearchPatientByHealthIdReqDTO> searchPatientByHealthIdDTO) {

		logger.info("Request Made For searchPatientByHealthId details from telemedicine ");
		logger.info("searchPatientByHealthIdDTO {}", searchPatientByHealthIdDTO);
		String url = searchPatientByHealthId;
		ResponseEntity<String> result = null;
		URI uri;
		List<ExceptionJSONInfoDTO> error = new ArrayList<>();
		ExceptionJSONInfoDTO errorDto = new ExceptionJSONInfoDTO();
		MainResponseDTO<SearchPatientByHealthIdRespDTO> respData = new MainResponseDTO<SearchPatientByHealthIdRespDTO>();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(searchPatientByHealthIdDTO);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, headers);
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

			respData = new Gson().fromJson(result.getBody(),
					new TypeToken<MainResponseDTO<SearchPatientByHealthIdRespDTO>>() {
					}.getType());
			if (respData.isStatus()) {
				if (respData.getResponse().getMobileNo()
						.equals(searchPatientByHealthIdDTO.getRequest().getMobileNo())) {
					errorDto.setMessage("ABHA ID/Number already linked with you.");
				} else {
					errorDto.setMessage("ABHA ID is already linked to other Patient. Try with other ABHA");
				}

			} else {
				errorDto.setMessage("ABHA ID/Number not available.");
			}

		} catch (Exception e) {
			logger.error("Error In Getting response From TM ", e);
			errorDto.setMessage("Facing some issue while processing your request " + e.getLocalizedMessage());
		}
		error.add(errorDto);
		respData.setErrors(error);
		return respData;
	}

}
