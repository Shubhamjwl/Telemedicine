
function setBiometricFunction(type)
{
	alert('setBiometricFunction from common.js');
	//var val=document.getElementById('type').value;
	var val= type;
	alert('val:'+val);
	if(val=="1")
	{

		$('#type').prop('disabled', 'disabled');

		var js = document.createElement("script");

		js.type = 'text/javascript';
		js.src = 'LegendScript.js';
		document.body.appendChild(js);


		$(document).ready(function(){
		$(".handdiv").prepend('<applet code="Legend.BiomAPI.class" width="0" height="0" MAYSCRIPT archive="BiomAPI.jar" id="Obj"></applet>');

		$(".10").on('click',function(){getFeatureUIDAIData(10);});
		$(".9").on('click',function(){getFeatureUIDAIData(9);});
		$(".8").on('click',function(){getFeatureUIDAIData(8);});
		$(".7").on('click',function(){getFeatureUIDAIData(7);});
		$(".6").on('click',function(){getFeatureUIDAIData(6);});
		$(".5").on('click',function(){getFeatureUIDAIData(5);});
		$(".4").on('click',function(){getFeatureUIDAIData(4);});
		$(".3").on('click',function(){getFeatureUIDAIData(3);});
		$(".2").on('click',function(){getFeatureUIDAIData(2);});
		$(".1").on('click',function(){getFeatureUIDAIData(1);});


		});

	}
	
		else
			if(val=="2")
			{

				$('#type').prop('disabled', 'disabled');

				var js = document.createElement("script");

				js.type = 'text/javascript';
				js.src = 'smartchip.js';
				document.body.appendChild(js);


				$(document).ready(function(){
					$(".handdiv").prepend('<applet width="1" height="1" archive="AuthenticationCapture.jar" code="online.auth.AuthenticationApplet" id="app1" name="app1"> </applet>');
					$(".10").on('click',function(){getBiomFinger(10);});
					$(".9").on('click',function(){getBiomFinger(9);});
					$(".8").on('click',function(){getBiomFinger(8);});
					$(".7").on('click',function(){getBiomFinger(7);});
					$(".6").on('click',function(){getBiomFinger(6);});
					$(".5").on('click',function(){getBiomFinger(5);});
					$(".4").on('click',function(){getBiomFinger(4);});
					$(".3").on('click',function(){getBiomFinger(3);});
					$(".2").on('click',function(){getBiomFinger(2);});
					$(".1").on('click',function(){getBiomFinger(1);});

				});
			}

			else
				if(val=="3")
				{
					getRdServiceData(1,1);
				}


				else 
					if(val=="4")
					{
						
						$('#type').prop('disabled', 'disabled');


						var js = document.createElement("script");

						js.type = 'text/javascript';
						js.src = 'LegendScript_rd.js';
						document.body.appendChild(js);

						$(document).ready(function() {
							
									$(".10").on('click',function(){getCogentRdServiceData(10);});
									$(".9").on('click',function(){getCogentRdServiceData(9);});
									$(".8").on('click',function(){getCogentRdServiceData(8);});
									$(".7").on('click',function(){getCogentRdServiceData(7);});
									$(".6").on('click',function(){getCogentRdServiceData(6);});
									$(".5").on('click',function(){getCogentRdServiceData(5);});
									$(".4").on('click',function(){getCogentRdServiceData(4);});
									$(".3").on('click',function(){getCogentRdServiceData(3);});
									$(".2").on('click',function(){getCogentRdServiceData(2);});
									$(".1").on('click',function(){getCogentRdServiceData(1);});

						});
					}
				// added by Ajit for precision RD
					else
						if(val=="5")
						{
							getPrecisionRdServiceData(1,1);
						}
	
				// Added by aashima for MANTRA RD
					else if(val=="6")
					{
						captureAvdmMantra(1,1);

					}
	
	
					else
						if(val=="0")
						{

							alert("Please Select proper Device type");

						}
}						






