package com.fvu.Enumeration;

public enum ErrorConstant {
	
	//XML FILE VALIDATION
	INVALID_FILE("201011","File does not exist or Empty File"),
	//FILE-HEADER VALIDATIONS
	INVALID_FILE_HEADER_RECORD_TYPE("201006","Value should be 'FH' (in Capital letters)"),
	INVALID_UPLOADED_BY_FLG("201007","Values for  Uploaded By  should be  'P'  OR  'O'  OR  'U'  OR  'K' or 'Z'"),
	INVALID_PAO_REG_NO("201008","Length should be exactly equal to 7 digits"),
	INVALID_BATCH_NO("201010","Value must be equal to 1"),
	INVALID_LINE_NO("201004","Value should only be 1"),
	FILE_HEADER_VALUE_NOT_FOUND("201001","Value not specified"),
	INVALID_FH_VALUE("201002","Invalid value"),
	INVALID_FILE_VERSION("201012","FVU Version is either Incorrect or NULL"),



	//BATH-HEADER VALIDATIONS
	BATCH_HEADER_VALUE_NOT_FOUND("202001","Value not specified"),
	INVALID_BH_VALUE("202003","Invalid value"),
	INVALID_BATCH_HEADER_RECORD_TYPE("202006","Value should be 'BH' (in Capital letters)"),
	INVALID_BH_BATCH_NO("202007","Value must be equal to 1"),
	INVALID_LENGTH("202005","Length is greater than allowed limit"),
	INVALID_CONTRI_FILE_TYPE("202008","Value should be 'R' for Regular(Original) Files and 'C' for Correction Files"),
	INVALID_BH_PAO_REG_NO("202009","PAO Reg. No. should be same as specified in the File Header"),
	INVALID_BH_HEADER_COUNT("202013","There should be atleast 1 DDO Header Record"),
	INVALID_FILE_CREATION_DATE_LENGTH("202019","Length should be exactly equal to 8 digits"),
	INVALID_FILE_CREATION_YEAR("202029","File creation date can not be less than year 2004"),
	INVALID_FILE_CREATION_DATE("202028","File creation date can not be a future date"),
	INVALID_BH_AMOUNT("202031","Value must be greater than 0.00"),
	INVALID_FOR_REGULAR_FILE("202018","Not applicable for Regular Files"),
	INVALID_BATCH_ID("202032","First 7 digits of Batch Id should be exactly equal to the Reg. No. mentioned in BH."),
	SUBSCRIBER_COUNT_MORE_THAN_REQUIRED("202030","Number of Subscriber Detail Records read is more than the count specified in the Batch Header"),
	SUBSCRIBER_COUNT_LESS_THN_REQUIRED("202025","Number of Subscriber Detail Records read is less than the count specified in the Batch Header"),
	DDO_HEADER_MORE_THAN_REQUIRED("202021","Number of DDO Header Records read is more than specified in Batch Header Record"),
	DDO_HEADER_LESS_THAN_REQUIRED("202020","Number of DDO Header Records read is less than specified in Batch Header  Record"),
	INVALID_GRAND_TOTAL_IN_BH("202026","Total of all the contributions is not equal to the amount specified in Grand Total"),
	INVALID_CONTRIBUTION_TOTAL("202014","Control Total for Government's Contribution and Control Total for Subscriber's mandatory contribution should be equal"),
	
	
	/*"202016","Length should be exactly equal to 13 digits
	"202017","Invalid value First five digits of the PRN Number must be same as 5-digit FC ID in FH Record*/
	
	//SUBSCRIBER_VALIDATIONS
	INVALID_SUB_CONT_TYPE("1204009","Value must be 'C' for Regular Contribution Type  and 'A' for Arrear Contribution Type"),
	SUB_COUNT_MORE_THAN_REQUIRED("203013","Number of Subscriber Detail Records read is less than specified in DDO Header  Record"),
	SUB_INVALID_FOR_REGULAR_FILE("204012","Not applicable for ('R') Regular Contribution Type"),
	INVALID_PRAN("204013","Length should be exactly equal to 12 digits"),
	INVALID_CONT_MONTH_VAL("204019","Contribution Month should be between 01 and 12"),
	INVALID_CONT_YEAR_FUTURE("204020","Contribution year cannot be future year"),
	INVALID_CONT_YEAR_PAST("204021","Contribution year cannot be less than four years from the current year"),
	INVALID_SUB_CONTR_AMT("204016","Government's Contribution amount and Subscriber's mandatory contribution amount is not equal to the Total Contribution amount for the subscriber"),
	INVALID_SUB_VALUE("204002","Invalid value"),
	INVALID_SUB_LENGTH("203010","There should be atleast 1 (SD) Subscriber Detail Record"),
	INVALID_YEAR_LENGTH("204011","Length should be exactly equal to 4 digits"),
	SD_VALUE_NOT_FOUND("204001","Value not Specified"),
	ADDITIONAL_REMARKS("20400","Other remark value is specified"),
	MONTH_OR_YEAR_NOT_MATCHING("204002","Year or Month is not matching with file header"),
	INVALID_SUBSCRIBER_RECORD("204003", "Invalid subscriber detail record");







	private String code;
	private String message;
	private ErrorConstant(String code, String message) {
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
