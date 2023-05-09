package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnNotifyRequestDTO {

	private String requestId;

	private String timestamp;

	private OnConfirmAuthDTO auth;

	private PatientDTO patient;

	private IdentifierDTO[] identifiers;

}
