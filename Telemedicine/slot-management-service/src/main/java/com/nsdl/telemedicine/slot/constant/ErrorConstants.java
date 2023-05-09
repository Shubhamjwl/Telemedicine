package com.nsdl.telemedicine.slot.constant;

public enum ErrorConstants {

	SOMETHING_WENT_WRONG("ERR-SLT-001", "Something went wrong, Please contact admin."),
	INVALID_SLOT_FROM_TIME("ERR-SLT-002", "Invalid slot from date and time."),
	INVALID_SLOT_TO_TIME("ERR-SLT-003", "Invalid slot to date and time."),
	INVALID_SLOT_DURATION("ERR-SLT-004", "Invalid slot duration."),
	INVALID_SLOT_DATE("ERR-SLT-005", "Invalid slot date."),
	INVALID_SLOT_TIME("ERR-SLT-006", "Invalid slot time."),
	INVALID_CONSULT_AMOUNT("ERR-SLT-007", "Invalid consult amount."),
	INVALID_SLOT_TIME_AVAILABLE("ERR-SLT-008", "Slot time alredy available"),
	FAIL_TO_SAVE_RECORD("ERR-SLT-009", "Failed to save records "),
	SLOT_SAVE("ERR-SLT-010", "slot has been added successfully "),
	INVALID_IS_ACTIVE("ERR-SLT-011", "Invalid value for is active."),
	NO_DETAILS_FOUND("ERR-SLT-012", "No details found for given input slot."),
	FAIL_TO_UPDATE("ERR-SLT-013", "Failed to update records."),
	FAIL_TO_DELETE("ERR-SLT-013", "Failed to delete slot details."),
	SLOT_UPDATE("ERR-SLT-014", "Slot updated successfully."),
	DR_DETAILS_NOT_FOUND("ERR-SLT-015", "No details found for given input scribe."),
	INVALID_DOC_DETAILS("ERR-SLT-016", "Doctor details not found. Please contact admin."),
	SLOT_DETAILS_NOT_FOUND("ERR-SLT-017", "No slot details found for given doctor."),
	INVALID_WORKING_DAYS("ERR-SLT-018", "Working days required."),
	INVALID_WORKING_TIME("ERR-SLT-019", "Working time required."),
	INVALID_YEAR_MONTH_FORMAT("ERR-SLT-020", "Invalid year & month format. It should be yyyy-MM."),
	PAST_SLOT_DATE("ERR-SLT-021", "Past date not supported."),
	APPOINTMENT_AVAILABLE("ERR-SLT-022", "Because appointent is present on that date."),
	NO_SLOT_DETAILS_FOUND("ERR-SLT-023", "No slot details found for given input date."),
	NO_HOLIDAY_DETAILS_FOUND("ERR-SLT-024", "Holiday details not found for given input date."),
	HOLIDAY_DETAILS_FOUND("ERR-SLT-025", "Holiday already available for given input date."),
	INVALID_SLOT_TYPE("ERR-SLT-0027", "Slot type required."),
	HOLIDAY_UPDATE("ERR-SLT-026", "Holiday updated successfully."),
	CANT_DELETE_PAST_DATE_SLOTS("ERR-SLT-027", "Can't delete past date slots"),
	CANT_DELETE_APPOINTED_SLOTS("ERR-SLT-027", "Can't delete appointed slots");
        private String code;
	private String message;
	private ErrorConstants(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
