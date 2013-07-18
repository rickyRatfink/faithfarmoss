<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.datawriters.PersonDao" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Weekly Benevolence Report</title>
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
          		<div style="font-size:14px"><b>Weekly Benevolence Report</b></div>
          		<div style="font-size:10px">Run on:&nbsp;&nbsp;<i><%=new java.util.Date()%></i></div>
          	</td>
          </tr>
          </table>
           <table width="90%" cellspacing="10" cellpadding="0" border="0">
             <tr>
				  <td width="48%" align="left" valign="top">
				  	<table width="100%" cellspacing="0" cellpadding="0" border="0">
				  		<tr>
				  			<td colspan="5">
				  				<h3 style="margin-right:0px">Students To Be Added</h3>
				  			</td>
				  		</tr>
				  		<tr>
				  			<td class="report-heading">Name</td>
				  			<td class="report-heading">Entry Date</td>
				  			<td class="report-heading">SSN</td>
				  			<td class="report-heading">New</td>
				  			<td class="report-heading">Repeat</td>
				  		</tr>
				  		<%
				  		Map<String, Object> properties = null;
				  		Map<String, Object> allProperties = null;
				  		ArrayList addedStudents = (ArrayList<Entity>)request.getAttribute("addedStudents");
				  		SimpleDateFormat exitFormat = new SimpleDateFormat("MM/dd/yyyy");
				  		SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
				  		SimpleDateFormat fromFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				  		Iterable<Entity> allStudents = PersonDao.getAllPersons(null, null, null, "Resident", null, null, null, (String)session.getAttribute("farmBase"));
				  		
				  		for(Integer idx = 0; idx < addedStudents.size(); idx++) {
				  			Entity current = (Entity)addedStudents.get(idx);
				  			properties = current.getProperties();
				  			Date studentEntryDate;
				  			String entryDateStr;
				  			try {
					  			studentEntryDate = fromFormat.parse((String)properties.get("entryDate"));
					  			entryDateStr = toFormat.format(studentEntryDate);
					  		} catch(ParseException e) {
					  			entryDateStr = "Unknown";
					  		}
				  			boolean isReturning = false;
				  			for(Entity student : allStudents) {
				  				allProperties = student.getProperties();
				  				String firstName = (String)allProperties.get("firstName");
				  				String lastName = (String)allProperties.get("lastName");
				  				String allEntryDate = (String)allProperties.get("entryDate");
				  				String currentEntryDate = (String)properties.get("entryDate");
				  				if(firstName.equals((String)properties.get("firstName")) && lastName.equals((String)properties.get("lastName")) && !allEntryDate.equals(currentEntryDate)) {
				  					isReturning = true;
				  				}
				  			}
				  			
				  		
				  		%>
				  		<tr>
				  			<td>
				  				<%= properties.get("firstName").toString().concat(" ").concat(properties.get("lastName").toString()) %>
				  			</td>
				  			<td>
				  				<%= entryDateStr %>
				  			</td>
				  			<td>
				  				<%= properties.get("ssn") %>
				  			</td>
				  			<td>
				  				<%= isReturning == false ? "Yes" : "No" %>
				  			</td>
				  			<td>
				  				<%= isReturning == true ? "Yes" : "No" %>
				  			</td>
				  		</tr>
				  		<% } %>
				  		
				  		
				  	</table>
				  </td>
				  <td width="48%" align="right" valign="top">
				  	<table width="100%" cellspacing="0" cellpadding="0" border="0">
				  		<tr>
				  			<td colspan="4">
				  				<h3 style="margin-right:0px">Students To Be Removed</h3>
				  			</td>
				  		</tr>
				  		<tr>
				  			<td class="report-heading">Name</td>
				  			<td class="report-heading">Exit Date</td>
				  			<td class="report-heading">SSN</td>
				  			<td class="report-heading">Reason</td>
				  		</tr>
				  		<%
				  		
				  		
				  		ArrayList removedStudents = (ArrayList<Entity>)request.getAttribute("removedStudents");
				  		
				  		
				  		for(Integer idx = 0; idx < removedStudents.size(); idx++) {
				  			Entity current = (Entity)removedStudents.get(idx);
				  			properties = current.getProperties();
				  			Date studentExitDate;
				  			String exitDateStr;
				  			try {
				  				studentExitDate = exitFormat.parse((String)properties.get("exitDate"));
				  				exitDateStr = toFormat.format(studentExitDate);
				  			} catch(ParseException e) {
				  				System.out.println(e.getMessage());
				  				exitDateStr = "Unknown";
				  			}
				  		%>
				  		<tr>
				  			<td>
				  				<%= properties.get("firstName").toString().concat(" ").concat(properties.get("lastName").toString()) %>
				  			</td>
				  			<td>
				  				<%= exitDateStr %>
				  			</td>
				  			<td>
				  				<%= properties.get("ssn") %>
				  			</td>
				  			<td>
				  				<%= properties.get("personStatus") %>
				  			</td>
				  		</tr>
				  		<% } %>
				  		
				  	</table>
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