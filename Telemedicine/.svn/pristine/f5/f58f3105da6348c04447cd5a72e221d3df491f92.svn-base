package com.fvu.utility;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.fvu.DTO.ErrorDetails;
import com.fvu.Enumeration.ErrorConstant;
import com.fvu.Request.RequestFile;
import com.fvu.Request.SubscriberDdtlType;
import com.fvu.constant.AppConstant;

public class ErrorGenerator {
	
	private static final Logger logger = Logger.getLogger(ErrorGenerator.class.getName());
	public final static String DATE_FORMAT="ddMMyyyy";
	public static Set<ErrorDetails> createCustomExceptionList(List<SAXParseException> validationErrorList,String inputFilePath) {
		Set<ErrorDetails> customErrorList=new HashSet<>();
		ErrorDetails errorDetails=null;
		int fileHeaderStartLine=Integer.parseInt(LineNumberLocator.getLineNumberOfTag("file-header", inputFilePath));
		int batchHeaderStartLine=Integer.parseInt(LineNumberLocator.getLineNumberOfTag("batch-header", inputFilePath));
		int subscriberHeaderStartLine=Integer.parseInt(LineNumberLocator.getLineNumberOfTag("subscriber-dtl", inputFilePath));
		String recordType=null;
		String errorCode=null;
		String errorMsg=null;
		String[] fileHeaderTags= {"line-no","record-type","uploaded-by","pao-reg-no","no-of-batches","file-flag"};
		int line_no=0;
		for (SAXParseException saxParseException : validationErrorList) 
		{
			errorDetails=new ErrorDetails(); 
			logger.info("Invalid value at line number:"+saxParseException.getLineNumber()+" "+saxParseException.getMessage());
					
			//FILE_HEADER VALIDATION 
			line_no=saxParseException.getLineNumber();
			String parserException=saxParseException.getMessage();
					if(line_no>=fileHeaderStartLine && saxParseException.getLineNumber()<batchHeaderStartLine)
					{
						recordType=AppConstant.FILE_HEADER_RECORD_TYPE;
						Set<String> missingFileTag=findMissingTag(saxParseException.getMessage(),fileHeaderTags);
						if(missingFileTag.size()>0)
						{
							errorCode=ErrorConstant.FILE_HEADER_VALUE_NOT_FOUND.getCode();
							errorMsg=ErrorConstant.FILE_HEADER_VALUE_NOT_FOUND.getMessage();
							for (String fieldName : missingFileTag) 
							{
								errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, recordType,String.valueOf(line_no-1), fieldName);
								customErrorList.add(errorDetails);
							}
						}
						else
						{
							if(parserException.contains(" No child element is expected at this point"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_FH_VALUE.getCode(),
										ErrorConstant.INVALID_FH_VALUE.getMessage(),recordType, String.valueOf(line_no), "version");
							customErrorList.add(errorDetails);

							}
							
							if(parserException.contains("'{version}' is expected"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_FILE_VERSION.getCode(),
											ErrorConstant.INVALID_FILE_VERSION.getMessage(),recordType, String.valueOf(line_no-1), "version");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'record-type' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_FILE_HEADER_RECORD_TYPE.getCode(),
										ErrorConstant.INVALID_FILE_HEADER_RECORD_TYPE.getMessage(),recordType,String.valueOf(line_no), "record-type");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'uploaded-by' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_UPLOADED_BY_FLG.getCode(),
										ErrorConstant.INVALID_UPLOADED_BY_FLG.getMessage(),recordType,String.valueOf(line_no), "uploaded-by");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'pao-reg-no' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_PAO_REG_NO.getCode(),ErrorConstant.INVALID_PAO_REG_NO.getMessage(), recordType, 
										String.valueOf(line_no), "pao-reg-no");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'no-of-batches' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BATCH_NO.getCode(),
										ErrorConstant.INVALID_BATCH_NO.getMessage(),recordType,String.valueOf(line_no), "no-of-batches");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'file-flag' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_FH_VALUE.getCode(),
										ErrorConstant.INVALID_FH_VALUE.getMessage(), recordType,String.valueOf(line_no), "file-flag");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'month' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_FH_VALUE.getCode(),
										ErrorConstant.INVALID_FH_VALUE.getMessage(), recordType,String.valueOf(line_no), "month");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'year' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_FH_VALUE.getCode(),
										ErrorConstant.INVALID_FH_VALUE.getMessage(), recordType,String.valueOf(line_no), "year");
								customErrorList.add(errorDetails);
							}
						}
					}
					//BATCH-HEADER
					else if(line_no>=batchHeaderStartLine && line_no<subscriberHeaderStartLine)
					{
						String[] batchHeaderTags={"line-no","record-type","batch-no","contr-file-type","pao-reg-no","date-file-creation","batch-id"
								,"txn-id","total-ddo","no-subscriber-records","control-total-govt-contr","control-total-sub-contr","grand-total"};
						recordType=AppConstant.BATCH_HEADER_RECORD_TYPE;
						Set<String> missingBatchTag=findMissingTag(parserException,batchHeaderTags);
						if(missingBatchTag.size()>0)
						{
							errorCode=ErrorConstant.BATCH_HEADER_VALUE_NOT_FOUND.getCode();
							errorMsg=ErrorConstant.BATCH_HEADER_VALUE_NOT_FOUND.getMessage();
							for (String fieldName : missingBatchTag) 
							{
								errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, recordType,String.valueOf(line_no-1), fieldName);
								customErrorList.add(errorDetails);
							}
						}
						else
						{
							if(parserException.contains(" No child element is expected at this point"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_VALUE.getCode(),
										ErrorConstant.INVALID_BH_VALUE.getMessage(),recordType, String.valueOf(line_no), "version");
							customErrorList.add(errorDetails);

							}
							
							if(parserException.contains("'record-type' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BATCH_HEADER_RECORD_TYPE.getCode(),
										ErrorConstant.INVALID_BATCH_HEADER_RECORD_TYPE.getMessage(),recordType,String.valueOf(line_no), "record-type");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'contr-file-type' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_CONTRI_FILE_TYPE.getCode(),
										ErrorConstant.INVALID_CONTRI_FILE_TYPE.getMessage(),recordType,String.valueOf(line_no), "contr-file-type");
								customErrorList.add(errorDetails);
								
							}
							if(parserException.contains("'pao-reg-no' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_VALUE.getCode(),
										ErrorConstant.INVALID_BH_VALUE.getMessage(),recordType,String.valueOf(line_no), "pao-reg-no");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'date-file-creation' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_FILE_CREATION_DATE_LENGTH.getCode(),
										ErrorConstant.INVALID_FILE_CREATION_DATE_LENGTH.getMessage(),recordType,String.valueOf(line_no), "date-file-creation");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'batch-id' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_VALUE.getCode(),
										ErrorConstant.INVALID_BH_VALUE.getMessage(),recordType,String.valueOf(line_no), "batch-id");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'txn-id' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_VALUE.getCode(),
										ErrorConstant.INVALID_BH_VALUE.getMessage(),recordType,String.valueOf(line_no), "txn-id");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'total-ddo' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_VALUE.getCode(),
										ErrorConstant.INVALID_BH_VALUE.getMessage(),recordType, 
										String.valueOf(line_no), "total-ddo");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("'no-subscriber-records' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_VALUE.getCode(),
										ErrorConstant.INVALID_BH_VALUE.getMessage(),recordType,String.valueOf(line_no), "no-subscriber-records");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("control-total-govt-contr' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_AMOUNT.getCode(),
										ErrorConstant.INVALID_BH_AMOUNT.getMessage(),recordType,String.valueOf(line_no), "control-total-govt-contr");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("control-total-sub-contr' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_AMOUNT.getCode(),
										ErrorConstant.INVALID_BH_AMOUNT.getMessage(),recordType,String.valueOf(line_no), "control-total-sub-contr");
								customErrorList.add(errorDetails);
							}
							if(parserException.contains("grand-total' is not valid"))
							{
								errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_AMOUNT.getCode(),
										ErrorConstant.INVALID_BH_AMOUNT.getMessage(),recordType,String.valueOf(line_no), "grand-total");
								customErrorList.add(errorDetails);
							}
						}
						
					}
					//SUBCRIBER_VALIDATIONS
					else
					{
						recordType=AppConstant.SUBSCRIBER_HEADER_RECORD_TYPE;
						String[] batchHeaderTags={"ddo-reg-no","pran","govt-contr-amt","subscriber-contr-amt","total-contr-subscriber","contr-type"};
						String sub_dtls=LineNumberLocator.getTagValueByTagName("subscriber-dtl", inputFilePath);
						if(sub_dtls==null)
						{
							errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_SUB_LENGTH.getCode(),
									ErrorConstant.INVALID_SUB_LENGTH.getMessage(),recordType,String.valueOf(line_no), "subscriber-dtl");
							customErrorList.add(errorDetails);
						}
						else
						{
							Set<String> missingBatchTag=findMissingTag(parserException,batchHeaderTags);
							if(missingBatchTag.size()>0)
							{
								errorCode=ErrorConstant.BATCH_HEADER_VALUE_NOT_FOUND.getCode();
								errorMsg=ErrorConstant.BATCH_HEADER_VALUE_NOT_FOUND.getMessage();
								for (String fieldName : missingBatchTag) 
								{
									errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, recordType,String.valueOf(line_no-1), fieldName);
									customErrorList.add(errorDetails);
								}
							}
							else
							{
								
								String remarks=LineNumberLocator.getTagValueByTagName("remarks", inputFilePath);
								if(null!=remarks && !remarks.equalsIgnoreCase("Others") && parserException.contains(" No child element is expected at this point") )
								{
									errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_SUB_VALUE.getCode(),
											ErrorConstant.INVALID_SUB_VALUE.getMessage(),recordType, String.valueOf(line_no), "version");
								customErrorList.add(errorDetails);

								}
								if(parserException.contains("'ddo-reg-no' is not valid"))
								{
									errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_SUB_VALUE.getCode(),
											ErrorConstant.INVALID_SUB_VALUE.getMessage(),recordType,String.valueOf(line_no), "ddo-reg-no");
									customErrorList.add(errorDetails);
								}
								if(parserException.contains("'pran' is not valid"))
								{
									errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_PRAN.getCode(),
											ErrorConstant.INVALID_PRAN.getMessage(),recordType,String.valueOf(line_no), "pran");
									customErrorList.add(errorDetails);
								}
								if(parserException.contains("'contr-type' is not valid"))
								{
									errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_SUB_CONT_TYPE.getCode(),
											ErrorConstant.INVALID_SUB_CONT_TYPE.getMessage(),recordType,String.valueOf(line_no), "contr-type");
									customErrorList.add(errorDetails);
								}
								if(parserException.contains("'contr-type' is not valid"))
								{
									errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_SUB_CONT_TYPE.getCode(),
											ErrorConstant.INVALID_SUB_CONT_TYPE.getMessage(),recordType,String.valueOf(line_no), "contr-type");
									customErrorList.add(errorDetails);
								}
								if(parserException.contains("'contr-month' is not valid"))
								{
									errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_CONT_MONTH_VAL.getCode(),
											ErrorConstant.INVALID_CONT_MONTH_VAL.getMessage(),recordType,String.valueOf(line_no), "month");
									customErrorList.add(errorDetails);
								}
								if(parserException.contains("'contr-year' is not valid"))
								{
									errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_YEAR_LENGTH.getCode(),
											ErrorConstant.INVALID_YEAR_LENGTH.getMessage(),recordType,String.valueOf(line_no), "year");
									customErrorList.add(errorDetails);
								}
								if(parserException.contains("cvc-enumeration-valid"))
								{
									errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_SUB_VALUE.getCode(),
											ErrorConstant.INVALID_SUB_VALUE.getMessage(),recordType,String.valueOf(line_no), "remarks");
									customErrorList.add(errorDetails);
								}
							}	
						}
						
						
					}
				
				}
		
		return customErrorList;
	}


	
	public static Set<String> findMissingTag(String inputString, String[] items) {
	    Set<String> missingTags=new HashSet<>();
	    for (String item : items) {
	        if (inputString.contains(item)) {
	        	missingTags.add(item);
	        }
	    }
	    return missingTags;
	}

