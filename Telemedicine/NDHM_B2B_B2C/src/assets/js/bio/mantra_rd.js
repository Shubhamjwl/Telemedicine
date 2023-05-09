
function captureAvdmMantra(buttonId, count)
{
	
	discoverAvdm();
	
	//deviceInfoAvdm();
	
	var finger=buttonId;
	var fingerCount=count;

	alert("Inside captureAvdmMantra Method");
	
	
var fingerindex=buttonId;
	

    switch(fingerindex)
	{
	case 1: $(document).ready(function(){ $("a").attr('onclick', '""'); });
	fingerindex= "RIGHT_THUMB";
	break;
	case 2: $(document).ready(function(){ $("a").attr('onclick', '""'); });
	fingerindex = "RIGHT_INDEX";
	break;	
	case 3: $(document).ready(function(){ $("a").attr('onclick', '""'); });
	fingerindex = "RIGHT_MIDDLE";
	break;
	case 4: $(document).ready(function(){ $("a").attr('onclick', '""'); });
	fingerindex = "RIGHT_RING";
	break;
	case 5: $(document).ready(function(){ $("a").attr('onclick', '""'); });
	fingerindex = "RIGHT_LITTLE";
	break;
	case 6: $(document).ready(function(){ $("a").attr('onclick', '""'); });
	fingerindex = "LEFT_THUMB";
	break;
	case 7: $(document).ready(function(){ $("a").attr('onclick', '""'); });
	fingerindex = "LEFT_INDEX";
	break;
	case 8: $(document).ready(function(){ $("a").attr('onclick', '""'); });
	fingerindex = "LEFT_MIDDLE";
	break;
	case 9: $(document).ready(function(){ $("a").attr('onclick', '""'); });
	fingerindex = "LEFT_RING";
	break;
	case 10:$(document).ready(function(){ $("a").attr('onclick', '""');  });
	fingerindex = "LEFT_LITTLE";
	break;
	}

	var XML='<?xml version="1.0"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"'+ fingerCount +'\" fType=\"0\" iCount=\"0\"  pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\"  posh=\"'+ fingerindex +'\" env=\"PP\" />'+'</PidOptions>';	
	alert(XML);  



	var verb = "CAPTURE";
	var finalUrl="";

	finalUrl="http://127.0.0.1:11100";
	var err = "";
	var $doc;
	var Message;
	var res;
	$.support.cors = true;
	var httpStaus = false;
	//	var jsonstr="";
        MethodCapture='/rd/capture';
       // alert('Method:'+MethodCapture);
	alert("Device is ready to Capture Fingerprint.");
	$.ajax({

		type: "CAPTURE",
		async: false,
		crossDomain: true,
		url: finalUrl+MethodCapture,
		data:XML,
		contentType: "text/xml; charset=utf-8",
		processData: false,
		success: function (data) {
	//		alert("Inside capture");
			//debugger;
			$doc = $.parseXML(data);
			console.log(data);
			//	alert("doc in capture method****"+" "+$doc);
			//alert("data in capture method***"+data);
		document.getElementById('rdServiceData').value=data;
	//		alert(document.getElementById('rdServiceData').value);

			//	alert("data in advm method"+data);
			httpStaus = true;
			res = { httpStaus: httpStaus, data: data };
			//alert("response****** in captureadvm method"+res);
			Message =  $($doc).find('Resp').attr('errInfo');

		//	document.getElementById('pidXML').value = $($doc).find('Data').text();
			//	alert("data"+document.getElementById('pidXML').value);

		//	document.getElementById('encHmac').value = $($doc).find('Hmac').text();
			//alert("hmac"+document.getElementById('encHmac').value);
		//	document.getElementById('certExpiryDate').value = $($doc).find('Skey').attr('ci');
			//alert("expdate"+document.getElementById('certExpiryDate').value);
		//	document.getElementById('encSession').value =  $($doc).find('Skey').text();
			//	alert("Skey"+document.getElementById('encSession').value);
		//	document.getElementById('fingerPosition').value = finger;
			//	alert("finger position"+document.getElementById('fingerPosition').value);


                       alert("Fingerprint captured");
		document.getElementById('rdServiceData').value=data;
				alert(document.getElementById('rdServiceData').value);

                        alert("checking handiv");
			$( document ).ready(function() { 	

				$(".handdiv ."+buttonId).html("<img src=\"image/check.jpg\"></img>");
					alert("handdiv");
			});

//			var rdServiceData= $("#rdServiceData").val();
//			var adharNo=$("#aadhaarNo").val();
//			var pidXml= $("#pidXML").val();

//			var name=$("#applicantName").val();
//			var year=$("#dob").val();
//			var gen=gender.charAt(0);

	//		alert("Fingerprint Capture is "+Message+".Kindly Verify.");

		},
		error: function (jqXHR, ajaxOptions, thrownError) {
                        alert('Exception'+thrownError);
                        alert('status:'+jqXHR.status);
			//$('#txtPidOptions').val(XML);
			//					alert(thrownError);
			res = { httpStaus: httpStaus, err: getHttpError(jqXHR) };
			//alert("REsponse in error::"+res+"error"+err);

		},
	});

	return res;
	//alert("Final Response:::"+res);
}


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



