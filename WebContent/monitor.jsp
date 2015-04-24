<html>
<head>
<title>Log Monitor</title>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>


</head>
<body>

Monitoring Services are running
<br>
<button type="button" onclick="checkLog();" class="btn btn-default">Click to check for Errors</button>

<div id="output" >output placeholder</div>

<!--  single check here -->
<script>
function checkLog(){
	var url="Monitor.do?A=1&ab=s";
	var data="action=checkLog";
	
	$.ajax({
	  type: "POST",
	  url: url,
	  data: data,
	  success: function(msg){
	  	$("#output").html(msg);
	  }
	  //,dataType: dataType
	});
}

</script>



<!--  polling code below -->
<div id="keepMonitoring" value="Y"  ></div>

<div onclick="stopMonitor();">STOP MONITOR</div>

<!-- <script defer>
function stopMonitor(){
//document.getElementById('keepMonitoring').value='N';
//document.getElementById('keepMonitoring').innerHTML='monitorflagstopped';

$('#keepMonitoring').attr("value","N");
$('#keepMonitoring').html("Monitoring stopped");
}

function poll(){
if($('#keepMonitoring').attr("value")=='Y'){
	alert('monitoring');
}
}

//alert($('#keepMonitoring').attr("value") );

window.setInterval(poll(),2000);




</script> -->

</body>
</html>