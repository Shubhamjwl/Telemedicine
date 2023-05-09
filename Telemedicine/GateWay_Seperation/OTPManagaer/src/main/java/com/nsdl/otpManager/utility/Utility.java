package com.nsdl.otpManager.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nsdl.otpManager.constant.AppConstant;
import com.nsdl.otpManager.dto.MainRequestDTO;
import com.nsdl.otpManager.dto.MainResponseDTO;
import com.nsdl.otpManager.enumeration.TemplateType;

@Component
public class Utility  {

	
	static long miliSecond=60*1000;
	
	
	
	/**Return system current Timestamp
	 * @return
	 * @throws Exception
	 */
	public static Timestamp getCurrentTimestamp()  {
		Timestamp timestamp=new Timestamp(new Date().getTime());
		return timestamp;
	}
	
	
	/**Add minutes to given timestamp and return new Timestamp
	 * @param OTPValidDuration
	 * @return
	 * @throws Exception
	 */
	public static Timestamp getOTPExpireTime(int OTPValidDuration) 
	{
		long validDurationInMS=OTPValidDuration*miliSecond;
		long newTimestamp=getCurrentTimestamp().getTime()+validDurationInMS;
		return new Timestamp(newTimestamp);
	}
	
	public static boolean stringIsNullOrEmpty(String str) {

		if (str == null|| str.equals(AppConstant.NULL)) {
			return true;
		} else {
			str = str.trim().replaceAll("\\s+", AppConstant.SPACE);
			if (str.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	 
		public static String getEncodedPwd(String otp)
		{
			 BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
			 String encodeOTP=passwordEncoder.encode(otp);
			 return encodeOTP;
		}
	
		public static boolean isOTPExpire(Timestamp expireTime ) throws Exception
		{
			int diff = getCurrentTimestamp().compareTo(expireTime);
			return((diff>0)?true:false);
		}
		
		public static boolean isPwdMatched(String oldPwd,String newPwd)
		{
			
			  BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
			  if(!passwordEncoder.matches(oldPwd, newPwd)) 
			  {
			 	  return false; 
			  }
			 
			return true;
		
		}
		
		public static String getNewOTP(Integer OTPLength, String otpString)  
	    { 
	             StringBuilder sb = new StringBuilder(OTPLength);
			for (int i = 0; i < OTPLength; i++) { 
	            int index = (int)(otpString.length() * Math.random()); 
	            sb.append(otpString.charAt(index)); 
	        } 
	       return sb.toString(); 
	       
	    }
		
		public static LocalDateTime getCurrentLocalDateTime() {
			return LocalDateTime.now();
		}
		
		public static MainResponseDTO<?> getMainResponseDto(MainRequestDTO<?> mainRequestDto ){
			MainResponseDTO<?> response=new MainResponseDTO<>();
			if(mainRequestDto.getRequest()==null) {
				return response;
			}
			response.setId(mainRequestDto.getId());
			response.setVersion(mainRequestDto.getVersion());
			response.setResponsetime(getCurrentLocalDateTime());		
			return response;
		}
		
		
		public static String getTemplateTypeName(String tempType)
		{
			String templateName=AppConstant.BLANK;
			if(tempType.equalsIgnoreCase(AppConstant.FORGOT))
			{
				templateName = TemplateType.FORGOT.templateTypeName();
			}
		
		  else if(tempType.equalsIgnoreCase(AppConstant.CHANGE)) 
		  { 
			  templateName =TemplateType.CHANGE.templateTypeName();
		  }
		 
			else if(tempType.equalsIgnoreCase(AppConstant.REGISTRATION))
			{
				templateName = TemplateType.REG.templateTypeName();
				
			}
			else if(tempType.equalsIgnoreCase(AppConstant.REJECT))
			{
				templateName = TemplateType.REJECT.templateTypeName();
				
			}
			else if(tempType.equalsIgnoreCase(AppConstant.VERIFY))
			{
				templateName = TemplateType.VERIFY.templateTypeName();
				
			}
			return templateName;
		
			
		}
		
}
