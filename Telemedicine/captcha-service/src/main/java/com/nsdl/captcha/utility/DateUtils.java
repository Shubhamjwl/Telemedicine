package com.nsdl.captcha.utility;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

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
}
