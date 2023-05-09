package com.nsdl.ndhm.service.impl;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nsdl.ndhm.constant.AppConstant;
import com.nsdl.ndhm.dto.CareContextDTO;
import com.nsdl.ndhm.dto.ConfirmationDTO;
import com.nsdl.ndhm.dto.DisLinkCareContextDTO;
import com.nsdl.ndhm.dto.DisLinkLinkDTO;
import com.nsdl.ndhm.dto.DisLinkMetaDto;
import com.nsdl.ndhm.dto.DisLinkOnInitResponseDTO;
import com.nsdl.ndhm.dto.DisLinkingInitReqDTO;
import com.nsdl.ndhm.dto.ErrorDTO;
import com.nsdl.ndhm.dto.LinkConfirmDTO;
import com.nsdl.ndhm.dto.MainRequestDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.OTPResponseDTO;
import com.nsdl.ndhm.dto.OnAddContextResponseDTO;
import com.nsdl.ndhm.dto.OnLinkConfirmDTO;
import com.nsdl.ndhm.dto.OtpRequestDTO;
import com.nsdl.ndhm.dto.RespDTO;
import com.nsdl.ndhm.dto.StatusDTO;
import com.nsdl.ndhm.dto.VerifyOTPRequestDTO;
import com.nsdl.ndhm.dto.link.PatientDTO;
import com.nsdl.ndhm.entity.AddContextEntity;
import com.nsdl.ndhm.entity.DisLinkInitEntity;
import com.nsdl.ndhm.entity.DisLinkedCareContextEntity;
import com.nsdl.ndhm.entity.PatientLinkedCareContext;
import com.nsdl.ndhm.logger.LoggingClientInfo;
import com.nsdl.ndhm.repository.AddContextRepository;
import com.nsdl.ndhm.repository.CareContextRepository;
import com.nsdl.ndhm.repository.DisLinkInitRepository;
import com.nsdl.ndhm.repository.DisLinkedCareContextRepository;
import com.nsdl.ndhm.repository.PatientLinkedCareContextsRepository;
import com.nsdl.ndhm.service.LinkCallbackService;
import com.nsdl.ndhm.service.SmsService;
import com.nsdl.ndhm.utility.CommonHeadersUtil;
import com.nsdl.ndhm.utility.GenerateOTPUtil;
import com.nsdl.ndhm.utility.RestCallUtil;

@Service
@LoggingClientInfo
public class LinkCallbackServiceImpl implements LinkCallbackService {
	private static final Logger logger = LoggerFactory.getLogger(LinkCallbackServiceImpl.class);

	@Autowired
	AddContextRepository addContextRepository;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EntityManager em;

	@Autowired
	CareContextRepository careContextRepository;

	@Autowired
	DisLinkInitRepository disLinkInitRepository;

	@Autowired
	DisLinkedCareContextRepository disLinkedCareContextRepository;

	@Autowired
	PatientLinkedCareContextsRepository patientLinkedCareContextsRepository;

	@Autowired
	GenerateOTPUtil generateOTPUtil;

	@Autowired
	SmsService smsService;

	@Value("${ndhm.discover.oninit}")
	String oninit;

	@Value("${ndhm.discover.linkOnConfirm}")
	String linkOnConfirm;

	@Value("${OTP_GENERATE_URL}")
	private String generateOtpURL;

	@Value("${OTP_VERIFY_URL}")
	private String verifyOtpURL;

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	@Override
	public void updateOnAddContext(OnAddContextResponseDTO addContextResponseDTO) {
		logger.info("Update add context In Service Starts");
		ObjectMapper mapper = new ObjectMapper();

		String json = "";
		try {
			json = mapper.writeValueAsString(addContextResponseDTO);
		} catch (JsonProcessingException e) {
			logger.error("Error in updateInit {} ", e);
		}
		AddContextEntity authInitEntity = AddContextEntity.builder()
				.requestId(addContextResponseDTO.getResp().getRequestId())
				.timestamp(addContextResponseDTO.getTimestamp()).status(true).response(json).build();
		try {
			addContextRepository.save(authInitEntity);
		} catch (Exception e) {
			logger.error("Error in Update add context {} ", e);

		}
		logger.info("Update add context In Service ends");

	}

