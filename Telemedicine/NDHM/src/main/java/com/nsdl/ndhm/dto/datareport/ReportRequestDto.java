package com.nsdl.ndhm.dto.datareport;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class ReportRequestDto {

    @NotNull(message="abhaId can not be null")
    @Valid
    private String abhaId;

    private List<CareContextDtls> careContextIds;
}
