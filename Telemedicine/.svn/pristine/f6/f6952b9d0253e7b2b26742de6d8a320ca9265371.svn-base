package com.fvu.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.fvu.DTO.ErrorDetails;
import com.fvu.Enumeration.ErrorConstant;
import com.fvu.constant.AppConstant;



public class XmlValidator {
	
	private static final Logger logger = Logger.getLogger(XmlValidator.class.getName());
	public static void main(String args[])
	{
<<<<<<< .mine
		String inputFilePath="/home/webwerks/NeoSOFT/NSDL/Code/Files/SD001.xml";
		String outputFilePath="/home/webwerks/NeoSOFT/NSDL/Code/Files/Output File/";
||||||| .r3828
	/*	String inputFilePath="D:\\CRA\\TEST_RESULTS\\TEST_RESULTS\\TestCase1\\contr1.xml";
		String outputFilePath="D:";
=======
		String inputFilePath="/home/webwerks/Downloads/FILE002.xml";
		String outputFilePath="/home/webwerks/NeoSOFT/NSDL/Code/Files/Output File/";
>>>>>>> .r4648
		boolean fileValidationStatus=validateXML(inputFilePath,outputFilePath);
		logger.info("File Validation Status:"+fileValidationStatus);
	}
	static
	{ 
	try
	{
		InputStream inputStream = XmlValidator.class.getResourceAsStream("/logging.properties");
		 if (null != inputStream) 
		 {
		      try 
		      {
		        LogManager.getLogManager().readConfiguration(inputStream);
		      } catch (IOException e) {
		        Logger.getGlobal().log(Level.SEVERE, "Init logging system", e);
		      }
		 }
			//loggerSetup();
	}
	 catch(Exception e)
	{
	e.printStackTrace();
	 throw new RuntimeException("Problem occurred with creating log files");
	}
		
	}
	
	public XmlValidator() throws Exception{}
	
	
	public static boolean validateXML(String inputFilePath, String outputFilePath) {
		boolean isSuccess=false;
		Set<ErrorDetails> customErrorList=null;
		Schema schema=null;
		List<SAXParseException> validationErrorList=null;
		try
		{
			if(AppUtility.isPathValid(inputFilePath) && AppUtility.isPathValid(outputFilePath))
			{

				outputFilePath=AppUtility.generateValidFilePath(outputFilePath);
				//Check File Extension
				String isValidFile=AppUtility.getFileExtension(inputFilePath);
				if(isValidFile.equalsIgnoreCase(AppConstant.XML_EXTENSION))
				{
					logger.info("File extension is valid");
					//load schema
					try
					{
						schema=loadSchema(AppConstant.SCHEMA_NAME);	
					}
					catch(Exception e)
					{
						logger.severe("Failed to load  XML schema from the classpath:"+e.getMessage());
						return false; 
					}
					// Validate for XSD, if error occurred, write the error file 
					
					try
					{
						validationErrorList=validateXMLWithSchema(inputFilePath,schema);	
					}
					catch(Exception e)
					{
						logger.severe("Exception occurred while xsd validation"+e.getMessage());
						return false; 
					}
					if(validationErrorList!=null && validationErrorList.size()>0)
					{
						//create Custom Exception List
						logger.info("Schema validation fails with no of errors:"+validationErrorList.size());
						customErrorList=ErrorGenerator.createCustomExceptionList(validationErrorList,inputFilePath);
					}
					else
					{
						logger.info("File Validated Successfully with schema.Business Validation starts...");
						
						//Check for business validations 
						customErrorList=ErrorGenerator.createBusinessExceptionList(inputFilePath);
					}
					//Write the errors to File
					if(!customErrorList.isEmpty() && customErrorList.size()>0)
					{
						logger.info("File validation fails  ");
						try 
						{
							outputFilePath=outputFilePath+AppConstant.ERRORLOG+AppConstant.FILE_SEPARATOR;
							String fileVersion=AppUtility.getFileVersion(inputFilePath);
							File errorFileDtls=FileGenerator.generateErrorFile(customErrorList, inputFilePath,outputFilePath,fileVersion);
							logger.info("Error file generated Successfully at path:"+errorFileDtls.getAbsolutePath());
						}
						catch (Exception e) 
						{
							logger.severe("Exception occurred while generating error file "+e.getMessage());
							e.printStackTrace();
							return false;
						}
						//isSuccess=false;
						//return isSuccess;
					}
					//File Validated Successfully
					else
					{
						//Generate Success File
						logger.info("File Validated Successfully with all business validations");
						File newFileName=new File(inputFilePath);
						String successFileName=FileGenerator.getErrorFileName(newFileName,AppConstant.SUCCESS_EXTENSION);
						outputFilePath=outputFilePath+AppConstant.SUCCESSLOG+AppConstant.FILE_SEPARATOR+successFileName;
						logger.info("Output File path :"+outputFilePath);
						try
						{
							FileGenerator.generateSuccessFile(inputFilePath,outputFilePath);
							
						}
						catch(Exception e)
						{
							e.printStackTrace();
							return false;
						}
						isSuccess=true;
					}
				}
				else
					
				{
					logger.info("XML file not valid :"+inputFilePath);
					ErrorDetails ed=new ErrorDetails();
					customErrorList=new HashSet<>();
					ed.setErrorDetails(ErrorConstant.INVALID_FILE.getMessage());
					ed.setErrorCode(ErrorConstant.INVALID_FILE.getCode());
					ed.setLineNumber(String.valueOf(0));
					ed.setRecordType(AppConstant.BLANK);
					ed.setFieldName(AppConstant.BLANK);
					customErrorList.add(ed);
					outputFilePath=outputFilePath+AppConstant.ERRORLOG+AppConstant.FILE_SEPARATOR;
					File errorFileDtls=FileGenerator.generateErrorFile(customErrorList, inputFilePath,outputFilePath,AppConstant.BLANK);
					logger.info("Error file generated Successfully at path:"+errorFileDtls.getAbsolutePath());
					 return false;
				}
					
				
			}
		else
		{
			logger.severe("Unable to Process, incorrect path details provided to the file validation utility");
			return false;
		}
	}
	catch(Exception e)
		{
		e.printStackTrace();
		return false;
		}
		logger.info("File Validation Status:"+isSuccess);	
		return isSuccess;
	}


	public static List<SAXParseException> validateXMLWithSchema(String filPath, Schema schema) throws Exception {
		List<SAXParseException> errorList = new ArrayList<SAXParseException>();  
		 try {
			       Validator validator = schema.newValidator();
		           validator.setErrorHandler(new ErrorHandler()
		          {
		            @Override
		            public void warning(SAXParseException ex) 
		            {
		            	logger.info(ex.getMessage());
		            }

		            @Override
		            public void fatalError(SAXParseException ex) 
		            {
		            	logger.info(ex.getMessage());
		            }

		            @Override
		            public void error(SAXParseException ex) 
		            {
		            	errorList.add(ex);
		            	
		            }
		          });
				   try{
					   validator.validate(new StreamSource(new File(filPath)));
				   } catch (SAXParseException e){
					   errorList.add(e);
				   }
		      }
			 catch (IOException  | SAXException e){
				e.printStackTrace();
				throw e;
		         
		      }
			return errorList;
		 
	}



	

	/**
	 * @param schemaPath
	 * @return schema from the classPath
	 * @throws Exception 
	 */
	public static Schema loadSchema(String schemaPath) throws Exception
	   {
		   Schema schema = null;
		   try {
			   SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		         URL xsdURL = XmlValidator.class.getResource(schemaPath);
		         schema = schemaFactory.newSchema(xsdURL);
		   } catch (Exception e) {
		       e.printStackTrace();
		       throw e;
		   }
		return schema;
	   }

	public static void loggerSetup() throws IOException
	{
		
			SimpleDateFormat sdf = new SimpleDateFormat(AppConstant.DATE_FORMAT);
			sdf.setLenient(false);
			String dateString= sdf.format(new Date());
			String path = System.getProperty("user.dir");
	        String logFilePath=path+AppConstant.FILE_SEPARATOR+AppConstant.LOGGER_FILE_PATH+AppConstant.FILE_SEPARATOR+dateString+AppConstant.FILE_SEPARATOR+AppConstant.LOG_FILE_NAME;
	         File file = new File(logFilePath);
				  if(!file.exists())
				  {
					  file.getParentFile().mkdirs();
				  }
				//FileHandler fh=new FileHandler(logFilePath,true);
				FileHandler fh=new FileHandler(logFilePath);
				fh.setLevel(Level.ALL);
				logger.addHandler(fh);
				fh.setFormatter(new SimpleFormatter());	
			}



}
