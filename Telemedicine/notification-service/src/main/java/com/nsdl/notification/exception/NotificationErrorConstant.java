package com.nsdl.notification.exception;

public enum NotificationErrorConstant {

	INTERNAL_SERVER_ERROR("NOT-EXP-001", "Something went wrong, try again later"),
	NOTIFICATION_ID_NOT_FOUND("NOT-EXP-002", "notifications id not found for given user."),
	USER_NOT_FOUND("NOT-EXP-003", "userid is not present in system."),
	TOKEN_SHOULD_NOT_BE_EMPTY_OR_NULL("NOT-EXP-004", "token should not be empty or null."),
	NOTIFICATION_FILE_UPLOAD_FAILED("NOT-EXP-005", "notifications file upload failed."),
	FILE_INVALID("NOT-EXP-006", "file is invalid."),
	TEMPLATE_NOT_FOUND("NOT-EXP-007", "template data not found."),
	FILE_DOWNLOAD_FAILED("NOT-EXP-008", "file download failed."),
	OPERATION_NOT_ALLOWED("NOT-EXP-009", "operation is not allowed for user."),
	NOTIFICATION_EMPTY("NOT-EXP-010", "notifications are empty for given user id."),
	NOTIFICATION_TEMPLATE_UPLOAD_FAILED("NOT-EXP-011", "notifications template upload failed."),
	DOCTOR_NOT_ALLOWED_FOR_WALLET_NOTIFICATION("NOT-EXP-012", "doctors not allowed for wallet notification."),
	AMOUNT_SHOULD_NOT_BE_LESS_THAN_OR_EQUAL_TO_ZERO("NOT-EXP-013", "amount should not be less than or equal to zero.");

	private final String errorCode;

	private final String errorMessage;

	private NotificationErrorConstant(final String errorCode, final String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
