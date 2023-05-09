package com.nsdl.telemedicine.doctor.service.Impl;

import com.nsdl.telemedicine.doctor.constant.AuthConstant;
import com.nsdl.telemedicine.doctor.constant.DoctorRegConstant;
import com.nsdl.telemedicine.doctor.constant.DoctorRegEnum;
import com.nsdl.telemedicine.doctor.dto.*;
import com.nsdl.telemedicine.doctor.entity.*;
import com.nsdl.telemedicine.doctor.exception.DoctorRegistrationException;
import com.nsdl.telemedicine.doctor.exception.DrRegErrorConstant;
import com.nsdl.telemedicine.doctor.exception.DrRegErrorMessage;
import com.nsdl.telemedicine.doctor.exception.ServiceErrors;
import com.nsdl.telemedicine.doctor.loggers.DoctorLoggingClientInfo;
import com.nsdl.telemedicine.doctor.repository.*;
import com.nsdl.telemedicine.doctor.service.RegistrationService;
import com.nsdl.telemedicine.doctor.utility.CommonValidationUtil;
import com.nsdl.telemedicine.doctor.utility.DateUtils;
import com.nsdl.telemedicine.doctor.utility.DoctorAuthUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.util.HtmlUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@DoctorLoggingClientInfo
public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Qualifier("doctorCommonValidation")
	private CommonValidationUtil validate;

	@Autowired
	RegistrationRepo registrationRepo;

	@Autowired
	DocumentRepo documentrepo;

	@Autowired
	DoctorMstrRepo doctorMstrRepo;

	@Autowired
	DoctorDocumentRepo doctorDocumentRepo;

	@Autowired
	ScribeRegRepo scribeRegRepo;

	@Autowired
	ScribeRegRepoAudited scribeRegRepoAudited;

	@Autowired
	DoctorAuditRepo doctorAuditRepo;
	
	@Autowired
	DoctorFeatureMapRepo doctorFeatureMapRepo;

	@Value("${ProfilePhotoPath}")
	private String profilePhotoBytePath;
	
	@Value("${ProfilePhotoPathUrL}")
	private String profilePhotoBytePathUrl;

	@Value("${CAPTCHA_VERIFY_URL}")
	private String captchaVerificationURL;

	@Value("${ProfilePhotoSize}")
	private String profilephotosize;

	@Value("${DocumentUploadSize}")
	private String documentuploadsize;

	@Value("${Update_user_details_URL}")
	private String updateUserDetailsURL;

	@Value("${DOCTOR_VERIFY_URL}")
	private String verifyDoctorURL;

	@Value("${DOCTOR_SAVE_SLOT_URL}")
	private String saveSlotDoctorURL;

	@Value("${verifyCaptchaByPass.flag}")
	private String bypassCapcthaFlag;

	@Value("${CREATE_USER_URL}")
	private String createUserURL;

	@Value("${OTP_GENERATE_URL}")
	private String generateOtpURL;

	@Value("${WindowDocPath}")
	private String windowdocpath;

	@Value("${prescription.path}")
	private String prescriptionPath;

	@Value("${path.seperator}")
	private String pathSeperator;
	
	@Value("${convenience.charge}")
	private Integer convenienceCharge;
	
	List<ExceptionJSONInfoDTO> listOfExceptions = null;
	ExceptionJSONInfoDTO exceptionJSONInfoDTO = null;
	MainResponseDTO<String> mainResponse = null;
	DoctorRegDtlsEntity doctorRegDtlsEntity = new DoctorRegDtlsEntity();

	@Autowired
	UserDtlRepo userDtlRepo;

	@Autowired
	private UserDTO userDto;

	@Autowired
	AppointmentDtlsRepository appointmentDtlsRepository;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("excelFileToDatabaseJob")
	Job job;

	/*
	 * Code Added by sayaliA to save registartion details as well as upload document
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public MainResponseDTO<String> saveDoctorRegistrationDetails(
			MainRequestDTO<DoctorRegDtlsDTO<DoctorDocsDtlsDTO>> registerRequest) throws Exception {
		// TODO Auto-generated method stub
		@SuppressWarnings("rawtypes")
		DoctorRegDtlsDTO doctorRegDtlsDTO = registerRequest.getRequest();
		DoctorRegDtlsEntity doctorrRegDtlsEntity = new DoctorRegDtlsEntity();
		MainResponseDTO<String> response = (MainResponseDTO<String>) DoctorAuthUtil.getMainResponseDto(registerRequest);
		MainResponseDTO<CaptchaResponseDto> responseCaptcha = null;
		responseCaptcha = verifyCaptcha(registerRequest.getRequest().getCaptchaCode(),
				registerRequest.getRequest().getSessionId());
		// if(!bypassCapcthaFlag.equalsIgnoreCase("Y"))
		// {
		if (!responseCaptcha.isStatus()) {
			return throwExceptionForCaptcha();
		}
		// }
		response = validateRequestFields(doctorRegDtlsDTO);
		if (response != null) {
			if (!response.getErrors().isEmpty()) {
				return response;
			}
		}
		response = verifyUniqueField(doctorRegDtlsDTO);
		if (!response.isStatus()) {
			return response;
		}
		response = saveDoctorProfilePhoto(registerRequest.getRequest(), doctorrRegDtlsEntity);
		if (!response.isStatus()) {
			return response;
		} else {
			doctorrRegDtlsEntity.setDrdDrFirstName(registerRequest.getRequest().getDrFirstName().toUpperCase());
			doctorrRegDtlsEntity.setDrdDrMiddleName(registerRequest.getRequest().getDrMiddleName().toUpperCase());
			doctorrRegDtlsEntity.setDrdDrLastName(registerRequest.getRequest().getDrLastName().toUpperCase());
			doctorrRegDtlsEntity.setDrdConsulFee(registerRequest.getRequest().getDrConsultFee());
			doctorrRegDtlsEntity.setDrdEmail(null != registerRequest.getRequest().getDrEmail()
					&& !registerRequest.getRequest().getDrEmail().isEmpty()
							? registerRequest.getRequest().getDrEmail().toUpperCase()
							: "");// changed by girishk
			doctorrRegDtlsEntity.setDrdMobileNo(registerRequest.getRequest().getDrMobNo());
			doctorrRegDtlsEntity.setDrdIsRegByIpan("N");
			// otp is removed so changed status to R directly previous it was O
			doctorrRegDtlsEntity.setDrdIsverified("R");
			doctorrRegDtlsEntity.setDrdModifiedBy(registerRequest.getRequest().getDrUserID().toUpperCase());
			doctorrRegDtlsEntity.setDrdOptiVersion(Long.valueOf(1));
			// doctorrRegDtlsEntity.setDrdPassword(HMACUtils.hash(registerRequest.getRequest().getDrPassword().getBytes(StandardCharsets.UTF_8)));
			doctorrRegDtlsEntity.setDrdPassword(registerRequest.getRequest().getDrPassword());
			doctorrRegDtlsEntity.setDrdUserId(registerRequest.getRequest().getDrUserID().toUpperCase());
			doctorrRegDtlsEntity.setDrdMciNumber(registerRequest.getRequest().getDrMCINo());
			doctorrRegDtlsEntity.setDrdSmcNumber(registerRequest.getRequest().getDrSMCNo());
			doctorrRegDtlsEntity.setDrdSpecialiazation(registerRequest.getRequest().getDrSpecilization().toUpperCase());
			doctorrRegDtlsEntity.setDrdOtpRefidFk(0);
			doctorrRegDtlsEntity.setDrdGender(registerRequest.getRequest().getDrGender());
			doctorrRegDtlsEntity.setDrdAddress1(registerRequest.getRequest().getDrAddress1().toUpperCase());
			if (registerRequest.getRequest().getDrAddress2() != "") {
				doctorrRegDtlsEntity.setDrdAddress2(registerRequest.getRequest().getDrAddress2().toUpperCase());
			}
			if (registerRequest.getRequest().getDrAddress3() != "") {
				doctorrRegDtlsEntity.setDrdAddress3(registerRequest.getRequest().getDrAddress3().toUpperCase());
			}
			doctorrRegDtlsEntity.setDrdState(registerRequest.getRequest().getDrState());
			doctorrRegDtlsEntity.setDrdCity(registerRequest.getRequest().getDrCity());
			doctorrRegDtlsEntity.setDrdPreassessmentFlag(false);
			doctorrRegDtlsEntity.setDrdPreassessmentLink(registerRequest.getRequest().getDrPreassessmentLink());
			doctorrRegDtlsEntity.setDrdIsIpanorMarsha(null);
			doctorrRegDtlsEntity.setDrdTcFlag(registerRequest.getRequest().getDrTermsAndConditionFlag());
			doctorrRegDtlsEntity.setDrdAssociationName(registerRequest.getRequest().getAssociationName());
			doctorrRegDtlsEntity.setDrdAssociationNumber(registerRequest.getRequest().getAssociationNumber());
			response = saveuploadedDocuments(registerRequest, doctorrRegDtlsEntity);
			if (!response.isStatus()) {
				// Call delete API to delete halfly stored Files which are less than 1
				// MB(transaction rollback)
				deleteuploadedDocuments(doctorrRegDtlsEntity.getDrdUserId());
				deletexistsUserID(doctorrRegDtlsEntity.getDrdUserId());
				return response;
			} else {
				DoctorRegDtlsEntity docRegEntity = registrationRepo.save(doctorrRegDtlsEntity);
				// Save Data to Audit Table
				saveAuditDetails(registerRequest.getRequest());
				response = (MainResponseDTO<String>) DoctorAuthUtil.getMainResponseDto(registerRequest);
				try {
					// boolean responseUsrCreate = false;
					// MainResponseDTO<OtpResponseDTO> responseOfOtp= null;
					/*
					 * if(docRegEntity != null) {
					 * logger.info("Calling API of generate OTP from doctor-registartion");
					 * responseOfOtp = generateOTPDoctorUser(docRegEntity);
					 * if(responseOfOtp.isStatus() &&
					 * responseOfOtp.getResponse().getDescription().equals(DoctorRegConstant.
					 * OTP_SUCESS_MSG.getMsg())) { responseUsrCreate = true; } else { //Call delete
					 * API to delete data stored in database if OTP generation api fails(transaction
					 * rollback) deleteuploadedDocuments(docRegEntity.getDrdUserId());
					 * deletexistsUserID(docRegEntity.getDrdUserId()); return
					 * throwExceptionForOTPFailure(responseOfOtp.getErrors().get(0).getErrorCode(),
					 * responseOfOtp.getErrors().get(0).getMessage()); } }
					 */
					if (docRegEntity != null) {
						response.setStatus(DoctorRegConstant.REGISTRATION_SUCCESS_STATUS.isStatus());
						response.setResponse(DoctorRegConstant.SUCCESSFUL_REG.getMsg());
						// response.setResponse(responseOfOtp.getResponse().getDescription());
					} else {
						// return
						// throwExceptionForOTPFailure(responseOfOtp.getErrors().get(0).getErrorCode(),responseOfOtp.getErrors().get(0).getMessage());
						throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT,
								DrRegErrorMessage.SERVER_ERROR));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new DoctorRegistrationException(
							new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.SERVER_ERROR));
				}
			}

		}
		return response;
	}

	/*
	 * private MainResponseDTO<String> throwExceptionForOTPFailure(String errorCode,
	 * String message) { // TODO Auto-generated method stub mainResponse = new
	 * MainResponseDTO<>(); listOfExceptions = new ArrayList<>();
	 * exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
	 * exceptionJSONInfoDTO.setErrorCode(errorCode);
	 * exceptionJSONInfoDTO.setMessage(message);
	 * listOfExceptions.add(exceptionJSONInfoDTO);
	 * mainResponse.setErrors(listOfExceptions); return mainResponse; }
	 */

	@SuppressWarnings("rawtypes")
	private MainResponseDTO<String> saveDoctorProfilePhoto(DoctorRegDtlsDTO doctorRegDtlsDTO,
			DoctorRegDtlsEntity doctorrRegDtlsEntity) {
		// TODO Auto-generated method stub
		try {
			File byteStorePath = null;
			byteStorePath = new File(profilePhotoBytePath);
			String temppath;
			MainResponseDTO<String> mainResponse = new MainResponseDTO<>();
			logger.info("Loading the scribe profile photo to file system");
			if (doctorRegDtlsDTO.getDrProfilePhoto() != "") {
				if (doctorRegDtlsDTO.getDrProfilePhoto().getBytes().length < Long.parseLong(profilephotosize)) {
					String[] strings = doctorRegDtlsDTO.getDrProfilePhoto().split(",");
					String extension = "jpeg";
					byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
					createDirectory(AuthConstant.DoctorProfileDirectory);
					temppath = byteStorePath + File.separator + AuthConstant.DoctorProfileDirectory;
					String path = temppath + File.separator + doctorRegDtlsDTO.getDrMobNo() + "_"
							+ doctorRegDtlsDTO.getDrUserID() + "." + extension;
					doctorrRegDtlsEntity.setDrdPhotoPath(path);
					File file = new File(path);
					try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
						outputStream.write(data);
					} catch (IOException e) {
						e.printStackTrace();
						mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
					}
					logger.info("loaded scribe profile photo successfully to file system");
					mainResponse.setStatus(DoctorRegConstant.REGISTRATION_SUCCESS_STATUS.isStatus());
				} else {
					return throwExceptionProfilePhotoSize();
				}
			}
		} catch (Exception e) {
			mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
			mainResponse.setResponse(DoctorRegConstant.PROFILE_PHOTO_INVALID.getMsg());
		}
		return mainResponse;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public MainResponseDTO<?> doctorProfile(MainRequestDTO<String> profileRequest) {

		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		DoctorMasterDTO doctorMstrDtlsDTO = new DoctorMasterDTO();
		List<DoctorDocDTO> doctorDocDtlsDTOList = new ArrayList<DoctorDocDTO>();
		DoctorMstrDtlsEntity doctorMstrDtlsEntity = null;
		if (null != profileRequest.getRequest() && !profileRequest.getRequest().isEmpty()) {
			try {
				doctorMstrDtlsEntity = doctorMstrRepo.findByDmdUserId(profileRequest.getRequest().toUpperCase());
				if (null == doctorMstrDtlsEntity) {
					logger.error(
							"Doctor UserId should not be null and empty or might not be present in DB: DmdUserId = "
									+ profileRequest.getRequest());
					throw new DoctorRegistrationException(
							new ServiceErrors(DrRegErrorConstant.NO_DATA_FOUND, DrRegErrorMessage.NO_DATA_FOUND));
				}
				BeanUtils.copyProperties(doctorMstrDtlsEntity, doctorMstrDtlsDTO);
				if(doctorMstrDtlsEntity.getDmdDrMiddleName()==null&&doctorMstrDtlsEntity.getDmdDrLastName()!=null) {
				doctorMstrDtlsDTO.setDmdDrName(doctorMstrDtlsEntity.getDmdDrFirstName()+" "+doctorMstrDtlsEntity.getDmdDrLastName());
				}else{
					doctorMstrDtlsDTO.setDmdDrName(doctorMstrDtlsEntity.getDmdDrFirstName()+" "+doctorMstrDtlsEntity.getDmdDrMiddleName()+" "+doctorMstrDtlsEntity.getDmdDrLastName());
				}
				//added convenience Charge in amount
				doctorMstrDtlsDTO.setDmdConvenienceCharge(convenienceCharge);
			} catch (Exception e) {
				logger.error("Exception while getting doctor details :");
				e.printStackTrace();
				throw e;
			}

			try {
				List<DoctorDocsDtlEntity> doctordocsEntityList = doctorDocumentRepo
						.findDoctorDocuments(profileRequest.getRequest());
				if (null != doctordocsEntityList && doctordocsEntityList.size() > 0) {
					for (DoctorDocsDtlEntity doctorDocsDtlEntity : doctordocsEntityList) {
						DoctorDocDTO doctorDocDtlsDTO = new DoctorDocDTO();
						BeanUtils.copyProperties(doctorDocsDtlEntity, doctorDocDtlsDTO);
						doctorDocDtlsDTOList.add(doctorDocDtlsDTO);
					}
					doctorMstrDtlsDTO.setDrDocsDtls(doctorDocDtlsDTOList);
				} else {
					logger.info("Doctor documents not available.");
				}
			} catch (Exception e) {
				logger.error("Exception while getting doctor document details :");
				e.printStackTrace();
				throw e;
			}

			// fetch profile photo
			if (null != doctorMstrDtlsEntity.getProfilePhoto() && !doctorMstrDtlsEntity.getProfilePhoto().isEmpty()) {
				String profilePhoto = getProfilePhoto(doctorMstrDtlsEntity.getProfilePhoto());
				doctorMstrDtlsDTO.setProfilePhoto(null != profilePhoto ? profilePhoto : null);
			}

			doctorMstrDtlsDTO.setDmdPreassessmentLink(doctorMstrDtlsDTO.getDmdPreassessmentLink() != null
					? HtmlUtils.htmlUnescape(doctorMstrDtlsDTO.getDmdPreassessmentLink())
					: null);
			doctorMstrDtlsDTO.setDmdDrProfileLink(doctorMstrDtlsDTO.getDmdDrProfileLink() != null
					? HtmlUtils.htmlUnescape(doctorMstrDtlsDTO.getDmdDrProfileLink())
					: null);
		} else {
			logger.error("Doctor UserId should not be null and empty or might not be present in DB : DmdUserId = "
					+ profileRequest.getRequest());
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		mainResponseDTO.setResponse(doctorMstrDtlsDTO);
		mainResponseDTO.setStatus(true);
		mainResponseDTO.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		logger.info(" returning response");
		return mainResponseDTO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MainResponseDTO<?> getDoctorDocumentsDetails(MainRequestDTO<String> drDocDtlsRequest) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		if (null != drDocDtlsRequest.getRequest() && !drDocDtlsRequest.getRequest().isEmpty()) {
			mainResponseDTO.setResponse(doctorDocumentRepo.findDoctorDocuments(drDocDtlsRequest.getRequest()));
		} else {
			logger.error("Doctor UserId should not be null and empty or might not be present in DB : drdUserId = "
					+ drDocDtlsRequest.getRequest());
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		mainResponseDTO.setStatus(true);
		mainResponseDTO.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		logger.info(" returning response");
		return mainResponseDTO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public MainResponseDTO<?> updateDoctorProfile(MainRequestDTO<DoctorMstrDtlsDTO> profileUpdateRequest) {

		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		DoctorMstrDtlsEntity updatedDoctorMstrDtlsEntity = new DoctorMstrDtlsEntity();
		DoctorMstrDtlsEntity doctorMstrDtlsEntity = null;
		if (null != profileUpdateRequest.getRequest().getDmdUserId()
				&& !profileUpdateRequest.getRequest().getDmdUserId().isEmpty()) {
			doctorMstrDtlsEntity = doctorMstrRepo
					.findByDmdUserId(profileUpdateRequest.getRequest().getDmdUserId().toUpperCase());
		} else {
			logger.error("Doctor UserId should not be null and empty or might not be present in DB : dmdUserId = "
					+ profileUpdateRequest.getRequest());
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		if (null != doctorMstrDtlsEntity.getDmdUserId()) {
			BeanUtils.copyProperties(doctorMstrDtlsEntity, updatedDoctorMstrDtlsEntity);
			updatedDoctorMstrDtlsEntity.setDmdDrFirstName(profileUpdateRequest.getRequest().getDmdDrFirstName().toUpperCase());
			updatedDoctorMstrDtlsEntity.setDmdDrMiddleName(profileUpdateRequest.getRequest().getDmdDrMiddleName().toUpperCase());
			updatedDoctorMstrDtlsEntity.setDmdDrLastName(profileUpdateRequest.getRequest().getDmdDrLastName().toUpperCase());
			updatedDoctorMstrDtlsEntity.setDmdMobileNo(profileUpdateRequest.getRequest().getDmdMobileNo());
			updatedDoctorMstrDtlsEntity.setDmdEmail(null != profileUpdateRequest.getRequest().getDmdEmail()
					&& !profileUpdateRequest.getRequest().getDmdEmail().isEmpty()
							? profileUpdateRequest.getRequest().getDmdEmail().toUpperCase()
							: "");// changed by girishk
			updatedDoctorMstrDtlsEntity.setDmdSmcNumber(profileUpdateRequest.getRequest().getDmdSmcNumber());
			updatedDoctorMstrDtlsEntity.setDmdMciNumber(profileUpdateRequest.getRequest().getDmdMciNumber());
			updatedDoctorMstrDtlsEntity.setDmdConsulFee(profileUpdateRequest.getRequest().getDmdConsulFee());
			updatedDoctorMstrDtlsEntity
					.setDmdAssociationName(profileUpdateRequest.getRequest().getDmdAssociationName());
			updatedDoctorMstrDtlsEntity
					.setDmdAssociationNumber(profileUpdateRequest.getRequest().getDmdAssociationNumber());
			updatedDoctorMstrDtlsEntity
					.setDmdSpecialiazation(profileUpdateRequest.getRequest().getDmdSpecialiazation().toUpperCase());
			updatedDoctorMstrDtlsEntity.setDmdGender(null != profileUpdateRequest.getRequest().getDmdGender()
					&& !profileUpdateRequest.getRequest().getDmdGender().isEmpty()
							? profileUpdateRequest.getRequest().getDmdGender()
							: doctorMstrDtlsEntity.getDmdGender());
			updatedDoctorMstrDtlsEntity
					.setDmdAddress1(profileUpdateRequest.getRequest().getDmdAddress1().toUpperCase());
			if (profileUpdateRequest.getRequest().getDmdAddress2() != "") {
				updatedDoctorMstrDtlsEntity
						.setDmdAddress2(profileUpdateRequest.getRequest().getDmdAddress2().toUpperCase());
			}
			if (profileUpdateRequest.getRequest().getDmdAddress3() != "") {
				updatedDoctorMstrDtlsEntity
						.setDmdAddress3(profileUpdateRequest.getRequest().getDmdAddress3().toUpperCase());
			}
			updatedDoctorMstrDtlsEntity.setDmdState(profileUpdateRequest.getRequest().getDmdState());
			updatedDoctorMstrDtlsEntity.setDmdCity(profileUpdateRequest.getRequest().getDmdCity());
			updatedDoctorMstrDtlsEntity
					.setDmdPreassessmentFlag(profileUpdateRequest.getRequest().isDmdPreassessmentFlag());
			updatedDoctorMstrDtlsEntity
					.setDmdPreassessmentLink(profileUpdateRequest.getRequest().getDmdPreassessmentLink());
			updatedDoctorMstrDtlsEntity.setDmdModifiedBy(userDto.getUserName());
			updatedDoctorMstrDtlsEntity.setDmdModifiedTmstmp(new Timestamp(System.currentTimeMillis()));
			try {

				if (null != profileUpdateRequest.getRequest().getProfilePhoto()
						&& !profileUpdateRequest.getRequest().getProfilePhoto().isEmpty()) {
					updateDoctorProfilePhoto(profileUpdateRequest.getRequest(), updatedDoctorMstrDtlsEntity);
				}
				// Update details to User details table
				doctorMstrRepo.save(updatedDoctorMstrDtlsEntity);
				logger.info("Calling  API of update user details of user-management calling from doctor-registartion");
				UpdateUserDetails(updatedDoctorMstrDtlsEntity);
			} catch (Exception e) {
				logger.error(" Exception while updating doctor profile : ");
				e.printStackTrace();
				throw e;
			}

		} else {
			logger.error("No record found for given input : dmdUserId = " + profileUpdateRequest.getRequest());
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.NO_DATA_FOUND, DrRegErrorMessage.NO_DATA_FOUND));
		}

		mainResponseDTO.setResponse("Profile Updated Successfully..");
		mainResponseDTO.setStatus(true);
		mainResponseDTO.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		logger.info(" returning response");
		return mainResponseDTO;
	}

	private MainResponseDTO<UserDetailsResponseDTO> UpdateUserDetails(
			DoctorMstrDtlsEntity updatedDoctorMstrDtlsEntity) {
		logger.info("Inside  API of update user details of user-management calling from doctor-registartion");
		MainRequestDTO<UserDetailsDTO> mainRequest = new MainRequestDTO<>();
		// HttpHeaders requestHeaders = new HttpHeaders();
		// requestHeaders.set("Authorization", req.getHeader("Authorization"));
		UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
		userDetailsDTO.setUserId(updatedDoctorMstrDtlsEntity.getDmdUserId());
		userDetailsDTO.setEmail(updatedDoctorMstrDtlsEntity.getDmdEmail());
		userDetailsDTO.setMciNumber(updatedDoctorMstrDtlsEntity.getDmdMciNumber());
		userDetailsDTO.setSmcNumber(updatedDoctorMstrDtlsEntity.getDmdSmcNumber());
		userDetailsDTO.setMobileNumber(updatedDoctorMstrDtlsEntity.getDmdMobileNo());
		userDetailsDTO.setUserFirstName(updatedDoctorMstrDtlsEntity.getDmdDrFirstName());
		userDetailsDTO.setUserMiddleName(updatedDoctorMstrDtlsEntity.getDmdDrMiddleName());
		userDetailsDTO.setUserLastName(updatedDoctorMstrDtlsEntity.getDmdDrLastName());
		mainRequest.setRequest(userDetailsDTO);
		HttpEntity<MainRequestDTO<UserDetailsDTO>> requestEntity = new HttpEntity<MainRequestDTO<UserDetailsDTO>>(
				mainRequest);
		ParameterizedTypeReference<MainResponseDTO<UserDetailsResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<UserDetailsResponseDTO>>() {
		};
		ResponseEntity<MainResponseDTO<UserDetailsResponseDTO>> response = restTemplate.exchange(updateUserDetailsURL,
				HttpMethod.POST, requestEntity, parameterizedResponse);
		return response.getBody();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public MainResponseDTO<?> viewScribeList(MainRequestDTO<ScribeRegDtlsDTO> scribeListRequest) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		List<ScribeRegDtlsDTO> scribeRegDtlsDTOList = new ArrayList<ScribeRegDtlsDTO>();
		List<ScribeRegEntity> scribeRegDtlsEntities = new ArrayList<ScribeRegEntity>();
		boolean isActiveFlag = false;
		if (null != scribeListRequest.getRequest() && null != scribeListRequest.getRequest().getScrbdrUserIDfk()
				&& !scribeListRequest.getRequest().getScrbdrUserIDfk().isEmpty()
				&& null != scribeListRequest.getRequest().getScrbisActive()
				&& !scribeListRequest.getRequest().getScrbisActive().isEmpty()
				&& (scribeListRequest.getRequest().getScrbisActive().equalsIgnoreCase("Y")
						|| scribeListRequest.getRequest().getScrbisActive().equalsIgnoreCase("N"))) {
			scribeRegDtlsEntities = scribeRegRepo.findScribeDetails(
					scribeListRequest.getRequest().getScrbdrUserIDfk().toLowerCase(),
					scribeListRequest.getRequest().getScrbisActive().toLowerCase());
		} else {
			logger.error("Request input fields should not be null and empty.");
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		if (null == scribeRegDtlsEntities || scribeRegDtlsEntities.size() <= 0) {
			logger.error("No record found for given inputs");
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.NO_DATA_FOUND, DrRegErrorMessage.NO_DATA_FOUND));
		}
		if (scribeListRequest.getRequest().getScrbisActive().equalsIgnoreCase("Y")) {
			isActiveFlag = true;
		}
		for (int i = 0; i < scribeRegDtlsEntities.size(); i++) {
			LoginUserEntity loginUserEntity = userDtlRepo
					.checkScribeIsActive(scribeRegDtlsEntities.get(i).getScrbUserID().toUpperCase(), isActiveFlag);
			if (null != loginUserEntity) {
				ScribeRegDtlsDTO scribeRegDtlsDTO = new ScribeRegDtlsDTO();
				BeanUtils.copyProperties(scribeRegDtlsEntities.get(i), scribeRegDtlsDTO);
				scribeRegDtlsDTOList.add(scribeRegDtlsDTO);
			}
		}
		mainResponseDTO.setResponse(scribeRegDtlsDTOList);
		mainResponseDTO.setStatus(true);
		mainResponseDTO.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		logger.info(" returning response");
		return mainResponseDTO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MainResponseDTO<?> activeDeactiveScribe(MainRequestDTO<ScribeRegDtlsDTO> scribeActivationRequest) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		AuditScribeRegEntity auditScribeRegEntity = new AuditScribeRegEntity();
		if (null != scribeActivationRequest.getRequest().getScrbUserID()
				&& !scribeActivationRequest.getRequest().getScrbUserID().isEmpty()
				&& null != scribeActivationRequest.getRequest().getScrbdrUserIDfk()
				&& !scribeActivationRequest.getRequest().getScrbdrUserIDfk().isEmpty()
				&& null != scribeActivationRequest.getRequest().getScrbisActive()
				&& !scribeActivationRequest.getRequest().getScrbisActive().isEmpty()
				&& (scribeActivationRequest.getRequest().getScrbisActive().equalsIgnoreCase("Y")
						|| scribeActivationRequest.getRequest().getScrbisActive().equalsIgnoreCase("N"))) {
			try {
				scribeRegRepo.activeDeactiveScribe(scribeActivationRequest.getRequest().getScrbUserID().toLowerCase(),
						scribeActivationRequest.getRequest().getScrbdrUserIDfk().toLowerCase(),
						scribeActivationRequest.getRequest().getScrbisActive().toUpperCase());
				boolean isActiveFlag = scribeActivationRequest.getRequest().getScrbisActive().equalsIgnoreCase("Y")
						? true
						: false;
				userDtlRepo.updateScribeIsActiveStatus(
						scribeActivationRequest.getRequest().getScrbUserID().toUpperCase(), isActiveFlag);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(" Exception while activate/deactivate scribe.", e);
				throw e;
			}

			// Inserted audited details
			try {
				ScribeRegEntity scribeRegEntity = scribeRegRepo.findScribe(
						scribeActivationRequest.getRequest().getScrbUserID().toLowerCase(),
						scribeActivationRequest.getRequest().getScrbdrUserIDfk().toLowerCase());
				BeanUtils.copyProperties(scribeRegEntity, auditScribeRegEntity);
				auditScribeRegEntity.setUserId(scribeRegEntity.getScrbCreadtedBy());
				scribeRegRepoAudited.save(auditScribeRegEntity);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(" Exception while saving audit information of scribe registration details.", e);
				throw e;
			}
		} else {
			logger.error("Request input fields should not be null or empty.");
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		mainResponseDTO.setResponse(
				scribeActivationRequest.getRequest().getScrbisActive().equalsIgnoreCase("Y") ? "Scribe activated"
						: "Scribe Deactivated");
		mainResponseDTO.setStatus(true);
		mainResponseDTO.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		logger.info(" returning response");
		return mainResponseDTO;
	}

	@Override
	public int deleteuploadedDocuments(String drd_user_id) {
		int status = 1;
		try {
			List<String> path = documentrepo.findDocumentPath(drd_user_id);
			File file = null;
			if (path != null) {
				for (String filepath : path) {
					file = new File(filepath);
					if (file.delete()) {
						// System.out.println(" File deleted ");
						documentrepo.deleteByUserId(drd_user_id);
					}
				}

			}
			// return documentrepo.deleteByUserId(drd_user_id);
		} catch (Exception e) {
			// TODO: handle exception
			status = 0;
		}
		return status;

	}

	@SuppressWarnings("unchecked")
	@Override
	public MainResponseDTO<String> saveuploadedDocuments(
			MainRequestDTO<DoctorRegDtlsDTO<DoctorDocsDtlsDTO>> registerRequest,
			DoctorRegDtlsEntity doctorrRegDtlsEntity) {
		MainResponseDTO<String> response = new MainResponseDTO<>();
		try {
			logger.info("Inside file save method");
			File byteStorePath = null;
			byteStorePath = new File(profilePhotoBytePath);
			String temppath;
			if (null != registerRequest.getRequest().getDocuments()) {
				for (int i = 0; i < registerRequest.getRequest().getDocuments().size(); i++) {
					logger.info("split the byte array request and conver with base 64 converter");
					if (registerRequest.getRequest().getDocuments().get(i).getFiles() != "") {
						if (registerRequest.getRequest().getDocuments().get(i).getFiles().length() < Long
								.parseLong(documentuploadsize)) {
							String[] strings = registerRequest.getRequest().getDocuments().get(i).getFiles().split(",");
							byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
							createDirectory(AuthConstant.DoctorRegistrationTempDirectory);
							if (doctorrRegDtlsEntity.getDrdDrFirstName() == null) {
								logger.info("External request received for file upload");
								doctorrRegDtlsEntity = registrationRepo
										.findDoctorDetailsByUserID(registerRequest.getRequest().getDrUserID());
							}
							createDirectory(AuthConstant.DoctorRegistrationTempDirectory + File.separator
									+ doctorrRegDtlsEntity.getDrdUserId());
							String[] text = strings[0].split(":");
							String[] givenname = text[1].split(";");
							String[] filename = givenname[0].split("/");
							logger.info("get path of file and save file to particular path");
							temppath = byteStorePath + File.separator + AuthConstant.DoctorRegistrationTempDirectory
									+ File.separator + doctorrRegDtlsEntity.getDrdUserId();
							String path = temppath + File.separator + filename[0];
							File file = new File(path);
							try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
								logger.info("write data to file");
								outputStream.write(data);
							} catch (IOException e) {
								e.printStackTrace();
								response.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
								logger.info("if path is not present to store file");
								return throwExceptionForFile();
							}
							if (path != null) {
								DoctorDocsDtlEntity doctorDocsDtlEntity = new DoctorDocsDtlEntity();
								logger.info("set all fields to doctor docs table");
								doctorDocsDtlEntity.setDdtDocsName(filename[0]);
								doctorDocsDtlEntity.setDdtDocsPath(path);
								doctorDocsDtlEntity.setDdtDocsType(filename[1]);
								doctorDocsDtlEntity.setDdtCreatedBy(registerRequest.getRequest().getDrUserID());
								doctorDocsDtlEntity.setDdtModifiedBy(registerRequest.getRequest().getDrUserID());
								doctorDocsDtlEntity.setDdtDocsRemark("document");
								doctorDocsDtlEntity.setDoctorRegDtlsEntity(doctorrRegDtlsEntity);
								// Save details to Document Table
								documentrepo.save(doctorDocsDtlEntity);
							}

							logger.info("set staus success");
							response = (MainResponseDTO<String>) DoctorAuthUtil.getMainResponseDto(registerRequest);
							response.setResponse(DoctorRegConstant.SUCCESS_UPLOAD_MSG.getMsg());
							response.setStatus(DoctorRegConstant.REGISTRATION_SUCCESS_STATUS.isStatus());
						} else {
							logger.info("error for file size exception,make object empty");
							response.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
							return throwExceptionFileSize();
						}

					} else {
						response.setResponse(DoctorRegConstant.EMPTY_FILES.getMsg());
					}
				}
			}

		} catch (Exception e) {
			response.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
			logger.error("error while file saving");
			return throwExceptionForFile();
		}
		return response;
	}

	/**
	 * @param docId
	 * @return
	 * 
	 * 		Added by girishk to get doctor document details by docid.
	 */
	@Override
	public DoctorDocsDtlEntity getDoctorDocDetailsByID(Integer docId) {
		DoctorDocsDtlEntity doctorDocsDtlEntity = doctorDocumentRepo.findByDdtDocIdPk(docId);
		if (null != doctorDocsDtlEntity) {
			return doctorDocsDtlEntity;
		} else {
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.NO_DATA_FOUND, DrRegErrorMessage.NO_DATA_FOUND));
		}
	}

	// flow change OTP verification process is exclude
	/*
	 * private MainResponseDTO<OtpResponseDTO>
	 * generateOTPDoctorUser(DoctorRegDtlsEntity docRegEntity) { logger.
	 * info("Request received Inside API of generate OTP from doctor-registartion");
	 * MainRequestDTO<OtpRequestDTO> mainRequest = new MainRequestDTO<>();
	 * OtpRequestDTO otpRequestDTO = new OtpRequestDTO();
	 * otpRequestDTO.setEmailID(docRegEntity.getDrdEmail());
	 * otpRequestDTO.setMobileNo(docRegEntity.getDrdMobileNo());
	 * otpRequestDTO.setOtpFor(DoctorRegConstant.OTP_FOR.getValidate());
	 * otpRequestDTO.setOtpGenerateTpye(DoctorRegConstant.OTP_GENERATE_TYPE.
	 * getValidate()); otpRequestDTO.setSendType(null != docRegEntity.getDrdEmail()
	 * && !docRegEntity.getDrdEmail().isEmpty() ?
	 * DoctorRegConstant.OTP_SEND_TYPE.getValidate() : "sms");
	 * otpRequestDTO.setUserId(docRegEntity.getDrdUserId());
	 * mainRequest.setRequest(otpRequestDTO);
	 * HttpEntity<MainRequestDTO<OtpRequestDTO>> requestEntity = new
	 * HttpEntity<MainRequestDTO<OtpRequestDTO>>(mainRequest);
	 * ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>>
	 * parameterizedResponse = new
	 * ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>>() { };
	 * ResponseEntity<MainResponseDTO<OtpResponseDTO>> response =
	 * restTemplate.exchange(generateOtpURL, HttpMethod.POST, requestEntity,
	 * parameterizedResponse); return response.getBody(); }
	 */

	private MainResponseDTO<String> validateRequestFields(DoctorRegDtlsDTO doctorRegDtlsDTO) {
		if ((doctorRegDtlsDTO.getDrFirstName() != null && doctorRegDtlsDTO.getDrFirstName().isEmpty()))
			return throwExceptionForDocFirstName();
		else if (doctorRegDtlsDTO.getDrLastName() != null && doctorRegDtlsDTO.getDrLastName().isEmpty())
			return throwExceptionForDocLastName();
		else if (doctorRegDtlsDTO.getDrMobNo() != null
				&& !validate.validateMobileNo(Long.valueOf(doctorRegDtlsDTO.getDrMobNo())))
			return thrownExceptionForMobile();
		else if (null != doctorRegDtlsDTO.getDrEmail() && !doctorRegDtlsDTO.getDrEmail().isEmpty()
				&& !validate.validateEmail(doctorRegDtlsDTO.getDrEmail()))
			return throwExceptionForEmail();
		else if (doctorRegDtlsDTO.getDrPassword() != null
				&& !validate.validatePassword(doctorRegDtlsDTO.getDrPassword()))
			return throwExceptionForDoctorPassword();
		else if (doctorRegDtlsDTO.getDrUserID() != null && doctorRegDtlsDTO.getDrUserID().isEmpty())
			return throwExceptionForDoctorUserID();
		else if (doctorRegDtlsDTO.getDrSMCNo() != null && doctorRegDtlsDTO.getDrSMCNo().isEmpty())
			return throwExceptionForDoctorSmcNO();
		else if (doctorRegDtlsDTO.getDrMCINo() != null && doctorRegDtlsDTO.getDrMCINo().isEmpty())
			return throwExceptionForDoctorMCINo();
		else if (doctorRegDtlsDTO.getDrSpecilization() != null && doctorRegDtlsDTO.getDrSpecilization().isEmpty())
			return throwExceptionForSpecilization();
		/*
		 * else if(doctorRegDtlsDTO.getDrSMCNo() != null && !
		 * validate.validateSMCNo(doctorRegDtlsDTO.getDrSMCNo())) return
		 * throwExceptionForSpecilizationFormat();
		 */
		/*
		 * else if(doctorRegDtlsDTO.getDrMCINo() != null && !
		 * validate.validateMCINo(doctorRegDtlsDTO.getDrMCINo())) return
		 * throwExceptionForMCISpecilizationFormat();
		 */
		else if (doctorRegDtlsDTO.getDrFirstName() != null
				&& !validate.validateFullName(doctorRegDtlsDTO.getDrFirstName()))
			return throwExceptionForDocFirstName();
		else if (doctorRegDtlsDTO.getDrLastName() != null
				&& !validate.validateFullName(doctorRegDtlsDTO.getDrLastName()))
			return throwExceptionForDocLastName();
		else if (doctorRegDtlsDTO.getDrAddress1().isEmpty())
			return throwExceptionForDocAddress();
		else if (doctorRegDtlsDTO.getDrUserID() != null && !validate.validateUserID(doctorRegDtlsDTO.getDrUserID()))
			return throwExceptionForDocUserID();
		else if (doctorRegDtlsDTO.getDrSMCNo().equals(doctorRegDtlsDTO.getDrMCINo()))
			return throwExceptionForSameNumbers();
		else if (doctorRegDtlsDTO.getAssociationName() != null && !doctorRegDtlsDTO.getAssociationName().isEmpty()) {
				if(!doctorRegDtlsDTO.getAssociationName().equals("Not selected")) {
			if ((doctorRegDtlsDTO.getAssociationNumber() != null
					&& !doctorRegDtlsDTO.getAssociationNumber().isEmpty())) {
				if (!validate.validateAssociationNumber(doctorRegDtlsDTO.getAssociationNumber())) {
					return throwExceptionAssociationNumberFormat();
				}
			} else {
				return throwExceptionAssociationNumber();
			}
				}
		}
		return null;
	}

	private MainResponseDTO<String> throwExceptionForSameNumbers() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.SMCMCI_ERROR.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.SMCMCI_ERROR.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	private MainResponseDTO<String> throwExceptionForDocUserID() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.USERID_ERROR.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.USERID_ERROR.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	private MainResponseDTO<String> throwExceptionForDocAddress() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.ADDRESS_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.ADDRESS_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	private MainResponseDTO<String> throwExceptionForSpecilization() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.Speciliztion_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.Speciliztion_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	private MainResponseDTO<String> throwExceptionForDoctorMCINo() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.MCINO_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.MCINO_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	private MainResponseDTO<String> throwExceptionForDoctorSmcNO() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.SMCNO_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.SMCNO_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	private MainResponseDTO<String> throwExceptionForEmail() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.EMAIL_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.EMAIL_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	private MainResponseDTO<String> thrownExceptionForMobile() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.MOBILE_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.MOBILE_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	private MainResponseDTO<String> throwExceptionForDocFirstName() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.DOCTOR_FIRSTNAME_VALIDATE.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.DOCTOR_FIRSTNAME_VALIDATE.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}
	
	private MainResponseDTO<String> throwExceptionForDocLastName() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.DOCTOR_LASTTNAME_VALIDATE.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.DOCTOR_LASTTNAME_VALIDATE.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}
	

	private MainResponseDTO<String> throwExceptionFileSize() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.FILE_SIZE_LIMIT.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.FILE_SIZE_LIMIT.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	private MainResponseDTO<String> throwExceptionProfilePhotoSize() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.SIZE_LIMIT.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.SIZE_LIMIT.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	private MainResponseDTO<String> throwExceptionForDoctorPassword() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.PASSWORD_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.PASSWORD_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	private MainResponseDTO<String> throwExceptionAssociationNumber() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.ASSOCIATION_NUMBER_EMPTY.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.ASSOCIATION_NUMBER_EMPTY.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}
	
	private MainResponseDTO<String> throwExceptionAssociationNumberFormat() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.ASSOCIATION_NUMBER_INCORRECT.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.ASSOCIATION_NUMBER_INCORRECT.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	private MainResponseDTO<String> throwExceptionForDoctorUserID() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.USERID_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.USERID_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	private MainResponseDTO<String> throwExceptionForAuditSave() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.AUDIT_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.AUDIT_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	@SuppressWarnings("rawtypes")
	private MainResponseDTO<String> verifyUniqueField(DoctorRegDtlsDTO doctorRegDtlsDTO) {
		mainResponse = new MainResponseDTO<>();
		if (registrationRepo.findByDocUserID(doctorRegDtlsDTO.getDrUserID().toUpperCase()).isPresent()) {
			listOfExceptions = new ArrayList<>();
			exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
			exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.USER_ID_EXIST.getCode());
			exceptionJSONInfoDTO.setMessage(DoctorRegConstant.USER_ID_EXIST.getMsg());
			listOfExceptions.add(exceptionJSONInfoDTO);
			mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
			mainResponse.setErrors(listOfExceptions);
			return mainResponse;
		} else if (registrationRepo.findByDocMobNo(doctorRegDtlsDTO.getDrMobNo()).isPresent()) {
			listOfExceptions = new ArrayList<>();
			exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
			exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.MOBILE_EXIST.getCode());
			exceptionJSONInfoDTO.setMessage(DoctorRegConstant.MOBILE_EXIST.getMsg());
			listOfExceptions.add(exceptionJSONInfoDTO);
			mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
			mainResponse.setErrors(listOfExceptions);
			return mainResponse;
		} else if (null != doctorRegDtlsDTO.getDrEmail() && !doctorRegDtlsDTO.getDrEmail().isEmpty()
				&& registrationRepo.findByDocEmail(doctorRegDtlsDTO.getDrEmail().toUpperCase()).isPresent())// changed
																											// by
																											// girishk
		{
			listOfExceptions = new ArrayList<>();
			exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
			exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.EMAIL_EXIST.getCode());
			exceptionJSONInfoDTO.setMessage(DoctorRegConstant.EMAIL_EXIST.getMsg());
			listOfExceptions.add(exceptionJSONInfoDTO);
			mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
			mainResponse.setErrors(listOfExceptions);
			return mainResponse;
		}
		mainResponse.setStatus(DoctorRegConstant.REGISTRATION_SUCCESS_STATUS.isStatus());
		return mainResponse;

	}

	public String getFilePathBasedOnOS() throws IOException {
		/*
		 * final String os = System.getProperty("os.name").toLowerCase(); return
		 * ((os.contains("windows")) ? LoadPropertyValues.WindowsDocumentPath :
		 * LoadPropertyValues.LinuxDocumentPath);
		 */
		return windowdocpath;
	}

	public void createDirectory(String folder_name) throws IOException {

		String directoryFilePath = getFilePathBasedOnOS() + "/" + folder_name;
		File file = new File(directoryFilePath);
		if (!file.exists()) {
			try {
				file.mkdir();
			} catch (Exception e) {
			}
		} else {
		}
	}

	@SuppressWarnings("rawtypes")
	public MainResponseDTO<String> saveAuditDetails(DoctorRegDtlsDTO doctorRegDtlsDTO) {
		try {
			// TODO Auto-generated method stub
			DoctorAuditDtlsEntity auditDtls = new DoctorAuditDtlsEntity();
			auditDtls.setDrdDrFirstName(doctorRegDtlsDTO.getDrFirstName().toUpperCase());
			auditDtls.setDrdDrMiddleName(doctorRegDtlsDTO.getDrMiddleName().toUpperCase());
			auditDtls.setDrdDrLastName(doctorRegDtlsDTO.getDrLastName().toUpperCase());
			auditDtls.setDrdConsulFee(doctorRegDtlsDTO.getDrConsultFee());
			auditDtls.setDrdEmail(null != doctorRegDtlsDTO.getDrEmail() && !doctorRegDtlsDTO.getDrEmail().isEmpty()
					? doctorRegDtlsDTO.getDrEmail().toUpperCase()
					: doctorRegDtlsDTO.getDrEmail());// changed by girishk
			auditDtls.setDrdMobileNo(doctorRegDtlsDTO.getDrMobNo());
			auditDtls.setDrdIsRegByIpan("y");
			auditDtls.setDrdIsverified("y");
			auditDtls.setDrdModifiedBy(doctorRegDtlsDTO.getDrFirstName().toUpperCase());
			auditDtls.setDrdOptiVersion(Long.valueOf(1));
			auditDtls.setDrdPassword(doctorRegDtlsDTO.getDrPassword());
			auditDtls.setDrdUserId(doctorRegDtlsDTO.getDrUserID().toUpperCase());
			auditDtls.setDrdMciNumber(doctorRegDtlsDTO.getDrMCINo());
			auditDtls.setDrdSmcNumber(doctorRegDtlsDTO.getDrSMCNo());
			auditDtls.setDrdSpecialiazation(doctorRegDtlsDTO.getDrSpecilization().toUpperCase());
			auditDtls.setDrdOtpRefidFk(0);
			auditDtls.setDrdAssociationName(doctorRegDtlsDTO.getAssociationName());
			auditDtls.setDrdAssociationNumber(doctorRegDtlsDTO.getAssociationNumber());
			auditDtls.setUserId(doctorRegDtlsDTO.getDrUserID().toUpperCase());
			// auditDtls.setTimestamp(timestamp);
			doctorAuditRepo.save(auditDtls);
		} catch (Exception e) {
			// TODO: handle exception
			throwExceptionForAuditSave();
		}
		return mainResponse;

	}

	/**
	 * @param doctorMstrDtlsDTO
	 * @return
	 * 
	 * 		Added by girishk to update doctor profile photo.
	 */
	private MainResponseDTO<String> updateDoctorProfilePhoto(DoctorMstrDtlsDTO doctorMstrDtlsDTO,
			DoctorMstrDtlsEntity updatedDoctorMstrDtlsEntity) {
		// TODO Auto-generated method stub
		try {
			File byteStorePath = null;
			byteStorePath = new File(profilePhotoBytePath);
			String temppath;
			MainResponseDTO<String> mainResponse = new MainResponseDTO<>();
			logger.info("Loading the updated doctor profile photo to file system");
			String[] strings = doctorMstrDtlsDTO.getProfilePhoto().split(",");
			String extension = "jpeg";
			byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
			createDirectory(AuthConstant.DoctorProfileDirectory);
			temppath = byteStorePath + File.separator + AuthConstant.DoctorProfileDirectory;
			String path = temppath + File.separator + updatedDoctorMstrDtlsEntity.getDmdMobileNo() + "_"
					+ updatedDoctorMstrDtlsEntity.getDmdUserId() + "." + extension;
			File file = new File(path);
			try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
				outputStream.write(data);
				logger.info("loaded updated doctor profile photo successfully to file system");
			} catch (IOException e) {
				e.printStackTrace();
				mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
			}
			mainResponse.setStatus(DoctorRegConstant.REGISTRATION_SUCCESS_STATUS.isStatus());
			updatedDoctorMstrDtlsEntity.setProfilePhoto(path);

		} catch (Exception e) {
			logger.error("Exception while updating profile photo : " + e);
			mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
		}
		return mainResponse;
	}

	/**
	 * @param profilePhotoPath
	 * @return
	 * 
	 * 		Added by girishk to get doctor profile photo.
	 */
	private String getProfilePhoto(String profilePhotoPath) {
		String finalResponse = null;
		try {
			finalResponse = new String(Base64.encodeBase64(Files.readAllBytes(Paths.get(profilePhotoPath))));
			finalResponse = "data:image/jpeg;base64," + finalResponse;
		} catch (Throwable e) {
			logger.error("Exception while getting doctor profile photo");
			e.printStackTrace();
		}
		// System.out.println("Response " + finalResponse);
		return finalResponse;
	}

	private MainResponseDTO<String> throwExceptionForFile() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.File_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.File_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	private MainResponseDTO<String> throwExceptionForCaptcha() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.CAPTCHA_VERIFY_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.CAPTCHA_VERIFY_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	private int deletexistsUserID(String drdUserId) {
		// TODO Auto-generated method stub
		int status = 1;
		try {
			registrationRepo.deleteByUserId(drdUserId.toUpperCase());
		} catch (Exception e) {
			// TODO: handle exception
			status = 0;
		}
		return status;
	}

	public MainResponseDTO<CaptchaResponseDto> verifyCaptcha(String captchaValue, String sessionId) {
		logger.info("Captcha Varification Api called");
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("sessionId", sessionId);
		requestHeaders.add("captchaValue", captchaValue);
		requestHeaders.add("flagValue", "true");
		HttpEntity<MainRequestDTO<CaptchaResponseDto>> requestEntity = new HttpEntity<MainRequestDTO<CaptchaResponseDto>>(
				requestHeaders);
		ParameterizedTypeReference<MainResponseDTO<CaptchaResponseDto>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<CaptchaResponseDto>>() {
		};
		ResponseEntity<MainResponseDTO<CaptchaResponseDto>> response = restTemplate.exchange(captchaVerificationURL,
				HttpMethod.POST, requestEntity, parameterizedResponse);
		logger.info("Captcha Api status : ");
		return response.getBody();
	}

	@Override
	public Long getCountOfDoctors() {
		Long count = 0L;
		try {
			count = doctorMstrRepo.count();
		} catch (Exception e) {
			logger.error("Exception while getting count of doctors.");
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MainResponseDTO<?> getScribeListByDoctorToActiveDeactive(
			MainRequestDTO<ScribeRegDtlsDTO> scribeListRequest) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		List<ScribeRegDtlsDTO> scribeRegDtlsDTOList = new ArrayList<ScribeRegDtlsDTO>();
		List<ScribeRegEntity> scribeRegDtlsEntities = new ArrayList<ScribeRegEntity>();
		if (null != scribeListRequest.getRequest() && null != scribeListRequest.getRequest().getScrbdrUserIDfk()
				&& !scribeListRequest.getRequest().getScrbdrUserIDfk().isEmpty()) {
			scribeRegDtlsEntities = scribeRegRepo.getScribeListByDoctorToActiveDeactive(
					scribeListRequest.getRequest().getScrbdrUserIDfk().toUpperCase());
		} else {
			logger.error("Request input fields should not be null and empty.");
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		if (null == scribeRegDtlsEntities || scribeRegDtlsEntities.size() <= 0) {
			logger.error("No record found for given inputs");
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.NO_DATA_FOUND, DrRegErrorMessage.NO_DATA_FOUND));
		}

		for (int i = 0; i < scribeRegDtlsEntities.size(); i++) {
			ScribeRegDtlsDTO scribeRegDtlsDTO = new ScribeRegDtlsDTO();
			BeanUtils.copyProperties(scribeRegDtlsEntities.get(i), scribeRegDtlsDTO);
			// fetch profile photo
			if (null != scribeRegDtlsEntities.get(i).getProfilePhotoPath()
					&& !scribeRegDtlsEntities.get(i).getProfilePhotoPath().isEmpty()) {
				String profilePhoto = getProfilePhoto(scribeRegDtlsEntities.get(i).getProfilePhotoPath());
				scribeRegDtlsDTO.setProfilePhoto(null != profilePhoto ? profilePhoto : "");
			}
			scribeRegDtlsDTOList.add(scribeRegDtlsDTO);
		}

		mainResponseDTO.setResponse(scribeRegDtlsDTOList);
		mainResponseDTO.setStatus(true);
		mainResponseDTO.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		logger.info(" returning response");
		return mainResponseDTO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MainResponseDTO<?> changeDefaultScribe(MainRequestDTO<ScribeRegDtlsDTO> changeDefaultScribeRequest,
			String doctorUserId) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		if (null != changeDefaultScribeRequest.getRequest().getScrbUserID()
				&& !changeDefaultScribeRequest.getRequest().getScrbUserID().isEmpty()) {
			try {
				scribeRegRepo.changeDefaultScribe(changeDefaultScribeRequest.getRequest().getScrbUserID().toUpperCase(),
						doctorUserId.toUpperCase());
			} catch (Exception e) {
				logger.error("Error while changing default scribe");
				e.printStackTrace();
				throw e;
			}
		} else {
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}
		mainResponseDTO.setResponse("Scribe changed to default successfully..");
		mainResponseDTO.setStatus(true);
		mainResponseDTO.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		mainResponseDTO.setVersion(changeDefaultScribeRequest.getVersion());
		logger.info(" returning response");
		return mainResponseDTO;
	}

	@SuppressWarnings("serial")
	@Override
	public MainResponseDTO<List<AppointmentDetailsResponseDTO>> searchCompletedAppointmentsForDoctor(
			MainRequestDTO<AppontmentDetailsRequestDTO> appointmentDetailsRequest) {
		List<AppointmentDtlsEntity> completedAppointmentEntities = new ArrayList<AppointmentDtlsEntity>();
		List<AppointmentDetailsResponseDTO> completedAppointmentDTOs = new ArrayList<AppointmentDetailsResponseDTO>();
		MainResponseDTO<List<AppointmentDetailsResponseDTO>> response = new MainResponseDTO<List<AppointmentDetailsResponseDTO>>();
		try {
			completedAppointmentEntities = appointmentDtlsRepository
					.findAll(new Specification<AppointmentDtlsEntity>() {
						@Override
						public Predicate toPredicate(Root<AppointmentDtlsEntity> root, CriteriaQuery<?> query,
								CriteriaBuilder criteriaBuilder) {
							List<Predicate> predicates = new ArrayList<>();
							if ((null != appointmentDetailsRequest.getRequest().getFromDate()
									&& null != appointmentDetailsRequest.getRequest().getToDate()
									&& !appointmentDetailsRequest.getRequest().getFromDate().isEmpty()
									&& !appointmentDetailsRequest.getRequest().getToDate().isEmpty())
									|| (null != appointmentDetailsRequest.getRequest().getToDate()
											&& !appointmentDetailsRequest.getRequest().getToDate().isEmpty())) {
								predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(
										root.get("adApptDateFk"),
										formatStringToDate(appointmentDetailsRequest.getRequest().getFromDate()))));
								predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(
										root.get("adApptDateFk"),
										formatStringToDate(appointmentDetailsRequest.getRequest().getToDate()))));
							}
							if (null != appointmentDetailsRequest.getRequest().getApptId()
									&& !appointmentDetailsRequest.getRequest().getApptId().isEmpty()) {
								predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("adApptId"),
										appointmentDetailsRequest.getRequest().getApptId())));
							}
							if (null != appointmentDetailsRequest.getRequest().getPatientFirstName()
									&& !appointmentDetailsRequest.getRequest().getPatientFirstName().isEmpty()) {
								predicates.add(criteriaBuilder.and(criteriaBuilder.like(
										criteriaBuilder.upper(root.get("patientRegDtlsEntity").get("prdPtFirstName")),
										"%" + appointmentDetailsRequest.getRequest().getPatientFirstName().toUpperCase()
												+ "%")));
							}
							if (null != appointmentDetailsRequest.getRequest().getPatientMiddleName()
									&& !appointmentDetailsRequest.getRequest().getPatientMiddleName().isEmpty()) {
								predicates.add(criteriaBuilder.and(criteriaBuilder.like(
										criteriaBuilder.upper(root.get("patientRegDtlsEntity").get("prdPtMiddleName")),
										"%" + appointmentDetailsRequest.getRequest().getPatientMiddleName()
												.toUpperCase() + "%")));
							}
							if (null != appointmentDetailsRequest.getRequest().getPatientLastName()
									&& !appointmentDetailsRequest.getRequest().getPatientLastName().isEmpty()) {
								predicates.add(criteriaBuilder.and(criteriaBuilder.like(
										criteriaBuilder.upper(root.get("patientRegDtlsEntity").get("prdPtLastName")),
										"%" + appointmentDetailsRequest.getRequest().getPatientLastName().toUpperCase()
												+ "%")));
							}
							predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("adApptStatus"), "C")));
							predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("docMstrDtlsEntity"),
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
					appointmentDetailsResponseDTO.setDoctorId(appointmentDtlsEntity.getDocMstrDtlsEntity());
					appointmentDetailsResponseDTO
							.setPatientId(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdUserId());
					if (appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdPtMiddleName() == null
							&& appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdPtLastName() != null) {
						appointmentDetailsResponseDTO
								.setPatientName(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdPtFirstName()
										+ " " + appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdPtLastName());
					} else {
						appointmentDetailsResponseDTO
								.setPatientName(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdPtFirstName()
										+ " " + appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdPtMiddleName()
										+ " " + appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdPtLastName());

					}
					completedAppointmentDTOs.add(appointmentDetailsResponseDTO);

				}
				// completedAppointmentDTOs.sort(Comparator.comparing(AppointmentDetailsResponseDTO::getAppointmentDate));
			} else {
				throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.NO_APPOINTMENT_FOUND,
						DrRegErrorMessage.NO_APPOINTMENT_FOUND));
			}
		} catch (Exception e) {
			logger.error("Exception while getting appointment details.");
			e.printStackTrace();
			throw e;
		}

		response.setVersion("1.0");
		response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
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
			logger.error("Exception while parsing date.");
			e1.printStackTrace();
		}
		return finalDate;
	}

	@Override
	public MainResponseDTO<List<DoctorActiveDTO>> getMappedDrListByPatientId(
			MainRequestDTO<PatientDetailsRequestDTO> requestDto) {

		// TODO Auto-generated method stub
		MainResponseDTO<List<DoctorActiveDTO>> response = new MainResponseDTO<List<DoctorActiveDTO>>();
		List<DoctorMstrDtlsEntity> drList = new ArrayList<DoctorMstrDtlsEntity>();
		List<DoctorActiveDTO> list = new ArrayList<>();
		List<ExceptionJSONInfoDTO> errors = new ArrayList<ExceptionJSONInfoDTO>();
		try {

			if (requestDto.getRequest() == null || requestDto.getRequest().getPtRegId() == null
					|| requestDto.getRequest().getPtRegId() == "") {
				response.setStatus(false);
				response.setVersion("1.0");
				response.setId("Doctor List");
				response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
				ExceptionJSONInfoDTO ex = new ExceptionJSONInfoDTO();
				ex.setErrorCode(DrRegErrorConstant.PATIENT_ID_NOT_BLANK);
				ex.setMessage(DrRegErrorMessage.PATIENT_ID_NOT_BLANK);
				errors.add(ex);
				response.setErrors(errors);
				return response;
			}
			drList = doctorMstrRepo.getMappedDrListByPatientId(requestDto.getRequest().getPtRegId());
			if (null != drList && drList.size() > 0) {
				for (DoctorMstrDtlsEntity doctorMstrDtlsEntity : drList) {
					DoctorActiveDTO activeDTO = new DoctorActiveDTO();

					activeDTO.setDocFirstName(doctorMstrDtlsEntity.getDmdDrFirstName());
					activeDTO.setDocMiddleName(doctorMstrDtlsEntity.getDmdDrMiddleName());
					activeDTO.setDocLastName(doctorMstrDtlsEntity.getDmdDrLastName());
					activeDTO.setDocUserID(doctorMstrDtlsEntity.getDmdUserId());
					activeDTO.setEmailID(doctorMstrDtlsEntity.getDmdEmail());
					activeDTO.setMICNO(doctorMstrDtlsEntity.getDmdMciNumber());
					activeDTO.setMobile(doctorMstrDtlsEntity.getDmdMobileNo());
					activeDTO.setPhotopath(doctorMstrDtlsEntity.getProfilePhoto());
					activeDTO.setSMCNO(doctorMstrDtlsEntity.getDmdSmcNumber());
					activeDTO.setSpecialization(doctorMstrDtlsEntity.getDmdSpecialiazation());
					list.add(activeDTO);
				}
				response.setStatus(true);
				response.setResponse(list);
			} else {
				response.setStatus(false);
				response.setVersion("1.0");
				response.setId("Doctor List");
				response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
				ExceptionJSONInfoDTO ex = new ExceptionJSONInfoDTO();
				ex.setErrorCode(DrRegErrorConstant.NO_DATA_FOUND);
				ex.setMessage(DrRegErrorMessage.NO_DATA_FOUND);
				errors.add(ex);
				response.setErrors(errors);
				return response;
			}

		} catch (Exception e) {
			response.setStatus(false);
			response.setVersion("1.0");
			response.setId("Doctor List");
			response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
			ExceptionJSONInfoDTO ex = new ExceptionJSONInfoDTO();
			ex.setErrorCode(DrRegErrorConstant.SERVER_ERROR);
			ex.setMessage(DrRegErrorMessage.SERVER_ERROR);
			errors.add(ex);
			response.setErrors(errors);
			return response;
		}
		response.setVersion("1.0");
		response.setId("Doctor List");
		response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		return response;

	}

	@Override
	public MainResponseDTO<Boolean> checkDoctorTagByPatientId(MainRequestDTO<PatientDetailsRequestDTO> requestDto) {
		MainResponseDTO<Boolean> response = new MainResponseDTO<Boolean>();

		if(requestDto == null || requestDto.getRequest() == null || requestDto.getRequest().getPtRegId() == null) {
			response.setId("Doctor Patient Tag status boolean");
			response.setVersion("1.0");
			response.setResponse(false);
			response.setStatus(false);
			response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
            return response;
		}
		MainResponseDTO<List<DoctorActiveDTO>> mainResponseDTO = getMappedDrListByPatientId(requestDto);

		BeanUtils.copyProperties(mainResponseDTO, response);
		response.setId("Doctor Patient Tag status boolean");

		response.setResponse(mainResponseDTO.getResponse() != null ? mainResponseDTO.getResponse()
				.stream().anyMatch(dt -> dt.getDocUserID().equals(requestDto.getRequest().getDrRegId())) : false);

		return response;
	}

	@Override
	public String updateTermsAndConditionForDoctor() {

		String userId = userDto.getRole().equalsIgnoreCase(DoctorRegConstant.DOCTOR.getValidate())
				? userDto.getUserName()
				: null;

		if (userId == null) {
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		int regStatus = registrationRepo.updateTermsAndConditionForDoctor(userId);
		int masterStatus = doctorMstrRepo.updateTermsAndConditionForDoctor(userId);

		if (regStatus == 0 || masterStatus == 0) {
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.DOCTOR_NOT_FOUND, DrRegErrorMessage.DOCTOR_NOT_FOUND));
		}

		return DoctorRegConstant.SUCCESSFUL_UPDATION_TERMS_AND_CONDITION.getMsg();
	}

	@Override
	public BulkDoctorResponseDTO saveBulkDoctorRegistrationDetails(BulkDoctorRegistrationDTO registerRequest) {

		BulkDoctorResponseDTO response = new BulkDoctorResponseDTO();
		String filePath = null;
		String excelFileInfo[] = null;
		String excelFileName = null;
		byte[] data = null;

		long jobId = Instant.now().getEpochSecond();
		if (registerRequest != null && registerRequest.getExcelFileOfBulkDoctorDtls() != null
				&& !registerRequest.getExcelFileOfBulkDoctorDtls().isEmpty()) {
			filePath = prescriptionPath + pathSeperator + "bulk_doctor_details" + pathSeperator;
			//filePath = "C:\\Users\\neosoft_satishg\\Desktop" + "\\" + "bulk_doctor_details" + "\\";
			excelFileInfo = registerRequest.getExcelFileOfBulkDoctorDtls().split(",");
			excelFileName = registerRequest.getFileName().contains("xlsx")
					? registerRequest.getFileName() + "_" + jobId + ".xlsx"
					: registerRequest.getFileName() + "_" + jobId + ".xls";
			data = DatatypeConverter.parseBase64Binary(excelFileInfo[1].trim());
		} else {
			logger.info("Exception while processing base64 excel file.");
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		uploadBulkDoctorDtlsFile(filePath, excelFileName, data);
		List<String> registeredDoctors = createAndVerifyDoctor(filePath, excelFileName, jobId);
		writeDoctorStatusIntoExcelFile(registeredDoctors, filePath, excelFileName);
		logger.info("API success, insertion in all tables successfull");

		if (!registeredDoctors.isEmpty()) {
			response.setInfo("Doctor registration successfull");
			response.setFileName(excelFileName);

			try {
				Path path = Paths.get(filePath + excelFileName);
				response.setData(DatatypeConverter.printBase64Binary(Files.readAllBytes(path)));
				Files.deleteIfExists(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.READ_BULK_FILE_FAILED,
						DrRegErrorMessage.READ_BULK_FILE_FAILED));
			}
		} else {
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.DOCTOR_REGISTRATION_FAILED,
					DrRegErrorMessage.DOCTOR_REGISTRATION_FAILED));
		}

		return response;
	}

	private void uploadBulkDoctorDtlsFile(String filePath, String excelFileName, byte[] data) {
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
			logger.info("write data to file");
			outputStream.write(data);
		} catch (IOException e) {
			logger.error("Exception while uploading doctor details excel file.");
			e.printStackTrace();
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.BULK_FILE_UPLOAD_FAILED,
					DrRegErrorMessage.BULK_FILE_UPLOAD_FAILED));
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<String> createAndVerifyDoctor(String filePath, String excelFileName, long jobId) {

		logger.info("Starting excelFileToDatabase job");
		JobParameters jobParameters = new JobParametersBuilder().addString("fileName", filePath + excelFileName)
				.addLong("JobId", jobId).toJobParameters();
		try {
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.READ_BULK_FILE_FAILED,
					DrRegErrorMessage.READ_BULK_FILE_FAILED));
		}

		logger.info("Stopping excelFileToDatabase job");

		List<DoctorRegDtlsEntity> docRegEntities = registrationRepo.findDoctorsByJobId(String.valueOf(jobId));

		if (docRegEntities == null || docRegEntities.isEmpty()) {
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.DOCTOR_LIST_EMPTY, DrRegErrorMessage.DOCTOR_LIST_EMPTY));
		}

		Map<String, String> doctorList = docRegEntities.stream()
				.collect(Collectors.toMap(DoctorRegDtlsEntity::getDrdUserId, DoctorRegDtlsEntity::getDrdDrFirstName));

		// verify doctor
		docRegEntities.parallelStream().forEach(doctor -> {

			boolean status = false;
			try {
				MainResponseDTO<CommonResponseDTO> level1Response = verifyDoctor(doctor.getDrdUserId());
				if (level1Response == null || level1Response.getResponse() == null || !level1Response.getResponse()
						.getMessage().equalsIgnoreCase("Doctor verified successfully")) {
					registrationRepo.deleteByUserId(doctor.getDrdUserId());
					doctorAuditRepo.deleteByUserId(doctor.getDrdUserId());
					doctorList.remove(doctor.getDrdUserId());
					return;
				}

				MainResponseDTO<CommonResponseDTO> level2Response = verifyDoctor(doctor.getDrdUserId());
				if (level2Response == null || level2Response.getResponse() == null || !level2Response.getResponse()
						.getMessage().equalsIgnoreCase("Doctor verified successfully")) {
					registrationRepo.deleteByUserId(doctor.getDrdUserId());
					doctorAuditRepo.deleteByUserId(doctor.getDrdUserId());
					doctorList.remove(doctor.getDrdUserId());
					return;
				}

				status = true;
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("verification failed for doctor {}", doctor.getDrdUserId());
				registrationRepo.deleteByUserId(doctor.getDrdUserId());
				doctorAuditRepo.deleteByUserId(doctor.getDrdUserId());
				doctorList.remove(doctor.getDrdUserId());
			}

			// save slot for doctor after verification
			if (status) {
				try {
					saveSlotForDoctor(doctor.getDrdUserId(), doctor.getDrdConsulFee());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("slot creation failed for doctor {}", doctor.getDrdUserId());
				}
			}
		});

		// disable login for doctors
		if (!doctorList.isEmpty()) {
			userDtlRepo.updateDoctorForLoginDisabledStatus(doctorList.keySet());
			logger.info("registration done for doctors {}", doctorList);
			return new ArrayList(doctorList.values());
		}
		return Arrays.asList();
	}

	private void writeDoctorStatusIntoExcelFile(List<String> doctorNames, String filePath, String excelFileName) {
		Workbook workbook = null;
		FileOutputStream out = null;
		try {
			workbook = WorkbookFactory.create(new FileInputStream(filePath + excelFileName));
			Sheet firstSheet = workbook.getSheetAt(0);
			Row row = firstSheet.getRow(0);

			int nameIndex = 0;
			int resultIndex = 0;
			short minColIx = row.getFirstCellNum();
			short maxColIx = row.getLastCellNum();
			for (short colIx = minColIx; colIx < maxColIx; colIx++) {
				Cell cell = row.getCell(colIx);
				if (cell.getCellType() == CellType.STRING
						&& cell.getStringCellValue().equalsIgnoreCase(DoctorRegEnum.NAME)) {
					nameIndex = cell.getColumnIndex();
					break;
				}
			}

			// Update the value of last cell
			Cell resultCell = row.createCell(maxColIx);
			resultCell.setCellValue(DoctorRegEnum.RESULT);
			resultIndex = maxColIx;

			Iterator<Row> iterator = firstSheet.iterator();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();

				if (nextRow.getRowNum() == 0) {
					continue;
				}

				Cell cell = nextRow.getCell(nameIndex);
				if (cell != null && cell.getCellType() == CellType.STRING
						&& doctorNames.contains(cell.getStringCellValue().toUpperCase())) {
					Cell nextResult = nextRow.createCell(resultIndex);
					nextResult.setCellValue(true);
				} else {
					Cell nextResult = nextRow.createCell(resultIndex);
					nextResult.setCellValue(false);
				}
			}
			out = new FileOutputStream(new File(filePath + excelFileName));
			workbook.write(out);

		} catch (Exception e) {
			logger.error("Exception while reading/writing doctor details from excel file.");
			e.printStackTrace();
		} finally {
			try {
				out.close();
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private MainResponseDTO<CommonResponseDTO> verifyDoctor(String doctorUserId) {

		MainRequestDTO<VerifyDoctorRequestDTO> mainRequest = new MainRequestDTO<>();
		VerifyDoctorRequestDTO dto = new VerifyDoctorRequestDTO();
		dto.setRegDocUserName(doctorUserId);
		dto.setIsIpanorMarsha("I");
		dto.setVerificationStatusFlag("Y");
		dto.setReason("");
		mainRequest.setRequest(dto);

		HttpHeaders headers = new HttpHeaders();
		RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
		if (attribs instanceof NativeWebRequest) {
			HttpServletRequest request = (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
			headers.set(HttpHeaders.AUTHORIZATION, request.getHeader("Authorization"));
		}

		HttpEntity<MainRequestDTO<VerifyDoctorRequestDTO>> requestEntity = new HttpEntity<MainRequestDTO<VerifyDoctorRequestDTO>>(
				mainRequest, headers);
		ParameterizedTypeReference<MainResponseDTO<CommonResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<CommonResponseDTO>>() {
		};
		ResponseEntity<MainResponseDTO<CommonResponseDTO>> response = restTemplate.exchange(verifyDoctorURL,
				HttpMethod.POST, requestEntity, parameterizedResponse);
		return response.getBody();
	}

	private MainResponseDTO<String> saveSlotForDoctor(String doctorUserId, int consultAmount) {

		MainRequestDTO<SlotDetailsDto> mainRequest = new MainRequestDTO<>();
		SlotDetailsDto dto = new SlotDetailsDto();
		dto.setSlotDuration(10);
		dto.setRepetForMonths(1);
		dto.setConsultAmount(consultAmount);
		dto.setAutoRep(false);
		dto.setSlotType("In-Clinic");
		dto.setDoctorUserID(doctorUserId);

		LocalDateTime date = LocalDateTime.now();
		dto.setSlotWorkingDays(new String[] { date.getDayOfWeek().name().toLowerCase().substring(0, 3) });

		WorkingTime time = new WorkingTime();
		time.setStartTime(String.valueOf(date.getHour()) + ":" + String.valueOf(date.getMinute()));
		time.setEndTime(String.valueOf(date.getHour()) + ":" + String.valueOf(date.plusMinutes(10).getMinute()));
		dto.setWorkingTime(Arrays.asList(time));
		mainRequest.setRequest(dto);

		HttpHeaders headers = new HttpHeaders();
		RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
		if (attribs instanceof NativeWebRequest) {
			HttpServletRequest request = (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
			headers.set(HttpHeaders.AUTHORIZATION, request.getHeader("Authorization"));
		}

		HttpEntity<MainRequestDTO<SlotDetailsDto>> requestEntity = new HttpEntity<MainRequestDTO<SlotDetailsDto>>(
				mainRequest, headers);
		ParameterizedTypeReference<MainResponseDTO<String>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<String>>() {
		};
		ResponseEntity<MainResponseDTO<String>> response = restTemplate.exchange(saveSlotDoctorURL, HttpMethod.POST,
				requestEntity, parameterizedResponse);
		return response.getBody();
	}

	@Override
	public String getDoctorProfilePath(String drEmailID) {

		if (!validate.validateEmail(drEmailID)) {
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.DOCTOR_EMAIL_VALIDATION_FAILED,
					DrRegErrorMessage.DOCTOR_EMAIL_VALIDATION_FAILED));
		}

		DoctorMstrDtlsEntity entity = doctorMstrRepo.findByDmdEmailId(drEmailID.toUpperCase());

		if (entity == null) {
			throw new DoctorRegistrationException(
					new ServiceErrors(DrRegErrorConstant.DOCTOR_NOT_FOUND, DrRegErrorMessage.DOCTOR_NOT_FOUND));
		}

		if (entity.getProfilePhoto() == null || entity.getProfilePhoto().isEmpty()) {
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.DOCTOR_PROFILE_PHOTO_NOT_PRESENT,
					DrRegErrorMessage.DOCTOR_PROFILE_PHOTO_NOT_PRESENT));
		}
		return entity.getProfilePhoto().replace(profilePhotoBytePath, profilePhotoBytePathUrl);
	}

	@Override
	public CategoryDtlsResponse fetchCategoryDtlsByDrId(String drUserName) {
		
		CategoryDtlsResponse categoryDtlsResponse = new CategoryDtlsResponse();
		List<CategoryDto> categoryDtoList = new ArrayList<>();
		List<DoctorFeatureMapEntity> doctorFeatureMapEntityList = doctorFeatureMapRepo.getCategoryDtls(drUserName);
		if(doctorFeatureMapEntityList.size() != 0 && doctorFeatureMapEntityList!=null && !doctorFeatureMapEntityList.isEmpty()) {
			 categoryDtoList = getCategoryType(doctorFeatureMapEntityList);
			
			categoryDtlsResponse.setDrUserId(drUserName);
			categoryDtlsResponse.setCategory(categoryDtoList);
			
		}else {
			categoryDtlsResponse.setDrUserId(drUserName);
			categoryDtlsResponse.setCategory(categoryDtoList);
		}
	  return categoryDtlsResponse;
	}
	
	private List<CategoryDto> getCategoryType(List<DoctorFeatureMapEntity> doctorFeatureMapEntityList) {
		
	       List<CategoryDto> categoryDtoList = new ArrayList<CategoryDto>();
	    for(DoctorFeatureMapEntity doctorFeatureMapEntity : doctorFeatureMapEntityList) {	
	    	CategoryDto categoryDto = new CategoryDto();
	    	categoryDto.setCategoryName(doctorFeatureMapEntity.getDfmdCategoryName());
	    	categoryDto.setCategoryFlag(doctorFeatureMapEntity.getDfmdFlag());
	    	categoryDtoList.add(categoryDto);
	    }
	    return categoryDtoList;
	}	
}