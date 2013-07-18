<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.memcache.*" %>
<%@ page import="org.faithfarm.datawriters.PersonDao" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<%
if(request.getParameter("delete") != null) {
	MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
	cache.clearAll();
}
%>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Monthly Corporate Student Census</title>
<link href="<%=request.getContextPath()%>/styles/report.css" rel="stylesheet" type="text/css" />
  
  <script language="javascript" src='script/jquery-1.6.min.js'></script>
  <script language="javascript" src='script/ajax.util.js'></script>

  	<script language="javascript">
	function printPage()
	{
	window.print();
	}
	
</script>

  </head>
<body>
</style>
  <!-- content -->  
  
 <!-- home page content -->
 <!-- list container -->
 <div align="center">
 
	   	  <table width="90%" cellspacing="0" cellpadding="0" border="0">
          <tr>
          	<td width="50%" align="left">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/report-logo.jpg"  /></td>
          	<td width="50%" align="right" valign="top">
          		<div style="font-size:14px"><b>Monthly Corporate Student Census</b></div>
          		<div style="font-size:10px">Run on:&nbsp;&nbsp;<i><%=new java.util.Date()%></i></div>
          	</td>
          </tr>
          </table>
          <table width="90%" cellspacing="1" cellpadding="3" border="1">
          	<tr>
          		<td width="20%" valign="top">
          			<%= session.getAttribute("farm") %> Campus
          		</td>
          		<td width="30%" valign="top">
          			Report for the month of: 
          			<%
          			Calendar reportCal = Calendar.getInstance();
          			Locale ourLocale = new Locale("ENGLISH", "US");
          			reportCal.set(Calendar.DAY_OF_MONTH, 1);
          			Date reportDate = reportCal.getTime();
          			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          			Integer[] beginningTotal = (Integer[])request.getAttribute("beginningTotal");
          			Integer[] newTotal = (Integer[])request.getAttribute("newTotal");
          			Integer[] leftTotal = (Integer[])request.getAttribute("leftTotal");
          			Integer[] walkedOff = (Integer[])request.getAttribute("walkedOff");
          			Integer[] dismissed = (Integer[])request.getAttribute("dismissed");
          			Integer[] graduated = (Integer[])request.getAttribute("graduated");
          			Integer[] toSLS = (Integer[])request.getAttribute("toSLS");
          			Integer[] leftVoluntarily = (Integer[])request.getAttribute("leftVoluntarily");
          			Integer[] toOmega = (Integer[])request.getAttribute("toOmega");
          			
          			%>
          			<strong><%= reportCal.getDisplayName(Calendar.MONTH, Calendar.LONG, ourLocale) %></strong>
          		</td>
          		<td>
          			<table>
          				<tr>
          					<td width="80%">Month: <strong><%= reportCal.getDisplayName(Calendar.MONTH, Calendar.LONG, ourLocale) %></strong></td>
          					<td width="20%">Year: <strong><%= reportCal.get(Calendar.YEAR) %></strong></td>
          				</tr>
          				<tr>
          					<td colspan="2">Prepared By: ___________________________________</td>
          				</tr>
          				<tr>
          					<td colspan="2">Today's Date: <strong><%= dateFormat.format(new Date()) %></strong></td>
          				</tr>
          			</table>
          		</td>
          	</tr>
		  </table>
		  <br/>
		  <div><strong>ACTIVITY SUMMARY:</strong></div>
		  <div style="margin-left: auto; margin-right: auto;">
		  	<div class="census-summary-inner">
			  	<ul>
			  		<li>How Many Students Entered This Month?&nbsp;<div><%= request.getAttribute("newStudents") %></div></li>
			  		<li>How Many First Time Students?&nbsp;<div><%= request.getAttribute("firstTime") %></div></li>
			  		<li>How Many Returning Students?&nbsp;<div><%= request.getAttribute("returning") %></div></li>
			  	</ul>
		  	</div>
		  </div>
		  
		  <table cellpadding="0" cellspacing="0" width="90%" border="1">
		  	<tr>
		  		<td colspan="2">
		  			<strong>IN AND OUT ACTIVITY THIS MONTH</strong>
		  		</td>
		  		<td>
		  			<strong>GENERAL</strong>
		  		</td>
		  		<td>
		  			<strong>SLS</strong>
		  		</td>
		  		<td>
		  			<strong>OMEGA</strong>
		  		</td>
		  		<td>
		  			<strong>TOTAL</strong>
		  		</td>
		  	</tr>
		  	
		  	<tr>
		  		<td colspan="2">
		  			Students In Program At Start Of Month
		  		</td>
		  		<td>
		  			<%= beginningTotal[0] %>
		  		</td>
		  		<td>
		  			<%= beginningTotal[1] %>
		  		</td>
		  		<td>
		  			<%= beginningTotal[2] %>
		  		</td>
		  		<td>
		  			<%= beginningTotal[3] %>
		  		</td>
		  	</tr>
		  	
		  	<tr>
		  		<td>
		  			New Students Checked In
		  		</td>
		  		<td>
		  			+
		  		</td>
		  		<td>
		  			<%= newTotal[0] %>
		  		</td>
		  		<td>
		  			<%= newTotal[1] %>
		  		</td>
		  		<td>
		  			<%= newTotal[2] %>
		  		</td>
		  		<td>
		  			<%= newTotal[3] %>
		  		</td>
		  	</tr>
		  	
		  	<tr>
		  		<td>
		  			Students Left This Month
		  		</td>
		  		<td>
		  			-
		  		</td>
		  		<td>
		  			<%= leftTotal[0] %>
		  		</td>
		  		<td>
		  			<%= leftTotal[1] %>
		  		</td>
		  		<td>
		  			<%= leftTotal[2] %>
		  		</td>
		  		<td>
		  			<%= leftTotal[3] %>
		  		</td>
		  	</tr>
		  	
		  	<tr>
		  		<td>
		  			<strong>TOTAL STUDENTS AT END OF THIS MONTH:</strong>
		  		</td>
		  		<td>
		  			=
		  		</td>
		  		<td>
		  			<%= beginningTotal[0] + newTotal[0] - leftTotal[0] %>
		  		</td>
		  		<td>
		  			<%= beginningTotal[1] + newTotal[1] - leftTotal[1] %>
		  		</td>
		  		<td>
		  			<%= beginningTotal[2] + newTotal[2] - leftTotal[2] %>
		  		</td>
		  		<td>
		  			<%= beginningTotal[3] + newTotal[3] - leftTotal[3] %>
		  		</td>
		  	</tr>
		  		
		  </table>
		  
		  <div>&nbsp;</div>
		  
		  <table width="90%" cellpadding="0" cellspacing="0" border="1">
		  	<tr>
		  		<td>
		  			<strong>CHECK OUT STUDENT DETAILS</strong>
		  		</td>
		  		<td>
		  			<strong>GENERAL</strong>
		  		</td>
		  		<td>
		  			<strong>SLS</strong>
		  		</td>
		  		<td>
		  			<strong>OMEGA</strong>
		  		</td>
		  		<td>
		  			<strong>TOTAL</strong>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>
		  			Walked Off
		  		</td>
		  		<td>
		  			<%= walkedOff[0] %>
		  		</td>
		  		<td>
		  			<%= walkedOff[1] %>
		  		</td>
		  		<td>
		  			<%= walkedOff[2] %>
		  		</td>
		  		<td>
		  			<%= walkedOff[3] %>
		  		</td>
		  	</tr>
		  	
		  	<tr>
		  		<td>
		  			Dismissed
		  		</td>
		  		<td>
		  			<%= dismissed[0] %>
		  		</td>
		  		<td>
		  			<%= dismissed[1] %>
		  		</td>
		  		<td>
		  			<%= dismissed[2] %>
		  		</td>
		  		<td>
		  			<%= dismissed[3] %>
		  		</td>
		  	</tr>
		  	
		  	<tr>
		  		<td>
		  			Left Properly / Graduated
		  		</td>
		  		<td>
		  			<%= graduated[0] %>
		  		</td>
		  		<td>
		  			<%= graduated[1] %>
		  		</td>
		  		<td>
		  			<%= graduated[2] %>
		  		</td>
		  		<td>
		  			<%= graduated[3] %>
		  		</td>
		  	</tr>
		  	
		  	<tr>
		  		<td>
		  			Left Properly / Did Not Graduate
		  		</td>
		  		<td>
		  			<%= leftVoluntarily[0] %>
		  		</td>
		  		<td>
		  			<%= leftVoluntarily[1] %>
		  		</td>
		  		<td>
		  			<%= leftVoluntarily[2] %>
		  		</td>
		  		<td>
		  			<%= leftVoluntarily[3] %>
		  		</td>
		  	</tr>
		  	
		  	<tr>
		  		<td>
		  			Left Properly / To SLS Program
		  		</td>
		  		<td>
		  			<%= toSLS[0] %>
		  		</td>
		  		<td colspan="2" class="black">
		  			&nbsp;
		  		</td>
		  		<td>
		  			<%= toSLS[3] %>
		  		</td>
		  	</tr>
		  	
		  	<tr>
		  		<td>
		  			Left Properly / To Omega Program
		  		</td>
		  		<td>
		  			<%= toOmega[0] %>
		  		</td>
		  		<td colspan="2" class="black">
		  			&nbsp;
		  		</td>
		  		<td>
		  			<%= toOmega[3] %>
		  		</td>
		  	</tr>
		  	
		  	<tr>
		  		<td>
		  			<strong>TOTAL STUDENTS CHECKED OUT THIS MONTH:</strong>
		  		</td>
		  		<td>
		  			<%= walkedOff[0] + dismissed[0] + graduated[0] + leftVoluntarily[0] + toSLS[0] + toOmega[0] %>
		  		</td>
		  		<td>
		  			<%= walkedOff[1] + dismissed[1] + graduated[1] + leftVoluntarily[1] + toSLS[1] + toOmega[1] %>
		  		</td>
		  		<td>
		  			<%= walkedOff[2] + dismissed[2] + graduated[2] + leftVoluntarily[2] + toSLS[2] + toOmega[2] %>
		  		</td>
		  		<td>
		  			<%= walkedOff[3] + dismissed[3] + graduated[3] + leftVoluntarily[3] + toSLS[3] + toOmega[3] %>
		  		</td>
		  	</tr>
		  		
		  			
		  	
		  			
		  </table>
			
	  	</br></br>	  		
	  		<a href="javascript:print();" style="text-decoration:none;"><img src="<%=request.getContextPath()%>/images/Printer-icon.png" border=0 height="15" width="15"></a>
  		</br></br>
	  	</div>
  		
<script type="text/javascript">
 $(window).load(function () {
   init();
 });
</script>
</body>
</html>