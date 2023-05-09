package com.nsdl.ndhm.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nsdl.ndhm.dto.AddContextLinkDTO;
import com.nsdl.ndhm.dto.AddContextPatientDTO;
import com.nsdl.ndhm.dto.AddContextRequestDTO;
import com.nsdl.ndhm.dto.AuthConfirmNotifyRequestDTO;
import com.nsdl.ndhm.dto.AuthInitReqDTO;
import com.nsdl.ndhm.dto.CareContextDTO;
import com.nsdl.ndhm.dto.CareContextTMDTO;
import com.nsdl.ndhm.dto.CareContextTMRequsetDTO;
import com.nsdl.ndhm.dto.IdentifierDTO;
import com.nsdl.ndhm.dto.MainRequestDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.OnConfirmRequestDTO;
import com.nsdl.ndhm.dto.OnFetchModesRequestDTO;
import com.nsdl.ndhm.entity.AuthInitEntity;
import com.nsdl.ndhm.entity.FetchModesEntity;
import com.nsdl.ndhm.entity.OnConfirmIdentifierEntity;
import com.nsdl.ndhm.entity.PatientLinkedCareContext;
import com.nsdl.ndhm.entity.SaveAuthOnConfirmRespEntity;
import com.nsdl.ndhm.logger.LoggingClientInfo;
import com.nsdl.ndhm.repository.AddContextRepository;
import com.nsdl.ndhm.repository.AuthConfirmRepository;
import com.nsdl.ndhm.repository.AuthInitRepository;
import com.nsdl.ndhm.repository.FetchModesRepository;
import com.nsdl.ndhm.repository.OnConfirmResponseRepository;
import com.nsdl.ndhm.repository.PatientLinkedCareContextsRepository;
import com.nsdl.ndhm.service.GatewayCallbackService;
import com.nsdl.ndhm.utility.CommonHeadersUtil;
import com.nsdl.ndhm.utility.CommonUtils;