	private DisLinkOnInitResponseDTO prepareDummyData(DisLinkingInitReqDTO disLinkingInitReqDTO) {

		SimpleDateFormat format1 = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		format1.setTimeZone(TimeZone.getTimeZone("UTC"));
		return DisLinkOnInitResponseDTO.builder().requestId(UUID.randomUUID().toString())
				.timestamp(format1.format(new Date())).transactionId(disLinkingInitReqDTO.getTransactionId())
				.resp(RespDTO.builder().requestId(disLinkingInitReqDTO.getRequestId()).build())
				.link(DisLinkLinkDTO.builder().authenticationType("DIRECT").referenceNumber("LINK_REF_67788")
						.meta(DisLinkMetaDto.builder().communicationMedium("MOBILE").communicationHint("MOBILE OTP")
								.communicationExpiry(
										new Date(Calendar.getInstance().getTimeInMillis() + (2 * 60 * 1000)).toString())
								.build())
						.build())
				.build();
	}

	/*
	 * discover and link init method
	 */
	public ResponseEntity<String> discoverInitDemo(DisLinkingInitReqDTO disLinkingInitReqDTO) {
		logger.info("Request Receives for Discover init Starts ");
		String url = oninit;
		ResponseEntity<String> result = null;
		URI uri;

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(prepareDummyData(disLinkingInitReqDTO));

			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
					commonHeadersUtil.getHeadersWithXCmIdFromServer());

			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			/*
			 * logger.info("discover Response with header {}", requestEntity);
			 * logger.info("result {}", result);
			 */
		} catch (Exception e) {
			logger.info("Error in On-discover {} ", e);
		}
		logger.info("Request Receives for Discover init ends ");
		return result;
	}

	private DisLinkInitEntity generateLinkRefNo(DisLinkingInitReqDTO disLinkingInitReqDTO) {
		SimpleDateFormat format1 = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		format1.setTimeZone(TimeZone.getTimeZone("UTC"));
		DisLinkInitEntity disLinkInitEntity = DisLinkInitEntity.builder().authModes("DIRECT")
				.patientReferenceNo(disLinkingInitReqDTO.getPatient().getReferenceNumber())
				.patientId(disLinkingInitReqDTO.getPatient().getId())
				.commnExpire(format1.format(new Date(Calendar.getInstance().getTimeInMillis() + (5 * 60 * 1000))))
				.commnMedium("MOBILE").otp(generateOTPUtil.otp(6)).requestId(disLinkingInitReqDTO.getRequestId())
				.timestamp(disLinkingInitReqDTO.getTimestamp()).transactionId(disLinkingInitReqDTO.getTransactionId())
				.build();

		List<DisLinkedCareContextEntity> disLinkedCareContextEntityList = new ArrayList<>();
		for (DisLinkCareContextDTO s : disLinkingInitReqDTO.getPatient().getCareContexts()) {
			disLinkedCareContextEntityList.add(DisLinkedCareContextEntity.builder()
					.careContextReferenceNo(s.getReferenceNumber()).disLinkInitEntity(disLinkInitEntity).build());
		}
		disLinkInitEntity = disLinkInitRepository.save(disLinkInitEntity);
		disLinkedCareContextRepository.saveAll(disLinkedCareContextEntityList);
		return disLinkInitEntity;
	}

	/*
	 * discover and link init method
	 */
	public ResponseEntity<String> discoverInit(DisLinkingInitReqDTO disLinkingInitReqDTO) throws Exception {
		logger.info("Request Receives for Discover init Starts ");
		String url = oninit;
		ResponseEntity<String> result = null;
		URI uri;
		SimpleDateFormat format1 = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		format1.setTimeZone(TimeZone.getTimeZone("UTC"));
		DisLinkInitEntity disLinkInitEntity = null;
		try {
			disLinkInitEntity = generateLinkRefNo(disLinkingInitReqDTO);
		} catch (Exception e) {
			disLinkInitEntity = null;
			logger.error("Error While fetching details from Database {}", e);
		}

		DisLinkOnInitResponseDTO disLinkOnInitResponseDTO = DisLinkOnInitResponseDTO.builder()
				.requestId(UUID.randomUUID().toString()).timestamp(format1.format(new Date()))
				.transactionId(disLinkingInitReqDTO.getTransactionId())
				.resp(RespDTO.builder().requestId(disLinkingInitReqDTO.getRequestId()).build()).build();

		if (disLinkInitEntity != null) {
			MainResponseDTO<StatusDTO> statusDTO = sendOTP(disLinkInitEntity);
			DisLinkMetaDto disLinkMetaDto = DisLinkMetaDto.builder()
					.communicationExpiry(disLinkInitEntity.getCommnExpire())
					.communicationMedium(disLinkInitEntity.getCommnMedium()).build();
			if (statusDTO != null && statusDTO.getResponse() != null) {
				disLinkMetaDto.setCommunicationHint(statusDTO.getResponse().getMessage());
			}

			disLinkOnInitResponseDTO.setLink(DisLinkLinkDTO.builder().authenticationType("DIRECT")
					.referenceNumber(disLinkInitEntity.getLinkReferenceNo()).meta(disLinkMetaDto).build());
			try {
				uri = new URI(url);
				String encryptedString = new Gson().toJson(disLinkOnInitResponseDTO);
				logger.info("encryptedString onInit {} ", encryptedString);
				HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
						commonHeadersUtil.getHeadersWithXCmIdFromServer());
				result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
				logger.info("result {}", result);

			} catch (Exception e) {
				logger.info("Error in On-discover {} ", e);
			}
		} else {
			disLinkOnInitResponseDTO
					.setError(ErrorDTO.builder().code("1000").message("Please Provide Helath Id").build());
			try {
				uri = new URI(url);
				String encryptedString = new Gson().toJson(disLinkOnInitResponseDTO);
				HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
						commonHeadersUtil.getHeadersWithXCmIdFromServer());
				result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			} catch (Exception e1) {
				logger.info("Error in on-discover health id missing  {} ", e1);
				result = new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		}
		return result;
	}

	private MainResponseDTO<StatusDTO> sendOTP(DisLinkInitEntity disLinkInitEntity) throws Exception {
		logger.info("sendOTP method Starts {} ", disLinkInitEntity);
		String mobileNo = careContextRepository.getMobileNoByPatientId(disLinkInitEntity.getPatientId());

		MainRequestDTO<OtpRequestDTO> mainRequest = new MainRequestDTO<>();
		OtpRequestDTO otpRequestDTO = new OtpRequestDTO();
		otpRequestDTO.setMobileNo(mobileNo);
		otpRequestDTO.setUserId(disLinkInitEntity.getLinkReferenceNo());
		otpRequestDTO.setOtpFor(AppConstant.OTP_FOR);
		otpRequestDTO.setOtpGenerateTpye(AppConstant.OTP_GENERATION_TYPE);
		otpRequestDTO.setSendType(AppConstant.OTP_SEND_TYPE);
		MainResponseDTO<StatusDTO> message = null;
		mainRequest.setRequest(otpRequestDTO);
		ResponseEntity<?> response = null;
		try {
			response = RestCallUtil.postApiRequest(generateOtpURL, mainRequest, String.class);
			message = new Gson().fromJson(response.getBody().toString(), new TypeToken<MainResponseDTO<StatusDTO>>() {
			}.getType());

			if (message.getResponse().getMessage().contentEquals("Yes")) {
				message.getResponse().setMessage(mobileNo);
			}
		} catch (Exception e) {
			logger.error(DATE_FORMAT);
		}

		logger.info("sendOTP method Ends");
		return message;
	}

	private MainResponseDTO<OTPResponseDTO> verifyOTP(String userId, String otp) throws Exception {
		logger.info("Request received for verifying OTP");
		MainRequestDTO<VerifyOTPRequestDTO> mainRequest = new MainRequestDTO<>();
		ResponseEntity<?> result = null;
		VerifyOTPRequestDTO verifyOTPRequest = new VerifyOTPRequestDTO();
		verifyOTPRequest.setUserId(userId);
		verifyOTPRequest.setMobileOTP(otp);
		verifyOTPRequest.setUserRole(AppConstant.USER_ROLE);

		mainRequest.setRequest(verifyOTPRequest);
		logger.info("prepare otpRequest to give REST-Call to OTP manager service to verify OTP");
		result = RestCallUtil.postApiRequest(verifyOtpURL, mainRequest, String.class);
		MainResponseDTO<OTPResponseDTO> message = new Gson().fromJson(result.getBody().toString(),
				new TypeToken<MainResponseDTO<OTPResponseDTO>>() {
				}.getType());

		logger.info("Called verify OTP service: status is {} ", message);
		return message;
	}

	/*
	 * Link on-confirm method
	 */
	@Override
	public ResponseEntity<String> linkOnConfirm(LinkConfirmDTO linkConfirmDTO) {
		logger.info("LinkOnConfirm In Service Starts");

		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String uuid = UUID.randomUUID().toString();

		String url = linkOnConfirm;
		ResponseEntity<String> result = null;
		URI uri;

		OnLinkConfirmDTO onLinkConfirmDTO = OnLinkConfirmDTO.builder().requestId(uuid)
				.timestamp(formatter.format(new Date()))
				.resp(RespDTO.builder().requestId(linkConfirmDTO.getRequestId()).build()).build();

		ConfirmationDTO confirmationDTO = linkConfirmDTO.getConfirmation();

		if (confirmationDTO != null && confirmationDTO.getLinkRefNumber() != null
				&& confirmationDTO.getToken() != null) {

			onLinkConfirmDTO.setPatient(PatientDTO.builder().referenceNumber("PAT_11_688").display("Rahul")

					.careContexts(Arrays.asList(

							CareContextDTO.builder().referenceNumber("LINK_REF_67788")
									.display("18-03-2022 Clinic Visit")

									.build()))
					.build());

		} else {
			onLinkConfirmDTO.setError(ErrorDTO.builder().code("1000").message("linkRefNumber is not received").build());
		}

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(onLinkConfirmDTO);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
					commonHeadersUtil.getHeadersWithXCmIdFromServer());
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		} catch (Exception e) {
			logger.error("Error is in LinkOnConfirm {}", e.getMessage());
		}
		logger.info("LinkOnConfirm In Service ends");
		return result;
	}

	/*
	 * Link on-confirm method
	 */
	@Override
	public ResponseEntity<String> linkOnConfirmActual(LinkConfirmDTO linkConfirmDTO) {
		logger.info("LinkOnConfirm In Service Starts");

		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String uuid = UUID.randomUUID().toString();

		String url = linkOnConfirm;
		ResponseEntity<String> result = null;
		URI uri;

		OnLinkConfirmDTO onLinkConfirmDTO = OnLinkConfirmDTO.builder().requestId(uuid)
				.timestamp(formatter.format(new Date()))
				.resp(RespDTO.builder().requestId(linkConfirmDTO.getRequestId()).build()).build();
		OTPResponseDTO otpValidationStatusDTO = validateOtp(linkConfirmDTO);

		logger.info("validateion status {} ", otpValidationStatusDTO);
		if (otpValidationStatusDTO.getMessage().contentEquals(AppConstant.VERIFIED)) {
			if (linkConfirmDTO.getConfirmation() != null
					&& linkConfirmDTO.getConfirmation().getLinkRefNumber() != null) {
				onLinkConfirmDTO.setPatient(fetchLinkedCareContextDetails(linkConfirmDTO.getConfirmation()));

			} else {
				onLinkConfirmDTO
						.setError(ErrorDTO.builder().code("1000").message("linkRefNumber is not received").build());
			}
		} else {
			onLinkConfirmDTO.setError(ErrorDTO.builder().code("1001").message("Entered Wrong OTP").build());
		}

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(onLinkConfirmDTO);
			logger.info("encryptedString onConfirm {}", encryptedString);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
					commonHeadersUtil.getHeadersWithXCmIdFromServer());
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			if (result != null && result.getStatusCode().is2xxSuccessful()) {

				PatientDTO patient = onLinkConfirmDTO.getPatient();
				for (CareContextDTO s : patient.getCareContexts()) {
					if (patientLinkedCareContextsRepository.findByCareContextId(s.getReferenceNumber()) == null) {
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
		} catch (Exception e) {
			logger.error("Error is in LinkOnConfirm {}", e.getMessage());
		}
		logger.info("LinkOnConfirm In Service ends");
		return result;
	}

	private PatientDTO fetchLinkedCareContextDetails(ConfirmationDTO confirmation) {
		logger.info("fetchLinkedCareContextDetails method Starts");
		PatientDTO patientDTO = PatientDTO.builder().build();
		List<CareContextDTO> careContexts = new ArrayList<>();

		Query query = em.createNamedQuery("getCareContextDetails").setParameter(1, confirmation.getLinkRefNumber());
		query.getResultList();
		for (Object a : query.getResultList()) {
			String s = a.toString().replaceAll("[()]", "");
			String[] datas = s.split(",");
			if (datas != null && datas.length >= 4) {
				patientDTO.setDisplay(datas[1]);
				patientDTO.setReferenceNumber(datas[0]);
				careContexts.add(CareContextDTO.builder().referenceNumber(datas[2])
						.display(datas[3].replaceAll("\"", "")).build());
			}

		}
		patientDTO.setCareContexts(careContexts);
		logger.info("fetchLinkedCareContextDetails method Ends");
		return patientDTO;
	}

	public OTPResponseDTO validateOtp(LinkConfirmDTO linkConfirmDTO) {
		logger.info("validateOtp method Starts {}");

		MainResponseDTO<OTPResponseDTO> status = null;

		try {
			status = verifyOTP(linkConfirmDTO.getConfirmation().getLinkRefNumber(),
					linkConfirmDTO.getConfirmation().getToken());
			logger.info("status {}", status);
		} catch (Exception e) {
			OTPResponseDTO response = new OTPResponseDTO();
			response.setMessage("Failed");
			response.setDescription("Invalid OTP");
			status.setResponse(response);
			logger.info("status {}", e);
		}
		logger.info("validateOtp method Ends");
		return status.getResponse();
	}

}
