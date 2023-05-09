package com.nsdl.ndhm.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CommonUtils {
	CommonUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String getTime() {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		format1.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format1.format(new Date());
	}
}
