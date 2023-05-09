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
public class EncounterDTO {
    private String id;
    private String instantType;
    private String addProfile;
    private String identifierSystem;
    private String identifierValue;
    private CodingDTO encounterCode;
    private String subjectReference;
    private String periodStart;
    private String periodEnd;
    private CodingDTO dischargeCode;
    private String dischargeText;
}