var MethodCapture="";
var MethodInfo="";
function discoverAvdm()
{
	// ddlAVDM.empty(); 

	var primaryUrl = "https://127.0.0.1:";
	url = "";
	// $("#ddlAVDM").empty();
	alert("Please wait while discovering port from 11100 to 11120.\nThis will take 20 Seconds. Click Ok.");

	for (var i = 11100; i <= 11120; i++)
	{

		// $("#lblStatus").text("Discovering RD service on port : " + i.toString());

		var verb = "RDSERVICE";
		var err = "";

		var res;
		$.support.cors = true;
		var httpStaus = false;
		var jsonstr="";
		var data = new Object();
		var obj = new Object();
		// var parser, xmlDoc;
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
				//debugger;

				httpStaus = true;
				res = { httpStaus: httpStaus, data: data };

				//alert("data is::" + data);
				//alert("Res is::" + res.toString());

				finalUrl = primaryUrl + i.toString();
				var $doc = $.parseXML(data);
				//	alert("$doc" + $doc);
				var CmbData1 =  $($doc).find('RDService').attr('status');
				var CmbData2 =  $($doc).find('RDService').attr('info');
				if(RegExp('\\b'+ 'Mantra' +'\\b').test(CmbData2)==true)
				{

					$("#ddlAVDM").append('<option value='+i.toString()+'>(' + CmbData1 +')'+CmbData2+'</option>')
					return;
				}

			},
			error: function (jqXHR, ajaxOptions, thrownError) {
				//alert(thrownError);
				res = { httpStaus: httpStaus, err: getHttpError(jqXHR) };
			},
		});
		//$("#ddlAVDM").val("0");



	}
	//$("select#ddlAVDM").prop('selectedIndex', 0);

	//$('#txtDeviceInfo').val(DataXML);

	//	var PortVal= $('#ddlAVDM').val($('#ddlAVDM').find('option').first().val()).val();

	//if(PortVal>11099)
	//	{
	discoverAvdmFirstNode(11100);
	//	}
	//alert("Device discovered successfully******");
	//alert("response in discoverAvdm method:::"+res);
	return res;	
}


function discoverAvdmFirstNode(PortNo)
{

	//$('#txtWadh').val('');
	// $('#txtDeviceInfo').val('');
	//$('#txtPidOptions').val('');
//	$('#txtPidData').val('');
	//var PortNo="11100";


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
			httpStaus = true;
			res = { httpStaus: httpStaus, data: data };
			//  alert("data in discoverAvdmFirstNode *****"+data);		

			//debugger;

			//	 $("#txtDeviceInfo").val(data);


			//alert($($doc).find('Interface').eq(1).attr('path'));


			MethodInfo='/rd/info';
			MethodCapture='/rd/capture';
			// alert("MethodInfo and capture"+MethodInfo+" "+MethodCapture);
			// alert("RDSERVICE Discover Successfully");
		},
		error: function (jqXHR, ajaxOptions, thrownError) {
			//	$('#txtDeviceInfo').val("");
			//alert(thrownError);
			res = { httpStaus: httpStaus, err: getHttpError(jqXHR) };
		},
	});
	//alert("alert in discoverAvdmFirstNode method::::"+res);
	return res;	
}
function deviceInfoAvdm()
{
	alert("Inside deviceInfoAvdm Method");
	// ddlAVDM.empty(); -->


	url = "";

	// alert(i.toString()); -->
	// $("#lblStatus").text("Discovering RD Service on Port : " + i.toString());
	finalUrl="https://127.0.0.1:"+"11100";
	var verb = "DEVICEINFO";
	//   alert("finalurl in deviceinfoavdm:"+finalUrl);

	var err = "";

	var res;
	$.support.cors = true;
	var httpStaus = false;
	var jsonstr="";
	//;
	var data = new Object();
	var obj = new Object();
	$.ajax({

		type: "DEVICEINFO",
		async: false,
		crossDomain: true,
		url: finalUrl+MethodInfo,
		contentType: "text/xml; charset=utf-8",
		processData: false,
		success: function (data) {
			//	alert("data in deviceInfoAvdm***"+data);
			httpStaus = true;
			res = { httpStaus: httpStaus, data: data };

			//$('#txtDeviceInfo').val(data);
			//alert("Device Info***"+$('#txtDeviceInfo').val(data));
		},
		error: function (jqXHR, ajaxOptions, thrownError) {
			//alert(thrownError);
			res = { httpStaus: httpStaus, err: getHttpError(jqXHR) };
		},
	});
	//alert("response in deviceInfoAvdm method:::"+res);
	return res;

}



