package com.nsdl.verifier.security;

import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nsdl.verifier.constant.VerifierConstant;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationManager.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${login.jwt.verify.url}")
	private String jwtVerifyUrl;

	@Override
	@SuppressWarnings("unchecked")
	public Mono authenticate(Authentication authentication) {
		String authHeader = authentication.getCredentials().toString();
		String request = authentication.getPrincipal().toString();
		String username = null;
		String authToken = null;
		DecodedJWT decodedJWT = null;

		logger.info("Token Assigned To User : " + authToken);
		try {
			decodedJWT = JWT.decode(authHeader);
			username = decodedJWT.getClaim(VerifierConstant.SUBJECT).asString();
		} catch (IllegalArgumentException e) {
			logger.error("an error occured during getting username from token", e);
		} catch (ExpiredJwtException e) {
			logger.warn("the token is expired and not valid anymore", e);
		} catch (SignatureException e) {
			logger.error("Authentication Failed. Username or Password not valid.");
		}

		if (username != null) {
			logger.info("userName: " + username);
			String validateTokenUrl = jwtVerifyUrl + "/jwtVerify";
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(validateTokenUrl).queryParam("jwtToken",
					authHeader);
			ResponseEntity<Boolean> response = restTemplate.getForEntity(builder.toUriString(), Boolean.class);
			logger.info(" userName: " + username + "Is tokern present: " + response);
			logger.info("Response for Token present in database : " + response.getBody());
			boolean tockenExpiry = validateExpiry(decodedJWT.getClaim(VerifierConstant.EXPIRATION).asLong());
			logger.info("Token expiry status :" + tockenExpiry + " userName: " + username);

			if (response.getBody() && tockenExpiry) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, username,
						Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
				logger.info("authenticated user " + username + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(auth);
				return Mono.just(auth);
			}
			return Mono.empty();
		} else {
			return Mono.empty();
		}
	}

	public boolean validateExpiry(long expTime) {
		long currentTime = new Date().getTime();
		long exp = expTime * 1000;
		if (expTime != 0 && currentTime < exp) {
			return true;
		}

		return false;
	}

}
