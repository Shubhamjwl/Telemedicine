package com.nsdl.ndhm.exception;

/**
 * @author Neosoft_JineshB
 */
public class HealthIdErrorConstands {

	private HealthIdErrorConstands() {
		throw new IllegalStateException("Utility class");
	}

	public static final String INVALID_USER_INPUT = "DRREG-001";

	public static final String NO_DATA_FOUND = "DRREG-002";

	public static final String FILE_NOT_FOUND = "DRREG-003";

	public static final String SERVER_ERROR = "DRREG-500";
}