function setBFDFunction(type)
{
	
	//var val=document.getElementById('type').value;
	var val= type;
	if(val=="1")
	{
		$('#type').prop('disabled', 'disabled');
		$( document ).ready(function() { $('#btnFormBFD').show(); });
		var js = document.createElement("script");

		js.type = 'text/javascript';
		js.src = 'LegendScript.js';
		document.body.appendChild(js);

		$(document).ready(function(){
			$(".handdiv").prepend('<applet code="Legend.BiomAPI.class" width="0" height="0" MAYSCRIPT archive="BiomAPI.jar" id="Obj"></applet>');
			$(".10").on('click',function(){GetFeatureBFDData(10);});
			$(".9").on('click',function(){GetFeatureBFDData(9);});
			$(".8").on('click',function(){GetFeatureBFDData(8);});
			$(".7").on('click',function(){GetFeatureBFDData(7);});
			$(".6").on('click',function(){GetFeatureBFDData(6);});
			$(".5").on('click',function(){GetFeatureBFDData(5);});
			$(".4").on('click',function(){GetFeatureBFDData(4);});
			$(".3").on('click',function(){GetFeatureBFDData(3);});
			$(".2").on('click',function(){GetFeatureBFDData(2);});
			$(".1").on('click',function(){GetFeatureBFDData(1);});

		});




	}
	else
		if(val=="0")
		{

			alert("Please select proper device type");

		}
		else
			if(val=="2")
			{

				$('#type').prop('disabled', 'disabled');
	
				var js = document.createElement("script");

				js.type = 'text/javascript';
				js.src = 'smartchip.js';
				document.body.appendChild(js);
				
				$(document).ready(function(){
					$(".handdiv").prepend('<applet width="1" height="1" archive="AuthenticationCapture.jar" code="online.auth.AuthenticationApplet" id="app1" name="app1"> </applet>');
	
					$(".10").on('click',function(){getBFDFinger(10);});
					$(".9").on('click',function(){getBFDFinger(9);});
					$(".8").on('click',function(){getBFDFinger(8);});
					$(".7").on('click',function(){getBFDFinger(7);});
					$(".6").on('click',function(){getBFDFinger(6);});
					$(".5").on('click',function(){getBFDFinger(5);});
					$(".4").on('click',function(){getBFDFinger(4);});
					$(".3").on('click',function(){getBFDFinger(3);});
					$(".2").on('click',function(){getBFDFinger(2);});
					$(".1").on('click',function(){getBFDFinger(1);});

				});
			}

			else
				if(val=="3")
				{

					$('#type').prop('disabled', 'disabled');
					getMorphoAppletlessDetails();
					var js = document.createElement("script");

					js.type = 'text/javascript';
					js.src = 'smartchip_appletless.js';
					document.body.appendChild(js);
					

					$(document).ready(function(){
						
						$(".10").on('click',function(){getBFDFingerAL(10);});
						$(".9").on('click',function(){getBFDFingerAL(9);});
						$(".8").on('click',function(){getBFDFingerAL(8);});
						$(".7").on('click',function(){getBFDFingerAL(7);});
						$(".6").on('click',function(){getBFDFingerAL(6);});
						$(".5").on('click',function(){getBFDFingerAL(5);});
						$(".4").on('click',function(){getBFDFingerAL(4);});
						$(".3").on('click',function(){getBFDFingerAL(3);});
						$(".2").on('click',function(){getBFDFingerAL(2);});
						$(".1").on('click',function(){getBFDFingerAL(1);});

					});
				}

				else 
					if(val=="4")
					{

						$('#type').prop('disabled', 'disabled');

						var js = document.createElement("script");

						js.type = 'text/javascript';
						js.src = 'LegendScript_Appletless.js';
						document.body.appendChild(js);

						$(document).ready(function() {
						
							var strMessage = "Initialize";
							$.ajax({
								type: "GET",
								url: cogentALurl+"?action=" + strMessage,
								crossDomain: true,
								dataType: 'jsonp',
								jsonp: 'callback',
								jsonpCallback: 'jsonpCallback',
								success: function(jsonObj)
								{
									alert(jsonObj.Message);
									getCogentAppletLessDetails();
									$(".10").on('click',function(){captureBFDFingerCogentAL(10);});
									$(".9").on('click',function(){captureBFDFingerCogentAL(9);});
									$(".8").on('click',function(){captureBFDFingerCogentAL(8);});
									$(".7").on('click',function(){captureBFDFingerCogentAL(7);});
									$(".6").on('click',function(){captureBFDFingerCogentAL(6);});
									$(".5").on('click',function(){captureBFDFingerCogentAL(5);});
									$(".4").on('click',function(){captureBFDFingerCogentAL(4);});
									$(".3").on('click',function(){captureBFDFingerCogentAL(3);});
									$(".2").on('click',function(){captureBFDFingerCogentAL(2);});
									$(".1").on('click',function(){captureBFDFingerCogentAL(1);});



								},
								error: function(jqXHR, textStatus, errorThrown)
								{
								}	    
							});
						});	


					}

}




