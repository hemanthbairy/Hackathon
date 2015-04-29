<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
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
<div class="container" >
<div class="jumbotron" >
<h2>Monitoring Services are running.</h2>
<br>Latest TimeStamp recorded : <bean:write name="MonitorForm" property="latestEventTS" />
<br>New Latest TS recorded : <span id="newLTS"></span>
<br>KeyWords for e-mail alert : <bean:write name="MonitorForm" property="emKeyWords" />
<br>
</div>

<input id="idLatestEventTSOnLoad" type="hidden" value="<bean:write name="MonitorForm" property="latestEventTS" />" />
<input id="idLatestEventTS" type="hidden" value="<bean:write name="MonitorForm" property="latestEventTS" />" />
<input id="tempEventTS" type="hidden" value="" />
<input id="firstCall" type="hidden" value="1" />
<input id="emKeyWords" type="hidden" value="<bean:write name="MonitorForm" property="emKeyWords" />" />





<button type="button" disabled onclick="checkLog();" class="btn btn-default">Click to check for Errors</button>
<button type="button" class="btn btn-default"  onclick="stopMonitor();">STOP MONITOR</button>
<div class="panel panel-default">
  <div class="panel-body bold" id="keepMonitoring" value="Y"  >Press stop monitor button to stop monitoring.</div>
</div>
<span style="" id="firstTime" val="N" ></span>

<br>
<div id="output" style="overflow:auto; width:100% ; max-height:500px; border:0px red solid; " >
<table class="table table-condensed table-hover table-bordered" style="font-size:14px;" >
    <thead>
      <tr>
        <th>Time</th>
        <th>Level</th>
        <th class="col-md-3" >Message</th>
        <th>File #Line</th>
        <th>Exception Message</th>
        <th>Ex:ClassName</th>
        <th>Ex:File #Line</th>
        <th>Ex:Method</th>
        <th>User</th>
        <!-- <th>Ex:L</th> -->
      </tr>
    </thead>
    <tbody id="logTblBody"></tbody>
    
  </table>
</div>

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

function pollLog(){
	var latestTS = $('#idLatestEventTS').val(); 
	if(latestTS==''){	return ;	}

	
	var url="Monitor.do?A=1&ab=s";
	var data="action=pollLog&latestTS="+latestTS+"";
	$("#firstCall").val("2");
	
	$.ajax({
	  type: "POST",
	  url: url,
	  data: data,
	  dataType: "json",
	  success: function(msg){
	  	$('#tempEventTS').val(msg.lastReadEventTS);
	  	$('#newLTS').html(msg.lastReadEventTS);
	  	//$("#output").append(msg.lastReadEventTS+":"+msg.logData);
	  	/* $.each( msg.logData, function( key, val ) {
				$("#output").append(key+"-"+val);
 			 }); */
		//$table = $('<table class="table table-condensed table-hover" >');
		//$header = $('<tr>').append($('<th>').html("Log TimeStamp"),$('<th>').html("Log Level"));
		//$table.append($header);
		$tr1 = $('<tr>').append($('<td>').html('Empty Result'));
		
		$.each(msg.logData,function(key,val){
				//Add warning class for row backGround
				//if(val.logger == 'ERROR')
				if(val.logLevel == 'ERROR'){
				$tr = $('<tr class="danger" >');
				}else{
					$tr = $('<tr>');
				}
				$tr.append(
					$('<td>').html(val.event_date),
					$('<td>').html(val.logLevel),
					$('<td>').addClass("col-md-3").html(val.message),
					$('<td>').html(val.logger+" #"+val.logger_line_num),
					$('<td>').html(val.ex_short),
					$('<td>').html(val.ex_class_name),
					$('<td>').html(val.ex_file_name+" #"+val.ex_line_num),
					$('<td>').html(val.ex_method),
					$('<td>').html(val.client_id)
					
				);
				//$tr.appendTo($table);
				$('#logTblBody').prepend($tr);
			}
		);

		//$("#output").html("");
		//$("#output").append($table);
	  
	  //Success Function ends here
	  }
	 
	});
}

</script>



<!--  polling code below -->
<br><br>
<!-- <div class="panel panel-default">
  <div class="panel-body bold" id="keepMonitoring" value="Y"  >Press stop monitor button to stop monitoring.</div>
</div>

<span style="" id="firstTime" val="N" ></span>
<button type="button" class="btn btn-default"  onclick="stopMonitor();">STOP MONITOR</button> -->

<script defer>
function stopMonitor(){
	$('#keepMonitoring').attr("value","N");
	$('#keepMonitoring').html("Monitoring stopped");
}

function poll(){
	if($("#firstCall").val() != '1' ){
		$('#idLatestEventTS').val($('#tempEventTS').val());
	}
	if($('#keepMonitoring').attr("value")=='Y'){
		//alert('monitoring');
		pollLog();
	}
}

//alert($('#keepMonitoring').attr("value") );

window.setInterval(poll,10000);




</script>

</div>
</body>
</html>