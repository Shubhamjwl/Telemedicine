package com.nsdl.ndhm.dto;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisLinkingPatientDTO {

	private String id;
	private String referenceNumber;
	private ArrayList<DisLinkCareContextDTO> careContexts;
}
