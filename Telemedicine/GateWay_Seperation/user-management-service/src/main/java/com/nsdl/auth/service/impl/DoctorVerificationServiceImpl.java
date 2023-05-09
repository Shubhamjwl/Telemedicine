package com.nsdl.auth.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.constant.DrVerificationStatus;
import com.nsdl.auth.constant.ErrorConstants;
import com.nsdl.auth.controller.LoginController;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.CreateUserRequest;
import com.nsdl.auth.dto.DoctorDocDtlsDTO;
import com.nsdl.auth.dto.DoctorRegDtlsDTO;
import com.nsdl.auth.dto.GetDoctorDetailsDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.UserDTO;
import com.nsdl.auth.dto.UserResponse;
import com.nsdl.auth.dto.VerifyDoctorRequestDTO;
import com.nsdl.auth.dto.VerifyNotificationDTO;
import com.nsdl.auth.entity.DoctorDocsDtlEntity;
import com.nsdl.auth.entity.DoctorMstrDtlsEntity;
import com.nsdl.auth.entity.DoctorRegDtlsEntity;
import com.nsdl.auth.exception.AuthErrorConstant;
import com.nsdl.auth.exception.DateParsingException;
import com.nsdl.auth.exception.ServiceError;
import com.nsdl.auth.repository.DoctorDocumentRepo;
import com.nsdl.auth.repository.DoctorMstrRepo;
import com.nsdl.auth.repository.LoginUserRepo;
import com.nsdl.auth.repository.RegistrationRepo;
import com.nsdl.auth.service.DoctorVerificationService;
import com.nsdl.auth.utility.DateUtils;
import com.nsdl.auth.utility.EmptyCheckUtility;
import com.nsdl.auth.utility.RestCallUtil;


@Service
@Transactional
public class DoctorVerificationServiceImpl implements DoctorVerificationService {

	@Autowired
	private RegistrationRepo registrationRepo;

	@Autowired
	private LoginController login;

	@Autowired
	DoctorMstrRepo doctorMstrRepo;

	@Autowired
	DoctorDocumentRepo doctorDocumentRepo;

	@Autowired
	LoginUserRepo loginRepo;

	@Autowired
	private UserDTO userDTO;

	@Value("${user.manager.verifyDoctor.response.msg}")
	private String verifyDoctorResponseMsg;

	@Value("${email.notification.url}")
	private String notifyURL;

	private static final Logger logger = LoggerFactory.getLogger(DoctorVerificationServiceImpl.class);

	/**
	 * <p>
	 * This method brings the all list of doctors present in the database.
	 * </p>
	 * </br>
	 * 1. Checks the db for doctor details based on maker checker user verification
	 * status.</br>
	 * 2. if list of doctors are not found throws the exception </br>
	 * 3. else returns list of doctor's as response </br>
	 * 
	 * @return List<DoctorRegDtlsDTO> list of doctor's </br>
	 */
	@Override
	public List<DoctorRegDtlsDTO> getDoctorListToVerify() {
		String userName = userDTO.getUserName();
		logger.info("Doctor list to verify request is received in service class, logged in user is:" + userName);
		List<DoctorRegDtlsDTO> doctorRegDtlsDTOList = null;
		if (!EmptyCheckUtility.isNullEmpty(userName)) {
			List<DoctorRegDtlsEntity> doctorRegDtlsEntityList = registrationRepo.findDoctorListToVerify(userName);
			if (!EmptyCheckUtility.isNullEmpty(doctorRegDtlsEntityList)) {
				doctorRegDtlsDTOList = doctorRegDtlsEntityList.parallelStream()
						.map(docDtls -> DoctorRegDtlsDTO.builder().drEmail(docDtls.getDrdEmail())
								.drUserID(docDtls.getDrdUserId()).drFullName(docDtls.getDrdDrName())
								.drMCINo(docDtls.getDrdMciNumber()).drMobNo(docDtls.getDrdMobileNo())
								.drSMCNo(docDtls.getDrdSmcNumber()).drSpecilization(docDtls.getDrdSpecialiazation())
								.currentStatus(getStatusFromStatusCode(docDtls.getDrdIsverified())).build())
						.collect(Collectors.toList());
			} else {
				logger.error("No Reg Doctor entity list is avaliable for current logged in user");
				throw new DateParsingException(new ServiceError(ErrorConstants.LIST_OF_DOCTOR_NOT_FOUND.getCode(),
						ErrorConstants.LIST_OF_DOCTOR_NOT_FOUND.getMessage()));

			}

		}
		logger.info("user Name is not present for logged in user");
		return doctorRegDtlsDTOList;
	}

