

document.write('<applet code="Legend.BiomAPI.class" width="0" height="0" MAYSCRIPT archive="BiomAPI.jar" id="Obj"></applet>');

function GetFeatureBFDData(iFingerID)
{
	Obj.PidXMLVersion("1.0");
	Obj.GetFeatureBFDData(iFingerID);
	var capAttempt = Obj.CaptureAttempt();
	$( document ).ready(function() { 	
		$(".handdiv ."+iFingerID).html("<img src=\"image/check.jpg\"></img>");

		});
	
}
function FormBFDData ()
{
	document.getElementsByName("pidXML")[0].value = "";
	document.getElementsByName("encSession")[0].value = "";
	document.getElementsByName("Session")[0].value = "";
	document.getElementsByName("certExpiryDate")[0].value = "";
	document.getElementsByName("encHmac")[0].value = "";
	document.getElementsByName("serialNo")[0].value = "";
	
	var lsDataXml = null;
	var lsUidaiSessionKey = null;
	var lsLSSessionKey = null;
	var lsUidaiCertExpiryDate = null;
	var lsHmacXml = null;
	var serialNo = null;
	
	Obj.FormBFDXML();
	
	serialNo=Obj.SerialNumber();
	lsTerminalID = Obj.TerminalID();
	lsDataXml = Obj.PidXMLData();		
	lsUidaiSessionKey = Obj.EncSessionKey();		
	lsLSSessionKey = Obj.SessionKey();	
	lsUidaiCertExpiryDate = Obj.CertExpiryDate();
	lsHmacXml = Obj.EncHmacData();
	
	if(lsDataXml != "" && lsDataXml != "" && lsUidaiSessionKey != "" && lsUidaiCertExpiryDate != "" && lsHmacXml != "" &&lsDataXml != null && lsDataXml != null && lsUidaiSessionKey != null && lsUidaiCertExpiryDate != null && lsHmacXml != null && serialNo !=null)
	{
	document.getElementsByName("pidXML")[0].value = lsDataXml;
	document.getElementsByName("encSession")[0].value = lsUidaiSessionKey;
	document.getElementsByName("Session")[0].value = lsLSSessionKey;
	document.getElementsByName("certExpiryDate")[0].value = lsUidaiCertExpiryDate;
	document.getElementsByName("encHmac")[0].value = lsHmacXml;
	document.getElementsByName("serialNo")[0].value = serialNo;
	
	alert("Fingerprint captured");
	}
	else
	{
	alert("Fingerprint not captured");
	}
	
}
function getFeatureUIDAIData (iFingerID)
{
	document.getElementsByName("pidXML")[0].value = "";
	document.getElementsByName("encSession")[0].value = "";
	document.getElementsByName("Session")[0].value = "";
	document.getElementsByName("certExpiryDate")[0].value = "";
	document.getElementsByName("encHmac")[0].value = "";
	document.getElementsByName("serialNo")[0].value = "";
	var lsUidaiSessionKey = null;
	var lsLSSessionKey = null;
	var lsUidaiCertExpiryDate = null;
	var lsHmacXml = null;
	var serialNo = null;
	Obj.PidXMLVersion("2.0");
	Obj.GetFeatureUIDAIData(iFingerID);
	serialNo=Obj.SerialNumber();
	lsTerminalID = Obj.TerminalID();
	lsDataXml = Obj.PidXMLData();		
	lsUidaiSessionKey = Obj.EncSessionKey();		
	lsLSSessionKey = Obj.SessionKey();	
	lsUidaiCertExpiryDate = Obj.CertExpiryDate();
	lsHmacXml = Obj.EncHmacData();
	timestamp=Obj.TimeStamp();
	if(lsDataXml != "" && lsDataXml != "" && lsUidaiSessionKey != "" && lsUidaiCertExpiryDate != "" && lsHmacXml != "" &&lsDataXml != null && lsDataXml != null && lsUidaiSessionKey != null && lsUidaiCertExpiryDate != null && lsHmacXml != null && serialNo != null)
	{
	document.getElementsByName("pidXML")[0].value = lsDataXml;
	document.getElementsByName("encSession")[0].value = lsUidaiSessionKey;
	document.getElementsByName("Session")[0].value = lsLSSessionKey;
	document.getElementsByName("certExpiryDate")[0].value = lsUidaiCertExpiryDate;
	document.getElementsByName("encHmac")[0].value = lsHmacXml;
	document.getElementsByName("serialNo")[0].value = serialNo;
	getUdcFromDevice(serialNo);

		$( document ).ready(function() { 	
			$(".handdiv ."+iFingerID).html("<img src=\"image/check.jpg\"></img>");
	
		});
	
		
	}
	else
	{
	alert("Fingerprint not captured");
	}
	
}


