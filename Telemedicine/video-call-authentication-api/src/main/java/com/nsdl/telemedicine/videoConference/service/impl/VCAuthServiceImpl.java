package com.nsdl.telemedicine.videoConference.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.videoConference.constant.AuthConstant;
import com.nsdl.telemedicine.videoConference.constant.ErrorConstants;
import com.nsdl.telemedicine.videoConference.dto.BasicTokenDto;
import com.nsdl.telemedicine.videoConference.dto.MainRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.MainResponseDTO;
import com.nsdl.telemedicine.videoConference.dto.UserDTO;
import com.nsdl.telemedicine.videoConference.dto.VCAutRequestDTO;
import com.nsdl.telemedicine.videoConference.dto.VCAuthResponseDTO;
import com.nsdl.telemedicine.videoConference.dto.VcTokenResponse;
import com.nsdl.telemedicine.videoConference.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.videoConference.entity.LoginUserEntity;
import com.nsdl.telemedicine.videoConference.entity.TokenEntity;
import com.nsdl.telemedicine.videoConference.entity.VcAuthToken;
import com.nsdl.telemedicine.videoConference.exception.DateParsingException;
import com.nsdl.telemedicine.videoConference.exception.ServiceError;
import com.nsdl.telemedicine.videoConference.logger.LoggingClientInfo;
import com.nsdl.telemedicine.videoConference.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.videoConference.repository.AuthRepo;
import com.nsdl.telemedicine.videoConference.repository.VcAuthRepository;
import com.nsdl.telemedicine.videoConference.repository.VcRepository;
import com.nsdl.telemedicine.videoConference.service.VcAuthService;
import com.nsdl.telemedicine.videoConference.utility.AuthUtil;
import com.nsdl.telemedicine.videoConference.utility.EmptyCheckUtility;
import com.nsdl.telemedicine.videoConference.utility.VcTokenGenerator;
import com.nsdl.telemedicine.videoConference.utility.VcTokenValidator;

import io.jsonwebtoken.ExpiredJwtException;


