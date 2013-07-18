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
<%@ page import="org.faithfarm.dataobjects.ResidentBenevolence" %>
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
          ArrayList system1Residents = (ArrayList)request.getAttribute("system1Residents");
          ArrayList system2Residents = (ArrayList)request.getAttribute("system2Residents");
          ArrayList omegaResidents = (ArrayList)request.getAttribute("omegaResidents");
          ArrayList slsResidents = (ArrayList)request.getAttribute("slsResidents");
          ArrayList leftResidents = (ArrayList)request.getAttribute("leftResidents");
         %>
         
 <form method="POST" action="/benevolence/ManualAdjustments">
 
 <div align="center">
 
	   	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tr>
          	<td width="50%" align="left">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/logo2.png" style="width:250px;"  /></td>
          	<td width="50%" align="right" valign="top">
          	<h2>
          		Resident Benevolence Manual YTD Adjustments<br/>
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
			</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="13"><strong>Residents</strong></td>
				</tr>
			<% 
			double totalPrepaidTYD=0.0;
			double totalPrevSavings=0.0;
			double totalAmtDue=0.0;
			double totalLessDeducion=0.0;
			double totalAmtPaid=0.0;
			double totalAmtPaidYTD=0.0;
			double totalWeeklySavings=0.0;
			double totalSavings=0.0;
			
			for (int i=0;i<system1Residents.size();i++) { 
				ResidentBenevolence ResidentBenevolence = (ResidentBenevolence)system1Residents.get(i);
			%>
					<tr>
						<td><%= ResidentBenevolence.getWeeks() %></td>
						<td><%= ResidentBenevolence.getFirstName() %>&nbsp;<%= ResidentBenevolence.getLastName() %></td>
						<td><%= ResidentBenevolence.getEntryDate() %></td>
						<td>$<input name="prepaidYTD_<%=KeyFactory.keyToString(ResidentBenevolence.getPersonKey())%>" value="<%= ResidentBenevolence.getPrepaidYTD() %>" maxlength="5" size="5"/></td>
						<td>$<input name="prepaidSavings_<%=KeyFactory.keyToString(ResidentBenevolence.getPersonKey())%>" value="<%= ResidentBenevolence.getPrevSavings() %>" maxlength="5" size="5"/></td>
					</tr>
			<% 
			} %>
		
		</tbody>
		</table>
					
	  	</br></br>
	  	
	  	
	  	<!-- Residents under the Old System -->
	  	  <table class="benevolence-table">
		  <thead>
			<tr class="tableheader">
				<th><div>Weeks</div></th>
				<th>Resident Name</th>
				<th><div>Admission Date</div></th>
				<th><div>Previous Paid YTD</div></th>
				<th><div>Previous Savings</div></th>
			</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="13"><strong>Residents</strong></td>
				</tr>
			<% 
			
			for (int i=0;i<system2Residents.size();i++) { 
				ResidentBenevolence ResidentBenevolence = (ResidentBenevolence)system2Residents.get(i);
			%>
					<tr>
						<td><%= ResidentBenevolence.getWeeks() %></td>
						<td><%= ResidentBenevolence.getFirstName() %>&nbsp;<%= ResidentBenevolence.getLastName() %></td>
						<td><%= ResidentBenevolence.getEntryDate() %></td>
						<td>$<input name="prepaidYTD_<%=KeyFactory.keyToString(ResidentBenevolence.getPersonKey())%>" value="<%= ResidentBenevolence.getPrepaidYTD() %>" maxlength="5" size="5"/></td>
						<td>$<input name="prepaidSavings_<%=KeyFactory.keyToString(ResidentBenevolence.getPersonKey())%>" value="<%= ResidentBenevolence.getPrevSavings() %>" maxlength="5" size="5"/></td>
					</tr>
			<% 
			} %>
			
		</tbody>
		</table>
		
	    </br></br>
	  	
	  	
	  	<!-- Omega School -->
	  	  <table class="benevolence-table">
		  <thead>
			<tr class="tableheader">
				<th><div>Weeks</div></th>
				<th>Resident Name</th>
				<th><div>Admission Date</div></th>
				<th><div>Previous Paid YTD</div></th>
				<th><div>Previous Savings</div></th>
			</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="13"><strong>SLS</strong></td>
				</tr>
			<% 
			for (int i=0;i<slsResidents.size();i++) { 
				ResidentBenevolence ResidentBenevolence = (ResidentBenevolence)slsResidents.get(i);
			%>
					<tr>
						<td><%= ResidentBenevolence.getWeeks() %></td>
						<td><%= ResidentBenevolence.getFirstName() %>&nbsp;<%= ResidentBenevolence.getLastName() %></td>
						<td><%= ResidentBenevolence.getEntryDate() %></td>
						<td>$<input name="prepaidYTD_<%=KeyFactory.keyToString(ResidentBenevolence.getPersonKey())%>" value="<%= ResidentBenevolence.getPrepaidYTD() %>" maxlength="5" size="5"/></td>
						<td>$<input name="prepaidSavings_<%=KeyFactory.keyToString(ResidentBenevolence.getPersonKey())%>" value="<%= ResidentBenevolence.getPrevSavings() %>" maxlength="5" size="5"/></td>
					</tr>
			<% 
			} %>
		
		</tbody>
		</table>
	  	</br></br>	
	  	
	  		  	<!-- Omega School -->
	  	  <table class="benevolence-table">
		  <thead>
			<tr class="tableheader">
				<th><div>Weeks</div></th>
				<th>Resident Name</th>
				<th><div>Admission Date</div></th>
				<th><div>Previous Paid YTD</div></th>
				<th><div>Previous Savings</div></th>
			</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="13"><strong>Omega School</strong></td>
				</tr>
			<% 
			
			for (int i=0;i<omegaResidents.size();i++) { 
				ResidentBenevolence ResidentBenevolence = (ResidentBenevolence)omegaResidents.get(i);
			%>
					<tr>
						<td><%= ResidentBenevolence.getWeeks() %></td>
						<td><%= ResidentBenevolence.getFirstName() %>&nbsp;<%= ResidentBenevolence.getLastName() %></td>
						<td><%= ResidentBenevolence.getEntryDate() %></td>
						<td>$<input name="prepaidYTD_<%=KeyFactory.keyToString(ResidentBenevolence.getPersonKey())%>" value="<%= ResidentBenevolence.getPrepaidYTD() %>" maxlength="5" size="5"/></td>
						<td>$<input name="prepaidSavings_<%=KeyFactory.keyToString(ResidentBenevolence.getPersonKey())%>" value="<%= ResidentBenevolence.getPrevSavings() %>" maxlength="5" size="5"/></td>
					</tr>
			<% 
			} %>
			
		</tbody>
		</table>
		
		</br></br>
		
		
	    <!-- Left School -->
	  	  <table class="benevolence-table">
		  <thead>
			<tr class="tableheader">
				<th><div>Weeks</div></th>
				<th>Resident Name</th>
				<th><div>Admission Date</div></th>
				<th><div>Previous Paid YTD</div></th>
				<th><div>Previous Savings</div></th>
				<th><div>Exit Date</div></th>
			</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="13"><strong>Left</strong></td>
				</tr>
			<% 
			String exitDate="";
			
			for (int i=0;i<leftResidents.size();i++) { 
				ResidentBenevolence ResidentBenevolence = (ResidentBenevolence)leftResidents.get(i);
				exitDate = (String)ResidentBenevolence.getExitDate();
				
			%>
					<tr>
						<td><%= ResidentBenevolence.getWeeks() %></td>
						<td><%= ResidentBenevolence.getFirstName() %>&nbsp;<%= ResidentBenevolence.getLastName() %></td>
						<td><%= ResidentBenevolence.getEntryDate() %></td>
						<td>$<input name="prepaidYTD_<%=KeyFactory.keyToString(ResidentBenevolence.getPersonKey())%>" value="<%= ResidentBenevolence.getPrepaidYTD() %>" maxlength="5" size="5"/></td>
						<td>$<input name="prepaidSavings_<%=KeyFactory.keyToString(ResidentBenevolence.getPersonKey())%>" value="<%= ResidentBenevolence.getPrevSavings() %>" maxlength="5" size="5"/></td>
						<td>
						<% try { 
							java.util.Date nDate = new java.util.Date(exitDate);
	        				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
						%>
							Left:(<%=dateFormat.format(nDate) %>)
						<% } catch (Exception e) { %>Left: (unknown) <% } %>
						</td>
					</tr>
			<% 
			} %>
				
		</tbody>
		</table>

	
		</br>
				<input type="submit"  name="action" onClick="javascript:confirmDialog();" value="Update YTD Values"/>
		</form>  		
	  	</div>
  		
<script type="text/javascript">
 $(window).load(function () {
   init();
 });
</script>
</body>
</html>
