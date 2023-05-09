package com.nsdl.telemedicine.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.nsdl.telemedicine.gateway.security.AuthenticationManager;
import com.nsdl.telemedicine.gateway.security.SecurityContextRepository;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Autowired
	private SecurityContextRepository securityContextRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	protected SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
		return http.cors().and().exceptionHandling().authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> {
			swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		})).accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> {
			swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
		})).and().csrf().disable().authenticationManager(authenticationManager)
				.securityContextRepository(securityContextRepository).authorizeExchange()
				.pathMatchers("/login/signin", "/login/validateToken",
						"/login/createuser","/roleFunction/getAllRoleFunctionsMapping", "/login/resetPassword",
						"/DoctorRegistration/v1/saveDoctorDetailsTele", "/patientRegistration/registration",
						"/login/forgotPassword", "/appointment/getCountOfConsultation",
						"/DoctorRegistration/v1/getCountOfDoctors", "/v1/getCountOfSpeciality",
						"/login/updateUserDetails", "/login/signoutWithoutToken","/review/v1/getNumberOfLikesToDoctor","/review/v1/getNumberOfCommentsToDoctor",
						"/validateServerToken","/captcha/generateCaptcha","/captcha/verifyCaptcha","/masterManagement/getMasterDetailsListByMasterName",
						"/masterManagement/getStateList","/masterManagement/getCityList","/masterManagement/getMasterList","/OTPManager/sendNotification","/OTPManager/generateOtp",
						"/OTPManager/checkUniqueValue","/OTPManager/verifyOTP","/OTPManager/sendInvitationLink","/masterManagement/getCountOfSpeciality"
						,"/login/getUserDetailsAndSendOTP")
				.permitAll().pathMatchers(HttpMethod.OPTIONS).permitAll().anyExchange().authenticated().and().build();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
