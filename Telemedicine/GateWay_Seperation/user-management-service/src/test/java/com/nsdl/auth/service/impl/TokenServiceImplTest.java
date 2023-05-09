package com.nsdl.auth.service.impl;


import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.nsdl.auth.dto.BasicTokenDto;
import com.nsdl.auth.entity.TokenEntity;
import com.nsdl.auth.repository.TokenRepo;

@RunWith(SpringRunner.class)
public class TokenServiceImplTest {
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@MockBean
	public TokenRepo tokenRepo;
	
	String authToken = null;
	
	@Before
	public void setUp() throws ParseException {
		
		authToken = "Authorization=BearereyJhbGciOiJIUzUxMiJ9"
				+ ".eyJzdWIiOiJTQVlBTEkyNSIsInJvbGUiOiJET0NUT1IiLCJpYXQiOjE2MDQ0OTI4MzEsImV4cCI6MTYwNDQ5NjQzMX0"
				+ ".l_Xk5nCuoeuAsuvFro9Kk7X0d4Noa9xdR7cAn1W6VDWfTnnd2il9r-xlHsbZ2Q-RXj56MWtZs52ggjL4-vuXoQ;"
				+ " Max-Age=3600000; Expires=Wed, 16-Dec-2020 04:27:11 GMT; Path=/; Secure; HttpOnly";
	}
	
	@Test
	public void StoreTokenPositiveTest() {
		
		String userId = "MOSIP";
		TokenEntity entity = new TokenEntity();
		entity.setUserId("MOSIP");
		
		BasicTokenDto token = new BasicTokenDto();
		token.setAuthToken(authToken);
		token.setExpiryTime(new Date());
		token.setRefreshToken("refresh");
		token.setUserId(userId);
		Mockito.when(tokenRepo.findByUserId(userId)).thenReturn(entity);
		Date expirationTime = token.getExpiryTime();
		Mockito.when(tokenRepo.updateToken(authToken, expirationTime, entity.getUserId(), LocalDateTime.now())).thenReturn(1);
		
		assertThat(tokenRepo.updateToken(authToken, expirationTime, entity.getUserId(), LocalDateTime.now())).isEqualTo(1);
	}
	
	@Test
	public void refreshTokenPositiveTest() {
		
		
		
	}

}
