package com.nsdl.gupshup.sms.utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.nsdl.gupshup.sms.aop.LoggingClientInfo;




@LoggingClientInfo
@Component
public class SmsUtil {
	private static final Logger  logger = LoggerFactory.getLogger(SmsUtil.class);
	
	@Value("${gupshup.sendsms.smsurl}")
	private String smsRequestUrl;
	
	@Value("${gupshup.sendsms.method}")
	private String smsMethod;
	
	@Value("${gupshup.sendsms.msg-type}")
	private String msgType;
	
	@Value("${gupshup.sendsms.user-id}")
	private String userId;
	

	@Value("${gupshup.sendsms.password}")
	private String passowrd;
		
	@Value("${gupshup.sendsms.version}")
	private String version;
	@Value("${gupshup.sendsms.format}")
	private String format;
	@Value("${gupshup.sendsms.auth-scheme}")
	private String authScheme;
	
	
	public int SendSms(String message, String mobileNo) throws Exception 
	{
		URL url;
		HttpsURLConnection connection;
		InputStream inputStream = null;
		StringBuffer response = null;
		String urlParameters="";
		String smsGatewayRequestUrl=null;
		  String resp =null;
		  int responseCode =0;
		  String mobile="91"+mobileNo;
		try 
		{
					
			// Construct request
			try
			{
				smsGatewayRequestUrl=smsRequestUrl;	
				urlParameters += smsGatewayRequestUrl;
				urlParameters += "method="+smsMethod ;
				urlParameters += "send_to=" + URLEncoder.encode(mobile,"UTF-8");
				urlParameters += "&msg=" +URLEncoder.encode(message,"UTF-8");
				urlParameters += "&msg_type="+msgType;
				urlParameters += "userid="+userId;
				urlParameters += "password="+URLEncoder.encode(passowrd,"UTF-8");
				urlParameters += "&auth_scheme="+authScheme;
				urlParameters += "v="+version;
				urlParameters += "format="+format;
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}
					
		logger.info("Parameters For the SMS API "+urlParameters);
		url = new URL(urlParameters);
		logger.info("URL::"+url);
			if(url.toString().startsWith("https"))
			{
				 if(doTrustToCertificates())
	             {
					logger.info("Post Parameter:" +urlParameters);
					connection = (HttpsURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setRequestProperty("Content-Length",Integer.toString(smsGatewayRequestUrl.getBytes().length));
					//connection.setRequestProperty("Content-Language", "en-US");
					connection.setDoOutput(true);
		
					DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
					wr.writeBytes(smsGatewayRequestUrl);
					wr.flush();
					wr.close();
					responseCode = connection.getResponseCode();
					//System.out.println("Response Code from SMS API: " + responseCode);
					 response = new StringBuffer();
				    inputStream = connection.getInputStream();
		            if(responseCode == 200 && inputStream != null)
		            {
		            	 logger.info((new StringBuilder()).append("Response Code : ").append(responseCode).toString());
		                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		                String inputLine;
		                while((inputLine = in.readLine()) != null) 
		                 response.append(inputLine);
		                in.close();
		              logger.info("SMS sent successfully");
		            }
		            resp = response.toString();
		            logger.info("Response String from SMS API : "+resp);
		            if(connection!=null)
		            {
		            	 connection.disconnect();	
		            }
			            
		                       
		            }
		        }
		
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return responseCode;
		
	}
			
			
/*	public static void main(String args[])
	{
		try
		{
			String msg="Dear User, Your registration request at NSDL's Telemedicine is verified successfully. Regards, NSDL e-Gov";
			StringBuilder builder = new StringBuilder(); 
			String mobile="";
			if(args.length>0)
			{
				builder.append("91");
				mobile=args[0];
				builder.append(mobile);
				//System.out.println("Trying to send sms to  : "+ builder.toString()+"...");
				SendSms(msg, builder.toString());	
			}
			else
			{
				//System.out.println("Please enter valid mobile number");
				throw new Exception("Number not provided");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	} */

	//Bypass Security
	private static boolean doTrustToCertificates() throws Exception
	{

		 //boolean bool = false;
		 logger.info("Inside doTrustToCertificates():");
		 try
		 {
			 //Security.addProvider(new Provider());
			 TrustManager[] trustAllCerts = new TrustManager[] 
					 { 
					    new X509TrustManager() {     
					        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
					            return new X509Certificate[0];
					        } 
					        public void checkClientTrusted( 
					            java.security.cert.X509Certificate[] certs, String authType) {
					            } 
					        public void checkServerTrusted( 
					            java.security.cert.X509Certificate[] certs, String authType) {
					        }
					    } 
					}; 
			 SSLContext sc = SSLContext.getInstance("SSL");
	         sc.init(null, trustAllCerts, new SecureRandom());
	         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	         HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() 
	         {
							@Override
							public boolean verify(String arg0, SSLSession arg1) 
							{
								return true;
							}
	         }
	        );
	}
	       
	
		 catch(Exception e)
		 {
			// bool = false;
			 logger.error("Exception Occurred inside doTrustToCertificates():"+e.getMessage());
	        e.printStackTrace();
	         throw e;
		 }
		return true;
	}		
	
	
	
	
}

