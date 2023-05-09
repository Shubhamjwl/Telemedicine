package com.nsdl.telemedicine.patient.service.impl;

package com.nsdl.telemedicine.patient.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nsdl.telemedicine.gateway.config.UserDTO;
import com.nsdl.telemedicine.patient.constant.AuthConstant;
import com.nsdl.telemedicine.patient.constant.AuthErrorConstant;
import com.nsdl.telemedicine.patient.dto.AppointmentDetailsResponseDTO;
import com.nsdl.telemedicine.patient.dto.AppontmentDetailsRequestDTO;
import com.nsdl.telemedicine.patient.dto.BulkPatientDetails;
import com.nsdl.telemedicine.patient.dto.BulkPatientRegistrationDTO;
import com.nsdl.telemedicine.patient.dto.CaptchaResponseDto;
import com.nsdl.telemedicine.patient.dto.CreateUserRequestDTO;
import com.nsdl.telemedicine.patient.dto.CreateUserResponseDTO;
import com.nsdl.telemedicine.patient.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.patient.dto.MainRequestDTO;
import com.nsdl.telemedicine.patient.dto.MainResponseDTO;
import com.nsdl.telemedicine.patient.dto.OtpRequestDTO;
import com.nsdl.telemedicine.patient.dto.OtpResponseDTO;
import com.nsdl.telemedicine.patient.dto.PatientDetailsDTO;
import com.nsdl.telemedicine.patient.dto.PatientRegistrationDto;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.patient.entity.AuditPatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.entity.DoctorPatientMapDtlsEntity;
import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.entity.ScribeRegEntity;
import com.nsdl.telemedicine.patient.exception.DateParsingException;
import com.nsdl.telemedicine.patient.exception.PatientRegException;
import com.nsdl.telemedicine.patient.loggers.PatientLoggingClientInfo;
import com.nsdl.telemedicine.patient.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.patient.repository.AuditPatientPersonalDetailsRepository;
import com.nsdl.telemedicine.patient.repository.ConsulationPrescriptionDetailsRepo;
import com.nsdl.telemedicine.patient.repository.PatientPersonalDetailsRepository;
import com.nsdl.telemedicine.patient.repository.PatientRegistrationByScribeRepository;
import com.nsdl.telemedicine.patient.repository.ScribeRegRepo;
import com.nsdl.telemedicine.patient.service.AuditPatientService;
import com.nsdl.telemedicine.patient.service.PatientRegistrationService;
import com.nsdl.telemedicine.patient.utility.AuthUtil;
import com.nsdl.telemedicine.patient.utility.CommonValidationUtil;
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
	AuditPatientPersonalDetailsRepository auditRepo;
	
	@Autowired
	private PatientRegistrationByScribeRepository patientRegistrationByScribeRepository;
	
	@Autowired
	private ScribeRegRepo scribeRegRepo;

	@Bean("template")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseWrapper<PatientResponseDto> savePatientDetails(RequestWrapper<PatientRegistrationDto> patientRegistrationDto) {
		ResponseWrapper<PatientResponseDto> response = null;
		response = (ResponseWrapper<PatientResponseDto>)AuthUtil.getMainResponseDto(patientRegistrationDto);
		PatientRegistrationDto request = patientRegistrationDto.getRequest();
		String userId = request.getPtUserID().trim().toUpperCase();
		validateRequest(request , userId);
		PatientPersonalDetailEntity entity = new PatientPersonalDetailEntity();
		BeanUtils.copyProperties(request, entity);
		entity.setPtUserID(userId);
		entity.setCreatedDate(LocalDateTime.now());

		/*Load the Patient Profile photo to file system*/
		if(request.getPtProfilePhoto()!=null || request.getPtProfilePhoto()!="") {
			PatientResponseDto path  = authUtil.savePatientProfilePhoto(request.getPtProfilePhoto(), request.getPtMobNo(), userId);
			entity.setProfilePhotoPath(path.getMessage());
		}
		PatientPersonalDetailEntity savedEntity;
		try {
			savedEntity = patientRegistrationRepository.save(entity);
			LOGGER.info("Saved user Data to Patient registration");
			auditPatientService.auditRegistrationServicePersonalDetail(savedEntity,userId);
		}catch (Exception e) {
			LOGGER.error("Exception while Patient registration..");
			e.printStackTrace();
			throw e;
		}
		if(savedEntity!=null) {
			// OTP Generate Rest Call 
			String otpResponse = generateOTP(savedEntity);
			if (null !=otpResponse && !otpResponse.isEmpty()) {
				LOGGER.info("OTP Generation SUCCESS");
				if (createUserForPatient(savedEntity)) {
					response.setStatus(true);
					PatientResponseDto patientResponseDto  = new PatientResponseDto();
					//					patientResponseDto.setInfo(AuthConstant.OTP_SUCCESS);
					patientResponseDto.setMessage(otpResponse);
					response.setResponse(patientResponseDto);
					LOGGER.info("User-Management Api Call SUCCESS");
				}else {
					LOGGER.error(AuthConstant.USER_MANAGEMENT_API_FAIL);
					response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.USER_MANAGEMENT_API_FAIL,AuthConstant.USER_MANAGEMENT_API_FAIL));
				}
			}else {
				LOGGER.error(AuthConstant.OTP_FAILURE);
				response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.OTP_FAILURE,AuthConstant.OTP_FAILURE));
			}
		}else {
			LOGGER.error(AuthConstant.PATIENT_REGISTRATION_FAIL);
			response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.PATIENT_REGISTRATION_FAIL,AuthConstant.PATIENT_REGISTRATION_FAIL));
		}
		return response;
	}

	private void validateRequest(PatientRegistrationDto request , String userId) {
		MainResponseDTO<CaptchaResponseDto> responseCaptcha=null;
		responseCaptcha=verifyCaptcha(request.getCaptchaCode(),request.getSessionId());
		responseCaptcha.setStatus(true);
		if(!responseCaptcha.isStatus()) {
			LOGGER.error("Captcha Code validation : Failed"); 
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_CAPTCHA_CODE,AuthConstant.INVALID_CAPTCHA_CODE)); 
		}
		if ((!validate.validateUserID(request.getPtUserID()))|| request.getPtUserID().length()<8 || request.getPtUserID().length()>25) {
			LOGGER.error("user-ID format validation : Failed"); 
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_USERID,AuthConstant.INVALID_USERID)); 
		}
		if ((!validate.validateUserName(request.getPtFullName()))|| request.getPtFullName().length()<1 || request.getPtFullName().length()>30) {
			LOGGER.error("Full name format validation : Failed"); throw new
			DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_FULL_NAME,AuthConstant.INVALID_FULL_NAME)); 
		}
		if (!validate.validatePassword(request.getPtPassword())) {
			LOGGER.error("user password format validation : Failed");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_PASSWORD_FORMAT_CODE,AuthConstant.INVALID_PASSWORD_FORMAT));
		}
		if (null != request.getPtEmail() && !request.getPtEmail().isEmpty() && !validate.validateEmail(request.getPtEmail())) {
			LOGGER.error("user email format validation : Failed");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_EMAIL_FORMAT_CODE, AuthConstant.INVALID_EMAIL_FORMAT));
		}
		if (!validate.validateMobileNo(request.getPtMobNo())) {
			LOGGER.error("user mobile number format validation : Failed");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_MOBILE_FORMAT_CODE, AuthConstant.INVALID_MOBILE_FORMAT));
		}
		if (patientRegistrationRepository.existsByPtUserID(userId)) { //|| patientRegistrationRepository.existsByptEmail(request.getPtEmail()) ) {
			LOGGER.error("given userid already exists");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.USERID_ALREADY_EXISTS_CODE, AuthConstant.USERID_ALREADY_EXISTS));
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
			//createUser.setRoleName(PatientRegConstant.PATIENT);
			createUser.setUserId(persistEntity.getPtUserID());
			createUser.setUserType(AuthConstant.PATIENT);
			mainRequestDTO.setRequest(createUser);
			LOGGER.info("USER MANAGEMENT API Called with userID "+createUser.getUserId() +"And UserName "+createUser.getUserFullName());
			HttpEntity<MainRequestDTO<CreateUserRequestDTO>> requestEntity = new HttpEntity<>(mainRequestDTO);
			ParameterizedTypeReference<MainResponseDTO<CreateUserResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<CreateUserResponseDTO>>() {
			};
			ResponseEntity<MainResponseDTO<CreateUserResponseDTO>> response = template.exchange(createUserURL,
					HttpMethod.POST, requestEntity, parameterizedResponse);
			LOGGER.info("USER MANAGEMENT API Status : "+response.getBody().isStatus());
			return response.getBody().isStatus();
		}catch (Exception e) {
			LOGGER.error("CALL TO USER MANAGEMENT API FAILED");
			e.printStackTrace();
			throw e;
		}
	}

	private String generateOTP(PatientPersonalDetailEntity persistEntity) {
		String description = null ;
		try {
			LOGGER.info("OTP Generation Api called");
			Boolean generateOtpResponse = false;

			MainRequestDTO<OtpRequestDTO> mainRequest = new MainRequestDTO<>();
			OtpRequestDTO otpRequestDTO = new OtpRequestDTO();
			otpRequestDTO.setEmailID(persistEntity.getPtEmail());
			otpRequestDTO.setMobileNo(persistEntity.getPtMobNo().toString());
			otpRequestDTO.setOtpFor(AuthConstant.OTP_FOR);
			otpRequestDTO.setOtpGenerateTpye(AuthConstant.OTP_GENERATE_TYPE);
			otpRequestDTO.setSendType(null != persistEntity.getPtEmail() && !persistEntity.getPtEmail().isEmpty() ? AuthConstant.OTP_SEND_TYPE : "sms");
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
			LOGGER.info("OTP Generation Api status : "+generateOtpResponse);
			return description;
		}catch (Exception e) {
			LOGGER.error("Call to OTP Generation API Failed");
			e.printStackTrace();
		}
		return description;
	}

	public MainResponseDTO<CaptchaResponseDto> verifyCaptcha(String captchaValue, String sessionId)
	{
		LOGGER.info("Captcha Varification Api called");
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("sessionId", sessionId);
		requestHeaders.add("captchaValue", captchaValue);
		requestHeaders.add("flagValue" , "true");
		HttpEntity<MainRequestDTO<CaptchaResponseDto>> requestEntity = new HttpEntity<MainRequestDTO<CaptchaResponseDto>>(
				requestHeaders);
		ParameterizedTypeReference<MainResponseDTO<CaptchaResponseDto>> parameterizedResponse = new 			ParameterizedTypeReference<MainResponseDTO<CaptchaResponseDto>>(){};
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
		LOGGER.info("Get Patient Details For:"+userId);
		if (entity == null) {
			LOGGER.error( AuthConstant.INVALID_USER_ERROR+" For:"+userId);
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_USER_ERR_CODE, AuthConstant.INVALID_USER_ERROR));
		}
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
			response.setStatus(true);
			response.setResponse(patientRegistrationDto);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings({ "serial", "unused" })
	@Override
	public ResponseWrapper<List<AppointmentDetailsResponseDTO>> searchCompletedAppointmentsForPatient(RequestWrapper<AppontmentDetailsRequestDTO> appontmentDetailsRequest) {
		List<AppointmentDtlsEntity> completedAppointmentEntities = new ArrayList<AppointmentDtlsEntity>();
		List<AppointmentDetailsResponseDTO> completedAppointmentDTOs = new ArrayList<AppointmentDetailsResponseDTO>();
		ResponseWrapper<List<AppointmentDetailsResponseDTO>> response = new ResponseWrapper<List<AppointmentDetailsResponseDTO>>();
		try {
			completedAppointmentEntities = appointmentDtlsRepository.findAll(new Specification<AppointmentDtlsEntity>() {
				@Override
				public Predicate toPredicate(Root<AppointmentDtlsEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<>();
					if ((null != appontmentDetailsRequest.getRequest().getFromDate() && null != appontmentDetailsRequest.getRequest().getToDate() &&
							!appontmentDetailsRequest.getRequest().getFromDate().isEmpty() && !appontmentDetailsRequest.getRequest().getToDate().isEmpty())
							|| (null != appontmentDetailsRequest.getRequest().getToDate() && !appontmentDetailsRequest.getRequest().getToDate().isEmpty())) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("adApptDateFk"), formatStringToDate(appontmentDetailsRequest.getRequest().getFromDate()))));
						predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("adApptDateFk"), formatStringToDate(appontmentDetailsRequest.getRequest().getToDate()))));
					}
					if (null != appontmentDetailsRequest.getRequest().getApptId() && !appontmentDetailsRequest.getRequest().getApptId().isEmpty()) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("adApptId"), appontmentDetailsRequest.getRequest().getApptId())));
					}
					if (null != appontmentDetailsRequest.getRequest().getDoctorName() && !appontmentDetailsRequest.getRequest().getDoctorName().isEmpty()) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("docMstrDtlsEntity").get("dmdDrName"), "%" + appontmentDetailsRequest.getRequest().getDoctorName().toUpperCase() + "%")));
					}
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("adApptStatus"), "C")));
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("patientRegDtlsEntity"), userDto.getUserName().toUpperCase())));
					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			});
			if(null !=completedAppointmentEntities && completedAppointmentEntities.size() > 0) {
				for (AppointmentDtlsEntity appointmentDtlsEntity : completedAppointmentEntities) {
					AppointmentDetailsResponseDTO appointmentDetailsResponseDTO = new AppointmentDetailsResponseDTO();
					appointmentDetailsResponseDTO.setAppointmentId(appointmentDtlsEntity.getAdApptId());
					appointmentDetailsResponseDTO.setAppointmentDate(new SimpleDateFormat("dd-MM-yyyy").format(appointmentDtlsEntity.getAdApptDateFk()));
					appointmentDetailsResponseDTO.setDoctorId(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());
					completedAppointmentDTOs.add(appointmentDetailsResponseDTO);
				}
			} 
			else {
				LOGGER.error("No Appointments Found..");
				throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.NO_APPOINTMENT_FOUND, AuthConstant.NO_APPOINTMENT_FOUND));
			}
		}catch(DateParsingException e1) {
			throw e1;
		}catch(Exception e) {
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
	 * @return
	 * Added by girishk to change string to date.
	 */
	@SuppressWarnings("unused")
	private Date formatStringToDate(String dateString) {
		SimpleDateFormat inputDateForamt = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Date finalDate = null;
		try {
			if(null != dateString && !dateString.isEmpty()) {
				Date date = inputDateForamt.parse(dateString);
			    finalDate = outputDateFormat.parse(outputDateFormat.format(date));
			}else {//get todays date
				Calendar cal = Calendar.getInstance();
		        Date date = cal.getTime();
		        finalDate = outputDateFormat.parse(outputDateFormat.format(date));
			}
		}catch(Exception e1) {
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
		//ConsultationDtl consultationDtl=null;
		try {
			String filePath = prescriptionPath + pathSeperator;
			completedAppointmentEntities = appointmentDtlsRepository.findapptDetails(userDto.getUserName());
			if(null !=completedAppointmentEntities && completedAppointmentEntities.size() > 0) {
				for (AppointmentDtlsEntity appointmentDtlsEntity : completedAppointmentEntities) {
					//path from database.
					//consultationDtl=consulationPrescriptionDetailsRepo.getprecsriptionPath(appointmentDtlsEntity.getAdApptId());
					AppointmentDetailsResponseDTO appointmentDetailsResponseDTO = new AppointmentDetailsResponseDTO();
					appointmentDetailsResponseDTO.setAppointmentId(appointmentDtlsEntity.getAdApptId());
					appointmentDetailsResponseDTO.setAppointmentDate(new SimpleDateFormat("yyyy-MM-dd").format(appointmentDtlsEntity.getAdApptDateFk()));
					appointmentDetailsResponseDTO.setDoctorId(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());
					appointmentDetailsResponseDTO.setDoctorname(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdDrName());
					appointmentDetailsResponseDTO.setTimestamp(appointmentDtlsEntity.getAdApptSlotFk());
					appointmentDetailsResponseDTO.setPath(filePath + appointmentDtlsEntity.getAdApptId() +".pdf");
					//appointmentDetailsResponseDTO.setPath(consultationDtl.getCtPrescriptionPath());
					completedAppointmentDTOs.add(appointmentDetailsResponseDTO);
				}
			} 
			else {
				LOGGER.error("No Appointments Found..");
				throw new PatientRegException(new ExceptionJSONInfoDTO(AuthErrorConstant.NO_APPOINTMENT_FOUND, AuthConstant.NO_APPOINTMENT_FOUND));
			}
		}catch(PatientRegException e1) {
			LOGGER.error("Exception while getting appointment details.");
			throw e1;
		}
		catch(Exception e) {
			LOGGER.error("Exception while getting appointment details.");
			e.printStackTrace();
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.PATIENT_REGISTRATION_FAIL, AuthConstant.INVALID_REQUEST));
		}
		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		response.setResponse(completedAppointmentDTOs);
		response.setStatus(true);
		return response;
	}
	
	@Override
	public ResponseWrapper<PatientResponseDto> bulkPatientRegistration(RequestWrapper<BulkPatientRegistrationDTO> bulkRegistrationRequest) {
		ResponseWrapper<PatientResponseDto> response = new ResponseWrapper<PatientResponseDto>();
		String filePath = null;
		String excelFileInfo[] = null;
		String excelFileName =null;
		byte[] data = null;
//		userDto.setUserName("SAYALIUSER800");
//		userDto.setRole("doctor");
		if(null != bulkRegistrationRequest && null != bulkRegistrationRequest.getRequest().getExcelFileOfBulkPatientDtls() && !bulkRegistrationRequest.getRequest().getExcelFileOfBulkPatientDtls().equals("")) { 
			filePath = prescriptionPath + pathSeperator + "bulk_patient_details" + pathSeperator;
			excelFileInfo = bulkRegistrationRequest.getRequest().getExcelFileOfBulkPatientDtls().split(",");
			excelFileName = bulkRegistrationRequest.getRequest().getFileName().contains("xlsx") ? bulkRegistrationRequest.getRequest().getDoctorUserID()+"_"+LocalDate.now()+".xlsx" 
												: bulkRegistrationRequest.getRequest().getDoctorUserID()+"_"+LocalDate.now()+".xls";
			data = DatatypeConverter.parseBase64Binary(excelFileInfo[1].trim());
		}else {
			LOGGER.info("Exception while processing base64 excel file.");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_REQUEST, AuthConstant.INVALID_REQUEST));
		}
		
		uploadBulkPatientDtlsFile(filePath, excelFileName, data);
		BulkPatientDetails bulkPatientDetails = readDataFromBulkPatientDtlsFile(filePath, excelFileName);
		saveBulkPatientDetails(bulkPatientDetails.getPatientList(),bulkPatientDetails.getMobileList());
		PatientResponseDto patientResponseDto  = new PatientResponseDto();
		patientResponseDto.setMessage("Patient registartion by doctor is sucessfull, you can login to telemedicine app");
		response.setResponse(patientResponseDto);
		response.setStatus(true);
		LOGGER.info("API success, insertion in all tables successfull");
		
		try {
			Files.deleteIfExists(Paths.get(filePath + excelFileName));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return response;
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
	        throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.BULK_FILE_UPLOAD_FAILED, AuthConstant.BULK_FILE_UPLOAD_FAILED));
	    }
	}
	
	public BulkPatientDetails readDataFromBulkPatientDtlsFile(String excelFilePath, String excelFileName) {
	    List<PatientDetailsDTO> patientList = new ArrayList<PatientDetailsDTO>();
	    FileInputStream inputStream = null;
	    List<String> ptMobileList = new ArrayList<String>();
	    BulkPatientDetails bulkPatientDetails = new BulkPatientDetails();
	    try {
	    	Workbook workbook = null;
	    	inputStream = new FileInputStream(new File(excelFilePath+excelFileName));
	    	if(excelFileName.contains("xlsx")) {
	    		workbook = new XSSFWorkbook(inputStream);
	    	}else {
	    		workbook = new HSSFWorkbook(inputStream);
	    	}
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	        Row nextRow1 = iterator.next();
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	            PatientDetailsDTO patientDetailsDTO = new PatientDetailsDTO();
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                switch (cell.getCellType()) {
	                    case Cell.CELL_TYPE_STRING:
	                    	patientDetailsDTO.setPatientName(cell.getStringCellValue());
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                    	BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
	                    	patientDetailsDTO.setPtMobileNo(bd.longValue());
	                    	ptMobileList.add(bd.toPlainString());
	                        break;
	                }
	            }
	            patientList.add(patientDetailsDTO);
	        }
	        bulkPatientDetails.setPatientList(patientList);
	        bulkPatientDetails.setMobileList(ptMobileList);
	    }catch(Exception e) {
	    	LOGGER.error("Exception while reading patient details from excel file.");
	    	e.printStackTrace();
	    	throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.READ_BULK_FILE_FAILED, AuthConstant.READ_BULK_FILE_FAILED));
	    }finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return bulkPatientDetails;
	}
	
	private void saveBulkPatientDetails(List<PatientDetailsDTO> patientList , List<String> mobileList) {
		List<PatientPersonalDetailEntity> patientEntities = new ArrayList<PatientPersonalDetailEntity>();
		List<AuditPatientPersonalDetailEntity> auditedEntities = new ArrayList<AuditPatientPersonalDetailEntity>();
		List<PatientPersonalDetailEntity> savedPatientEntities = new ArrayList<PatientPersonalDetailEntity>();
		List<DoctorPatientMapDtlsEntity> doctorPatientMapDtlsEntities = new ArrayList<DoctorPatientMapDtlsEntity>();
		List<String> existedMobileNumber = new ArrayList<String>();
		Map<String,String> ptDetails = new HashMap<String,String>();
		
		ScribeRegEntity scribeEntity= userDto.getRole().equalsIgnoreCase("scribe") ? scribeRegRepo.findScribe(userDto.getUserName().toLowerCase()) : null;
		existedMobileNumber = patientRegistrationRepository.checkIfPtMobileNoExist(mobileList);
		
		try {
//			String newRandomPassword = RandomStringUtil.randomAlphaNumeric(10, "LGN-PWD");
//			LOGGER.info("new random password"+newRandomPassword);
			if(null != existedMobileNumber && existedMobileNumber.size() > 0) {
				for (String ptDetail : existedMobileNumber) {
					ptDetails.put(ptDetail.split("_")[0], ptDetail.split("_")[1]);
				}
			}
			for (PatientDetailsDTO patientDetailsDTO : patientList) {
				if(null != patientDetailsDTO.getPatientName() && null != patientDetailsDTO.getPtMobileNo()) {
					if(null != existedMobileNumber && existedMobileNumber.size() > 0 && null != ptDetails.get(String.valueOf(patientDetailsDTO.getPtMobileNo()))) {
						DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity = new DoctorPatientMapDtlsEntity();
						doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(userDto.getRole().equalsIgnoreCase("doctor") ? userDto.getUserName() : scribeEntity.getScrbdrUserIDfk());
						doctorPatientMapDtlsEntity.setDpmdCreatedBy(userDto.getUserName());
						doctorPatientMapDtlsEntity.setDpmdCreatedTmstmp(LocalDateTime.now());
//						doctorPatientMapDtlsEntity.setDpmdModifiedBy(userDto.getUserName());
						doctorPatientMapDtlsEntity.setDpmdStatus("Y");
						doctorPatientMapDtlsEntity.setDpmdPtUserIdFk(ptDetails.get(String.valueOf(patientDetailsDTO.getPtMobileNo())));
						doctorPatientMapDtlsEntities.add(doctorPatientMapDtlsEntity);
					}else {
						PatientPersonalDetailEntity entity = new PatientPersonalDetailEntity();
						entity.setPtUserID("P"+String.valueOf(patientDetailsDTO.getPtMobileNo()));
						entity.setPtPassword(RandomStringUtil.randomAlphaNumeric(10, "LGN-PWD"));
						entity.setPtMobNo(patientDetailsDTO.getPtMobileNo());
						entity.setPtFullName(patientDetailsDTO.getPatientName().toUpperCase());
						entity.setCreatedDate(LocalDateTime.now());
						entity.setIsactive('Y');
						entity.setIsRegByIpan('N');  
						entity.setProfileFlag('N');   
						entity.setPtEmail("Nsdl012@egov.in");
						/*Load the Patient Profile photo to file system*/
						if(null !=patientDetailsDTO.getPtProfilePhoto() && patientDetailsDTO.getPtProfilePhoto()!="") {
							PatientResponseDto path  = authUtil.savePatientProfilePhoto(patientDetailsDTO.getPtProfilePhoto(), patientDetailsDTO.getPtMobileNo(), String.valueOf(patientDetailsDTO.getPtMobileNo()));
							entity.setProfilePhotoPath(path.getMessage());
						}
						patientEntities.add(entity);
					}
				}
			}
			savedPatientEntities = patientRegistrationRepository.saveAll(patientEntities);
			if(null != existedMobileNumber && existedMobileNumber.size() > 0) {
				patientRegistrationByScribeRepository.saveAll(doctorPatientMapDtlsEntities);//if mobile is already exist then save recordd in map table only.
			}
			LOGGER.info("Saved user Data to Patient registration");
		}catch (Exception e) {
			LOGGER.error("Exception while Patient registration..");
			e.printStackTrace();
			throw e;
		}
		//audited entry
		if(savedPatientEntities.size() > 0) {
			try {
				for (PatientPersonalDetailEntity patientPersonalDetailEntity : savedPatientEntities) {
					AuditPatientPersonalDetailEntity auditEntity = new AuditPatientPersonalDetailEntity();
					BeanUtils.copyProperties(patientPersonalDetailEntity, auditEntity);
					auditEntity.setAudUserId(patientPersonalDetailEntity.getPtUserID());
					auditedEntities.add(auditEntity);
				}
				auditRepo.saveAll(auditedEntities);
				LOGGER.info("Saved user Data to audit patient registration");
			}catch (Exception e) {
				LOGGER.error("Exception while audit patient registration..");
				e.printStackTrace();
				throw e;
			}
		}
		//entries in usr_dtls and map table.
		if(null != savedPatientEntities && savedPatientEntities.size() > 0) {
			for (PatientPersonalDetailEntity patientPersonalDetailEntity : savedPatientEntities) {
				if (createUserForPatientByScribe(patientPersonalDetailEntity)) {
					if(userDto.getRole().equalsIgnoreCase("doctor") || userDto.getRole().equalsIgnoreCase("scribe"))
					{
						DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity = new DoctorPatientMapDtlsEntity();
						doctorPatientMapDtlsEntity.setDpmdDrUserIdFk(userDto.getRole().equalsIgnoreCase("doctor") ? userDto.getUserName() : scribeEntity.getScrbdrUserIDfk());
						doctorPatientMapDtlsEntity.setDpmdCreatedBy(userDto.getUserName());
						doctorPatientMapDtlsEntity.setDpmdCreatedTmstmp(LocalDateTime.now());
//						doctorPatientMapDtlsEntity.setDpmdModifiedBy(userDto.getUserName());
						doctorPatientMapDtlsEntity.setDpmdStatus("Y");
						doctorPatientMapDtlsEntity.setDpmdPtUserIdFk(String.valueOf("P"+patientPersonalDetailEntity.getPtMobNo()));
						doctorPatientMapDtlsEntities.add(doctorPatientMapDtlsEntity);
					}
					LOGGER.info("User-Management Api Call SUCCESS");
				}
//				else {
//					LOGGER.error(AuthConstant.USER_MANAGEMENT_API_FAIL);
//					throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.USER_MANAGEMENT_API_FAIL, AuthConstant.USER_MANAGEMENT_API_FAIL));
//				}
			}
			try {
				patientRegistrationByScribeRepository.saveAll(doctorPatientMapDtlsEntities);
				LOGGER.info("Saved user Data to doctor to patient map details");
			}catch (Exception e) {
				LOGGER.error("Exception while inserting doctor to patient map details..");
				e.printStackTrace();
				throw e;
			}
		}else {
			LOGGER.error(AuthConstant.PATIENT_REGISTRATION_FAIL);
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.PATIENT_REGISTRATION_FAIL, AuthConstant.PATIENT_REGISTRATION_FAIL));
		}
	}
}
