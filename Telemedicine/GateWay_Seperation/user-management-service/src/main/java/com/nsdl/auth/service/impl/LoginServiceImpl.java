package com.nsdl.auth.service.impl;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.constant.UserRoleByUserType;
import com.nsdl.auth.dto.CaptchaResponseDto;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.CreateUserRequest;
import com.nsdl.auth.dto.ForgotPasswordRequest;
import com.nsdl.auth.dto.LoginRequest;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.NotifyRequestDTO;
import com.nsdl.auth.dto.NotifyResponseDTO;
import com.nsdl.auth.dto.ResetPasswordRequest;
import com.nsdl.auth.dto.UpdateUserDetailsRequest;
import com.nsdl.auth.dto.UserActiveDeactiveRequestDTO;
import com.nsdl.auth.dto.UserDTO;
import com.nsdl.auth.dto.UserResponse;
import com.nsdl.auth.entity.DoctorMstrDtlsEntity;
import com.nsdl.auth.entity.DoctorRegDtlsEntity;
import com.nsdl.auth.entity.LoginUserEntity;
import com.nsdl.auth.entity.RoleMasterEntity;
import com.nsdl.auth.entity.UserHistory;
import com.nsdl.auth.exception.AuthErrorConstant;
import com.nsdl.auth.exception.DateParsingException;
import com.nsdl.auth.exception.ServiceError;
import com.nsdl.auth.logger.LoggingClientInfo;
import com.nsdl.auth.repository.DoctorMstrRepo;
import com.nsdl.auth.repository.LoginUserRepo;
import com.nsdl.auth.repository.RegistrationRepo;
import com.nsdl.auth.repository.RoleServiceRepository;
import com.nsdl.auth.repository.TokenRepo;
import com.nsdl.auth.repository.UserHistoryRepo;
import com.nsdl.auth.service.AuditService;
import com.nsdl.auth.service.LoginService;
import com.nsdl.auth.utility.CommonValidationUtil;
import com.nsdl.auth.utility.DateUtils;
import com.nsdl.auth.utility.EmptyCheckUtility;
import com.nsdl.auth.utility.HMACUtils;
import com.nsdl.auth.utility.RandomStringUtil;
import com.nsdl.auth.utility.RestCallUtil;

@Service
@LoggingClientInfo
public class LoginServiceImpl implements LoginService {

	@Value("${login.password.incorrect.attempt.threshold}")
	Long allowedValidationCount;

	@Value("${login.password.expiry.time}")
	Long userPassExpiryDuration;

	@Value("${login.password.locking.time}")
	String userFreezedTime;

	@Value("${email.notification.url}")
	String emailUrl;

	@Value("${login.password.limit}")
	Long passwordLimit;

	@Value("${login.password.hash.enable.flag}")
	boolean passHashFlag;

	@Value("${captcha.url}")
	String captchaUrl;
	
	@Value("${OTP_GENERATE_URL}")
	private String generateOtpURL;
	
	@Value("${OTP_VERIFY_URL}")
	private String verifyOtpURL;

	@Autowired
	LoginUserRepo userRepo;

	@Autowired
	TokenRepo tokenRepo;

	@Autowired
	UserHistoryRepo userHistoryRepo;

	@Autowired
	RoleServiceRepository userRoleRepo;

	@Autowired
	AuditService auditService;

	@Autowired
	private UserDTO userDetails;
	
	@Autowired
	DoctorPatientMapRepo doctorPatientMapRepo;
	