function setTFAFunction(type)
{

	//var val=document.getElementById('type').value;
	var val= type;
	if(val=="1")
	{
		$('#type').prop('disabled', 'disabled');
		
		var js = document.createElement("script");

		js.type = 'text/javascript';
		js.src = 'LegendScript.js';
		document.body.appendChild(js);


		$(document).ready(function(){
			$(".handdiv").prepend('<applet code="Legend.BiomAPI.class" width="0" height="0" MAYSCRIPT archive="BiomAPI.jar" id="Obj"></applet>');
			$(".10").on('click',function(){GetFeatureTFAData(10);});
			$(".9").on('click',function(){GetFeatureTFAData(9);});
			$(".8").on('click',function(){GetFeatureTFAData(8);});
			$(".7").on('click',function(){GetFeatureTFAData(7);});
			$(".6").on('click',function(){GetFeatureTFAData(6);});
			$(".5").on('click',function(){GetFeatureTFAData(5);});
			$(".4").on('click',function(){GetFeatureTFAData(4);});
			$(".3").on('click',function(){GetFeatureTFAData(3);});
			$(".2").on('click',function(){GetFeatureTFAData(2);});
			$(".1").on('click',function(){GetFeatureTFAData(1);});

		});




	}
	else
		if(val=="0")
		{

			alert("Please select proper device type");

		}
		else
			if(val=="2")
			{

				$('#type').prop('disabled', 'disabled');
				
				var js = document.createElement("script");

				js.type = 'text/javascript';
				js.src = 'smartchip.js';
				document.body.appendChild(js);
				


				$(document).ready(function(){
					$(".handdiv").prepend('<applet width="1" height="1" archive="AuthenticationCapture.jar" code="online.auth.AuthenticationApplet" id="app1" name="app1"> </applet>');
					$(".10").on('click',function(){getTFAFinger(10);});
					$(".9").on('click',function(){getTFAFinger(9);});
					$(".8").on('click',function(){getTFAFinger(8);});
					$(".7").on('click',function(){getTFAFinger(7);});
					$(".6").on('click',function(){getTFAFinger(6);});
					$(".5").on('click',function(){getTFAFinger(5);});
					$(".4").on('click',function(){getTFAFinger(4);});
					$(".3").on('click',function(){getTFAFinger(3);});
					$(".2").on('click',function(){getTFAFinger(2);});
					$(".1").on('click',function(){getTFAFinger(1);});

				});
			}

	//added by Supneet for MORPHO RD
			else
				if(val=="3")
				{

					$('#type').prop('disabled', 'disabled');
					//getMorphoAppletlessDetails();
					var js = document.createElement("script");

					js.type = 'text/javascript';
					js.src = 'smartchip_rd.js';
					document.body.appendChild(js);


					$(document).ready(function(){
				
						$(".10").on('click',function(){getRdServiceData(10,2);});
						$(".9").on('click',function(){getRdServiceData(9,2);});
						$(".8").on('click',function(){getRdServiceData(8,2);});
						$(".7").on('click',function(){getRdServiceData(7,2);});
						$(".6").on('click',function(){getRdServiceData(6,2);});
						$(".5").on('click',function(){getRdServiceData(5,2);});
						$(".4").on('click',function(){getRdServiceData(4,2);});
						$(".3").on('click',function(){getRdServiceData(3,2);});
						$(".2").on('click',function(){getRdServiceData(2,2);});
						$(".1").on('click',function(){getRdServiceData(1,2);});

					});
				}

				else 
					if(val=="4")
					{
						
						$('#type').prop('disabled', 'disabled');

						var js = document.createElement("script");

						js.type = 'text/javascript';
						js.src = 'LegendScript_rd.js';
						document.body.appendChild(js);

						$(document).ready(function() {
						
									$(".10").on('click',function(){getTFAFingerCogentRD(10);});
									$(".9").on('click',function(){getTFAFingerCogentRD(9);});
									$(".8").on('click',function(){getTFAFingerCogentRD(8);});
									$(".7").on('click',function(){getTFAFingerCogentRD(7);});
									$(".6").on('click',function(){getTFAFingerCogentRD(6);});
									$(".5").on('click',function(){getTFAFingerCogentRD(5);});
									$(".4").on('click',function(){getTFAFingerCogentRD(4);});
									$(".3").on('click',function(){getTFAFingerCogentRD(3);});
									$(".2").on('click',function(){getTFAFingerCogentRD(2);});
									$(".1").on('click',function(){getTFAFingerCogentRD(1);});


								});	
						}

	//Added by aashima for MANTRA RD
					else
						if(val=="5")
						{

							$('#type').prop('disabled', 'disabled');
							//getMorphoAppletlessDetails();
							var js = document.createElement("script");

							js.type = 'text/javascript';
							js.src = 'mantra_rd.js';
							document.body.appendChild(js);


							$(document).ready(function(){
						
								$(".10").on('click',function(){captureAvdmMantra(10,2);});
								$(".9").on('click',function(){captureAvdmMantra(9,2);});
								$(".8").on('click',function(){captureAvdmMantra(8,2);});
								$(".7").on('click',function(){captureAvdmMantra(7,2);});
								$(".6").on('click',function(){captureAvdmMantra(6,2);});
								$(".5").on('click',function(){captureAvdmMantra(5,2);});
								$(".4").on('click',function(){captureAvdmMantra(4,2);});
								$(".3").on('click',function(){captureAvdmMantra(3,2);});
								$(".2").on('click',function(){captureAvdmMantra(2,2);});
								$(".1").on('click',function(){captureAvdmMantra(1,2);});

							});
						}

					}




