package com.nsdl.ndhm.utility;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.nsdl.ndhm.dto.GeneratePublicKeyDTO;

@Component
public class PublicKeyUtils {
	private static final Logger logger = LoggerFactory.getLogger(PublicKeyUtils.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CommonHeadersUtil commonHeadersUtil;

	@Value("${generatePublicKey}")
	String generatePublicKey;

	GeneratePublicKeyDTO generatePublicKeyDTO = null;
	public static final String ERROR_MSG = "PublicKeyUtils Class";
	private String publicKeyVal = "";

	public String generatePublicKey() {

		logger.info("Request Received For Public Key");

		if (publicKeyVal.contentEquals("")) {
			String publicKey = "";
			String url = generatePublicKey;
			URI uri;
			ResponseEntity<String> result = null;
			HttpEntity<String> requestEntity = new HttpEntity<>("",
					commonHeadersUtil.getHeadersWithRefreshTokenFromServer());
			try {
				uri = new URI(url);
				result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
				if (null != result.getBody()) {
					publicKey = result.getBody().replace("\n", "");
					Pattern pattern = Pattern.compile("-----BEGIN PUBLIC KEY-----(.*?)-----END PUBLIC KEY-----",
							Pattern.DOTALL);
					Matcher matcher = pattern.matcher(publicKey);
					while (matcher.find()) {
						publicKey = matcher.group(1);
					}
				}
				publicKeyVal = publicKey;
			} catch (HttpClientErrorException | HttpServerErrorException e) {
				logger.error("Error In generatePublicKey {} ", e);
			} catch (Exception e) {
				logger.error("Error In generatePublicKey {} ", e);
			}
		}

		return publicKeyVal;
	}
}
