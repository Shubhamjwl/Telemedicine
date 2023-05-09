package com.nsdl.telemedicine.gateway.constant;

public enum ErrorCode {

	InvalidReferenceIdFormatException("TIN-REFID-002","INVALID REFERENCE ID FORMAT FOR : "),
	RemarkNotFoundException("TIN-REMARK-001","REMERK NOT FOUND FOR REJECTED ENTITY : "), 
	ReferenceIdNotFoundException("TIN-REFID-002","REFERENCE ID NOT FOUND"),
	StatusNotFoundException("TIN-STATUS-001","STATUS NOT FOUND FOR ENTITY : "),
	StatusFormatException("TIN-STATUS-002","STATUS SHOULD BE 'ACCEPT' OR 'REJECT' FOR ENTITY : "),
	DefaultException("TIN-SYS-001","SOMETHING WENT WRONG, PLEASE TRY AGAIN LATER"),
	AuthorizationException("TIN-AUTH-001","OPERATION NOT ASSIGNED TO YOUR ROLE"),
	NoFunctionalityAssignedException("TIN-AUTH-002","NO FUNCTIONALITY ASSIGNED FOR CURRENT USER"),
	DateRangeException("TIN-DATE-001", "INVALID DATE RANGE START DATE CANNOT EXCEED END DATE FOUND"),
	DateNotFoundException("TIN-DATE-002", "DATE CANNOT BE EMPTY"),
	PageSizeException("TIN-PAGINATION-001", "PAGE SIZE CANNOT BE ZERO OR BLANK"),
	PanFormatException("TIN-PAN-001", "INVALID PAN FORMAT"),
	ReferenceIdFormatException("TIN-REFID-001","REFERENCE ID SHOULD BE 10 DIGIN INTEGER"),
	InvalidTypeException("TIN-INVTYP-001","PROVIDED TYPE IS INVALID"), 
	InvalidWindowException("TIN-INVWND-001","PROVIDED WINDOW IS INVALID "), 
	InvalidCategoryException("TIN-INVCAT-001","PROVIDED CATEGORY IS INVALID"), 
	InvalidDateFormatException("TIN-DATE-003","PROVIDED DATE FORMAT IS INVALID"), 
	WindowNotFoundException("TIN-WINDOW-001","WINDOW SHOULD BE MENTIONED"),
	NoRecordFoundException("TIN-NORCD-001","NO RECORD FOUND EXCEPTION");
	
	private final String errorCode;
	private final String errorMessage;

	private ErrorCode(String errorCode, String errorMessage) {
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
