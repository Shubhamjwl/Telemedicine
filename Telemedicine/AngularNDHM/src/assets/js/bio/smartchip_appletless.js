
//For MORPHO MSO 1300 E2(AppletLess) for biometric

var template;
function captureBiometricFinger(buttonId)

{
	alert("Click on finger tip to scan");
	var fingerindex=buttonId;
	var deviceStat;
	var url = "http://localhost:8000/CaptureFingerprint";
	var xmlhttp;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();

	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");

	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			fpobject = JSON.parse(xmlhttp.responseText);
			
			template = fpobject.Base64ISOTemplate; 	   
			
			deviceStat=fpobject.ReturnCode;

			if(deviceStat == "0")
			{

				document.getElementById('fingerPrint').value = fpobject.Base64ISOTemplate;
				
				alert("Finger Print Captured");
				


				switch(fingerindex)
				{
				case 1: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				document.getElementById('fingerPosition').value = "RIGHT_THUMB";
				break;
				case 2: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				document.getElementById('fingerPosition').value = "RIGHT_INDEX";
				break;	
				case 3: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				document.getElementById('fingerPosition').value = "RIGHT_MIDDLE";
				break;
				case 4: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				document.getElementById('fingerPosition').value = "RIGHT_RING";
				break;
				case 5: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				document.getElementById('fingerPosition').value = "RIGHT_LITTLE";
				break;
				case 6: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				document.getElementById('fingerPosition').value = "LEFT_THUMB";
				break;
				case 7: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				document.getElementById('fingerPosition').value = "LEFT_INDEX";
				break;
				case 8: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				document.getElementById('fingerPosition').value = "LEFT_MIDDLE";
				break;
				case 9: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				document.getElementById('fingerPosition').value = "LEFT_RING";
				break;
				case 10:$(document).ready(function(){ $("a").attr('onclick', '""');  });
				document.getElementById('fingerPosition').value = "LEFT_LITTLE";
				break;


				}
		
		

				$( document ).ready(function() { 	
					$(".handdiv ."+fingerindex).html("<img src=\"image/check.jpg\"></img>");

				});
				
				

			}
		
			else
			{
				alert("Finger print not captured");
			}
		}

		xmlhttp.onerror = function () {
	         alert("Check If Morpho Service/Utility is Running");
	    };

		};
		
	
	var timeout = 5;
	xmlhttp.open("POST",url+"?"+timeout+"$"+fingerindex,true);
	xmlhttp.send();


}




//For MORPHO MSO 1300 AppletLess for BFD

var template;
function getBFDFingerAL(buttonId)

{
	var fingerindex=buttonId;
	var deviceStat;
	var url = "http://localhost:8000/CaptureFingerprint";
       var finger = ""; 
       var nfiq = "";
       var fingerPos = "";
    	var xmlhttp;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();

	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");

	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			fpobject = JSON.parse(xmlhttp.responseText);
			
			template = fpobject.Base64ISOTemplate; 	   
			
			deviceStat=fpobject.ReturnCode;

			if(deviceStat == "0")
			{

				finger = fpobject.Base64ISOTemplate;
                            nfiq = fpobject.NFIQ;
                            document.getElementById('bfdFingerPrint').value = document.getElementById('bfdFingerPrint').value+finger+"^";
				document.getElementById('bfdNfiq').value = document.getElementById('bfdNfiq').value + nfiq + "^";
				
				alert("Finger Print Captured");
				


				switch(fingerindex)
				{
				case 1: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				fingerPos  = "RIGHT_THUMB";
				break;
				case 2: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				fingerPos  = "RIGHT_INDEX";
				break;	
				case 3: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				fingerPos  = "RIGHT_MIDDLE";
				break;
				case 4: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				fingerPos  = "RIGHT_RING";
				break;
				case 5: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				fingerPos  = "RIGHT_LITTLE";
				break;
				case 6: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				fingerPos  = "LEFT_THUMB";
				break;
				case 7: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				fingerPos  = "LEFT_INDEX";
				break;
				case 8: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				fingerPos  = "LEFT_MIDDLE";
				break;
				case 9: $(document).ready(function(){ $("a").attr('onclick', '""'); });
				fingerPos  = "LEFT_RING";
				break;
				case 10:$(document).ready(function(){ $("a").attr('onclick', '""');  });
				fingerPos  = "LEFT_LITTLE";
				break;


				}
		
		        document.getElementById('bfdFingerPosition').value = document.getElementById('bfdFingerPosition').value + fingerPos + "^";

				$( document ).ready(function() { 	
					$(".handdiv ."+fingerindex).html("<img src=\"image/check.jpg\"></img>");

				});
				
			}
		
			else
			{
				alert("Finger print not captured");
			}
		}

		xmlhttp.onerror = function () {
	         alert("Check If Morpho Service/Utility is Running");
	    };

		};
	
	var timeout = 5;
	xmlhttp.open("POST",url+"?"+timeout+"$"+fingerindex,true);
	xmlhttp.send();


}






