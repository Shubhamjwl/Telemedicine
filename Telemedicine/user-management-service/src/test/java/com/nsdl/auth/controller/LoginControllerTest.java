package com.nsdl.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.BasicTokenDto;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.CreateUserRequest;
import com.nsdl.auth.dto.ForgotPasswordRequest;
import com.nsdl.auth.dto.LoginRequest;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.RefreshTokenRequestDTO;
import com.nsdl.auth.dto.ResetPasswordRequest;
import com.nsdl.auth.dto.UpdateUserDetailsRequest;
import com.nsdl.auth.dto.UserActiveDeactiveRequestDTO;
import com.nsdl.auth.dto.UserResponse;
import com.nsdl.auth.entity.TokenEntity;
import com.nsdl.auth.repository.TokenRepo;
import com.nsdl.auth.service.TokenService;
import com.nsdl.auth.service.impl.LoginServiceImpl;
import com.nsdl.auth.utility.TokenGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {

	public MockMvc mockMvc;

	@Autowired
	public WebApplicationContext context;

	@Autowired
	public ObjectMapper mapper;

	@MockBean
	private LoginServiceImpl loginService;

	@Mock
	public LoginController loginController;

	@MockBean
	public TokenService tokenService;

	@MockBean
	TokenGenerator tokenGenerator;

	@MockBean
	public TokenRepo tokenRepo;

	public String authToken = null;
	public String token = null;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		ReflectionTestUtils.setField(loginController, "tokenFlag", true);
		ReflectionTestUtils.setField(loginController, "tokenExpiry", 86400000);

		authToken = "Authorization=BearereyJhbGciOiJIUzUxMiJ9"
				+ ".eyJzdWIiOiJTQVlBTEkyNSIsInJvbGUiOiJET0NUT1IiLCJpYXQiOjE2MDQ0OTI4MzEsImV4cCI6MTYwNDQ5NjQzMX0"
				+ ".l_Xk5nCuoeuAsuvFro9Kk7X0d4Noa9xdR7cAn1W6VDWfTnnd2il9r-xlHsbZ2Q-RXj56MWtZs52ggjL4-vuXoQ;"
				+ " Max-Age=3600000; Expires=Wed, 16-Dec-2020 04:27:11 GMT; Path=/; Secure; HttpOnly";
		
		token="BearereyJhbGciOiJIUzUxMiJ9"
				+ ".eyJzdWIiOiJTQVlBTEkyNSIsInJvbGUiOiJET0NUT1IiLCJpYXQiOjE2MDQ0OTI4MzEsImV4cCI6MTYwNDQ5NjQzMX0"
				+ ".l_Xk5nCuoeuAsuvFro9Kk7X0d4Noa9xdR7cAn1W6VDWfTnnd2il9r-xlHsbZ2Q-RXj56MWtZs52ggjL4-vuXoQ";
		
	}

	 @Test
	public void createUserPositiveTest() throws Exception {

		MainResponseDTO<UserResponse> mainUserExpectedResponse = new MainResponseDTO<>();
		mainUserExpectedResponse.setStatus(true);
		UserResponse userResponse = new UserResponse();
		userResponse.setMessage(AuthConstant.USER_CREATE_SUCCESS);
		userResponse.setUserId("MOSIP");
		userResponse.setRole("PATIENT");
		mainUserExpectedResponse.setResponse(userResponse);

		MainRequestDTO<CreateUserRequest> mainRequest = new MainRequestDTO<>();
		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setUserFullName("server test user");
		createUserRequest.setUserId("MOSIP1234");
		createUserRequest.setPassword("Pas@12345");
		createUserRequest.setMobileNumber(9100023221l);
		createUserRequest.setEmail("test@gmail.com");
		createUserRequest.setUserType("PATIENT");

		mainRequest.setRequest(createUserRequest);
		String jsonStr = mapper.writeValueAsString(mainRequest);
		Mockito.when(loginService.createUserService(Mockito.any(MainRequestDTO.class)))
				.thenReturn(mainUserExpectedResponse);
		MvcResult mvcResult = mockMvc
				.perform(post("/login/createuser").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<UserResponse> actualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<UserResponse>>() {
				});

		assertThat(actualResponse.getResponse()).isEqualToIgnoringGivenFields(mainUserExpectedResponse.getResponse());
	}

	 @Test
	public void userLoginPositiveTest() throws Exception {

		MainResponseDTO<UserResponse> mainResponseExpected = new MainResponseDTO<>();
		UserResponse userResponse = new UserResponse();
		userResponse.setUserId("MOSIP");
		userResponse.setRole("PATIENT");
		userResponse.setMessage(AuthConstant.USERPWD_SUCCESS_MESSAGE);
		userResponse.setToken(authToken);
		mainResponseExpected.setResponse(userResponse);
		mainResponseExpected.setStatus(true);

		MainRequestDTO<LoginRequest> mainRequest = new MainRequestDTO<>();
		LoginRequest loginReq = new LoginRequest();
		loginReq.setUserId("MOSIP");
		loginReq.setPassword("Pas@12345");
		mainRequest.setId("user");
		mainRequest.setRequest(loginReq);

		BasicTokenDto basicTokenDto = new BasicTokenDto();
		basicTokenDto.setAuthToken(authToken);
		basicTokenDto.setExpiryTime(new Date(System.currentTimeMillis() + 86400000));

		String jsonStr = mapper.writeValueAsString(mainRequest);
		Mockito.when(loginService.verifyCaptcha(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.when(loginService.userLoginService(mainRequest)).thenReturn(mainResponseExpected);
		Mockito.when(tokenGenerator.basicGenerate(Mockito.anyString(), Mockito.anyString())).thenReturn(basicTokenDto);
		Mockito.doNothing().when(tokenService).StoreToken(Mockito.any(BasicTokenDto.class));

		MvcResult mvcResult = mockMvc
				.perform(post("/login/signin").content(jsonStr).header("sessionId", Mockito.anyString())
						.header("captchaValue", Mockito.anyString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<UserResponse> actualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<UserResponse>>() {
				});
		assertThat(actualResponse.getResponse()).isEqualToIgnoringGivenFields(mainResponseExpected.getResponse());
	}

	 @Test
	public void userLogoutPositiveTest() throws Exception {

		MainResponseDTO<CommonResponseDTO> mainResponseExpected = new MainResponseDTO<>();
		mainResponseExpected.setStatus(true);
		CommonResponseDTO userResponse = new CommonResponseDTO();
		userResponse.setMessage(AuthConstant.USER_LOGOUT_SUCCESS);
		mainResponseExpected.setResponse(userResponse);

		MainRequestDTO<ForgotPasswordRequest> mainRequest = new MainRequestDTO<>();
		ForgotPasswordRequest logoutRequest = new ForgotPasswordRequest();
		logoutRequest.setUserId("MOSIP");
		mainRequest.setRequest(logoutRequest);
		String jsonStr = mapper.writeValueAsString(mainRequest);
		Mockito.when(loginService.userLogoutService(Mockito.anyString())).thenReturn(mainResponseExpected);

		MvcResult mvcResult = mockMvc
				.perform(post("/login/signout").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		
		String jsonResponse = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<CommonResponseDTO> mainResponseActual = mapper.readValue(jsonResponse,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});

		assertThat(mainResponseActual.getResponse()).isEqualToIgnoringGivenFields(userResponse);
	}

	@Test
	public void refreshTokenPositiveTest() throws Exception {

		BasicTokenDto basicTokenDto = new BasicTokenDto();

		basicTokenDto.setAuthToken(token);
		basicTokenDto.setExpiryTime(new Date(System.currentTimeMillis() + 86400000));
		//basicTokenDto.setRefreshToken(authToken);
		basicTokenDto.setUserId("MOSIP");

		RefreshTokenRequestDTO refreshTokenRequest = new RefreshTokenRequestDTO();
		refreshTokenRequest.setUserId("MOSIP");

		String requestStr = mapper.writeValueAsString(refreshTokenRequest);

		Mockito.when(tokenService.refreshToken(Mockito.anyString(), Mockito.anyString())).thenReturn(basicTokenDto);

		MvcResult mvcResult = mockMvc.perform(post("/login/refreshToken").header("Authorization", token)
				.content(requestStr).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String jsonResponse = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<BasicTokenDto> mainResponseActual = mapper.readValue(jsonResponse,
				new TypeReference<MainResponseDTO<BasicTokenDto>>() {
				});
		assertThat(mainResponseActual.getResponse()).isEqualToIgnoringGivenFields(basicTokenDto);
	}

	 @Test
	public void validateTokenPositiveTest() throws Exception {

		boolean expectedResult = true;
		TokenEntity tokenEntityResonseActual = new TokenEntity();
		tokenEntityResonseActual.setActive(true);
		tokenEntityResonseActual.setAuthToken(authToken);
		tokenEntityResonseActual.setCrBy("Jeevan");
		tokenEntityResonseActual.setCreatedTime(LocalDateTime.now());
		tokenEntityResonseActual.setCreatedTime(LocalDateTime.now());
		tokenEntityResonseActual.setExpirationTime(new Date());
		tokenEntityResonseActual.setUpdBy("Jeevan");
		tokenEntityResonseActual.setUpdTime(LocalDateTime.now());
		tokenEntityResonseActual.setUserId("Jeevan");

		Mockito.when(tokenRepo.findByAuthToken(Mockito.anyString())).thenReturn(tokenEntityResonseActual);

		MvcResult mvcResult = mockMvc.perform(
				get("/login/validateToken").param("authToken", authToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		Boolean actualResponse = mapper.readValue(response, Boolean.class);
		assertThat(actualResponse).isEqualTo(expectedResult);
	}

	@Test
	public void userPasswordResetPositiveTest() throws Exception {
		MainResponseDTO<CommonResponseDTO> expectedResponse = new MainResponseDTO<>();
		CommonResponseDTO userResponse = new CommonResponseDTO();
		userResponse.setMessage("Password successfully reset`");
		expectedResponse.setResponse(userResponse);

		MainRequestDTO<ResetPasswordRequest> mainRequest = new MainRequestDTO<>();
		ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
		resetPasswordRequest.setUserId("MOSIP");
		resetPasswordRequest.setOldPwd("Pass12!@");
		resetPasswordRequest.setNewPwd("12!@Pass");
		resetPasswordRequest.setConfirmPwd("12!@Pass");
		mainRequest.setRequest(resetPasswordRequest);

		Mockito.when(loginService.verifyCaptcha(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.when(loginService.userPasswordResetService(mainRequest)).thenReturn(expectedResponse);

		MvcResult mvcResult = mockMvc.perform(post("/login/resetPassword")
				.content(mapper.writeValueAsString(mainRequest)).header("sessionId", Mockito.anyString())
				.header("captchaValue", Mockito.anyString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<CommonResponseDTO> actualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});

		assertThat(actualResponse.getResponse()).isEqualToComparingFieldByField(expectedResponse.getResponse());
	}

	 @Test
	public void userActiveDeactivePositiveTest() throws Exception {

		MainResponseDTO<CommonResponseDTO> mainResponseExpected = new MainResponseDTO<>();
		CommonResponseDTO userResponse = new CommonResponseDTO();
		userResponse.setMessage(AuthConstant.ACTIVE_DEACTIVE_SUCCESS);
		mainResponseExpected.setResponse(userResponse);

		MainRequestDTO<UserActiveDeactiveRequestDTO> mainRequest = new MainRequestDTO<>();
		UserActiveDeactiveRequestDTO userActiveDeactiveRequestDTO = new UserActiveDeactiveRequestDTO();
		userActiveDeactiveRequestDTO.setUserId("MOSIP");
		userActiveDeactiveRequestDTO.setOperationType("ACTIVE");
		mainRequest.setRequest(userActiveDeactiveRequestDTO);

		Mockito.when(loginService.userActiveDeactiveService(mainRequest)).thenReturn(mainResponseExpected);

		MvcResult mvcResult = mockMvc.perform(post("/login/userActiveDeactive")
				.content(mapper.writeValueAsString(mainRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<CommonResponseDTO> actualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});

		assertThat(actualResponse.getResponse()).isEqualToIgnoringGivenFields(mainResponseExpected.getResponse());

	}

	 @Test
	public void userForgotPasswordPositiveTest() throws Exception {
		MainResponseDTO<CommonResponseDTO> mainResponseExpected = new MainResponseDTO<>();
		CommonResponseDTO userResponse = new CommonResponseDTO();
		userResponse.setMessage(AuthConstant.EMAIL_SEND_SUCCESS);
		mainResponseExpected.setResponse(userResponse);

		MainRequestDTO<ForgotPasswordRequest> mainRequest = new MainRequestDTO<>();
		ForgotPasswordRequest forgotPasswordRequestDTO = new ForgotPasswordRequest();
		forgotPasswordRequestDTO.setUserId("MOSIP");
		mainRequest.setRequest(forgotPasswordRequestDTO);

		Mockito.when(loginService.userForgotPasswordService(Mockito.any(MainRequestDTO.class)))
				.thenReturn(mainResponseExpected);
		String jsonStr = mapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc
				.perform(post("/login/forgotPassword").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<CommonResponseDTO> actualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});
		assertThat(actualResponse.getResponse()).isEqualToComparingFieldByField(mainResponseExpected.getResponse());

	}

	 @Test
	public void updateuserDetailsTest() throws JsonProcessingException, Exception {

		MainRequestDTO<UpdateUserDetailsRequest> mainRequest = new MainRequestDTO<UpdateUserDetailsRequest>();
		UpdateUserDetailsRequest updateUserDetailsRequest = new UpdateUserDetailsRequest();
		updateUserDetailsRequest.setUserId("SYSTEM123");
		updateUserDetailsRequest.setEmail("Test@gmail.com");
		updateUserDetailsRequest.setMobileNumber(7654321009l);

		mainRequest.setRequest(updateUserDetailsRequest);

		MainResponseDTO<CommonResponseDTO> mainResponseExpected = new MainResponseDTO<CommonResponseDTO>();
		CommonResponseDTO userResponse = new CommonResponseDTO();
		userResponse.setMessage("User Details Updated Successfully");
		mainResponseExpected.setResponse(userResponse);

		Mockito.when(loginService.updateUserDetailsRequest(mainRequest)).thenReturn(mainResponseExpected);

		MvcResult mvcResult = mockMvc.perform(post("/login/updateUserDetails")
				.content(mapper.writeValueAsString(mainRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<CommonResponseDTO> responseWrapperActual = mapper.readValue(resultContent,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});
		assertThat(responseWrapperActual.getResponse()).isEqualToComparingFieldByField(userResponse);
	}
}
