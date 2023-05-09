package com.nsdl.ndhm.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.nsdl.ndhm.constant.HealthIdCreationConstands;
import com.nsdl.ndhm.dto.ConfirmOtpDTO;
import com.nsdl.ndhm.dto.ConfirmOtpResp;
import com.nsdl.ndhm.dto.ExceptionJSONInfoDTO;
import com.nsdl.ndhm.dto.GenerateOtpDTO;
import com.nsdl.ndhm.dto.GenerateOtpResp;
import com.nsdl.ndhm.dto.HealthIDDTO;
import com.nsdl.ndhm.dto.HealthIDResp;
import com.nsdl.ndhm.dto.HealthIdDetailsToTelemedicineReqDTO;
import com.nsdl.ndhm.dto.MainRequestDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.Request;
import com.nsdl.ndhm.dto.ResendOtpDTO;
import com.nsdl.ndhm.dto.ResendOtpResp;
import com.nsdl.ndhm.entity.HealthIdCreationEntity;
import com.nsdl.ndhm.logger.LoggingClientInfo;
import com.nsdl.ndhm.repository.HealthIdCreationRepo;
import com.nsdl.ndhm.service.AssistedHealthIdCreationService;
import com.nsdl.ndhm.utility.CommonHeadersUtil;
import com.nsdl.ndhm.utility.CommonValidationUtil;

