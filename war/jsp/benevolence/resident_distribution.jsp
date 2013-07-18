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
<%@ page import="org.faithfarm.Util" %>
<%@ page import="org.faithfarm.datawriters.PersonDao" %>
<%@ page import="org.faithfarm.dataobjects.Benevolence" %>
<%@ page import="org.faithfarm.dataobjects.Deduction" %>
<%@ page import="org.faithfarm.utilities.ApplicationTools" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Benevolence - Resident Distribution</title>
<link href="<%=request.getContextPath()%>/styles/report.css" rel="stylesheet" type="text/css"/>
  
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
           <%
          String farmBase = (String)session.getAttribute("farmBase");
          Date thisFriday = (Date)request.getAttribute("thisFriday");
          Date theFirst = (Date)request.getAttribute("theFirst");
          SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
          String weekEnding = toFormat.format(thisFriday);
          Map<Entity, Benevolence> activeRecipientMap = (Map<Entity, Benevolence>)request.getAttribute("activeRecipientMap");
          Map<Entity, Benevolence> omegaRecipientMap = (Map<Entity, Benevolence>)request.getAttribute("omegaRecipientMap");
          Map<Entity, Benevolence> slsRecipientMap = (Map<Entity, Benevolence>)request.getAttribute("slsRecipientMap");
          Map<Entity, Benevolence> otherRecipientMap = (Map<Entity, Benevolence>)request.getAttribute("otherRecipientMap");
          HashMap<String, Integer> grandTotal = Benevolence.getGrandTotal(farmBase);
          int totalDue = 0, totalDed = 0, totalYtd = 0, totalSavings = 0, totalPrevSavings = 0;
          %>
 <div align="center">
 
	   	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tr>
          	<td width="50%" align="left">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/logo2.png" style="width:250px;"  /></td>
          	<td width="50%" align="right" valign="top">
          	<h2>
          		Resident Benevolence Distribution Form<br/>
          		Location: <%= farmBase %><br/>
          		Week Ending: <%= weekEnding %>
          	</h2>
          	</td>
          </tr>
          </table>
          
		  <table class="benevolence-table">
		  <thead>
			<tr class="tableheader">
				<th><div>Weeks</div></th>
				<th>Resident Name</th>
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
			</thead>
			<tbody>
				<tr>
					<td colspan="13"><strong>Residents</strong></td>
				</tr>
			<%
			int x = 0;
			int lastAmt = 7;
			int activeTotalYtd = 0;
			int activePrevSavings = 0;
			int activeTotalDue = 0;
			int activeTotalDed = 0;
			int activeSavings = 0;
			
			for(Map.Entry<Entity, Benevolence> entry : activeRecipientMap.entrySet()) {
				Entity entity = entry.getKey();
				Benevolence benevolenceInstance = entry.getValue();
				Integer amountDue = benevolenceInstance.getAmountDue() != 0 ? benevolenceInstance.getAmountDue() : Integer.parseInt(Benevolence.getAmountDueFromEntity(entity), 10);
				int totalDeductions = Deduction.getTotalDeductions(entity);
				int weeklySavings = Benevolence.getWeeklySavingsFromEntity(entity);
				totalDue += amountDue;
				totalDed += totalDeductions;
				totalYtd += benevolenceInstance.getYtdPaid();
				totalSavings += weeklySavings;
				totalPrevSavings += benevolenceInstance.getSaved();
				Boolean pageBreak = false;
				if(lastAmt == 7 && amountDue != 7) {
					pageBreak = true;  
					//adjust totalDue to reset for totals for those
					//resident who are not paid the $7
					totalDue-=lastAmt;
				}
				else {
					 activeTotalYtd = totalYtd;
					 activePrevSavings = totalPrevSavings;
					 activeTotalDue = totalDue;
					 activeTotalDed = totalDed;
					 activeSavings = totalSavings;
				}
				lastAmt = amountDue;
				

				if(pageBreak) {  					%>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td><strong>Resident Totals</strong></td>
						<td>$<%= activeTotalYtd %>.00</td>
						<td>$<%= activePrevSavings %>.00</td>
						<td>$<%= activeTotalDue %>.00</td>
						<td>$<%= activeTotalDed %>.00</td>
						<td>&nbsp;</td>
						<td>$<%= activeTotalDue - activeTotalDed %>.00</td>
						<td>$<%= activeSavings %>.00</td>
						<td>$<%= activeTotalYtd + (activeTotalDue - activeTotalDed) %>.00</td>
						<td>$<%= activeSavings + activePrevSavings %>.00</td>
						<td>&nbsp;</td>
					</tr>
					<%
					 activeTotalYtd = 0;
					 activePrevSavings = 0;
					 activeTotalDue = 0;
					 activeTotalDed = 0;
					 activeSavings = 0;
					 
					
					%>
					</tbody>
					</table>
					<div class="page-break">&nbsp;</div>
					<table class="benevolence-table">
					  <thead>
						<tr class="tableheader">
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
						</thead>
						<tbody>
					<%
				}
				
				%>
				<tr class="<%= x % 2 == 0 ? "even" : "odd" %>">
					<td><%= Benevolence.getWeeksFromEntity(entity) %></td>
					<td><%= Benevolence.getNameFromEntity(entity) %></td>
					<td><%= Benevolence.getEntryDateFromEntity(entity) %></td>
					<td>$<%= benevolenceInstance.getYtdPaid() %>.00</td>
					<td>$<%= benevolenceInstance.getSaved() %>.00</td>
					<td>$<%= amountDue.toString() %>.00</td>
					<td>$<%= String.valueOf(totalDeductions) %>.00</td>
					<td><%= totalDeductions != 0 ? Deduction.getDeductionString(entity) : "" %></td>
					<td>$<%= String.valueOf(amountDue - totalDeductions) %>.00</td>
					<td>$<%= weeklySavings %>.00</td>
					<td>$<%= benevolenceInstance.getYtdPaid() + (amountDue - totalDeductions) %>.00</td>
					<td>$<%= benevolenceInstance.getSaved() + weeklySavings %>.00</td>
					<td>&nbsp;</td>
				</tr>
				<%
				x++;
				
			}
			 activeTotalYtd = totalYtd;
			 activePrevSavings = totalPrevSavings;
			 activeTotalDue = totalDue;
			 activeTotalDed = totalDed;
			 activeSavings = totalSavings;
			%>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><strong>Resident Totals</strong></td>
				<td>$<%= activeTotalYtd %>.00</td>
				<td>$<%= activePrevSavings %>.00</td>
				<td>$<%= activeTotalDue %>.00</td>
				<td>$<%= activeTotalDed %>.00</td>
				<td>&nbsp;</td>
				<td>$<%= activeTotalDue - activeTotalDed %>.00</td>
				<td>$<%= activeSavings %>.00</td>
				<td>$<%= activeTotalYtd + (activeTotalDue - activeTotalDed) %>.00</td>
				<td>$<%= activeSavings + activePrevSavings %>.00</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<table class="benevolence-table">
		  <thead>
			<tr class="tableheader">
				<th><div>Weeks</div></th>
				<th>Resident Name</th>
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
			</thead>
			<tbody>
			<tr>
				<td colspan="13"><strong>SLS</strong></td>
			</tr>
			<%
			for(Map.Entry<Entity, Benevolence> entry : slsRecipientMap.entrySet()) {
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
					<td><%= totalDeductions != 0 ? Deduction.getDeductionString(entity) : "" %></td>
					<td>$<%= String.valueOf(amountDue - totalDeductions) %>.00</td>
					<td>$<%= weeklySavings %>.00</td>
					<td>$<%= benevolenceInstance.getYtdPaid() + amountDue %>.00</td>
					<td>$<%= benevolenceInstance.getSaved() + weeklySavings %>.00</td>
					<td>&nbsp;</td>
				</tr>
				<%
				x++;
			}
			int slsTotalYtd = totalYtd - activeTotalYtd;
			int slsPrevSavings = totalPrevSavings - activePrevSavings;
			int slsTotalDue = totalDue - activeTotalDue;
			int slsTotalDed = totalDed - activeTotalDed;
			int slsSavings = totalSavings - activeSavings;
			%>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><strong>SLS Totals</strong></td>
				<td>$<%= slsTotalYtd %>.00</td>
				<td>$<%= slsPrevSavings %>.00</td>
				<td>$<%= slsTotalDue %>.00</td>
				<td>$<%= slsTotalDed %>.00</td>
				<td>&nbsp;</td>
				<td>$<%= slsTotalDue - slsTotalDed %>.00</td>
				<td>$<%= slsSavings %>.00</td>
				<td>$<%= slsTotalYtd + (slsTotalDue - slsTotalDed) %>.00</td>
				<td>$<%= slsSavings + slsPrevSavings %>.00</td>
				<td>&nbsp;</td>
			</tr>
		</tbody>
		</table>
		<div class="page-break">&nbsp;</div>
		<table class="benevolence-table">
		  <thead>
			<tr class="tableheader">
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
			</thead>
			<tbody>
			<tr>
				<td colspan="13"><strong>Omega</strong></td>
			</tr>
			<%
			for(Map.Entry<Entity, Benevolence> entry : omegaRecipientMap.entrySet()) {
				Entity entity = entry.getKey();
				Benevolence benevolenceInstance = entry.getValue();
				Integer amountDue = 25;
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
					<td><%= totalDeductions != 0 ? Deduction.getDeductionString(entity) : "" %></td>
					<td>$<%= String.valueOf(amountDue - totalDeductions) %>.00</td>
					<td>$<%= weeklySavings %>.00</td>
					<td>$<%= benevolenceInstance.getYtdPaid() + amountDue %>.00</td>
					<td>$<%= benevolenceInstance.getSaved() + weeklySavings %>.00</td>
					<td>&nbsp;</td>
				</tr>
				<%
				x++;
			}
			int omegaTotalYtd = totalYtd - activeTotalYtd - slsTotalYtd;
			int omegaPrevSavings = totalPrevSavings - activePrevSavings - slsPrevSavings;
			int omegaTotalDue = totalDue - activeTotalDue - slsTotalDue;
			int omegaTotalDed = totalDed - activeTotalDed - slsTotalDed;
			int omegaSavings = totalSavings - activeSavings - slsSavings;
			%>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><strong>Omega Totals</strong></td>
				<td>$<%= omegaTotalYtd %>.00</td>
				<td>$<%= omegaPrevSavings %>.00</td>
				<td>$<%= omegaTotalDue %>.00</td>
				<td>$<%= omegaTotalDed %>.00</td>
				<td>&nbsp;</td>
				<td>$<%= omegaTotalDue - omegaTotalDed %>.00</td>
				<td>$<%= omegaSavings %>.00</td>
				<td>$<%= omegaTotalYtd + (omegaTotalDue - omegaTotalDed) %>.00</td>
				<td>$<%= omegaSavings + omegaPrevSavings %>.00</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div class="page-break">&nbsp;</div>
		<table class="benevolence-table">
		  <thead>
			<tr class="tableheader">
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
			</thead>
			<tbody>
			<tr>
				<td colspan="13"><strong>Left</strong></td>
			</tr>
			<%
			for(Map.Entry<Entity, Benevolence> entry : otherRecipientMap.entrySet()) {
				Entity entity = entry.getKey();
				
				System.out.println (entity.getProperty("firstName")+" "+entity.getProperty("lastName"));
				
				Benevolence benevolenceInstance = entry.getValue();
				Integer amountDue = 0;
				int totalDeductions = 0;
				int weeklySavings = 0;
				totalDue += amountDue;
				totalDed += totalDeductions;
				totalYtd += benevolenceInstance.getYtdPaid();
				totalSavings += weeklySavings;
				totalPrevSavings += benevolenceInstance.getSaved();
				Date leftDate = Util.parseDate((String)entity.getProperty("exitDate"));
				String leftStr = "Unknown";
				if(leftDate != null) {
					leftStr = toFormat.format(leftDate);
				}
				%>
				<tr class="<%= x % 2 == 0 ? "even" : "odd" %>">
					<td><%= Benevolence.getWeeksFromEntity(entity) %></td>
					<td><%= Benevolence.getNameFromEntity(entity) %></td>
					<td><%= Benevolence.getEntryDateFromEntity(entity) %></td>
					<td>$<%= benevolenceInstance.getYtdPaid() %>.00</td>
					<td>$<%= benevolenceInstance.getSaved() %>.00</td>
					<td>$<%= amountDue.toString() %>.00</td>
					<td>$<%= String.valueOf(totalDeductions) %>.00</td>
					<td><%= totalDeductions != 0 ? Deduction.getDeductionString(entity) : "" %></td>
					<td>$<%= String.valueOf(amountDue - totalDeductions) %>.00</td>
					<td>$<%= weeklySavings %>.00</td>
					<td>$<%= benevolenceInstance.getYtdPaid() + amountDue %>.00</td>
					<td>$<%= benevolenceInstance.getSaved() + weeklySavings %>.00</td>
					<td><strong>Left: <%= leftStr %></strong></td>
				</tr>
				<%
				x++;
			}
			int otherTotalYtd = totalYtd - activeTotalYtd - slsTotalYtd - omegaTotalYtd;
			int otherPrevSavings = totalPrevSavings - activePrevSavings - slsPrevSavings - omegaPrevSavings;
			int otherTotalDue = totalDue - activeTotalDue - slsTotalDue - omegaTotalDue;
			int otherTotalDed = totalDed - activeTotalDed - slsTotalDed - omegaTotalDed;
			int otherSavings = totalSavings - activeSavings - slsSavings - omegaSavings;
			%>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><strong>Left Totals</strong></td>
				<td>$<%= otherTotalYtd %>.00</td>
				<td>$<%= otherPrevSavings %>.00</td>
				<td>$<%= otherTotalDue %>.00</td>
				<td>$<%= otherTotalDed %>.00</td>
				<td>&nbsp;</td>
				<td>$<%= otherTotalDue - otherTotalDed %>.00</td>
				<td>$<%= otherSavings %>.00</td>
				<td>$<%= otherTotalYtd + (otherTotalDue - otherTotalDed) %>.00</td>
				<td>$<%= otherSavings + otherPrevSavings %>.00</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><strong>Grand Totals</strong></td>
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
			</tbody>
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