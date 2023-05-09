package com.nsdl.telemedicine.patient.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchPatientResponseDTO {

	private String userId;
	private String mobileNo;
	private String name;
	private String firstName;
	private String lastName;
	private String middleName;
	private String email;
	private String gender;
	private String address;
	private String abhaAddress;
	private String abhaNo;
}
