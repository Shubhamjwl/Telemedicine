package com.nsdl.user.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.user.dto.BioRequest;
import com.nsdl.user.dto.BioResponse;
import com.nsdl.user.dto.ConsentChangeRequest;
import com.nsdl.user.dto.ConsentChangeResponse;
import com.nsdl.user.dto.MainResponseDTO;
import com.nsdl.user.dto.ServiceError;
import com.nsdl.user.dto.SubmitOtpRequest;
import com.nsdl.user.dto.SubmitOtpResponse;
import com.nsdl.user.dto.SubmitUidRequest;
import com.nsdl.user.dto.SubmitUidResponse;
import com.nsdl.user.dto.VerifyUidOtpRequest;
import com.nsdl.user.dto.VerifyUidOtpResponse;
import com.nsdl.user.dto.VerifyUidVerifyOtpRequest;
import com.nsdl.user.dto.VerifyUidVerifyOtpResponse;
import com.nsdl.user.entity.ProteanIdUserEntity;
import com.nsdl.user.exception.UserErrorConstant;
import com.nsdl.user.exception.UserException;
import com.nsdl.user.repository.UserRepository;
import com.nsdl.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository repo;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${uin.authenticate.url}")
	private String uinAuthenticateUrl;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public SubmitUidResponse submitUid(SubmitUidRequest request) {
		// TODO Auto-generated method stub

		SubmitUidResponse response = new SubmitUidResponse();
		ProteanIdUserEntity entity = repo.findByUid(Long.valueOf(request.getUid()));

		if (entity != null) {
			if (!entity.getMobileId().equalsIgnoreCase(request.getAndroidId()) && !request.getWhitelistOverrideFlag()) {
				throw new UserException(new ServiceError(UserErrorConstant.ANOTHER_MOBILEID_WHITELISTED.getErrorCode(),
						UserErrorConstant.ANOTHER_MOBILEID_WHITELISTED.getErrorMessage()));
			}
		}

		VerifyUidOtpRequest verifyRequest = new VerifyUidOtpRequest();
		verifyRequest.setUid(request.getUid());

		HttpEntity<VerifyUidOtpRequest> requestEntity = new HttpEntity<VerifyUidOtpRequest>(verifyRequest);
		ParameterizedTypeReference<MainResponseDTO<VerifyUidOtpResponse>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<VerifyUidOtpResponse>>() {
		};
		logger.info("Initiate uid verification check:" + verifyRequest.getUid());
		ResponseEntity<MainResponseDTO<VerifyUidOtpResponse>> apiResponse = null;
		try {
			apiResponse = restTemplate.exchange(uinAuthenticateUrl + "/verifyUidOtp", HttpMethod.POST, requestEntity,
					parameterizedResponse);
		} catch (HttpStatusCodeException e) {
			MainResponseDTO<VerifyUidOtpResponse> mainResponse = new MainResponseDTO<VerifyUidOtpResponse>();
			try {
				TypeReference<MainResponseDTO<VerifyUidOtpResponse>> typeRef = new TypeReference<MainResponseDTO<VerifyUidOtpResponse>>() {
				};
				mainResponse = mapper.readValue(e.getResponseBodyAsString(), typeRef);
			} catch (Exception e1) {
				throw new UserException(new ServiceError(UserErrorConstant.PARSE_ERROR.getErrorCode(),
						UserErrorConstant.PARSE_ERROR.getErrorMessage()));
			}
			throw new UserException(new ServiceError(mainResponse.getErrors().get(0).getErrorCode(),
					mainResponse.getErrors().get(0).getMessage()));
		}

		if (apiResponse == null) {
			throw new UserException(new ServiceError(UserErrorConstant.INTERNAL_SERVER_ERROR.getErrorCode(),
					UserErrorConstant.INTERNAL_SERVER_ERROR.getErrorMessage()));
		}

		response.setUidVerificationStatus(apiResponse.getBody().getResponse().getUidVerificationStatus());
		response.setWhitelistFlag(request.getWhitelistOverrideFlag());
		return response;
	}

	@Override
	public SubmitOtpResponse submitOtp(SubmitOtpRequest request) {
		// TODO Auto-generated method stub

		SubmitOtpResponse response = new SubmitOtpResponse();

		VerifyUidVerifyOtpRequest verifyRequest = new VerifyUidVerifyOtpRequest();
		verifyRequest.setUid(request.getUid());
		verifyRequest.setOtp(request.getOtp());

		HttpEntity<VerifyUidVerifyOtpRequest> requestEntity = new HttpEntity<VerifyUidVerifyOtpRequest>(verifyRequest);
		ParameterizedTypeReference<MainResponseDTO<VerifyUidVerifyOtpResponse>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<VerifyUidVerifyOtpResponse>>() {
		};
		logger.info("Initiate uid otp verification check:" + verifyRequest.getUid());
		ResponseEntity<MainResponseDTO<VerifyUidVerifyOtpResponse>> apiResponse = null;
		try {
			apiResponse = restTemplate.exchange(uinAuthenticateUrl + "/verifyUidVerifyOtp", HttpMethod.POST,
					requestEntity, parameterizedResponse);
		} catch (HttpStatusCodeException e) {
			MainResponseDTO<VerifyUidVerifyOtpResponse> mainResponse = new MainResponseDTO<VerifyUidVerifyOtpResponse>();
			try {
				TypeReference<MainResponseDTO<VerifyUidVerifyOtpResponse>> typeRef = new TypeReference<MainResponseDTO<VerifyUidVerifyOtpResponse>>() {
				};
				mainResponse = mapper.readValue(e.getResponseBodyAsString(), typeRef);
			} catch (Exception e1) {
				throw new UserException(new ServiceError(UserErrorConstant.PARSE_ERROR.getErrorCode(),
						UserErrorConstant.PARSE_ERROR.getErrorMessage()));
			}
			throw new UserException(new ServiceError(mainResponse.getErrors().get(0).getErrorCode(),
					mainResponse.getErrors().get(0).getMessage()));
		}

		if (apiResponse == null) {
			throw new UserException(new ServiceError(UserErrorConstant.INTERNAL_SERVER_ERROR.getErrorCode(),
					UserErrorConstant.INTERNAL_SERVER_ERROR.getErrorMessage()));
		}

		ProteanIdUserEntity entity = repo.findByUid(Long.valueOf(request.getUid()));

		if (entity != null) {
			entity.setMobileId(request.getAndroidId());
			entity.setUpdatedTmsTmp(LocalDateTime.now());
			entity.setConsentFlag(true);
			entity.setWhiteListFlag(true);
		} else {
			
			ProteanIdUserEntity newEntity = new ProteanIdUserEntity();
			newEntity.setUid(Long.valueOf(request.getUid()));
			newEntity.setMobileId(request.getAndroidId());
			newEntity.setCreatedTmsTmp(LocalDateTime.now());
			newEntity.setConsentFlag(true);
			newEntity.setWhiteListFlag(true);
			repo.save(newEntity);
		}

		response.setPhoto(apiResponse.getBody().getResponse().getPhoto());
		response.setDob(apiResponse.getBody().getResponse().getDob());
		response.setUserName(apiResponse.getBody().getResponse().getUserName());
		response.setGender(apiResponse.getBody().getResponse().getGender());

		return response;
	}

	@Override
	public ConsentChangeResponse consentQrCodeVerification(ConsentChangeRequest request) {
		// TODO Auto-generated method stub

		ConsentChangeResponse response = new ConsentChangeResponse();
		ProteanIdUserEntity entity = repo.findByMobileId(request.getAndroidId());

		if (entity == null) {
			throw new UserException(new ServiceError(UserErrorConstant.MOBILE_ID_NOT_PRESENT.getErrorCode(),
					UserErrorConstant.MOBILE_ID_NOT_PRESENT.getErrorMessage()));
		}

		entity.setConsentFlag(request.isConsent());
		repo.save(entity);

		response.setConsentChangeStatus("Consent updated successfully");
		return response;
	}

	@Override
	public BioResponse getBioDetails(BioRequest bioRequest) {

		HttpEntity<BioRequest> requestEntity = new HttpEntity<BioRequest>(bioRequest);
		ParameterizedTypeReference<MainResponseDTO<BioResponse>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<BioResponse>>() {
		};
		logger.info("Initiate get bio details:" + bioRequest.getUid());
		ResponseEntity<MainResponseDTO<BioResponse>> apiResponse = null;
		try {
			apiResponse = restTemplate.exchange(uinAuthenticateUrl + "/getBioDetails", HttpMethod.POST, requestEntity,
					parameterizedResponse);
		} catch (HttpStatusCodeException e) {
			MainResponseDTO<BioResponse> mainResponse = new MainResponseDTO<BioResponse>();
			try {
				TypeReference<MainResponseDTO<BioResponse>> typeRef = new TypeReference<MainResponseDTO<BioResponse>>() {
				};
				mainResponse = mapper.readValue(e.getResponseBodyAsString(), typeRef);
			} catch (Exception e1) {
				throw new UserException(new ServiceError(UserErrorConstant.PARSE_ERROR.getErrorCode(),
						UserErrorConstant.PARSE_ERROR.getErrorMessage()));
			}
			throw new UserException(new ServiceError(mainResponse.getErrors().get(0).getErrorCode(),
					mainResponse.getErrors().get(0).getMessage()));
		}

		if (apiResponse == null) {
			throw new UserException(new ServiceError(UserErrorConstant.INTERNAL_SERVER_ERROR.getErrorCode(),
					UserErrorConstant.INTERNAL_SERVER_ERROR.getErrorMessage()));
		}

		return apiResponse.getBody().getResponse();
	}

}
