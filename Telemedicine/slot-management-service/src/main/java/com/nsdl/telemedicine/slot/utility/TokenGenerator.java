package com.nsdl.telemedicine.slot.utility;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Date;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nsdl.telemedicine.slot.constant.SlotConstant;
import com.nsdl.telemedicine.slot.dto.BasicTokenDto;
import com.nsdl.telemedicine.slot.dto.TimeToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.minidev.json.JSONObject;

@Component
public class TokenGenerator {


	@Value("${auth.jwt.vc.secret}")
	private String jwtSecret;

	
	@Value("${auth.jwt.vc.expiry}")
	private Long tokenExpiry;

	@Value("${envFlag}")
	private String envFlag;
	
	
	private static final Logger logger = LoggerFactory.getLogger(TokenGenerator.class);

	
	public BasicTokenDto basicGenerate(String doctorName, String userName, String role, String speclization, int consultfee) throws GeneralSecurityException, IOException, JSONException {
		BasicTokenDto basicTokenDto = new BasicTokenDto();
		JSONObject user=new JSONObject();
		 user.put("doctorname",doctorName);
		 user.put("userID", userName);
		 user.put("role", role); 
		 user.put("speclization", speclization); 
		 user.put("consultfee", consultfee);
	   	 logger.info("User:"+user);
		 JSONObject context=new JSONObject();
		 context.put("user", user);
		// System.out.println("Context:"+context);
		 Claims claims = Jwts.claims().setIssuer("telemedCV").setSubject("*").setAudience("telemed.cloudvoice.in");
		 claims.put("algorithm", SignatureAlgorithm.RS256);
		claims.put("room", userName);
		claims.put("context", context);
		if(envFlag.equalsIgnoreCase(SlotConstant.DEV_ENV_FLAG_SLOTBOOK))
		{
			claims.put("env", false);	
		}
		claims.put("moderator", true);
		
		TimeToken token = getToken(claims);
		logger.info("Generated Token::"+token.getToken());
		basicTokenDto.setAuthToken(token.getToken());
		basicTokenDto.setExpiryTime(new Date(token.getExpTime()));
		basicTokenDto.setUserId(userName);
		return basicTokenDto;
	}
	
	private TimeToken getToken(Claims claims) throws GeneralSecurityException, IOException {
		logger.info("Inside gettoken method to set alogo and signature");
		TimeToken timeToken = new TimeToken();
		long exptime = 0;
		long currentTimeInMs = System.currentTimeMillis();
		Date currentDate = new Date(currentTimeInMs);
		
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setClaims(claims).setIssuedAt(currentDate)
				.signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes() );

		if (tokenExpiry >= 0) {
			exptime = currentTimeInMs + tokenExpiry;
			builder.setExpiration(new Date(exptime));
		}
		timeToken.setToken(builder.compact());
		timeToken.setExpTime(exptime);
		return timeToken;
	}
	
	public static Key loadPrivateKey(String stored) throws GeneralSecurityException, IOException 
	  {
		  KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
	      SecureRandom random=new SecureRandom();
	      random.setSeed(stored.getBytes());
	      //Initializing the KeyPairGenerator
	      keyPairGen.initialize(2048,random);     
	      //Generating the pair of keys
	      KeyPair pair = keyPairGen.generateKeyPair();
	      return pair.getPrivate();
	   }

	public BasicTokenDto getEncryptedString(String dmdDrName, String userName, String role, String dmdSpecialiazation,
			Integer dmdConsulFee) {
		// TODO Auto-generated method stub
		return null;
	}

}
