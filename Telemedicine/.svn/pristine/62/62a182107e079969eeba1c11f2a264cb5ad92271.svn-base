package com.nsdl.telemedicine.gateway.builder;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nsdl.telemedicine.gateway.config.AuthProperties;
import com.nsdl.telemedicine.gateway.dto.TokenDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenValidator {

	@Autowired
	private AuthProperties authProperties;
	
	public TokenDTO buildTokenDTO(String token) {
		final Claims claims = getClaims(token);
		return new TokenDTO(claims.getSubject(), (String) claims.get("role"), (String) claims.get("ien"),
				(Integer) claims.get("iat"), (Integer) claims.get("exp"));
	}

	private Claims getClaims(String token) {

		Claims claims = Jwts.parser().setSigningKey(authProperties.getJwtProperties().getSecret()).parseClaimsJws(token).getBody();

		return claims;
	}

	public boolean validateExpiry(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			Integer expTime = (Integer) claims.get("exp");
			long currentTime = new Date().getTime();
			long exp = expTime.longValue() * 1000;
			if (expTime != 0 && currentTime < exp) {
				return true;
			}
		}
		return false;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !validateExpiry(token));
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getClaims(token);
		return claimsResolver.apply(claims);
	}

	public String getRoleFromToken(String token) {
		return getClaims(token).get("role").toString();
	}
}