@LoggingClientInfo
@Service
public class VCAuthServiceImpl implements VcAuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(VCAuthServiceImpl.class);

	@Autowired
	private UserDTO userDetails;
	
	@Autowired
	private VcRepository vcRepository;
	
	@Autowired
	AppointmentDtlsRepository appRepo;
	
	@Autowired
	AuthRepo authRepo;
	@Autowired
	VcAuthRepository vcAuthRepo;
	
	@Value("${videoConference.serice.videoconf.secretKey}")
	private String secretKey;

	@Value("${videoConference.url}")
	private String videoConferenceUrl;
	
	@Autowired
	VcTokenGenerator tokenGenerator;
	
	@Autowired
	private VcTokenValidator tokenValidator;
	
	@Value("${videoConference.url.encode.string}")
	private String urlEncodeString;
	
	@SuppressWarnings("unchecked")
	@Override
	public  MainResponseDTO<VCAuthResponseDTO> generateAuthToken(@Valid MainRequestDTO<VCAutRequestDTO> vcAuthRequest) {
		logger.info("Inside generateVC token method...++++"+vcAuthRequest.getRequest().getApptId());
		MainResponseDTO<VCAuthResponseDTO> response = null;
		TokenEntity loggedInTokenDetails=null;
		VCAuthResponseDTO vcResponse = new VCAuthResponseDTO();
		VCAutRequestDTO request = vcAuthRequest.getRequest();
		VcAuthToken newTokenDtls= null;
		BasicTokenDto basicTokenDto = null;
		String loggedInUser=userDetails.getUserName().trim();
		LoginUserEntity userEntity = vcRepository.findByUserIdIgnoreCase(loggedInUser);	
	//	checkIsValidUser(userEntity);
		//Check appointment Date and time
		AppointmentDtlsEntity appointmentDtlsEntity = appRepo.findByAdApptId(request.getApptId());
		checkIsAppointmentValid(appointmentDtlsEntity);
		logger.info("Requested time to start Video Confrenceing is valid");
		//Check token of user in db
		loggedInTokenDetails=authRepo.findByUserIdIgnoreCase(loggedInUser);
		isTokenExist(loggedInTokenDetails);	
		try 
		{
			logger.info("Inside method of token generation+++++");
			basicTokenDto = tokenGenerator.basicGenerate(userDetails.getUserName(),userDetails.getRole(),request.getApptId(),userDetails.getUserName());
		
		}
		catch (Exception e) 
		{
			logger.info("Exception occurred while creating token:"+e.getMessage());	
			throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
		}
		if(basicTokenDto!=null)
		{
			 newTokenDtls= new VcAuthToken();
			 newTokenDtls.setApptId(request.getApptId());	
			 newTokenDtls.setUserId(userDetails.getUserName());
			 newTokenDtls.setUserRole(userDetails.getRole());
			 newTokenDtls.setAuthToken(basicTokenDto.getAuthToken());
			 newTokenDtls.setCreatedBy(userDetails.getUserName());
			 newTokenDtls.setExpireTime(basicTokenDto.getExpiryTime());
			 newTokenDtls.setCreatedTime(LocalDateTime.now());
			 saveNewTokenDetails(newTokenDtls);
			 logger.info("Token Detail Saved SuccessFully!!");	
			 String msg = videoConferenceUrl + request.getApptId()+urlEncodeString+newTokenDtls.getAuthToken();
			 vcResponse.setSuccess(true);
			 vcResponse.setMessage(msg);
				
		}
		else
		{
			vcResponse.setMessage(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage());
			vcResponse.setSuccess(false);
		}
		//callRestApi(vcResponse);
		
		//code added by girishk to capture start time of appointment start.
		try {
			if (userDetails.getRole().equalsIgnoreCase("doctor") || userDetails.getRole().equalsIgnoreCase("scribe")) {
				appointmentDtlsEntity.setAdApptStartTime(java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
				appRepo.save(appointmentDtlsEntity);
			}
		} catch (Exception e) {
			logger.info("Exception occurred while capturing appointment start time :" + e.getMessage());
		}
		//code added by girishk to capture start time of appointment end.
		response = (MainResponseDTO<VCAuthResponseDTO>) AuthUtil.getMainResponseDto(vcAuthRequest);
		response.setStatus(true);
		response.setResponse(vcResponse);
		return response;	
}
	
	

	
	public void saveNewTokenDetails(VcAuthToken newTokenDtls) {
		logger.info("Inside method of save token details...++++=");
	//	VcAuthToken user = checkUser(newTokenDtls.getUserId());
		/*
		 * if (user != null) { Date expirationTime = newTokenDtls.getExpireTime(); try {
		 * vcAuthRepo.updateToken(newTokenDtls.getAuthToken(), user.getUserId(),
		 * LocalDateTime.now(),expirationTime);
		 * logger.info("Video call token details Updated Successfully!!"); }
		 * catch(Exception e) {
		 * logger.info("Exception occurred while insert record in vcauth_token"+e.
		 * getMessage()); throw new DateParsingException(new
		 * ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),ErrorConstants
		 * .UNABLE_TO_GENERATE_TOKEN.getMessage())); }
		 * 
		 * } else {
		 */
			try
			{
				vcAuthRepo.save(newTokenDtls);
				logger.info("Video call token details Saved Successfully!!");
			}
			catch(Exception e)
			{
				logger.info("Exception occurred while insert record in vcauth_token"+e.getMessage());
				throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(),ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
			}
			
		//}
		
	}

	public VcAuthToken checkUser(String userId) {
		return vcAuthRepo.findByUserIdIgnoreCase(userId.trim());
	}

	/**
	 * @param loggedInTokenDetails
	 * To check if logged in user token exist in database
	 */
	private void isTokenExist(TokenEntity loggedInTokenDetails) {
		if (loggedInTokenDetails == null) {
			logger.error("Bearer Toekn not found for given user in database");
			throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(), ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
		}
		
	}
	
	
	/**
	 * @param appointmentDtlsEntity
	 * to check whether appointment date and time is valid or not
	 */
	private void checkIsAppointmentValid(AppointmentDtlsEntity appointmentDtlsEntity) {
		LocalDate appointmentDate = null;
		String slotTime = null;
		
		if (appointmentDtlsEntity == null) {
			logger.error("Appointment Details not found for given user");
			throw new DateParsingException(new ServiceError(ErrorConstants.APPOINTMENT_NOT_FOUND.getCode(), ErrorConstants.APPOINTMENT_NOT_FOUND.getMessage()));
		}
		else 
		{
			appointmentDate = appointmentDtlsEntity.getAdApptDateFk();
			slotTime = appointmentDtlsEntity.getAdApptSlotFk();
			logger.info("Appointment date is:" + appointmentDate + "and slot is:" + slotTime);
			if((EmptyCheckUtility.isNullEmpty(appointmentDate)) || (EmptyCheckUtility.isNullEmpty(slotTime)))
			{
				logger.info("Appointment Date/Slot time not found in the database");
				throw new DateParsingException(new ServiceError(ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getCode(), ErrorConstants.UNABLE_TO_GENERATE_TOKEN.getMessage()));
			}
			/*else if(!isValidDateTime(appointmentDate,slotTime))
			{
				logger.info("Appointment Date/Time is not valid to start Video call");
				throw new DateParsingException(new ServiceError(ErrorConstants.INVALID_APPOINTMENT.getCode(), ErrorConstants.INVALID_APPOINTMENT.getMessage()));
			}*/
			
		}
		
	}
	
	/**
	 * @param appointmentDate
	 * @param slotTime
	 * @return
	 * 
	 */
	public static boolean isValidDateTime(LocalDate appointmentDate,String slotTime)
	{
		boolean isValidTime=false;
		String startTime=null;
		String endTime=null;
		String[] startAndEndTime = slotTime.split(AuthConstant.DASH);
		if(startAndEndTime.length>=2)
		{
			startTime=startAndEndTime[0];
			endTime=startAndEndTime[1];
		}
		
		LocalTime startTimeDt = LocalTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_TIME);
	    LocalTime endTimeDt = LocalTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_TIME);
	    LocalTime curr_time = LocalTime.now();
	    LocalDate curr_Date=LocalDate.now();
	   	isValidTime=((curr_Date.isEqual(appointmentDate))?((curr_time.isAfter(startTimeDt) && curr_time.isBefore(endTimeDt))?true:false):false);
		return isValidTime;
		
	}
	
	@Override
	public MainResponseDTO<VcTokenResponse> verifyAuthToken(String authToken) {
		MainResponseDTO<VcTokenResponse> response = new MainResponseDTO<>();
		VcTokenResponse vcResponse = new VcTokenResponse();
		String apptId=null;
		String tokenUserId=null;
		Date expDate=null;
		logger.info("Token Validation starts for S2S..");
		 logger.info("Token Received From Server: "+authToken);
		//Check if token  exist in db
		 checkIfValidToken(authToken);
		 logger.info("Token Status in DB: Available");
		 logger.info("Trying to read claimset from token...");
		//Extract information from token
		try 
		{
				tokenValidator.parseJWT(authToken);
				logger.info("Token Verified With Signature");
				//tokenUserName=tokenValidator.getUsernameFromToken(authToken);
				tokenUserId=tokenValidator.getUserDetailsFromToken(authToken);
				apptId=tokenValidator.getApptIdFromToken(authToken);
				expDate=tokenValidator.getExpirationDateFromToken(authToken);
		} 
		catch (Exception e) 
		{
			
			if(e instanceof ExpiredJwtException)
			{
				logger.info("Token Expired: "+e.getMessage());
				throw new DateParsingException(new ServiceError(ErrorConstants.TOKEN_EXPIRED.getCode(), ErrorConstants.TOKEN_EXPIRED.getMessage()));
			}
			
			
			logger.info("Exception occurred while Reading Claimset from token"+e.getMessage());	
			e.printStackTrace();
			throw new DateParsingException(new ServiceError(ErrorConstants.VERIFICATION_FAIL.getCode(), ErrorConstants.VERIFICATION_FAIL.getMessage()));
		}
		 logger.info("claimset Read from token successfully!!");
		//Check token expired
		validateTokenExpiry(expDate);
		//check if user valid
		LoginUserEntity userEntity = vcRepository.findByUserIdIgnoreCase(tokenUserId);	
		checkIsValidUser(userEntity);
		logger.info("User details verified from WToken obtained from Server");
		//Check Appointment date and time is valid
		AppointmentDtlsEntity appointmentDtlsEntity = appRepo.findByAdApptId(apptId);
		checkIsAppointmentValid(appointmentDtlsEntity);
		logger.info("Token obtained from Server is valid!!!");
		vcResponse.setMessage(AuthConstant.TOKEN_VALID);
		vcResponse.setSuccess(true);
		response.setResponse(vcResponse);
		return response;
			}



	
	public void validateTokenExpiry(Date expDate) {
		if(isTokenExpire(expDate))
		{
			logger.info("Token Expired!!");
			throw new DateParsingException(new ServiceError(ErrorConstants.TOKEN_EXPIRED.getCode(), ErrorConstants.TOKEN_EXPIRED.getMessage()));
		}
		
	}


	private void checkIfValidToken(String authToken) {
		VcAuthToken token = vcAuthRepo.findByAuthToken(authToken.trim());
		if(token==null)
		{
			logger.info("Token not found in database");
			throw new DateParsingException(new ServiceError(ErrorConstants.INVALID_TOKEN.getCode(), ErrorConstants.INVALID_TOKEN.getMessage()));
		}
		
	}


	/**
	 * @param userEntity
	 * To check user is valid or not (Active & Logged in)
	 */
	private void checkIsValidUser(LoginUserEntity userEntity) {
		if (userEntity == null) {
			logger.error("User data not found for given user : Invalid User");
			throw new DateParsingException(new ServiceError(ErrorConstants.INVALID_USER_ERROR.getCode(), ErrorConstants.INVALID_USER_ERROR.getMessage()));
		}
		else if (!userEntity.getIsActive()) 
		{
			logger.error("User status is not Active : InActive User");
			throw new DateParsingException(new ServiceError(ErrorConstants.USER_NOT_ACTIVE.getCode(), ErrorConstants.USER_NOT_ACTIVE.getMessage()));
		}
		 else if (!userEntity.getIsLoggedIn())
		 {
				logger.error("User Not Logged in");
				throw new DateParsingException(new ServiceError(ErrorConstants.USER_NOT_LOGIN.getCode(), ErrorConstants.USER_NOT_LOGIN.getMessage()));
		}
	}


	public boolean isTokenExpire(Date token) {
		LocalDateTime curr_time = LocalDateTime.now();
		LocalDateTime token_time=convertToLocalDateTime(token);
		boolean isExpire=false;
		isExpire=(token_time.isBefore(curr_time))?true:false;
		return isExpire;
	}
	
	
	public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime())
			      .atZone(ZoneId.systemDefault())
			      .toLocalDateTime();
	}


	@Override
	public boolean validateToken(VCAutRequestDTO vCAutRequestDTO) {
		VcAuthToken token = vcAuthRepo.findByAuthToken(vCAutRequestDTO.getToken());
		if (token != null && !isTokenExpire(token.getExpireTime())) {
			return true;
		}
		return false;
	}




}
