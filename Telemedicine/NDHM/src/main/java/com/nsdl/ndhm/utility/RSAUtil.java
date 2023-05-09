package com.nsdl.ndhm.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAUtil {
	private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

	private static final String CYPHER_TOKEN = "RSA/ECB/PKCS1Padding";

	public PublicKey getPublicKey(String base64PublicKey) {
		PublicKey publicKey = null;
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.error("Error In getPublicKey {}", e.getMessage());
		}
		return publicKey;
	}

	public byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException,
			InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
		Cipher cipher = Cipher.getInstance(CYPHER_TOKEN);
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		return cipher.doFinal(data.getBytes());
	}

	public String getEncryptData(String data, String publicKey) {
		try {
			return Base64.getEncoder().encodeToString(encrypt(data, publicKey));
		} catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException
				| NoSuchAlgorithmException e) {
			logger.error("Error In getEncryptData {}", e.getMessage());
			return "";
		}
	}

}
