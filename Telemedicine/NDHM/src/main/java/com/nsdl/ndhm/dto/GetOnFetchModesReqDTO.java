package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetOnFetchModesReqDTO {

	private String requestId;
	private String timeStamp;
}
