package com.nsdl.ndhm.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecoveryPatientDTO {

	private String id;
	private List<VerifiedIdentifierDTO> verifiedIdentifiers;
	private List<UnverifiedIdentifierDTO> unverifiedIdentifiers;
	private String name;
	private String gender;
	private int yearOfBirth;
}
