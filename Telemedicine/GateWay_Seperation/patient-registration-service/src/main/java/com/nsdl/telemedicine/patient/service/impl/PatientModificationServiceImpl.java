
package com.nsdl.telemedicine.patient.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nsdl.telemedicine.patient.constant.AuthConstant;
import com.nsdl.telemedicine.patient.constant.AuthErrorConstant;
import com.nsdl.telemedicine.patient.dto.AllPatientDetailDto;
import com.nsdl.telemedicine.patient.dto.CreateUserRequestDTO;
import com.nsdl.telemedicine.patient.dto.CreateUserResponseDTO;
import com.nsdl.telemedicine.patient.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.patient.dto.LifeStyleDetailDto;
import com.nsdl.telemedicine.patient.dto.MainRequestDTO;
import com.nsdl.telemedicine.patient.dto.MainResponseDTO;
import com.nsdl.telemedicine.patient.dto.MedicalDetailDto;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.PersonalDetailDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.dto.UserDTO;
import com.nsdl.telemedicine.patient.entity.PatientLifestyleDetailsEntity;
import com.nsdl.telemedicine.patient.entity.PatientMedicalDetailsEntity;
import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.exception.DateParsingException;
import com.nsdl.telemedicine.patient.exception.PatientRegistrationException;
import com.nsdl.telemedicine.patient.loggers.PatientLoggingClientInfo;
import com.nsdl.telemedicine.patient.repository.PatientLifestyleDetailsRepository;
import com.nsdl.telemedicine.patient.repository.PatientMedicalDetailsRepository;
import com.nsdl.telemedicine.patient.repository.PatientPersonalDetailsRepository;
import com.nsdl.telemedicine.patient.service.AuditPatientService;
import com.nsdl.telemedicine.patient.service.PatientModificationService;
import com.nsdl.telemedicine.patient.utility.AuthUtil;
import com.nsdl.telemedicine.patient.utility.CommonValidationUtil;

@Service
@PatientLoggingClientInfo
public class PatientModificationServiceImpl implements PatientModificationService{

	private static final Logger LOGGER = LogManager.getLogger(PatientModificationServiceImpl.class);

	@Autowired 
	private PatientPersonalDetailsRepository patientRegistrationRepository;

	@Autowired 
	private PatientMedicalDetailsRepository patientMedicalDetailsRepository;

	@Autowired
	private PatientLifestyleDetailsRepository patientLifestyleDetailsRepository;

	@Autowired
	AuditPatientService auditPatientService;

	@Autowired
	private AuthUtil authUtil; 

	@Autowired
	private RestTemplate template;

	@Autowired
	private UserDTO userDTO;

	@Value("${UPDATE_USER_URL}")
	private String updateUserURL;

	@Autowired
	@Qualifier("patientCommonValidation") 
	CommonValidationUtil validate;