/*For Morpho service device integration*/

function getRdServiceData(buttonId, count){
	
//	alert("before calling checkRD service");
	checkRDService();
	
    var url = "https://127.0.0.1:11100/capture";
    var fingerCount = count;
	var wadh ="E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=";
	var PIDOPTS='<PidOptions ver=\"1.0\">'+'<Opts fCount=\"'+ fingerCount +'\" fType=\"0\" iCount=\"\" iType=\"\" pCount=\"\" pType=\"\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\" wadh=\"'+ wadh +'\" posh=\"\"/>'+'</PidOptions>';
	 
	   /*
	   format=\"0\"     --> XML
	   format=\"1\"     --> Protobuf
	   */
	 var xhr;
				var ua = window.navigator.userAgent;
				var msie = ua.indexOf("MSIE ");

				if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) // If Internet Explorer, return version number
				{
					//IE browser
					xhr = new ActiveXObject("Microsoft.XMLHTTP");
				} else {
					//other browser
					xhr = new XMLHttpRequest();
				}
	        
	        xhr.open('CAPTURE', url, false);
			xhr.setRequestHeader("Content-Type","text/xml");
			xhr.setRequestHeader("Accept","text/xml");

	        xhr.onreadystatechange = function () {
      	
		
	if (xhr.readyState == 4){
	            var status = xhr.status;

	            if (status == 200) {
	            	alert("Fingerprint captured");
	            	 document.getElementById('rdServiceData').value=xhr.responseText;
						//	alert(document.getElementById('rdServiceData').value);
				
							$( document ).ready(function() { 	
								$(".handdiv ."+buttonId).html("<img src=\"image/check.jpg\"></img>");
							});
							
//				alert("PID xml is \n"+xhr.responseText);
	            } else {
	            	alert("Fingerprint not captured");
		            console.log(xhr.response);

	            }
				}

	        };
//	        alert("PIDOPTS \n"+PIDOPTS);		
	        xhr.send(PIDOPTS);
}





function checkRDService()
{

	var url = "https://127.0.0.1:11100";
  
      var xhr;
			var ua = window.navigator.userAgent;
			var msie = ua.indexOf("MSIE ");

			if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) // If Internet Explorer, return version number
			{
				//IE browser
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			} else {
				//other browser
				xhr = new XMLHttpRequest();
			}
        
        xhr.open('RDSERVICE', url, false);

         xhr.onreadystatechange = function () {
   	 
	
if (xhr.readyState == 4){
            var status = xhr.status;

            if (status == 200) {

                alert("Device is ready to capture firgure\n"+xhr.responseText);
				
				//Capture();                   //Call Capture() here if FingerPrint Capture is required inside RDService() call           
	            console.log(xhr.response);

            } else {
	            console.log(xhr.response);
	            alert("RD services not running, check if the device is connected properly");
            }
			}

        };

	 xhr.send();

}


