package com.nsdl.telemedicine.scribe.dto;

import lombok.Data;

@Data
public class CreateUserResponseDTO {
        
    private String message;

    private String userId;

    private String role;

}
