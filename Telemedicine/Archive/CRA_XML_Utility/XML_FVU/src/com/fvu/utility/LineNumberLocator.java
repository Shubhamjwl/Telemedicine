package com.fvu.utility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.fvu.constant.AppConstant;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class LineNumberLocator extends DefaultHandler{
	
	private static final Logger logger = Logger.getLogger(LineNumberLocator.class.getName());
		
	 /**
	 * @param tagName
	 * @param inputFilePath
	 * @return line number of the specified tag in xml document
	 */
	public static String getLineNumberOfTag(String tagName,String inputFilePath)
	  {
		  String lineNo="";
		  InputStream is;
		  Document doc = null;
		  File file=new File(inputFilePath);
		  if(file.exists())
		  {
			try 
			{
				if(tagName!=null && tagName!="")
				{
					is = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));
					try
					{
						doc = PositionalXMLReader.readXML(is);
					} catch (SAXException e) 
					{
						e.printStackTrace();
					}
					is.close();

					Node node = doc.getElementsByTagName(tagName).item(0);
					if(node!=null)
					{
						//logger.info("Line number: " + node.getUserData("lineNumber"));
						lineNo=node.getUserData("lineNumber").toString();
					}
					else
					{
						logger.info("XML File does not conatin the tag with name :"+tagName);
					}
					
				}
			     
			} 
			catch (IOException e) 
			{
				
				e.printStackTrace();
			}
		  }
		  return lineNo;
		  
	  }

	public static String getTagValueByTagName(String tagName,String inputFilePath)
	  {
		  String value=null;
		  InputStream is;
		  Document doc = null;
		  File file=new File(inputFilePath);
		  if(file.exists())
		  {
			if(null!=tagName && tagName!=AppConstant.BLANK)
			{
				  try 
					{
						  is = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));
						try
						{
							doc = PositionalXMLReader.readXML(is);
						} catch (SAXException e) {
								e.printStackTrace();
						}
					      is.close();
					      Node node = doc.getElementsByTagName(tagName).item(0);
					      if(node!=null)
					      {
					    	   value=node.getTextContent(); 
					      }
					     					   
					} 
					catch (IOException e) 
					{
						
						e.printStackTrace();
					}
				  }	
			}
			
		  return value;
		  
	  }


}