@Service
@LoggingClientInfo
public class GatewayCallbackServiceImpl implements GatewayCallbackService {
	private static final Logger logger = LoggerFactory.getLogger(GatewayCallbackServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${ndhm.addContext}")
	String addContext;

	@Value("${ndhm.telemedicine.getCareContextDtls}")
	String getCareContextDtls;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Autowired
	FetchModesRepository fetchModesRepository;

	@Autowired
	AuthInitRepository authInitRepository;

	@Autowired
	AuthConfirmRepository authConfirmRepository;

	@Autowired
	OnConfirmResponseRepository onConfirmResponseRepository;

	@Autowired
	AddContextRepository addContextRepository;

	@Autowired
	PatientLinkedCareContextsRepository patientLinkedCareContextsRepository;

	/* update on fetch modes data */
	@Override
	public FetchModesEntity updateFetchModes(OnFetchModesRequestDTO onFetchModesDTO) {
		logger.info("Update Fetch modes In Service Starts");
		ObjectMapper mapper = new ObjectMapper();

		String json = "";
		try {
			json = mapper.writeValueAsString(onFetchModesDTO);
		} catch (JsonProcessingException e) {
			logger.error("Error in updateFetchModes {} ", e);
		}

		FetchModesEntity fetchModesEntity = FetchModesEntity.builder()
				.requestId(onFetchModesDTO.getResp().getRequestId()).timestamp(onFetchModesDTO.getTimestamp())
				.status(true).response(json).build();

		try {
			fetchModesEntity = fetchModesRepository.save(fetchModesEntity);
		} catch (Exception e) {
			logger.error("Error in updateFetchModes {} ", e);
			fetchModesEntity = null;
		}
		logger.info("Update Fetch modes In Service ends");
		return fetchModesEntity;
	}

	/* update on init data */
	@Override
	public AuthInitEntity updateInit(AuthInitReqDTO authInitReqDTO) {
		logger.info("Update INIT In Service Starts");
		ObjectMapper mapper = new ObjectMapper();

		String json = "";
		try {
			json = mapper.writeValueAsString(authInitReqDTO);
		} catch (JsonProcessingException e) {
			logger.error("Error in updateInit {} ", e);
		}
		AuthInitEntity authInitEntity = AuthInitEntity.builder().requestId(authInitReqDTO.getResp().getRequestId())
				.timestamp(authInitReqDTO.getTimestamp()).transactionId(authInitReqDTO.getAuth().getTransactionId())
				.status(true).expiry(authInitReqDTO.getAuth().getMeta().getExpiry()).response(json).build();
		try {
			authInitEntity = authInitRepository.save(authInitEntity);
		} catch (Exception e) {
			logger.error("Error in updateInit {} ", e);
			authInitEntity = null;
		}
		logger.info("Update Init In Service ends");
		return authInitEntity;
	}

	/* update on confirm data */
	@Transactional
	@Override
	public void updateConfirm(OnConfirmRequestDTO onConfirmRequestDTO) {
		logger.info("Update confirm  In Service Starts {} ", onConfirmRequestDTO);
		ObjectMapper mapper = new ObjectMapper();
		SaveAuthOnConfirmRespEntity responseEntity = null;
		List<OnConfirmIdentifierEntity> identifierList = new ArrayList<>();
		String json = "";
		try {
			json = mapper.writeValueAsString(onConfirmRequestDTO);
		} catch (JsonProcessingException e) {
			logger.error("Error in updateConfirm {} ", e);
		}

		if (onConfirmRequestDTO != null && onConfirmRequestDTO.getAuth() != null
				&& onConfirmRequestDTO.getAuth().getAccessToken() != null
				&& onConfirmRequestDTO.getAuth().getPatient() != null) {
			responseEntity = SaveAuthOnConfirmRespEntity.builder()
					.requestId(onConfirmRequestDTO.getResp().getRequestId())
					.timeStamp(onConfirmRequestDTO.getTimestamp()).token(onConfirmRequestDTO.getAuth().getAccessToken())
					.healthId(onConfirmRequestDTO.getAuth().getPatient().getId())
					.name(onConfirmRequestDTO.getAuth().getPatient().getName())
					.yearOfBirth(onConfirmRequestDTO.getAuth().getPatient().getYearOfBirth()).jsonResp(json)
					.expiry(onConfirmRequestDTO.getAuth().getValidity() != null
							? onConfirmRequestDTO.getAuth().getValidity().getExpiry()
							: "")
					.limits(onConfirmRequestDTO.getAuth().getValidity() != null
							? onConfirmRequestDTO.getAuth().getValidity().getLimit()
							: "")
					.perpose(onConfirmRequestDTO.getAuth().getValidity() != null
							? onConfirmRequestDTO.getAuth().getValidity().getPurpose()
							: "")
					.build();

			for (IdentifierDTO i : onConfirmRequestDTO.getAuth().getPatient().getIdentifiers()) {
				identifierList.add(OnConfirmIdentifierEntity.builder().type(i.getType()).value(i.getValue())
						.saveAuthOnConfirmRespEntity(responseEntity).build());
			}

		} else if (onConfirmRequestDTO != null && onConfirmRequestDTO.getAuth() != null
				&& onConfirmRequestDTO.getAuth().getAccessToken() == null
				&& onConfirmRequestDTO.getAuth().getPatient() != null) {
			responseEntity = SaveAuthOnConfirmRespEntity.builder()
					.requestId(onConfirmRequestDTO.getResp().getRequestId())
					.timeStamp(onConfirmRequestDTO.getTimestamp())
					.healthId(onConfirmRequestDTO.getAuth().getPatient().getId())
					.name(onConfirmRequestDTO.getAuth().getPatient().getName())
					.yearOfBirth(onConfirmRequestDTO.getAuth().getPatient().getYearOfBirth()).jsonResp(json)
					.expiry(onConfirmRequestDTO.getAuth().getValidity() != null
							? onConfirmRequestDTO.getAuth().getValidity().getExpiry()
							: "")
					.limits(onConfirmRequestDTO.getAuth().getValidity() != null
							? onConfirmRequestDTO.getAuth().getValidity().getLimit()
							: "")
					.perpose(onConfirmRequestDTO.getAuth().getValidity() != null
							? onConfirmRequestDTO.getAuth().getValidity().getPurpose()
							: "")
					.build();

			for (IdentifierDTO i : onConfirmRequestDTO.getAuth().getPatient().getIdentifiers()) {
				identifierList.add(OnConfirmIdentifierEntity.builder().type(i.getType()).value(i.getValue())
						.saveAuthOnConfirmRespEntity(responseEntity).build());
			}

		}

		try {
			if (null != onConfirmRequestDTO && onConfirmRequestDTO.getAuth() != null
					&& onConfirmRequestDTO.getAuth().getPatient() != null && null != responseEntity) {
				callAddCareContext(onConfirmRequestDTO);
				responseEntity.setIdentities(identifierList);
				SaveAuthOnConfirmRespEntity savedDetails = onConfirmResponseRepository
						.findByHealthId(responseEntity.getHealthId());
				if (savedDetails != null) {
					if (savedDetails.getToken() != null && !savedDetails.getToken().isEmpty()
							&& responseEntity.getToken() != null && !responseEntity.getToken().isEmpty()) {
						savedDetails.setToken(responseEntity.getToken());
						savedDetails.setExpiry(responseEntity.getExpiry());
						savedDetails.setLimits(responseEntity.getLimits());
						savedDetails.setPerpose(responseEntity.getPerpose());
						onConfirmResponseRepository.save(savedDetails);
					} else {
						savedDetails.setExpiry(responseEntity.getExpiry());
						savedDetails.setLimits(responseEntity.getLimits());
						savedDetails.setPerpose(responseEntity.getPerpose());
						onConfirmResponseRepository.save(savedDetails);
					}

				} else {
					onConfirmResponseRepository.save(responseEntity);
				}

			}

			if (null != onConfirmRequestDTO) {
				authConfirmRepository.updateResponseAndTokenByRequestId(json,
						onConfirmRequestDTO.getAuth().getAccessToken() != null
								? onConfirmRequestDTO.getAuth().getAccessToken()
								: "",
						onConfirmRequestDTO.getResp().getRequestId(), true);
			}

		} catch (Exception e) {
			logger.error("Error in updateConfirm {} ", e);

		}
		logger.info("Update confirm In Service ends");

	}

	private void callAddCareContext(OnConfirmRequestDTO onConfirmRequestDTO) {
		logger.info("Request Receives for Add context Confirm");
		String url = addContext;
		URI uri;
		ResponseEntity<String> result = null;
		List<CareContextDTO> careContextDTOList = new ArrayList<>();

		List<CareContextTMDTO> careContextsFromTM = getCareContextDetailsFromTM(
				onConfirmRequestDTO.getAuth().getPatient().getId());
		logger.info("Data Received from TM {}", careContextsFromTM);
		for (CareContextTMDTO b : careContextsFromTM) {

			PatientLinkedCareContext details = patientLinkedCareContextsRepository.findByCareContextId(b.careContextId);

			if (details == null) {
				careContextDTOList.add(CareContextDTO.builder().referenceNumber(b.getCareContextId())
						.display(b.getDisplayName()).build());
			}

		}
		logger.info("careContextDTOList {}", careContextDTOList);

		if (careContextDTOList != null && !careContextDTOList.isEmpty() && careContextsFromTM != null
				&& !careContextsFromTM.isEmpty()) {
			logger.info("Inside if for method call for add carecontext");
			AddContextPatientDTO patient = AddContextPatientDTO.builder()
					.referenceNumber(careContextsFromTM.get(0).getPatientId())
					.display(careContextsFromTM.get(0).getPatientName()).careContexts(careContextDTOList).build();

			AddContextRequestDTO addContextRequestDTO = AddContextRequestDTO.builder()
					.requestId(UUID.randomUUID().toString()).timestamp(CommonUtils.getTime())
					.link(AddContextLinkDTO.builder().accessToken(onConfirmRequestDTO.getAuth().getAccessToken())
							.patient(patient).build())
					.build();

			logger.info("addContextRequestDTO request {} ", addContextRequestDTO);
			try {
				if (null != addContextRequestDTO.getLink().getPatient().getCareContexts()) {
					uri = new URI(url);
					String encryptedString = new Gson().toJson(addContextRequestDTO);
					HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
							commonHeadersUtil.getHeadersWithXCmIdFromServer());
					result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
					if (result.getStatusCode().is2xxSuccessful()) {
						//callContextNotify(addContextRequestDTO, careContextsFromTM);
						for (CareContextDTO s : careContextDTOList) {
							if (patientLinkedCareContextsRepository
									.findByCareContextId(s.getReferenceNumber()) == null) {
								PatientLinkedCareContext linkedDetails = new PatientLinkedCareContext();
								linkedDetails.setCareContextId(s.getReferenceNumber());
								linkedDetails.setDisplayName(s.getDisplay());
								linkedDetails.setHealthId(patient.getDisplay());
								linkedDetails.setPatientId(patient.getDisplay());
								linkedDetails.setPatientReferenceNo(patient.getReferenceNumber());
								patientLinkedCareContextsRepository.save(linkedDetails);
							}

						}

					}
					logger.info("result after add carecontext", result);
				}

			} catch (HttpClientErrorException | HttpServerErrorException e) {

				logger.info("Error in adding care contexts {} ", e);

			} catch (Exception e) {

				logger.info("Error in adding care contexts {} ", e);
			}
		}

		logger.info("Request ends for Add context Confirm");
	}

