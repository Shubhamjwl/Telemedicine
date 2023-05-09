var CapCount = 0;

function getBiomFingerCogentAL(buttonId)

{
	$(document).ready(function() {
		
		alert("Click on finger tip to scan");
		
		var button=buttonId;
		var strMessage = "Capture";
		var imageNum = 1;

		$.ajax({
			type: "GET",
			url: cogentALurl+"?action=" + strMessage + "&image=" + imageNum,
			crossDomain: true,
			dataType: 'jsonp',
			jsonp: 'callback',
			jsonpCallback: 'jsonpCallback',
			success: function(jsonObj)
			{
				if(jsonObj.byteIsoImage != undefined)
				{

					document.getElementById('fingerPrint').value =  jsonObj.byteIsoImage;
					alert("Finger Print Captured");


					switch(button)
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
						$(".handdiv ."+button).html("<img src=\"image/check.jpg\"></img>");

					});

				}else
				{
					alert("Finger print not captured");
				}

				CapCount++;
				if (CapCount == 1)
				{
					DeInitializeDevice();
				}

			},
			error: function(jqXHR, textStatus, errorThrown)
			{
				alert("Finger print not captured");
			}	 

		});

	}); 
}


function getTFAFingerCogentAL(fingerindex)

{
	$(document).ready(function() {
		
		var strMessage = "Capture";
		var imageNum = 1;

		var finger = ""; 
		var fingerPos = "";

		$.ajax({
			type: "GET",
			url: cogentALurl+"?action=" + strMessage + "&image=" + imageNum,
			crossDomain: true,
			dataType: 'jsonp',
			jsonp: 'callback',
			jsonpCallback: 'jsonpCallback',
			success: function(jsonObj)
			{
				if(jsonObj.byteIsoImage != undefined)
				{

					finger =  jsonObj.byteIsoImage;
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

				}else
				{
					alert("Finger print not captured");
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
				
				CapCount++;
				if (CapCount == 2)
				{
					DeInitializeDevice();
				}

			},
			error: function(jqXHR, textStatus, errorThrown)
			{
				alert("Finger print not captured");
			}	 

		});

		
	}); 

}


function captureBFDFingerCogentAL(fingerindex)

{

	$(document).ready(function() {

		var button=fingerindex;
		var strMessage = "Capture";
		var imageNum = 1;

		var finger = ""; 
		var nfiq = ""
			var fingerPos = "";

		$.ajax({
			type: "GET",
			url: cogentALurl+"?action=" + strMessage + "&image=" + imageNum,
			crossDomain: true,
			dataType: 'jsonp',
			jsonp: 'callback',
			jsonpCallback: 'jsonpCallback',
			success: function(jsonObj)
			{
				if(jsonObj.byteIsoImage != undefined)
				{

					finger =  jsonObj.byteIsoImage;
					nfiq = "1";
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

					
					CapCount++;
					if (CapCount == 10)
					{
						DeInitializeDevice();
					}

				}else
				{
					alert("Finger print not captured");
				}

			},
			error: function(jqXHR, textStatus, errorThrown)
			{
				alert("Finger print not captured");
			}	 

		});
	}); 

}


function DeInitializeDevice()
{

	var strMessage = "DeInitialize";
	$.ajax({
		type: "GET",
		url: cogentALurl+"?action=" + strMessage,
		dataType: 'jsonp',
		jsonp: 'callback',
		jsonpCallback: 'jsonpCallback',
		success: function(jsonObj)
		{
			alert(jsonObj.Message);
		},
		error: function(jqXHR, textStatus, errorThrown)
		{
		}	    
	}); 
}
