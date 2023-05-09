package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmPasswordDTO {
    private String password;
    private String txnId;
}