public static Set<ErrorDetails> createBusinessExceptionList(String inputFilePath) throws IOException, ParserConfigurationException, SAXException {
	RequestFile request=new RequestFile();
	Set<ErrorDetails> customErrorList=new HashSet<>();
	ErrorDetails errorDetails=null;
	String lineNo=AppConstant.BLANK;
	String dateFileCreation=AppConstant.BLANK;
	String fh_pao=AppConstant.BLANK;
	String bh_pao=AppConstant.BLANK;
	String errorCode=AppConstant.BLANK;
	String errorMsg=AppConstant.BLANK;
	BigDecimal total_Subscriber_Contribution=new BigDecimal(0);
	BigDecimal total_govt_Contribution=new BigDecimal(0);
	BigDecimal total_contribution=new BigDecimal(0);
	int totalDDO=0;
//	int sub_total_record_size=0;
	try {
		request=(RequestFile) AppUtility.generateObjectForXML(request,inputFilePath);
	} catch (JAXBException e) {
			e.printStackTrace();
	}
	
	//File Header Validation
	if((null!=request.getFileHeader().getSalDisbDate()) && (!AppUtility.isValidDateFormat(DATE_FORMAT, request.getFileHeader().getSalDisbDate())))
	{
		errorDetails=new ErrorDetails();
		lineNo=LineNumberLocator.getLineNumberOfTag("sal-disb-date", inputFilePath);
		logger.info("Invalid value found for sal-disb-date, value not in DDMMYYYY format");
		errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_FH_VALUE.getCode(),ErrorConstant.INVALID_FH_VALUE.getMessage(), AppConstant.FILE_HEADER_RECORD_TYPE,lineNo, "sal-disb-date");
		customErrorList.add(errorDetails);
	}
	if((null!=request.getFileHeader().getFileFlag()) && request.getFileHeader().getFileFlag().equalsIgnoreCase("M"))
	{
		errorDetails=new ErrorDetails();
		if(request.getFileHeader().getMonth()==null || request.getFileHeader().getMonth().equals(""))
		{
			logger.info("Month value can not be null for file flag 'M' ");
			lineNo=LineNumberLocator.getLineNumberOfTag("month", inputFilePath);
			errorDetails=AppUtility.createErrorObject(ErrorConstant.FILE_HEADER_VALUE_NOT_FOUND.getCode(),ErrorConstant.FILE_HEADER_VALUE_NOT_FOUND.getMessage(), AppConstant.FILE_HEADER_RECORD_TYPE,lineNo, "month");
			customErrorList.add(errorDetails);
		}
		else if(request.getFileHeader().getYear()==null || request.getFileHeader().getYear().equals(""))
		{
			logger.info("Year value can not be null for file flag 'M' ");
			lineNo=LineNumberLocator.getLineNumberOfTag("year", inputFilePath);
			errorDetails=AppUtility.createErrorObject(ErrorConstant.FILE_HEADER_VALUE_NOT_FOUND.getCode(),ErrorConstant.FILE_HEADER_VALUE_NOT_FOUND.getMessage(), AppConstant.FILE_HEADER_RECORD_TYPE,lineNo, "year");
			customErrorList.add(errorDetails);
		}
	}else if(request.getFileHeader().getFileFlag().equalsIgnoreCase("A")){
		if(request.getFileHeader().getYear()!=null || request.getFileHeader().getMonth()!=null)
		{
			lineNo=LineNumberLocator.getLineNumberOfTag("file-flag", inputFilePath);
			logger.info("If file flag is A year and month must be null");
			errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_FH_VALUE.getCode(),ErrorConstant.INVALID_FH_VALUE.getMessage(), AppConstant.FILE_HEADER_RECORD_TYPE,lineNo, "file-flag");
			customErrorList.add(errorDetails);
		}
}
	
	dateFileCreation=request.getBatchHeader().getDateFileCreation();
	//Batch Header Validation
	if((null!=dateFileCreation) && (!AppUtility.isValidDateFormat(DATE_FORMAT, dateFileCreation)))
	{
		errorDetails=new ErrorDetails();
		lineNo=LineNumberLocator.getLineNumberOfTag("date-file-creation", inputFilePath);
		String isDateValid=AppUtility.isValidDateFileCreation(dateFileCreation);
		if(null!=isDateValid)
		{
			if(isDateValid.contains("Future Date"))
			{
				errorCode=ErrorConstant.INVALID_FILE_CREATION_DATE.getCode();
				errorMsg=ErrorConstant.INVALID_FILE_CREATION_DATE.getMessage();
				logger.info("Invalid value found for date-file-creation, Input date is future Date");
			}
			else
			{
				errorCode=ErrorConstant.INVALID_FILE_CREATION_YEAR.getCode();
				errorMsg=ErrorConstant.INVALID_FILE_CREATION_YEAR.getMessage();
				logger.info("Invalid value found for date-file-creation, Input year is less than 2004");
			}
			errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.BATCH_HEADER_RECORD_TYPE,lineNo, "date-file-creation");
			customErrorList.add(errorDetails);	
		}
		else
		{
			logger.info("Invalid value found for sal-disb-date, value not in DDMMYYYY format");
			errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_VALUE.getCode(),ErrorConstant.INVALID_BH_VALUE.getMessage(), AppConstant.BATCH_HEADER_RECORD_TYPE,lineNo, "date-file-creation");
			customErrorList.add(errorDetails);	
		}
	}
	fh_pao=request.getFileHeader().getPaoRegNo();
	bh_pao=request.getBatchHeader().getPaoRegNo();
	if(null!=fh_pao && null!=bh_pao)
	{
		errorDetails=new ErrorDetails();
		if(!fh_pao.equals(bh_pao))
		{
			lineNo=LineNumberLocator.getLineNumberOfTag("pao-reg-no", inputFilePath);
			logger.info("Invalid value found for PAO_REG_NO, Value of File header and batch headr mismatched");	
			errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BH_PAO_REG_NO.getCode(),ErrorConstant.INVALID_BH_PAO_REG_NO.getMessage(), AppConstant.BATCH_HEADER_RECORD_TYPE,lineNo, "pao-reg-no");
			customErrorList.add(errorDetails);
		}
		if(null!=request.getBatchHeader().getBatchId())
		{
			errorDetails=new ErrorDetails();
			lineNo=LineNumberLocator.getLineNumberOfTag("batch-id", inputFilePath);
			String batchIdAfterTruncate=request.getBatchHeader().getBatchId().substring(0,7);
			if(!bh_pao.equals(batchIdAfterTruncate))
			{
				errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_BATCH_ID.getCode(),ErrorConstant.INVALID_BATCH_ID.getMessage(), AppConstant.BATCH_HEADER_RECORD_TYPE,lineNo, "batch-id");
				customErrorList.add(errorDetails);
			}	
		}
	}
	if(null!=request.getBatchHeader().getContrFileType())
	{
		if(request.getBatchHeader().getContrFileType().equalsIgnoreCase("C"))
		{
			errorDetails=new ErrorDetails();
			if(request.getBatchHeader().getTxnId()==null || request.getBatchHeader().getTxnId().equals(""))
			{
				logger.info("Txn Id can not be null for file flag 'C' ");
				lineNo=LineNumberLocator.getLineNumberOfTag("txn-id", inputFilePath);
				errorDetails=AppUtility.createErrorObject(ErrorConstant.BATCH_HEADER_VALUE_NOT_FOUND.getCode(),ErrorConstant.BATCH_HEADER_VALUE_NOT_FOUND.getMessage(), AppConstant.BATCH_HEADER_RECORD_TYPE,lineNo, "txn-id");
				customErrorList.add(errorDetails);
			}	
		}
	}
	try
	{
		totalDDO=AppUtility.calculateTotalDDO(inputFilePath);
		logger.info("Total number of unique DDO found in the file:"+totalDDO);
		errorDetails=new ErrorDetails();
		lineNo=LineNumberLocator.getLineNumberOfTag("total-ddo", inputFilePath);
		String recordType=AppConstant.BATCH_HEADER_RECORD_TYPE;
		if(totalDDO==0)
		{
			errorCode=ErrorConstant.INVALID_BH_HEADER_COUNT.getCode();
			errorMsg=ErrorConstant.INVALID_BH_HEADER_COUNT.getMessage();
			errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, recordType,lineNo, "total-ddo");
			customErrorList.add(errorDetails);
		}
		else if(null!=request.getBatchHeader().getTotalDdo())
		{
			if(request.getBatchHeader().getTotalDdo()<totalDDO)
			{
			errorCode=ErrorConstant.DDO_HEADER_MORE_THAN_REQUIRED.getCode();
			errorMsg=ErrorConstant.DDO_HEADER_MORE_THAN_REQUIRED.getMessage();
			errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, recordType,lineNo, "total-ddo");
			customErrorList.add(errorDetails);
			}
			else if (request.getBatchHeader().getTotalDdo()>totalDDO)
			{
				errorCode=ErrorConstant.DDO_HEADER_LESS_THAN_REQUIRED.getCode();
				errorMsg=ErrorConstant.DDO_HEADER_LESS_THAN_REQUIRED.getMessage();
				errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, recordType,lineNo, "total-ddo");
				customErrorList.add(errorDetails);
			}
		}
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	if(null!=request.getSubscriberDtl())
	{
		errorDetails=new ErrorDetails();
		lineNo=LineNumberLocator.getLineNumberOfTag("no-subscriber-records", inputFilePath);
		
		inputFilePath.replaceAll("<(\\w+)></\\1>\n\\s+", "");
		
		if(request.getSubscriberDtl().size()>request.getBatchHeader().getNoSubscriberRecords())
		{
			errorCode=ErrorConstant.SUBSCRIBER_COUNT_LESS_THN_REQUIRED.getCode();
			errorMsg=ErrorConstant.SUBSCRIBER_COUNT_LESS_THN_REQUIRED.getMessage();	
			errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.BATCH_HEADER_RECORD_TYPE,lineNo, "no-subscriber-records");
			customErrorList.add(errorDetails);
		}
		else if(request.getSubscriberDtl().size()<request.getBatchHeader().getNoSubscriberRecords())
		{
			errorCode=ErrorConstant.SUBSCRIBER_COUNT_MORE_THAN_REQUIRED.getCode();
			errorMsg=ErrorConstant.SUBSCRIBER_COUNT_MORE_THAN_REQUIRED.getMessage();
			errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.BATCH_HEADER_RECORD_TYPE,lineNo, "no-subscriber-records");
			customErrorList.add(errorDetails);
		}
	
	}
	
	for (SubscriberDdtlType subscriberDetail : request.getSubscriberDtl())
	{
		total_Subscriber_Contribution=total_Subscriber_Contribution.add(subscriberDetail.getSubscriberContrAmt());
		total_govt_Contribution=total_govt_Contribution.add(subscriberDetail.getGovtContrAmt());		
	}
	total_contribution=total_Subscriber_Contribution.add(total_govt_Contribution);
	if(request.getBatchHeader().getControlTotalSubContr().setScale(0,RoundingMode.HALF_UP).compareTo(total_Subscriber_Contribution.setScale(0,RoundingMode.HALF_UP))!=0)
	{
		errorDetails=new ErrorDetails();
		lineNo=LineNumberLocator.getLineNumberOfTag("control-total-sub-contr", inputFilePath);
		errorCode=ErrorConstant.INVALID_CONTRIBUTION_TOTAL.getCode();
		errorMsg=ErrorConstant.INVALID_CONTRIBUTION_TOTAL.getMessage();
		errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.BATCH_HEADER_RECORD_TYPE,lineNo, "control-total-sub-contr");
		customErrorList.add(errorDetails);
			
	}
	if((request.getBatchHeader().getControlTotalGovtContr().setScale(0,RoundingMode.HALF_UP)).compareTo(total_govt_Contribution.setScale(0,RoundingMode.HALF_UP))!=0)
	{
		errorDetails=new ErrorDetails();
		lineNo=LineNumberLocator.getLineNumberOfTag("control-total-govt-contr", inputFilePath);
		errorCode=ErrorConstant.INVALID_CONTRIBUTION_TOTAL.getCode();
		errorMsg=ErrorConstant.INVALID_CONTRIBUTION_TOTAL.getMessage();
		errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.BATCH_HEADER_RECORD_TYPE,lineNo, "control-total-govt-contr");
		customErrorList.add(errorDetails);
	}
	if((request.getBatchHeader().getGrandTotal()).setScale(0,RoundingMode.HALF_UP).compareTo(total_contribution.setScale(0,RoundingMode.HALF_UP))!=0)
	{
		errorDetails=new ErrorDetails();
		lineNo=LineNumberLocator.getLineNumberOfTag("grand-total", inputFilePath);
		errorCode=ErrorConstant.INVALID_GRAND_TOTAL_IN_BH.getCode();
		errorMsg=ErrorConstant.INVALID_GRAND_TOTAL_IN_BH.getMessage();
		errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.BATCH_HEADER_RECORD_TYPE,lineNo, "grand-total");
		customErrorList.add(errorDetails);
	}
	
	
	//SUBSCRIBER VALIDATIONS
	if(null!=request.getSubscriberDtl() && request.getSubscriberDtl().size()>0)
	{
		errorDetails=new ErrorDetails();
		//Subscription_Detail validation
        for (SubscriberDdtlType subscriberDetail : request.getSubscriberDtl())
        {
        	if(null!=subscriberDetail.getContrType())
        	{
        		if(subscriberDetail.getContrType().equals("C"))
        		{
        			if(subscriberDetail.getContrMonth()==null || subscriberDetail.getContrMonth().equals(""))
        			{
						lineNo=LineNumberLocator.getLineNumberOfTag("contr-month", inputFilePath);
						errorCode=ErrorConstant.SD_VALUE_NOT_FOUND.getCode();
        				errorMsg=ErrorConstant.SD_VALUE_NOT_FOUND.getMessage();
        				errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.SUBSCRIBER_HEADER_RECORD_TYPE,lineNo, "contr-month");
        				customErrorList.add(errorDetails);
        			}
					if (subscriberDetail.getContrYear()==null || subscriberDetail.getContrYear().equals(""))
        			{
            			lineNo=LineNumberLocator.getLineNumberOfTag("contr-year", inputFilePath);
        				errorCode=ErrorConstant.SD_VALUE_NOT_FOUND.getCode();
        				errorMsg=ErrorConstant.SD_VALUE_NOT_FOUND.getMessage();
        				errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.SUBSCRIBER_HEADER_RECORD_TYPE,lineNo, "contr-year");
        				customErrorList.add(errorDetails);	
        			}
					if(request.getFileHeader().getFileFlag().equals("M") )
					{
						if(!subscriberDetail.getContrMonth().equals(request.getFileHeader().getMonth())) {
							errorDetails=new ErrorDetails();
							lineNo=LineNumberLocator.getLineNumberOfTag("contr-month", inputFilePath);
							errorCode = ErrorConstant.MONTH_OR_YEAR_NOT_MATCHING.getCode();
							errorMsg = ErrorConstant.MONTH_OR_YEAR_NOT_MATCHING.getMessage();
							errorDetails = AppUtility.createErrorObject(errorCode, errorMsg, AppConstant.SUBSCRIBER_HEADER_RECORD_TYPE, lineNo, "contr-month");
							customErrorList.add(errorDetails);
						}
						if (!subscriberDetail.getContrYear().equals(request.getFileHeader().getYear()))
						{
							errorDetails=new ErrorDetails();
							lineNo=LineNumberLocator.getLineNumberOfTag("contr-year", inputFilePath);
							errorCode=ErrorConstant.MONTH_OR_YEAR_NOT_MATCHING.getCode();
							errorMsg=ErrorConstant.MONTH_OR_YEAR_NOT_MATCHING.getMessage();
							errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.SUBSCRIBER_HEADER_RECORD_TYPE,lineNo, "contr-year");
							customErrorList.add(errorDetails);
						}
					}
        		} else if(subscriberDetail.getContrType().equalsIgnoreCase("A")){
					errorDetails=new ErrorDetails();
					lineNo=LineNumberLocator.getLineNumberOfTag("contr-month", inputFilePath);
					if (subscriberDetail.getRemarks().equals("") || !subscriberDetail.getRemarks().toLowerCase().matches("(arrears|da arrears|festival advance|leave arrears|pay difference|" +
							"earned leave|interest|others).*") && subscriberDetail.getRemarks().length() <= 75)
					{
						errorCode=ErrorConstant.SD_VALUE_NOT_FOUND.getCode();
						errorMsg=ErrorConstant.SD_VALUE_NOT_FOUND.getMessage();
						errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.SUBSCRIBER_HEADER_RECORD_TYPE,lineNo, "remarks");
						customErrorList.add(errorDetails);
					}
				}
        	}
        	
			
        	if((subscriberDetail.getSubscriberContrAmt().add(subscriberDetail.getGovtContrAmt()).setScale(0,RoundingMode.HALF_UP).compareTo(subscriberDetail.getTotalContrSubscriber().setScale(0,RoundingMode.HALF_UP)))!=0)
        	{
        		errorDetails=new ErrorDetails();
    			lineNo=LineNumberLocator.getLineNumberOfTag("total-contr-subscriber", inputFilePath);
        		errorCode=ErrorConstant.INVALID_SUB_CONTR_AMT.getCode();
				errorMsg=ErrorConstant.INVALID_SUB_CONTR_AMT.getMessage();
				errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.SUBSCRIBER_HEADER_RECORD_TYPE,lineNo, "total-contr-subscriber");
				customErrorList.add(errorDetails);	
        	}
			if(subscriberDetail.getRecordType().equalsIgnoreCase("SD"))
			{
				errorDetails=new ErrorDetails();
				lineNo=LineNumberLocator.getLineNumberOfTag("record-type", inputFilePath);
				errorCode=ErrorConstant.INVALID_SUB_LENGTH.getCode();
				errorMsg=ErrorConstant.INVALID_SUB_LENGTH.getMessage();
				errorDetails=AppUtility.createErrorObject(errorCode,errorMsg, AppConstant.SUBSCRIBER_HEADER_RECORD_TYPE,lineNo, "record-type");
				customErrorList.add(errorDetails);
			}
        	
        	//For Remarks
        }
        
	}
	else
	{
		errorDetails=new ErrorDetails();
		errorDetails=AppUtility.createErrorObject(ErrorConstant.INVALID_SUB_LENGTH.getCode(),ErrorConstant.INVALID_SUB_LENGTH.getCode(), AppConstant.SUBSCRIBER_HEADER_RECORD_TYPE,lineNo, "subscriber-dtl");
		customErrorList.add(errorDetails);
	}
	return customErrorList;
	
}
}
