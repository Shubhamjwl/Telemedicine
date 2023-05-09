package com.nsdl.auth.constant;

import lombok.Getter;

public enum UserRoleByUserType {
	DOCTOR("DOCTOR", "DOCTOR"), SCRIBE("SCRIBE", "SCRIBE"), PATIENT("PATIENT", "PATIENT"), ADMIN("ADMIN", "ADMIN"),
	SYSTEMUSER("SYSTEMUSER", "SYSTEMUSER"),
	RECEPTIONIST("RECEPTIONIST", "RECEPTIONIST"),
	CALLCENTRE("CALLCENTRE", "CALLCENTRE");

	UserRoleByUserType(String userType, String role) {
		this.userType = userType;
		this.role = role;
	}

	@Getter
	private String role;

	@Getter
	private String userType;

}
