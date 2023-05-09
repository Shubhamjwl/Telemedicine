package com.nsdl.ndhm.transfer.service.impl;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.nsdl.ndhm.dto.RespDTO;
import com.nsdl.ndhm.entity.CareContextEntity;
import com.nsdl.ndhm.logger.LoggingClientInfo;
import com.nsdl.ndhm.repository.CareContextRepository;
import com.nsdl.ndhm.transfer.dto.AcknowledgementDTO;
import com.nsdl.ndhm.transfer.dto.CareContextDTO;
import com.nsdl.ndhm.transfer.dto.ConsentDetailDTO;
import com.nsdl.ndhm.transfer.dto.HipNotifyReqDTO;
import com.nsdl.ndhm.transfer.dto.NotifyRequestDTO;
import com.nsdl.ndhm.transfer.dto.OnNotifyRespDTO;
import com.nsdl.ndhm.transfer.entity.CareContextConsentEntity;
import com.nsdl.ndhm.transfer.entity.CareContextsEntity;
import com.nsdl.ndhm.transfer.repository.CareContextConsentRepository;
import com.nsdl.ndhm.transfer.repository.CareContextsRepository;
import com.nsdl.ndhm.transfer.service.DataTransferService;
import com.nsdl.ndhm.utility.CommonHeadersUtil;

@Service
@LoggingClientInfo
public class DataTransferServiceImpl implements DataTransferService {
	private static final Logger logger = LoggerFactory.getLogger(DataTransferServiceImpl.class);

	@Autowired
	CareContextRepository careContextRepository;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CareContextConsentRepository careContextConsentRepository;

	@Autowired
	CareContextsRepository careContextsRepository;

	@Value("${ndhm.onNotify}")
	String onNotify;

	@Value("${ndhm.onRequest}")
	String onRequest;

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	/**
	 * get care context from database by healthid
	 */
	public List<CareContextEntity> getCareContextFromDbByHealthId(String healthId) {
		logger.info("Fetch care contexts from DB By HealthId starts {}", healthId);
		List<CareContextEntity> careContextEntityList = null;
		try {
			careContextEntityList = careContextRepository.getCareContextsByHealthId(healthId);
		} catch (Exception e) {
			logger.error(" error while saving in db {}", e);
		}
		return careContextEntityList;
	}

	/*
	 * for notify call
	 */
	@Override
	public ResponseEntity<String> notify(HipNotifyReqDTO notifyRequestDTO) {
		logger.info("Request Receives for HIP Notify Starts");

		OnNotifyRespDTO onNotifyRespDTO = null;

		String url = onNotify;
		ResponseEntity<String> result = null;
		URI uri;
		ConsentDetailDTO consentDetail = notifyRequestDTO.getNotification().getConsentDetail();

		SimpleDateFormat format1 = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		format1.setTimeZone(TimeZone.getTimeZone("UTC"));

		onNotifyRespDTO = OnNotifyRespDTO.builder().requestId(UUID.randomUUID().toString())
				.timestamp(format1.format(new Date()))
				.acknowledgement(AcknowledgementDTO.builder().status("OK")
						.consentId(notifyRequestDTO.getNotification().getConsentId()).build())
				.resp(RespDTO.builder().requestId(notifyRequestDTO.getRequestId()).build()).build();

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(onNotifyRespDTO);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
					commonHeadersUtil.getHeadersWithXCmIdFromServer());
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

			logger.info("data sent {} ", encryptedString);
			if (result != null && result.getStatusCode().is2xxSuccessful() && consentDetail != null) {
				CareContextConsentEntity careContextConsent = CareContextConsentEntity.builder()
						.concentId(consentDetail.getConsentId()).hip(consentDetail.getHip().getId())
						.patientId(consentDetail.getPatient().getId()).perpose(consentDetail.getPurpose().getText())
						.timestamp(notifyRequestDTO.getTimestamp()).requestId(notifyRequestDTO.getRequestId())
						.consentManager(consentDetail.getConsentManager().getId())
						.accessMode(consentDetail.getPermission().getAccessMode())
						.dataEraseAt(consentDetail.getPermission().getDataEraseAt()).build();
				List<CareContextsEntity> careContextsEntityList = new ArrayList<>();
				for (CareContextDTO s : consentDetail.getCareContexts()) {

					careContextsEntityList.add(CareContextsEntity.builder().patientReference(s.getPatientReference())
							.careContextReference(s.getCareContextReference())
							.careContextConsentEntity(careContextConsent).build());
				}
				careContextConsentRepository.save(careContextConsent);
				careContextsRepository.saveAll(careContextsEntityList);
			}

		} catch (Exception e) {
			logger.info("Error in HIP OnNotify call {}", e);
		}

		return result;
	}

	private OnNotifyRespDTO prepareDummyData(NotifyRequestDTO notifyRequestDTO) {
		SimpleDateFormat format1 = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		format1.setTimeZone(TimeZone.getTimeZone("UTC"));

		return OnNotifyRespDTO.builder().requestId(UUID.randomUUID().toString()).timestamp(format1.format(new Date()))
				.acknowledgement(AcknowledgementDTO.builder().status("OK")
						.consentId(notifyRequestDTO.getNotification().getConsentId()).build())
				.resp(RespDTO.builder().requestId(notifyRequestDTO.getRequestId()).build()).build();
	}

	/*
	 * for notify demo call
	 */
	@Override
	public ResponseEntity<String> notifyDemo(NotifyRequestDTO notifyRequestDTO) {
		logger.info("Request Receives for Notify Starts");

		String url = onNotify;
		ResponseEntity<String> result = null;
		URI uri;

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(prepareDummyData(notifyRequestDTO));
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
					commonHeadersUtil.getHeadersWithXCmIdFromServer());

			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

		} catch (Exception e) {
			logger.info("Error in On-discover {} ", e);
		}
		logger.info("Request Receives for Notify Ends");
		return result;
	}

}
