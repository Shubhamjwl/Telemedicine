package com.nsdl.telemedicine.videoConference.utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nsdl.telemedicine.videoConference.dto.Context;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class VcTokenValidator {

	
	@Value("${auth.jwt.secret}")
	private String jwtSecret;
	
		
	
	
	
	public Claims parseJWT(String token)
	{
		Claims claims = null;
		
		try {
			
			try {
				claims = Jwts.parser().setSigningKey(jwtSecret.getBytes("UTF-8")).parseClaimsJws(token).getBody();
			} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
			}
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			e.printStackTrace();
		}
		return claims;	
	}

	public boolean validateExpiry(String token) {
		Claims claims = parseJWT(token);
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
		final Claims claims = parseJWT(token);
		return claimsResolver.apply(claims);
	}

	public String getRoleFromToken(String token) {
		return parseJWT(token).get("role").toString();
	}
	
	public String getApptIdFromToken(String token) {
		return parseJWT(token).get("room").toString();
	}
	public String getUserDetailsFromToken(String token) {
	GsonBuilder gsonBuilder = new GsonBuilder();
	Gson gson = gsonBuilder.create();
	String context=(parseJWT(token).get("context").toString());
	System.out.println("Context:+++++++++++"+context);
	Context user = gson.fromJson(context, Context.class);
	return user.getUser().getId();
	
	}
	public static Key loadPublicKey(String stored) throws GeneralSecurityException, IOException 
	  {
		  KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
	      SecureRandom random=new SecureRandom();
	      random.setSeed(stored.getBytes());
	      //Initializing the KeyPairGenerator
	      keyPairGen.initialize(2048,random);     
	      //Generating the pair of keys
	      KeyPair pair = keyPairGen.generateKeyPair();
	      return pair.getPublic();
	   }
}
