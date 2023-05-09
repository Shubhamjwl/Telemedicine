package com.nsdl.telemedicine.patient.service.impl;

import com.nsdl.telemedicine.patient.constant.AuthConstant;
import com.nsdl.telemedicine.patient.constant.AuthErrorConstant;
import com.nsdl.telemedicine.patient.dto.*;
import com.nsdl.telemedicine.patient.entity.*;
import com.nsdl.telemedicine.patient.exception.DateParsingException;
import com.nsdl.telemedicine.patient.repository.*;
import com.nsdl.telemedicine.patient.service.AuditPatientService;
import com.nsdl.telemedicine.patient.service.PatientVerificationService;
import com.nsdl.telemedicine.patient.utility.AuthUtil;
import com.nsdl.telemedicine.patient.utility.DateUtils;
import com.nsdl.telemedicine.patient.utility.RandomStringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PatientVerificationServiceImpl implements PatientVerificationService {

	private static final Logger LOGGER = LogManager.getLogger(PatientVerificationServiceImpl.class);

	@Autowired
	private PatientPersonalDetailsRepository personalDetailsRepository;

	@Autowired
	private AuthUtil authUtil;

	@Autowired
	private UserDTO userDto;

	@Autowired
	private PatientRegistrationByScribeRepository patientRegistrationByScribeRepository;

	@Autowired
	private ScribeRegRepo scribeRegRepo;

	@Autowired
	private UserLoginRepositiory userLoginRepositiory;

	@Autowired
	private AuditPatientService auditPatientService;

	@Autowired
	private NDHMIntegrationRepo ndhmRepo;

	@Autowired
	private RestTemplate template;

	@Value("${CREATE_USER_URL}")
	private String createUserURL;

	@Value("${email.notification.url}")
	String emailUrl;

	private static final String MATCH_PATTERN = "^\\d{1,13}$";

	@SuppressWarnings("unchecked")
	@Override
	public ResponseWrapper<SearchPatientResponseDTO> searchPatientByHealthId(
			@Valid RequestWrapper<SearchPatientRequestDTO> searchPatientRequestDTO) {
		SearchPatientResponseDTO searchPatientResponseDTO = new SearchPatientResponseDTO();
		ResponseWrapper<SearchPatientResponseDTO> response = null;
		response = (ResponseWrapper<SearchPatientResponseDTO>) AuthUtil.getMainResponseDto(searchPatientRequestDTO);

		PatientPersonalDetailEntity personalentity = personalDetailsRepository
				.getPatientDtlsById(searchPatientRequestDTO.getRequest().getHealthId());
		if (personalentity != null) {
			searchPatientResponseDTO.setUserId(personalentity.getPtUserID());
			searchPatientResponseDTO.setMobileNo(personalentity.getPtMobNo().toString());
			searchPatientResponseDTO.setName(personalentity.getPtFullName());
			searchPatientResponseDTO.setAbhaAddress(personalentity.getHealthID());
			searchPatientResponseDTO.setAbhaNo(personalentity.getHealthNumber());
			searchPatientResponseDTO.setGender(personalentity.getGender());
			searchPatientResponseDTO.setEmail(personalentity.getPtEmail());
			searchPatientResponseDTO.setAddress(personalentity.getAddress1());
			response.setResponse(searchPatientResponseDTO);
			response.setStatus(true);
		} else {
			response.setStatus(false);
		}

		return response;
	}

	/**
	 * added by jinesh for save registration details with health id details
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseWrapper<PatientResponseDto> saveRegistrationDetailsWithHealthId(
			@Valid RequestWrapper<PatientDetailsWithHealthIdDTO> patientDetailsDTO) {

		ResponseWrapper<PatientResponseDto> response = null;
		response = (ResponseWrapper<PatientResponseDto>) AuthUtil.getMainResponseDto(patientDetailsDTO);
		PatientPersonalDetailEntity entitydtls = null;
		entitydtls = personalDetailsRepository
				.existsByPatientMobNo(String.valueOf(patientDetailsDTO.getRequest().getPtMobileNo()));
		if (entitydtls != null) {

			String ptUserID = "P" + patientDetailsDTO.getRequest().getPtMobileNo();
			String ptStatus = "Y";
			DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity = patientRegistrationByScribeRepository
					.findByDpmdDrUserIdFkAndDpmdPtUserIdFkAndDpmdStatus(
							patientDetailsDTO.getRequest().getDoctorUserID(), ptUserID, ptStatus);
			if (null != doctorPatientMapDtlsEntity) {
				LOGGER.info("Patient is alreday register with same doctor.");

				PatientResponseDto patientResponseDto = new PatientResponseDto();

				if (entitydtls.getHealthID() == null || entitydtls.getHealthID() == ""
						|| entitydtls.getHealthNumber() == null || entitydtls.getHealthNumber() == "") {
					if (null != patientDetailsDTO.getRequest().getHealthId()
							&& !"".equals(patientDetailsDTO.getRequest().getHealthId()))

						entitydtls.setHealthID(patientDetailsDTO.getRequest().getHealthId());
					if (null != patientDetailsDTO.getRequest().getHealthNo()
							&& !"".equals(patientDetailsDTO.getRequest().getHealthNo()))
						entitydtls.setHealthNumber(patientDetailsDTO.getRequest().getHealthNo());

					PatientPersonalDetailEntity savedPatientDetails = personalDetailsRepository.save(entitydtls);
					if (savedPatientDetails != null) {
						patientResponseDto.setMessage(
								"Duplicate records found. Patient is already registered with you in Protean Clinic and ABHA address of the patient is updated.");
					}

				} else {
					patientResponseDto.setMessage(
							"Duplicate records found. Patient is already registered with you in Protean Clinic");
				}

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

					if (entitydtls.getHealthID() != null && entitydtls.getHealthID() != ""
							|| entitydtls.getHealthNumber() != null && entitydtls.getHealthNumber() != "") {
						PatientResponseDto patientResponseDto = new PatientResponseDto();
						patientResponseDto.setMessage(
								"The patient is successfully registered with you using the information already available in Protean Clinic.");
						response.setResponse(patientResponseDto);
						response.setStatus(true);
						LOGGER.info("API success, insertion in all tables successfull");
						return response;
					} else {
						if (null != patientDetailsDTO.getRequest().getHealthId()
								&& !"".equals(patientDetailsDTO.getRequest().getHealthId()))

							entitydtls.setHealthID(patientDetailsDTO.getRequest().getHealthId());
						if (null != patientDetailsDTO.getRequest().getHealthNo()
								&& !"".equals(patientDetailsDTO.getRequest().getHealthNo()))
							entitydtls.setHealthNumber(patientDetailsDTO.getRequest().getHealthNo());

						personalDetailsRepository.save(entitydtls);

						PatientResponseDto patientResponseDto = new PatientResponseDto();
						patientResponseDto.setMessage(
								"The patient is successfully registered with you using the information already available in Protean Clinic. Patient's ABHA details are also updated.");
						response.setResponse(patientResponseDto);
						response.setStatus(true);
						LOGGER.info("API success, insertion in all tables successfull");
						return response;
					}

				} else {
					response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
							AuthConstant.PATIENT_REG_ERROR));
				}
			}

		}
		String newRandomPassword = RandomStringUtil.randomAlphaNumeric(10, "LGN-PWD");
		LOGGER.info("new random password" + newRandomPassword);
		PatientDetailsWithHealthIdDTO request = patientDetailsDTO.getRequest();
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
		if (null != request.getHealthId())
			entity.setHealthID(request.getHealthId());
		if (null != request.getHealthNo())
			entity.setHealthNumber(request.getHealthNo());
		if (null != request.getPtEmailID()) {
			entity.setPtEmail(request.getPtEmailID().toUpperCase());
		}
		if (null != request.getAccessToken()) {
			entity.setNdhmToken(request.getAccessToken());
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
				savedEntity = personalDetailsRepository.save(entity);
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
				System.out.println("doctorPatientMapDtlsEntity " + doctorPatientMapDtlsEntity);
				DoctorPatientMapDtlsEntity doctorPatientMapDtlsEntity2 = null;
				doctorPatientMapDtlsEntity2 = patientRegistrationByScribeRepository.save(doctorPatientMapDtlsEntity);
				if (doctorPatientMapDtlsEntity2 != null) {
					PatientResponseDto patientResponseDto = new PatientResponseDto();
					patientResponseDto.setMessage("Patient has registered successfully.");
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
						patientResponseDto.setMessage("Patient has registered successfully.");
						response.setResponse(patientResponseDto);
						response.setStatus(true);
					} else {
						LOGGER.info("Random Password Email sent successfully");
						patientResponseDto.setMessage(
								"Something went wrong while sending email,Patient registration by doctor is successful, you can login to Protean Clinic");
						response.setResponse(patientResponseDto);
						response.setStatus(true);
					}

				} else {
					// call to rollback transaction
					personalDetailsRepository.deleteByUserId(savedEntity.getPtUserID());
					userLoginRepositiory.deleteByUserId(savedEntity.getPtUserID());
					LOGGER.error("issue while saving to mapping table");
					response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
							AuthConstant.PATIENT_REG_ERROR));
				}
			} else {
				// call to rollback transaction
				personalDetailsRepository.deleteByUserId(savedEntity.getPtUserID());
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
			createUser.setUserFullName(persistEntity.getPtFullName().trim());
			if (null != persistEntity.getPtEmail()) 
				createUser.setEmail(persistEntity.getPtEmail());
			  
			createUser.setMobileNumber(persistEntity.getPtMobNo());
			createUser.setPassword(persistEntity.getPtPassword());
			// createUser.setRoleName(PatientRegConstant.PATIENT);
			createUser.setUserId("P" + persistEntity.getPtMobNo());
			createUser.setUserType(AuthConstant.PATIENT);
			createUser.setStatusFlag("patientByScribe");
			mainRequestDTO.setRequest(createUser);
			LOGGER.info("USER MANAGEMENT API Called with userID " + createUser.getUserId() + " And UserName "
					+ createUser.getUserFullName());
			HttpEntity<MainRequestDTO<CreateUserRequestDTO>> requestEntity = new HttpEntity<>(mainRequestDTO);
			ParameterizedTypeReference<MainResponseDTO<CreateUserResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<CreateUserResponseDTO>>() {
			};
			System.out.println("requestEntity "+ requestEntity);
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
	@SuppressWarnings("unchecked")
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

	/**
	 * added by jinesh for update abha details
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseWrapper<PatientResponseDto> updateAbhaDetails(
			@Valid RequestWrapper<UpdateAbhaDetailsDTO> updateAbhaDetailsDTO) {
		ResponseWrapper<PatientResponseDto> response = null;
		response = (ResponseWrapper<PatientResponseDto>) AuthUtil.getMainResponseDto(updateAbhaDetailsDTO);
		NDHMDeatilsEntity ndhmentity = new NDHMDeatilsEntity();
		PatientPersonalDetailEntity personalentity = personalDetailsRepository
				.existsByPtMobNo(updateAbhaDetailsDTO.getRequest().getMobileNo());

		if (personalentity != null) {
			personalentity.setHealthID(updateAbhaDetailsDTO.getRequest().getHealthId());
			if (null != updateAbhaDetailsDTO.getRequest().getHealthNumber()
					&& !"".equals(updateAbhaDetailsDTO.getRequest().getHealthNumber()))
				personalentity.setHealthNumber(updateAbhaDetailsDTO.getRequest().getHealthNumber());
			if (null != updateAbhaDetailsDTO.getRequest().getToken()
					&& !"".equals(updateAbhaDetailsDTO.getRequest().getToken()))
				personalentity.setNdhmToken(updateAbhaDetailsDTO.getRequest().getToken());
			if (null != updateAbhaDetailsDTO.getRequest().getFirstName()
					&& null != updateAbhaDetailsDTO.getRequest().getMiddleName()
					&& null != updateAbhaDetailsDTO.getRequest().getLastName())
				personalentity.setPtFullName(updateAbhaDetailsDTO.getRequest().getFirstName() + " "
						+ updateAbhaDetailsDTO.getRequest().getMiddleName() + " "
						+ updateAbhaDetailsDTO.getRequest().getLastName());

			if (null != updateAbhaDetailsDTO.getRequest().getAddress()
					&& !"".equals(updateAbhaDetailsDTO.getRequest().getAddress()))
				personalentity.setAddress1(updateAbhaDetailsDTO.getRequest().getAddress());

			if (null != updateAbhaDetailsDTO.getRequest().getGender()
					&& !"".equals(updateAbhaDetailsDTO.getRequest().getGender()))
				personalentity.setGender(updateAbhaDetailsDTO.getRequest().getGender());

			personalDetailsRepository.save(personalentity);
		} else {
			LOGGER.info("Patient details not exists");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.PATIENT_REGISTRATION_FAIL,
					AuthConstant.PATIENT_DETAILS_NOT_FOUND));
		}
		LoginUserEntity userentity = userLoginRepositiory.findPatientDetails(personalentity.getPtUserID());
		if (userentity != null) {
			if (updateAbhaDetailsDTO.getRequest().getHealthNumber() != null) {
				userentity.setIsHelathIDExists("Y");
			} else {
				userentity.setIsHelathIDExists("N");
			}

		}

		ndhmentity.setNdMobileno(Long.parseLong(updateAbhaDetailsDTO.getRequest().getMobileNo()));
		ndhmentity.setNdStatus("I");
		ndhmentity.setNdErrorcode(updateAbhaDetailsDTO.getRequest().getErrorCode());
		ndhmentity.setNdErrormessage(updateAbhaDetailsDTO.getRequest().getErrorMsg());
		ndhmRepo.save(ndhmentity);
		PatientResponseDto patientResponseDto = new PatientResponseDto();
		patientResponseDto.setMessage("Data updated successfully!!!!");
		response.setResponse(patientResponseDto);
		response.setStatus(true);
		return response;
	}

	@Override
	public ResponseWrapper<List<PersonalDetailDto>> getPatientDetails(RequestWrapper<PersonalDetailDto> request) {

		ResponseWrapper<List<PersonalDetailDto>> response = new ResponseWrapper<List<PersonalDetailDto>>();
		response.setId("Verification");
		response.setVersion("1.0");
		response.setResponsetime(LocalDateTime.now());
		PatientPersonalDetailEntity personalDetail = null;
		PersonalDetailDto personalDetailResponseDto = null;
		List<PersonalDetailDto> personalDetailResponseDtoList = new ArrayList<PersonalDetailDto>();
		String mobNoOrName = "";
		if (request.getRequest() == null || request.getRequest().getPtFullName() == null) {
			LOGGER.error("Patient mobile number or name  is required.");
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.REQUIRED_MOB_OR_NAME,
					AuthConstant.REQUIRED_MOB_OR_NAME));
		} else if (request.getRequest().getPtFullName().matches("[0-9]+")
				? !request.getRequest().getPtFullName().matches(MATCH_PATTERN)
				: false) {
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_MOBILE_FORMAT_CODE,
					AuthConstant.INVALID_MOBILE_FORMAT));
		}
		try {

			mobNoOrName = request.getRequest().getPtFullName();

			personalDetail = personalDetailsRepository.existsByPtMobNo(mobNoOrName);

			if (personalDetail != null) {
				LOGGER.info("Get Patient Personal Details For: " + mobNoOrName);
				personalDetailResponseDto = new PersonalDetailDto();
				personalDetailResponseDto.setPtFullName(personalDetail.getPtFullName());
				personalDetailResponseDto.setPtEmail(personalDetail.getPtEmail());
				personalDetailResponseDto.setPtMobNo(personalDetail.getPtMobNo());
				personalDetailResponseDto.setPtUserId(personalDetail.getPtUserID());
				personalDetailResponseDto.setPtProfilePhoto(personalDetail.getProfilePhotoPath());
				if (null != personalDetail.getHealthID())
					personalDetailResponseDto.setAbhaId(personalDetail.getHealthID());
				if (null != personalDetail.getHealthNumber())
					personalDetailResponseDto.setAbhaNumber(personalDetail.getHealthNumber());
				
				personalDetailResponseDtoList.add(personalDetailResponseDto);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception occured while fetching Personal Details " + e.getMessage());
			response.setErrors(
					AuthUtil.getExceptionList(null, AuthErrorConstant.INVALID_REQUEST, AuthConstant.INVALID_REQUEST));
		}
		if (personalDetailResponseDtoList.size() == 0) {
			LOGGER.info(AuthConstant.INVALID_USER_ERROR + ": " + mobNoOrName);
			response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.PATIENT_DETAILS_NOT_FOUND,
					AuthConstant.PATIENT_DETAILS_NOT_FOUND));
		} else {
			response.setStatus(true);
			response.setResponse(personalDetailResponseDtoList);
			LOGGER.info(" Get All Detail For:" + mobNoOrName);
		}
		return response;
	}

}
