package com.nsdl.telemedicine.patient.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import com.nsdl.telemedicine.patient.constant.AuthConstant;
import com.nsdl.telemedicine.patient.constant.AuthErrorConstant;
import com.nsdl.telemedicine.patient.dto.AppointmentDetailsResponseDTO;
import com.nsdl.telemedicine.patient.dto.AppontmentDetailsRequestDTO;
import com.nsdl.telemedicine.patient.dto.BulkPatientDetails;
import com.nsdl.telemedicine.patient.dto.BulkPatientDetailsResponse;
import com.nsdl.telemedicine.patient.dto.BulkPatientRegistrationDTO;
import com.nsdl.telemedicine.patient.dto.CaptchaResponseDto;
import com.nsdl.telemedicine.patient.dto.CareContextDtls;
import com.nsdl.telemedicine.patient.dto.CareContextResponse;
import com.nsdl.telemedicine.patient.dto.CareContextResponseDto;
import com.nsdl.telemedicine.patient.dto.CreateUserRequestDTO;
import com.nsdl.telemedicine.patient.dto.CreateUserResponseDTO;
import com.nsdl.telemedicine.patient.dto.DoctorDetailsDto;
import com.nsdl.telemedicine.patient.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.patient.dto.HealthIdDetailsToTelemedicineDTO;
import com.nsdl.telemedicine.patient.dto.MainRequestDTO;
import com.nsdl.telemedicine.patient.dto.MainRequestsDTO;
import com.nsdl.telemedicine.patient.dto.MainResponseDTO;
import com.nsdl.telemedicine.patient.dto.NotifyRequestDTO;
import com.nsdl.telemedicine.patient.dto.NotifyResponseDTO;
import com.nsdl.telemedicine.patient.dto.OtpRequestDTO;
import com.nsdl.telemedicine.patient.dto.OtpResponseDTO;
import com.nsdl.telemedicine.patient.dto.PatientDetailsDTO;
import com.nsdl.telemedicine.patient.dto.PatientRefRequestDto;
import com.nsdl.telemedicine.patient.dto.PatientRefResponseDto;
import com.nsdl.telemedicine.patient.dto.PatientRegDetailsDTO;
import com.nsdl.telemedicine.patient.dto.PatientRegistrationDto;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.ReportRequestDto;
import com.nsdl.telemedicine.patient.dto.ReportResponseDtls;
import com.nsdl.telemedicine.patient.dto.ReportResponseDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.dto.TokenDetailsDTO;
import com.nsdl.telemedicine.patient.dto.UserDTO;
import com.nsdl.telemedicine.patient.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.patient.entity.AuditPatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.entity.ConsultationDtl;
import com.nsdl.telemedicine.patient.entity.DoctorMstrDtlsEntity;
import com.nsdl.telemedicine.patient.entity.DoctorPatientMapDtlsEntity;
import com.nsdl.telemedicine.patient.entity.LoginUserEntity;
import com.nsdl.telemedicine.patient.entity.NDHMDeatilsEntity;
import com.nsdl.telemedicine.patient.entity.PatientCareContextEntity;
import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.entity.PatientReportUploadDtlsEntity;
import com.nsdl.telemedicine.patient.entity.ScribeRegEntity;
import com.nsdl.telemedicine.patient.exception.DateParsingException;
import com.nsdl.telemedicine.patient.exception.PatientRegException;
import com.nsdl.telemedicine.patient.loggers.PatientLoggingClientInfo;
import com.nsdl.telemedicine.patient.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.patient.repository.AuditPatientPersonalDetailsRepository;
import com.nsdl.telemedicine.patient.repository.ConsulationPrescriptionDetailsRepo;
import com.nsdl.telemedicine.patient.repository.DoctorMstrRepo;
import com.nsdl.telemedicine.patient.repository.DoctorPatientMapRepo;
import com.nsdl.telemedicine.patient.repository.NDHMIntegrationRepo;
import com.nsdl.telemedicine.patient.repository.PatientCareContextRepo;
import com.nsdl.telemedicine.patient.repository.PatientPersonalDetailsRepository;
import com.nsdl.telemedicine.patient.repository.PatientRegistrationByScribeRepository;
import com.nsdl.telemedicine.patient.repository.PatientReportUploadDtlsRepository;
import com.nsdl.telemedicine.patient.repository.ScribeRegRepo;
import com.nsdl.telemedicine.patient.repository.UserLoginRepositiory;
import com.nsdl.telemedicine.patient.service.AuditPatientService;
import com.nsdl.telemedicine.patient.service.PatientRegistrationService;
import com.nsdl.telemedicine.patient.utility.AESUtils;
import com.nsdl.telemedicine.patient.utility.AuthUtil;
import com.nsdl.telemedicine.patient.utility.CommonValidationUtil;
import com.nsdl.telemedicine.patient.utility.DateUtils;
import com.nsdl.telemedicine.patient.utility.RandomStringUtil;

@Service
@PatientLoggingClientInfo
@Transactional
public class PatientRegistrationServiceImpl implements PatientRegistrationService {

	private static final Logger LOGGER = LogManager.getLogger(PatientRegistrationServiceImpl.class);

	@Autowired
	private PatientPersonalDetailsRepository patientRegistrationRepository;

	@Autowired
	@Qualifier("patientCommonValidation")
	CommonValidationUtil validate;

	@Autowired
	private RestTemplate template;

	@Autowired
	private AuthUtil authUtil;

	@Autowired
	private UserDTO userDto;

	@Autowired
	private AuditPatientService auditPatientService;

	@Value("${CREATE_USER_URL}")
	private String createUserURL;

	@Value("${OTP_GENERATE_URL}")
	private String generateOtpURL;

	@Value("${CAPTCHA_VERIFICATION_URL}")
	private String captchaVerificationURL;

	@Autowired
	AppointmentDtlsRepository appointmentDtlsRepository;

	@Value("${prescription.path}")
	private String prescriptionPath;

	@Value("${path.seperator}")
	private String pathSeperator;

	@Autowired
	ConsulationPrescriptionDetailsRepo consulationPrescriptionDetailsRepo;
	
	@Autowired
	private DoctorMstrRepo doctorMstrRepo;

	@Autowired
	AuditPatientPersonalDetailsRepository auditRepo;

	@Autowired
	private PatientRegistrationByScribeRepository patientRegistrationByScribeRepository;

	@Autowired
	private ScribeRegRepo scribeRegRepo;

	@Autowired
	private UserLoginRepositiory userLoginRepositiory;

	@Autowired
	private DoctorPatientMapRepo doctorPatientMapRepo;

	@Autowired
	private PatientPersonalDetailsRepository personalDetailsRepository;
	
	@Autowired
	private PatientCareContextRepo patientCareContextRepo;
	
	@Autowired
	private PatientReportUploadDtlsRepository patientReportUploadDtlsRepository; 
	
	@Value("${email.notification.url}")
	String emailUrl;

	@Autowired
	private NDHMIntegrationRepo ndhmRepo;

	/*
	 * @Value("${BulkRecordList}") private String bulkrecordlist;
	 */

	/*
	 * @Value("${WindowDocPaths}") private String windowdocpath;
	 */