	/**
	 * <p>
	 * This method used to verify doctor by current logged in user .
	 * </p>
	 * </br>
	 * 1. Checks the db for doctor details.</br>
	 * 2. if doctor details not found throws the exception </br>
	 * 3. else returns the VerifyDoctorResponseDTO </br>
	 * 
	 * @return VerifyDoctorResponseDTO </br>
	 */
	@Override
	@Transactional(rollbackFor = { SQLException.class, DateParsingException.class })
	public MainResponseDTO<CommonResponseDTO> verifyDoctorByDocName(VerifyDoctorRequestDTO verifyDoctorRequestDTO) {
		logger.info("Request received for verify doctor by doctor name");
		String userName = userDTO.getUserName();
		VerifyNotificationDTO notifyRequest = new VerifyNotificationDTO();
		String doctorId = verifyDoctorRequestDTO.getRegDocUserName().toUpperCase();
		MainResponseDTO<CommonResponseDTO> response = new MainResponseDTO<CommonResponseDTO>();
		CommonResponseDTO verifyResponse = new CommonResponseDTO();
		DoctorRegDtlsEntity doctorRegDtlsEntity = null;
		String Status = verifyDoctorRequestDTO.getVerificationStatusFlag();
		doctorRegDtlsEntity = registrationRepo.findDoctorDetailsByUserID(doctorId);
		if (doctorRegDtlsEntity == null) {
			throw new DateParsingException(new ServiceError(AuthErrorConstant.DR_NOT_FOUND_ERR_CODE,
					String.format(AuthConstant.DR_NOT_FOUND, doctorId)));
		}
		notifyRequest.setEmailId(doctorRegDtlsEntity.getDrdEmail());
		notifyRequest.setMobileNo(doctorRegDtlsEntity.getDrdMobileNo());
		notifyRequest.setUserId(doctorRegDtlsEntity.getDrdUserId());
		notifyRequest.setSendType(null != doctorRegDtlsEntity.getDrdEmail() && !doctorRegDtlsEntity.getDrdEmail().isEmpty() ? AuthConstant.NOTIFY_BOTH_SEND_TYPE : "sms");//added by girishk
//		logger.info("Checking Doctor Status");
//		checkStatusCode(Status, doctorRegDtlsEntity.getDrdIsverified());

		switch (Status) {
		case "Y":
			logger.info("Request received for doctor verification");
			if (doctorRegDtlsEntity.getDrdIsverified().equals(DrVerificationStatus.REGISTERED.getStatus())) {
				logger.info("Doctor Current status is REGISTERED");
				if (registrationRepo.levelOneApproval(DrVerificationStatus.APPROVED.getStatus(), userName,
						DateUtils.getCurrentLocalDateTime(), doctorId) > 0) {
					logger.info("Level one verification of " + doctorId + " : SUCCESS");
					verifyResponse.setMessage(AuthConstant.LEVEL_ONE_SUCCESS);
				} else {
					logger.info("Level one verification of " + doctorId + " : FAILED");
					verifyResponse.setMessage(String.format(AuthConstant.DOCTOR_VERIFICATION_FAILED, doctorId));
				}

			} else if (doctorRegDtlsEntity.getDrdIsverified().equals(DrVerificationStatus.APPROVED.getStatus())) {
				logger.info("Doctor Current status is APPROVED");
				int levelTwoStatus = registrationRepo.levelTwoApproval(DrVerificationStatus.VERIFIED.getStatus(),
						userName, DateUtils.getCurrentLocalDateTime(), doctorId);
				if (levelTwoStatus > 0) {
					logger.info("Level two verification of " + doctorId + " : SUCCESS");
					logger.info("Getting updated Doctor Data for Registration Table");
					DoctorRegDtlsEntity updatedDocRegEntity = registrationRepo.findDoctorDetailsByUserID(doctorId);
					logger.info("Saving Doctor Data to Master Table And User Details Table");
					if (saveDoctorDetailsToMaster(updatedDocRegEntity)
							&& saveDoctorDetailsToUserDetails(updatedDocRegEntity, userName)) {
						// sending sms and email notification
						notifyRequest.setTemplateType(AuthConstant.DR_NOTIFY_TEMPLATE_TYPE_VERIFY);
						sendNotification(notifyRequest);
						verifyResponse.setMessage(AuthConstant.LEVEL_TWO_SUCCESS);
					}
				} else {
					logger.info("Level one verification of " + doctorId + " : FAILED");
					verifyResponse.setMessage(AuthConstant.DOCTOR_VERIFICATION_FAILED);
				}
			} else if (doctorRegDtlsEntity.getDrdIsverified().equals(DrVerificationStatus.VERIFIED.getStatus())) {
				throw new DateParsingException(new ServiceError(AuthErrorConstant.DR_ALREADY_VERIFIED_ERR_CODE,
						AuthConstant.DR_ALREADY_VERIFIED));
			}
			break;

		case "N":
			logger.info("Request received for doctor Rejection");
			if (EmptyCheckUtility.isNullEmpty(verifyDoctorRequestDTO.getReason())) {
				throw new DateParsingException(new ServiceError(AuthErrorConstant.DR_REJECT_REASON_EMPTY_ERR_CODE,
						String.format(AuthConstant.DR_REJECT_EMPTY_REASON_, Status)));
			}
			int notVerifiedStatus = registrationRepo.notVerified(DrVerificationStatus.NOTVERIFIED.getStatus(), userName,
					DateUtils.getCurrentLocalDateTime(), doctorId, verifyDoctorRequestDTO.getReason().toUpperCase());
			if (notVerifiedStatus > 0) {
				logger.info("Doctor " + doctorId + " Rejection status Update : SUCCESS");
				// sending notification
				notifyRequest.setTemplateType(AuthConstant.DR_NOTIFY_TEMPLATE_TYPE_REJECT);
				notifyRequest.setRejectReason(verifyDoctorRequestDTO.getReason());
				sendNotification(notifyRequest);
				verifyResponse.setMessage(AuthConstant.DR_REJECTION_SUCCESS_MESSAGE);
			} else {
				logger.info("Doctor " + doctorId + " Rejection status Update : SUCCESS");
				verifyResponse.setMessage(AuthConstant.DOCTOR_VERIFICATION_FAILED);
			}
			break;
		default:
			logger.info("In Default condition");
			logger.error("Invalid Verification Status Code :: " + Status);
			throw new DateParsingException(new ServiceError(AuthErrorConstant.INVALID_DR_VERIFICATION_STATUS_ERR_CODE,
					String.format(AuthConstant.INVALID_DOCTOR_VERIFICATION_STATUS, Status)));
		}
		response.setResponse(verifyResponse);

		return response;
	}