	/*private void callContextNotify(AddContextRequestDTO addContextRequestDTO,
			List<CareContextTMDTO> careContextsFromTM) {
		logger.info("callContextNotify method started");
		String url = addContext;
		URI uri;
		ResponseEntity<String> result = null;
		AbdmRequestDTO<AddContextNotificationDTO> request = new AbdmRequestDTO<>();
		request.setRequestId(UUID.randomUUID().toString());
		request.setTimestamp(CommonUtils.getTime());
		AddContextNotificationDTO notification = AddContextNotificationDTO.builder().careContext(null).build();
	} */

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

	@Override
	public void confirmNotify(AuthConfirmNotifyRequestDTO authConfirmNotifyRequestDTO) {
		logger.info("confirmNotify method starts");
		logger.info("request {}", authConfirmNotifyRequestDTO);
		SaveAuthOnConfirmRespEntity authConfirmEntity = null;
		if (authConfirmNotifyRequestDTO.getAuth() != null
				&& authConfirmNotifyRequestDTO.getAuth().getAccessToken() != null
				&& !authConfirmNotifyRequestDTO.getAuth().getAccessToken().isEmpty()) {
			authConfirmEntity = onConfirmResponseRepository
					.findByToken(authConfirmNotifyRequestDTO.getAuth().getAccessToken());
			authConfirmEntity.setGrantStatus(authConfirmNotifyRequestDTO.getAuth().getStatus());

			onConfirmResponseRepository.save(authConfirmEntity);
		}

		logger.info("confirmNotify method ends");
	}
}
