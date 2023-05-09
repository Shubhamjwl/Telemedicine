package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisLinkMetaDto {
	 private String communicationMedium;
	 private String communicationHint;
	 private String communicationExpiry;
}
