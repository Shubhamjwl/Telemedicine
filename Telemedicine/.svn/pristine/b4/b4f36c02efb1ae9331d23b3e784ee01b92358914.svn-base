package com.nsdl.ndhm.service;

import com.nsdl.ndhm.dto.*;

import java.util.Map;

public interface ForgotHealthIdAndNumService {

    MainResponseDTO<GenerateOtpResp> generateAadhaarOTP(Map<String, String> headers, GenerateOtpDTO generateAadhaarOtpDTO);

    MainResponseDTO<GenerateHealthIdAndNumRespDTO> getHealthIdByAadhaar(Map<String, String> headers,
                                                                        ConfirmOtpDTO confirmOtpDTO);

    MainResponseDTO<GenerateOtpResp> generateMobileOTP(Map<String, String> headers, GenerateOtpDTO generateMobileOtpDTO);

    MainResponseDTO<GenerateHealthIdAndNumRespDTO> getHealthIdByMobile(Map<String, String> headers,
                                                                       ForgotHealthByMobileReqDTO healthByMobileReqDTO);

}