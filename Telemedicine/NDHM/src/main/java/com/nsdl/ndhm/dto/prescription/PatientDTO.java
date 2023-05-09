package com.nsdl.ndhm.dto.prescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private String id;
    private String versionId;
    private String lastUpdatedElement;
    private String instantType;
    private String addProfile;
    private String divAsString;
    private CodingDTO codingDTO;
    private String identifierSystem;
    private String identifierValue;
    private String nameText;
    private String telecomValue;
    private String gender;
    private String birthDateElement;
}