	private static final Logger  logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Override
	public MainResponseDTO<UserResponse> createUserService(MainRequestDTO<CreateUserRequest> request) {

		MainResponseDTO<UserResponse> response = new MainResponseDTO<UserResponse>();
		UserResponse userResponse = new UserResponse();
		logger.info("Getting user data from createUser request");
		CreateUserRequest userRequest = request.getRequest();
		String userId = userRequest.getUserId().trim().toUpperCase();
		userResponse.setUserId(userId);
		validateRequestData(userRequest);

		if (userRepo.existsByUserId(userId)) {
			logger.error("given userid already exists");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.USERID_ALREADY_EXISTS_CODE, AuthConstant.USERID_ALREADY_EXISTS));
		}
		if (userRepo.existsByMobile(userRequest.getMobileNumber())) {
			logger.error("given Mobile Number already exists");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.USERID_ALREADY_EXISTS_CODE, AuthConstant.MOBILE_ALREADY_EXISTS));
		}
		if (userRepo.existsByEmail(userRequest.getEmail().toUpperCase())) {
			logger.error("given Email id already exists");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.USERID_ALREADY_EXISTS_CODE, AuthConstant.EMAIL_ALREADY_EXISTS));
		}
		String userType = userRequest.getUserType().trim().toUpperCase();
		if (userType.equals(UserRoleByUserType.ADMIN.getUserType())) {
			if (userRepo.existsByUserType(userType)) {
				logger.error("User Already Exists for User Type : Admin");
				throw new DateParsingException(new ServiceError(AuthErrorConstant.USER_ALREADY_EXIST_FOR_ADMIN_ERR_CODE,
						AuthConstant.USER_ALREADY_EXIST_FOR_ADMIN));
			}
		}
		LoginUserEntity savedEntity = createUserEntityAndSave(userRequest);
		logger.info("Saved user Data to user details");
		if (savedEntity != null) {
			logger.info("User creation : Success");
			response.setStatus(true);
			userResponse.setMessage(AuthConstant.USER_CREATE_SUCCESS);
			userResponse.setRole(savedEntity.getRoleEntity().getRoleName());
			saveUserToHistory(savedEntity, "NEW");
		} else {
			logger.info("User creation : Failed");
			userResponse.setMessage(AuthConstant.USER_CREATE_FAIL);
		}
		// response.setResponsetime(DateUtils.getCurrentLocalDateTime());
		response.setResponse(userResponse);
		logger.info("Returning response User Creation");
		return response;
	}

	@Override
	public MainResponseDTO<UserResponse> userLoginService(MainRequestDTO<LoginRequest> request) {
		UserResponse userResponse = new UserResponse();
		MainResponseDTO<UserResponse> response = null;
		String userIdFromReq = request.getRequest().getUserId().toUpperCase();
		logger.info("Getting user Data from database on the basis of userId");
		System.out.println(userIdFromReq.matches("[0-9]+"));
		LoginUserEntity userEntity = userRepo.findByUserIdOrMobile(userIdFromReq,
				(userIdFromReq.matches("[0-9]+") ? Long.parseLong(userIdFromReq) : null));
		checkIsValidUser(userEntity);
		String userId = userEntity.getUserId();
		userResponse.setUserId(userId);
		userResponse.setUserName(userEntity.getUserFullName());
		if (userEntity.getIsLoggedIn()) {
			logger.info("user already logged in");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.USER_ALREADY_LOGGED_IN_ERR_CODE,
					AuthConstant.USER_ALREADY_LOGGED_IN));
		}
		// Long attemptCount = userEntity.getIncorrectCount();

		if (userEntity.getIsActive() && !userEntity.getIsLock()
				&& userEntity.getFailAttemptCount() == allowedValidationCount) {
			// audit before blocking the user
			auditService.auditloginService(userEntity, userId);
			userRepo.freezeUser(userId, DateUtils.getCurrentLocalDateTime());
			// throw exception
			logger.error("user is in blocked/locked state");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.FAILURE_AND_FREEZED_ERR_CODE,
					AuthConstant.FAILURE_AND_FREEZED_MESSAGE));
		}

		if (userEntity.getIsLock()) {
			if (DateUtils.timeDifferenceInSeconds(userEntity.getModifiedTime(),
					DateUtils.getCurrentLocalDateTime()) < (Integer.parseInt(userFreezedTime))) {
				logger.error("user blocking period not over");
				throw new DateParsingException(new ServiceError(AuthErrorConstant.FAILURE_AND_FREEZED_ERR_CODE,
						AuthConstant.FAILURE_AND_FREEZED_MESSAGE));
			} else {
				logger.info("user blocking period is over");
				// audit before unblocking the user
				auditService.auditloginService(userEntity, userId);
				userRepo.unFreezeUser(userId, DateUtils.getCurrentLocalDateTime());
				logger.info("user unblock : Success");
			}
		}

		String hashString = null;
		if (passHashFlag) {
			hashString = HMACUtils.hash(request.getRequest().getPassword().trim().getBytes(StandardCharsets.UTF_8));
		} else {
			hashString = request.getRequest().getPassword().trim();
		}
		if (!hashString.equals(userEntity.getPassword())) {
			// audit before incrementing the ValidationAttemptCount
			auditService.auditloginService(userEntity, userId);
			int updaterow = userRepo.updateValidationAttemptCount(userId, DateUtils.getCurrentLocalDateTime());
			System.out.println("updaterow" + updaterow);
			// throw exception
			logger.error("user password validation : Failed");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.INCORRECT_PASSWORD_ERR_CODE,
					AuthConstant.INCORRECT_PASSWORD_ERROR));
		}
		if (!EmptyCheckUtility.isNullEmpty(userEntity.getPwdExpiryTime())
				&& userEntity.getPwdExpiryTime().isBefore(DateUtils.getCurrentLocalDateTime())) {
			logger.error("user password : Expired");
			// when user password is expired,allow for login but set ischangedpassword flag
			// to true,for forcefully reset password from frontend
			auditService.auditloginService(userEntity, userId);
			userRepo.userLoggedInOnPasswordExpired(userId, DateUtils.getCurrentLocalDateTime());
			logger.error(
					"user login sucess on password expired and forcefully allow user to reset password from frontend");
			userResponse.setPasswordChanged(true);
			userResponse.setMessage(AuthConstant.PWD_EXPIRED_ERROR);
			/*
			 * throw new DateParsingException( new
			 * ServiceError(AuthErrorConstant.PWD_EXPIRED_ERR_CODE,
			 * AuthConstant.PWD_EXPIRED_ERROR));
			 */
		} else {
			auditService.auditloginService(userEntity, userId);
			userRepo.userLoggedIn(userId, DateUtils.getCurrentLocalDateTime());
			logger.info("user login : Success");
			userResponse.setMessage(AuthConstant.USERPWD_SUCCESS_MESSAGE);
			userResponse.setPasswordChanged(userEntity.getIsPwdChange());
		}
		
		//code added by girishk start
		if(userEntity.getRoleEntity().getRoleName().equalsIgnoreCase("PATIENT")) {
			List<DoctorPatientMapEntity> doctorPatientMappingDtls = doctorPatientMapRepo.checkPatientToDoctorMapping(userId);
			if(null != doctorPatientMappingDtls && doctorPatientMappingDtls.size() > 0) {
				userResponse.setCloseDrGrp(true);
			}else {
				userResponse.setCloseDrGrp(false);
			}
		}else {
			userResponse.setCloseDrGrp(false);
		}
		//code added by girishk end
		userResponse.setRole(userEntity.getRoleEntity().getRoleName());
		// audit before changing isloggedin flag (successfull signin)

		response = new MainResponseDTO<UserResponse>();
		response.setStatus(true);
		response.setResponse(userResponse);
		logger.info("Returning response User Sign in");
		return response;

	}

	@Override
	public MainResponseDTO<CommonResponseDTO> userLogoutService(String userId) {
		MainResponseDTO<CommonResponseDTO> response = new MainResponseDTO<CommonResponseDTO>();
		CommonResponseDTO userResponse = new CommonResponseDTO();
		userId = userId.trim().toUpperCase();
		String tokenUserId = (!EmptyCheckUtility.isNullEmpty(userDetails.getUserName())
				? userDetails.getUserName().toUpperCase()
				: userId);
		logger.info("Getting user Data from database on the basis of userId");
		LoginUserEntity userEntity = userRepo.findByUserId(userId);
		checkIsValidUser(userEntity);
		if (!userEntity.getIsLoggedIn()) {
			logger.error("User is not logged in to perform logout");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.USER_NOT_LOGIN_ERR_CODE, AuthConstant.USER_NOT_LOGIN));
		}
		// audit before user logout
		auditService.auditloginService(userEntity, tokenUserId);
		int count = userRepo.userLoggedOut(userId, DateUtils.getCurrentLocalDateTime(), tokenUserId);
		if (count < 1) {
			logger.error("User logout : Failed");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.USER_LOGOUT_ERR_CODE, AuthConstant.USER_LOGOUT_ERROR));
		}
		logger.info("Deleting user token from Database");
		tokenRepo.deleteByUserId(userId);
		logger.info("User logout : Success");
		userResponse.setMessage(AuthConstant.USER_LOGOUT_SUCCESS);
		response.setStatus(true);
		response.setResponse(userResponse);
		response.setResponsetime(DateUtils.getCurrentLocalDateTime());
		logger.info("Returning response User Logout");
		return response;
	}

	private void saveUserToHistory(LoginUserEntity userEntity, String flag) {
		UserHistory userHistory = new UserHistory();
		userHistory.setUserId(userEntity.getUserId());
		userHistory.setPassword(userEntity.getPassword());
		if (flag.equals("NEW")) {
			userHistory.setCreatedTime(userEntity.getCreatedTime());
			userHistory.setCreatedBy(userEntity.getCreatedBy());
		} else if (flag.equals("RESET")) {
			userHistory.setCreatedTime(userEntity.getModifiedTime());
			userHistory.setCreatedBy(userEntity.getModifiedBy());
		}

		// userHistory.setCreatedTime(userEntity.getCreatedTime());
		userHistoryRepo.saveAndFlush(userHistory);
		logger.info("Saved user Data to user history");
	}

	private LoginUserEntity createUserEntityAndSave(CreateUserRequest req) {
		String requestUserId = req.getUserId().trim().toUpperCase();

		String createdByUser = EmptyCheckUtility.isNullEmpty(userDetails.getUserName()) ? requestUserId
				: userDetails.getUserName().toUpperCase();

		LoginUserEntity entity = new LoginUserEntity();
		entity.setUserId(requestUserId);
		entity.setUserFullName(req.getUserFullName().trim().toUpperCase());
		entity.setMobile(req.getMobileNumber());
		entity.setEmail(req.getEmail() != null ? req.getEmail().trim().toUpperCase() : null);
		entity.setSmcNumber(req.getSmcNumber());
		entity.setMciNumber(req.getMciNumber());
		entity.setFailAttemptCount(0l);
		// LocalDateTime time = LocalDateTime.now().plusMonths(userPassExpiryDuration);
		// LocalDateTime time = LocalDateTime.now().plusMinutes(userPassExpiryDuration);
		entity.setPwdExpiryTime(LocalDateTime.now().plusMinutes(userPassExpiryDuration));
		if (passHashFlag) {
			entity.setPassword(HMACUtils.hash(req.getPassword().trim().getBytes(StandardCharsets.UTF_8)));
		} else {
			entity.setPassword(req.getPassword().trim());
		}
		entity.setUserType(req.getUserType().trim().toUpperCase());
		entity.setCreatedBy(createdByUser);
		entity.setCreatedTime(DateUtils.getCurrentLocalDateTime());
		entity.setModifiedTime(DateUtils.getCurrentLocalDateTime());
		String userType = req.getUserType().trim().toUpperCase();

		if (userType.equals(UserRoleByUserType.PATIENT.getUserType())) {
			entity.setRoleEntity(getRoleByUserType(UserRoleByUserType.PATIENT.getRole()));
		} else if (userType.equals(UserRoleByUserType.DOCTOR.getUserType())) {
			entity.setRoleEntity(getRoleByUserType(UserRoleByUserType.DOCTOR.getRole()));
		} else if (userType.equals(UserRoleByUserType.SCRIBE.getUserType())) {
			entity.setIsActive(true);
			entity.setRoleEntity(getRoleByUserType(UserRoleByUserType.SCRIBE.getRole()));
		} else if (userType.equals(UserRoleByUserType.ADMIN.getUserType())) {
			entity.setIsActive(true);
			entity.setRoleEntity(getRoleByUserType(UserRoleByUserType.ADMIN.getRole()));
		} else if (userType.equals(UserRoleByUserType.SYSTEMUSER.getUserType())) {
			entity.setIsActive(true);
			entity.setRoleEntity(getRoleByUserType(UserRoleByUserType.SYSTEMUSER.getRole()));
		}
		auditService.auditloginService(entity, createdByUser);
		try {
			entity = userRepo.saveAndFlush(entity);
		} catch (Exception e) {
			logger.error("Database Exception Occurred while creating user::" + e.getMessage());
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.TECHNICAL_ERR_CODE, AuthConstant.TECHNICAL_EXCEPTION));
		}

		return entity;
	}

	private RoleMasterEntity getRoleByUserType(String type) {
		return userRoleRepo.findByRoleName(type);
	}

	private boolean checkLastThreePassword(String userId, String newPass) {
		List<UserHistory> userTransactionList = userHistoryRepo.checkLastThreePassword(userId, passwordLimit);
		boolean result = false;
		if (userTransactionList != null) {
			result = userTransactionList.stream().anyMatch(dtls -> newPass.equals(dtls.getPassword()));
		}
		return result;
	}

	@Override
	public MainResponseDTO<CommonResponseDTO> userPasswordResetService(MainRequestDTO<ResetPasswordRequest> request) {
		CommonResponseDTO userResponse = new CommonResponseDTO();
		DoctorRegDtlsEntity doctorRegDtlsEntity=null;
		DoctorMstrDtlsEntity doctorMstrDtlsEntity=null;
		MainResponseDTO<CommonResponseDTO> response = new MainResponseDTO<CommonResponseDTO>();
		MainResponseDTO<OTPResponse> otpVerificationResponse = null;
		// response = (MainResponseDTO<CommonResponseDTO>)
		// AuthUtil.getMainResponseDto(request);
		ResetPasswordRequest resetRequest = request.getRequest();
		String userId = resetRequest.getUserId().trim().toUpperCase();
		logger.info("Getting user Data from database on the basis of userId");
		LoginUserEntity userEntity = userRepo.findByUserId(userId);
		//code added by girishk to verify otp only if user is login first time start.
//		if(userEntity.getIsPwdChange()) {
		if(null != request.getRequest().getOtp() && !request.getRequest().getOtp().isEmpty()) {
			try {
				otpVerificationResponse = verifyOTP(userEntity, request.getRequest().getOtp());
			} catch (Exception e) {
				logger.error("Exception while calling verifyOTP API.");
				e.printStackTrace();
				throw new DateParsingException(new ServiceError(AuthErrorConstant.UNABLE_TO_CONNECT_OTP_VERIFICATION_SERVICE, AuthConstant.UNABLE_TO_CONNECT_OTP_VERIFICATION_SERVICE));
			}
			if(!otpVerificationResponse.isStatus()) {
				logger.error("OTP verification failed..");
				throw new DateParsingException(new ServiceError(AuthErrorConstant.OTP_VERIFICATION_FAILED, AuthConstant.OTP_VERIFICATION_FAILED));
			}
		}else {
			logger.error("Invalid OTP value");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.INVALID_OTP, AuthConstant.INVALID_OTP));
		}