//For MORPHO MSO 1300 E2(AppletLess) for TFA

var template;
function getTFAFingerAL(buttonId)

{
	var fingerindex=buttonId;
	var deviceStat;
	var url = "http://localhost:8000/CaptureFingerprint";
        var finger = ""; 
	var xmlhttp;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();

	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");

	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			fpobject = JSON.parse(xmlhttp.responseText);
			
			template = fpobject.Base64ISOTemplate; 	   
			
			deviceStat=fpobject.ReturnCode;

			if(deviceStat == "0")
			{

			    finger = fpobject.Base64ISOTemplate;
                
                document.getElementById('tfaFingerPrint').value = document.getElementById('tfaFingerPrint').value+finger+"^";
			    
				alert("Finger Print Captured");
				


				switch(fingerindex)
				{
				case 1: $(document).ready(function(){ $(".1").attr('onclick', '""'); });
						document.getElementById('tfaFingerPosition').value = document.getElementById('tfaFingerPosition').value+"RIGHT_THUMB^";
						break;
				case 2: $(document).ready(function(){ $(".2").attr('onclick', '""'); });
						document.getElementById('tfaFingerPosition').value = document.getElementById('tfaFingerPosition').value+"RIGHT_INDEX^";
						break;	
				case 3: $(document).ready(function(){ $(".3").attr('onclick', '""'); });
						document.getElementById('tfaFingerPosition').value =document.getElementById('tfaFingerPosition').value+ "RIGHT_MIDDLE^";
						break;
				case 4: $(document).ready(function(){ $(".4").attr('onclick', '""'); });
						document.getElementById('tfaFingerPosition').value = document.getElementById('tfaFingerPosition').value+"RIGHT_RING^";
						break;
				case 5: $(document).ready(function(){ $(".5").attr('onclick', '""'); });
						document.getElementById('tfaFingerPosition').value =document.getElementById('tfaFingerPosition').value+ "RIGHT_LITTLE^";
						break;
				case 6: $(document).ready(function(){ $(".6").attr('onclick', '""'); });
						document.getElementById('tfaFingerPosition').value = document.getElementById('tfaFingerPosition').value+"LEFT_THUMB^";
						break;
				case 7: $(document).ready(function(){ $(".7").attr('onclick', '""'); });
						document.getElementById('tfaFingerPosition').value = document.getElementById('tfaFingerPosition').value+"LEFT_INDEX^";
						break;
				case 8: $(document).ready(function(){ $(".8").attr('onclick', '""'); });
						document.getElementById('tfaFingerPosition').value = document.getElementById('tfaFingerPosition').value+"LEFT_MIDDLE^";
						break;
				case 9: $(document).ready(function(){ $(".9").attr('onclick', '""'); });
						document.getElementById('tfaFingerPosition').value = document.getElementById('tfaFingerPosition').value+"LEFT_RING^";
						break;
				case 10:$(document).ready(function(){ $(".10").attr('onclick', '""');  });
						document.getElementById('tfaFingerPosition').value = document.getElementById('tfaFingerPosition').value+"LEFT_LITTLE^";
						break;
				
				
				}
		              
				
				$( document ).ready(function() { 	
					$(".handdiv ."+fingerindex).html("<img src=\"image/check.jpg\"></img>");

					});
					
					}
		
				else
					{
						alert("Finger print not captured");
					}
		}
				
				var count = document.getElementById('capAttempt').value;
				
				if(count>=2)
				{
					$(document).ready(function(){
					for(var j=1;j<=10;j++)
					{
						 $("."+j).attr('onclick', '""');
					}
					 });
				}
				xmlhttp.onerror = function () {
			         alert("Check If Morpho Service/Utility is Running");
			    };

				};
	
	var timeout = 5;
	xmlhttp.open("POST",url+"?"+timeout+"$"+fingerindex,true);
	xmlhttp.send();


}
