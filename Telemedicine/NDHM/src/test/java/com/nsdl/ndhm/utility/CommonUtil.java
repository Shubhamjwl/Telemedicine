package com.nsdl.ndhm.utility;

import com.nsdl.ndhm.dto.MainRequestDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;

public class CommonUtil {

    public static MainRequestDTO getMainRequest() {
        MainRequestDTO mainRequest = new MainRequestDTO<>();
        mainRequest.setId("1");
        mainRequest.setVersion("v1");
        mainRequest.setRequestTime(null);
        mainRequest.setStatus(true);
        mainRequest.setErrors(null);
        return mainRequest;
    }

    public static MainResponseDTO getMainResponse() {
        MainResponseDTO response = new MainResponseDTO<>();
        response.setId("1");
        response.setVersion("v1");
        response.setResponsetime(null);
        response.setStatus(true);
        response.setErrors(null);
        return response;
    }
}
