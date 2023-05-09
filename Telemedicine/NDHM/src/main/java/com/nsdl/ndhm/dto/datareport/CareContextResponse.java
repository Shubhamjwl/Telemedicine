package com.nsdl.ndhm.dto.datareport;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CareContextResponse {
    @NotNull(message="careContextId can not be null")
    @Valid
    private String careContextId;
    private LocalDateTime careContextCreationTimestamp;
    private DoctorDetailsDto doctorDetails;
    private List<ReportResponseDtls> reportResponseDtls;
}
