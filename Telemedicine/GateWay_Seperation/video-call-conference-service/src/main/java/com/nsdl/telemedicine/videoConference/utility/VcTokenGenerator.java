package com.nsdl.telemedicine.videoConference.utility;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.nsdl.telemedicine.videoConference.dto.BasicTokenDto;
import com.nsdl.telemedicine.videoConference.dto.TimeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.minidev.json.JSONObject;

@Component
public class VcTokenGenerator {

	@Value("${auth.jwt.secret}")
	private String jwtSecret;

	
	@Value("${auth.jwt.expiry}")
	private Long tokenExpiry;

	private static final Logger logger = LoggerFactory.getLogger(VcTokenGenerator.class);

	
	public BasicTokenDto basicGenerate(String userId, String role, String appId, String userName, String email) throws GeneralSecurityException, IOException {
		BasicTokenDto basicTokenDto = new BasicTokenDto();
		// ObjectMapper mapper = new ObjectMapper();
		
		 // create a JSON object
		 JSONObject user=new JSONObject();
		 user.put("id",userId);
		 user.put("name", userName);
		 user.put("email", email);
		 user.put("role", role); 
	   	 logger.info("User:"+user);
		 JSONObject context=new JSONObject();
		 context.put("user", user);
		 System.out.println("Context:"+context);
		 Claims claims = Jwts.claims().setIssuer("telemedCV").setSubject("*").setAudience("telemed.cloudvoice.in");
		if(role.equalsIgnoreCase("DOCTOR") || role.equalsIgnoreCase("SCRIBE"))
		{
			claims.put("moderator", true);
		}
		else
		{
			claims.put("moderator", false);
		}
		
		claims.put("algorithm", SignatureAlgorithm.RS256);
		claims.put("room", appId);
		claims.put("context", context);
		TimeToken token = getToken(claims);
		basicTokenDto.setAuthToken(token.getToken());
		basicTokenDto.setExpiryTime(new Date(token.getExpTime()));
		basicTokenDto.setUserId(userId);
		return basicTokenDto;
	}
	
	private TimeToken getToken(Claims claims) throws GeneralSecurityException, IOException {
		TimeToken timeToken = new TimeToken();
		long exptime = 0;
		long currentTimeInMs = System.currentTimeMillis();
		Date currentDate = new Date(currentTimeInMs);
		
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setClaims(claims).setIssuedAt(currentDate)
				.signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes("UTF-8") );

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

}
