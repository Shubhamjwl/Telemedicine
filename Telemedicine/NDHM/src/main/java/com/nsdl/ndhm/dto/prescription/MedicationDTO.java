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
public class MedicationDTO {
    private String id;
    private String addProfile;
    private CodingDTO medicationCode;
    private String subjectReference;
    private String subjectDisplay;
    private String authoredOnElementDate;
    private String requesterReference;
    private String requesterDisplay;
    private CodingDTO reasonCode;
    private String reasonReference;
    private String dosageInstructionText;
    private CodingDTO additionalInstructCode;
    private int timingRepeatFrequency;
    private int timingRepeatPeriod;
    private CodingDTO routeCode;
    private CodingDTO methodCode;
}
