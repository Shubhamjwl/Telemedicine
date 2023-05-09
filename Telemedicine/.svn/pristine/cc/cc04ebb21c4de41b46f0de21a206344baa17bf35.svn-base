package com.nsdl.ndhm.utility;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GenerateOTPUtil {

	public String otp(int len) {
		String numbers = "0123456789";
		Random randomMethod = new Random();

		char[] otp = new char[len];

		for (int i = 0; i < len; i++) {
			otp[i] = numbers.charAt(randomMethod.nextInt(numbers.length()));
		}
		return new String(otp);
	}
}
