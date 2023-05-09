package com.nsdl.authenticate.utils;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;

public class HMACUtils {

	@Value("${login.password.hash.algorithm}")
	private static String hmac_algo_name;

	private static MessageDigest messageDigest;

	public static byte[] generateHash(final byte[] bytes) {
		return messageDigest.digest(bytes);
	}

	public static void update(final byte[] bytes) {
		messageDigest.update(bytes);
	}

	public static String hash(final byte[] data) {
		return digestAsPlainText(HMACUtils.generateHash(data));
	}
	
	public static byte[] updatedHash() {
		return messageDigest.digest();
	}

	public static String digestAsPlainText(final byte[] bytes) {
		return DatatypeConverter.printHexBinary(bytes).toUpperCase();
	}

	static {
		try {
			messageDigest = messageDigest != null ? messageDigest : MessageDigest.getInstance("SHA-256");
		} catch (java.security.NoSuchAlgorithmException exception) {
			System.out.println(exception.getMessage());
		}
	}
	private HMACUtils() {
	}
}
