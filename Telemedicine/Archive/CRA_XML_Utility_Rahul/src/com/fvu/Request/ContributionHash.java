package com.fvu.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.fvu.constant.AppConstant;



public class ContributionHash {

	
	private static final Logger logger = Logger.getLogger(ContributionHash.class.getName());


	
	//Process Hash
	public static ArrayList<String> processFTCHash(ArrayList<String> finalList) {
		String recStr;
		int recNo = 0;
		long fileLevelHC=0;
		long rlh = 0;
		recNo = 1;

		recStr = (String) finalList.get(0);
		//fileLevelHC = hashCode(recStr, true);
		String recordStr =AppConstant.BLANK;
		for (int i = 1; i < finalList.size(); i++) {
			recNo++;
			recordStr = (String) finalList.get(i);

			rlh = hashCode(recordStr, false);
			fileLevelHC += (recNo * rlh);
			recordStr = recordStr + get20digitHashCode(rlh);
			finalList.set(i, recordStr);
		}
		
		//FH Records
		//8 FVU File Level Hash
		/*recStr = (String) finalList.get(0);
		recStr +="^";
		recStr += get20digitHashCode(fileLevelHC);
		//9 10 11 12 Filler
		recStr += "^^^^"; */
		
		//Changed
		String ver=getFileVersion(recStr);
		String withoutVer=recStr.substring(0,recStr.lastIndexOf("^"))+"^";
	//	System.out.println("Without version:"+withoutVer);
		//withoutVer=removeOptionalMsg(withoutVer);	
		long fhHash=hashCode(withoutVer,false);
		withoutVer=withoutVer+get20digitHashCode(fhHash)+"^";
		String withVersion=withoutVer+ver+"^";
		long fileh=hashCode(withVersion,false);
		//System.out.println("File level hash:"+fileh);
		fileh+=fileLevelHC;
		withVersion += get20digitHashCode(fileh);
		//9 10 11 12 Filler
		//withVersion += "^^^^";
		finalList.set(0, withVersion);
		return finalList;
	}
	
	
	
	public static String removeOptionalMsg(String strin)
	{
		List<String> result=new ArrayList<String>();
		String [] str=strin.split("\\^");
		List<String> list=Arrays.asList(str);
		for (String string : list) {
			if(string!=null &&! string.isEmpty())
			{
				result.add(string);		}
		}
		String finalString=String.join("^", result);
		StringBuilder sb=new StringBuilder(finalString);
		//sb.append("^");
		return sb.toString();
	}
	
	//	Hash functions
	public static long getHashofLine(String str)
	{
		long lineHash = 0;
		try
		{
			lineHash = hashCode(str, false);
		}
		catch (Exception e)
		{
			logger.info("Exception occured in getting the Hash of Line"+e);
			return 0;
		}
		return lineHash;
	}
	
	public static long hashCode(String recStr, boolean includeCaret)
	{
		long hc = 0;
		int i = 0;
		for (; i < recStr.length(); i++)
		{
			hc += (((int) recStr.charAt(i)) * (i + 1));
		}
		if (includeCaret == true)
			hc += ((i + 1) * ((int) '^'));
		return hc;
	}

	public static String get20digitHashCode(long hashCode)
	{
		String hashCodeStr = Long.toString(hashCode);
		StringBuffer sb = new StringBuffer();
		for (int i = hashCodeStr.length(); i < 20; i++)
			sb.append('0');
		sb.append(hashCodeStr);
		return sb.toString();
	}
	
	private static String getFileVersion(String recStr) {
		String ver=AppConstant.BLANK;	
		if(null!=recStr && recStr!=AppConstant.BLANK)
			{
				String[] responseArray = recStr.split("\\^");
				if(responseArray.length>0)
				{
					int leng=responseArray.length;
					ver=responseArray[leng-1];
				}	
				logger.info("File Version:"+ver);
			}
			return ver;
		}
}
