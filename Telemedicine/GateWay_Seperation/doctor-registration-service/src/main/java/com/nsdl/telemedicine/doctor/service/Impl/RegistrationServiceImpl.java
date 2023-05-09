package com.nsdl.telemedicine.doctor.service.Impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.nsdl.telemedicine.doctor.constant.AuthConstant;
import com.nsdl.telemedicine.doctor.constant.DoctorRegConstant;
import com.nsdl.telemedicine.doctor.dto.AppointmentDetailsResponseDTO;
import com.nsdl.telemedicine.doctor.dto.AppontmentDetailsRequestDTO;
import com.nsdl.telemedicine.doctor.dto.CaptchaResponseDto;
import com.nsdl.telemedicine.doctor.dto.DoctorDocDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorDocsDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorMstrDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorRegDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.doctor.dto.MainRequestDTO;
import com.nsdl.telemedicine.doctor.dto.MainResponseDTO;
import com.nsdl.telemedicine.doctor.dto.OtpRequestDTO;
import com.nsdl.telemedicine.doctor.dto.OtpResponseDTO;
import com.nsdl.telemedicine.doctor.dto.ScribeRegDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.UserDTO;
import com.nsdl.telemedicine.doctor.dto.UserDetailsDTO;
import com.nsdl.telemedicine.doctor.dto.UserDetailsResponseDTO;
import com.nsdl.telemedicine.doctor.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.doctor.entity.AuditScribeRegEntity;
import com.nsdl.telemedicine.doctor.entity.DoctorAuditDtlsEntity;
import com.nsdl.telemedicine.doctor.entity.DoctorDocsDtlEntity;
import com.nsdl.telemedicine.doctor.entity.DoctorMstrDtlsEntity;
import com.nsdl.telemedicine.doctor.entity.DoctorRegDtlsEntity;
import com.nsdl.telemedicine.doctor.entity.LoginUserEntity;
import com.nsdl.telemedicine.doctor.entity.ScribeRegEntity;
import com.nsdl.telemedicine.doctor.exception.DoctorRegistrationException;
import com.nsdl.telemedicine.doctor.exception.DrRegErrorConstant;
import com.nsdl.telemedicine.doctor.exception.DrRegErrorMessage;
import com.nsdl.telemedicine.doctor.exception.ServiceErrors;
import com.nsdl.telemedicine.doctor.loggers.DoctorLoggingClientInfo;
import com.nsdl.telemedicine.doctor.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.doctor.repository.DoctorAuditRepo;
import com.nsdl.telemedicine.doctor.repository.DoctorDocumentRepo;
import com.nsdl.telemedicine.doctor.repository.DoctorMstrRepo;
import com.nsdl.telemedicine.doctor.repository.DocumentRepo;
import com.nsdl.telemedicine.doctor.repository.RegistrationRepo;
import com.nsdl.telemedicine.doctor.repository.ScribeRegRepo;
import com.nsdl.telemedicine.doctor.repository.ScribeRegRepoAudited;
import com.nsdl.telemedicine.doctor.repository.UserDtlRepo;
import com.nsdl.telemedicine.doctor.service.RegistrationService;
import com.nsdl.telemedicine.doctor.utility.CommonValidationUtil;
import com.nsdl.telemedicine.doctor.utility.DateUtils;
import com.nsdl.telemedicine.doctor.utility.DoctorAuthUtil;

