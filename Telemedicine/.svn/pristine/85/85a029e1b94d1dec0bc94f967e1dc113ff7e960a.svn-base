package com.nsdl.telemedicine.review.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

	private DateTimeUtil() {
		super();
	}

	public static String formatDateToString(LocalDate date) {
		if (date != null) {
			return date.format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
		}
		return null;
	}
	
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