//		}
		//code added by girishk to verify otp only if user is login first time end.
		// userResponse.setUserId(userId);
		checkIsValidUser(userEntity);
		if (!CommonValidationUtil.validatePassword(resetRequest.getNewPwd())) {
			logger.error("user new password format validation : Failed");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.INVALID_PASSWORD_FORMAT_CODE,
					AuthConstant.INVALID_PASSWORD_FORMAT));
		}

		//commented by girishk
//		if (resetRequest.getOldPwd().equals(resetRequest.getNewPwd())) {
//			logger.error("user old password value and new password value : Matched");
//			throw new DateParsingException(new ServiceError(AuthErrorConstant.OLD_AND_NEW_PASSWORD_MATCH_ERR_CODE,
//					AuthConstant.OLD_AND_NEW_PASSWORD_MATCH_ERROR));
//		}
		if (!resetRequest.getNewPwd().equals(resetRequest.getConfirmPwd())) {
			logger.error("user new password value and confirm password value : Not Matched");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.CONFIRMPASSWOD_MISMATCH_ERR_CODE,
					AuthConstant.CONFIRM_PASSWORD_MISMATCH_ERROR));
		}
		// commented condition to check user is blocked or not while resetting the
		// password..(As forgot and reset password functionality is changed)
		/*
		 * if (userEntity.getIsLock()) { if
		 * (DateUtils.timeDifferenceInSeconds(userEntity.getModifiedTime(),
		 * DateUtils.getCurrentLocalDateTime()) < (Integer.parseInt(userFreezedTime))) {
		 * logger.error("user blocking period not over"); throw new
		 * DateParsingException(new
		 * ServiceError(AuthErrorConstant.FAILURE_AND_FREEZED_ERR_CODE,
		 * AuthConstant.FAILURE_AND_FREEZED_MESSAGE)); } else {
		 * logger.info("user blocking period is over"); // audit before unblocking the
		 * user auditService.auditloginService(userEntity, userId);
		 * userRepo.unFreezeUser(userId, DateUtils.getCurrentLocalDateTime());
		 * logger.info("user unblock : Success"); } }
		 */