@Service
@DoctorLoggingClientInfo
public class RegistrationServiceImpl implements RegistrationService{
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);
	
	@Value("${CREATE_USER_URL}")
	private String createUserURL;
	
	@Value("${OTP_GENERATE_URL}")
	private String generateOtpURL;
	
	@Value("${WindowDocPath}")
	private  String windowdocpath;
	
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
	
	@Value("${ProfilePhotoPath}")
	private  String profilePhotoBytePath;
	
	@Value("${CAPTCHA_VERIFY_URL}")
	private String captchaVerificationURL;
	
	@Value("${ProfilePhotoSize}")
	private String profilephotosize;
	
	@Value("${DocumentUploadSize}")
	private String documentuploadsize;
	
	@Value("${Update_user_details_URL}")
	private String updateUserDetailsURL;

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
	
	/*
	 * Code Added by sayaliA to save registartion details as well as upload document
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public MainResponseDTO<String> saveDoctorRegistrationDetails(MainRequestDTO<DoctorRegDtlsDTO<DoctorDocsDtlsDTO>> registerRequest) throws Exception {
		// TODO Auto-generated method stub
		@SuppressWarnings("rawtypes")
		DoctorRegDtlsDTO doctorRegDtlsDTO=registerRequest.getRequest();
		DoctorRegDtlsEntity doctorrRegDtlsEntity  = new DoctorRegDtlsEntity();
		MainResponseDTO<String> response = (MainResponseDTO<String>) DoctorAuthUtil.getMainResponseDto(registerRequest);
		MainResponseDTO<CaptchaResponseDto> responseCaptcha=null;
		responseCaptcha=verifyCaptcha(registerRequest.getRequest().getCaptchaCode(),registerRequest.getRequest().getSessionId());
		if(!responseCaptcha.isStatus())
		{
			return throwExceptionForCaptcha();
		}
		
		response = validateRequestFields(doctorRegDtlsDTO);
		if(response != null) {
			if(!response.getErrors().isEmpty()) {
				 return response;
			 }
		}
		response = verifyUniqueField(doctorRegDtlsDTO);
		if(!response.isStatus()) {
			return response;
		}
		response=saveDoctorProfilePhoto(registerRequest.getRequest(),doctorrRegDtlsEntity);
		if(!response.isStatus())
		{
			return response;
		}
		else
		{
		doctorrRegDtlsEntity.setDrdDrName(registerRequest.getRequest().getDrFullName().toUpperCase());
		doctorrRegDtlsEntity.setDrdConsulFee(registerRequest.getRequest().getDrConsultFee());
		doctorrRegDtlsEntity.setDrdEmail(null != registerRequest.getRequest().getDrEmail() && !registerRequest.getRequest().getDrEmail().isEmpty() ? registerRequest.getRequest().getDrEmail().toUpperCase() : "");//changed by girishk
		doctorrRegDtlsEntity.setDrdMobileNo(registerRequest.getRequest().getDrMobNo());
		doctorrRegDtlsEntity.setDrdIsRegByIpan("N");
		doctorrRegDtlsEntity.setDrdIsverified("O");
		doctorrRegDtlsEntity.setDrdModifiedBy(registerRequest.getRequest().getDrFullName().toUpperCase());
		doctorrRegDtlsEntity.setDrdOptiVersion(Long.valueOf(1));
		//doctorrRegDtlsEntity.setDrdPassword(HMACUtils.hash(registerRequest.getRequest().getDrPassword().getBytes(StandardCharsets.UTF_8)));
		doctorrRegDtlsEntity.setDrdPassword(registerRequest.getRequest().getDrPassword());
		doctorrRegDtlsEntity.setDrdUserId(registerRequest.getRequest().getDrUserID().toUpperCase());
		doctorrRegDtlsEntity.setDrdMciNumber(registerRequest.getRequest().getDrMCINo());
		doctorrRegDtlsEntity.setDrdSmcNumber(registerRequest.getRequest().getDrSMCNo());
		doctorrRegDtlsEntity.setDrdSpecialiazation(registerRequest.getRequest().getDrSpecilization().toUpperCase());
		doctorrRegDtlsEntity.setDrdOtpRefidFk(0);
		doctorrRegDtlsEntity.setDrdGender(registerRequest.getRequest().getDrGender());
		doctorrRegDtlsEntity.setDrdAddress1(registerRequest.getRequest().getDrAddress1().toUpperCase());
		if(registerRequest.getRequest().getDrAddress2()!="")
		{
			doctorrRegDtlsEntity.setDrdAddress2(registerRequest.getRequest().getDrAddress2().toUpperCase());
		}
		if(registerRequest.getRequest().getDrAddress3()!="")
		{
			doctorrRegDtlsEntity.setDrdAddress3(registerRequest.getRequest().getDrAddress3().toUpperCase());
		}
		doctorrRegDtlsEntity.setDrdState(registerRequest.getRequest().getDrState());
		doctorrRegDtlsEntity.setDrdCity(registerRequest.getRequest().getDrCity());
		response=saveuploadedDocuments(registerRequest,doctorrRegDtlsEntity);
		if(!response.isStatus())
		{
			//Call delete API to delete halfly stored Files which are less than 1 MB(transaction rollback)
			deleteuploadedDocuments(doctorrRegDtlsEntity.getDrdUserId());
			deletexistsUserID(doctorrRegDtlsEntity.getDrdUserId());
			return response;
		}else
		{
			DoctorRegDtlsEntity docRegEntity=registrationRepo.save(doctorrRegDtlsEntity);
			//Save Data to Audit Table
			saveAuditDetails(registerRequest.getRequest());
			response = (MainResponseDTO<String>) DoctorAuthUtil.getMainResponseDto(registerRequest);
			try {
			boolean responseUsrCreate = false;
			MainResponseDTO<OtpResponseDTO> responseOfOtp= null;
			if(docRegEntity != null) {
				responseOfOtp = generateOTPDoctorUser(docRegEntity);
				if(responseOfOtp.isStatus() && responseOfOtp.getResponse().getDescription().equals(DoctorRegConstant.OTP_SUCESS_MSG.getMsg())) {
					responseUsrCreate = true;
				}
				else {
					//Call delete API to delete data stored in database if OTP generation api fails(transaction rollback)
					deleteuploadedDocuments(docRegEntity.getDrdUserId());
					deletexistsUserID(docRegEntity.getDrdUserId());
					return throwExceptionForOTPFailure(responseOfOtp.getErrors().get(0).getErrorCode(),responseOfOtp.getErrors().get(0).getMessage());
				}
			}
				if(responseUsrCreate) {
					response.setStatus(DoctorRegConstant.REGISTRATION_SUCCESS_STATUS.isStatus());
					response.setResponse(responseOfOtp.getResponse().getDescription());
				}else {
					return throwExceptionForOTPFailure(responseOfOtp.getErrors().get(0).getErrorCode(),responseOfOtp.getErrors().get(0).getMessage());
				}
			}
			 catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
		
	}	
		return response;
	}

	private MainResponseDTO<String> throwExceptionForOTPFailure(String errorCode, String message) {
		// TODO Auto-generated method stub
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(errorCode);
		exceptionJSONInfoDTO.setMessage(message);
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	@SuppressWarnings("rawtypes")
	private MainResponseDTO<String> saveDoctorProfilePhoto(DoctorRegDtlsDTO doctorRegDtlsDTO, DoctorRegDtlsEntity doctorrRegDtlsEntity) {
		// TODO Auto-generated method stub
		try{
			File byteStorePath = null;
			byteStorePath = new File(profilePhotoBytePath);
			String temppath;
			MainResponseDTO<String> mainResponse = new MainResponseDTO<>();
			logger.info("Loading the scribe profile photo to file system");
			if(doctorRegDtlsDTO.getDrProfilePhoto()!="")
			{
				if(doctorRegDtlsDTO.getDrProfilePhoto().getBytes().length<Long.parseLong(profilephotosize))
				{
					String[] strings = doctorRegDtlsDTO.getDrProfilePhoto().split(",");
					 String extension = "jpeg"; 
					 byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
					 createDirectory(AuthConstant.DoctorProfileDirectory);
						temppath = byteStorePath + File.separator + AuthConstant.DoctorProfileDirectory;
					     String path = temppath + File.separator + doctorRegDtlsDTO.getDrMobNo()+"_"+doctorRegDtlsDTO.getDrUserID()+"."+ extension;
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
				}
				else
				{
					return throwExceptionProfilePhotoSize();
				}
			}
		}catch(Exception e)
		{
			mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
			mainResponse.setResponse(DoctorRegConstant.PROFILE_PHOTO_INVALID.getMsg());
		}
		return mainResponse;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public MainResponseDTO<?> doctorProfile(MainRequestDTO<String> profileRequest) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		DoctorMstrDtlsDTO doctorMstrDtlsDTO = new DoctorMstrDtlsDTO();
		List<DoctorDocDtlsDTO> doctorDocDtlsDTOList = new ArrayList<DoctorDocDtlsDTO>();
		DoctorMstrDtlsEntity doctorMstrDtlsEntity = null;
		if(null != profileRequest.getRequest() && !profileRequest.getRequest().isEmpty()) {
			try {
				doctorMstrDtlsEntity = doctorMstrRepo.findByDmdUserId(profileRequest.getRequest().toUpperCase());
				if(null == doctorMstrDtlsEntity) {
					logger.error("Doctor UserId should not be null and empty or might not be present in DB: DmdUserId = "+profileRequest.getRequest());
					throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.NO_DATA_FOUND, DrRegErrorMessage.NO_DATA_FOUND));
				}
				BeanUtils.copyProperties(doctorMstrDtlsEntity, doctorMstrDtlsDTO);
			}catch(Exception e) {
				logger.error("Exception while getting doctor details :");
				e.printStackTrace();
				throw e;
			}
			
			try {
				List<DoctorDocsDtlEntity> doctordocsEntityList = doctorDocumentRepo.findDoctorDocuments(profileRequest.getRequest());
				if(null != doctordocsEntityList && doctordocsEntityList.size() > 0) {
					for (DoctorDocsDtlEntity doctorDocsDtlEntity : doctordocsEntityList) {
						DoctorDocDtlsDTO doctorDocDtlsDTO = new DoctorDocDtlsDTO();
						BeanUtils.copyProperties(doctorDocsDtlEntity, doctorDocDtlsDTO);
						doctorDocDtlsDTOList.add(doctorDocDtlsDTO);
					}
					doctorMstrDtlsDTO.setDrDocsDtls(doctorDocDtlsDTOList);
				}else {
					logger.info("Doctor documents not available.");
				}
			}catch(Exception e) {
				logger.error("Exception while getting doctor document details :");
				e.printStackTrace();
				throw e;
			}
			
			//fetch profile photo
			if(null != doctorMstrDtlsEntity.getProfilePhoto() && !doctorMstrDtlsEntity.getProfilePhoto().isEmpty()) {
				String profilePhoto = getProfilePhoto(doctorMstrDtlsEntity.getProfilePhoto());
				doctorMstrDtlsDTO.setProfilePhoto(null != profilePhoto ? profilePhoto : null);
			}
		}else {
			logger.error("Doctor UserId should not be null and empty or might not be present in DB : DmdUserId = "+profileRequest.getRequest());
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
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
		if(null != drDocDtlsRequest.getRequest() && !drDocDtlsRequest.getRequest().isEmpty()) {
			mainResponseDTO.setResponse(doctorDocumentRepo.findDoctorDocuments(drDocDtlsRequest.getRequest()));
		}else {
			logger.error("Doctor UserId should not be null and empty or might not be present in DB : drdUserId = "+drDocDtlsRequest.getRequest());
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
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
		if(null != profileUpdateRequest.getRequest().getDmdUserId() && !profileUpdateRequest.getRequest().getDmdUserId().isEmpty()) {
			doctorMstrDtlsEntity = doctorMstrRepo.findByDmdUserId(profileUpdateRequest.getRequest().getDmdUserId().toUpperCase());
		}else {
			logger.error("Doctor UserId should not be null and empty or might not be present in DB : dmdUserId = "+profileUpdateRequest.getRequest());
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		if(null != doctorMstrDtlsEntity.getDmdUserId()) {
			BeanUtils.copyProperties(doctorMstrDtlsEntity, updatedDoctorMstrDtlsEntity);
			updatedDoctorMstrDtlsEntity.setDmdDrName(profileUpdateRequest.getRequest().getDmdDrName().toUpperCase());
			updatedDoctorMstrDtlsEntity.setDmdMobileNo(profileUpdateRequest.getRequest().getDmdMobileNo());
			updatedDoctorMstrDtlsEntity.setDmdEmail(null != profileUpdateRequest.getRequest().getDmdEmail() && !profileUpdateRequest.getRequest().getDmdEmail().isEmpty() ? profileUpdateRequest.getRequest().getDmdEmail().toUpperCase() : "");//changed by girishk
			updatedDoctorMstrDtlsEntity.setDmdSmcNumber(profileUpdateRequest.getRequest().getDmdSmcNumber());
			updatedDoctorMstrDtlsEntity.setDmdMciNumber(profileUpdateRequest.getRequest().getDmdMciNumber());
			updatedDoctorMstrDtlsEntity.setDmdConsulFee(profileUpdateRequest.getRequest().getDmdConsulFee());
			updatedDoctorMstrDtlsEntity.setDmdSpecialiazation(profileUpdateRequest.getRequest().getDmdSpecialiazation().toUpperCase());
			updatedDoctorMstrDtlsEntity.setDmdGender(null !=profileUpdateRequest.getRequest().getDmdGender() && !profileUpdateRequest.getRequest().getDmdGender().isEmpty() 
														? profileUpdateRequest.getRequest().getDmdGender() : doctorMstrDtlsEntity.getDmdGender());
			updatedDoctorMstrDtlsEntity.setDmdAddress1(profileUpdateRequest.getRequest().getDmdAddress1().toUpperCase());
			if(profileUpdateRequest.getRequest().getDmdAddress2()!="")
			{
				updatedDoctorMstrDtlsEntity.setDmdAddress2(profileUpdateRequest.getRequest().getDmdAddress2().toUpperCase());	
			}
			if(profileUpdateRequest.getRequest().getDmdAddress3()!="")
			{
				updatedDoctorMstrDtlsEntity.setDmdAddress3(profileUpdateRequest.getRequest().getDmdAddress3().toUpperCase());
			}
			updatedDoctorMstrDtlsEntity.setDmdState(profileUpdateRequest.getRequest().getDmdState());
			updatedDoctorMstrDtlsEntity.setDmdCity(profileUpdateRequest.getRequest().getDmdCity());
			
			try {
				
				if(null != profileUpdateRequest.getRequest().getProfilePhoto() && !profileUpdateRequest.getRequest().getProfilePhoto().isEmpty()) {
					updateDoctorProfilePhoto(profileUpdateRequest.getRequest(), updatedDoctorMstrDtlsEntity);
				}
				//Update details to User details table 
				doctorMstrRepo.save(updatedDoctorMstrDtlsEntity);
				UpdateUserDetails(updatedDoctorMstrDtlsEntity);
			}catch(Exception e) {
				logger.error(" Exception while updating doctor profile : ");
				e.printStackTrace();
				throw e;
			}
			
		}else {
			logger.error("No record found for given input : dmdUserId = "+profileUpdateRequest.getRequest());
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.NO_DATA_FOUND,DrRegErrorMessage.NO_DATA_FOUND));
		}
		
		mainResponseDTO.setResponse("Profile Updated Successfully..");
		mainResponseDTO.setStatus(true);
		mainResponseDTO.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		logger.info(" returning response");
		return mainResponseDTO;
	}

	private MainResponseDTO<UserDetailsResponseDTO> UpdateUserDetails(DoctorMstrDtlsEntity updatedDoctorMstrDtlsEntity) {
		MainRequestDTO<UserDetailsDTO> mainRequest = new MainRequestDTO<>();
		//HttpHeaders requestHeaders = new HttpHeaders();
		//requestHeaders.set("Authorization", req.getHeader("Authorization"));
		UserDetailsDTO userDetailsDTO=new UserDetailsDTO();
		userDetailsDTO.setUserId(updatedDoctorMstrDtlsEntity.getDmdUserId());
		userDetailsDTO.setEmail(updatedDoctorMstrDtlsEntity.getDmdEmail());
		userDetailsDTO.setMciNumber(updatedDoctorMstrDtlsEntity.getDmdMciNumber());
		userDetailsDTO.setSmcNumber(updatedDoctorMstrDtlsEntity.getDmdSmcNumber());
		userDetailsDTO.setMobileNumber(updatedDoctorMstrDtlsEntity.getDmdMobileNo());
		userDetailsDTO.setUserFullName(updatedDoctorMstrDtlsEntity.getDmdDrName());
		mainRequest.setRequest(userDetailsDTO);
		HttpEntity<MainRequestDTO<UserDetailsDTO>> requestEntity = new HttpEntity<MainRequestDTO<UserDetailsDTO>>(mainRequest);
		ParameterizedTypeReference<MainResponseDTO<UserDetailsResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<UserDetailsResponseDTO>>() {
		};
		ResponseEntity<MainResponseDTO<UserDetailsResponseDTO>> response = restTemplate.exchange(updateUserDetailsURL, HttpMethod.POST, requestEntity, parameterizedResponse);
		return response.getBody();
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public MainResponseDTO<?> viewScribeList(MainRequestDTO<ScribeRegDtlsDTO> scribeListRequest) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		List<ScribeRegDtlsDTO> scribeRegDtlsDTOList = new ArrayList<ScribeRegDtlsDTO>();
		List<ScribeRegEntity> scribeRegDtlsEntities = new ArrayList<ScribeRegEntity>();
		boolean isActiveFlag = false;
		if(null != scribeListRequest.getRequest() && null != scribeListRequest.getRequest().getScrbdrUserIDfk() && !scribeListRequest.getRequest().getScrbdrUserIDfk().isEmpty()
				&& null != scribeListRequest.getRequest().getScrbisActive() && !scribeListRequest.getRequest().getScrbisActive().isEmpty() &&
				(scribeListRequest.getRequest().getScrbisActive().equalsIgnoreCase("Y") || scribeListRequest.getRequest().getScrbisActive().equalsIgnoreCase("N"))) {
			scribeRegDtlsEntities = scribeRegRepo.findScribeDetails(scribeListRequest.getRequest().getScrbdrUserIDfk().toLowerCase(),scribeListRequest.getRequest().getScrbisActive().toLowerCase());
		}else {
			logger.error("Request input fields should not be null and empty.");
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		if(null == scribeRegDtlsEntities || scribeRegDtlsEntities.size() <= 0) {
			logger.error("No record found for given inputs");
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.NO_DATA_FOUND,DrRegErrorMessage.NO_DATA_FOUND));
		}
		if(scribeListRequest.getRequest().getScrbisActive().equalsIgnoreCase("Y")) {
			isActiveFlag = true;
		}
		for(int i=0; i<scribeRegDtlsEntities.size();i++) {
			LoginUserEntity loginUserEntity = userDtlRepo.checkScribeIsActive(scribeRegDtlsEntities.get(i).getScrbUserID().toUpperCase(),isActiveFlag);
			if(null != loginUserEntity) {
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
		if(null != scribeActivationRequest.getRequest().getScrbUserID() && !scribeActivationRequest.getRequest().getScrbUserID().isEmpty()
			&&	null != scribeActivationRequest.getRequest().getScrbdrUserIDfk() && !scribeActivationRequest.getRequest().getScrbdrUserIDfk().isEmpty()
			&&	null != scribeActivationRequest.getRequest().getScrbisActive() && !scribeActivationRequest.getRequest().getScrbisActive().isEmpty()
			&& (scribeActivationRequest.getRequest().getScrbisActive().equalsIgnoreCase("Y") || scribeActivationRequest.getRequest().getScrbisActive().equalsIgnoreCase("N"))) 
		{
			try {
				scribeRegRepo.activeDeactiveScribe(scribeActivationRequest.getRequest().getScrbUserID().toLowerCase(), scribeActivationRequest.getRequest().getScrbdrUserIDfk().toLowerCase(), scribeActivationRequest.getRequest().getScrbisActive().toUpperCase());
				boolean isActiveFlag = scribeActivationRequest.getRequest().getScrbisActive().equalsIgnoreCase("Y") ? true : false;
				userDtlRepo.updateScribeIsActiveStatus(scribeActivationRequest.getRequest().getScrbUserID().toUpperCase(), isActiveFlag);
			}catch(Exception e) {
				e.printStackTrace();
				logger.error(" Exception while activate/deactivate scribe.",e);
				throw e;
			}
			
			//Inserted audited details
			try {
				ScribeRegEntity scribeRegEntity = scribeRegRepo.findScribe(scribeActivationRequest.getRequest().getScrbUserID().toLowerCase(),scribeActivationRequest.getRequest().getScrbdrUserIDfk().toLowerCase());
				BeanUtils.copyProperties(scribeRegEntity, auditScribeRegEntity);
				auditScribeRegEntity.setUserId(scribeRegEntity.getScrbCreadtedBy());
				scribeRegRepoAudited.save(auditScribeRegEntity);
			}catch(Exception e) {
				e.printStackTrace();
				logger.error(" Exception while saving audit information of scribe registration details.",e);
				throw e;
			}
		}else {
			logger.error("Request input fields should not be null or empty.");
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}
		
		mainResponseDTO.setResponse(scribeActivationRequest.getRequest().getScrbisActive().equalsIgnoreCase("Y") ? "Scribe activated" : "Scribe Deactivated");
		mainResponseDTO.setStatus(true);
		mainResponseDTO.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		logger.info(" returning response");
		return mainResponseDTO;
	}
	
	@Override
	public int deleteuploadedDocuments(String drd_user_id) {
		int status=1;
		try{
			List<String> path=documentrepo.findDocumentPath(drd_user_id);
			File file = null;
			if(path!=null)
			{
				for (String filepath : path) {
					file = new File(filepath);
					if (file.delete()) {
						System.out.println(" File deleted ");
						documentrepo.deleteByUserId(drd_user_id);
					}
				}
				
				
			}
			 //return  documentrepo.deleteByUserId(drd_user_id);
		}catch (Exception e) {
			// TODO: handle exception
			status=0;
		}
		return status;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public MainResponseDTO<String> saveuploadedDocuments(MainRequestDTO<DoctorRegDtlsDTO<DoctorDocsDtlsDTO>> registerRequest, DoctorRegDtlsEntity doctorrRegDtlsEntity) {
		MainResponseDTO<String> response = new MainResponseDTO<>();
		try{
			logger.info("Inside file save method");
			File byteStorePath = null;
			byteStorePath = new File(profilePhotoBytePath);
			String temppath;
			if(null!=registerRequest.getRequest().getDocuments())
			{
				for (int i = 0; i <registerRequest.getRequest().getDocuments().size(); i++) {
					logger.info("split the byte array request and conver with base 64 converter");
					if(registerRequest.getRequest().getDocuments().get(i).getFiles()!="")
					{
						if(registerRequest.getRequest().getDocuments().get(i).getFiles().length()<Long.parseLong(documentuploadsize))
						{
							String[] strings = registerRequest.getRequest().getDocuments().get(i).getFiles().split(",");
							 byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
							 createDirectory(AuthConstant.DoctorRegistrationTempDirectory);
							 if(doctorrRegDtlsEntity.getDrdDrName()==null)
							 {
								 logger.info("External request received for file upload");
								 doctorrRegDtlsEntity=registrationRepo.findDoctorDetailsByUserID(registerRequest.getRequest().getDrUserID());
							 }
							createDirectory(AuthConstant.DoctorRegistrationTempDirectory + File.separator + doctorrRegDtlsEntity.getDrdUserId());
							String[] text=strings[0].split(":");
							String[] givenname=text[1].split(";");
							String[] filename=givenname[0].split("/");
							logger.info("get path of file and save file to particular path");
								temppath = byteStorePath + File.separator + AuthConstant.DoctorRegistrationTempDirectory + File.separator + doctorrRegDtlsEntity.getDrdUserId();
								String path = temppath + File.separator +filename[0];
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
							    if(path!=null)
								{
							    DoctorDocsDtlEntity doctorDocsDtlEntity=new DoctorDocsDtlEntity();
								logger.info("set all fields to doctor docs table");
								doctorDocsDtlEntity.setDdtDocsName(filename[0]);
								doctorDocsDtlEntity.setDdtDocsPath(path);
								doctorDocsDtlEntity.setDdtDocsType(filename[1]);
								doctorDocsDtlEntity.setDdtCreatedBy(registerRequest.getRequest().getDrUserID());
								doctorDocsDtlEntity.setDdtModifiedBy(registerRequest.getRequest().getDrUserID());
								doctorDocsDtlEntity.setDdtDocsRemark("document");
								doctorDocsDtlEntity.setDoctorRegDtlsEntity(doctorrRegDtlsEntity);
								//Save details to Document Table
								documentrepo.save(doctorDocsDtlEntity);
								}
							    
							    logger.info("set staus success");
							    response = (MainResponseDTO<String>) DoctorAuthUtil.getMainResponseDto(registerRequest);
							    response.setResponse(DoctorRegConstant.SUCCESS_UPLOAD_MSG.getMsg());
							    response.setStatus(DoctorRegConstant.REGISTRATION_SUCCESS_STATUS.isStatus());
						}else
						{
							logger.info("error for file size exception,make object empty");
							response.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
							return throwExceptionFileSize();
						}
						
					}else
					{
						response.setResponse(DoctorRegConstant.EMPTY_FILES.getMsg());
					}
				}
			}
			
		}catch(Exception e)
		{
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
	 * Added by girishk to get doctor document details by docid.
	 */
	@Override
	public DoctorDocsDtlEntity getDoctorDocDetailsByID(Integer docId) {
		DoctorDocsDtlEntity doctorDocsDtlEntity =  doctorDocumentRepo.findByDdtDocIdPk(docId);
		if(null != doctorDocsDtlEntity) {
			return doctorDocsDtlEntity;
		}else {
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.NO_DATA_FOUND,DrRegErrorMessage.NO_DATA_FOUND));
		}
	}
	
	private MainResponseDTO<OtpResponseDTO> generateOTPDoctorUser(DoctorRegDtlsEntity docRegEntity) {
		MainRequestDTO<OtpRequestDTO> mainRequest = new MainRequestDTO<>();
		OtpRequestDTO otpRequestDTO = new OtpRequestDTO();
		otpRequestDTO.setEmailID(docRegEntity.getDrdEmail());
		otpRequestDTO.setMobileNo(docRegEntity.getDrdMobileNo());
		otpRequestDTO.setOtpFor(DoctorRegConstant.OTP_FOR.getValidate());
		otpRequestDTO.setOtpGenerateTpye(DoctorRegConstant.OTP_GENERATE_TYPE.getValidate());
		otpRequestDTO.setSendType(null != docRegEntity.getDrdEmail() && !docRegEntity.getDrdEmail().isEmpty() ? DoctorRegConstant.OTP_SEND_TYPE.getValidate() : "sms");
		otpRequestDTO.setUserId(docRegEntity.getDrdUserId());
		mainRequest.setRequest(otpRequestDTO);
		HttpEntity<MainRequestDTO<OtpRequestDTO>> requestEntity = new HttpEntity<MainRequestDTO<OtpRequestDTO>>(mainRequest);
		ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>>() {
		};
		ResponseEntity<MainResponseDTO<OtpResponseDTO>> response = restTemplate.exchange(generateOtpURL, HttpMethod.POST, requestEntity, parameterizedResponse);
		return response.getBody();
	}
	
	private MainResponseDTO<String> validateRequestFields(DoctorRegDtlsDTO doctorRegDtlsDTO) {
		if(doctorRegDtlsDTO.getDrFullName() != null && doctorRegDtlsDTO.getDrFullName().isEmpty())
			return throwExceptionForDocFullName();
		else if(doctorRegDtlsDTO.getDrMobNo() != null && !validate.validateMobileNo(Long.valueOf(doctorRegDtlsDTO.getDrMobNo())))
			return thrownExceptionForMobile();
		else if(null != doctorRegDtlsDTO.getDrEmail() && !doctorRegDtlsDTO.getDrEmail().isEmpty() && !validate.validateEmail(doctorRegDtlsDTO.getDrEmail()))
			return throwExceptionForEmail();
		else if(doctorRegDtlsDTO.getDrPassword() != null && !validate.validatePassword(doctorRegDtlsDTO.getDrPassword()))
			return throwExceptionForDoctorPassword();
		else if(doctorRegDtlsDTO.getDrUserID() != null && doctorRegDtlsDTO.getDrUserID().isEmpty())
			return throwExceptionForDoctorUserID();
		else if(doctorRegDtlsDTO.getDrSMCNo() != null && doctorRegDtlsDTO.getDrSMCNo().isEmpty())
			return throwExceptionForDoctorSmcNO();
		else if(doctorRegDtlsDTO.getDrMCINo() != null && doctorRegDtlsDTO.getDrMCINo().isEmpty())
			return throwExceptionForDoctorMCINo();
		else if(doctorRegDtlsDTO.getDrSpecilization() != null && doctorRegDtlsDTO.getDrSpecilization().isEmpty())
			return throwExceptionForSpecilization();
		else if(doctorRegDtlsDTO.getDrSMCNo() != null && ! validate.validateSMCNo(doctorRegDtlsDTO.getDrSMCNo()))
			return throwExceptionForSpecilizationFormat();
		else if(doctorRegDtlsDTO.getDrMCINo() != null && ! validate.validateMCINo(doctorRegDtlsDTO.getDrMCINo()))
			return throwExceptionForMCISpecilizationFormat();
		else if(doctorRegDtlsDTO.getDrFullName() !=null && ! validate.validateFullName(doctorRegDtlsDTO.getDrFullName()))
			return throwExceptionForDocFullName();
		else if(doctorRegDtlsDTO.getDrAddress1().isEmpty())
			return throwExceptionForDocAddress();
		else if(doctorRegDtlsDTO.getDrUserID()!=null && ! validate.validateUserID(doctorRegDtlsDTO.getDrUserID()))
			return throwExceptionForDocUserID();
		else if(doctorRegDtlsDTO.getDrSMCNo().equals(doctorRegDtlsDTO.getDrMCINo()))
		  return throwExceptionForSameNumbers();
		return null;
	}
	private MainResponseDTO<String> throwExceptionForSameNumbers()
	{
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.SMCMCI_ERROR.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.SMCMCI_ERROR.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}
	private MainResponseDTO<String> throwExceptionForDocUserID()
	{
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.USERID_ERROR.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.USERID_ERROR.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}
	private MainResponseDTO<String> throwExceptionForDocAddress()
	{
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.ADDRESS_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.ADDRESS_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}
	private MainResponseDTO<String> throwExceptionForSpecilizationFormat() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.SMC_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.SMC_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
		
	}
	private MainResponseDTO<String> throwExceptionForMCISpecilizationFormat() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.MCI_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.MCI_EXCEPTION_MSG.getMsg());
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
	
	private MainResponseDTO<String> throwExceptionForDocFullName() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.DOCTOR_FULLNAME_VALIDATE.getCode());
		exceptionJSONInfoDTO.setMessage(DoctorRegConstant.DOCTOR_FULLNAME_VALIDATE.getMsg());
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
	private MainResponseDTO<String> throwExceptionForAuditSave()
	{
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
		 if(registrationRepo.findByDocUserID(doctorRegDtlsDTO.getDrUserID().toUpperCase()).isPresent()){
			listOfExceptions = new ArrayList<>();
			exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
			exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.USER_ID_EXIST.getCode());
			exceptionJSONInfoDTO.setMessage(DoctorRegConstant.USER_ID_EXIST.getMsg());
			listOfExceptions.add(exceptionJSONInfoDTO);
			mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
			mainResponse.setErrors(listOfExceptions);
			return mainResponse;
		}else if(registrationRepo.findByDocMobNo(doctorRegDtlsDTO.getDrMobNo()).isPresent()) {
			listOfExceptions = new ArrayList<>();
			exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
			exceptionJSONInfoDTO.setErrorCode(DoctorRegConstant.MOBILE_EXIST.getCode());
			exceptionJSONInfoDTO.setMessage(DoctorRegConstant.MOBILE_EXIST.getMsg());
			listOfExceptions.add(exceptionJSONInfoDTO);
			mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
			mainResponse.setErrors(listOfExceptions);
			return mainResponse;
		}else if(null != doctorRegDtlsDTO.getDrEmail() && !doctorRegDtlsDTO.getDrEmail().isEmpty() && registrationRepo.findByDocEmail(doctorRegDtlsDTO.getDrEmail().toUpperCase()).isPresent())//changed by girishk
		{	listOfExceptions = new ArrayList<>();
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
	public  String getFilePathBasedOnOS() throws IOException {
	/*final String os = System.getProperty("os.name").toLowerCase();
		return ((os.contains("windows")) ? LoadPropertyValues.WindowsDocumentPath
				: LoadPropertyValues.LinuxDocumentPath);*/
		return windowdocpath;
	}
	public  void createDirectory(String folder_name) throws IOException {
		
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
		try
		{
			// TODO Auto-generated method stub
			DoctorAuditDtlsEntity auditDtls=new DoctorAuditDtlsEntity();
			auditDtls.setDrdDrName(doctorRegDtlsDTO.getDrFullName().toUpperCase());
			auditDtls.setDrdConsulFee(doctorRegDtlsDTO.getDrConsultFee());
			auditDtls.setDrdEmail(null != doctorRegDtlsDTO.getDrEmail() && !doctorRegDtlsDTO.getDrEmail().isEmpty() ? doctorRegDtlsDTO.getDrEmail().toUpperCase() : doctorRegDtlsDTO.getDrEmail());//changed by girishk
			auditDtls.setDrdMobileNo(doctorRegDtlsDTO.getDrMobNo());
			auditDtls.setDrdIsRegByIpan("y");
			auditDtls.setDrdIsverified("y");
			auditDtls.setDrdModifiedBy(doctorRegDtlsDTO.getDrFullName().toUpperCase());
			auditDtls.setDrdOptiVersion(Long.valueOf(1));
			auditDtls.setDrdPassword(doctorRegDtlsDTO.getDrPassword());
			auditDtls.setDrdUserId(doctorRegDtlsDTO.getDrUserID().toUpperCase());
			auditDtls.setDrdMciNumber(doctorRegDtlsDTO.getDrMCINo());
			auditDtls.setDrdSmcNumber(doctorRegDtlsDTO.getDrSMCNo());
			auditDtls.setDrdSpecialiazation(doctorRegDtlsDTO.getDrSpecilization().toUpperCase());
			auditDtls.setDrdOtpRefidFk(0);
			auditDtls.setUserId(doctorRegDtlsDTO.getDrUserID().toUpperCase());
			//auditDtls.setTimestamp(timestamp);
			doctorAuditRepo.save(auditDtls);
		}
		catch (Exception e) {
			// TODO: handle exception
			throwExceptionForAuditSave();
		}
		return mainResponse;
		
	}
	
	/**
	 * @param doctorMstrDtlsDTO
	 * @return
	 * 
	 * Added by girishk to update doctor profile photo.
	 */
	private MainResponseDTO<String> updateDoctorProfilePhoto(DoctorMstrDtlsDTO doctorMstrDtlsDTO, DoctorMstrDtlsEntity updatedDoctorMstrDtlsEntity) {
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
			String path = temppath + File.separator + updatedDoctorMstrDtlsEntity.getDmdMobileNo() + "_" + updatedDoctorMstrDtlsEntity.getDmdUserId() + "." + extension;
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

		}catch(Exception e)
		{
			logger.error("Exception while updating profile photo : "+ e);
			mainResponse.setStatus(DoctorRegConstant.REGISTRATION_FAIL_STATUS.isStatus());
		}
		return mainResponse;
	}
	
	/**
	 * @param profilePhotoPath
	 * @return
	 * 
	 * Added by girishk to get doctor profile photo.
	 */
	private String getProfilePhoto(String profilePhotoPath) {
		String finalResponse = null;
		try 
		{
			finalResponse = new String(Base64.encodeBase64(Files.readAllBytes(Paths.get(profilePhotoPath))));
			finalResponse =  "data:image/jpeg;base64," + finalResponse;
		} catch (Throwable e) {
			logger.error("Exception while getting doctor profile photo");
			e.printStackTrace();
		}
		System.out.println("Response " + finalResponse);
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
		int status=1;
		try{
				registrationRepo.deleteByUserId(drdUserId.toUpperCase());
			}catch (Exception e) {
			// TODO: handle exception
			status=0;
		}
		return status;
	}
	public MainResponseDTO<CaptchaResponseDto> verifyCaptcha(String captchaValue, String sessionId)
	{
			logger.info("Captcha Varification Api called");
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("sessionId", sessionId);
			requestHeaders.add("captchaValue", captchaValue);
			requestHeaders.add("flagValue", "true");
			HttpEntity<MainRequestDTO<CaptchaResponseDto>> requestEntity = new HttpEntity<MainRequestDTO<CaptchaResponseDto>>(
					requestHeaders);
			ParameterizedTypeReference<MainResponseDTO<CaptchaResponseDto>> parameterizedResponse = new 			ParameterizedTypeReference<MainResponseDTO<CaptchaResponseDto>>(){};
			ResponseEntity<MainResponseDTO<CaptchaResponseDto>> response = restTemplate.exchange(captchaVerificationURL,
					HttpMethod.POST, requestEntity, parameterizedResponse);
			logger.info("Captcha Api status : ");
			return response.getBody();
	}

	@Override
	public Long getCountOfDoctors() {
		Long count = 0L;
		try {
			count =  doctorMstrRepo.count();
		}catch(Exception e) {
			logger.error("Exception while getting count of doctors.");
	        e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MainResponseDTO<?> getScribeListByDoctorToActiveDeactive(MainRequestDTO<ScribeRegDtlsDTO> scribeListRequest) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		List<ScribeRegDtlsDTO> scribeRegDtlsDTOList = new ArrayList<ScribeRegDtlsDTO>();
		List<ScribeRegEntity> scribeRegDtlsEntities = new ArrayList<ScribeRegEntity>();
		if(null != scribeListRequest.getRequest() && null != scribeListRequest.getRequest().getScrbdrUserIDfk() && !scribeListRequest.getRequest().getScrbdrUserIDfk().isEmpty()) {
			scribeRegDtlsEntities = scribeRegRepo.getScribeListByDoctorToActiveDeactive(scribeListRequest.getRequest().getScrbdrUserIDfk().toUpperCase());
		}else {
			logger.error("Request input fields should not be null and empty.");
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT, DrRegErrorMessage.INVALID_USER_INPUT));
		}

		if(null == scribeRegDtlsEntities || scribeRegDtlsEntities.size() <= 0) {
			logger.error("No record found for given inputs");
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.NO_DATA_FOUND,DrRegErrorMessage.NO_DATA_FOUND));
		}
		
		for(int i=0; i<scribeRegDtlsEntities.size();i++) {
			ScribeRegDtlsDTO scribeRegDtlsDTO = new ScribeRegDtlsDTO();
			BeanUtils.copyProperties(scribeRegDtlsEntities.get(i), scribeRegDtlsDTO);
			//fetch profile photo
			if(null != scribeRegDtlsEntities.get(i).getProfilePhotoPath() && !scribeRegDtlsEntities.get(i).getProfilePhotoPath().isEmpty()) {
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
	public MainResponseDTO<?> changeDefaultScribe(MainRequestDTO<ScribeRegDtlsDTO> changeDefaultScribeRequest, String doctorUserId) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		if(null != changeDefaultScribeRequest.getRequest().getScrbUserID() && !changeDefaultScribeRequest.getRequest().getScrbUserID().isEmpty()) 
		{	
			try {
				scribeRegRepo.changeDefaultScribe(changeDefaultScribeRequest.getRequest().getScrbUserID().toUpperCase(), doctorUserId.toUpperCase());
			}catch(Exception e) {
				logger.error("Error while changing default scribe");
				e.printStackTrace();
				throw e;
			}
		}else {
			throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.INVALID_USER_INPUT,DrRegErrorMessage.INVALID_USER_INPUT));
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
	public MainResponseDTO<List<AppointmentDetailsResponseDTO>> searchCompletedAppointmentsForDoctor(MainRequestDTO<AppontmentDetailsRequestDTO> appointmentDetailsRequest) {
		List<AppointmentDtlsEntity> completedAppointmentEntities = new ArrayList<AppointmentDtlsEntity>();
		List<AppointmentDetailsResponseDTO> completedAppointmentDTOs = new ArrayList<AppointmentDetailsResponseDTO>();
		MainResponseDTO<List<AppointmentDetailsResponseDTO>> response = new MainResponseDTO<List<AppointmentDetailsResponseDTO>>();
		try {
			completedAppointmentEntities = appointmentDtlsRepository.findAll(new Specification<AppointmentDtlsEntity>() {
				@Override
				public Predicate toPredicate(Root<AppointmentDtlsEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<>();
					if ((null != appointmentDetailsRequest.getRequest().getFromDate() && null != appointmentDetailsRequest.getRequest().getToDate() &&
							!appointmentDetailsRequest.getRequest().getFromDate().isEmpty() && !appointmentDetailsRequest.getRequest().getToDate().isEmpty())
							|| (null != appointmentDetailsRequest.getRequest().getToDate() && !appointmentDetailsRequest.getRequest().getToDate().isEmpty())) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("adApptDateFk"), formatStringToDate(appointmentDetailsRequest.getRequest().getFromDate()))));
						predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("adApptDateFk"), formatStringToDate(appointmentDetailsRequest.getRequest().getToDate()))));
					}
					if (null != appointmentDetailsRequest.getRequest().getApptId() && !appointmentDetailsRequest.getRequest().getApptId().isEmpty()) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("adApptId"), appointmentDetailsRequest.getRequest().getApptId())));
					}
					if (null != appointmentDetailsRequest.getRequest().getPatientName() && !appointmentDetailsRequest.getRequest().getPatientName().isEmpty()) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("patientRegDtlsEntity").get("prdPtName")), "%" + appointmentDetailsRequest.getRequest().getPatientName().toUpperCase() + "%")));
					}
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("adApptStatus"), "C")));
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("docMstrDtlsEntity"), userDto.getUserName().toUpperCase())));
					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			});
			if(null !=completedAppointmentEntities && completedAppointmentEntities.size() > 0) {
				for (AppointmentDtlsEntity appointmentDtlsEntity : completedAppointmentEntities) {
					AppointmentDetailsResponseDTO appointmentDetailsResponseDTO = new AppointmentDetailsResponseDTO();
					appointmentDetailsResponseDTO.setAppointmentId(appointmentDtlsEntity.getAdApptId());
					appointmentDetailsResponseDTO.setAppointmentDate(new SimpleDateFormat("dd-MM-yyyy").format(appointmentDtlsEntity.getAdApptDateFk()));
					appointmentDetailsResponseDTO.setDoctorId(appointmentDtlsEntity.getDocMstrDtlsEntity());
					appointmentDetailsResponseDTO.setPatientId(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdUserId());
					appointmentDetailsResponseDTO.setPatientName(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdPtName());
					completedAppointmentDTOs.add(appointmentDetailsResponseDTO);
				}
			} 
			else {
				throw new DoctorRegistrationException(new ServiceErrors(DrRegErrorConstant.NO_APPOINTMENT_FOUND,DrRegErrorMessage.NO_APPOINTMENT_FOUND));
			}
		}catch(Exception e) {
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
			logger.error("Exception while parsing date.");
			e1.printStackTrace();
		}
		return finalDate;
	}
		
}
