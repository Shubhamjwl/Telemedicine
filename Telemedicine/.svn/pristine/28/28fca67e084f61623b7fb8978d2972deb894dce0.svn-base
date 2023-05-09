package com.nsdl.ndhm.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nsdl.ndhm.dto.*;
import com.nsdl.ndhm.repository.CareContextRepository;
import com.nsdl.ndhm.repository.PatientDiscoveryRepository;
import com.nsdl.ndhm.service.DiscoverCareContextService;
import com.nsdl.ndhm.utility.CommonHeadersUtil;
import com.nsdl.ndhm.utility.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DiscoverCareContextServiceImpl implements DiscoverCareContextService {
	private static final Logger logger = LoggerFactory.getLogger(DiscoverCareContextServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	PatientDiscoveryRepository patientDiscoveryRepository;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Value("${ndhm.onDiscover}")
	String onDiscover;

	@Value("${ndhm.telemedicine.getCareContextDtls}")
	String getCareContextDtls;

	@Autowired
	CareContextRepository careContextRepository;

	List<ExceptionJSONInfoDTO> listOfExceptions = null;

	/*
	 * get care context details by healthid
	 */
	List<CareContextTMDTO> getCareContextDetailsFromTM(String healthId) {
		logger.info("Request Made For Getting careContext details from telemedicine {}", healthId);

		String url = getCareContextDtls;
		ResponseEntity<String> result = null;
		URI uri;
		List<CareContextTMDTO> respData = new ArrayList<>();
		MainRequestDTO<CareContextTMRequsetDTO> requestData = new MainRequestDTO<>();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		try {
			requestData.setRequest(CareContextTMRequsetDTO.builder().healthId(healthId).build());
			uri = new URI(url);
			String encryptedString = new Gson().toJson(requestData);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, headers);
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

			MainResponseDTO<List<CareContextTMDTO>> message = new Gson().fromJson(result.getBody(),
					new TypeToken<MainResponseDTO<List<CareContextTMDTO>>>() {
					}.getType());

			respData = message.getResponse();

		} catch (Exception e) {
			logger.error("Error In Getting response From TM ", e);
		}

		return respData;

	}

	/*
	 * call discovery method
	 */
	public ResponseEntity<String> discover(DiscoverRequestDTO discoverRequestDTO) {
		logger.info("Request Receives for Discover Starts");
		String url = onDiscover;
		ResponseEntity<String> result = null;
		URI uri;
		List<CareContextDTO> careContextDTOList = new ArrayList<>();

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		format1.setTimeZone(TimeZone.getTimeZone("UTC"));

		String timeStamp = format1.format(new Date(Calendar.getInstance().getTimeInMillis() + (1350 * 60)));

		logger.info("TimeStamp: {}",timeStamp);
		logger.info("CommonUtils.getTime(): {}", CommonUtils.getTime());

/*		OnDiscoverDTO onDiscoverDTO = OnDiscoverDTO.builder().requestId(UUID.randomUUID().toString())
				.timestamp(CommonUtils.getTime()).transactionId(discoverRequestDTO.getTransactionId())
				.resp(RespDTO.builder().requestId(discoverRequestDTO.getRequestId()).build()).build();*/

		OnDiscoverDTO onDiscoverDTO = OnDiscoverDTO.builder().requestId(UUID.randomUUID().toString())
				.timestamp(timeStamp).transactionId(discoverRequestDTO.getTransactionId())
				.resp(RespDTO.builder().requestId(discoverRequestDTO.getRequestId()).build()).build();

		List<CareContextTMDTO> careContextsFromTM = getCareContextDetailsFromTM(
				discoverRequestDTO.getPatient().getId());

		for (CareContextTMDTO b : careContextsFromTM) {
			careContextDTOList.add(
					CareContextDTO.builder().referenceNumber(b.getCareContextId()).display(b.getDisplayName().trim()).build());
		}

		if (careContextsFromTM != null && !careContextsFromTM.isEmpty()) {
			OnDiscoverPatientDTO onRecoveryPatientDTO = OnDiscoverPatientDTO.builder()
					.referenceNumber(careContextsFromTM.get(0).getPatientId())
					.display(careContextsFromTM.get(0).getPatientName()).matchedBy(Arrays.asList("HEALTH_ID"))
					.careContexts(careContextDTOList).build();
			onDiscoverDTO.setPatient(onRecoveryPatientDTO);
		}
		if (careContextsFromTM != null) {

			try {
				uri = new URI(url);
				String encryptedString = new Gson().toJson(onDiscoverDTO);
				HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
						commonHeadersUtil.getHeadersWithXCmIdFromServer());
				logger.info("encryptedString of ondiscover {} ", encryptedString);
				result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
				/*
				 * List<CareContextEntity> entityList = new ArrayList<>(); for (CareContextTMDTO
				 * s : careContextsFromTM) { if(s.getHealthId()!= null) { CareContextEntity
				 * target = CareContextEntity.builder().build(); BeanUtils.copyProperties(s,
				 * target); entityList.add(target); }
				 * 
				 * } careContextRepository.saveAll(entityList);
				 */

			} catch (Exception e) {
				logger.info("Error in On-discover {} ", e);
				result = new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}

		} else {
			onDiscoverDTO.setError(ErrorDTO.builder().code("1000").message("No Care Context Availabe").build());
			try {
				uri = new URI(url);
				String encryptedString = new Gson().toJson(onDiscoverDTO);
				HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
						commonHeadersUtil.getHeadersWithXCmIdFromServer());
				result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			} catch (Exception e1) {
				logger.info("Error in on-discover health id missing  {} ", e1);
				result = new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		}
		logger.info("Request Receives for Discover ends");

		return result;

	}

	private OnDiscoverDTO prepareDummyData(DiscoverRequestDTO discoverRequestDTO) {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		format1.setTimeZone(TimeZone.getTimeZone("UTC"));
		List<CareContextDTO> careContextDTOList = new ArrayList<>();
		careContextDTOList.add(
				CareContextDTO.builder().display("18-03-2022 Clinic Visit").referenceNumber("CARE_CNTX_11688").build());

		return OnDiscoverDTO.builder().requestId(UUID.randomUUID().toString()).timestamp(format1.format(new Date()))
				.resp(RespDTO.builder().requestId(discoverRequestDTO.getRequestId()).build())
				.transactionId(discoverRequestDTO.getTransactionId())
				.patient(OnDiscoverPatientDTO.builder().referenceNumber("PAT_11_688").display("Rahul")
						.matchedBy(Arrays.asList("MOBILE")).careContexts(careContextDTOList).build())
				.build();
	}

	/*
	 * demo method
	 */
	public ResponseEntity<String> discoverDemo(DiscoverRequestDTO discoverRequestDTO) {
		logger.info("Request Receives for Discover starts ");
		String url = onDiscover;
		ResponseEntity<String> result = null;
		URI uri;

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(prepareDummyData(discoverRequestDTO));

			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
					commonHeadersUtil.getHeadersWithXCmIdFromServer());
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

		} catch (Exception e) {
			logger.info("Error in On-discover {} ", e);
		}
		logger.info("Request Receives for Discover ends ");
		return result;
	}
}
