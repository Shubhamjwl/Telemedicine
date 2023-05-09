/*package com.nsdl.auth.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.CreateUserRequest;
import com.nsdl.auth.dto.ForgotPasswordRequest;
import com.nsdl.auth.dto.LoginRequest;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.NotifyRequestDTO;
import com.nsdl.auth.dto.ResetPasswordRequest;
import com.nsdl.auth.dto.UpdateUserDetailsRequest;
import com.nsdl.auth.dto.UserActiveDeactiveRequestDTO;
import com.nsdl.auth.dto.UserResponse;
import com.nsdl.auth.entity.LoginUserEntity;
import com.nsdl.auth.entity.RoleMasterEntity;
import com.nsdl.auth.entity.UserHistory;
import com.nsdl.auth.repository.LoginUserRepo;
import com.nsdl.auth.repository.RoleServiceRepository;
import com.nsdl.auth.repository.TokenRepo;
import com.nsdl.auth.repository.UserHistoryRepo;
import com.nsdl.auth.service.AuditService;
import com.nsdl.auth.utility.CommonValidationUtil;
import com.nsdl.auth.utility.DateUtils;
import com.nsdl.auth.utility.HMACUtils;
import com.nsdl.telemedicine.gateway.config.UserDTO;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoginServiceImpl.class})
public class LoginServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	public LoginServiceImpl loginService;

	@Mock
	public AuditService auditService;

	@Mock
	public LoginUserRepo userRepo;

	@Mock
	private TokenRepo tokenRepo;

	@MockBean
	private CommonValidationUtil validate;

	@Mock
	public RoleServiceRepository userRoleRepo;

	@Mock
	private UserHistoryRepo userHistoryRepo;

	UserDTO userDetails = new UserDTO();

	LoginUserEntity entity = new LoginUserEntity();
	UserHistory userHistory = new UserHistory();

	@Before
	public void setUp() throws ParseException {
		ReflectionTestUtils.setField(loginService, "passHashFlag", true);
		ReflectionTestUtils.setField(loginService, "userPassExpiryDuration", 10080l);
		ReflectionTestUtils.setField(loginService, "allowedValidationCount", 3l);
		ReflectionTestUtils.setField(loginService, "userFreezedTime", "120");

		userDetails.setUserName("SYS123");
		ReflectionTestUtils.setField(loginService, "userDetails", userDetails);

		entity.setUserId("MOSIP");
		entity.setUserFullName("MOSIP NSDL");
		entity.setMobile(7984483756l);
		entity.setEmail("TEST@GMAIL.COM");
		entity.setSmcNumber("SMC1234");
		entity.setMciNumber("MCI1234");
		entity.setFailAttemptCount(0l);
		entity.setPwdExpiryTime(LocalDateTime.now().plusMinutes(10080l));
		entity.setPassword(HMACUtils.hash("Pass12!@".getBytes(StandardCharsets.UTF_8)));
		//System.out.println("password is" + HMACUtils.hash("Pass123!@#".getBytes(StandardCharsets.UTF_8)));
		entity.setUserType("PATIENT");
		entity.setCreatedBy("MOSIP");
		entity.setCreatedTime(DateUtils.getCurrentLocalDateTime());
		entity.setModifiedTime(DateUtils.getCurrentLocalDateTime());
		entity.setIsActive(true);
		entity.setIsLock(false);
		entity.setIsLoggedIn(false);

		RoleMasterEntity masterEntity = new RoleMasterEntity();
		masterEntity.setID(101);
		masterEntity.setRoleName("PATIENT");
		entity.setRoleEntity(masterEntity);

		userHistory.setUserId(entity.getUserId());
		userHistory.setPassword(entity.getPassword());
		userHistory.setCreatedTime(entity.getCreatedTime());
		userHistory.setCreatedBy(entity.getCreatedBy());
	}

	 @Test
	public void createUserServicePositiveTest() {

		MainRequestDTO<CreateUserRequest> mainRequest = new MainRequestDTO<>();
		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setEmail("test@gmail.com");
		createUserRequest.setMobileNumber(7984483756l);
		createUserRequest.setPassword("Pass12!@");
		createUserRequest.setUserFullName("Mosip Nsdl");
		createUserRequest.setUserId("mosip");
		createUserRequest.setUserType("PATIENT");
		mainRequest.setRequest(createUserRequest);

		RoleMasterEntity expectedMasterEntity = new RoleMasterEntity();
		expectedMasterEntity.setID(101);
		expectedMasterEntity.setRoleName("PATIENT");

		Mockito.when(userRepo.existsByUserId(Mockito.anyString())).thenReturn(false);
		Mockito.when(userRepo.existsByUserType(Mockito.anyString())).thenReturn(false);
		Mockito.when(userRepo.existsByMobile(Mockito.anyLong())).thenReturn(false);
		Mockito.when(userRepo.existsByEmail(Mockito.anyString())).thenReturn(false);
		Mockito.when(auditService.auditloginService(Mockito.any(LoginUserEntity.class), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(userRoleRepo.findByRoleName(Mockito.anyString())).thenReturn(expectedMasterEntity);
		Mockito.when(userRepo.saveAndFlush(Mockito.any(LoginUserEntity.class))).thenReturn(entity);
		Mockito.when(userHistoryRepo.saveAndFlush(userHistory)).thenReturn(userHistory);
		UserResponse expectedUserResponse = new UserResponse();
		expectedUserResponse.setMessage(AuthConstant.USER_CREATE_SUCCESS);
		expectedUserResponse.setRole("PATIENT");
		expectedUserResponse.setUserId("MOSIP");
		MainResponseDTO<UserResponse> loginUserEntityActual = loginService.createUserService(mainRequest);

		assertThat(loginUserEntityActual.getResponse()).isEqualTo(expectedUserResponse);
	}

	 @Test
	public void userLoginServicePositiveTest() {
		MainRequestDTO<LoginRequest> request = new MainRequestDTO<>();
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserId("MOSIP");
		loginRequest.setPassword("Pass12!@");
		request.setRequest(loginRequest);
		Mockito.when(userRepo.findByUserIdOrMobile(Mockito.anyString(), Mockito.any())).thenReturn(entity);
		// Mockito.when(userRepo.findByUserId(loginRequest.getUserId())).thenReturn(entity);
		Mockito.when(auditService.auditloginService(Mockito.any(LoginUserEntity.class), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(userRepo.freezeUser(Mockito.anyString(), Mockito.any(LocalDateTime.class))).thenReturn(2);
		Mockito.when(userRepo.unFreezeUser(Mockito.anyString(), Mockito.any(LocalDateTime.class))).thenReturn(2);
		Mockito.when(userRepo.updateValidationAttemptCount(Mockito.anyString(), Mockito.any(LocalDateTime.class)))
				.thenReturn(2);
		Mockito.when(auditService.auditloginService(Mockito.any(LoginUserEntity.class), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(userRepo.userLoggedIn(Mockito.anyString(), Mockito.any(LocalDateTime.class))).thenReturn(2);

		UserResponse userResponse = new UserResponse();
		userResponse.setMessage(AuthConstant.USERPWD_SUCCESS_MESSAGE);
		userResponse.setRole("PATIENT");
		userResponse.setUserId("MOSIP");
		MainResponseDTO<UserResponse> mainResponseActual = loginService.userLoginService(request);
		assertThat(mainResponseActual.getResponse()).isEqualTo(userResponse);
	}

	 @Test
	public void userLogoutServicePositiveTest() {
		LoginUserEntity entity = new LoginUserEntity();
		entity.setIsLoggedIn(true);
		entity.setIsActive(true);
		entity.setUserId("MOSIP");
		Mockito.when(userRepo.findByUserId(Mockito.anyString())).thenReturn(entity);
		Mockito.when(auditService.auditloginService(Mockito.any(LoginUserEntity.class), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(userRepo.userLoggedOut(Mockito.anyString(), Mockito.any(LocalDateTime.class), Mockito.anyString()))
				.thenReturn(2);
		Mockito.when(tokenRepo.deleteByUserId(Mockito.anyString())).thenReturn(2);

		CommonResponseDTO userResponseExpected = new CommonResponseDTO();
		userResponseExpected.setMessage(AuthConstant.USER_LOGOUT_SUCCESS);
		assertThat(loginService.userLogoutService("MOSIP").getResponse()).isEqualTo(userResponseExpected);
	}

	 @Test
	public void userPasswordResetServicePositiveTest() {

		MainRequestDTO<ResetPasswordRequest> request = new MainRequestDTO<>();
		ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
		resetPasswordRequest.setUserId("mosip");
		resetPasswordRequest.setOldPwd("Pass12!@");
		resetPasswordRequest.setNewPwd("Pass123!@#");
		resetPasswordRequest.setConfirmPwd("Pass123!@#");
		request.setRequest(resetPasswordRequest);

		LoginUserEntity userEntity = new LoginUserEntity();
		userEntity.setIsLock(false);
		Mockito.when(userRepo.findByUserId(Mockito.anyString())).thenReturn(entity);

		List<UserHistory> listOfUserEntities = new ArrayList<>();
		UserHistory userHistory = new UserHistory();
		userHistory.setId(101);
		userHistory.setPassword("12345678");
		userHistory.setUserId("mosip");
		listOfUserEntities.add(userHistory);

		Mockito.when(userHistoryRepo.checkLastThreePassword(Mockito.anyString(),Mockito.anyLong())).thenReturn(listOfUserEntities);
		Mockito.when(auditService.auditloginService(Mockito.any(LoginUserEntity.class), Mockito.anyString()))
				.thenReturn(true);

		Mockito.when(userRepo.resetPassword(Mockito.anyString(), Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class), Mockito.anyString())).thenReturn(1);
		Mockito.when(tokenRepo.deleteByUserId(Mockito.anyString())).thenReturn(1);
		Mockito.when(userRepo.findByUserId(Mockito.anyString())).thenReturn(entity);
		Mockito.when(userHistoryRepo.saveAndFlush(Mockito.any(UserHistory.class))).thenReturn(userHistory);

		CommonResponseDTO userResponse = new CommonResponseDTO();
		userResponse.setMessage(AuthConstant.PWD_RESET_SUCCESS);

		MainResponseDTO<CommonResponseDTO> mainResponseActual = loginService.userPasswordResetService(request);
		assertThat(mainResponseActual.getResponse()).isEqualTo(userResponse);
	}

	 @Test
	public void userActiveDeactiveServicePositiveTest() {
		LoginUserEntity adminEntity = new LoginUserEntity();

		adminEntity.setIsLock(false);
		adminEntity.setIsActive(true);
		adminEntity.setUserId("MOSIP");
		adminEntity.setUserType("ADMIN");

		MainRequestDTO<UserActiveDeactiveRequestDTO> mainRequest = new MainRequestDTO<UserActiveDeactiveRequestDTO>();
		UserActiveDeactiveRequestDTO userActiveDeactiveRequestDTO = new UserActiveDeactiveRequestDTO();
		userActiveDeactiveRequestDTO.setOperationType("DEACTIVE");
		userActiveDeactiveRequestDTO.setReason("Malicious Activity detected");
		userActiveDeactiveRequestDTO.setUserId("MOSIP");

		mainRequest.setRequest(userActiveDeactiveRequestDTO);

		CommonResponseDTO expectedActiveDeactiveResponse = new CommonResponseDTO();
		expectedActiveDeactiveResponse.setMessage(AuthConstant.ACTIVE_DEACTIVE_SUCCESS);

		Mockito.when(userRepo.findByUserId(Mockito.anyString())).thenReturn(adminEntity);
		Mockito.when(auditService.auditloginService(Mockito.any(LoginUserEntity.class), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(userRepo.activeDeactiveUser(Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyString(),
				Mockito.any(LocalDateTime.class), Mockito.anyString())).thenReturn(1);
		assertThat(loginService.userActiveDeactiveService(mainRequest).getResponse())
				.isEqualTo(expectedActiveDeactiveResponse);
	}

	@Test
	public void userForgotPasswordServicePositiveTest() throws Exception {
		MainRequestDTO<ForgotPasswordRequest> mainRequest = new MainRequestDTO<ForgotPasswordRequest>();
		ForgotPasswordRequest forgotRequest = new ForgotPasswordRequest();
		forgotRequest.setUserId("MOSIP");
		mainRequest.setRequest(forgotRequest);
		Mockito.when(userRepo.findByUserIdOrMobile(Mockito.anyString(), Mockito.any())).thenReturn(entity);
		Mockito.when(auditService.auditloginService(Mockito.any(LoginUserEntity.class), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(
				userRepo.setRandomPassword(Mockito.anyString(), Mockito.any(LocalDateTime.class), Mockito.anyString()))
				.thenReturn(1);
		LoginServiceImpl loginServiceImplSpy = PowerMockito.spy(loginService);

		PowerMockito.doReturn(true).when(loginServiceImplSpy, "sendRandomPassByEmail",
				ArgumentMatchers.any(NotifyRequestDTO.class));
		CommonResponseDTO expectedResponse = new CommonResponseDTO();
		expectedResponse.setMessage(AuthConstant.EMAIL_SEND_SUCCESS);

		MainResponseDTO<CommonResponseDTO> mainResponse = loginServiceImplSpy.userForgotPasswordService(mainRequest);
		assertThat(expectedResponse).isEqualTo(mainResponse.getResponse());
	}

	 @Test
	public void updateUserDetailsRequestPositiveTest() {
		CommonResponseDTO expectedResponse = new CommonResponseDTO();
		expectedResponse.setMessage("User Details Updated Successfully");
		LoginUserEntity userEntity = new LoginUserEntity();
		userEntity.setCreatedBy("nsdl");
		userEntity.setCreatedTime(LocalDateTime.now());
		userEntity.setDeRegisterReason("Register");
		userEntity.setEmail("test@mail.com");
		userEntity.setIsLoggedIn(false);
		userEntity.setIsActive(true);
		userEntity.setIsLock(false);
		userEntity.setFailAttemptCount(null);
		userEntity.setModifiedTime(LocalDateTime.now().minusMinutes(50));
		String userId = "SYSTEMUSER123";

		MainRequestDTO<UpdateUserDetailsRequest> mainRequest = new MainRequestDTO<UpdateUserDetailsRequest>();
		UpdateUserDetailsRequest updateUserDetailsRequest = new UpdateUserDetailsRequest();
		updateUserDetailsRequest.setUserId("SYSTEMUSER123");
		updateUserDetailsRequest.setEmail("Test@gmail.com");
		updateUserDetailsRequest.setMobileNumber(7654321009l);
		mainRequest.setRequest(updateUserDetailsRequest);

		Mockito.when(userRepo.findByUserId(Mockito.anyString())).thenReturn(userEntity);
		Mockito.when(userRepo.existsByMobileAndUserIdNot(Mockito.anyLong(), Mockito.anyString())).thenReturn(false);
		Mockito.when(userRepo.existsByEmailAndUserIdNot(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		Mockito.when(auditService.auditloginService(userEntity, userId)).thenReturn(true);
		Mockito.when(userRepo.saveAndFlush(userEntity)).thenReturn(userEntity);
		MainResponseDTO<CommonResponseDTO> responseList = loginService.updateUserDetailsRequest(mainRequest);
		assertThat(expectedResponse).isEqualTo(responseList.getResponse());
	}

}
*/