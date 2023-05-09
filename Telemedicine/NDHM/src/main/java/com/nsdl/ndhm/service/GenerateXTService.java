package com.nsdl.ndhm.service;

import com.nsdl.ndhm.dto.*;

import java.util.Map;

public interface GenerateXTService {
    MainResponseDTO<GenerateOtpResp> authInit(Map<String, String> headers, AuthInitDTO authInitDTO);

    MainResponseDTO<ConfirmOtpResp> confirmAadhaarOTP(Map<String, String> headers, ConfirmOtpDTO confirmOtpDTO);

    MainResponseDTO<ConfirmOtpResp> confirmMobileOTP(Map<String, String> headers, ConfirmOtpDTO confirmOtpDTO);

    MainResponseDTO<ConfirmOtpResp> confirmByPassword(Map<String, String> headers, ConfirmPasswordDTO confirmPasswordDTO);

    MainResponseDTO<ConfirmOtpResp> confirmAadhaarBio(Map<String, String> headers, ConfirmAadhaarBioDTO confirmAadhaarBioDTO);

    MainResponseDTO<SearchExistHealthIdRespDTO> confirmDemograpics(Map<String, String> headers,
                                                                   ConfirmDemograpicsDTO confirmDemograpicsDTO);
}
