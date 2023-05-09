package com.nsdl.ndhm.dto.opconsult;

import com.nsdl.ndhm.dto.prescription.CodingDTO;
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
public class OrganizationDTO {
    private String id;
    private String addProfile;
    private CodingDTO identifierCode;
    private String identifierSystem;
    private String identifierValue;
    private String organisationName;
    private String contactNum;
    private String email;
}
