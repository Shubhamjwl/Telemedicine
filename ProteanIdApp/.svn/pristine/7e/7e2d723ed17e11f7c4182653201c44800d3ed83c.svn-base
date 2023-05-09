package com.nsdl.authenticate.utils;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Utility {
	
	static long miliSecond=60*1000;

	public static Timestamp getCurrentTimestamp() {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		return timestamp;
	}
	
	public static Timestamp getOTPExpireTime(int OTPValidDuration) 
	{
		long validDurationInMS=OTPValidDuration*miliSecond;
		long newTimestamp=getCurrentTimestamp().getTime()+validDurationInMS;
		return new Timestamp(newTimestamp);
	}
	
	public static boolean isOTPExpire(Timestamp expireTime ) throws Exception
	{
		int diff = getCurrentTimestamp().compareTo(expireTime);
		return((diff>0)?true:false);
	}

}
