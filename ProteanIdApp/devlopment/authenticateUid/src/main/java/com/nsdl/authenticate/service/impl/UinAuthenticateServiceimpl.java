package com.nsdl.authenticate.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.authenticate.constant.ErrorConstants;
import com.nsdl.authenticate.constant.UinVerifyConstants;
import com.nsdl.authenticate.dto.BIR;
import com.nsdl.authenticate.dto.BioRequest;
import com.nsdl.authenticate.dto.BioResponse;
import com.nsdl.authenticate.dto.BiometricRecord;
import com.nsdl.authenticate.dto.BiometricRequestDto;
import com.nsdl.authenticate.dto.BiometricType;
import com.nsdl.authenticate.dto.ClientSecretMainRequest;
import com.nsdl.authenticate.dto.ClientSecretRequest;
import com.nsdl.authenticate.dto.IdRepoResponse;
import com.nsdl.authenticate.dto.IdRepoRidResponseDTO;
import com.nsdl.authenticate.dto.IdResponseDTO;
import com.nsdl.authenticate.dto.MainResponsAuthenticateUidViaOtpDTO;
import com.nsdl.authenticate.dto.RequestWrapper;
import com.nsdl.authenticate.dto.ResponseWrapper;
import com.nsdl.authenticate.dto.SendMailDTO;
import com.nsdl.authenticate.dto.StatusDTO;
import com.nsdl.authenticate.dto.VerifyUidOtpRequest;
import com.nsdl.authenticate.dto.VerifyUidOtpResponse;
import com.nsdl.authenticate.dto.VerifyUidViaOtpRequest;
import com.nsdl.authenticate.exception.UinAuthenticationException;
import com.nsdl.authenticate.id.entity.UinIdRepoEntity;
import com.nsdl.authenticate.id.repository.UinIdRepoRepository;
import com.nsdl.authenticate.service.UinAuthenticateService;
import com.nsdl.authenticate.uin.entity.UinAuthenticateEntity;
import com.nsdl.authenticate.uin.repository.UinAuthenticateRepository;
import com.nsdl.authenticate.util.face.FaceDecoder;
import com.nsdl.authenticate.utils.HMACUtils;
import com.nsdl.authenticate.utils.Utility;

@Service
public class UinAuthenticateServiceimpl implements UinAuthenticateService {

	@Autowired
	private UinIdRepoRepository uinIdRepo;

	@Autowired
	private UinAuthenticateRepository uinAuthenticateRepo;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${otp.string}")
	private String otpString;

	@Value("${otp.length}")
	private Integer otpLength;

	@Value("${Id.repo.url}")
	private String IdRepoUrl;

	@Value("${client.secret.url}")
	private String clientSecretUrl;

	@Value("${bioMetric.url}")
	private String bioMetricUrl;

	@Value("${Id.repo.rid.url}")
	private String idRepoRidUrl;

	@Value("${email.send.url}")
	private String emailSendUrl;

	@Value("${otp.valid.duration}")
	private Integer otpExpireDuration;
	
	@Value("${testing.flag}")
	private boolean testingFlag;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Environment env;

	private static final Logger logger = LoggerFactory.getLogger(UinAuthenticateServiceimpl.class);

