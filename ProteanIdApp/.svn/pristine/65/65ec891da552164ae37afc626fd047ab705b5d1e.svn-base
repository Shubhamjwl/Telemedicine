package com.nsdl.user.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsdl.user.dto.SyncRequest;
import com.nsdl.user.dto.SyncResponse;
import com.nsdl.user.entity.ProteanIdUserEntity;
import com.nsdl.user.repository.UserRepository;
import com.nsdl.user.service.VerifierService;

@Service
public class VerifierServiceImpl implements VerifierService {

	@Autowired
	private UserRepository repo;

	@Override
	public SyncResponse sync(SyncRequest request) {
		// TODO Auto-generated method stub

		SyncResponse response = new SyncResponse();
		List<ProteanIdUserEntity> data = repo.findAll();

		try {

			KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
			SecretKey myDesKey = keygenerator.generateKey();

			String encodedData = symmetricEncrypt(data, myDesKey);

			byte[] publicBytes = Base64.getDecoder().decode(request.getPublicKey());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);

			String encodedSecretKey = asymmetricEncrypt(myDesKey, publicKey);

			String combinedData = encodedData + "#Splitter#" + encodedSecretKey;
			String encodedCombinedData = java.util.Base64.getEncoder().encodeToString(combinedData.getBytes());

			response.setEncryptedData(encodedCombinedData);
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private KeyPair generateKeys() {

		KeyPair kyp = null;
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(1024);
			kyp = kpg.generateKeyPair();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kyp;
	}

	private String symmetricEncrypt(List<ProteanIdUserEntity> data, SecretKey myDesKey) throws Exception {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);

		oos.writeObject(data);
		byte[] text = bos.toByteArray();

		Cipher desCipher;
		desCipher = Cipher.getInstance("DES");

		desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
		return Base64.getEncoder().encodeToString(desCipher.doFinal(text));
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private List<ProteanIdUserEntity> symmetricDecrypt(String encodedData, SecretKey myDesKey) throws Exception {

		Cipher desCipher;
		desCipher = Cipher.getInstance("DES");
		desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
		byte[] textDecrypted = desCipher.doFinal(Base64.getDecoder().decode(encodedData));

		ByteArrayInputStream bis = new ByteArrayInputStream(textDecrypted);
		ObjectInputStream ois = new ObjectInputStream(bis);

		return (List<ProteanIdUserEntity>) ois.readObject();
	}

	private String asymmetricEncrypt(SecretKey myDesKey, PublicKey publicKey) throws Exception {

		String encodedKey = Base64.getEncoder().encodeToString(myDesKey.getEncoded());

		Cipher desCipher;
		desCipher = Cipher.getInstance("RSA");
		desCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return Base64.getEncoder().encodeToString(desCipher.doFinal(encodedKey.getBytes("UTF-8")));
	}

	@SuppressWarnings("unused")
	private SecretKey asymmetricDecrypt(String encryptedKey, PrivateKey privateKey) throws Exception {

		Cipher desCipher;
		desCipher = Cipher.getInstance("RSA");
		desCipher.init(Cipher.DECRYPT_MODE, privateKey);
		String decryptedKey = new String(desCipher.doFinal(Base64.getDecoder().decode(encryptedKey)), "UTF-8");

		byte[] decodedKey = Base64.getDecoder().decode(decryptedKey);
		// rebuild key using SecretKeySpec
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
	}

}