/*For Mantra service device integration*/

var GetCustomDomName = '127.0.0.1';
var OldPort = false;
var finalUrl = "";
var MethodInfo = "";
var MethodCapture = "";
var ddlAVDM ="";
var txtDeviceInfo="";
var txtPidData ="";
var txtPidOptions ="";

function captureAvdmMantra(buttonId, count)
{
	discoverAvdm();
	CaptureAvdm();
}


function discoverAvdm() {
	var SuccessFlag = 0;
	var primaryUrl = "https://" + GetCustomDomName + ":";
	try {
		var protocol = window.location.href;
		if (protocol.indexOf("https") >= 0) {
			primaryUrl = "https://" + GetCustomDomName + ":";
		}
	} catch (e) {
	}

	url = "";
	//alert("Please wait while discovering port from 11100 to 11120.\nThis will take some time.");
	SuccessFlag = 0;
	for (var i = 11100; i <= 11105; i++) {
		if (primaryUrl == "https://" + GetCustomDomName + ":" && OldPort == true) {
			i = "8005";
		}
		var verb = "RDSERVICE";
		var err = "";

		var res;
		$.support.cors = true;
		var httpStaus = false;
		var jsonstr = "";
		var data = new Object();
		var obj = new Object();

		$.ajax({
				type : "RDSERVICE",
				async : false,
				crossDomain : true,
				url : primaryUrl + i.toString(),
				contentType : "text/xml; charset=utf-8",
				processData : false,
				cache : false,
				crossDomain : true,
				success : function(data) {
						httpStaus = true;
						res = {
							httpStaus : httpStaus,
							data : data
						};
						//alert(data);
						//$("#txtDeviceInfo").val(data);
						txtDeviceInfo = data;
						finalUrl = primaryUrl + i.toString();
						var $doc = $.parseXML(data);
						var CmbData1 = $($doc).find('RDService').attr('status');
						var CmbData2 = $($doc).find('RDService').attr('info');
						if (RegExp('\\b' + 'Mantra' + '\\b').test(CmbData2) == true) {
							if ($($doc).find('Interface').eq(0).attr('path') == "/rd/capture") {
								MethodCapture = $($doc).find('Interface').eq(0).attr('path');
							}
							if ($($doc).find('Interface').eq(1).attr('path') == "/rd/capture") {
								MethodCapture = $($doc).find('Interface').eq(1).attr('path');
							}
							if ($($doc).find('Interface').eq(0).attr('path') == "/rd/info") {
								MethodInfo = $($doc).find('Interface').eq(0).attr('path');
							}
							if ($($doc).find('Interface').eq(1).attr('path') == "/rd/info") {
								MethodInfo = $($doc).find('Interface').eq(1).attr('path');
							}
							//ddlAVDM ='<option value=' + i.toString() + '>('	+ CmbData1 + '-' + i.toString()+ ')' + CmbData2 + '</option>';
							ddlAVDM=i.toString();
							SuccessFlag = 1;
							//alert("RDSERVICE Discover Successfully");
							//return;
						}
						//alert(CmbData1);
						//alert(CmbData2);
					},
					error : function(jqXHR, ajaxOptions, thrownError) {
						if (i == "8005" && OldPort == true) {
							OldPort = false;
							i = "11099";
						}
					},

				});

		if (SuccessFlag == 1) {
			//break;
		}
	}

	if (SuccessFlag == 0) {
		alert("Connection failed Please try again.");
	} else {
		alert("RDSERVICE Discover Successfully");
	}
	return res;
}

