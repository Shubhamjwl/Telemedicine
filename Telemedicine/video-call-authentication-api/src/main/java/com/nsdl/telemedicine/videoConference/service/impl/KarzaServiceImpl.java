package com.nsdl.telemedicine.videoConference.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.nsdl.telemedicine.videoConference.constant.ErrorConstants;
import com.nsdl.telemedicine.videoConference.dto.KarzaVCRequest;
import com.nsdl.telemedicine.videoConference.dto.KarzaVCResponse;
import com.nsdl.telemedicine.videoConference.dto.UserDTO;
import com.nsdl.telemedicine.videoConference.entity.KarzaVCEntity;
import com.nsdl.telemedicine.videoConference.exception.DateParsingException;
import com.nsdl.telemedicine.videoConference.exception.ServiceError;
import com.nsdl.telemedicine.videoConference.repository.KarzaVCRepo;
import com.nsdl.telemedicine.videoConference.service.KarzaService;

@Service
public class KarzaServiceImpl implements KarzaService {

	private static final Logger logger = LoggerFactory.getLogger(KarzaServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${x-karza-key}")
	private String karzaKey;

	@Value("${karza.create.jwt.url}")
	private String karzaJwtUrl;

	@Value("${karza.create.transaction.url}")
	private String karzaTransactionUrl;

	@Value("${karza.create.user.token.url}")
	private String karzaUserTokenUrl;

	@Value("${karza.create.link.url}")
	private String karzaLinkUrl;

	@Autowired
	KarzaVCRepo karzaVCRepo;

	@Autowired
	UserDTO userDto;

	@Override
	public KarzaVCResponse createMeeting(KarzaVCRequest request) {

		KarzaVCResponse response = new KarzaVCResponse();

		KarzaVCEntity meetingData = karzaVCRepo.findByKvdAppointmentId(request.getAppointmentId());
		if (meetingData != null) {
			response.setMeetingUrl(meetingData.getKvdMeetingUrl());
			return response;
		}

		String token = getKarzaToken();
		String transactionId = getTransactionIdForPatient(token, request);
		String userToken = getUserToken(token, transactionId);
		JSONObject data = getLink(userToken);

		String webLink = data.getString("webLink");
		String linkExpiryTimestamp = data.getString("linkExpiryTimestamp");

		// save meeting details into database
		KarzaVCEntity entity = new KarzaVCEntity();
		entity.setKvdAppointmentId(request.getAppointmentId());
		entity.setKvdDoctorId(request.getDoctorId());
		entity.setKvdPatientId(request.getPatientId());
		entity.setKvdPatientName(request.getPatientName());
		entity.setKvdEmail(request.getEmail());
		entity.setKvdPhone(request.getMobile());
		entity.setKvdMeetingUrl(webLink);
		entity.setKvdExpiryTime(new Timestamp(Long.parseLong(linkExpiryTimestamp)).toLocalDateTime());
		entity.setKvdCreatedBy(userDto.getUserName());
		entity.setKvdCreatedTmstmp(LocalDateTime.now());
		karzaVCRepo.save(entity);

		response.setMeetingUrl(webLink);
		return response;
	}

	private String getKarzaToken() {

		String karzaToken = null;
		JSONObject request = new JSONObject();
		JSONArray arr = new JSONArray();
		arr.put("video_kyc");
		request.put("productId", arr);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("x-karza-key", karzaKey);
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

		ResponseEntity<String> response = restTemplate.exchange(karzaJwtUrl, HttpMethod.POST, entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			JSONObject json = new JSONObject(response.getBody());
			JSONObject result = json.getJSONObject("result");

			boolean status = result.getBoolean("success");
			if (status) {
				JSONObject data = result.getJSONObject("data");
				karzaToken = data.getString("karzaToken");
			} else {
				String reason = result.getString("reason");
				logger.info("jwt token generation failed for reason {}", reason);
				throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),
						ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
			}
		} else {
			throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),
					ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
		}
		return karzaToken;
	}

	private String getTransactionIdForPatient(String token, KarzaVCRequest karzaRequest) {

		String transactionId = null;
		JSONObject request = new JSONObject();
		JSONObject applicantFormData = new JSONObject();
		request.put("customerId", karzaRequest.getPatientId());
		applicantFormData.put("firstName", karzaRequest.getPatientName());
		applicantFormData.put("phone", karzaRequest.getMobile());
		applicantFormData.put("email", karzaRequest.getEmail());
		request.put("applicantFormData", applicantFormData);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("karzatoken", token);
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

		ResponseEntity<String> response = restTemplate.exchange(karzaTransactionUrl, HttpMethod.POST, entity,
				String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			JSONObject json = new JSONObject(response.getBody());
			JSONObject result = json.getJSONObject("result");

			boolean status = result.getBoolean("success");
			if (status) {
				JSONObject data = result.getJSONObject("data");
				transactionId = data.getString("transactionId");
			} else {
				String reason = result.getString("reason");
				logger.info("transaction id generation failed for reason {}", reason);
				throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),
						ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
			}
		} else {
			throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),
					ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
		}
		return transactionId;
	}

	private String getUserToken(String token, String transactionId) {

		String userToken = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("karzatoken", token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = restTemplate.exchange(karzaUserTokenUrl + "/" + transactionId, HttpMethod.GET,
				entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			JSONObject json = new JSONObject(response.getBody());
			JSONObject result = json.getJSONObject("result");

			boolean status = result.getBoolean("success");
			if (status) {
				JSONObject data = result.getJSONObject("data");
				userToken = data.getString("userToken");
			} else {
				String reason = result.getString("reason");
				logger.info("user token generation failed for reason {}", reason);
				throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),
						ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
			}
		} else {
			throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),
					ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
		}
		return userToken;
	}

	private JSONObject getLink(String token) {

		JSONObject data = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("karzatoken", token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = restTemplate.exchange(karzaLinkUrl, HttpMethod.GET, entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			JSONObject json = new JSONObject(response.getBody());
			JSONObject result = json.getJSONObject("result");

			boolean status = result.getBoolean("success");
			if (status) {
				data = result.getJSONObject("data");
			} else {
				String reason = result.getString("reason");
				logger.info("user token generation failed for reason {}", reason);
				throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),
						ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
			}
		} else {
			throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),
					ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
		}
		return data;
	}

	@Override
	public KarzaVCResponse getKarzaMeetingUrl(String patientId, String appointmentId) {

		KarzaVCResponse response = new KarzaVCResponse();
		KarzaVCEntity entity = karzaVCRepo.findByKvdPatientIdAndKvdAppointmentId(patientId, appointmentId);

		if (entity == null) {
			throw new DateParsingException(new ServiceError(ErrorConstants.APPOINTMENT_NOT_FOUND.getCode(),
					ErrorConstants.APPOINTMENT_NOT_FOUND.getMessage()));
		}

		response.setMeetingUrl(entity.getKvdMeetingUrl());
		return response;
	}

}