	@SuppressWarnings("unchecked")
	@Override
	public ResponseWrapper<PatientResponseDto> savePatientDetails(
			RequestWrapper<PatientRegistrationDto> patientRegistrationDto) {
		ResponseWrapper<PatientResponseDto> response = null;
		response = (ResponseWrapper<PatientResponseDto>) AuthUtil.getMainResponseDto(patientRegistrationDto);
		PatientRegistrationDto request = patientRegistrationDto.getRequest();
		String userId = request.getPtUserID().trim().toUpperCase();
		validateRequest(request, userId);
		PatientPersonalDetailEntity entity = new PatientPersonalDetailEntity();
		BeanUtils.copyProperties(request, entity);
		entity.setPtUserID(userId);
		entity.setCreatedDate(LocalDateTime.now());

		/* Load the Patient Profile photo to file system */
		if (request.getPtProfilePhoto() != null || request.getPtProfilePhoto() != "") {
			PatientResponseDto path = authUtil.savePatientProfilePhoto(request.getPtProfilePhoto(),
					request.getPtMobNo(), userId);
			entity.setProfilePhotoPath(path.getMessage());
		}
		PatientPersonalDetailEntity savedEntity;
		try {
			savedEntity = patientRegistrationRepository.save(entity);
			LOGGER.info("Saved user Data to Patient registration");
			auditPatientService.auditRegistrationServicePersonalDetail(savedEntity, userId);
		} catch (Exception e) {
			LOGGER.error("Exception while Patient registration..");
			e.printStackTrace();
			throw e;
		}
		if (savedEntity != null) {
			// OTP Generate Rest Call
			String otpResponse = generateOTP(savedEntity);
			if (null != otpResponse && !otpResponse.isEmpty()) {
				LOGGER.info("OTP Generation SUCCESS");
				if (createUserForPatient(savedEntity)) {
					response.setStatus(true);
					PatientResponseDto patientResponseDto = new PatientResponseDto();
					// patientResponseDto.setInfo(AuthConstant.OTP_SUCCESS);
					patientResponseDto.setMessage(otpResponse);
					response.setResponse(patientResponseDto);
					LOGGER.info("User-Management Api Call SUCCESS");
				} else {
					LOGGER.error(AuthConstant.USER_MANAGEMENT_API_FAIL);
					response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.USER_MANAGEMENT_API_FAIL,
							AuthConstant.USER_MANAGEMENT_API_FAIL));
				}
			} else {
				LOGGER.error(AuthConstant.OTP_FAILURE);
				response.setErrors(
						AuthUtil.getExceptionList(null, AuthErrorConstant.OTP_FAILURE, AuthConstant.OTP_FAILURE));
			}
		} else {
			LOGGER.error(AuthConstant.PATIENT_REGISTRATION_FAIL);
			response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
					AuthConstant.PATIENT_REGISTRATION_FAIL));
		}
		return response;
	}

	private void validateRequest(PatientRegistrationDto request, String userId) {
		MainResponseDTO<CaptchaResponseDto> responseCaptcha = null;
		responseCaptcha = verifyCaptcha(request.getCaptchaCode(), request.getSessionId());
		responseCaptcha.setStatus(true);
		if (!responseCaptcha.isStatus()) {
			LOGGER.error("Captcha Code validation : Failed");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_CAPTCHA_CODE,
					AuthConstant.INVALID_CAPTCHA_CODE));
		}
		if ((!validate.validateUserID(request.getPtUserID())) || request.getPtUserID().length() < 8
				|| request.getPtUserID().length() > 25) {
			LOGGER.error("user-ID format validation : Failed");
			throw new DateParsingException(
					new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_USERID, AuthConstant.INVALID_USERID));
		}
		if ((!validate.validateUserName(request.getPtFullName())) || request.getPtFullName().length() < 1
				|| request.getPtFullName().length() > 30) {
			LOGGER.error("Full name format validation : Failed");
			throw new DateParsingException(
					new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_FULL_NAME, AuthConstant.INVALID_FULL_NAME));
		}
		if (!validate.validatePassword(request.getPtPassword())) {
			LOGGER.error("user password format validation : Failed");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_PASSWORD_FORMAT_CODE,
					AuthConstant.INVALID_PASSWORD_FORMAT));
		}
		if (null != request.getPtEmail() && !request.getPtEmail().isEmpty()
				&& !validate.validateEmail(request.getPtEmail())) {
			LOGGER.error("user email format validation : Failed");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_EMAIL_FORMAT_CODE,
					AuthConstant.INVALID_EMAIL_FORMAT));
		}
		if (!validate.validateMobileNo(request.getPtMobNo())) {
			LOGGER.error("user mobile number format validation : Failed");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_MOBILE_FORMAT_CODE,
					AuthConstant.INVALID_MOBILE_FORMAT));
		}
		if (patientRegistrationRepository.existsByPtUserID(userId)) { // ||
																		// patientRegistrationRepository.existsByptEmail(request.getPtEmail())
																		// ) {
			LOGGER.error("given userid already exists");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.USERID_ALREADY_EXISTS_CODE,
					AuthConstant.USERID_ALREADY_EXISTS));
		}
	}

	private boolean createUserForPatient(PatientPersonalDetailEntity persistEntity) {
		try {
			LOGGER.info("USER MANAGEMENT API Called");
			MainRequestDTO<CreateUserRequestDTO> mainRequestDTO = new MainRequestDTO<>();
			CreateUserRequestDTO createUser = new CreateUserRequestDTO();
			createUser.setUserFullName(persistEntity.getPtFullName());
			createUser.setEmail(persistEntity.getPtEmail());
			createUser.setMobileNumber(persistEntity.getPtMobNo());
			createUser.setPassword(persistEntity.getPtPassword());
			// createUser.setRoleName(PatientRegConstant.PATIENT);
			createUser.setUserId(persistEntity.getPtUserID());
			createUser.setUserType(AuthConstant.PATIENT);
			mainRequestDTO.setRequest(createUser);
			LOGGER.info("USER MANAGEMENT API Called with userID " + createUser.getUserId() + "And UserName "
					+ createUser.getUserFullName());
			HttpEntity<MainRequestDTO<CreateUserRequestDTO>> requestEntity = new HttpEntity<>(mainRequestDTO);
			ParameterizedTypeReference<MainResponseDTO<CreateUserResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<CreateUserResponseDTO>>() {
			};
			ResponseEntity<MainResponseDTO<CreateUserResponseDTO>> response = template.exchange(createUserURL,
					HttpMethod.POST, requestEntity, parameterizedResponse);
			LOGGER.info("USER MANAGEMENT API Status : " + response.getBody().isStatus());
			return response.getBody().isStatus();
		} catch (Exception e) {
			LOGGER.error("CALL TO USER MANAGEMENT API FAILED");
			e.printStackTrace();
			throw e;
		}
	}

	private String generateOTP(PatientPersonalDetailEntity persistEntity) {
		String description = null;
		try {
			LOGGER.info("OTP Generation Api called");
			Boolean generateOtpResponse = false;

			MainRequestDTO<OtpRequestDTO> mainRequest = new MainRequestDTO<>();
			OtpRequestDTO otpRequestDTO = new OtpRequestDTO();
			otpRequestDTO.setEmailID(persistEntity.getPtEmail());
			otpRequestDTO.setMobileNo(persistEntity.getPtMobNo().toString());
			otpRequestDTO.setOtpFor(AuthConstant.OTP_FOR);
			otpRequestDTO.setOtpGenerateTpye(AuthConstant.OTP_GENERATE_TYPE);
			otpRequestDTO.setSendType(null != persistEntity.getPtEmail() && !persistEntity.getPtEmail().isEmpty()
					? AuthConstant.OTP_SEND_TYPE
					: "sms");
			otpRequestDTO.setUserId(persistEntity.getPtUserID());
			mainRequest.setRequest(otpRequestDTO);

			HttpEntity<MainRequestDTO<OtpRequestDTO>> requestEntity = new HttpEntity<MainRequestDTO<OtpRequestDTO>>(
					mainRequest);
			ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>>() {
			};

			ResponseEntity<MainResponseDTO<OtpResponseDTO>> response = template.exchange(generateOtpURL,
					HttpMethod.POST, requestEntity, parameterizedResponse);

			if (response.getBody().isStatus()) {
				if (response.getBody().getResponse().getMessage().equalsIgnoreCase("yes")) {
					generateOtpResponse = true;
					description = response.getBody().getResponse().getDescription();
				}
			}
			LOGGER.info("OTP Generation Api status : " + generateOtpResponse);
			return description;
		} catch (Exception e) {
			LOGGER.error("Call to OTP Generation API Failed");
			e.printStackTrace();
		}
		return description;
	}

	public MainResponseDTO<CaptchaResponseDto> verifyCaptcha(String captchaValue, String sessionId) {
		LOGGER.info("Captcha Varification Api called");
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("sessionId", sessionId);
		requestHeaders.add("captchaValue", captchaValue);
		requestHeaders.add("flagValue", "true");
		HttpEntity<MainRequestDTO<CaptchaResponseDto>> requestEntity = new HttpEntity<MainRequestDTO<CaptchaResponseDto>>(
				requestHeaders);
		ParameterizedTypeReference<MainResponseDTO<CaptchaResponseDto>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<CaptchaResponseDto>>() {
		};
		ResponseEntity<MainResponseDTO<CaptchaResponseDto>> response = template.exchange(captchaVerificationURL,
				HttpMethod.POST, requestEntity, parameterizedResponse);
		LOGGER.info("Captcha Api status : ");
		return response.getBody();
	}

	@Override
	public ResponseWrapper<PatientRegistrationDto> getPatientDetailsFromIPAN() {

		ResponseWrapper<PatientRegistrationDto> response = new ResponseWrapper<PatientRegistrationDto>();
		response.setId("registration");
		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		String userId = userDto.getUserName().trim().toUpperCase();
		PatientRegistrationDto patientRegistrationDto = null;
		PatientPersonalDetailEntity entity = patientRegistrationRepository.findByPtUserID(userId);
		LOGGER.info("Get Patient Details For:" + userId);
		if (entity == null) {
			LOGGER.error(AuthConstant.INVALID_USER_ERROR + " For:" + userId);
			throw new DateParsingException(
					new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_USER_ERR_CODE, AuthConstant.INVALID_USER_ERROR));
		}
		LoginUserEntity userentity = userLoginRepositiory.findPatientDetails(entity.getPtUserID());
		try {
			patientRegistrationDto = new PatientRegistrationDto();
			patientRegistrationDto.setPtFullName(entity.getPtFullName());
			patientRegistrationDto.setPtMobNo(entity.getPtMobNo());
			patientRegistrationDto.setPtEmail(entity.getPtEmail());
			patientRegistrationDto.setPtUserID(entity.getPtUserID());
			patientRegistrationDto.setPtPassword(entity.getPtPassword());
			patientRegistrationDto.setPtProfilePhoto(entity.getProfilePhotoPath());
			patientRegistrationDto.setGender(entity.getGender());
			patientRegistrationDto.setAddress1(entity.getAddress1());
			patientRegistrationDto.setAddress2(entity.getAddress2());
			patientRegistrationDto.setAddress3(entity.getAddress3());
			patientRegistrationDto.setBloodGroup(entity.getBloodGroup());
			patientRegistrationDto.setDob(entity.getDob());
			patientRegistrationDto.setEmergContanctNo(entity.getEmergContanctNo());
			patientRegistrationDto.setHeight(entity.getHeight());
			patientRegistrationDto.setPincode(entity.getPincode());
			patientRegistrationDto.setPtCity(entity.getPtCity());
			patientRegistrationDto.setPtCountry(entity.getPtCountry());
			patientRegistrationDto.setPtState(entity.getPtState());
			patientRegistrationDto.setPtUserID(entity.getPtUserID());
			patientRegistrationDto.setWeight(entity.getWeight());
			patientRegistrationDto.setHealthNumber(entity.getHealthNumber());
			patientRegistrationDto.setHealthID(entity.getHealthID());
			patientRegistrationDto.setWallet(entity.getWallet());	
			if (userentity != null) {
				patientRegistrationDto.setHealthidVerificationStatus(userentity.getIsHealthIDVerificationStatus());
			}
			if (userentity != null) {
				patientRegistrationDto.setIsHelathIDExists(userentity.getIsHelathIDExists());
			}
			response.setStatus(true);
			response.setResponse(patientRegistrationDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings({ "serial", "unused" })
	@Override
	public ResponseWrapper<List<AppointmentDetailsResponseDTO>> searchCompletedAppointmentsForPatient(
			RequestWrapper<AppontmentDetailsRequestDTO> appontmentDetailsRequest) {
		List<AppointmentDtlsEntity> completedAppointmentEntities = new ArrayList<AppointmentDtlsEntity>();
		List<AppointmentDetailsResponseDTO> completedAppointmentDTOs = new ArrayList<AppointmentDetailsResponseDTO>();
		ResponseWrapper<List<AppointmentDetailsResponseDTO>> response = new ResponseWrapper<List<AppointmentDetailsResponseDTO>>();
		try {
			completedAppointmentEntities = appointmentDtlsRepository
					.findAll(new Specification<AppointmentDtlsEntity>() {
						@Override
						public Predicate toPredicate(Root<AppointmentDtlsEntity> root, CriteriaQuery<?> query,
								CriteriaBuilder criteriaBuilder) {
							List<Predicate> predicates = new ArrayList<>();
							if ((null != appontmentDetailsRequest.getRequest().getFromDate()
									&& null != appontmentDetailsRequest.getRequest().getToDate()
									&& !appontmentDetailsRequest.getRequest().getFromDate().isEmpty()
									&& !appontmentDetailsRequest.getRequest().getToDate().isEmpty())
									|| (null != appontmentDetailsRequest.getRequest().getToDate()
											&& !appontmentDetailsRequest.getRequest().getToDate().isEmpty())) {
								predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(
										root.get("adApptDateFk"),
										formatStringToDate(appontmentDetailsRequest.getRequest().getFromDate()))));
								predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(
										root.get("adApptDateFk"),
										formatStringToDate(appontmentDetailsRequest.getRequest().getToDate()))));
							}
							if (null != appontmentDetailsRequest.getRequest().getApptId()
									&& !appontmentDetailsRequest.getRequest().getApptId().isEmpty()) {
								predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("adApptId"),
										appontmentDetailsRequest.getRequest().getApptId())));
							}
							if (null != appontmentDetailsRequest.getRequest().getDoctorName()
									&& !appontmentDetailsRequest.getRequest().getDoctorName().isEmpty()) {
								predicates.add(criteriaBuilder.and(criteriaBuilder.like(
										root.get("docMstrDtlsEntity").get("dmdDrName"),
										"%" + appontmentDetailsRequest.getRequest().getDoctorName().toUpperCase()
												+ "%")));
							}
							predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("adApptStatus"), "C")));
							predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("patientRegDtlsEntity"),
									userDto.getUserName().toUpperCase())));
							query.orderBy(criteriaBuilder.desc(root.get("adApptDateFk")));
							return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
						}
					});
			if (null != completedAppointmentEntities && completedAppointmentEntities.size() > 0) {
				for (AppointmentDtlsEntity appointmentDtlsEntity : completedAppointmentEntities) {
					AppointmentDetailsResponseDTO appointmentDetailsResponseDTO = new AppointmentDetailsResponseDTO();
					appointmentDetailsResponseDTO.setAppointmentId(appointmentDtlsEntity.getAdApptId());
					appointmentDetailsResponseDTO.setAppointmentDate(
							new SimpleDateFormat("dd-MM-yyyy").format(appointmentDtlsEntity.getAdApptDateFk()));
					appointmentDetailsResponseDTO
							.setDoctorId(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());
					completedAppointmentDTOs.add(appointmentDetailsResponseDTO);
				}
			} else {
				LOGGER.error("No Appointments Found..");
				throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.NO_APPOINTMENT_FOUND,
						AuthConstant.NO_APPOINTMENT_FOUND));
			}
		} catch (DateParsingException e1) {
			throw e1;
		} catch (Exception e) {
			LOGGER.error("Exception while getting appointment details.");
			e.printStackTrace();
			throw e;
		}
		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		response.setResponse(completedAppointmentDTOs);
		response.setStatus(true);
		return response;
	}

	/**
	 * @param dateString
	 * @return Added by girishk to change string to date.
	 */
	private Date formatStringToDate(String dateString) {
		SimpleDateFormat inputDateForamt = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date finalDate = null;
		try {
			if (null != dateString && !dateString.isEmpty()) {
				Date date = inputDateForamt.parse(dateString);
				finalDate = outputDateFormat.parse(outputDateFormat.format(date));
			} else {// get todays date
				Calendar cal = Calendar.getInstance();
				Date date = cal.getTime();
				finalDate = outputDateFormat.parse(outputDateFormat.format(date));
			}
		} catch (Exception e1) {
			LOGGER.error("Exception while parsing date.");
			e1.printStackTrace();
		}
		return finalDate;
	}

	@Override
	public ResponseWrapper<List<AppointmentDetailsResponseDTO>> listOfCompletedAppointmentsForPatient() {
		// TODO Auto-generated method stub
		List<AppointmentDtlsEntity> completedAppointmentEntities = new ArrayList<AppointmentDtlsEntity>();
		List<AppointmentDetailsResponseDTO> completedAppointmentDTOs = new ArrayList<AppointmentDetailsResponseDTO>();
		ResponseWrapper<List<AppointmentDetailsResponseDTO>> response = new ResponseWrapper<List<AppointmentDetailsResponseDTO>>();
		// ConsultationDtl consultationDtl=null;
		try {
			String filePath = prescriptionPath + pathSeperator;
			completedAppointmentEntities = appointmentDtlsRepository.findapptDetails(userDto.getUserName());
			if (null != completedAppointmentEntities && completedAppointmentEntities.size() > 0) {
				for (AppointmentDtlsEntity appointmentDtlsEntity : completedAppointmentEntities) {
					// path from database.
					// consultationDtl=consulationPrescriptionDetailsRepo.getprecsriptionPath(appointmentDtlsEntity.getAdApptId());
					AppointmentDetailsResponseDTO appointmentDetailsResponseDTO = new AppointmentDetailsResponseDTO();
					appointmentDetailsResponseDTO.setAppointmentId(appointmentDtlsEntity.getAdApptId());
					appointmentDetailsResponseDTO.setAppointmentDate(
							new SimpleDateFormat("yyyy-MM-dd").format(appointmentDtlsEntity.getAdApptDateFk()));
					appointmentDetailsResponseDTO
							.setDoctorId(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());
					appointmentDetailsResponseDTO
							.setDoctorname(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdDrName());
					appointmentDetailsResponseDTO.setTimestamp(appointmentDtlsEntity.getAdApptSlotFk());
					appointmentDetailsResponseDTO.setPath(filePath + appointmentDtlsEntity.getAdApptId() + ".pdf");
					// appointmentDetailsResponseDTO.setPath(consultationDtl.getCtPrescriptionPath());
					completedAppointmentDTOs.add(appointmentDetailsResponseDTO);
				}
			} else {
				LOGGER.error("No Appointments Found..");
				throw new PatientRegException(new ExceptionJSONInfoDTO(AuthErrorConstant.NO_APPOINTMENT_FOUND,
						AuthConstant.NO_APPOINTMENT_FOUND));
			}
		} catch (PatientRegException e1) {
			LOGGER.error("Exception while getting appointment details.");
			throw e1;
		} catch (Exception e) {
			LOGGER.error("Exception while getting appointment details.");
			e.printStackTrace();
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
					AuthConstant.INVALID_REQUEST));
		}
		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		response.setResponse(completedAppointmentDTOs);
		response.setStatus(true);
		return response;
	}

	@Override
	public ResponseWrapper<BulkPatientDetailsResponse> bulkPatientRegistration(
			RequestWrapper<BulkPatientRegistrationDTO> bulkRegistrationRequest) throws Exception {
		ResponseWrapper<BulkPatientDetailsResponse> response = new ResponseWrapper<BulkPatientDetailsResponse>();
		String filePath = null;
		String excelFileInfo[] = null;
		String excelFileName = null;
		byte[] data = null;
		if (null != bulkRegistrationRequest
				&& null != bulkRegistrationRequest.getRequest().getExcelFileOfBulkPatientDtls()
				&& !bulkRegistrationRequest.getRequest().getExcelFileOfBulkPatientDtls().equals("")) {
			filePath = prescriptionPath + pathSeperator + "bulk_patient_details" + pathSeperator;
			excelFileInfo = bulkRegistrationRequest.getRequest().getExcelFileOfBulkPatientDtls().split(",");
			excelFileName = bulkRegistrationRequest.getRequest().getFileName().contains("xlsx")
					? bulkRegistrationRequest.getRequest().getDoctorUserID() + "_" + LocalDate.now() + ".xlsx"
					: bulkRegistrationRequest.getRequest().getDoctorUserID() + "_" + LocalDate.now() + ".xls";
			data = DatatypeConverter.parseBase64Binary(excelFileInfo[1].trim());
		} else {
			LOGGER.info("Exception while processing base64 excel file.");
			throw new DateParsingException(
					new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_REQUEST, AuthConstant.INVALID_REQUEST));
		}

		uploadBulkPatientDtlsFile(filePath, excelFileName, data);
		BulkPatientDetails bulkPatientDetails = readDataFromBulkPatientDtlsFile(filePath, excelFileName);
		if (bulkPatientDetails.getPatientList() != null && !bulkPatientDetails.getPatientList().isEmpty()) {
			saveBulkPatientDetails(bulkPatientDetails.getPatientList(), bulkPatientDetails.getMobileList(),
					bulkRegistrationRequest.getRequest().getExcelFileOfBulkPatientDtls());
			bulkRegistrationRequest.getRequest().getExcelFileOfBulkPatientDtls().split(",");
		}
		createPatientDataReport(filePath, excelFileName, bulkPatientDetails.getPatientList());
		File file = new File(filePath + excelFileName);
		FileInputStream fis = new FileInputStream(file);
		byte[] excelData = new byte[(int) file.length()];
		fis.read(excelData);
		fis.close();
		BulkPatientDetailsResponse bulkPatientDetailsResponse = new BulkPatientDetailsResponse();
		bulkPatientDetailsResponse.setFileData(excelData);
		bulkPatientDetailsResponse.setFileName(excelFileName);
		bulkPatientDetailsResponse.setMessage(AuthConstant.PATIENT_REGISTRATION_SUCCESS);
		response.setStatus(true);
		LOGGER.info("API success, insertion in all tables successfull");
		response.setResponse(bulkPatientDetailsResponse);
		try {
			Files.deleteIfExists(Paths.get(filePath + excelFileName));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return response;
	}

	private void createPatientDataReport(String filePath, String excelFileName, List<PatientDetailsDTO> patientList) {
		LOGGER.info("Inside createPatientDataReport method excecution");
		FileInputStream inputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(new File(filePath + excelFileName));
			if (excelFileName.contains("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				workbook = new HSSFWorkbook(inputStream);
			}
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();

			// Add additional column for results
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Cell cell = currentRow.createCell(currentRow.getLastCellNum());
				if (currentRow.getRowNum() == 0)
					cell.setCellValue(AuthConstant.EXCEL_DOB_COLUMN);
			}

			Map<Integer, String> columnsMap = new HashMap<Integer, String>(); // Create map
			Row row = firstSheet.getRow(0);
			short minColIx = row.getFirstCellNum();
			short maxColIx = row.getLastCellNum();
			for (short colIx = minColIx; colIx < maxColIx; colIx++) {
				Cell cell = row.getCell(colIx);
				columnsMap.put(cell.getColumnIndex(), cell.getStringCellValue());
			}
			iterator = firstSheet.iterator();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				if (!isRowEmpty(nextRow)) {

					boolean nameflag = false;
					boolean mobileflag = false;

					if (nextRow.getRowNum() == 0) {
						continue;
					}

					for (int i = 0; i < columnsMap.size(); i++) {
						Cell cell = nextRow.getCell(i);
						switch (columnsMap.get(i)) {
						case AuthConstant.PATIENT_NAME:
							if (patientList != null && cell != null) {
								if (patientList.stream().anyMatch(
										p -> p.getPatientName().equalsIgnoreCase(cell.getStringCellValue()))) {
									nameflag = true;
									break;
								} else {
									nameflag = false;
								}
							}
							break;
						case AuthConstant.MOBILE_NUMBER:
							if (patientList != null && cell != null) {
								if (patientList.stream().anyMatch(p -> p.getPtMobileNo().toString()
										.equalsIgnoreCase(new BigDecimal(cell.getNumericCellValue()).toString()))) {
									mobileflag = true;
									break;
								} else {
									mobileflag = false;
								}
							}
							break;
						case AuthConstant.EXCEL_DOB_COLUMN:
							if (mobileflag && nameflag) {
								if (cell == null) {
									Cell newCell = nextRow.createCell(i);
									newCell.setCellValue("Success");
								} else {
									cell.setCellValue("Success");
								}
							} else {
								if (cell == null) {
									Cell newCell = nextRow.createCell(i);
									newCell.setCellValue("Failed");
								} else {
									cell.setCellValue("Failed");
								}
							}
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception while reading patient details from excel file.");
			e.printStackTrace();
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.READ_BULK_FILE_FAILED,
					AuthConstant.READ_BULK_FILE_FAILED));
		} finally {
			try {
				inputStream.close();
				FileOutputStream outputStream = new FileOutputStream(filePath + excelFileName);
				workbook.write(outputStream);
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("CreatePatientDataReport method excecution completed");
	}
	
	private void uploadBulkPatientDtlsFile(String filePath, String excelFileName, byte[] data) {
		try {
			Files.deleteIfExists(Paths.get(filePath + excelFileName));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File directory = new File(filePath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		File file = new File(filePath + excelFileName);
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			LOGGER.info("write data to file");
			outputStream.write(data);
		} catch (IOException e) {
			LOGGER.error("Exception while uploading patient details excel file.");
			e.printStackTrace();
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.BULK_FILE_UPLOAD_FAILED,
					AuthConstant.BULK_FILE_UPLOAD_FAILED));
		}
	}

	public BulkPatientDetails readDataFromBulkPatientDtlsFile(String excelFilePath, String excelFileName) {
		List<PatientDetailsDTO> patientList = new ArrayList<PatientDetailsDTO>();
		FileInputStream inputStream = null;
		List<String> ptMobileList = new ArrayList<String>();
		Workbook workbook = null;
		BulkPatientDetails bulkPatientDetails = new BulkPatientDetails();
		try {
			inputStream = new FileInputStream(new File(excelFilePath + excelFileName));
			if (excelFileName.contains("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				workbook = new HSSFWorkbook(inputStream);
			}
			Sheet firstSheet = workbook.getSheetAt(0);

			Map<Integer, String> columnsMap = new HashMap<Integer, String>(); // Create map
			Row row = firstSheet.getRow(0);
			short minColIx = row.getFirstCellNum();
			short maxColIx = row.getLastCellNum();
			for (short colIx = minColIx; colIx < maxColIx; colIx++) {
				Cell cell = row.getCell(colIx);
				columnsMap.put(cell.getColumnIndex(), cell.getStringCellValue());
			}

			Iterator<Row> iterator = firstSheet.iterator();
			first: while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				if (!isRowEmpty(nextRow)) {
					if (nextRow.getRowNum() == 0) {
						continue;
					}
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					PatientDetailsDTO patientDetailsDTO = new PatientDetailsDTO();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (columnsMap.get(cell.getColumnIndex())) {
						case AuthConstant.PATIENT_NAME:
							if (cell.getStringCellValue() == null || cell.getStringCellValue().isEmpty()) {
								continue first;
							}
							patientDetailsDTO.setPatientName(cell.getStringCellValue());
							break;
						case AuthConstant.EMAIL_ID:
							if (!validate.validateEmail(cell.getStringCellValue())) {
								continue;
							}
							patientDetailsDTO.setPtEmailID(cell.getStringCellValue());
							break;
						case AuthConstant.MOBILE_NUMBER:
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
								if (bd == null || !validate.validateMobileNo(bd.longValue())) {
									continue first;
								}
								patientDetailsDTO.setPtMobileNo(bd.longValue());
								ptMobileList.add(bd.toPlainString());
							}
							break;
						case AuthConstant.PATIENT_DOB:
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
									&& validate.validateDob(sdf.format(cell.getDateCellValue()))) {
								patientDetailsDTO.setPtDateOfBirth(cell.getDateCellValue());
							} else if (cell.getCellType() == Cell.CELL_TYPE_STRING
									&& validate.validateDob(cell.getStringCellValue())) {
								patientDetailsDTO.setPtDateOfBirth(sdf.parse(cell.getStringCellValue()));
							} else {
								continue;
							}
							break;
						}
					}
					if (patientNullCheck(patientDetailsDTO) && !patientList.stream()
							.anyMatch(p -> p.getPtMobileNo().equals(patientDetailsDTO.getPtMobileNo()))) {
						patientList.add(patientDetailsDTO);
					}
				}
			}
			
			bulkPatientDetails.setPatientList(patientList);
			bulkPatientDetails.setMobileList(ptMobileList);
		} catch (Exception e) {
			LOGGER.error("Exception while reading patient details from excel file.");
			e.printStackTrace();
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.READ_BULK_FILE_FAILED,
					AuthConstant.READ_BULK_FILE_FAILED));
		} finally {
			try {
				inputStream.close();
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("ReadDataFromBulkPatientDtlsFile exceution completed");
		return bulkPatientDetails;
	}
	
	private boolean patientNullCheck(PatientDetailsDTO patient) {

		if (patient.getPatientName() != null && patient.getPtMobileNo() != null) {
			return true;
		} else
			return false;
	}

	@SuppressWarnings("unused")
	private void saveBulkPatientDetails(List<PatientDetailsDTO> patientList, List<String> mobileList, String file)
			throws Exception {
		LOGGER.info("Inside saveBulkPatientDetails method exceution");
		List<PatientPersonalDetailEntity> patientEntities = new ArrayList<PatientPersonalDetailEntity>();
		List<AuditPatientPersonalDetailEntity> auditedEntities = new ArrayList<AuditPatientPersonalDetailEntity>();
		List<PatientPersonalDetailEntity> savedPatientEntities = new ArrayList<PatientPersonalDetailEntity>();
		List<DoctorPatientMapDtlsEntity> doctorPatientMapDtlsEntities = new ArrayList<DoctorPatientMapDtlsEntity>();
		List<String> existedMobileNumber = new ArrayList<String>();
		List<DoctorPatientMapDtlsEntity> nonrepeateList = new ArrayList<DoctorPatientMapDtlsEntity>();
		Map<String, String> ptDetails = new HashMap<String, String>();
		ScribeRegEntity scribeEntity = userDto.getRole().equalsIgnoreCase("scribe")
				? scribeRegRepo.findScribe(userDto.getUserName().toLowerCase())
				: null;
		existedMobileNumber = patientRegistrationRepository.checkIfPtMobileNoExist(mobileList);
		String excelpath = null;
		
		try {
			if (null != existedMobileNumber && existedMobileNumber.size() > 0) {
				for (String ptDetail : existedMobileNumber) {
					ptDetails.put(ptDetail.split("_")[0], ptDetail.split("_")[1]);
				}
			}
			
			for (PatientDetailsDTO patientDetailsDTO : patientList) {
				if (null != existedMobileNumber && existedMobileNumber.size() > 0
						&& null != ptDetails.get(String.valueOf(patientDetailsDTO.getPtMobileNo()))) {

					String userID = ptDetails.get(String.valueOf(patientDetailsDTO.getPtMobileNo()));
					DoctorPatientMapDtlsEntity existedEntity = doctorPatientMapRepo
							.existsBySameDoctor(userDto.getUserName(), userID);
					if (existedEntity != null) {
						if(existedEntity.getDpmdStatus().equalsIgnoreCase("N")) {
							existedEntity.setDpmdStatus("Y");
							doctorPatientMapRepo.save(existedEntity);	
						}
					} else {
						DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity = new DoctorPatientMapDtlsEntity();
						doctorPatientMapDtlsEntity
								.setDpmdDrUserIdFk(userDto.getRole().equalsIgnoreCase("doctor") ? userDto.getUserName()
										: scribeEntity.getScrbdrUserIDfk());
						doctorPatientMapDtlsEntity.setDpmdCreatedBy(userDto.getUserName());
						doctorPatientMapDtlsEntity.setDpmdCreatedTmstmp(LocalDateTime.now());
						doctorPatientMapDtlsEntity.setDpmdStatus("Y");
						doctorPatientMapDtlsEntity
								.setDpmdPtUserIdFk(ptDetails.get(String.valueOf(patientDetailsDTO.getPtMobileNo())));
						doctorPatientMapDtlsEntities.add(doctorPatientMapDtlsEntity);
					}

				} else {
					PatientPersonalDetailEntity entity = new PatientPersonalDetailEntity();
					entity.setPtUserID("P" + String.valueOf(patientDetailsDTO.getPtMobileNo()));
					entity.setPtPassword(RandomStringUtil.randomAlphaNumeric(10, "LGN-PWD"));
					entity.setPtMobNo(patientDetailsDTO.getPtMobileNo());
					entity.setPtFullName(patientDetailsDTO.getPatientName().toUpperCase());
					if (patientDetailsDTO.getPtDateOfBirth() != null) {
						// changing date format
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String formatDate = sdf.format(patientDetailsDTO.getPtDateOfBirth());
						entity.setDob(sdf.parse(formatDate));
					}
					entity.setCreatedDate(LocalDateTime.now());
					entity.setIsactive('Y');
					entity.setIsRegByIpan('N');
					entity.setProfileFlag('N');
					if (null != patientDetailsDTO.getPtEmailID()) {
						entity.setPtEmail(patientDetailsDTO.getPtEmailID().toUpperCase());
					}
					/* Load the Patient Profile photo to file system */
					if (null != patientDetailsDTO.getPtProfilePhoto() && patientDetailsDTO.getPtProfilePhoto() != "") {
						PatientResponseDto path = authUtil.savePatientProfilePhoto(
								patientDetailsDTO.getPtProfilePhoto(), patientDetailsDTO.getPtMobileNo(),
								String.valueOf(patientDetailsDTO.getPtMobileNo()));
						entity.setProfilePhotoPath(path.getMessage());
					}
					patientEntities.add(entity);
				}
			}

			savedPatientEntities = patientRegistrationRepository.saveAll(patientEntities);

			LOGGER.info("Saved user Data to Patient registration");
		} catch (Exception e) {
			LOGGER.error("Exception while Patient registration..");
			e.printStackTrace();
			throw e;
		}
		
		// audited entry
		if (savedPatientEntities.size() > 0) {
			try {
				for (PatientPersonalDetailEntity patientPersonalDetailEntity : savedPatientEntities) {
					AuditPatientPersonalDetailEntity auditEntity = new AuditPatientPersonalDetailEntity();
					BeanUtils.copyProperties(patientPersonalDetailEntity, auditEntity);
					auditEntity.setAudUserId(patientPersonalDetailEntity.getPtUserID());
					auditedEntities.add(auditEntity);
				}
				auditRepo.saveAll(auditedEntities);
				LOGGER.info("Saved user Data to audit patient registration");
			} catch (Exception e) {
				LOGGER.error("Exception while audit patient registration..");
				e.printStackTrace();
				throw e;
			}
		}

		if (null != savedPatientEntities && savedPatientEntities.size() > 0) {
			for (PatientPersonalDetailEntity patientPersonalDetailEntity : savedPatientEntities) {
				if (createUserForPatientByScribe(patientPersonalDetailEntity)) {
					if (userDto.getRole().equalsIgnoreCase("doctor") || userDto.getRole().equalsIgnoreCase("scribe")) {
						DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity = new DoctorPatientMapDtlsEntity();
						doctorPatientMapDtlsEntity
								.setDpmdDrUserIdFk(userDto.getRole().equalsIgnoreCase("doctor") ? userDto.getUserName()
										: scribeEntity.getScrbdrUserIDfk());
						doctorPatientMapDtlsEntity.setDpmdCreatedBy(userDto.getUserName());
						doctorPatientMapDtlsEntity.setDpmdCreatedTmstmp(LocalDateTime.now());
						doctorPatientMapDtlsEntity.setDpmdStatus("Y");
						doctorPatientMapDtlsEntity
								.setDpmdPtUserIdFk(String.valueOf("P" + patientPersonalDetailEntity.getPtMobNo()));
						doctorPatientMapDtlsEntities.add(doctorPatientMapDtlsEntity);
					}
					LOGGER.info("User-Management Api Call SUCCESS");
				} else {
					userLoginRepositiory.deleteByUserId(patientPersonalDetailEntity.getPtUserID());
				}

			}
		}
		
		try {
			patientRegistrationByScribeRepository.saveAll(doctorPatientMapDtlsEntities);
			LOGGER.info("Saved user Data to doctor to patient map details");
		} catch (Exception e) {
			LOGGER.error("Exception while inserting doctor to patient map details..");
			e.printStackTrace();
			throw e;
		}
		LOGGER.info("saveBulkPatientDetails method excecution completed");
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public ResponseWrapper<PatientResponseDto> saveRegistrationDetails(
			@Valid RequestWrapper<PatientDetailsDTO> patientDetailsDTO) {
		// TODO Auto-generated method stub
		ResponseWrapper<PatientResponseDto> response = null;
		response = (ResponseWrapper<PatientResponseDto>) AuthUtil.getMainResponseDto(patientDetailsDTO);
		PatientPersonalDetailEntity entitydtls = null;
		entitydtls = patientRegistrationRepository
				.existsByPatientMobNo(String.valueOf(patientDetailsDTO.getRequest().getPtMobileNo()));
		if (entitydtls != null) {
			String ptUserID = "P" + patientDetailsDTO.getRequest().getPtMobileNo();
			String ptStatus = "Y";
			DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity = patientRegistrationByScribeRepository
					.findByDpmdDrUserIdFkAndDpmdPtUserIdFkAndDpmdStatus(
							patientDetailsDTO.getRequest().getDoctorUserID(), ptUserID, ptStatus);
			if (null != doctorPatientMapDtlsEntity) {
				LOGGER.info("Patient alreday register with same doctor");
				PatientResponseDto patientResponseDto = new PatientResponseDto();
				patientResponseDto
						.setMessage("Patient registration by doctor is successful, you can login to protean clinic");
				response.setResponse(patientResponseDto);
				response.setStatus(true);
				LOGGER.info("API success, insertion in all tables successfull");
				return response;
			} else {
				LOGGER.error("given userid already exists");
				LOGGER.info("Inside for saving to map dtls  table if data insertion successfull in usr_dtls table");
				ScribeRegEntity scribeEntity = null;
				if (userDto.getRole().equalsIgnoreCase("scribe")) {
					LOGGER.info("if user role is scribe then fetch scribe all details ");
					scribeEntity = scribeRegRepo.findScribe(userDto.getUserName().toLowerCase());
					LOGGER.info("userid From session : " + userDto.getUserName());
					LOGGER.info("userid From scribe entity : " + scribeEntity.getScrbdrUserIDfk());
				}
				doctorPatientMapDtlsEntity = new DoctorPatientMapDtlsEntity();
				doctorPatientMapDtlsEntity.setDpmdCreatedBy(userDto.getUserName());
				doctorPatientMapDtlsEntity.setDpmdCreatedTmstmp(LocalDateTime.now());
				doctorPatientMapDtlsEntity.setDpmdStatus("Y");
				doctorPatientMapDtlsEntity.setDpmdPtUserIdFk(entitydtls.getPtUserID());// changed by girishk
				if (userDto.getRole().equalsIgnoreCase("doctor")) {
					doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(userDto.getUserName());
				} else {
					doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(scribeEntity.getScrbdrUserIDfk());
				}
				doctorPatientMapDtlsEntity = patientRegistrationByScribeRepository.save(doctorPatientMapDtlsEntity);
				if (doctorPatientMapDtlsEntity != null) {
					PatientResponseDto patientResponseDto = new PatientResponseDto();
					patientResponseDto.setMessage(
							"Patient registration by doctor is successful, you can login to protean clinic");
					response.setResponse(patientResponseDto);
					response.setStatus(true);
					LOGGER.info("API success, insertion in all tables successfull");
					return response;
				} else {
					response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
							AuthConstant.PATIENT_REG_ERROR));
				}
			}

		}
		String newRandomPassword = RandomStringUtil.randomAlphaNumeric(10, "LGN-PWD");
		LOGGER.info("new random password" + newRandomPassword);
		PatientDetailsDTO request = patientDetailsDTO.getRequest();
		String userId = String.valueOf(request.getPtMobileNo());
		PatientPersonalDetailEntity entity = new PatientPersonalDetailEntity();
		entity.setPtUserID("P" + userId);
		entity.setPtPassword(newRandomPassword);
		entity.setPtMobNo(request.getPtMobileNo());
		entity.setDob(request.getPtDateOfBirth());
		entity.setPtFullName(request.getPatientName().toUpperCase());
		entity.setCreatedDate(LocalDateTime.now());
		entity.setIsactive('Y');
		entity.setIsRegByIpan('N');
		entity.setProfileFlag('N');
		if (null != request.getPtEmailID()) {
			entity.setPtEmail(request.getPtEmailID().toUpperCase());
		}
		/* Load the Patient Profile photo to file system */
		if (request.getPtProfilePhoto() != null || request.getPtProfilePhoto() != "") {
			PatientResponseDto path = authUtil.savePatientProfilePhoto(request.getPtProfilePhoto(),
					request.getPtMobileNo(), userId);
			entity.setProfilePhotoPath(path.getMessage());
		}
		PatientPersonalDetailEntity savedEntity = null;
		try {
			if (userDto.getRole().equalsIgnoreCase("doctor") || userDto.getRole().equalsIgnoreCase("scribe")) {
				savedEntity = patientRegistrationRepository.save(entity);
			} else {
				LOGGER.info("Logged in user is not doctor or scribe");
				response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
						AuthConstant.PATIENT_REG_FAIL));
			}
			LOGGER.info("Saved user Data to Patient registration");
			auditPatientService.auditRegistrationServicePersonalDetail(savedEntity, userId);
		} catch (Exception e) {
			LOGGER.error("Exception while Patient registration..");
			e.printStackTrace();
			throw e;
		}

		if (savedEntity != null) {
			LOGGER.info("Inside for saving to user dtls table if data insertion successfull in pt_reg_dtls table");
			if (createUserForPatientByScribe(savedEntity)) {
				LOGGER.info("Inside for saving to map dtls  table if data insertion successfull in usr_dtls table");
				LOGGER.info("Saved user Data to Patient registration");
				ScribeRegEntity scribeEntity = null;
				DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity = new DoctorPatientMapDtlsEntity();
				if (userDto.getRole().equalsIgnoreCase("scribe")) {
					LOGGER.info("if user role is scribe then fetch scribe all details ");
					scribeEntity = scribeRegRepo.findScribe(userDto.getUserName().toLowerCase());
					LOGGER.info("userid From session : " + userDto.getUserName());
					LOGGER.info("userid From scribe entity : " + scribeEntity.getScrbdrUserIDfk());
				}
				doctorPatientMapDtlsEntity.setDpmdCreatedBy(userDto.getUserName());
				doctorPatientMapDtlsEntity.setDpmdCreatedTmstmp(LocalDateTime.now());
				doctorPatientMapDtlsEntity.setDpmdStatus("Y");
				doctorPatientMapDtlsEntity.setDpmdPtUserIdFk(String.valueOf("P" + request.getPtMobileNo()));
				if (userDto.getRole().equalsIgnoreCase("doctor")) {
					doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(userDto.getUserName());
				} else {
					doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(scribeEntity.getScrbdrUserIDfk());
				}
				DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity2 = null;
				doctorPatientMapDtlsEntity2 = patientRegistrationByScribeRepository.save(doctorPatientMapDtlsEntity);
				if (doctorPatientMapDtlsEntity2 != null) {
					PatientResponseDto patientResponseDto = new PatientResponseDto();
					patientResponseDto.setMessage(
							"Patient registration by doctor is successful, you can login to protean clinic");
					response.setResponse(patientResponseDto);
					response.setStatus(true);
					LOGGER.info("API success, insertion in all tables successfull");
					// sms email integration
					NotifyRequestDTO restRequest = new NotifyRequestDTO();
					restRequest.setEmailId(entity.getPtEmail());
					restRequest.setPassword(newRandomPassword);
					restRequest.setTemplateType(AuthConstant.PATIENT_REGISTER);
					// restRequest.setTemplateType(AuthConstant.OTP_FOR);
					restRequest.setUserId(userId);
					restRequest.setMobileNo(request.getPtMobileNo());
					restRequest.setSendType(null != entity.getPtEmail() && !entity.getPtEmail().isEmpty()
							? AuthConstant.NOTIFY_BOTH_SEND_TYPE
							: "sms");// added by girishk
					LOGGER.info("Request Created for calling Email and SMS notification for sending Random Password");
					MainResponseDTO<NotifyResponseDTO> responseEmail = null;
					responseEmail = sendRandomPassByEmail(restRequest);
					if (responseEmail.isStatus()) {
						LOGGER.info("Random Password Email sent successfully");
						patientResponseDto.setMessage(
								"Patient registration by doctor is successful, you can login to protean clinic");
						response.setResponse(patientResponseDto);
						response.setStatus(true);
					} else {
						LOGGER.info("Random Password Email sent successfully");
						patientResponseDto.setMessage(
								"Something went wrong while sending email,Patient registartion by doctor is sucessfull, you can login to protean clinic");
						response.setResponse(patientResponseDto);
						response.setStatus(true);
					}

				} else {
					// call to rollback transaction
					patientRegistrationRepository.deleteByUserId(savedEntity.getPtUserID());
					userLoginRepositiory.deleteByUserId(savedEntity.getPtUserID());
					LOGGER.error("issue while saving to mapping table");
					response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
							AuthConstant.PATIENT_REG_ERROR));
				}
			} else {
				// call to rollback transaction
				patientRegistrationRepository.deleteByUserId(savedEntity.getPtUserID());
				LOGGER.error(AuthConstant.PATIENT_REGISTRATION_FAIL);
				response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
						AuthConstant.PATIENT_REG_ERROR));
			}
		} else {
			LOGGER.error(AuthConstant.PATIENT_REGISTRATION_FAIL);
			response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
					AuthConstant.PATIENT_REG_ERROR));
		}
		return response;

	}

	// Create user API added by sayali for scribe
	private boolean createUserForPatientByScribe(PatientPersonalDetailEntity persistEntity) {
		try {
			LOGGER.info("USER MANAGEMENT API Called");
			MainRequestDTO<CreateUserRequestDTO> mainRequestDTO = new MainRequestDTO<>();
			CreateUserRequestDTO createUser = new CreateUserRequestDTO();
			createUser.setUserFullName(persistEntity.getPtFullName());
			if (null != persistEntity.getPtEmail()) {
				createUser.setEmail(persistEntity.getPtEmail());
			} else {
				createUser.setEmail("");
			}
			createUser.setMobileNumber(persistEntity.getPtMobNo());
			createUser.setPassword(persistEntity.getPtPassword());
			// createUser.setRoleName(PatientRegConstant.PATIENT);
			createUser.setUserId("P" + persistEntity.getPtMobNo());
			createUser.setUserType(AuthConstant.PATIENT);
			createUser.setStatusFlag("patientByScribe");
			mainRequestDTO.setRequest(createUser);
			LOGGER.info("USER MANAGEMENT API Called with userID " + createUser.getUserId() + "And UserName "
					+ createUser.getUserFullName());
			HttpEntity<MainRequestDTO<CreateUserRequestDTO>> requestEntity = new HttpEntity<>(mainRequestDTO);
			ParameterizedTypeReference<MainResponseDTO<CreateUserResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<CreateUserResponseDTO>>() {
			};
			ResponseEntity<MainResponseDTO<CreateUserResponseDTO>> response = template.exchange(createUserURL,
					HttpMethod.POST, requestEntity, parameterizedResponse);
			LOGGER.info("USER MANAGEMENT API Status : " + response.getBody().isStatus());
			return response.getBody().isStatus();
		} catch (Exception e) {
			LOGGER.error("CALL TO USER MANAGEMENT API FAILED");
			e.printStackTrace();
			throw e;
		}
	}

	// SMS email integration added by sayali
	private MainResponseDTO<NotifyResponseDTO> sendRandomPassByEmail(NotifyRequestDTO restRequest) {
		LOGGER.info("Request received Inside SMS Email Notification send");
		MainRequestsDTO<NotifyRequestDTO> mainRequest = new MainRequestsDTO<>();
		ResponseEntity<MainResponseDTO<NotifyResponseDTO>> responseEntity = null;
		mainRequest.setId(AuthConstant.API_ID);
		mainRequest.setVersion(AuthConstant.API_VERSION);
		mainRequest.setMethod(AuthConstant.API_METHOD);
		mainRequest.setRequesttime(
				Date.from(DateUtils.getCurrentLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()));
		mainRequest.setRequest(restRequest);
		HttpEntity<MainRequestsDTO<NotifyRequestDTO>> requestEntity = new HttpEntity<MainRequestsDTO<NotifyRequestDTO>>(
				mainRequest);
		ParameterizedTypeReference<MainResponseDTO<NotifyResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<NotifyResponseDTO>>() {
		};
		ResponseEntity<MainResponseDTO<NotifyResponseDTO>> response = template.exchange(emailUrl, HttpMethod.POST,
				requestEntity, parameterizedResponse);
		return response.getBody();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseWrapper<PatientResponseDto> savePatientRegistrationDetails(
			@Valid RequestWrapper<PatientDetailsDTO> patientDetailsDTO) {
		// TODO Auto-generated method stub
		ResponseWrapper<PatientResponseDto> response = null;
		String doctorID = null;
		try {
			if (null != patientDetailsDTO.getRequest().getPtEmailID()) {
				if (patientDetailsDTO.getRequest().getPtEmailID().equalsIgnoreCase("patientUrl@registration.com")) {
					doctorID = AESUtils.decrypt(patientDetailsDTO.getRequest().getDoctorUserID());
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response = (ResponseWrapper<PatientResponseDto>) AuthUtil.getMainResponseDto(patientDetailsDTO);
		PatientPersonalDetailEntity entitydtls = null;
		entitydtls = patientRegistrationRepository
				.existsByPatientMobNo(String.valueOf(patientDetailsDTO.getRequest().getPtMobileNo()));
		if (entitydtls != null) {
			if (null != patientDetailsDTO.getRequest().getPtEmailID()) {
				if (patientDetailsDTO.getRequest().getPtEmailID().equalsIgnoreCase("patientUrl@registration.com")) {
					if ((patientDetailsDTO.getRequest().getPatientName().toUpperCase().trim().replace(" ", "")
							.equals(entitydtls.getPtFullName().trim().replace(" ", "")))) {
						DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity;
						if (doctorID != null) {
							doctorPatientMapDtlsEntity = patientRegistrationByScribeRepository
									.findByDpmdDrUserIdFkAndDpmdPtUserIdFkAndDpmdStatus(doctorID,
											entitydtls.getPtUserID(), "Y");
							if (null != doctorPatientMapDtlsEntity) {
								throw new DateParsingException(new ExceptionJSONInfoDTO(
										AuthErrorConstant.PATIENT_REGISTRATION_FAIL, AuthConstant.Patient_Exists));
							}
						}
						// condition check for single calender also otherwise need to change

					}
				}
			}
			if (!(patientDetailsDTO.getRequest().getPatientName().toUpperCase().trim().replace(" ", "")
					.equals(entitydtls.getPtFullName().trim().replace(" ", "")))) {
				LOGGER.info("Patient alreday register with same doctor but entering different name");
				throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
						AuthConstant.Patient_Registration_ERROR));

			}

			if (entitydtls.getPtEmail() == null) {
				if (null != patientDetailsDTO.getRequest().getPtEmailID()) {
					entitydtls.setPtEmail(patientDetailsDTO.getRequest().getPtEmailID());
					{
						patientRegistrationRepository.save(entitydtls);
					}
				}

			}
			String ptUserID = "P" + patientDetailsDTO.getRequest().getPtMobileNo();
			String ptStatus = "Y";
			DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity;
			if (doctorID != null) {
				doctorPatientMapDtlsEntity = patientRegistrationByScribeRepository
						.findByDpmdDrUserIdFkAndDpmdPtUserIdFkAndDpmdStatus(doctorID, ptUserID, ptStatus);

			} else {
				doctorPatientMapDtlsEntity = patientRegistrationByScribeRepository
						.findByDpmdDrUserIdFkAndDpmdPtUserIdFkAndDpmdStatus(
								patientDetailsDTO.getRequest().getDoctorUserID(), ptUserID, ptStatus);

			}

			if (null != doctorPatientMapDtlsEntity) {
				LOGGER.info("Patient alreday register with same doctor");
				PatientResponseDto patientResponseDto = new PatientResponseDto();
				patientResponseDto.setPtUserID(doctorPatientMapDtlsEntity.getDpmdPtUserIdFk());
				patientResponseDto
						.setMessage("Patient registartion by doctor is sucessfull, you can login to protean clinic");
				response.setResponse(patientResponseDto);
				response.setStatus(true);
				LOGGER.info("API success, insertion in all tables successfull");
				return response;
			} else {
				LOGGER.error("given userid already exists");
				LOGGER.info("Inside for saving to map dtls  table if data insertion successfull in usr_dtls table");
				// ScribeRegEntity scribeEntity=null;
				/*
				 * if(userDto.getRole().equalsIgnoreCase("scribe")) {
				 * LOGGER.info("if user role is scribe then fetch scribe all details ");
				 * scribeEntity= scribeRegRepo.findScribe(userDto.getUserName().toLowerCase());
				 * LOGGER.info("userid From session : "+userDto.getUserName());
				 * LOGGER.info("userid From scribe entity : "+scribeEntity.getScrbdrUserIDfk());
				 * }
				 */
				doctorPatientMapDtlsEntity = new DoctorPatientMapDtlsEntity();
				doctorPatientMapDtlsEntity.setDpmdCreatedBy(userDto.getUserName());
				doctorPatientMapDtlsEntity.setDpmdCreatedTmstmp(LocalDateTime.now());
				doctorPatientMapDtlsEntity.setDpmdStatus("Y");
				doctorPatientMapDtlsEntity.setDpmdPtUserIdFk(entitydtls.getPtUserID());// changed by girishk
				if (null != doctorID) {
					doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(doctorID);
				} else {
					doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(patientDetailsDTO.getRequest().getDoctorUserID());
				}

				/*
				 * else {
				 * doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(scribeEntity.getScrbdrUserIDfk()
				 * ); }
				 */
				doctorPatientMapDtlsEntity = patientRegistrationByScribeRepository.save(doctorPatientMapDtlsEntity);
				if (doctorPatientMapDtlsEntity != null) {
					PatientResponseDto patientResponseDto = new PatientResponseDto();
					patientResponseDto.setPtUserID(doctorPatientMapDtlsEntity.getDpmdPtUserIdFk());
					patientResponseDto.setMessage(
							"Patient registartion by doctor is sucessfull, you can login to protean clinic");
					response.setResponse(patientResponseDto);
					response.setStatus(true);
					LOGGER.info("API success, insertion in all tables successfull");
					return response;
				} else {
					response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
							AuthConstant.PATIENT_REG_ERROR));
				}
			}

		}
		String newRandomPassword = RandomStringUtil.randomAlphaNumeric(10, "LGN-PWD");
		LOGGER.info("new random password" + newRandomPassword);
		PatientDetailsDTO request = patientDetailsDTO.getRequest();
		String userId = String.valueOf(request.getPtMobileNo());
		PatientPersonalDetailEntity entity = new PatientPersonalDetailEntity();
		entity.setPtUserID("P" + userId);
		entity.setPtPassword(newRandomPassword);
		entity.setPtMobNo(request.getPtMobileNo());
		entity.setDob(request.getPtDateOfBirth());
		entity.setPtFullName(request.getPatientName().toUpperCase());
		entity.setCreatedDate(LocalDateTime.now());
		entity.setIsactive('Y');
		entity.setIsRegByIpan('N');
		entity.setProfileFlag('N');
		if (request.getPtEmailID() != null) {
			entity.setPtEmail(request.getPtEmailID().toUpperCase());
		}
		/* Load the Patient Profile photo to file system */
		if (request.getPtProfilePhoto() != null || request.getPtProfilePhoto() != "") {
			PatientResponseDto path = authUtil.savePatientProfilePhoto(request.getPtProfilePhoto(),
					request.getPtMobileNo(), userId);
			entity.setProfilePhotoPath(path.getMessage());
		}
		PatientPersonalDetailEntity savedEntity = null;
		try {

			savedEntity = patientRegistrationRepository.save(entity);
		} catch (Exception e) {
			LOGGER.info("Logged in user is not doctor or scribe");
			response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
					AuthConstant.PATIENT_REG_FAIL));
		}
		LOGGER.info("Saved user Data to Patient registration");
		auditPatientService.auditRegistrationServicePersonalDetail(savedEntity, userId);
		if (savedEntity != null) {
			LOGGER.info("Inside for saving to user dtls table if data insertion successfull in pt_reg_dtls table");
			if (createUserForPatientByScribe(savedEntity)) {
				LOGGER.info("Inside for saving to map dtls  table if data insertion successfull in usr_dtls table");
				LOGGER.info("Saved user Data to Patient registration");
				DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity = new DoctorPatientMapDtlsEntity();
				doctorPatientMapDtlsEntity.setDpmdCreatedBy(userDto.getUserName());
				doctorPatientMapDtlsEntity.setDpmdCreatedTmstmp(LocalDateTime.now());
				doctorPatientMapDtlsEntity.setDpmdStatus("Y");
				doctorPatientMapDtlsEntity.setDpmdPtUserIdFk(String.valueOf("P" + request.getPtMobileNo()));
				if (null != doctorID) {
					doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(doctorID);
				} else {
					doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(patientDetailsDTO.getRequest().getDoctorUserID());
				}
				DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity2 = null;
				doctorPatientMapDtlsEntity2 = patientRegistrationByScribeRepository.save(doctorPatientMapDtlsEntity);
				if (doctorPatientMapDtlsEntity2 != null) {
					PatientResponseDto patientResponseDto = new PatientResponseDto();
					patientResponseDto.setMessage(
							"Patient registartion by doctor is sucessfull, you can login to protean clinic");
					patientResponseDto.setPtUserID(doctorPatientMapDtlsEntity.getDpmdPtUserIdFk());
					response.setResponse(patientResponseDto);
					response.setStatus(true);
					LOGGER.info("API success, insertion in all tables successfull");
					// sms email integration
					NotifyRequestDTO restRequest = new NotifyRequestDTO();
					if (entity.getPtEmail() != null) {
						restRequest.setEmailId(entity.getPtEmail());
					} else {
						restRequest.setEmailId("");
					}
					restRequest.setPassword(newRandomPassword);
					// restRequest.setTemplateType(AuthConstant.FORGOT_PASS_TEMPLATE);
					restRequest.setTemplateType(AuthConstant.PATIENT_REGISTER);
					// restRequest.setTemplateType(AuthConstant.OTP_FOR);
					restRequest.setUserId(userId);
					restRequest.setMobileNo(request.getPtMobileNo());
					restRequest.setSendType(null != entity.getPtEmail() && !entity.getPtEmail().isEmpty()
							? AuthConstant.NOTIFY_BOTH_SEND_TYPE
							: "sms");// added by girishk
					LOGGER.info(
							"Request Created for calling Email for sending Password" + restRequest.getTemplateType());
					LOGGER.info("Request Created for calling Email and SMS notification for sending Random Password");
					MainResponseDTO<NotifyResponseDTO> responseEmail = null;
					responseEmail = sendRandomPassByEmail(restRequest);
					if (responseEmail.isStatus()) {
						LOGGER.info("Random Password Email sent successfully");
						patientResponseDto.setMessage(
								"Patient registartion by doctor is sucessfull, you can login to protean clinic");
						patientResponseDto.setPtUserID(doctorPatientMapDtlsEntity.getDpmdPtUserIdFk());
						response.setResponse(patientResponseDto);
						response.setStatus(true);
					} else {
						LOGGER.info("Random Password Email sent successfully");
						patientResponseDto.setMessage(
								"Something went wrong while sending email,Patient registartion by doctor is sucessfull, you can login to protean clinic");
						response.setResponse(patientResponseDto);
						response.setStatus(true);
					}

				} else {
					// call to rollback transaction
					patientRegistrationRepository.deleteByUserId(savedEntity.getPtUserID());
					userLoginRepositiory.deleteByUserId(savedEntity.getPtUserID());
					LOGGER.error("issue while saving to mapping table");
					response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
							AuthConstant.PATIENT_REG_ERROR));
				}
			} else {
				// call to rollback transaction
				patientRegistrationRepository.deleteByUserId(savedEntity.getPtUserID());
				LOGGER.error(AuthConstant.PATIENT_REGISTRATION_FAIL);
				response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
						AuthConstant.PATIENT_REG_ERROR));
			}
		} else {
			LOGGER.error(AuthConstant.PATIENT_REGISTRATION_FAIL);
			response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
					AuthConstant.PATIENT_REG_ERROR));
		}
		return response;

	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseWrapper<PatientResponseDto> savePatientRegistrationLinkDetails(
			RequestWrapper<TokenDetailsDTO> patientDetailsDTO) {

		String doctoruserID = null;
		try {
			doctoruserID = AESUtils.decrypt(patientDetailsDTO.getRequest().getDocUserID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseWrapper<PatientResponseDto> response = null;
		response = (ResponseWrapper<PatientResponseDto>) AuthUtil.getMainResponseDto(patientDetailsDTO);
		PatientPersonalDetailEntity entitydtls = null;
		entitydtls = patientRegistrationRepository
				.existsByPatientMobNo(String.valueOf(patientDetailsDTO.getRequest().getPtMobileNo()));
		if (entitydtls != null) {
			if (!(patientDetailsDTO.getRequest().getPatientName().toUpperCase().trim().replace(" ", "")
					.equals(entitydtls.getPtFullName().trim().replace(" ", "")))) {
				LOGGER.info("Patient alreday register with same doctor but entering different name");
				throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
						AuthConstant.Patient_Registration_ERROR));
			}
			/*
			 * if(entitydtls.getPtEmail()==null) {
			 * if(null!=patientDetailsDTO.getRequest().getPtEmailID()) {
			 * entitydtls.setPtEmail(patientDetailsDTO.getRequest().getPtEmailID()); {
			 * patientRegistrationRepository.save(entitydtls); } }
			 * 
			 * }
			 */
			String ptUserID = "P" + patientDetailsDTO.getRequest().getPtMobileNo();
			String ptStatus = "Y";
			DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity = patientRegistrationByScribeRepository
					.findByDpmdDrUserIdFkAndDpmdPtUserIdFkAndDpmdStatus(doctoruserID, ptUserID, ptStatus);

			if (null != doctorPatientMapDtlsEntity) {
				LOGGER.info("Patient alreday register with same doctor");
				PatientResponseDto patientResponseDto = new PatientResponseDto();
				patientResponseDto.setPtUserID(doctorPatientMapDtlsEntity.getDpmdPtUserIdFk());
				patientResponseDto.setMessage("You have been successfully registered on Dr." + doctoruserID
						+ "portal and appointment system. To fix appointments with Dr." + doctoruserID
						+ "get self assessment forms, health education videos and more from Dr,  please visit the doctor’s profile.");
				response.setResponse(patientResponseDto);
				response.setStatus(true);
				LOGGER.info("API success, insertion in all tables successfull");
				return response;
			} else {
				LOGGER.error("given userid already exists");
				LOGGER.info("Inside for saving to map dtls  table if data insertion successfull in usr_dtls table");
				// ScribeRegEntity scribeEntity=null;
				/*
				 * if(userDto.getRole().equalsIgnoreCase("scribe")) {
				 * LOGGER.info("if user role is scribe then fetch scribe all details ");
				 * scribeEntity= scribeRegRepo.findScribe(userDto.getUserName().toLowerCase());
				 * LOGGER.info("userid From session : "+userDto.getUserName());
				 * LOGGER.info("userid From scribe entity : "+scribeEntity.getScrbdrUserIDfk());
				 * }
				 */
				doctorPatientMapDtlsEntity = new DoctorPatientMapDtlsEntity();
				doctorPatientMapDtlsEntity.setDpmdCreatedBy(userDto.getUserName());
				doctorPatientMapDtlsEntity.setDpmdCreatedTmstmp(LocalDateTime.now());
				doctorPatientMapDtlsEntity.setDpmdStatus("Y");
				doctorPatientMapDtlsEntity.setDpmdPtUserIdFk(entitydtls.getPtUserID());// changed by girishk
				doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(doctoruserID);
				/*
				 * else {
				 * doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(scribeEntity.getScrbdrUserIDfk()
				 * ); }
				 */
				doctorPatientMapDtlsEntity = patientRegistrationByScribeRepository.save(doctorPatientMapDtlsEntity);
				if (doctorPatientMapDtlsEntity != null) {
					PatientResponseDto patientResponseDto = new PatientResponseDto();
					patientResponseDto.setPtUserID(doctorPatientMapDtlsEntity.getDpmdPtUserIdFk());
					patientResponseDto.setMessage("You have been successfully registered on Dr." + doctoruserID
							+ "portal and appointment system. To fix appointments with Dr." + doctoruserID
							+ "get self assessment forms, health education videos and more from Dr,  please visit the doctor’s profile.");
					response.setResponse(patientResponseDto);
					response.setStatus(true);
					LOGGER.info("API success, insertion in all tables successfull");
					return response;
				} else {
					response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
							AuthConstant.PATIENT_REG_ERROR));
				}
			}

		}
		String newRandomPassword = RandomStringUtil.randomAlphaNumeric(10, "LGN-PWD");
		LOGGER.info("new random password" + newRandomPassword);
		TokenDetailsDTO request = patientDetailsDTO.getRequest();
		String userId = String.valueOf(request.getPtMobileNo());
		PatientPersonalDetailEntity entity = new PatientPersonalDetailEntity();
		entity.setPtUserID("P" + userId);
		entity.setPtPassword(newRandomPassword);
		entity.setPtMobNo(request.getPtMobileNo());
		entity.setPtFullName(request.getPatientName().toUpperCase());
		entity.setCreatedDate(LocalDateTime.now());
		entity.setIsactive('Y');
		entity.setIsRegByIpan('N');
		entity.setProfileFlag('N');

		/* Load the Patient Profile photo to file system */
		/*
		 * if(request.getPtProfilePhoto()!=null || request.getPtProfilePhoto()!="") {
		 * PatientResponseDto path =
		 * authUtil.savePatientProfilePhoto(request.getPtProfilePhoto(),
		 * request.getPtMobileNo(), userId);
		 * entity.setProfilePhotoPath(path.getMessage()); }
		 */
		PatientPersonalDetailEntity savedEntity = null;
		try {

			savedEntity = patientRegistrationRepository.save(entity);
		} catch (Exception e) {
			LOGGER.info("Logged in user is not doctor or scribe");
			response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
					AuthConstant.PATIENT_REG_FAIL));
		}
		LOGGER.info("Saved user Data to Patient registration");
		auditPatientService.auditRegistrationServicePersonalDetail(savedEntity, userId);
		if (savedEntity != null) {
			LOGGER.info("Inside for saving to user dtls table if data insertion successfull in pt_reg_dtls table");
			if (createUserForPatientByScribe(savedEntity)) {
				LOGGER.info("Inside for saving to map dtls  table if data insertion successfull in usr_dtls table");
				LOGGER.info("Saved user Data to Patient registration");
				DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity = new DoctorPatientMapDtlsEntity();
				doctorPatientMapDtlsEntity.setDpmdCreatedBy(userDto.getUserName());
				doctorPatientMapDtlsEntity.setDpmdCreatedTmstmp(LocalDateTime.now());
				doctorPatientMapDtlsEntity.setDpmdStatus("Y");
				doctorPatientMapDtlsEntity.setDpmdPtUserIdFk(String.valueOf("P" + request.getPtMobileNo()));
				doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(doctoruserID);
				DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity2 = null;
				doctorPatientMapDtlsEntity2 = patientRegistrationByScribeRepository.save(doctorPatientMapDtlsEntity);
				if (doctorPatientMapDtlsEntity2 != null) {
					PatientResponseDto patientResponseDto = new PatientResponseDto();
					patientResponseDto.setMessage("You have been successfully registered on Dr." + doctoruserID
							+ "portal and appointment system. To fix appointments with Dr." + doctoruserID
							+ "get self assessment forms, health education videos and more from Dr,  please visit the doctor’s profile.");
					patientResponseDto.setPtUserID(doctorPatientMapDtlsEntity.getDpmdPtUserIdFk());
					response.setResponse(patientResponseDto);
					response.setStatus(true);
					LOGGER.info("API success, insertion in all tables successfull");
					// sms email integration
					NotifyRequestDTO restRequest = new NotifyRequestDTO();
					if (entity.getPtEmail() != null) {
						restRequest.setEmailId(entity.getPtEmail());
					} else {
						restRequest.setEmailId("");
					}
					restRequest.setPassword(newRandomPassword);
					// restRequest.setTemplateType(AuthConstant.FORGOT_PASS_TEMPLATE);
					restRequest.setTemplateType(AuthConstant.PATIENT_REGISTER);
					// restRequest.setTemplateType(AuthConstant.OTP_FOR);
					restRequest.setUserId(userId);
					restRequest.setMobileNo(request.getPtMobileNo());
					restRequest.setSendType(null != entity.getPtEmail() && !entity.getPtEmail().isEmpty()
							? AuthConstant.NOTIFY_BOTH_SEND_TYPE
							: "sms");// added by girishk
					LOGGER.info(
							"Request Created for calling Email for sending Password" + restRequest.getTemplateType());
					LOGGER.info("Request Created for calling Email and SMS notification for sending Random Password");
					MainResponseDTO<NotifyResponseDTO> responseEmail = null;
					responseEmail = sendRandomPassByEmail(restRequest);
					if (responseEmail.isStatus()) {
						LOGGER.info("Random Password Email sent successfully");
						patientResponseDto.setMessage("You have been successfully registered on Dr." + doctoruserID
								+ "portal and appointment system. To fix appointments with Dr." + doctoruserID
								+ "get self assessment forms, health education videos and more from Dr,  please visit the doctor’s profile.");
						patientResponseDto.setPtUserID(doctorPatientMapDtlsEntity.getDpmdPtUserIdFk());
						response.setResponse(patientResponseDto);
						response.setStatus(true);
					} else {
						LOGGER.info("Random Password Email sent successfully");
						patientResponseDto.setMessage(
								"Something went wrong while sending email,Patient registartion by doctor is sucessfull, you can login to protean clinic");
						response.setResponse(patientResponseDto);
						response.setStatus(true);
					}

				} else {
					// call to rollback transaction
					patientRegistrationRepository.deleteByUserId(savedEntity.getPtUserID());
					userLoginRepositiory.deleteByUserId(savedEntity.getPtUserID());
					LOGGER.error("issue while saving to mapping table");
					response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
							AuthConstant.PATIENT_REG_ERROR));
				}
			} else {
				// call to rollback transaction
				patientRegistrationRepository.deleteByUserId(savedEntity.getPtUserID());
				LOGGER.error(AuthConstant.PATIENT_REGISTRATION_FAIL);
				response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
						AuthConstant.PATIENT_REG_ERROR));
			}
		} else {
			LOGGER.error(AuthConstant.PATIENT_REGISTRATION_FAIL);
			response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
					AuthConstant.PATIENT_REG_ERROR));
		}
		return response;

	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public ResponseWrapper<PatientResponseDto> updateHealthIDDetails(
			@Valid RequestWrapper<HealthIdDetailsToTelemedicineDTO> healthIdDetailsDTO) {
		// TODO Auto-generated method stub
		ResponseWrapper<PatientResponseDto> response = null;
		response = (ResponseWrapper<PatientResponseDto>) AuthUtil.getMainResponseDto(healthIdDetailsDTO);
		NDHMDeatilsEntity ndhmentity = new NDHMDeatilsEntity();
		PatientPersonalDetailEntity personalentity = personalDetailsRepository
				.existsByPtMobNo(healthIdDetailsDTO.getRequest().getMobileNo());

		if (personalentity != null) {
			personalentity.setHealthID(healthIdDetailsDTO.getRequest().getHealthId());
			personalentity.setHealthNumber(healthIdDetailsDTO.getRequest().getHealthNumber());
			personalDetailsRepository.save(personalentity);
		} else {
			LOGGER.info("Patient details not exists");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
					AuthConstant.PATIENT_DETAILS_NOT_FOUND));
		}
		LoginUserEntity userentity = userLoginRepositiory.findPatientDetails(personalentity.getPtUserID());
		if (userentity != null) {
			if (healthIdDetailsDTO.getRequest().getHealthNumber() != null) {
				userentity.setIsHelathIDExists("Y");
			} else {
				userentity.setIsHelathIDExists("N");
			}

		}

		ndhmentity.setNdMobileno(Long.parseLong(healthIdDetailsDTO.getRequest().getMobileNo()));
		ndhmentity.setNdStatus("I");
		ndhmentity.setNdErrorcode(healthIdDetailsDTO.getRequest().getErrorCode());
		ndhmentity.setNdErrormessage(healthIdDetailsDTO.getRequest().getErrorMsg());
		ndhmRepo.save(ndhmentity);
		PatientResponseDto patientResponseDto = new PatientResponseDto();
		patientResponseDto.setMessage("Data updatted successfully!!!!");
		response.setResponse(patientResponseDto);
		response.setStatus(true);
		return response;

	}

	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
				return false;
			}
		}
		return true;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseWrapper<PatientRefResponseDto> getPatientRefByPatientId(
			@Valid RequestWrapper<PatientRefRequestDto> patientRefRequestDto) {
		PatientRefResponseDto patientRefResponseDto= new PatientRefResponseDto();
		ResponseWrapper<PatientRefResponseDto> response = null;
		response = (ResponseWrapper<PatientRefResponseDto>) AuthUtil.getMainResponseDto(patientRefRequestDto);
	
		//PatientPersonalDetailEntity patientPersonalDetailEntity = new PatientPersonalDetailEntity();
		PatientPersonalDetailEntity personalentity = personalDetailsRepository
				.getPatientDtlsById(patientRefRequestDto.getRequest().getHealthId());		
		if(personalentity !=null) {
		patientRefResponseDto.setReferenceNo(personalentity.getPtUserID());
		patientRefResponseDto.setDisplayName(personalentity.getPtFullName());
		}
		response.setResponse(patientRefResponseDto);
		response.setStatus(true);
		return response;

	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseWrapper<List<CareContextResponseDto>> getCareContextDtls(
			@Valid RequestWrapper<PatientRefRequestDto> getCareContextByHealthId) {
		
		List<CareContextResponseDto> careContextResponseList =new ArrayList<CareContextResponseDto>();
		ResponseWrapper<List<CareContextResponseDto>> response = null;
		response = (ResponseWrapper<List<CareContextResponseDto>>) AuthUtil.getMainResponseDto(getCareContextByHealthId);
		String healthId=getCareContextByHealthId.getRequest().getHealthId();
		if(healthId!=null) {
		List<PatientCareContextEntity> patientCareContextEntity= patientCareContextRepo.getDetailsByHealthId(healthId);
		if(patientCareContextEntity !=null) {
		for(PatientCareContextEntity patientCareContext: patientCareContextEntity) {
			CareContextResponseDto careContextResponseDto =new CareContextResponseDto();
			String careContextId=(patientCareContext.getCareContextsId()!=null)? patientCareContext.getCareContextsId():"";
			careContextResponseDto.setCareContextId(careContextId);
			String displayName=(patientCareContext.getDisplayName()!=null)? patientCareContext.getDisplayName():"";
			careContextResponseDto.setDisplayName(displayName);
			String health_Id=(patientCareContext.getHealthId()!=null)? patientCareContext.getHealthId():"";
			careContextResponseDto.setHealthId(health_Id);
			String healthNo= (patientCareContext.getHealthNo()!=null)? patientCareContext.getHealthNo():"";
			careContextResponseDto.setHealthNo(healthNo);
			String mobNo=(patientCareContext.getMobileNo()!=null)? patientCareContext.getMobileNo():"";
			careContextResponseDto.setMobileNo(mobNo);
			String patientId=(patientCareContext.getPatientId()!=null)? patientCareContext.getPatientId():"";
			careContextResponseDto.setPatientId(patientId);
			String patientName=(patientCareContext.getPatientName()!=null)? patientCareContext.getPatientName():"";
			careContextResponseDto.setPatientName(patientName);
			String aadhaarNo= (patientCareContext.getAadhaarNo()!=null)? patientCareContext.getAadhaarNo():"";
			careContextResponseDto.setAadhaarNo(aadhaarNo);
			
			careContextResponseList.add(careContextResponseDto);
				
		}
		}
		}else {
			LOGGER.info("HealthId not found in PatientCareContext table for this patient");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.NO_DETAILS_FOUND,
					AuthConstant.PATIENT_DETAILS_NOT_FOUND));
		}
		
		response.setResponse(careContextResponseList);
		response.setStatus(true);
		return response;

	}

	@Override
	public ReportResponseDto getReportsByCareContextIds(
			@Valid RequestWrapper<ReportRequestDto> reportRequestDtls) {
		List<CareContextResponse> careResponseList = new ArrayList<CareContextResponse>();
		ReportResponseDto reportResponseDto = new ReportResponseDto();
		List<CareContextDtls> apptId = reportRequestDtls.getRequest().getCareContextIds();
		PatientRegDetailsDTO patientRegDetailsDTO = new PatientRegDetailsDTO();
		PatientPersonalDetailEntity personalentity = personalDetailsRepository
				.getPatientDtlsById(reportRequestDtls.getRequest().getAbhaId());
		if(personalentity != null) {
		String healthId=(personalentity.getHealthID()!=null)?personalentity.getHealthID():"";
		patientRegDetailsDTO.setAbhaId(healthId);
		String healthNo=(personalentity.getHealthNumber()!=null)?personalentity.getHealthNumber():"";
		patientRegDetailsDTO.setAbhaNo(healthNo);
		String ptFullName=(personalentity.getPtFullName()!=null)?personalentity.getPtFullName():"";
		patientRegDetailsDTO.setName(ptFullName);
		String ptUserId=(personalentity.getPtUserID()!=null)?personalentity.getPtUserID():"";
		patientRegDetailsDTO.setId(ptUserId);
		Long ptMobNo=(personalentity.getPtMobNo()!=null)?personalentity.getPtMobNo():null;
		patientRegDetailsDTO.setMob(ptMobNo);
		Date dob=(personalentity.getDob()!=null)?personalentity.getDob():null;
		patientRegDetailsDTO.setDob(dob);
		String gender=(personalentity.getGender()!=null)?personalentity.getGender():"";
		patientRegDetailsDTO.setGender(gender);
	  }
		
		if (apptId != null && !apptId.isEmpty()) {
			
			for (CareContextDtls careDtls : apptId) {
				CareContextResponse careResponse = new CareContextResponse();
				List<ReportResponseDtls> reportResponseDtlsList = new ArrayList<ReportResponseDtls>();
				ReportResponseDtls reportResponseDtls = new ReportResponseDtls();
				DoctorDetailsDto doctorDetailsDto = new DoctorDetailsDto();
				String aptId = careDtls.getCareContextId();

				AppointmentDtlsEntity apptCrtTime= appointmentDtlsRepository.getCreatedTimeById(aptId);
				
				ConsultationDtl consultationDtl = consulationPrescriptionDetailsRepo.getPath(aptId);
				String doctorId=consultationDtl.getCtDoctorId();
				DoctorMstrDtlsEntity doctorMstrDtlsEntity=doctorMstrRepo.findByDmdUserId(doctorId);
				String docType = consultationDtl.getCtPrescriptionPath();
				try {
				if (consultationDtl != null && docType.contains(".pdf")) {
					Path path = Paths.get(docType); 
					byte[] data = Files.readAllBytes(path);
			        String docTypeEncoded = Base64Utils.encodeToString(data);
			        String docEncType=(docTypeEncoded)!=null?docTypeEncoded:"";
					reportResponseDtls.setReport_doc(docEncType);
					String documentType=(docType.substring(docType.lastIndexOf("/") + 1))!=null?docType.substring(docType.lastIndexOf("/") + 1):"";
					reportResponseDtls.setReport_name(documentType);
					reportResponseDtls.setReport_type("Prescription PDF");
					LocalDateTime crtTime=(consultationDtl.getCtCreatedTmstmp())!=null?consultationDtl.getCtCreatedTmstmp():null;
					reportResponseDtls.setDocCreationTimestamp(crtTime);
					reportResponseDtlsList.add(reportResponseDtls);
				}

				PatientReportUploadDtlsEntity patientReportUploadDtlsEntity = patientReportUploadDtlsRepository
						.getUploadReportDtls(aptId);

				if (patientReportUploadDtlsEntity !=null && patientReportUploadDtlsEntity.getApptIdFk() != null) {

					String docReportPath = patientReportUploadDtlsEntity.getPath();
					Path reportPath = Paths.get(docReportPath);
					byte[] reportData = Files.readAllBytes(reportPath);
			        String reportDocTypeEncoded = Base64Utils.encodeToString(reportData);
			        String docEncType=(reportDocTypeEncoded)!=null?reportDocTypeEncoded:"";
					reportResponseDtls.setReport_doc(docEncType);
					String repoName=(patientReportUploadDtlsEntity.getReportName())!=null?patientReportUploadDtlsEntity.getReportName():"";
					reportResponseDtls.setReport_name(repoName);
					String repoType=(patientReportUploadDtlsEntity.getReportType())!=null?patientReportUploadDtlsEntity.getReportType():"";
					reportResponseDtls.setReport_type(repoType);
					LocalDateTime crtTime=(consultationDtl.getCtCreatedTmstmp())!=null?consultationDtl.getCtCreatedTmstmp():null;
					reportResponseDtls.setDocCreationTimestamp(crtTime);
					reportResponseDtlsList.add(reportResponseDtls);
				}
				}catch(Exception e) {
					LOGGER.error("Requested file data not found on server");
					e.printStackTrace();	
				}
				Integer drId=(doctorMstrDtlsEntity.getDmdIdPk())!=null?doctorMstrDtlsEntity.getDmdIdPk():null;
				doctorDetailsDto.setDrId(drId);
				String drName=(doctorMstrDtlsEntity.getDmdDrName())!=null?doctorMstrDtlsEntity.getDmdDrName():"";
				doctorDetailsDto.setDrName(drName);
				String dmdEmail=(doctorMstrDtlsEntity.getDmdEmail())!=null?doctorMstrDtlsEntity.getDmdEmail():"";
				doctorDetailsDto.setEmail(dmdEmail);
				Long dmdMobNo=(doctorMstrDtlsEntity.getDmdMobileNo())!=null?doctorMstrDtlsEntity.getDmdMobileNo():null;
				doctorDetailsDto.setMob(dmdMobNo);
				String mciNo=(doctorMstrDtlsEntity.getDmdMciNumber())!=null?doctorMstrDtlsEntity.getDmdMciNumber():"";
				doctorDetailsDto.setMciNo(mciNo);
				String smcNo=(doctorMstrDtlsEntity.getDmdSmcNumber())!=null?doctorMstrDtlsEntity.getDmdSmcNumber():"";
				doctorDetailsDto.setSmcNo(smcNo);
				String dmdSpecialiazation=(doctorMstrDtlsEntity.getDmdSpecialiazation())!=null?doctorMstrDtlsEntity.getDmdSpecialiazation():"";
				doctorDetailsDto.setSpeciality(dmdSpecialiazation);
				
				careResponse.setDoctorDetails(doctorDetailsDto);
				
				careResponse.setCareContextId(aptId);
				LocalDateTime aptCrtTime=(apptCrtTime.getAdCreatedTmstmp())!=null?apptCrtTime.getAdCreatedTmstmp():null;
				careResponse.setCareContextCreationTimestamp(aptCrtTime);
				careResponse.setReportResponseDtls(reportResponseDtlsList);
              careResponseList.add(careResponse);  
			}
			
		} else {
			LOGGER.info("Care ContextId not found");
			throw new DateParsingException(
					new ExceptionJSONInfoDTO(AuthErrorConstant.NO_DETAILS_FOUND, AuthConstant.CARECONTEXTID_NOTFOUND));
		}
		reportResponseDto.setCareContextDetails(careResponseList);
		reportResponseDto.setPatient(patientRegDetailsDTO);
		return reportResponseDto;
	}
	
	
	
	/*
	 * private String saveRepeatedRdcordList(String file2, List<PatientDetailsDTO>
	 * patientList) { // TODO Auto-generated method stub MainResponseDTO<String>
	 * mainResponse = new MainResponseDTO<>(); String path = null; try{ File
	 * byteStorePath = null; byteStorePath = new File(bulkrecordlist); String
	 * temppath; if(file2!="") {
	 * //if(doctorRegDtlsDTO.getDrProfilePhoto().getBytes().length<Long.parseLong(
	 * profilephotosize)) //{ String[] strings = file2.split(","); String extension
	 * = "xlsx"; byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
	 * createDirectory(AuthConstant.PatientProfileDirectory); temppath =
	 * byteStorePath + File.separator + AuthConstant.PatientProfileDirectory; path =
	 * temppath + File.separator + patientList.get(0).getPtMobileNo()+"."+
	 * extension; File file = new File(path); try (OutputStream outputStream = new
	 * BufferedOutputStream(new FileOutputStream(file))) { outputStream.write(data);
	 * } catch (IOException e) { e.printStackTrace(); mainResponse.setStatus(false);
	 * } mainResponse.setStatus(true); //}
	 * 
	 * } }catch(Exception e) { //
	 * mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus())
	 * ;
	 * //mainResponse.setResponse(DoctorRegConstant.PROFILE_PHOTO_INVALID.getMsg());
	 * } return path; }
	 */

	/*
	 * public void createDirectory(String folder_name) throws IOException {
	 * 
	 * String directoryFilePath; File file = new File(directoryFilePath); if
	 * (!file.exists()) { try { file.mkdir(); } catch (Exception e) { } } else { } }
	 */
	/*
	 * public String getFilePathBasedOnOS() throws IOException { final String os =
	 * System.getProperty("os.name").toLowerCase(); return ((os.contains("windows"))
	 * ? LoadPropertyValues.WindowsDocumentPath :
	 * LoadPropertyValues.LinuxDocumentPath); return windowdocpath; }
	 */

	/*
	 * public void createexcel(List<DoctorPatientMapDtlsEntity> tempList) throws
	 * IOException { // workbook object XSSFWorkbook workbook = new XSSFWorkbook();
	 * // spreadsheet object XSSFSheet spreadsheet =
	 * workbook.createSheet(" Student Data ");
	 * 
	 * // creating a row object XSSFRow row;
	 * 
	 * 
	 * int rowid = 0; // writing the data into the sheets...
	 * 
	 * for(int rownum = 0; rownum < tempList.size(); rownum++){ row =
	 * spreadsheet.createRow(rowid++); for(int cellnum = 0; cellnum < 3; cellnum++){
	 * Cell cell = row.createCell(cellnum); //String tempList = new
	 * CellReference(cell).formatAsString(); //
	 * cell.setCellValue(tempList.get(rownum)); } // .xlsx is the format for Excel
	 * Sheets... // writing the workbook into the file... FileOutputStream out = new
	 * FileOutputStream( new File("D:/GFGsheet.xlsx"));
	 * 
	 * workbook.write(out); out.close(); }
	 * 
	 * }
	 */

}
