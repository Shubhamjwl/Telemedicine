package com.nsdl.auth.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.nsdl.auth.logger.LoggingClientInfo;
import com.nsdl.auth.repository.TokenRepo;
import com.nsdl.auth.service.LoginService;
import com.nsdl.auth.service.TokenService;
import com.nsdl.auth.utility.TokenGenerator;

@RestController
@RequestMapping("/login")
@LoggingClientInfo
public class LoginController {

	@Autowired
	LoginService service;

	@Autowired
	TokenGenerator tokenGenerator;

	@Autowired
	TokenService tokenService;

	@Autowired
	TokenRepo tokenRepo;

	@Value("${login.generate.token.flag}")
	boolean tokenFlag;

	@Value("${auth.jwt.expiry}")
	private int tokenExpiry;

	@Value("${auth.token.header}")
	private String authTokenHeader;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@PostMapping("/createuser")
	public ResponseEntity<MainResponseDTO<UserResponse>> createUser(
			@RequestBody @Valid MainRequestDTO<CreateUserRequest> createUserRequest) {
		logger.info("Create user Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(service.createUserService(createUserRequest));
	}

	@PostMapping("/signin")
	public ResponseEntity<MainResponseDTO<UserResponse>> userlogin(
			@RequestHeader(value = "sessionId", required = true) String sessionId,
			@RequestHeader(value = "captchaValue", required = true) String captchaValue,
			@RequestBody @Valid MainRequestDTO<LoginRequest> loginRequest, HttpServletResponse res) {
		logger.info("User Sign in Request Received");
		MainResponseDTO<UserResponse> response = null;
		if (service.verifyCaptcha(captchaValue, sessionId)) {
			response = service.userLoginService(loginRequest);
			if (tokenFlag && response.isStatus()) {
				// rest call to token API
				logger.info("Generating token for user");
				BasicTokenDto basicTokenDto = tokenGenerator.basicGenerate(response.getResponse().getUserId(),
						response.getResponse().getRole());
				if (basicTokenDto != null) {
					logger.info("Token generation : Success");
					// added temporarily - satish
					response.getResponse().setToken(basicTokenDto.getAuthToken());
					Cookie cookie = createCookie(basicTokenDto.getAuthToken(), tokenExpiry);
					res.addCookie(cookie);
					tokenService.StoreToken(basicTokenDto);
					logger.info("Saved generated token in database for given user");
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/signout")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> userlogout(
			@RequestBody @Valid MainRequestDTO<ForgotPasswordRequest> signoutRequest) {
		logger.info("User Sign Out Request Received");
		return ResponseEntity.status(HttpStatus.OK)
				.body(service.userLogoutService(signoutRequest.getRequest().getUserId()));
	}

	@PostMapping(value = "/refreshToken", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<BasicTokenDto>>refreshToken(HttpServletRequest req, HttpServletResponse res,
			@RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
		logger.info("User Refresh Token Request Received");
		String authToken = req.getHeader("Authorization");
		BasicTokenDto basicTokenDto = null;
		MainResponseDTO<BasicTokenDto> mainResponse = new MainResponseDTO<BasicTokenDto>();
		basicTokenDto = tokenService.refreshToken(authToken, refreshTokenRequest.getUserId());
		if (basicTokenDto != null) {
			Cookie cookie = createCookie(basicTokenDto.getAuthToken(), tokenExpiry);
			res.addCookie(cookie);
			mainResponse.setResponse(basicTokenDto);
		}
		return ResponseEntity.status(HttpStatus.OK).body(mainResponse);
	}

	@GetMapping(value = "/validateToken", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> validateToken(@RequestParam(value = "authToken", required = true) String authToken) {
		TokenEntity token = tokenRepo.findByAuthToken(authToken);
		if (token != null) {
			return ResponseEntity.status(HttpStatus.OK).body(true);
		}
		return ResponseEntity.status(HttpStatus.OK).body(false);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> userPasswordReset(
			@RequestHeader(value = "sessionId", required = true) String sessionId,
			@RequestHeader(value = "captchaValue", required = true) String captchaValue,
			@RequestBody @Valid MainRequestDTO<ResetPasswordRequest> resetPasswordRequest) {
		logger.info("User Reset Password Request Received");
		logger.info("Reset password Request is : " + resetPasswordRequest);
		logger.info("sessionId : " + sessionId);
		logger.info("captchaValue : " + captchaValue);
		MainResponseDTO<CommonResponseDTO> response = null;
		if (service.verifyCaptcha(captchaValue, sessionId)) {
			response = service.userPasswordResetService(resetPasswordRequest);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/userActiveDeactive")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> userActiveDeactive(
			@RequestBody MainRequestDTO<UserActiveDeactiveRequestDTO> userActiveDeactiveRequest) {
		logger.info("User Active/deactive request Received");
		return ResponseEntity.status(HttpStatus.OK).body(service.userActiveDeactiveService(userActiveDeactiveRequest));
	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> userForgotPassword(
			@RequestBody @Valid MainRequestDTO<ForgotPasswordRequest> forgotPasswordRequest) {
		logger.info("User forgot Password Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(service.userForgotPasswordService(forgotPasswordRequest));
	}

	private Cookie createCookie(final String content, final int expirationTimeSeconds) {
		final Cookie cookie = new Cookie(authTokenHeader, content);
		cookie.setMaxAge(expirationTimeSeconds);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		return cookie;
	}

	@PostMapping("/updateUserDetails")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> updateuserDetails(
			@RequestBody @Valid MainRequestDTO<UpdateUserDetailsRequest> updateUserDetailsRequest) {
		logger.info("User forgot Password Request Received");
		return ResponseEntity.status(HttpStatus.OK).body(service.updateUserDetailsRequest(updateUserDetailsRequest));
	}
	
	@PostMapping("/signoutWithoutToken")
	public ResponseEntity<MainResponseDTO<CommonResponseDTO>> userlogoutWithoutToken(
			@RequestBody @Valid MainRequestDTO<ForgotPasswordRequest> signoutRequest) {
		logger.info("User Sign Out without token Request Received");
		return ResponseEntity.status(HttpStatus.OK)
				.body(service.userLogoutService(signoutRequest.getRequest().getUserId()));
	}
	
	/**
	 * @param userDetailsRequest
	 * @return
	 * Added by girishk to get user details and send OTP on SMS before forgot/change password.
	 */
	@PostMapping("/getUserDetailsAndSendOTP")
	public ResponseEntity<MainResponseDTO<OTPResponse>> getUserDetailsAndSendOTP(@RequestBody @Valid MainRequestDTO<UserDetailsRequest> userDetailsRequest) {
		logger.info("Request Received to get user details and send OTP before forgot/change password");
		return ResponseEntity.status(HttpStatus.OK).body(service.getUserDetailsAndSendOTP(userDetailsRequest.getRequest()));
	}

}
