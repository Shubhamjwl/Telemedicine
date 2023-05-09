package com.nsdl.ndhm.dto.datareport;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorDetailsDto {
    private Integer drId;
    private String drName;
    private String email;
    private Long mob;
    private String mciNo;
    private String smcNo;
    private String speciality;
}
