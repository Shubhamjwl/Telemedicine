package com.nsdl.auth.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nsdl.auth.dto.BasicTokenDto;
import com.nsdl.auth.dto.TimeToken;

import java.util.Date;

@Component
public class TokenGenerator {

	@Value("${auth.jwt.secret}")
	private String jwtSecret;

	@Value("${auth.jwt.base}")
	private String tokenBase;

	@Value("${auth.jwt.expiry}")
	private Long tokenExpiry;

	public BasicTokenDto basicGenerate(String userId, String role) {
		BasicTokenDto basicTokenDto = new BasicTokenDto();
		Claims claims = Jwts.claims().setSubject(userId);
		claims.put("role", role);
		TimeToken token = getToken(claims);
		basicTokenDto.setAuthToken(token.getToken());
		basicTokenDto.setExpiryTime(new Date(token.getExpTime()));
		basicTokenDto.setUserId(userId);
		return basicTokenDto;
	}

	private TimeToken getToken(Claims claims) {
		TimeToken timeToken = new TimeToken();
		long exptime = 0;

		long currentTimeInMs = System.currentTimeMillis();
		Date currentDate = new Date(currentTimeInMs);

		JwtBuilder builder = Jwts.builder().setClaims(claims).setIssuedAt(currentDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret);

		if (tokenExpiry >= 0) {
			exptime = currentTimeInMs + tokenExpiry;
			builder.setExpiration(new Date(exptime));
		}
		timeToken.setToken(tokenBase.concat(builder.compact()));
		timeToken.setExpTime(exptime);
		return timeToken;
	}

}
