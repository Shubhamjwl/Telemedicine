package com.nsdl.ndhm.transfer.dto;

import com.nsdl.ndhm.dto.IdentifierDTO;
import com.nsdl.ndhm.dto.OnConfirmAuthDTO;
import com.nsdl.ndhm.dto.PatientDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDTO {

	private String requestId;

	private String timestamp;

	private OnConfirmAuthDTO auth;

	private PatientDTO patient;

	private IdentifierDTO[] identifiers;

}