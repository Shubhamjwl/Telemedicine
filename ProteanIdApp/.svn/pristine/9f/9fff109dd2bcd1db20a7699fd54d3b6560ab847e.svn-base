package com.nsdl.authenticate.dto;


public enum PurposeType {

	VERIFY("Verify"),
	IDENTIFY("Identify"),
	ENROLL("Enroll"),
	ENROLLVERIFY("EnrollVerify"),
	ENROLLIDENTIFY("EnrollIdentify"),
	AUDIT("Audit");

	private final String value;

	PurposeType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static PurposeType fromValue(String v) {
		for (PurposeType c : PurposeType.values()) {
			if (c.value.equalsIgnoreCase(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