	private boolean saveDoctorDetailsToMaster(DoctorRegDtlsEntity doctorRegDtlsEntity) {
		logger.info("Saving Data to Doctor Master Entity");
		DoctorMstrDtlsEntity doctorMstrDtlsEntity = new DoctorMstrDtlsEntity();
		doctorMstrDtlsEntity.setDmdUserId(doctorRegDtlsEntity.getDrdUserId());
		doctorMstrDtlsEntity.setDmdDrName(doctorRegDtlsEntity.getDrdDrName());
		doctorMstrDtlsEntity.setDmdConsulFee(doctorRegDtlsEntity.getDrdConsulFee());
		doctorMstrDtlsEntity.setDmdIsRegByIpan("y");
		doctorMstrDtlsEntity.setDmdIsverified(true);
		doctorMstrDtlsEntity.setDmdMciNumber(doctorRegDtlsEntity.getDrdMciNumber());
		//doctorMstrDtlsEntity.setDmdMobileNo(Long.valueOf(doctorRegDtlsEntity.getDrdMobileNo()));
		doctorMstrDtlsEntity.setDmdMobileNo(Long.parseLong(doctorRegDtlsEntity.getDrdMobileNo()));
		doctorMstrDtlsEntity.setDmdPassword(doctorRegDtlsEntity.getDrdPassword());
		doctorMstrDtlsEntity.setDmdSmcNumber(doctorRegDtlsEntity.getDrdSmcNumber());
		doctorMstrDtlsEntity.setDmdSpecialiazation(doctorRegDtlsEntity.getDrdSpecialiazation());
		doctorMstrDtlsEntity.setDmdGender(doctorRegDtlsEntity.getDrdGender());
		doctorMstrDtlsEntity
				.setDmdEmail(null != doctorRegDtlsEntity.getDrdEmail() ? doctorRegDtlsEntity.getDrdEmail() : "");
		doctorMstrDtlsEntity.setProfilePhoto(doctorRegDtlsEntity.getDrdPhotoPath());
		doctorMstrDtlsEntity.setDmdAddress1(doctorRegDtlsEntity.getDrdAddress1());
		doctorMstrDtlsEntity.setDmdAddress2(doctorRegDtlsEntity.getDrdAddress2());
		doctorMstrDtlsEntity.setDmdAddress3(doctorRegDtlsEntity.getDrdAddress3());
		doctorMstrDtlsEntity.setDmdCity(doctorRegDtlsEntity.getDrdCity());
		doctorMstrDtlsEntity.setDmdState(doctorRegDtlsEntity.getDrdState());
		try {
			if (doctorMstrRepo.save(doctorMstrDtlsEntity) != null) {
				return true;
			}
			logger.info("Doctor Data Saved to Master Entity Successfully");

		} catch (Exception e) {
			logger.error(
					"Exception Occurred while saving Doctor Data to Doctor Master Details Table :: " + e.getMessage());
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.TECHNICAL_ERR_CODE, AuthConstant.TECHNICAL_EXCEPTION));
		}
		return false;
	}

	private boolean saveDoctorDetailsToUserDetails(DoctorRegDtlsEntity doctorRegDtlsEntity, String systemUserId) {
		MainRequestDTO<CreateUserRequest> mainRequest = new MainRequestDTO<>();
		CreateUserRequest userRequest = new CreateUserRequest();
		userRequest.setUserFullName(doctorRegDtlsEntity.getDrdDrName());
		//userRequest.setMobileNumber(Long.valueOf(doctorRegDtlsEntity.getDrdMobileNo()));
		userRequest.setMobileNumber(Long.parseLong(doctorRegDtlsEntity.getDrdMobileNo()));
		userRequest.setSmcNumber(doctorRegDtlsEntity.getDrdSmcNumber());
		userRequest.setMciNumber(doctorRegDtlsEntity.getDrdMciNumber());
		userRequest.setEmail(doctorRegDtlsEntity.getDrdEmail());
		userRequest.setPassword(doctorRegDtlsEntity.getDrdPassword().trim());
		userRequest.setUserId(doctorRegDtlsEntity.getDrdUserId());
		userRequest.setUserType(AuthConstant.DOCTOR_USER_TYPE);
		mainRequest.setRequest(userRequest);
		MainResponseDTO<UserResponse> response = login.createUser(mainRequest).getBody();
		if (response.getErrors() != null) {
			logger.error("Error Occurred While saving Doctor Data to User Details Table : ");
			response.getErrors().forEach(e -> {
				logger.error(e.getErrorCode() + " : " + e.getMessage());
			});
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.TECHNICAL_ERR_CODE, AuthConstant.TECHNICAL_EXCEPTION));
		} else {
			if (loginRepo.activeDeactiveUser(response.getResponse().getUserId(), true, null,
					DateUtils.getCurrentLocalDateTime(), systemUserId) < 1) {
				logger.error("Error Occurred while Activating the User : " + response.getResponse().getUserId());
				throw new DateParsingException(
						new ServiceError(AuthErrorConstant.TECHNICAL_ERR_CODE, AuthConstant.TECHNICAL_EXCEPTION));
			}
		}
		return true;
	}

	private String getStatusFromStatusCode(String drVerStatus) {
		switch (drVerStatus.toUpperCase()) {
		case "R":
			return DrVerificationStatus.REGISTERED.getStatusName();
		case "A":
			return DrVerificationStatus.APPROVED.getStatusName();
		case "APPROVED":
			return DrVerificationStatus.APPROVED.getStatus();
		case "NOT VERIFIED":
			return DrVerificationStatus.NOTVERIFIED.getStatus();
		case "VERIFIED":
			return DrVerificationStatus.VERIFIED.getStatus();
		default:
			logger.error("Invalid Verification Status Code :: " + drVerStatus);
			throw new DateParsingException(new ServiceError(AuthErrorConstant.INVALID_DR_VERIFICATION_STATUS_ERR_CODE,
					String.format(AuthConstant.INVALID_DOCTOR_VERIFICATION_STATUS, drVerStatus)));
		}
	}

	@Override
	public MainResponseDTO<GetDoctorDetailsDTO> getDoctorDetailsByDoctorId(String doctorId) {
		MainResponseDTO<GetDoctorDetailsDTO> response = null;
		GetDoctorDetailsDTO doctorDetails = new GetDoctorDetailsDTO();
		List<DoctorDocDtlsDTO> doctorDocDtlsDTOList = new ArrayList<DoctorDocDtlsDTO>();
		logger.info("Getting Doctor Details from docter registration table");
		DoctorRegDtlsEntity doctorRegDtlsEntity = registrationRepo.findDoctorDetailsByUserID(doctorId);
		if (doctorRegDtlsEntity == null) {
			logger.error("Doctor Details not present doctor in registration table for given registration id");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.DR_NOT_FOUND_ERR_CODE,
					String.format(AuthConstant.DR_NOT_FOUND, doctorId)));
		}
		BeanUtils.copyProperties(doctorRegDtlsEntity, doctorDetails);

		logger.info("Getting Doctor Document Details from docter document table");
		List<DoctorDocsDtlEntity> doctordocsEntityList = doctorDocumentRepo.findDoctorDocuments(doctorId);
		if (null != doctordocsEntityList && !doctordocsEntityList.isEmpty()) {

			for (DoctorDocsDtlEntity doctorDocsDtlEntity : doctordocsEntityList) {
				DoctorDocDtlsDTO doctorDocDtlsDTO = new DoctorDocDtlsDTO();
				BeanUtils.copyProperties(doctorDocsDtlEntity, doctorDocDtlsDTO);
				doctorDocDtlsDTOList.add(doctorDocDtlsDTO);
			}
			doctorDetails.setDrDocsDtls(doctorDocDtlsDTOList);
		} else {
			logger.info("Doctor documents not available.");
		}
		logger.info("Getting Doctor profile photo");
		// fetch profile photo
		if (null != doctorRegDtlsEntity.getDrdPhotoPath() && !doctorRegDtlsEntity.getDrdPhotoPath().isEmpty()) {
			String profilePhoto = getDoctorProfilePhoto(doctorRegDtlsEntity.getDrdPhotoPath());
			doctorDetails.setDrdPhotoPath(null != profilePhoto ? profilePhoto : "");
		}
		response = new MainResponseDTO<GetDoctorDetailsDTO>();
		response.setResponse(doctorDetails);
		return response;
	}

	private String getDoctorProfilePhoto(String profilePhotoPath) {
		String finalResponse = null;
		try {
			finalResponse = new String(Base64.encodeBase64(Files.readAllBytes(Paths.get(profilePhotoPath))));
			finalResponse = "data:image/jpeg;base64," + finalResponse;
		} catch (Throwable e) {
			logger.error("Exception while getting doctor profile photo");
			e.printStackTrace();
		}
		System.out.println("Response " + finalResponse);
		return finalResponse;
	}

	private boolean sendNotification(VerifyNotificationDTO restRequest) {
		MainRequestDTO<VerifyNotificationDTO> mainRequest = new MainRequestDTO<VerifyNotificationDTO>();
		ResponseEntity<MainResponseDTO<VerifyNotificationDTO>> responseEntity = null;
		mainRequest.setId(AuthConstant.API_ID);
		mainRequest.setVersion(AuthConstant.API_VERSION);
		mainRequest.setMethod(AuthConstant.API_METHOD);
		mainRequest.setRequesttime(
				Date.from(DateUtils.getCurrentLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()));
		mainRequest.setRequest(restRequest);
		try {
			responseEntity = (ResponseEntity<MainResponseDTO<VerifyNotificationDTO>>) RestCallUtil
					.postApiRequest(notifyURL, mainRequest, MainResponseDTO.class);
		} catch (Exception e) {
			logger.error("Exception Occurred while calling Email Notitification Service for Doctor verification :: "
					+ e.getMessage());
			/*
			 * throw new DateParsingException( new
			 * ServiceError(AuthErrorConstant.TECHNICAL_ERR_CODE,
			 * AuthConstant.TECHNICAL_EXCEPTION));
			 */
		}
		MainResponseDTO<VerifyNotificationDTO> mainResponse = responseEntity.getBody();
		if (mainResponse.isStatus()) {
			return true;
		} else {
			logger.error("Email Notification : FAILED");
			if (!mainResponse.getErrors().isEmpty() || mainResponse.getErrors() != null) {
				logger.error("Error in notification service : " + mainResponse.getErrors().get(0).getErrorCode()
						+ " Error message is" + mainResponse.getErrors().get(0).getMessage());

			} else {
				logger.error("No Error Messages Present ,Email Notification : FAILED");
			}
		}
		return false;
	}

}
