package com.nsdl.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.BasicTokenDto;
import com.nsdl.auth.entity.LoginUserEntity;
import com.nsdl.auth.entity.TokenEntity;
import com.nsdl.auth.exception.AuthErrorConstant;
import com.nsdl.auth.exception.DateParsingException;
import com.nsdl.auth.exception.ServiceError;
import com.nsdl.auth.repository.LoginUserRepo;
import com.nsdl.auth.repository.TokenRepo;
import com.nsdl.auth.service.TokenService;
import com.nsdl.auth.utility.TokenGenerator;
import com.nsdl.auth.utility.TokenValidator;


@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	TokenRepo tokenRepo;

	@Autowired
	LoginUserRepo idRepo;

	@Autowired
	TokenService tokenService;

	@Autowired
	TokenGenerator tokenGenerator;
	
	@Autowired
	TokenValidator tokenValidator;
	
	@Value("${auth.jwt.base}")
	private String tokenBase;

	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public void StoreToken(BasicTokenDto token) {
		TokenEntity user = checkUser(token.getUserId());
		if (user != null) {
			Date expirationTime = token.getExpiryTime();
			String authToken = token.getAuthToken();
			tokenRepo.updateToken(authToken, expirationTime, user.getUserId(), LocalDateTime.now());
		} else {
			TokenEntity tokenEntity = new TokenEntity();
			tokenEntity.setActive(true);
			tokenEntity.setAuthToken(token.getAuthToken());
			tokenEntity.setCrBy(token.getUserId());
			tokenEntity.setCreatedTime(LocalDateTime.now());
			tokenEntity.setExpirationTime(token.getExpiryTime());
			tokenEntity.setUserId(token.getUserId());
			tokenRepo.save(tokenEntity);
		}
	}

	private TokenEntity checkUser(String userId) {
		return tokenRepo.findByUserId(userId);
	}

	@Override
	public BasicTokenDto refreshToken(String authToken, String userId) {
		// TODO Auto-generated method stub

		BasicTokenDto basicTokenDto = new BasicTokenDto();
		
		String authTokenValidation = authToken.replace(tokenBase, "");
		if(!tokenValidator.getUsernameFromToken(authTokenValidation).equalsIgnoreCase(userId.trim().toUpperCase())) {
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.TOKEN_VALIDATION_FAILED, AuthConstant.TOKEN_VALIDATION_FAILED));
		}
		
		LoginUserEntity userEntity = idRepo.findByUserId(userId.trim().toUpperCase());
		// RoleEntity role = roleRepo.findByUser(user);
		if (userEntity == null) {
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.INVALID_USER_ERR_CODE, AuthConstant.INVALID_USER_ERROR));
		}

		TokenEntity token = tokenRepo.findByAuthToken(authToken);
		if (token != null && userEntity.getIsLoggedIn()) {
			basicTokenDto = tokenGenerator.basicGenerate(userEntity.getUserId(),
					userEntity.getRoleEntity().getRoleName());
			if (basicTokenDto != null) {
				tokenService.StoreToken(basicTokenDto);
				return basicTokenDto;
			}
		}
		return basicTokenDto;
	}

}
