package com.nsdl.ndhm.dto.datareport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CareContextDtls {
    private String careContextId;

    @JsonCreator
    public CareContextDtls(@JsonProperty("careContextId") String careContextId) {
        this.careContextId = careContextId;
    }
}