function ClearBFDData()
{
	document.getElementsByName("uid")[0].value="";
	document.getElementsByName("fingpos")[0].value="0";
    Obj.ClearBFDData();
}

function GetFeatureTFAData (iFingerID)
{
	var iCaptureAttempt = document.getElementsByName("capAttempt")[0].value;

	document.getElementsByName("pidXML")[0].value = "";
	document.getElementsByName("encSession")[0].value = "";
	document.getElementsByName("Session")[0].value = "";
	document.getElementsByName("certExpiryDate")[0].value = "";
	document.getElementsByName("encHmac")[0].value = "";
	document.getElementsByName("serialNo")[0].value = "";
	
	var lsDataXml = null;
	var lsUidaiSessionKey = null;
	var lsLSSessionKey = null;
	var lsUidaiCertExpiryDate = null;
	var lsHmacXml = null;
	var serialNo = null;

	Obj.PidXMLVersion("2.0");
	Obj.GetFeatureTFAData(iFingerID,iCaptureAttempt);
	
	serialNo=Obj.SerialNumber();
	lsTerminalID = Obj.TerminalID();
	lsDataXml = Obj.PidXMLData();		
	lsUidaiSessionKey = Obj.EncSessionKey();		
	lsLSSessionKey = Obj.SessionKey();	
	lsUidaiCertExpiryDate = Obj.CertExpiryDate();
	lsHmacXml = Obj.EncHmacData();
	
	if(lsDataXml != "" && lsDataXml != "" && lsUidaiSessionKey != "" && lsUidaiCertExpiryDate != "" && lsHmacXml != "" &&lsDataXml != null && lsDataXml != null && lsUidaiSessionKey != null && lsUidaiCertExpiryDate != null && lsHmacXml != null && serialNo != null)
	{

	document.getElementsByName("pidXML")[0].value = lsDataXml;
	document.getElementsByName("encSession")[0].value = lsUidaiSessionKey;
	document.getElementsByName("Session")[0].value = lsLSSessionKey;
	document.getElementsByName("certExpiryDate")[0].value = lsUidaiCertExpiryDate;
	document.getElementsByName("encHmac")[0].value = lsHmacXml;
	document.getElementsByName("serialNo")[0].value = serialNo;
	
  $( document ).ready(function() {
                $(".handdiv ."+iFingerID).html("<img src=\"image/check.jpg\"></img>");

                });

	}
	else
	{
	alert("Fingerprint not captured");
	}
}



function getUdcFromDevice(serialNo)
{
	var xmlhttp1;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp1=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp1=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp1.onreadystatechange=function()
	{
		if (xmlhttp1.readyState==4 && xmlhttp1.status==200)
		{
			var res =  xmlhttp1.responseText;
			document.getElementById('udc').value=res;
			if(res!="NA")
			{
				alert("Serial Number "+serialNo+" Found");
				document.getElementById('submit').disabled=false;
			}
			else{
				alert("Serial Number "+serialNo+" not present in the property file");
				document.getElementById('submit').disabled=true;
			}
	
		}
	};
	xmlhttp1.open("POST","/AUA/UdcServlet",true);
	xmlhttp1.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp1.send("isSrNoCheck=Y&serialNo="+document.getElementById('serialNo').value);
} 






