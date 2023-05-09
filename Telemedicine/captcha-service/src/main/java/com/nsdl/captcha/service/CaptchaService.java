package com.nsdl.captcha.service;

import com.nsdl.captcha.dto.CaptchaResponseDTO;
import com.nsdl.captcha.dto.MainResponseDTO;

public interface CaptchaService {
	public byte[] generateCaptcha(String sessionId);
	public MainResponseDTO<CaptchaResponseDTO> verifyCaptcha(String sessionId , String captchaValue, String flagValue);
}
