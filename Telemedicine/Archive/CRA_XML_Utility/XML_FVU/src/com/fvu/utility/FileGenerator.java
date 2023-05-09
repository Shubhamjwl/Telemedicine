package com.fvu.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fvu.DTO.ErrorDetails;
import com.fvu.Request.ContributionHash;
import com.fvu.Request.RequestFile;
import com.fvu.Request.SubscriberDdtlType;
import com.fvu.constant.AppConstant;


public class FileGenerator {
	
	private static final Logger logger = Logger.getLogger(FileGenerator.class.getName());
	 public static File generateErrorFile(Set<ErrorDetails> errorsList,String inputFileName,String outFile, String fileVersion)
		{
		 String errorFileName = AppConstant.BLANK;
		 File errorFile = null;
		 try
		 {
			 if(inputFileName!=AppConstant.BLANK && outFile!=AppConstant.BLANK)
			 {
			 errorFileName = getErrorFileName(new File(inputFileName),AppConstant.FAIL_EXTENSION);
			 errorFile = new File(outFile+errorFileName);
			 if(!errorFile.exists())
			  {
				 errorFile.getParentFile().mkdirs();
			  }
		       String footerString="</TABLE> <TABLE class=text><TR><TD>* Field Name & No. is as per the file format</TD></TR><TR><TD>FVU Version : @version</TD></TR><TR><TD> Input File Name : @inputFilePath</TD></TR> </TABLE></BODY></HTML>";
		       String errorString="<HTML><HEAD><TITLE></TITLE></HEAD><STYLE type='text/css'> .tdColumnDetails {text-align: center;} .text {COLOR : #000000;FONT-FAMILY : Verdana, Arial, Helvetica, sans-serif;FONT-SIZE : 10pt;FONT-WEIGHT : normal;}.tabledetails{BACKGROUND-COLOR : #EEEECC;COLOR :  #000000;FONT-FAMILY : helvetica, serif, Arial;FONT-SIZE : 10pt;FONT-WEIGHT : bold;} .table {BACKGROUND-COLOR : #006699;COLOR : white;FONT-FAMILY : helvetica, serif, Arial;FONT-SIZE : 10pt;FONT-WEIGHT : normal;} .H2{FONT-WEIGHT: bold;FONT-SIZE: 12pt;MARGIN: 5px;COLOR: #006699;FONT-FAMILY: Arial, Helvetica, sans-serif;TEXT-ALIGN: center}</STYLE><BODY><p class=H2>SUBSCRIBER CONTRIBUTION ERROR FILE<!-- FIlE HEADING --></p><BR><TABLE border=1 class=table><!-- TABLE HEADER --><TR class=tdColumnDetails><TD WIDTH=70 VALIGN=top ><B>Line No</B></CENTER></TD><TD WIDTH=85 VALIGN=top><B>Record Type</B></CENTER></TD><TD WIDTH=85 VALIGN=top><B>Field Name & No.*</B></CENTER></TD><TD WIDTH=85 VALIGN=top><B>DDO Serial No.</B></CENTER></TD><TD WIDTH=85 VALIGN=top><B>    Subscriber Contribution Serial No.   </B></CENTER></TD> <TD WIDTH=85 VALIGN=top><B> PRAN </B></CENTER></TD> <TD WIDTH=130 VALIGN=top><B>    Error Code   </B></CENTER></TD><TD WIDTH=450 VALIGN=top><B>Error Description</B></CENTER></TD><TD WIDTH=130 VALIGN=top><B>Error / Warning</B></CENTER></TD></TR>";
		       for (ErrorDetails errorDetails : errorsList) {

		    	   logger.info("Error:"+errorDetails.getErrorDetails());
		    	   
		    	   errorString+="<TR class=tabledetails><TD ALIGN=RIGHT WIDTH=70>@lineNo</TD>";
		    	   errorString+="<TD ALIGN=LEFT WIDTH=85>@headerValue</TD>";
		    	   errorString+="<TD ALIGN=LEFT WIDTH=50>@fieldName</TD><TD ALIGN=LEFT WIDTH=85>-</TD>";
		    	   errorString+="<TD ALIGN=LEFT WIDTH=85>-</TD><TD ALIGN=LEFT WIDTH=85>-</TD>";
		    	   errorString+="<TD ALIGN=LEFT WIDTH=130>@errorCode</TD><TD ALIGN=LEFT WIDTH=450>@errorMessage</TD>";
		    	   errorString+="<TD ALIGN=LEFT WIDTH=130>-</TD></TR>";

				   /*Integer lineNo = Integer.parseInt(errorDetails.getLineNumber()) + 1;
				   errorDetails.setLineNumber(lineNo.toString());*/
		    	   errorString=errorString.replace("@errorCode", errorDetails.getErrorCode());
		    	   errorString=errorString.replace("@errorMessage", errorDetails.getErrorDetails());
		    	   errorString=errorString.replace("@lineNo", errorDetails.getLineNumber());
		    	   errorString=errorString.replace("@headerValue", errorDetails.getRecordType());
		    	   errorString=errorString.replace("@fieldName", errorDetails.getFieldName());
		    	 }
		       errorString+=footerString; 
		       errorString=errorString.replace("@inputFilePath", inputFileName);
		       errorString=errorString.replace("@version", fileVersion);
		       try
		       {
		    	   BufferedWriter bw = new BufferedWriter(new FileWriter(errorFile));
			        bw.write(errorString);
			        bw.close();   
		       }
		       catch(Exception e)
		       {
		    	  logger.severe("Exception  occurred while Writing to error file");
		    	   e.printStackTrace();
		       } 
			 }
		 }
		catch(Exception e)
		 {
			 logger.severe("Exception  occurred while Creating  error file");
			e.printStackTrace();
			throw e;
		 }
		return errorFile;
	      
		 
		}
	 
	 
	 public static String getErrorFileName(File f, String newExtension) {
		  int i = f.getName().lastIndexOf('.');
		  String name = f.getName().substring(0,i);
		  //return new File(f.getParent(), name + newExtension);
		  return (name + newExtension);
		}