@Service
@LoggingClientInfo
public class AssistedHealthIdCreationServiceImpl implements AssistedHealthIdCreationService {
	private static final Logger logger = LoggerFactory.getLogger(AssistedHealthIdCreationServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Value("${ndhm.generateOTPForMobile}")
	String generateOTPForMobile;

	@Value("${ndhm.resendOTPforMobile}")
	String resendOTPforMobile;

	@Value("${ndhm.verifyOTPForMobile}")
	String verifyOTPForMobile;

	@Value("${ndhm.helathIDcreationForMobile}")
	String helathIDcreationForMobile;

	@Value("${ndhm.telemedicine.sendHealthIdDtlsToTelemedicine}")
	String sendHealthIdDtlsToTelemedicine;

	@Autowired
	@Qualifier("healthIdCommonValidation")
	private CommonValidationUtil validate;

	@Autowired
	HealthIdCreationRepo healthIdCreationRepo;

	List<ExceptionJSONInfoDTO> listOfExceptions = null;
	ExceptionJSONInfoDTO exceptionJSONInfoDTO = null;
	MainResponseDTO<HealthIDResp> mainResponse = null;

	/*
	 * for generating mobile OTP
	 */

	@Override
	public MainResponseDTO<GenerateOtpResp> generateOTPForAssisted(Map<String, String> headers,
			GenerateOtpDTO generateOtpDTO) {
		logger.info("Request Receives for generate OTP");
		String url = generateOTPForMobile;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<GenerateOtpResp> resp = new MainResponseDTO<>();
		GenerateOtpResp respData = null;

		try {
			uri = new URI(url);
			String encryptedString = new Gson().toJson(generateOtpDTO);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), GenerateOtpResp.class); // Json to response mapping
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("Error In generateOTPForAssisted {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (Exception e) {
			logger.error("Error In generateOTPForAssisted  {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
					HealthIdCreationConstands.OTP_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		}
		logger.info("Generated OTP Request Ends ");
		return resp;
	}

	/*
	 * re sending mobile otp
	 */
	@Override
	public MainResponseDTO<ResendOtpResp> resendOTPForAssisted(Map<String, String> headers, ResendOtpDTO resendOtpDTO) {
		logger.info("Request Receives for Resend OTP");
		String url = resendOTPforMobile;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<ResendOtpResp> resp = new MainResponseDTO<>();
		ResendOtpResp respData = null;
		Boolean resendStatus = null;

		try {

			uri = new URI(url);
			String encryptedString = new Gson().toJson(resendOtpDTO);

			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			resendStatus = new Gson().fromJson(result.getBody(), Boolean.class); // Json to response mapping
			respData = ResendOtpResp.builder().resendStatus(resendStatus).build();
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("Error In resendOTPForAssisted  {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		} catch (Exception e) {
			logger.error("Error In resendOTPForAssisted  {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
					HealthIdCreationConstands.OTP_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		}
		logger.info("Resend OTP Request Ends ");
		return resp;
	}
	/*
	 * for verifying mobile OTP
	 */

	@Override
	public MainResponseDTO<ConfirmOtpResp> confirmOTPForAssisted(Map<String, String> headers,
			ConfirmOtpDTO confirmOtpDTOReq) {
		logger.info("Request Receives for confirm OTP");
		String url = verifyOTPForMobile;
		URI uri;
		ResponseEntity<String> result = null;
		MainResponseDTO<ConfirmOtpResp> resp = new MainResponseDTO<>();
		ConfirmOtpResp respData = null;
		try {

			uri = new URI(url);
			String encryptedString = new Gson().toJson(confirmOtpDTOReq);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString, commonHeadersUtil.getHeaders(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			respData = new Gson().fromJson(result.getBody(), ConfirmOtpResp.class); // Json to response mapping
			resp.setResponse(respData);
			resp.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("Error In confirmOTPForAssisted  {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;

		} catch (Exception e) {
			logger.error("Error In confirmOTPForAssisted  {} ", e);
			listOfExceptions = new ArrayList<>();
			listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.OTP_FAILURE.getCode(),
					HealthIdCreationConstands.OTP_FAILURE.getMsg()));
			resp.setErrors(listOfExceptions);
			resp.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return resp;
		}
		logger.info("Confirm OTP Request Ends ");
		return resp;
	}

	/*
	 * create and save health Id
	 */
	@Override
	public MainResponseDTO<HealthIDResp> saveHealthIdByMobileForAssisted(Map<String, String> headers,
			MainRequestDTO<HealthIDDTO> requestHealthIDDTO) {
		logger.info("saving the healthId user details");
		MainResponseDTO<HealthIDResp> mainResponse;
		HealthIdCreationEntity healthIdCreateEntity = new HealthIdCreationEntity();
		HealthIDDTO healthIDDTO = requestHealthIDDTO.getRequest();
		mainResponse = validateRequestFields(healthIDDTO);
		if (mainResponse != null && !mainResponse.getErrors().isEmpty()) {

			logger.error("Request body data is invalid");
			return mainResponse;

		}

		mainResponse = new MainResponseDTO<>();

		MainResponseDTO<HealthIDResp> responseUsrCreate = null;

		responseUsrCreate = createHealthId(headers, healthIDDTO);

		if (responseUsrCreate.isStatus()) {
			try {
				sendHealthIdDetailsToTelemedicine(headers, responseUsrCreate.getResponse(), healthIDDTO.getTxnId());
			} catch (Exception e) {
				logger.error("Error In saveHealthIdByMobileForAssisted  {} ", e);
				listOfExceptions = new ArrayList<>();
				listOfExceptions
						.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.HEALTH_ID_SENDING_FAIL.getCode(),
								HealthIdCreationConstands.HEALTH_ID_SENDING_FAIL.getMsg()));
				mainResponse.setErrors(listOfExceptions);
				mainResponse.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());

			}

			mainResponse.setResponse(responseUsrCreate.getResponse());

			mainResponse.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
			BeanUtils.copyProperties(responseUsrCreate.getResponse(), healthIdCreateEntity);
			healthIdCreateEntity.setCreatedFrom(HealthIdCreationConstands.CREATED_FROM_MOBILE.getValidate());
			try {
				healthIdCreationRepo.save(healthIdCreateEntity);
			} catch (Exception e) {
				logger.error("Error In saveHealthIdByMobileForAssisted  {} ", e);
				listOfExceptions = new ArrayList<>();
				listOfExceptions.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.HEALTH_ID_SAVE_FAIL.getCode(),
						HealthIdCreationConstands.HEALTH_ID_SAVE_FAIL.getMsg()));
				mainResponse.setErrors(listOfExceptions);
				mainResponse.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
				return mainResponse;
			}

		} else {
			mainResponse.setResponse(responseUsrCreate.getResponse());
			mainResponse.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			mainResponse.setErrors(responseUsrCreate.getErrors());
		}
		logger.info("User details saved successfully");
		return mainResponse;
	}

	/*
	 * for validate require feilds
	 */
	private MainResponseDTO<HealthIDResp> validateRequestFields(HealthIDDTO healthIDDTO) {

		logger.info("Validating the request body fields");

		if (healthIDDTO.getFirstName() != null && healthIDDTO.getFirstName().isEmpty()
				|| healthIDDTO.getFirstName().length() < 1 || healthIDDTO.getFirstName().length() > 99) {
			logger.error("User first name is empty");
			return throwExceptionForHealthIdFirstName();
		}
		if (healthIDDTO.getLastName() != null && healthIDDTO.getLastName().isEmpty()
				|| healthIDDTO.getLastName().length() < 1 || healthIDDTO.getLastName().length() > 99) {
			logger.error("User Last name is empty");
			return throwExceptionForHealthIdLastName();
		} else if ((healthIDDTO.getGender().isEmpty() || healthIDDTO.getGender() == null)) {
			logger.error("Select User Gender");
			return throwExceptionForHealthIdGender();
		} else if ((!healthIDDTO.getEmail().isEmpty() || healthIDDTO.getEmail() != null)
				&& !validate.validateEmail(healthIDDTO.getEmail())) {
			logger.error("User emailID pattern or is empty. Please check");
			return throwExceptionForHealthIdEmail();
		} else if (healthIDDTO.getPassword() != null && !validate.validatePassword(healthIDDTO.getPassword())) {
			logger.error("User password is empty. Please check");
			return throwExceptionForHealthIdPassword();
		} else if (healthIDDTO.getMobileNo() != null && !validate.validateMobileNo(healthIDDTO.getMobileNo())) {
			logger.error("User Mobile Number is empty. Please check");
			return throwExceptionForHealthIdMobileNo();
		}

		return null;

	}

	/*
	 * exception for first name
	 */
	private MainResponseDTO<HealthIDResp> throwExceptionForHealthIdFirstName() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.USER_FIRSTNAME_VALIDATE.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.USER_FIRSTNAME_VALIDATE.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	/*
	 * exception for last name
	 */
	private MainResponseDTO<HealthIDResp> throwExceptionForHealthIdLastName() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.USER_LASTNAME_VALIDATE.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.USER_LASTNAME_VALIDATE.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	/*
	 * exception for gender
	 */
	private MainResponseDTO<HealthIDResp> throwExceptionForHealthIdGender() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.USER_GENDER_VALIDATE.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.USER_GENDER_VALIDATE.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	/*
	 * exception for email id
	 */
	private MainResponseDTO<HealthIDResp> throwExceptionForHealthIdEmail() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.EMAIL_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.EMAIL_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	/*
	 * exception for password
	 */
	private MainResponseDTO<HealthIDResp> throwExceptionForHealthIdPassword() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.PASSWORD_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.PASSWORD_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	/*
	 * exception for mobile No
	 */
	private MainResponseDTO<HealthIDResp> throwExceptionForHealthIdMobileNo() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.USER_MOBILE_NO_VALIDATE.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.USER_MOBILE_NO_VALIDATE.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	/*
	 * create health id by calling NDHM APIs
	 */
	private MainResponseDTO<HealthIDResp> createHealthId(Map<String, String> reqeaders, HealthIDDTO healthIDDTO) {

		logger.info("Creating the healthID for new  user");
		String url = helathIDcreationForMobile;
		URI uri;
		ResponseEntity<String> result = null;
		HealthIDResp resp = null;
		MainResponseDTO<HealthIDResp> response = new MainResponseDTO<>();
		try {
			HealthIDDTO createUser = checkNullANdCreateDto(healthIDDTO);

			uri = new URI(url);
			String encryptedString = new Gson().toJson(createUser);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
					commonHeadersUtil.getHeaders(reqeaders));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			resp = new Gson().fromJson(result.getBody(), HealthIDResp.class);
			response.setResponse(resp);
			response.setStatus(HealthIdCreationConstands.SERVICE_SUCCESS_STATUS.isStatus());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("Error In createHealthId  {} ", e);
			List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();
			errorList
					.add(new ExceptionJSONInfoDTO(String.valueOf(e.getStatusCode().value()), e.getStatusCode().name()));
			response.setErrors(errorList);
			response.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return response;
		} catch (Exception e) {
			logger.error("Error In createHealthId  {} ", e);
			List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();
			errorList.add(new ExceptionJSONInfoDTO(HealthIdCreationConstands.USER_MANAGEMENT_FAIL.getCode(),
					HealthIdCreationConstands.USER_MANAGEMENT_FAIL.getMsg()));
			response.setErrors(errorList);
			response.setStatus(HealthIdCreationConstands.SERVICE_FAIL_STATUS.isStatus());
			return response;

		}
		logger.info("Created HealthID for new  user successfully");
		return response;

	}

	/* to send healthid details to telemedicine */
	private ResponseEntity<?> sendHealthIdDetailsToTelemedicine(Map<String, String> headers, HealthIDResp healthIDDTO,
			String txnId) {

		logger.info("Sending healthID Details to telemedicine starts");
		String url = sendHealthIdDtlsToTelemedicine;
		URI uri;
		ResponseEntity<String> result = null;
		try {
			Request healthIdDetails = Request.builder().healthId(healthIDDTO.getHealthId())
					.healthNumber(healthIDDTO.getHealthIdNumber()).mobileNo(healthIDDTO.getMobile()).txnId(txnId)
					.build();
			HealthIdDetailsToTelemedicineReqDTO request = HealthIdDetailsToTelemedicineReqDTO.builder()
					.request(healthIdDetails).aPI("NDHM integration").method("POST").mimeType("multipart/form-data")
					.requestTime("").version("1.0").build();
			uri = new URI(url);
			String encryptedString = new Gson().toJson(request);
			HttpEntity<String> requestEntity = new HttpEntity<>(encryptedString,
					commonHeadersUtil.getHeadersContentTypeOnly(headers));
			result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

		} catch (Exception e) {
			result = new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			logger.error("Error In sendHealthIdDetailsToTelemedicine  {} ", e);
		}
		logger.info("Sending healthID Details to telemedicine ends");
		return result;
	}

	private HealthIDDTO checkNullANdCreateDto(HealthIDDTO healthIDDTO) {

		HealthIDDTO.builder()
				.firstName(null != healthIDDTO.getFirstName() && !"".equals(healthIDDTO.getFirstName())
						? healthIDDTO.getFirstName()
						: "")
				.middleName(null != healthIDDTO.getMiddleName() && !"".equals(healthIDDTO.getMiddleName())
						? healthIDDTO.getMiddleName()
						: "")
				.lastName(null != healthIDDTO.getLastName() && !"".equals(healthIDDTO.getLastName())
						? healthIDDTO.getLastName()
						: "")
				.name(null != healthIDDTO.getName() && !"".equals(healthIDDTO.getName()) ? healthIDDTO.getName() : "")
				.email(null != healthIDDTO.getEmail() && !"".equals(healthIDDTO.getEmail()) ? healthIDDTO.getEmail()
						: "")
				.mobileNo(null != healthIDDTO.getMobileNo() && !"".equals(healthIDDTO.getMobileNo())
						? healthIDDTO.getMobileNo()
						: "")
				.gender(healthIDDTO.getGender() != null && !"".equals(healthIDDTO.getGender()) ? healthIDDTO.getGender()
						: "")
				.address(null != healthIDDTO.getAddress() && !"".equals(healthIDDTO.getAddress())
						? healthIDDTO.getAddress()
						: "")
				.stateCode(healthIDDTO.getStateCode() != null && !"".equals(healthIDDTO.getStateCode())
						? healthIDDTO.getStateCode()
						: "")
				.districtCode(null != healthIDDTO.getDistrictCode() && !"".equals(healthIDDTO.getDistrictCode())
						? healthIDDTO.getDistrictCode()
						: "")
				.pincode(healthIDDTO.getPincode())
				.dayOfBirth(null != healthIDDTO.getDayOfBirth() && !"".equals(healthIDDTO.getDayOfBirth())
						? healthIDDTO.getDayOfBirth()
						: "")
				.monthOfBirth(null != healthIDDTO.getMonthOfBirth() && !"".equals(healthIDDTO.getMonthOfBirth())
						? healthIDDTO.getMonthOfBirth()
						: "")
				.yearOfBirth(null != healthIDDTO.getYearOfBirth() && !"".equals(healthIDDTO.getYearOfBirth())
						? healthIDDTO.getYearOfBirth()
						: "")
				.profilePhoto(null != healthIDDTO.getProfilePhoto() && !"".equals(healthIDDTO.getProfilePhoto())
						? healthIDDTO.getProfilePhoto()
						: "")
				.healthId(null != healthIDDTO.getHealthId() && !"".equals(healthIDDTO.getHealthId())
						? healthIDDTO.getHealthId()
						: "")
				.password(null != healthIDDTO.getPassword() && !"".equals(healthIDDTO.getPassword())
						? healthIDDTO.getPassword()
						: "")
				.txnId(healthIDDTO.getTxnId()).token(healthIDDTO.getToken()).build();
		return healthIDDTO;

	}
}
