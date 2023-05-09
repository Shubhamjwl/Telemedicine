package com.nsdl.telemedicine.slot.utility;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public final class DateUtils {

	public static String getUTCCurrentDateTimeString() {
		return OffsetDateTime.now().toInstant().toString();
	}
	
	public static int timeDifferenceInSeconds(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		return (int) fromDateTime.until(toDateTime, ChronoUnit.SECONDS);
	}
	
	public static int timeDifferenceInhours(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		return (int) fromDateTime.until(toDateTime, ChronoUnit.HOURS);
	}
	
	public static LocalDateTime getCurrentLocalDateTime() {
		return LocalDateTime.now();
	}
	public static LocalDateTime getLocalDateTimeFromDateString(String dateString) {
		try {
			return LocalDateTime.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	public static Date getDateFromDateString(String dateString,String dateFormat) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
			return sdf.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
}