	 public static void generateSuccessFile(String inputFilePath,String outFile)
	 {
		 RequestFile request=new RequestFile();
		 ArrayList<SubscriberDdtlType> subscriberList=new ArrayList<>();
		 ArrayList<String> list = new ArrayList<String>();
		 try
		 {
			 request=(RequestFile) AppUtility.generateObjectForXML(request,inputFilePath);
			// System.out.println("Request::"+request.toString());
			 for (SubscriberDdtlType obj : request.getSubscriberDtl()) {
					subscriberList.add(obj);
				}
				//Collections.sort(subscriberList);
				request.setSubscriberDtl(subscriberList);
				// Now Group by DDO_REG_NO
		         Map<String, List<SubscriberDdtlType>> subscriberByDdoMap= subscriberList.stream().collect(Collectors.groupingBy(SubscriberDdtlType::getDdoRegNo,LinkedHashMap::new, Collectors.toList()));
		         //Calculate Subscriber Details List and respective DDO HEaders
		         outFile=AppUtility.calculateSubscriberDetails(subscriberByDdoMap,outFile,inputFilePath);
		         logger.info("Subscriber Details calulated successfully ");
		         //Calculate Hash
		     	 list=AppUtility.readFile(outFile);
		     	 logger.info("Calculating hash....");
		     	 try
		     	 {
		     		ArrayList<String>stringWithHash=ContributionHash.processFTCHash(list);
		     		 logger.info("Hash Calculated Sucessfully....");
		     		AppUtility.writeFile(stringWithHash, outFile); 
		     		logger.info("File Write Successfully at Path:"+outFile);
			     
		     	 }
		     	 catch(Exception e)
		     	 {
		     		 logger.severe("Failed to write hash value"+outFile);
		     		 throw e;
		     	 }
		     
		     	
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
	 }
	 
	
}
