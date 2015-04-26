<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import = "org.apache.logging.log4j.Logger" %>
	<%@ page import = "org.apache.logging.log4j.LogManager" %>
	<%@ page import = "com.vz.services.ParentService" %>
	
	<%! private static final Logger logger = LogManager.getLogger("logGen.jsp"); %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<title>Generating Logs </title>
</head>
<body>
<input id="hostName" type="hidden" value="ebiz.verizon.com" />

<bean:write name="LogGenForm" property="message" />
<script defer>
$.get("http://ipinfo.io", function(response) {
    //alert(response.hostname);
$("#hostName").val(response.hostname);
$("#showHName").html(response.hostname);
$("#showResponse").html(response);
}, "jsonp");
</script>

<br>HostName:<span id="showHName" ></span>
<br>Response:<span id="showResponse" ></span>
<br>

<!--  Generating Logs -->
<%
out.print("Generating Logs");

//Call Different files (multiple times) here for writing logs

logger.warn("Application is up. Accessing timberland.");
logger.info("Start the workflow. ");
logger.warn("Warn log.", new Exception("Pavneet Raised a question"));
System.out.println("done writing Start up logs ... starting continuous logging mechanism");

ParentService parentService = new ParentService();
parentService.startServices();

//have thread so that no order is found and have some sleep times for 3 threads.

%>
</body>
</html>