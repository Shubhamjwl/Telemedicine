package com.nsdl.authenticate.dto;


public enum ProcessedLevelType {

	RAW("Raw"),
	INTERMEDIATE("Intermediate"),
	PROCESSED("Processed");

	private final String value;

	ProcessedLevelType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static ProcessedLevelType fromValue(String v) {
		for (ProcessedLevelType c : ProcessedLevelType.values()) {
			if (c.value.equalsIgnoreCase(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
