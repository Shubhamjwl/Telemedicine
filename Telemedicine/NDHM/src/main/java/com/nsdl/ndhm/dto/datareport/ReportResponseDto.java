package com.nsdl.ndhm.dto.datareport;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReportResponseDto {

    private PatientRegDetailsDTO patient;
    private List<CareContextResponse> careContextDetails;
}
