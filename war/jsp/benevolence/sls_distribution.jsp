<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.datawriters.PersonDao" %>
<%@ page import="org.faithfarm.dataobjects.Benevolence" %>
<%@ page import="org.faithfarm.dataobjects.Deduction" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Benevolence - SLS Distribution</title>
<link href="<%=request.getContextPath()%>/styles/report.css" rel="stylesheet" type="text/css" />
  
  <script language="javascript" src='/script/jquery-1.6.min.js'></script>
  <script language="javascript" src='/script/ajax.util.js'></script>

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
 
	   	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tr>
          	<td width="50%" align="left">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/report-logo.jpg"  /></td>
          	<td width="50%" align="right" valign="top">
          		<div style="font-size:14px"><b>SLS Benevolence Distribution</b></div>
          		<div style="font-size:10px">Run on:&nbsp;&nbsp;<i><%=new java.util.Date()%></i></div>
          	</td>
          </tr>
          </table>
          <%
          String farmBase = (String)session.getAttribute("farmBase");
          Date thisFriday = (Date)request.getAttribute("thisFriday");
          Date theFirst = (Date)request.getAttribute("theFirst");
          SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
          String weekEnding = toFormat.format(thisFriday);
          Map<Entity, Benevolence> recipientMap = (Map<Entity, Benevolence>)request.getAttribute("recipientMap");
          HashMap<String, Integer> grandTotal = Benevolence.getGrandTotal(farmBase);
          int totalDue = 0, totalDed = 0, totalYtd = 0, totalSavings = 0, totalPrevSavings = 0;
          %>
          <div style="text-align: left;width: 100%; margin-left: auto; margin-right: auto;">
          	
          	<h3>
          		SLS Benevolence Distribution Form<br/>
          		Location: <%= farmBase %><br/>
          		Week Ending: <%= weekEnding %>
          	</h3>
          </div>
          
		  <table class="benevolence-table">
				<th><div>Weeks</div></th>
				<th>Student Name</th>
				<th><div>Admission Date</div></th>
				<th><div>Previous Paid YTD</div></th>
				<th><div>Previous Savings</div></th>
				<th><div>Amount Due</div></th>
				<th><div>Less Deductions</div></th>
				<th>Deduction Description</th>
				<th><div>Amount Paid</div></th>
				<th><div>Weekly Savings</div></th>
				<th><div>Amount Paid YTD</div></th>
				<th><div>Total Savings</div></th>
				<th>Received by: (Signature)</th>
			</tr>
			<%
			int x = 0;
			for(Map.Entry<Entity, Benevolence> entry : recipientMap.entrySet()) {
				Entity entity = entry.getKey();
				Benevolence benevolenceInstance = entry.getValue();
				Integer amountDue = Benevolence.getAmountDueSLSFromEntity(entity);
				int totalDeductions = Deduction.getTotalDeductions(entity);
				int weeklySavings = 0;
				totalDue += amountDue;
				totalDed += totalDeductions;
				totalYtd += benevolenceInstance.getYtdPaid();
				totalSavings += weeklySavings;
				totalPrevSavings += benevolenceInstance.getSaved();
				%>
				<tr class="<%= x % 2 == 0 ? "even" : "odd" %>">
					<td><%= Benevolence.getWeeksFromEntity(entity) %></td>
					<td><%= Benevolence.getNameFromEntity(entity) %></td>
					<td><%= Benevolence.getEntryDateFromEntity(entity) %></td>
					<td>$<%= benevolenceInstance.getYtdPaid() %>.00</td>
					<td>$<%= benevolenceInstance.getSaved() %>.00</td>
					<td>$<%= amountDue.toString() %>.00</td>
					<td>$<%= String.valueOf(totalDeductions) %>.00</td>
					<td><%= Deduction.getDeductionString(entity) %></td>
					<td>$<%= String.valueOf(amountDue - totalDeductions) %>.00</td>
					<td>$<%= weeklySavings %>.00</td>
					<td>$<%= benevolenceInstance.getYtdPaid() + amountDue %>.00</td>
					<td>$<%= benevolenceInstance.getSaved() + weeklySavings %>.00</td>
					<td>&nbsp;</td>
				</tr>
				<%
			}
			%>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><strong>SLS Totals</strong></td>
				<td>$<%= totalYtd %></td>
				<td>$<%= totalPrevSavings %></td>
				<td>$<%= totalDue %></td>
				<td>$<%= totalDed %></td>
				<td>&nbsp;</td>
				<td>$<%= totalDue - totalDed %></td>
				<td>$<%= totalSavings %></td>
				<td>$<%= (totalDue - totalDed) + totalYtd %></td>
				<td>$<%= totalPrevSavings + totalSavings %></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><strong>GRAND TOTAL</strong></td>
				<td>$<%= grandTotal.get("previousPaidYtd") %></td>
				<td>$<%= grandTotal.get("previousSavings") %></td>
				<td>$<%= grandTotal.get("amountDue") %></td>
				<td>$<%= grandTotal.get("deductions") %></td>
				<td>&nbsp;</td>
				<td>$<%= grandTotal.get("amountPaid") %></td>
				<td>$<%= grandTotal.get("weeklySavings") %></td>
				<td>$<%= grandTotal.get("amountPaidYtd") %></td>
				<td>$<%= grandTotal.get("totalSavings") %></td>
				<td></td>
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