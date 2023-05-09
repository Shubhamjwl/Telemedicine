package com.nsdl.telemedicine.gateway.security;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.nsdl.telemedicine.gateway.builder.TokenValidator;
import com.nsdl.telemedicine.gateway.config.AuthProperties;
import com.nsdl.telemedicine.gateway.config.GatewayConfig;
import com.nsdl.telemedicine.gateway.dto.FunctionDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationManager.class);

	@Autowired
	private TokenValidator tokenValidator;

	@Autowired
	private AuthProperties authProperties;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GatewayConfig gatewayConfig;

	@Override
	@SuppressWarnings("unchecked")
	public Mono authenticate(Authentication authentication) {
		String authHeader = authentication.getCredentials().toString();
		String request = authentication.getPrincipal().toString();
		String username = null;
		String authToken = null;
		String role = null;

		if (authHeader != null && authHeader.startsWith(authProperties.getJwtProperties().getBase())) {
			authToken = authHeader.replace(authProperties.getJwtProperties().getBase(), "");
			logger.info("Token Assigned To User : " + authToken);
			try {
				username = tokenValidator.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
			} catch (SignatureException e) {
				logger.error("Authentication Failed. Username or Password not valid.");
			}
		} else {
			logger.warn("couldn't find bearer string, will ignore the header.");
		}

		if (username != null) {
			logger.info("userName: " + username);
			String validateTokenUrl = authProperties.getToken().getCustomUrl() + "/validateToken";
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(validateTokenUrl).queryParam("authToken",
					authHeader);
			ResponseEntity<Boolean> response = restTemplate.getForEntity(builder.toUriString(), Boolean.class);
			logger.info(" userName: " + username + "Is tokern present: " + response);
			logger.info("Response for Token present in database : " + response.getBody());

			role = tokenValidator.getRoleFromToken(authToken);
			//boolean functionValidated = validateFunctionality(request, role);
			
			boolean functionValidated = true;

			boolean tockenExpiry = tokenValidator.validateExpiry(authToken);
			logger.info("Token expiry status :" + tockenExpiry + " userName: " + username);

			if (response.getBody() && tockenExpiry && functionValidated) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, username,
						Arrays.asList(new SimpleGrantedAuthority(role)));
				logger.info("authenticated user " + username + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(auth);
				return Mono.just(auth);
			}
			return Mono.empty();
		} else {
			return Mono.empty();
		}
	}

	private boolean validateFunctionality(String req, String role) {
		logger.info("Request URI: " + req);
		List<FunctionDetails> functions = gatewayConfig.getRoleFunctionMap().get(role);
		if (functions.size() == 0) {
			logger.info("No function is assigned for current user");
			return false;
		}
		// Map<String, String> functionUriMap = functionIdUriMap.getFunctionUriMap();
		if (!checkIfFunctionPresent(functions, req)) {
			logger.info("OPERATION NOT ASSIGNED TO YOUR ROLE");
			return false;
		}
		return true;
	}

	private boolean checkIfFunctionPresent(final List<FunctionDetails> functions, final String functionUri) {
		return functions.stream().filter(o -> o.getFunctionUrl().equalsIgnoreCase(functionUri)).findFirst().isPresent();
	}

}