function CaptureAvdm() {

	var strWadh = "E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=";
	var strOtp = "";
	var DemoFinalString = '';
	//Demo();
	strWadh = " wadh=\"" + strWadh + '"';
	strOtp = " otp=\"" + strOtp + '"';
	
	var XML = '<?xml version="1.0"?> <PidOptions ver="1.0"> <Opts fCount="1" fType="0" iCount="0" pCount="0" pgCount="2" ' + strOtp + ' format="0" pidVer="2.0" timeout="10000" pTimeout="20000"' + strWadh + ' posh="UNKNOWN" env="PP"/> '
			+ DemoFinalString + '<CustOpts><Param name="mantrakey" value="" /></CustOpts> </PidOptions>';

	finalUrl = "https://" + GetCustomDomName + ":" + ddlAVDM;

	try {
		var protocol = window.location.href;
		if (protocol.indexOf("https") >= 0) {
			finalUrl = "https://" + GetCustomDomName + ":"+ ddlAVDM;
		}
	} catch (e) {
	}

	var verb = "CAPTURE";

	var err = "";

	var res;
	$.support.cors = true;
	var httpStaus = false;
	var jsonstr = "";
	;
	$.ajax({

		type : "CAPTURE",
		async : false,
		crossDomain : true,
		url : finalUrl + MethodCapture,
		data : XML,
		contentType : "text/xml; charset=utf-8",
		processData : false,
		success : function(data) {
			//alert(data);
			httpStaus = true;
			res = {
				httpStaus : httpStaus,
				data : data
			};

			txtPidData = data ;
			//txtPidOptions= XML;
			//alert("PID XML 1: \n"+data);
			 document.getElementById('rdServiceData').value= data;
			//alert("PID XML 2: \n"+XML);
			
			var $doc = $.parseXML(data);
			var Message = $($doc).find('Resp').attr('errInfo');

			//alert(Message);

		},
		error : function(jqXHR, ajaxOptions, thrownError) {
			//$('#txtPidOptions').val(XML);
			alert(thrownError);
			res = {
				httpStaus : httpStaus,
				err : getHttpError(jqXHR)
			};
		},
	});

	return res;
}

/*For Precision service device integration*/

function getPrecisionRdServiceData(buttonId, count){
	discoverPrecisionRDService();
	precisionCaptureBio(buttonId, count);
	
}

//Search for the valid RDService from port 11100 to 11120
function discoverPrecisionRDService(){
	
    var primaryUrl = "https://127.0.0.1:";
	var tempcount = 0;
    url = "";
	$("#ddlRDS").empty();
	alert("Precision Device is getting ready to Capture Fingerprint.\n TPlease wait for some time. Click Ok.");
	
    for (var i = 11100; i <= 11120; i++)
	{
		var verb = "RDSERVICE";
        var err = "";
	
	// alert(i);
		var res;
		$.support.cors = true;
		var httpStaus = false;
		var jsonstr="";
		var data = new Object();
		var obj = new Object();
		var xmlstr;
				
		$.ajax({
			type: "RDSERVICE",
			async: false,
			crossDomain: true,
			url: primaryUrl + i.toString(),
			contentType: "text/xml; charset=utf-8",
			processData: false,
			cache: false,
			async:false,
			crossDomain:true,
			
			success: function (data) {
			try{
				xmlstr = data.xml ? data.xml : (new XMLSerializer()).serializeToString(data);
				// alert(xmlstr);
				data = xmlstr;
					
				httpStaus = true;
				res = { httpStaus: httpStaus, data: data };
			    // alert(data);
				finalUrl = primaryUrl + i.toString();
				finalPort = i.toString();
				var $doc = $.parseXML(xmlstr);
				var CmbData1 =  $($doc).find('RDService').attr('status');
				var CmbData2 =  $($doc).find('RDService').attr('info');
					
				$("#ddlRDS").append('<option value='+i.toString()+'>Port : '+i.toString()+' Status : ' + CmbData1 +'('+CmbData2+')</option>')
				tempcount++;
				}
				catch(e){}
			},
			error: function(msg) {
                var result = msg.status + msg.statusText + msg.responseText;
               // alert( result);
                
            }
		});
		
		if(xmlstr != null)
		{
			if(xmlstr.indexOf('Precision') > 0)
			{
			// alert(xmlstr);
				
				$("select#ddlRDS").prop('selectedIndex', tempcount-1);
				finalport=i.toString();
				discoverFirstNode(i);
				break;
			}
		}
				
	}
	return res;	
}

