package com.nsdl.telemedicine.consultancy.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	
}
