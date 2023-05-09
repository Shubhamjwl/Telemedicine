package com.nsdl.captcha.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nsdl.captcha.constant.Constants;
import com.nsdl.captcha.dto.CaptchaResponseDTO;
import com.nsdl.captcha.dto.MainResponseDTO;
import com.nsdl.captcha.exception.DataParsingException;
import com.nsdl.captcha.exception.ServiceError;
import com.nsdl.captcha.service.CaptchaService;
import com.nsdl.captcha.utility.DateUtils;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

@Service
public class CaptchaServiceImpl implements CaptchaService {

	private static final Logger logger = LoggerFactory.getLogger(CaptchaServiceImpl.class);

	@Value("${captcha.expiry.time}")
	String captchaExpiryLimit;

	@Value("${verifyCaptchaByPass.flag}")
	String verifyCaptchaByPassFlag;
	
	Map<String, String> sessionMap = new HashMap<String, String>();
	Map<String, LocalDateTime> timerMap = new HashMap<String, LocalDateTime>();

	@Override
	public byte[] generateCaptcha(String sessionId) {

		// response.setContentType("image/jpg");
		List<Color> colors = new ArrayList<Color>();
		colors.add(Color.black);

		List<Font> fonts = new ArrayList<Font>();
		fonts.add(new Font("SERIF", Font.ITALIC, 28));
		char[] srcChars = { '0', 'O', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
				'J', 'K', 'L', 'M', 'N', '1', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'Y', 'W', 'Z', 'I', 'a', 'b', 'c',
				'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k' };
		DefaultTextProducer defaultTextProducer = new DefaultTextProducer(5, srcChars);
		Captcha captcha = new Captcha.Builder(140, 30)
				.addText(defaultTextProducer, new DefaultWordRenderer(colors, fonts))
				.addBackground(new GradiatedBackgroundProducer(Color.white, Color.lightGray)).addBorder().build();
		// CaptchaServletUtil.writeImage(response, captcha.getImage());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(captcha.getImage(), "png", baos);
			baos.flush();
		} catch (IOException e) {
			logger.error("Exception while generating Captcha : " + e.getMessage());
			e.printStackTrace();
		}
		byte[] imageInByte = baos.toByteArray();
		try {
			baos.close();
		} catch (IOException e) {
			logger.error("Exception while generating Captcha : " + e.getMessage());
			e.printStackTrace();
		}
		if (sessionMap.containsKey(sessionId)) {
			sessionMap.replace(sessionId, captcha.getAnswer());
		} else {
			sessionMap.put(sessionId, captcha.getAnswer());
		}
		timerMap.put(captcha.getAnswer(), DateUtils.getCurrentLocalDateTime());
		logger.info("Catpcha generation : Success");
		return imageInByte;

	}

	@Override
	public  MainResponseDTO<CaptchaResponseDTO> verifyCaptcha(String sessionId, String captchaValue,String flagValue) {
		LocalDateTime verifyCaptchaTime = DateUtils.getCurrentLocalDateTime();
		MainResponseDTO<CaptchaResponseDTO> mainResponse = null;
		CaptchaResponseDTO captchaResponse = null ;
		
		//Added code for performance testing:start
		if(verifyCaptchaByPassFlag.equals("Y")) {
			mainResponse = new MainResponseDTO<CaptchaResponseDTO>();
			captchaResponse = new CaptchaResponseDTO();
			captchaResponse.setResponseMsg(Constants.VERIFY_CAPTCHA_SUCCESS.getMessage());
			mainResponse.setId("captcha");
			mainResponse.setVersion("v1");
			mainResponse.setResponsetime(DateUtils.getCurrentLocalDateTime());
			mainResponse.setStatus(true);
			mainResponse.setResponse(captchaResponse);
			mainResponse.setStatusCode("200");
			if (flagValue.equalsIgnoreCase("true")) {
				sessionMap.remove(sessionId);
				timerMap.remove(captchaValue);
			}
			logger.info("Catpcha Verification Bypass: Success");
		}
		//Added code for performance testing:end
		else {
			if (sessionMap.containsKey(sessionId)) {
				String verifyCaptcha = sessionMap.get(sessionId);
				if (captchaValue.equals(verifyCaptcha)) {
					if (DateUtils.timeDifferenceInSeconds(timerMap.get(captchaValue),
							verifyCaptchaTime) <= (Integer.parseInt(captchaExpiryLimit))) {
						mainResponse = new MainResponseDTO<CaptchaResponseDTO>();
						captchaResponse = new CaptchaResponseDTO();
						captchaResponse.setResponseMsg(Constants.VERIFY_CAPTCHA_SUCCESS.getMessage());
						mainResponse.setId("captcha");
						mainResponse.setVersion("v1");
						mainResponse.setResponsetime(DateUtils.getCurrentLocalDateTime());
						mainResponse.setStatus(true);
						mainResponse.setResponse(captchaResponse);
						mainResponse.setStatusCode("200");
						if (flagValue.equalsIgnoreCase("true")) {
							sessionMap.remove(sessionId);
							timerMap.remove(captchaValue);
						}
						logger.info("Catpcha Verification : Success");
					} else {
						logger.error("Catpcha Expired");
						throw new DataParsingException(new ServiceError(Constants.CAPTCHA_EXPIRED.getCode(),
								Constants.CAPTCHA_EXPIRED.getMessage()));
					}
				} else {
					logger.error("Catpcha Verification : Failed");
					throw new DataParsingException(new ServiceError(Constants.VERIFY_CAPTCHA_FAILED.getCode(),
							Constants.VERIFY_CAPTCHA_FAILED.getMessage()));
				}
			} else {
				logger.error("Catpcha Session Id not Found");
				throw new DataParsingException(
						new ServiceError(Constants.SESSION_NOT_FOUND.getCode(), Constants.SESSION_NOT_FOUND.getMessage()));
			}
		}
		
		return mainResponse;
	}
}