function discoverFirstNode(PortNo)				// Get the Status of the RDService found by discoverRDService()
{
	$('#txtDeviceInfo').val('');
	$('#txtPidOptions').val('');
	$('#txtPidData').val('');
	
//alert(PortNo);

	var primaryUrl = "https://127.0.0.1:";
    url = "";
	var verb = "RDSERVICE";
    var err = "";
	var res;
	$.support.cors = true;
	var httpStaus = false;
	var jsonstr="";
	var data = new Object();
	var obj = new Object();
		
	$.ajax({
		type: "RDSERVICE",
		async: false,
		crossDomain: true,
		url: primaryUrl + PortNo,
		contentType: "text/xml; charset=utf-8",
		processData: false,
		cache: false,
		async:false,
		crossDomain:true,
		success: function (data) {
			var xmlstr = data.xml ? data.xml : (new XMLSerializer()).serializeToString(data);
			//alert(xmlstr);
			data = xmlstr;
			//alert(data);
			httpStaus = true;
			res = { httpStaus: httpStaus, data: data };
		    //alert(data);		
			/*$('#txtRDSInfo').val(data);
					 
			var $doc = $.parseXML(data);
					
			for(var j=0;j<2;j++)
			{
				if($($doc).find('Interface').eq(j).attr('id') == 'CAPTURE')
				{
					MethodCapture=$($doc).find('Interface').eq(j).attr('path');
				}else if($($doc).find('Interface').eq(j).attr('id') == 'DEVICEINFO')
				{
					MethodInfo=$($doc).find('Interface').eq(j).attr('path');
				}
			}*/
			
			MethodInfo='/rd/info';
			MethodCapture='/rd/capture';
			
			alert("Precision RDSERVICE Discover Successfully");
		},
		error: function(msg) {
                var result = msg.status + msg.statusText + msg.responseText;
                alert( result);
                
            }
	});
		
	return res;	
					
}

function precisionCaptureBio(buttonId, count)						// Get Capture Info from the path given by RDService Info
{
	var fCount = "1";
	var fType = "0";
	var format = "0";
	var pidver = "2.0";
	var timeout = "10000";
	var env = "PP"; // PP - preprod & P - production
	var lock = "";
	var wadh ="E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=";
	finalUrl="https://127.0.0.1:"+finalport;

// Form input Pid OPtions for capture Input
	var XML='<?xml version="1.0"?> <PidOptions ver="1.0"> <Opts fCount="'+ fCount +'" fType="'+ fType +'" format="'+ format +'" pidVer="'+ pidver +'" timeout="'+ timeout +'" env="'+ env +'" wadh="'+ wadh +'"/> <CustOpts> <Param name="LockingKey" value="'+lock +'" /> </CustOpts> </PidOptions>';
	
	var verb = "CAPTURE";
    var err = "";
	var res;
	$.support.cors = true;
	var httpStaus = false;
	var jsonstr="";
	$.ajax({
		type: "CAPTURE",
		async: false,
		crossDomain: true,
		url: finalUrl+MethodCapture,
		data:XML,
		contentType: "text/xml; charset=utf-8",
		processData: false,
		success: function (data) {
			var xmlstr = data.xml ? data.xml : (new XMLSerializer()).serializeToString(data);
			//alert(xmlstr);
			data = xmlstr;
			//alert(data);
			httpStaus = true;
			res = { httpStaus: httpStaus, data: data };
					
			/*$('#txtPidData').val(data);
			$('#txtPidOptions').val(XML);*/
			
	           document.getElementById('rdServiceData').value=data;
	           alert("Fingerprint captured");
						
			var $doc = $.parseXML(data);
			var Message =  $($doc).find('Resp').attr('errInfo');
		},
		error: function(msg) {
                var result = msg.status + msg.statusText + msg.responseText;
                alert( result);
                
            }
	});
				
	return res;
	    
}

// common HTTP error block for all RD services
function getHttpError(jqXHR) {
	var err = "Unhandled Exception";
	if (jqXHR.status === 0) {
		err = 'Service Unavailable';
	} else if (jqXHR.status == 404) {
		err = 'Requested page not found';
	} else if (jqXHR.status == 500) {
		err = 'Internal Server Error';
	} else if (thrownError === 'parsererror') {
		err = 'Requested JSON parse failed';
	} else if (thrownError === 'timeout') {
		err = 'Time out error';
	} else if (thrownError === 'abort') {
		err = 'Ajax request aborted';
	} else {
		err = 'Unhandled Error';
	}
	return err;
}

