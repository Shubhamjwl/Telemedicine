package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForgotHealthByMobileReqDTO {

    private String dayOfBirth;
    private String firstName;
    private String gender;
    private String lastName;
    private String middleName;
    private String monthOfBirth;
    private String name;
    private String otp;
    private String txnId;
    private String yearOfBirth;

}