//		String oldPassword = null;
		String newPassword = null;
		if (passHashFlag) {
//			oldPassword = HMACUtils.hash(resetRequest.getOldPwd().getBytes(StandardCharsets.UTF_8));
			newPassword = HMACUtils.hash(resetRequest.getNewPwd().getBytes(StandardCharsets.UTF_8));
		} else {
//			oldPassword = resetRequest.getOldPwd();
			newPassword = resetRequest.getNewPwd();
		}
//		if (!oldPassword.equals(userEntity.getPassword())) {
//			logger.error("Old password value validation with existing password : Failed");
//			throw new DateParsingException(new ServiceError(AuthErrorConstant.PASSWOD_MISMATCH_ERR_CODE,
//					AuthConstant.PASSWORD_MISMATCH_ERROR));
//		}
		if (checkLastThreePassword(userId, newPassword)) {
			logger.error("New password matches with last three password ");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.PASSWORD_MATCH_ERR_CODE, AuthConstant.PASSWORD_MATCH_ERROR));
		}
		// audit before change password
		auditService.auditloginService(userEntity, userId);
		logger.info("Reset the new password and logout the user");
		int updateStatus = userRepo.resetPassword(userId, LocalDateTime.now().plusMinutes(userPassExpiryDuration),
				DateUtils.getCurrentLocalDateTime(), newPassword);
		/*//Set new password to doctor registration and doctor master table ,changes by sayali
		doctorRegDtlsEntity=registrationRepo.findDoctorDetailsByUserID(userId);
		if(null!=doctorRegDtlsEntity)
		{
			doctorRegDtlsEntity.setDrdPassword(newPassword);
			registrationRepo.save(doctorRegDtlsEntity);
		}else
		{
			logger.error("user details not present");
			throw new DateParsingException(new ServiceError("LS-0000", AuthConstant.DR_NOT_FOUND));
		}
		doctorMstrDtlsEntity=doctorMstrRepo.findByDmdUserId(userId);
		if(null!=doctorMstrDtlsEntity)
		{
			doctorMstrDtlsEntity.setDmdPassword(newPassword);
			doctorMstrRepo.save(doctorMstrDtlsEntity);
		}else
		{
			logger.error("user details not present");
			throw new DateParsingException(new ServiceError("LS-0000", AuthConstant.DR_NOT_FOUND));
		}*/
		if (updateStatus > 0) {
			logger.info("User new Password reset : Success");
			userResponse.setMessage(AuthConstant.PWD_RESET_SUCCESS);
			response.setResponse(userResponse);
			response.setStatus(true);
			logger.info("Delete the token from DB");
			// tokenRepo.deleteByUserId(userId);
			saveUserToHistory(userRepo.findByUserId(userId), "RESET");
		}
		logger.info("Returning response User password Reset");
		return response;
	}

	private void validateRequestData(Object request) {
		String password = "";
		String email = "";
		String mci = "";
		String smc = "";
		Long mobileNo = null;
		if (request instanceof CreateUserRequest) {
			CreateUserRequest userRequest = (CreateUserRequest) request;
			password = userRequest.getPassword();
			email = userRequest.getEmail();
			mobileNo = userRequest.getMobileNumber();
			mci = userRequest.getMciNumber();
			smc = userRequest.getSmcNumber();
		} else if (request instanceof UpdateUserDetailsRequest) {
			UpdateUserDetailsRequest updateRequest = (UpdateUserDetailsRequest) request;
			email = updateRequest.getEmail();
			mobileNo = updateRequest.getMobileNumber();
			mci = updateRequest.getMciNumber();
			smc = updateRequest.getSmcNumber();
		}
		//commented by sayali
		/*if (!EmptyCheckUtility.isNullEmpty(password) && !CommonValidationUtil.validatePassword(password)) {
			logger.error("user password format validation : Failed");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.INVALID_PASSWORD_FORMAT_CODE,
					AuthConstant.INVALID_PASSWORD_FORMAT));
		}*/

		if (!EmptyCheckUtility.isNullEmpty(email) && !CommonValidationUtil.validateEmail(email)) {
			logger.error("user email format validation : Failed");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.INVALID_EMAIL_FORMAT_CODE, AuthConstant.INVALID_EMAIL_FORMAT));
		}
		if (!EmptyCheckUtility.isNullEmpty(mobileNo) && !CommonValidationUtil.validateMobileNo(mobileNo)) {
			logger.error("user mobile no format validation : Failed");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.INVALID_MOBILE_FORMAT_CODE, AuthConstant.INVALID_MOBILE_FORMAT));
		}
		if (!EmptyCheckUtility.isNullEmpty(mci) && !CommonValidationUtil.validateSmcNumber(mci)) {
			logger.error("user smcNumber format validation : Failed");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.INVALID_SMC_NO_FORMAT_CODE, AuthConstant.INVALID_SMC_NO_FORMAT));
		}
		if (!EmptyCheckUtility.isNullEmpty(smc) && !CommonValidationUtil.validateMciNumber(smc)) {
			logger.error("user mciNumber format validation : Failed");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.INVALID_MCI_NO_FORMAT_CODE, AuthConstant.INVALID_MCI_NO_FORMAT));
		}
	}

	private void checkIsValidUser(LoginUserEntity userEntity) {
		if (userEntity == null) {
			logger.error("User data not found for given user : Invalid User");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.INVALID_USER_ERR_CODE, AuthConstant.INVALID_USER_ERROR));
		} else if (!userEntity.getIsActive()) {
			logger.error("User status is not Active : InActive User");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.INACTIVE_USER_ERR_CODE, AuthConstant.INACTIVE_USER_ERROR));
		}
	}

	@Override
	public MainResponseDTO<CommonResponseDTO> userActiveDeactiveService(
			MainRequestDTO<UserActiveDeactiveRequestDTO> request) {
		MainResponseDTO<CommonResponseDTO> response = null;
		CommonResponseDTO activeDeactiveResponse = new CommonResponseDTO();
		UserActiveDeactiveRequestDTO userActiveDeactiveRequest = request.getRequest();
		String operationType = userActiveDeactiveRequest.getOperationType().trim().toUpperCase();
		String userId = userActiveDeactiveRequest.getUserId().trim().toUpperCase();
		String tokenUserId = userDetails.getUserName().toUpperCase();
		LoginUserEntity userEntity = userRepo.findByUserId(userId);
		logger.info("Fetched user Entity");
		LoginUserEntity adminUserEntity = userRepo.findByUserId(tokenUserId);
		logger.info("Fetched user entity for Admin data");

		if (userEntity == null) {
			logger.error("User data not found for given userId : Invalid User");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.INVALID_USER_ERR_CODE, AuthConstant.INVALID_USER_ERROR));
		}

		/*
		 * if (adminUserEntity == null) {
		 * logger.error("User data not found for given userId : Invalid User"); throw
		 * new DateParsingException( new
		 * ServiceError(AuthErrorConstant.INVALID_USER_ERR_CODE,
		 * AuthConstant.INVALID_USER_ERROR)); }
		 */
		if (!adminUserEntity.getUserType().equals(UserRoleByUserType.ADMIN.getUserType())) {
			logger.error("Usertype is not Admin :Operation not allowed");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.OPERATION_NOT_ALLOWED_ERR_CODE,
					AuthConstant.OPERATION_NOT_ALLOWED));
		} else {
			boolean status = true;
			switch (operationType) {
			case AuthConstant.ACTIVE:
				logger.info("Operation Type is :Active");
				if (userEntity.getIsActive()) {
					logger.error("Requested UserId is Already Active");
					throw new DateParsingException(new ServiceError(AuthErrorConstant.USER_ALREADY_ACTIVE_ERR_CODE,
							AuthConstant.USER_ALREADY_ACTIVE));
				}
				status = true;
				break;
			case AuthConstant.DEACTIVE:
				logger.info("Operation Type is :Deactive");
				if (!userEntity.getIsActive()) {
					logger.error("Requested UserId is Already Deactive");
					throw new DateParsingException(new ServiceError(AuthErrorConstant.USER_ALREADY_DEACTIVE_ERR_CODE,
							AuthConstant.USER_ALREADY_DEACTIVE));
				}
				status = false;
				break;
			default:
				logger.error("Invalid Operation Type : Not supported");
				throw new DateParsingException(
						new ServiceError(AuthErrorConstant.INVALID_OPERATION_ERR_CODE, AuthConstant.INVALID_OPERATION));
			}
			response = new MainResponseDTO<CommonResponseDTO>();
			String reason = (userActiveDeactiveRequest.getReason() != null
					? userActiveDeactiveRequest.getReason().toUpperCase()
					: null);
			auditService.auditloginService(userEntity, tokenUserId);
			int result = userRepo.activeDeactiveUser(userId, status, reason, DateUtils.getCurrentLocalDateTime(),
					tokenUserId);
			if (result > 0) {
				logger.info("User Active/Deactive: Success");
				activeDeactiveResponse.setMessage(AuthConstant.ACTIVE_DEACTIVE_SUCCESS);
			} else {
				logger.info("User Active/Deactive: Failed");
				activeDeactiveResponse.setMessage(AuthConstant.ACTIVE_DEACTIVE_FAILED);
			}
		}
		response.setResponse(activeDeactiveResponse);
		response.setResponsetime(DateUtils.getCurrentLocalDateTime());
		logger.info("Returning response of User Active/Deactive");
		return response;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MainResponseDTO<CommonResponseDTO> userForgotPasswordService(MainRequestDTO<ForgotPasswordRequest> request) {

		CommonResponseDTO userResponse = new CommonResponseDTO();
		MainResponseDTO<CommonResponseDTO> response = new MainResponseDTO<CommonResponseDTO>();
		MainResponseDTO<OTPResponse> otpVerificationResponse = null;
		// response = (MainResponseDTO<UserResponse>)
		// AuthUtil.getMainResponseDto(request);
		ForgotPasswordRequest forgotRequest = request.getRequest();
		String userIdFromReq = forgotRequest.getUserId().toUpperCase();
		logger.info("Getting user Data from database on the basis of UserId or mobile number");
		LoginUserEntity userEntity = userRepo.findByUserIdOrMobile(userIdFromReq,
				(userIdFromReq.matches("[0-9]+") ? Long.parseLong(userIdFromReq) : null));
		//code added by girishk to verify otp start.
		if(null != request.getRequest().getOtp() && !request.getRequest().getOtp().isEmpty()) {
			try {
				otpVerificationResponse = verifyOTP(userEntity, request.getRequest().getOtp());
			} catch (Exception e) {
				logger.error("Exception while calling verifyOTP API.");
				e.printStackTrace();
				throw new DateParsingException(new ServiceError(AuthErrorConstant.UNABLE_TO_CONNECT_OTP_VERIFICATION_SERVICE, AuthConstant.UNABLE_TO_CONNECT_OTP_VERIFICATION_SERVICE));
			}
			if(!otpVerificationResponse.isStatus()) {
				logger.error("OTP verification failed..");
				throw new DateParsingException(new ServiceError(AuthErrorConstant.OTP_VERIFICATION_FAILED, AuthConstant.OTP_VERIFICATION_FAILED));
			}
		}else {
			logger.error("Invalid OTP value");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.INVALID_OTP, AuthConstant.INVALID_OTP));
		}
		//code added by girishk to verify otp end.
		checkIsValidUser(userEntity);
		// LoginUserEntity userEntity = userRepo.findByEmail(emailId);
		String userId = userEntity.getUserId();
		logger.info("Generating random Password");
		String newRandomPassword = RandomStringUtil.randomAlphaNumeric(10, "LGN-PWD");
		// call to email service.
		String hashRandomPassword = null;
		if (passHashFlag) {
			hashRandomPassword = HMACUtils.hash(newRandomPassword.getBytes(StandardCharsets.UTF_8));
		} else {
			hashRandomPassword = newRandomPassword;
		}
		// audit before change password
		auditService.auditloginService(userEntity, userId);
		int updateStatus = userRepo.setRandomPassword(userId, DateUtils.getCurrentLocalDateTime(), hashRandomPassword);
		if (updateStatus > 0) {
			// saveUserToHistory(userRepo.findByUserId(userId), "RESET");
			logger.info("User Random Password saved : Success");
			logger.info("Calling Email notification API");
			NotifyRequestDTO restRequest = new NotifyRequestDTO();
			restRequest.setEmailId(userEntity.getEmail());
			restRequest.setPassword(newRandomPassword);
			restRequest.setTemplateType(AuthConstant.FORGOT_PASS_TEMPLATE);
			restRequest.setUserId(userId);
			restRequest.setMobileNo(userEntity.getMobile());
			restRequest.setSendType(null != userEntity.getEmail() && !userEntity.getEmail().isEmpty() ? AuthConstant.NOTIFY_BOTH_SEND_TYPE : "sms");
			logger.info("Request Created for calling Email and SMS notification for sending Random Password");
			if(null != restRequest.getEmailId() && !restRequest.getEmailId().isEmpty())//added by girishk
			{
				if (sendRandomPassByEmail(restRequest)) {
					logger.info("Random Password Email sent successfully");
					userResponse.setMessage(AuthConstant.EMAIL_SEND_SUCCESS);
					response.setResponse(userResponse);
					response.setStatus(true);
				}
			}
		}
		logger.info("Returning response of User forgot password");
		return response;
	}

	private boolean sendRandomPassByEmail(NotifyRequestDTO restRequest) {
		MainRequestDTO<NotifyRequestDTO> mainRequest = new MainRequestDTO<NotifyRequestDTO>();
		ResponseEntity<MainResponseDTO<NotifyResponseDTO>> responseEntity = null;
		mainRequest.setId(AuthConstant.API_ID);
		mainRequest.setVersion(AuthConstant.API_VERSION);
		mainRequest.setMethod(AuthConstant.API_METHOD);
		mainRequest.setRequesttime(
				Date.from(DateUtils.getCurrentLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()));
		mainRequest.setRequest(restRequest);

		try {
			responseEntity = (ResponseEntity<MainResponseDTO<NotifyResponseDTO>>) RestCallUtil.postApiRequest(emailUrl,
					mainRequest, MainResponseDTO.class);
		} catch (Exception e) {
			logger.error("Exception Occurred while calling Email Notitification Service :: " + e.getMessage());
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.TECHNICAL_ERR_CODE, AuthConstant.TECHNICAL_EXCEPTION));
		}
		MainResponseDTO<NotifyResponseDTO> mainResponse = responseEntity.getBody();
		if (mainResponse.isStatus()) {
			return true;
		} else {
			logger.error("Email Notification : FAILED");
			if (!mainResponse.getErrors().isEmpty() || mainResponse.getErrors() != null) {
				throw new DateParsingException(new ServiceError(mainResponse.getErrors().get(0).getErrorCode(),
						mainResponse.getErrors().get(0).getMessage()));
			} else {
				logger.info("No Error Messages Present ,Email Notification : FAILED");
				throw new DateParsingException(
						new ServiceError(AuthErrorConstant.TECHNICAL_ERR_CODE, AuthConstant.TECHNICAL_EXCEPTION));
			}

		}

	}

	@Override
	@Transactional(rollbackFor = { SQLException.class, DateParsingException.class })
	public MainResponseDTO<CommonResponseDTO> updateUserDetailsRequest(
			MainRequestDTO<UpdateUserDetailsRequest> request) {
		UpdateUserDetailsRequest updateUserDetails = request.getRequest();
		validateRequestData(updateUserDetails);
		CommonResponseDTO commonResponseDTO = new CommonResponseDTO();
		MainResponseDTO<CommonResponseDTO> response = null;
		String userIdFromReq = updateUserDetails.getUserId().toUpperCase();
		logger.info("Getting user Data from database on the basis of userId");
		LoginUserEntity userEntity = userRepo.findByUserId(userIdFromReq);
		checkIsValidUser(userEntity);

		// audit before data update
		LoginUserEntity auditEntity = new LoginUserEntity();
		BeanUtils.copyProperties(userEntity, auditEntity);

		if (!EmptyCheckUtility.isNullEmpty(updateUserDetails.getMobileNumber())
				&& userRepo.existsByMobileAndUserIdNot(updateUserDetails.getMobileNumber(), userIdFromReq)) {
			logger.error("given Mobile Number already registered with other user");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.USERID_ALREADY_EXISTS_CODE, AuthConstant.MOBILE_ALREADY_EXISTS));
		}
		if (!EmptyCheckUtility.isNullEmpty(updateUserDetails.getEmail())
				&& userRepo.existsByEmailAndUserIdNot(updateUserDetails.getEmail().toUpperCase(), userIdFromReq)) {
			logger.error("given Email id already registered with other user");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.USERID_ALREADY_EXISTS_CODE, AuthConstant.EMAIL_ALREADY_EXISTS));
		}

		boolean updateFlag = false;

		if (!EmptyCheckUtility.isNullEmpty(updateUserDetails.getMobileNumber())) {
			userEntity.setMobile(updateUserDetails.getMobileNumber());
			updateFlag = true;
		}

		if (!EmptyCheckUtility.isNullEmpty(updateUserDetails.getEmail())) {
			userEntity.setEmail(updateUserDetails.getEmail().toUpperCase());
			updateFlag = true;
		}
		if (!EmptyCheckUtility.isNullEmpty(updateUserDetails.getUserFullName())) {
			userEntity.setUserFullName(updateUserDetails.getUserFullName().toUpperCase());
			updateFlag = true;
		}

		if (!EmptyCheckUtility.isNullEmpty(updateUserDetails.getSmcNumber())) {
			userEntity.setSmcNumber(updateUserDetails.getSmcNumber().toUpperCase());
			updateFlag = true;
		}
		if (!EmptyCheckUtility.isNullEmpty(updateUserDetails.getMciNumber())) {
			userEntity.setMciNumber(updateUserDetails.getMciNumber().toUpperCase());
			updateFlag = true;
		}
		if (updateFlag) {
			userEntity.setModifiedBy(userIdFromReq);
			userEntity.setModifiedTime(DateUtils.getCurrentLocalDateTime());

			logger.info("Updating user data in user details table");
			try {
				auditService.auditloginService(auditEntity, userIdFromReq);
				if (userRepo.saveAndFlush(userEntity) != null) {
					commonResponseDTO.setMessage(AuthConstant.UPDATE_USR_DETAILS_SUCCESS);
				}
			} catch (Exception e) {
				logger.error("Exception occured while updating user details in database : " + e.getMessage());
				throw new DateParsingException(
						new ServiceError(AuthErrorConstant.TECHNICAL_ERR_CODE, AuthConstant.TECHNICAL_EXCEPTION));
			}
		} else {
			logger.error("No details present in Request for Updation");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.UPDATE_DTLS_NOT_PRESENT_ERR_CODE,
					AuthConstant.UPDATE_DTLS_NOT_PRESENT));
		}
		response = new MainResponseDTO<CommonResponseDTO>();
		response.setResponse(commonResponseDTO);
		logger.info("Returning response of update user details");
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyCaptcha(String captchaValue, String sessionId) {
		logger.info("Captcha Verification Api called");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("sessionId", sessionId);
		requestHeaders.add("captchaValue", captchaValue);
		requestHeaders.add("flagValue", "true");
		HttpEntity<?> request = new HttpEntity<>(requestHeaders);

		MainResponseDTO<CaptchaResponseDto> mainResponse = restTemplate.postForObject(captchaUrl, request,
				MainResponseDTO.class);
		logger.info("captcha responsee received");
		if (mainResponse.isStatus()) {
			logger.info("captcha verification : SUCCESS");
			return true;
		} else {
			if (!mainResponse.getErrors().isEmpty() || mainResponse.getErrors() != null) {
				logger.info("captcha verification : FAILED");
				throw new DateParsingException(new ServiceError(mainResponse.getErrors().get(0).getErrorCode(),
						mainResponse.getErrors().get(0).getMessage()));
			}
		}
		return false;
	}
	
	@Override
	public MainResponseDTO<OTPResponse> getUserDetailsAndSendOTP(UserDetailsRequest userDetailsRequest) {
		MainResponseDTO<OTPResponse> response = null;
		try {
			String userIdFromReq = userDetailsRequest.getUserId().toUpperCase();
			LoginUserEntity userEntity = userRepo.findByUserIdOrMobile(userIdFromReq, (userIdFromReq.matches("[0-9]+") ? Long.parseLong(userIdFromReq) : null));
			generateOTP(userDetailsRequest, userEntity);
		}catch(Exception e) {
			logger.error("Exception while getting user details to send OTP");
			e.printStackTrace();
		}
		return response;
	}
	
	@SuppressWarnings({ "unchecked" })
	private MainResponseDTO<OTPResponse> generateOTP(UserDetailsRequest userDetailsRequest , LoginUserEntity userEntity) throws Exception {
		MainRequestDTO<OtpRequestDTO> mainRequest = new MainRequestDTO<>();
		OtpRequestDTO otpRequestDTO = new OtpRequestDTO();
		otpRequestDTO.setEmailID(userEntity.getEmail());
		otpRequestDTO.setMobileNo(String.valueOf((userEntity.getMobile())));
		otpRequestDTO.setOtpFor(userDetailsRequest.getOtpFor());
		otpRequestDTO.setOtpGenerateTpye("same");
		otpRequestDTO.setSendType("sms");
		otpRequestDTO.setUserId(userEntity.getUserId());
		mainRequest.setRequest(otpRequestDTO);
		logger.info("Calling generate OTP service");
		ResponseEntity<MainResponseDTO<OTPResponse>> response = (ResponseEntity<MainResponseDTO<OTPResponse>>) RestCallUtil.postApiRequest(generateOtpURL, mainRequest, MainResponseDTO.class);
		logger.info("Called generate OTP service: status is : " + response.getBody().isStatus());
		return response.getBody();
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private MainResponseDTO<OTPResponse> verifyOTP(LoginUserEntity userEntity, String otp) throws Exception {
		logger.info("Request received for verifying OTP");
		MainRequestDTO<VerifyOTPRequest> mainRequest = new MainRequestDTO<>();
		VerifyOTPRequest verifyOTPRequest = new VerifyOTPRequest();
		verifyOTPRequest.setUserId(userEntity.getUserId());
		verifyOTPRequest.setMobileOTP(otp);
		verifyOTPRequest.setEmailOTP(otp);
		verifyOTPRequest.setUserRole(userEntity.getRoleEntity().getRoleName().toLowerCase());
		mainRequest.setRequest(verifyOTPRequest);
		logger.info("prepare otpRequest to give REST-Call to OTP manager service to verify OTP");
		ResponseEntity<MainResponseDTO<OTPResponse>> response = (ResponseEntity<MainResponseDTO<OTPResponse>>) RestCallUtil.postApiRequest(verifyOtpURL, mainRequest, MainResponseDTO.class);
		logger.info("Called verify OTP service: status is : " + response.getBody().isStatus());
		return response.getBody();
	}

}