	@Override
	public ResponseWrapper<AllPatientDetailDto> getPatientAllDetails() {

		ResponseWrapper<AllPatientDetailDto> response = new ResponseWrapper<AllPatientDetailDto>();
		response.setId("registration");
		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		String userId = userDTO.getUserName().trim().toUpperCase(); 

		AllPatientDetailDto allPatientDetailDto = new AllPatientDetailDto();
		PatientPersonalDetailEntity personalDetail =null; 

		List<PatientMedicalDetailsEntity> medicalDetail = null;
		MedicalDetailDto medicalDetailDto = null;

		List<PatientLifestyleDetailsEntity> lifeStyleDetail = null;
		LifeStyleDetailDto lifeStyleDetailDto = null;

		PersonalDetailDto personalDetailResponseDto = null;
		try { 
			personalDetail =patientRegistrationRepository.findByPtUserID(userId);
			if(personalDetail!=null) {
				LOGGER.info("Get Patient Personal Details For: "+userId);
				personalDetailResponseDto = new PersonalDetailDto();
				personalDetailResponseDto.setPtFullName(personalDetail.getPtFullName());
				personalDetailResponseDto.setPtEmail(personalDetail.getPtEmail());
				personalDetailResponseDto.setPtMobNo(personalDetail.getPtMobNo());
				personalDetailResponseDto.setHeight(personalDetail.getHeight());
				personalDetailResponseDto.setWeight(personalDetail.getWeight());
				personalDetailResponseDto.setBloodgrp(personalDetail.getBloodGroup());
				personalDetailResponseDto.setDob(personalDetail.getDob());
				String address1 = personalDetail.getAddress1();
				String address2 = personalDetail.getAddress2();
				String address3 = personalDetail.getAddress3();
				personalDetailResponseDto.getAddress().setAddress1(address1);
				personalDetailResponseDto.getAddress().setAddress2(address2);
				personalDetailResponseDto.getAddress().setAddress3(address3);
				personalDetailResponseDto.setPtGender(personalDetail.getGender());
				personalDetailResponseDto.setPtProfilePhoto(personalDetail.getProfilePhotoPath());
				personalDetailResponseDto.setPtCity(personalDetail.getPtCity());
				personalDetailResponseDto.setPtState(personalDetail.getPtState());
				personalDetailResponseDto.setPtCountry(personalDetail.getPtCountry());
				allPatientDetailDto.setPtPersonalDtls(personalDetailResponseDto);
			}

		}catch (Exception e) {
			LOGGER.error("Exception occured while fetching Personal Details "+e.getMessage());
//			LOGGER.error(AuthConstant.INVALID_USER_ERROR+" For Personal Details "+userId);
			response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.INVALID_REQUEST,AuthConstant.INVALID_REQUEST));
		}
		try {
			medicalDetail =patientMedicalDetailsRepository.findByPtUserID(userId);
			if(medicalDetail.size()>0) {
				medicalDetailDto = new MedicalDetailDto();
				LOGGER.info("Get Patient Medical Details For: "+userId);
				for(PatientMedicalDetailsEntity medicalEtity : medicalDetail) {
					if(AuthConstant.ALLERGIES.equalsIgnoreCase(medicalEtity.getMedicalType())) {
						medicalDetailDto.setAllergies(medicalEtity.getMedicalTypeValue());
					}if(AuthConstant.CHRONICDISEASES.equalsIgnoreCase(medicalEtity.getMedicalType())) {
						medicalDetailDto.setChronicdiseases(medicalEtity.getMedicalTypeValue());
					}if(AuthConstant.CURRENTMEDICATION.equalsIgnoreCase(medicalEtity.getMedicalType())) {
						medicalDetailDto.setCurrentMedication(medicalEtity.getMedicalTypeValue());
					}if(AuthConstant.PASTMEDICATION.equalsIgnoreCase(medicalEtity.getMedicalType())) {
						medicalDetailDto.setPastMedication(medicalEtity.getMedicalTypeValue());
					}if(AuthConstant.INJURIES.equalsIgnoreCase(medicalEtity.getMedicalType())) {
						medicalDetailDto.setInjuries(medicalEtity.getMedicalTypeValue());
					}if(AuthConstant.SURGRIES.equalsIgnoreCase(medicalEtity.getMedicalType())) {
						medicalDetailDto.setSurgries(medicalEtity.getMedicalTypeValue());
					}
				}
				allPatientDetailDto.setPtMedicalDtls(medicalDetailDto);
			}
		}catch (Exception e) {
			LOGGER.error("Exception occured while fetching Medical Details "+e.getMessage());
//			LOGGER.error(AuthConstant.INVALID_USER_ERROR+" For Medical Details "+userId);
			response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.INVALID_REQUEST,AuthConstant.INVALID_REQUEST));
		}
		try {
			lifeStyleDetail =patientLifestyleDetailsRepository.findByPtUserID(userId);
			if(lifeStyleDetail.size()>0) {
				lifeStyleDetailDto = new LifeStyleDetailDto();
				LOGGER.info("Get Patient Lifestyle Details For: "+userId);
				for(PatientLifestyleDetailsEntity lifeStyleEtity : lifeStyleDetail) {
					if(AuthConstant.SMOKING.equalsIgnoreCase(lifeStyleEtity.getLifeStyleType())) {
						lifeStyleDetailDto.setSmoking(lifeStyleEtity.getLifeStyleTypeValue());
					}if(AuthConstant.ALCOHOL.equalsIgnoreCase(lifeStyleEtity.getLifeStyleType())) {
						lifeStyleDetailDto.setAlcohol(lifeStyleEtity.getLifeStyleTypeValue());
					}if(AuthConstant.ACTIVITYLVL.equalsIgnoreCase(lifeStyleEtity.getLifeStyleType())) {
						lifeStyleDetailDto.setActivityLvl(lifeStyleEtity.getLifeStyleTypeValue());
					}if(AuthConstant.FOODPREFERENCE.equalsIgnoreCase(lifeStyleEtity.getLifeStyleType())) {
						lifeStyleDetailDto.setFoodPreferance(lifeStyleEtity.getLifeStyleTypeValue());
					}if(AuthConstant.OCCUPATION.equalsIgnoreCase(lifeStyleEtity.getLifeStyleType())) {
						lifeStyleDetailDto.setOccupation(lifeStyleEtity.getLifeStyleTypeValue());
					}
				}
				allPatientDetailDto.setPtLifeStyleDtls(lifeStyleDetailDto);
			}
		}catch (Exception e) {
			LOGGER.error("Exception occured while fetching Lifestyle Details "+e.getMessage());
//			LOGGER.error(AuthConstant.INVALID_USER_ERROR+" For LifeStyle Details "+userId);
			response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.INVALID_REQUEST,AuthConstant.INVALID_REQUEST));
		}
		if(personalDetailResponseDto==null && medicalDetailDto==null && lifeStyleDetailDto==null) {
			LOGGER.info(AuthConstant.INVALID_USER_ERROR+": "+userId);
			response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.PATIENT_DETAILS_NOT_FOUND,AuthConstant.PATIENT_DETAILS_NOT_FOUND));
		}else {
			response.setStatus(true);
			response.setResponse(allPatientDetailDto);
			LOGGER.info(" Get All Detail For:"+userId);
		}
		return response; 
	}


	@SuppressWarnings("unchecked")
	@Transactional
	@Override 
	public ResponseWrapper<PatientResponseDto> updatePatientPersonalDetails(RequestWrapper<?> request) { 
		ResponseWrapper<PatientResponseDto> response = null;
		boolean scribeRegEntity ;
		PatientPersonalDetailEntity updateEntity = new PatientPersonalDetailEntity();
		response = (ResponseWrapper<PatientResponseDto>)AuthUtil.getMainResponseDto(request);
		PersonalDetailDto personalDetailDto = (PersonalDetailDto) request.getRequest();
		String userId = userDTO.getUserName().trim().toUpperCase();
//		validateRequest(personalDetailDto); //code commented by girishk
		PatientPersonalDetailEntity entity =  patientRegistrationRepository.findByPtUserID(userId);
		if (entity==null) {
			LOGGER.info("given userid Does Not Registered");
			throw new DateParsingException(
					new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_USER_ERR_CODE, AuthConstant.INVALID_USER_ERROR));
		}
		try { 
			if(!entity.getPtUserID().isEmpty()) {
				BeanUtils.copyProperties(entity, updateEntity);
				if(personalDetailDto.getPtEmail()!=null && !entity.getPtEmail().equalsIgnoreCase(personalDetailDto.getPtEmail())) {
					scribeRegEntity = patientRegistrationRepository.existsByptEmail(personalDetailDto.getPtEmail());
					if(!scribeRegEntity) {
						updateEntity.setPtEmail(personalDetailDto.getPtEmail());
					}else {
						throw new DateParsingException(
								new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_EMAIL_FORMAT_CODE, AuthConstant.EMAIL_ID_ALREADY_EXISTS));
					}
				}
				if(personalDetailDto.getPtMobNo()!=null && !entity.getPtMobNo().equals(personalDetailDto.getPtMobNo())){//condition changed by girishk
					PatientPersonalDetailEntity ptEntity = patientRegistrationRepository.existsByPtMobNo(personalDetailDto.getPtMobNo().toString());
					if(ptEntity==null) {
						updateEntity.setPtMobNo(personalDetailDto.getPtMobNo());
					}else {
						throw new DateParsingException(
								new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_MOBILE_FORMAT_CODE, AuthConstant.MOBILE_NUMBER_ALREADY_EXISTS));
					}
				}
				updateEntity.setPtFullName(personalDetailDto.getPtFullName()==null?updateEntity.getPtFullName():personalDetailDto.getPtFullName());
				updateEntity.setPtEmail(personalDetailDto.getPtEmail()==null? updateEntity.getPtEmail():personalDetailDto.getPtEmail());
				updateEntity.setPtMobNo(personalDetailDto.getPtMobNo()==null? updateEntity.getPtMobNo():personalDetailDto.getPtMobNo());
				updateEntity.setHeight(personalDetailDto.getHeight()==null? updateEntity.getHeight():personalDetailDto.getHeight());
				updateEntity.setWeight(personalDetailDto.getWeight()==null ? updateEntity.getWeight():personalDetailDto.getWeight());
				updateEntity.setBloodGroup(personalDetailDto.getBloodgrp() == null ? updateEntity.getBloodGroup():personalDetailDto.getBloodgrp());
				updateEntity.setDob(personalDetailDto.getDob()==null ? updateEntity.getDob() : personalDetailDto.getDob());
				updateEntity.setAddress1(personalDetailDto.getAddress().getAddress1()==null ? updateEntity.getAddress1() :personalDetailDto.getAddress().getAddress1());
				updateEntity.setAddress2(personalDetailDto.getAddress().getAddress2()==null ? updateEntity.getAddress2():personalDetailDto.getAddress().getAddress2());
				updateEntity.setAddress3(personalDetailDto.getAddress().getAddress3()==null ? updateEntity.getAddress3():personalDetailDto.getAddress().getAddress3());
				updateEntity.setModificationDate(LocalDateTime.now());

				updateEntity.setGender(personalDetailDto.getPtGender()==null ?updateEntity.getGender():personalDetailDto.getPtGender());
				updateEntity.setPtCity(personalDetailDto.getPtCity()==null ? updateEntity.getPtCity() :personalDetailDto.getPtCity());
				updateEntity.setPtState(personalDetailDto.getPtState()==null ? updateEntity.getPtState():personalDetailDto.getPtState());
				updateEntity.setPtCountry(personalDetailDto.getPtCountry()==null ? updateEntity.getPtCountry() : personalDetailDto.getPtCountry());
				if(personalDetailDto.getPtProfilePhoto()!=null) {
					PatientResponseDto profilePhotoPath = authUtil.savePatientProfilePhoto(personalDetailDto.getPtProfilePhoto() ,updateEntity.getPtMobNo() ,userId);
					updateEntity.setProfilePhotoPath(profilePhotoPath.getMessage());
				}else {
					updateEntity.setProfilePhotoPath(updateEntity.getProfilePhotoPath());
				}
				try {
					updateEntity = patientRegistrationRepository.save(updateEntity);
					if(updateEntity!=null) {
						if(updateUserForPatient(personalDetailDto , userId)) {
							response.setStatus(true);
							PatientResponseDto responseDto = new PatientResponseDto();
							responseDto.setMessage(AuthConstant.REG_UPDATE_SUCEESS);
							response.setResponse(responseDto); 
							LOGGER.info("Update Patient Personal Details For: "+userId);
						}else {
							LOGGER.info(AuthConstant.UPDATE_FAILED+" For Personal Details");
							response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.UPDATE_FAILED,AuthConstant.UPDATE_FAILED+" For Personal Details"));
						}
					}
				}catch(Exception e) {
					LOGGER.error(" Exception while updating Patient profile : ");
					e.printStackTrace();
					throw e;
				}
			}else {
				LOGGER.info(AuthConstant.UPDATE_FAILED+" For Personal Details");
				response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.UPDATE_FAILED,AuthConstant.UPDATE_FAILED+" For Personal Details"));
			}
			auditPatientService.auditRegistrationServicePersonalDetail(updateEntity, userId);

		} catch (PatientRegistrationException e)
		{
			LOGGER.error(AuthConstant.UPDATE_FAILED);
			throw new PatientRegistrationException(AuthErrorConstant.UPDATE_FAILED ,AuthConstant.UPDATE_FAILED);
		} 
		return response; 
	}

	private void validateRequest(PersonalDetailDto personalDetailDto) {
		PatientPersonalDetailEntity entity = new PatientPersonalDetailEntity();
		if(personalDetailDto.getPtEmail()!=null) {
			if (patientRegistrationRepository.existsByptEmail(personalDetailDto.getPtEmail())) {
				LOGGER.info("given Email already exists");
				throw new DateParsingException(
						new ExceptionJSONInfoDTO(AuthErrorConstant.USERID_ALREADY_EXISTS_CODE, AuthConstant.EMAIL_ID_ALREADY_EXISTS));
			}
		}
		if(personalDetailDto.getPtMobNo()!=null) {
			entity = patientRegistrationRepository.existsByPtMobNo(String.valueOf(personalDetailDto.getPtMobNo()));
			if (entity!=null) {
				LOGGER.info("given Mobile-Number already exists");
				throw new DateParsingException(
						new ExceptionJSONInfoDTO(AuthErrorConstant.USERID_ALREADY_EXISTS_CODE, AuthConstant.MOBILE_NUMBER_ALREADY_EXISTS));
			}
		}
	}


	private boolean updateUserForPatient(PersonalDetailDto persistEntity,String userId) {
		try {
			LOGGER.info("USER MANAGEMENT API Called");
			MainRequestDTO<CreateUserRequestDTO> mainRequestDTO = new MainRequestDTO<>();
			CreateUserRequestDTO createUser = new CreateUserRequestDTO();
			createUser.setUserId(userId);
			if(persistEntity.getPtFullName()!=null) {
				createUser.setUserFullName(persistEntity.getPtFullName());
			}
			if(persistEntity.getPtEmail()!=null) {
				createUser.setEmail(persistEntity.getPtEmail());
			}
			if(persistEntity.getPtMobNo()!=null) {
				createUser.setMobileNumber(persistEntity.getPtMobNo());
			}
			mainRequestDTO.setRequest(createUser);
			LOGGER.info("USER MANAGEMENT API Called with userID "+createUser.getUserId());
			HttpEntity<MainRequestDTO<CreateUserRequestDTO>> requestEntity = new HttpEntity<>(mainRequestDTO);
			System.out.println("user management request "+requestEntity.toString());
			ParameterizedTypeReference<MainResponseDTO<CreateUserResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<CreateUserResponseDTO>>() {
			};
			ResponseEntity<MainResponseDTO<CreateUserResponseDTO>> response = template.exchange(updateUserURL,
					HttpMethod.POST, requestEntity, parameterizedResponse);

			if(!response.getBody().isStatus()) {
				LOGGER.info("USER MANAGEMENT API Status : "+response.getBody().isStatus()+" due to :"+response.getBody().getErrors().get(0).getMessage());
			}
			return response.getBody().isStatus();
		}catch (Exception e) {
			LOGGER.error("USER MANAGEMENT API FAILED");
			e.printStackTrace();
			throw new DateParsingException( new ExceptionJSONInfoDTO(AuthErrorConstant.USER_MANAGEMENT_API_FAIL,AuthConstant.USER_MANAGEMENT_API_FAIL)); 
		}
	}



	@SuppressWarnings("unchecked")
	@Override 
	public ResponseWrapper<PatientResponseDto> updatePatientMedicalDetails(RequestWrapper<?> request)
	{ 
		ResponseWrapper<PatientResponseDto> response = null;
		response = (ResponseWrapper<PatientResponseDto>)AuthUtil.getMainResponseDto(request);
		MedicalDetailDto medicalDetailDto = (MedicalDetailDto) request.getRequest();
		String userId = userDTO.getUserName().trim().toUpperCase();
		if (!patientRegistrationRepository.existsByPtUserID(userId)) {
			LOGGER.info("given userid Does Not Registered");
			throw new DateParsingException(
					new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_USER_ERR_CODE, AuthConstant.INVALID_USER_ERROR));
		}
		else{
			try {
				List<PatientMedicalDetailsEntity> list = patientMedicalDetailsRepository.findByPtUserID(userId);
				for(PatientMedicalDetailsEntity medicalEntity:list) {
					auditPatientService.auditRegistrationServiceMedicalDetail(medicalEntity, userId);
				}
				patientMedicalDetailsRepository.deleteAllByPtUserID(userId);
			}catch (Exception e) {
				LOGGER.error("Exception Occured while deleting patient previous Medical Details : ");
				e.printStackTrace();
				response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.UPDATE_FAILED,AuthConstant.DELETE_FAILED+" For Medical Details"));
			}
		}
		PatientMedicalDetailsEntity entity = null; 
		Map<String, String> ptMedicalDtls = new HashMap<>();
		ptMedicalDtls.put(AuthConstant.ALLERGIES,medicalDetailDto.getAllergies());
		ptMedicalDtls.put(AuthConstant.CHRONICDISEASES,medicalDetailDto.getChronicdiseases());
		ptMedicalDtls.put(AuthConstant.CURRENTMEDICATION, medicalDetailDto.getCurrentMedication());
		ptMedicalDtls.put(AuthConstant.PASTMEDICATION,medicalDetailDto.getPastMedication());
		ptMedicalDtls.put(AuthConstant.INJURIES,medicalDetailDto.getInjuries());
		ptMedicalDtls.put(AuthConstant.SURGRIES, medicalDetailDto.getSurgries()); 
		for(Map.Entry<String,String> entry:ptMedicalDtls.entrySet()) { 
			entity = new PatientMedicalDetailsEntity();
			entity.setPtUserID(userId); 
			entity.setOptiVersion(1l);
			entity.setMedicalType(entry.getKey());
			entity.setMedicalTypeValue(entry.getValue());
			try { 
				auditPatientService.auditRegistrationServiceMedicalDetail(entity, userId);
				entity = patientMedicalDetailsRepository.save(entity); 
				LOGGER.info("Update Patient Medical Details For: "+userId +" with "+entry.getKey()+": "+entry.getValue());
			}
			catch (Exception e)
			{ 
				LOGGER.error("Exception occured while updating patient medical details.");
				e.printStackTrace();
				throw e;
//				throw new PatientRegistrationException(AuthErrorConstant.UPDATE_FAILED,AuthConstant.UPDATE_FAILED);
			} 
		} 
		PatientResponseDto responseDto = new PatientResponseDto();
		response.setStatus(true);
		responseDto.setMessage(AuthConstant.REG_UPDATE_SUCEESS);
		response.setResponse(responseDto);
		LOGGER.info(AuthConstant.REG_UPDATE_SUCEESS);
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override 
	public ResponseWrapper<PatientResponseDto> updatePatientLifeStyleDetails(RequestWrapper<?> request) {

		ResponseWrapper<PatientResponseDto> response = null;
		response = (ResponseWrapper<PatientResponseDto>)AuthUtil.getMainResponseDto(request);
		LifeStyleDetailDto lifeStyleDetailDto = (LifeStyleDetailDto) request.getRequest();
		String userId = userDTO.getUserName().trim().toUpperCase();
		if (!patientRegistrationRepository.existsByPtUserID(userId)) {
			LOGGER.info(" Given UserId Does Not Registered ");
			throw new DateParsingException(
					new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_USER_ERR_CODE, AuthConstant.INVALID_USER_ERROR));
		}
		else{
			try {
				List<PatientLifestyleDetailsEntity> list = patientLifestyleDetailsRepository.findByPtUserID(userId);
				for(PatientLifestyleDetailsEntity lifeStyleEntity : list) {
					auditPatientService.auditRegistrationServiceLifeStyleDetail(lifeStyleEntity, userId);
				}
				patientLifestyleDetailsRepository.deleteAllByPtUserID(userId);
			}catch (Exception e) {
				LOGGER.error("Exception Occured while deleting patient previous Lifestyle Details : ");
				e.printStackTrace();
				response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.UPDATE_FAILED,AuthConstant.DELETE_FAILED+" For LifeStyle Details"));
			}
		}
		PatientLifestyleDetailsEntity entity;
		Map<String, String> ptLIfeStyleDtls = new HashMap<>();
		ptLIfeStyleDtls.put(AuthConstant.SMOKING.toString(),lifeStyleDetailDto.getSmoking());
		ptLIfeStyleDtls.put(AuthConstant.ALCOHOL.toString().toString(),lifeStyleDetailDto.getAlcohol());
		ptLIfeStyleDtls.put(AuthConstant.ACTIVITYLVL.toString(),lifeStyleDetailDto.getActivityLvl());
		ptLIfeStyleDtls.put(AuthConstant.FOODPREFERENCE.toString(),lifeStyleDetailDto.getFoodPreferance());
		ptLIfeStyleDtls.put(AuthConstant.OCCUPATION.toString(),lifeStyleDetailDto.getOccupation());
		for(Map.Entry<String,String> entry:ptLIfeStyleDtls.entrySet())
		{ 
			entity = new PatientLifestyleDetailsEntity();
			entity.setPtUserID(userId); 
			entity.setOptiVersion(1l);
			entity.setLifeStyleType(entry.getKey());
			entity.setLifeStyleTypeValue(entry.getValue());
			try { 
				auditPatientService.auditRegistrationServiceLifeStyleDetail(entity, userId);
				entity = patientLifestyleDetailsRepository.save(entity);
				LOGGER.info("Update Patient LifeStyle Details For: "+userId +" with "+entry.getKey()+": "+entry.getValue());
			}
			catch (Exception e)
			{ 	
				LOGGER.error("Exception occured while updating patient lifestyle details.");
				e.printStackTrace();
				throw e;
			} 
		} 
		PatientResponseDto responseDto = new PatientResponseDto();
		response.setStatus(true);
		responseDto.setMessage(AuthConstant.REG_UPDATE_SUCEESS);
		response.setResponse(responseDto);
		LOGGER.info(AuthConstant.REG_UPDATE_SUCEESS);
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override 
	public ResponseWrapper<PatientResponseDto> updateAllPatientDetails(RequestWrapper<AllPatientDetailDto> request) { 
		ResponseWrapper<PatientResponseDto> response = null;
		response = (ResponseWrapper<PatientResponseDto>)AuthUtil.getMainResponseDto(request);
		AllPatientDetailDto allPatientDetailDto = request.getRequest();
		RequestWrapper<Object> requestWrapper = new RequestWrapper<>();

		// Update personal Details 
		try {
			requestWrapper.setRequest(allPatientDetailDto.getPtPersonalDtls());
			updatePatientPersonalDetails(requestWrapper);
			LOGGER.info(" Update personal detail");
		}catch(DateParsingException d) {
			throw d;
		}catch (Exception e) {
			response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.UPDATE_FAILED,AuthConstant.UPDATE_FAILED+" For Personal Details"));
		}
		// Update Medical Details 
		try {
			requestWrapper.setRequest(allPatientDetailDto.getPtMedicalDtls());
			updatePatientMedicalDetails(requestWrapper);
			LOGGER.info(" Update medical detail");
		}catch(DateParsingException d) {
			throw d;
		}catch (Exception e) {
			response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.UPDATE_FAILED,AuthConstant.UPDATE_FAILED+" For Medical Details"));
		}
		// Update LifeStyle Details 
		try {
			requestWrapper.setRequest(allPatientDetailDto.getPtLifeStyleDtls());
			updatePatientLifeStyleDetails(requestWrapper); 
			LOGGER.info(" Update life-Style detail");
		}catch(DateParsingException d) {
			throw d;
		}catch (Exception e) {
			response.setErrors(AuthUtil.getExceptionList(null,AuthErrorConstant.UPDATE_FAILED,AuthConstant.UPDATE_FAILED+" For LifeStyle Details"));
		}
		PatientResponseDto responseDto = new PatientResponseDto();
		response.setStatus(true);
		responseDto.setMessage(AuthConstant.REG_UPDATE_SUCEESS);
		response.setResponse(responseDto);
		LOGGER.info(AuthConstant.REG_UPDATE_SUCEESS);
		return response;
	}
}