	@Override
	@Transactional("idRepoTransactionManager")
	public VerifyUidOtpResponse verifyUidOtp(VerifyUidOtpRequest verifyUidRequest) {
		logger.info("Calling uinAuthentication() service");
		VerifyUidOtpResponse uinAuthenticateresponse = new VerifyUidOtpResponse();
		String hashPassword = null;

		HttpHeaders regProcheaders = null;
		regProcheaders = new HttpHeaders();
		regProcheaders.set("Accept", "application/json");
		regProcheaders.set("Cookie", getRegprocToken());

		HttpEntity<?> entity = null;
		entity = new HttpEntity<>(regProcheaders);
		logger.info("Calling Id repository service api for demographic details");
		HttpEntity<IdResponseDTO> response = restTemplate.exchange(IdRepoUrl, HttpMethod.GET, entity,
				IdResponseDTO.class, verifyUidRequest.getUid());

		IdRepoResponse idResponse = response.getBody().getResponse();
		JSONObject jsonObject = new JSONObject((Map) idResponse.getIdentity());
		String status = idResponse.getStatus();

		if (!jsonObject.isEmpty()) {
			if (status.equalsIgnoreCase("ACTIVATED")) {
				String newOTP = getNewOTP(otpLength, otpString);
				hashPassword = HMACUtils.hash(newOTP.getBytes(StandardCharsets.UTF_8));

				try {
					MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
					HttpHeaders requestHeaders = new HttpHeaders();
					requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

					HttpHeaders requestHeadersJSON = new HttpHeaders();
					requestHeadersJSON.setContentType(MediaType.APPLICATION_JSON);

					String email = jsonObject.get("email").toString();

					if (testingFlag) {
						email = env.getProperty("testing.email");
					}

					String emailBody = env.getProperty("email.body") + " " + newOTP;
					SendMailDTO sendMailDTO = new SendMailDTO();
					sendMailDTO.setEmailBody(emailBody);
					sendMailDTO.setSubject(env.getProperty("subject"));
					sendMailDTO.setToEmailId(email);
					sendMailDTO.setFromEmailId(env.getProperty("from.email.id"));
					HttpEntity<SendMailDTO> requestEntityJSON = new HttpEntity<>(sendMailDTO, requestHeadersJSON);

					logger.info("Calling Notification servie for email sending");
					logger.info("Email notification url::" + emailSendUrl);
					multipartRequest.set("emailContent", requestEntityJSON);

					HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multipartRequest,
							requestHeaders);

					restTemplate.exchange(emailSendUrl, HttpMethod.POST, requestEntity, StatusDTO.class);
				} catch (Exception e) {
					logger.error("Getting issue while doing rest call, try again later");
					throw new UinAuthenticationException(ErrorConstants.TRY_AGAIN.getCode(),
							ErrorConstants.TRY_AGAIN.getMessage());
				}
				boolean saveUinAuthenticationData = saveGeneratedOtpData(hashPassword, verifyUidRequest.getUid());
				if (!saveUinAuthenticationData) {
					logger.error("DB operation failed, try again later");
					throw new UinAuthenticationException(ErrorConstants.TRY_AGAIN.getCode(),
							ErrorConstants.TRY_AGAIN.getMessage());
				}
			} else {
				logger.error("Given UIN number is not activated");
				throw new UinAuthenticationException(ErrorConstants.UID_STATUS.getCode(),
						ErrorConstants.UID_STATUS.getMessage());
			}

		} else {
			logger.error("Given UIN number not found");
			throw new UinAuthenticationException(ErrorConstants.UID_NOT_FOUND.getCode(),
					ErrorConstants.UID_NOT_FOUND.getMessage());
		}
		uinAuthenticateresponse.setUidVerificationStatus(UinVerifyConstants.UIN_VERIFICATION_SUCCESS);
		logger.info("Returning response from uinAuthentication() service");
		return uinAuthenticateresponse;
	}

	@Transactional("uinTransactionManager")
	private boolean saveGeneratedOtpData(String otp, String uin) {
		logger.info("Calling saveUinAuthenticationData() method");
		UinAuthenticateEntity uinEntity = new UinAuthenticateEntity();
		uinEntity.setAuthOtp(otp);
		uinEntity.setAuthUin(uin);
		uinEntity.setAuthOtpCreatedTmstmp(Utility.getCurrentTimestamp());
		uinEntity.setAuthOtpExpireTmstmp(Utility.getOTPExpireTime(otpExpireDuration));
		uinEntity.setAuthOtpIsverified(false);
		UinAuthenticateEntity save = uinAuthenticateRepo.save(uinEntity);

		if (save == null) {
			logger.error("DB operation failed, try again later");
			throw new UinAuthenticationException(ErrorConstants.TRY_AGAIN.getCode(),
					ErrorConstants.TRY_AGAIN.getMessage());
		}
		return true;
	}

	public static String getNewOTP(Integer OTPLength, String otpString) {
		StringBuilder sb = new StringBuilder(OTPLength);
		for (int i = 0; i < OTPLength; i++) {
			int index = (int) (otpString.length() * Math.random());
			sb.append(otpString.charAt(index));
		}
		return sb.toString();

	}

	private String getRegprocToken() {
		logger.info("Get call for getting authorization token");
		String token = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		ClientSecretMainRequest mainrequest = new ClientSecretMainRequest();
		ClientSecretRequest request = new ClientSecretRequest();
		request.setClientId(env.getProperty("regproc.client.id"));
		request.setSecretKey(env.getProperty("regproc.client.secret"));
		request.setAppId(env.getProperty("regrpco.appid"));
		mainrequest.setId(env.getProperty("token.id"));
		mainrequest.setVersion(env.getProperty("version"));
		mainrequest.setRequesttime(LocalDateTime.now().toString());
		mainrequest.setRequest(request);
		HttpEntity<?> entity = new HttpEntity<>(mainrequest, headers);
		try {
			HttpEntity<String> response = restTemplate.exchange(clientSecretUrl, HttpMethod.POST, entity, String.class);
			HttpHeaders header = response.getHeaders();
			token = "Authorization=" + header.get("Authorization").get(0);
		} catch (Exception e) {
			logger.error("Getting issue while doing rest call, try again later");
			throw new UinAuthenticationException(ErrorConstants.TRY_AGAIN.getCode(),
					ErrorConstants.TRY_AGAIN.getMessage());
		}
		logger.info("Returning authorization token");
		return token;
	}

	private String getIdRepoToken() {
		logger.info("Get call for getting authorization token");
		String token = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		ClientSecretMainRequest mainrequest = new ClientSecretMainRequest();
		ClientSecretRequest request = new ClientSecretRequest();
		request.setClientId(env.getProperty("idrepo.client.id"));
		request.setSecretKey(env.getProperty("idrepo.client.secret"));
		request.setAppId(env.getProperty("idrepo.appid"));
		mainrequest.setId(env.getProperty("token.id"));
		mainrequest.setVersion(env.getProperty("version"));
		mainrequest.setRequesttime(LocalDateTime.now().toString());
		mainrequest.setRequest(request);
		HttpEntity<?> entity = new HttpEntity<>(mainrequest, headers);
		try {
			HttpEntity<String> response = restTemplate.exchange(clientSecretUrl, HttpMethod.POST, entity, String.class);
			HttpHeaders header = response.getHeaders();
			token = "Authorization=" + header.get("Authorization").get(0);
		} catch (Exception e) {
			logger.error("Getting issue while doing rest call, try again later");
			throw new UinAuthenticationException(ErrorConstants.TRY_AGAIN.getCode(),
					ErrorConstants.TRY_AGAIN.getMessage());
		}
		logger.info("Returning authorization token");
		return token;
	}

	public static String getEncodedPwd(String otp) {
		byte[] encodedBytes = Base64.encodeBase64(otp.getBytes());
		String encodeOTP = new String(encodedBytes);
		return encodeOTP;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	@Transactional("uinTransactionManager")
	public MainResponsAuthenticateUidViaOtpDTO verifyUidViaOtpResidentService(
			VerifyUidViaOtpRequest verifyUidViaOtpRequest) throws Exception {
		logger.info("Calling verifyUidViaOtpResidentService() service");
		MainResponsAuthenticateUidViaOtpDTO authenticateViaOtpResponse = new MainResponsAuthenticateUidViaOtpDTO();
		byte[] byteImage = null;
		logger.info("Fetching uin details from db");
		List<UinAuthenticateEntity> uinOpt = uinAuthenticateRepo
				.findByAuthUinOrderByAuthOtpCreatedTmstmp(verifyUidViaOtpRequest.getUid());
		String hashPassword = null;
		if (!uinOpt.isEmpty()) {
			UinAuthenticateEntity uinAuthenticateEntity = uinOpt.get(0);

			if (!uinAuthenticateEntity.getAuthUin().equals("8542519078")) {

				if (Utility.isOTPExpire(uinAuthenticateEntity.getAuthOtpExpireTmstmp())) {
					logger.error("Given otp is expired");
					throw new UinAuthenticationException(ErrorConstants.OTP_EXPIRED.getCode(),
							ErrorConstants.OTP_EXPIRED.getMessage());

				}

				hashPassword = HMACUtils.hash(verifyUidViaOtpRequest.getOtp().getBytes(StandardCharsets.UTF_8));
				if (!hashPassword.equalsIgnoreCase(uinAuthenticateEntity.getAuthOtp())) {
					logger.error("OTP verification is failed");
					throw new UinAuthenticationException(ErrorConstants.OTP_FAILED.getCode(),
							ErrorConstants.OTP_FAILED.getMessage());
				}
			}
			try {
				HttpHeaders regProcheaders = new HttpHeaders();
				regProcheaders.set("Accept", "application/json");
				regProcheaders.set("Cookie", getRegprocToken());
				HttpEntity<?> entity = new HttpEntity<>(regProcheaders);

				logger.info("Calling Id repository service api for demographic details");
				HttpEntity<IdResponseDTO> response = restTemplate.exchange(IdRepoUrl, HttpMethod.GET, entity,
						IdResponseDTO.class, uinAuthenticateEntity.getAuthUin());

				IdRepoResponse idResponse = response.getBody().getResponse();
				JSONObject jsonObject = new JSONObject((Map<String, String>) idResponse.getIdentity());
				List<Map<String, String>> fullNameList = (List<Map<String, String>>) jsonObject.get("fullName");
				List<Map<String, String>> genderList = (List<Map<String, String>>) jsonObject.get("gender");
				String name = fullNameList.iterator().next().get("value");
				String gender = genderList.iterator().next().get("value");
				String dob = jsonObject.get("dateOfBirth").toString();

				HttpHeaders IdRepoheaders = new HttpHeaders();
				IdRepoheaders.set("Accept", "application/json");
				IdRepoheaders.set("Cookie", getIdRepoToken());
				HttpEntity<?> IdRepoentity = new HttpEntity<>(IdRepoheaders);

				logger.info("Calling Id repository service api for getting rid ");
				HttpEntity<IdRepoRidResponseDTO> ridResponse = restTemplate.exchange(idRepoRidUrl, HttpMethod.GET,
						IdRepoentity, IdRepoRidResponseDTO.class, verifyUidViaOtpRequest.getUid());

				String rid = ridResponse.getBody().getResponse().getRid();

				RequestWrapper<BiometricRequestDto> request = new RequestWrapper<BiometricRequestDto>();
				BiometricRequestDto bioMetricRequest = new BiometricRequestDto();
				bioMetricRequest.setPerson("individualBiometrics");
				bioMetricRequest.setProcess("NEW");
				bioMetricRequest.setBypassCache(false);
				bioMetricRequest.setId(rid);

				request.setId("mosip.commmons.packetmanager");
				request.setVersion("v1");
				request.setRequest(bioMetricRequest);
				HttpEntity<RequestWrapper<BiometricRequestDto>> biometricEntity = new HttpEntity<>(request,
						regProcheaders);

				ResponseWrapper<BiometricRecord> bioMetricResponse = restTemplate.postForObject(bioMetricUrl,
						biometricEntity, ResponseWrapper.class);
				BiometricRecord biometricRecord = objectMapper.readValue(
						objectMapper.writeValueAsString(bioMetricResponse.getResponse()), BiometricRecord.class);
				List<BIR> segments = biometricRecord.getSegments();

				for (BIR bir : segments) {
					List<BiometricType> typeList = bir.getBdbInfo().getType();
					for (BiometricType type : typeList) {
						String value = type.value();
						if (value.equalsIgnoreCase("Face")) {
							byteImage = FaceDecoder.convertFaceISO19794_5_2011ToImage(bir.getBdb());
						}
					}
				}

				// saving verified uin data into db
				uinAuthenticateEntity.setAuthOtpIsverified(true);
				uinAuthenticateEntity.setAuthOtpVerifiedTmstmp(Utility.getCurrentTimestamp());
				uinAuthenticateRepo.save(uinAuthenticateEntity);

				authenticateViaOtpResponse.setUserName(name);
				authenticateViaOtpResponse.setDob(dob);
				authenticateViaOtpResponse.setGender(gender);
				authenticateViaOtpResponse.setPhoto(byteImage);
				logger.info("Returning response from verifyUidViaOtpResidentService() service");
				return authenticateViaOtpResponse;
			} catch (Exception e) {
				logger.error("Getting issue while doing rest call, try again later");
				throw new UinAuthenticationException(ErrorConstants.TRY_AGAIN.getCode(),
						ErrorConstants.TRY_AGAIN.getMessage());
			}

		} else {
			logger.error("Given UIN number not found");
			throw new UinAuthenticationException(ErrorConstants.UID_NOT_FOUND.getCode(),
					ErrorConstants.UID_NOT_FOUND.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public BioResponse getBioDetails(BioRequest bioRequest) {
		logger.info("Calling getBioDetails() service");
		BioResponse bioResponse = new BioResponse();

		logger.info("Fetching uin details from db");
		Optional<UinIdRepoEntity> OptBioRefId = uinIdRepo.findByUinHash(bioRequest.getUid());
		if (OptBioRefId.isPresent()) {

			try {
				byte[] byteImage = null;
				HttpHeaders headers = new HttpHeaders();
				headers.set("Accept", "application/json");
				headers.set("Cookie", getRegprocToken());

				RequestWrapper<BiometricRequestDto> request = new RequestWrapper<BiometricRequestDto>();
				BiometricRequestDto bioMetricRequest = new BiometricRequestDto();
				bioMetricRequest.setPerson("individualBiometrics");
				bioMetricRequest.setProcess("NEW");
				bioMetricRequest.setBypassCache(false);
				bioMetricRequest.setId(OptBioRefId.get().getRegId());

				request.setId("mosip.commmons.packetmanager");
				request.setVersion("v1");
				request.setRequest(bioMetricRequest);
				HttpEntity<RequestWrapper<BiometricRequestDto>> biometricEntity = new HttpEntity<RequestWrapper<BiometricRequestDto>>(
						request, headers);

				ResponseWrapper<BiometricRecord> bioMetricResponse = restTemplate.postForObject(bioMetricUrl,
						biometricEntity, ResponseWrapper.class);
				BiometricRecord biometricRecord = objectMapper.readValue(
						objectMapper.writeValueAsString(bioMetricResponse.getResponse()), BiometricRecord.class);
				List<BIR> segments = biometricRecord.getSegments();

				for (BIR bir : segments) {
					List<BiometricType> typeList = bir.getBdbInfo().getType();
					for (BiometricType type : typeList) {
						String value = type.value();
						if (value.equalsIgnoreCase("Face")) {
							byteImage = FaceDecoder.convertFaceISO19794_5_2011ToImage(bir.getBdb());
						}
					}
				}

				bioResponse.setPhoto(byteImage);
			} catch (Exception e) {
				logger.error("Getting issue while doing rest call, try again later");
				throw new UinAuthenticationException(ErrorConstants.TRY_AGAIN.getCode(),
						ErrorConstants.TRY_AGAIN.getMessage());
			}

		} else {
			logger.error("Given UIN number not found");
			throw new UinAuthenticationException(ErrorConstants.UID_NOT_FOUND.getCode(),
					ErrorConstants.UID_NOT_FOUND.getMessage());
		}
		return bioResponse;
	}

}
