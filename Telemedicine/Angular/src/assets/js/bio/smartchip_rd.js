

function getRdServiceData(buttonId, count){
	
	checkRDService();
	
	
	var fingerindex=buttonId;
	var fingerCount=count;

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

    var url = "http://127.0.0.1:11100/capture";

	var PIDOPTS='<PidOptions ver=\"1.0\">'+'<Opts fCount=\"'+ fingerCount +'\" fType=\"0\" iCount=\"\" iType=\"\" pCount=\"\" pType=\"\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\" wadh=\"\" posh=\"\"/>'+'</PidOptions>';
	 
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
							
				alert(xhr.responseText);
	            } else {
	            	alert("Fingerprint not captured");
		            console.log(xhr.response);

	            }
				}

	        };

	        xhr.send(PIDOPTS);
}





function checkRDService()
{

	var url = "http://127.0.0.1:11100";
  
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

                alert(xhr.responseText);
				
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
	 

