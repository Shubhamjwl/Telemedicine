package com.nsdl.telemedicine.patient.utility;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class RandomStringUtil {
	
	private static final String ALPHA_NUMERIC_STRING_FOR_USER_PSWD_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHA_LOWER_STRING_FOR_USER_PSWD_KEY="abcdefghijklmnopqrstuvwxyz";
	private static final String NUMERIC_STRING_FOR_TRANSACTION_PSWD_KEY = "0123456789";
	private static final String SPE_STRING_FOR_TRANSACTION_PSWD_KEY = "@#*!)(_-$/";

	public static String randomAlphaNumeric(int length, String typeOdPswd) {
		StringBuilder builder = new StringBuilder();
		int count =4;
		int character = 0;
		List<String> lettersAlpha = Arrays.asList(ALPHA_NUMERIC_STRING_FOR_USER_PSWD_KEY);
		List<String> lettersAlphalower = Arrays.asList(ALPHA_LOWER_STRING_FOR_USER_PSWD_KEY);
		List<String> lettersNumber = Arrays.asList(NUMERIC_STRING_FOR_TRANSACTION_PSWD_KEY);
		List<String> lettersSpe = Arrays.asList(SPE_STRING_FOR_TRANSACTION_PSWD_KEY);
		
	    Collections.shuffle(lettersAlpha);
	    Collections.shuffle(lettersAlphalower);
	    Collections.shuffle(lettersNumber);
	    Collections.shuffle(lettersSpe);
	    
	    String shuffledStringlettersAlpha = lettersAlpha.stream().collect(Collectors.joining());
	    String shuffledStringlettersAlphalower = lettersAlphalower.stream().collect(Collectors.joining());
	    String shuffledStringlettersNumber =  lettersNumber.stream().collect(Collectors.joining());
	    String shuffledStringlettersSpe =  lettersSpe.stream().collect(Collectors.joining());
	    
		while (count-- != 0) {
			if(typeOdPswd.equalsIgnoreCase("LGN-PWD")) {
				character = (int)(Math.random()*shuffledStringlettersAlpha.length());
				builder.append(ALPHA_NUMERIC_STRING_FOR_USER_PSWD_KEY.charAt(character));
			}
			if(typeOdPswd.equalsIgnoreCase("LGN-PWD")) {
				character = (int)(Math.random()*shuffledStringlettersAlphalower.length());
				builder.append(ALPHA_LOWER_STRING_FOR_USER_PSWD_KEY.charAt(character));
			}
			if(typeOdPswd.equalsIgnoreCase("LGN-PWD")) {
				character = (int)(Math.random()*shuffledStringlettersNumber.length());
				builder.append(NUMERIC_STRING_FOR_TRANSACTION_PSWD_KEY.charAt(character));
			}
		if(typeOdPswd.equalsIgnoreCase("LGN-PWD")) {
			character = (int)(Math.random()*shuffledStringlettersSpe.length());
				builder.append(SPE_STRING_FOR_TRANSACTION_PSWD_KEY.charAt(character));
			}
		}
		return builder.toString().substring(0,length);
	}

	public static void main(String[] args) {
		System.out.println(RandomStringUtil.randomAlphaNumeric(10,"LGN-PWD"));
	}
}
