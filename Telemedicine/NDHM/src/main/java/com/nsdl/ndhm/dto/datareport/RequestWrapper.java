package com.nsdl.ndhm.dto.datareport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestWrapper<T> {
    private String id;
    private String version;
    private String requestTime;
    private String method;
    private T request;
}
