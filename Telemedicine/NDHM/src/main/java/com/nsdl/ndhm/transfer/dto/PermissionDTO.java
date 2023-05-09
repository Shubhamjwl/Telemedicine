package com.nsdl.ndhm.transfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDTO {
	 private String accessMode;
	 private DateRangeDTO dateRange;
	 private String dataEraseAt;
	 private FrequencyDTO frequency;
}
