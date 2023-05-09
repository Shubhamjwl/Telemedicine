package com.nsdl.telemedicine.patient.dto;

import lombok.Data;

@Data
public class CreateUserResponseDTO {
        
    private String message;

    private String userId;

    private String role;

}
