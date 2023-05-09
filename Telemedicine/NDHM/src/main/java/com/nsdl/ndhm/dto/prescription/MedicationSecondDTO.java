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
public class MedicationSecondDTO {
    private String id;
    private String addProfile;
    private String medicationCodeText;
    private String subjectReference;
    private String subjectDisplay;
    private String authoredOnElementDate;
    private String requesterReference;
    private String requesterDisplay;
    private CodingDTO reasonCode;
    private String reasonReference;
    private String dosageInstructionText;
}
