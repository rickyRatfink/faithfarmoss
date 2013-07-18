<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="org.faithfarm.dataobjects.Benevolence" %>
<%@ page import="org.faithfarm.dataobjects.Deduction" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Benevolence - Confirmation Report</title>
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
           <%
          String farmBase = (String)session.getAttribute("farmBase");
          Date thisFriday = (Date)request.getAttribute("thisFriday");
          SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
          String weekEnding = toFormat.format(thisFriday);
          Iterable<Entity> residents = (Iterable<Entity>)request.getAttribute("residents");
          Iterable<Entity> omega = (Iterable<Entity>)request.getAttribute("omega");
          Iterable<Entity> sls = (Iterable<Entity>)request.getAttribute("sls");
          %>
 <div align="center">
 
	   	  <table width="90%" cellspacing="0" cellpadding="0" border="0">
          <tr>
          	<td width="50%" align="left">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/logo2.png" style="width:250px;"  /></td>
          	<td width="50%" align="right" valign="top">
          	<h2>
          		Confirmation Report<br/>
          		Location: <%= farmBase %><br/>
          		Week Ending: <%= weekEnding %>
          	</h2>
          	</td>
          </tr>
          </table>


          
		  <table class="benevolence-table" style="width: 90%">
		  	<tr>
		  		<td colspan="5" style="text-align:center; font-size: 16pt; font-weight:bold;">Residents</td>
		  	</tr>
		  	<tr style="font-weight:bold;">
		  		<td>Name</td><td>Social</td><td>Amount Due</td><td>Deductions</td><td>Amount Paid</td>
		  	</tr>
		  	<%
		  	int residentTotal = 0;
		  	for(Entity resident : residents) {
		  		int amountDue = Integer.parseInt(Benevolence.getAmountDueFromEntity(resident));
		  		int deductions = Deduction.getTotalDeductions(resident);
		  		residentTotal += (amountDue - deductions);
		  		
		  	   String ssn="";
               
               try {
            	   //this is why we don't name variables the same name as a class.... PS
               	ssn = "***-**-" + org.faithfarm.dataobjects.Person.decryptSsn(resident.getProperty("ssn").toString()).substring(5,9);
               //ssn= "***-**-"+ssn.substring(5,9);
	 		   } catch (Exception e) { ssn="N/A"; } 
           
		  		%>
		  		<tr>
		  			<td><%= Benevolence.getNameFromEntity(resident) %></td>
		  			<td><%= ssn %></td>
		  			<td>$<%= amountDue %>.00</td>
		  			<td>$<%= deductions %>.00</td>
		  			<td>$<%= amountDue - deductions %>.00</td>
		  		</tr>
		  		<%
		  	}
		  	%>
		  	<tr>
		  		<td colspan="3">&nbsp;</td>
		  		<td><strong>Total</strong></td>
		  		<td>$<%= residentTotal %>.00</td>
		  	</tr>
		  	<tr>
		  		<td colspan="5" style="text-align:center; font-size: 16pt; font-weight:bold;">Omega</td>
		  	</tr>
		  	<%
		  	int omegaTotal = 0;
		  	for(Entity person : omega) {
		  		int amountDue = 25;
		  		int deductions = Deduction.getTotalDeductions(person);
		  		omegaTotal += (amountDue - deductions);
		  		
		  	   String ssn="";
               
               try {
            	   //this is why we don't name variables the same name as a class.... PS
               	ssn = "***-**-" + org.faithfarm.dataobjects.Person.decryptSsn(person.getProperty("ssn").toString()).substring(5,9);
               //ssn= "***-**-"+ssn.substring(5,9);
	 		   } catch (Exception e) { ssn="N/A"; } 
           
		  		%>
		  		<tr>
		  			<td><%= Benevolence.getNameFromEntity(person) %></td>
		  			<td><%= ssn%></td>
		  			<td>$<%= amountDue %>.00</td>
		  			<td>$<%= deductions %>.00</td>
		  			<td>$<%= amountDue - deductions %></td>
		  		</tr>
		  			
		  		<%
		  	}
		  	%>
		  	<tr>
		  		<td colspan="3">&nbsp;</td>
		  		<td><strong>Total</strong></td>
		  		<td>$<%= omegaTotal %>.00</td>
		  	</tr>
		  	<tr>
		  		<td colspan="5" style="text-align:center; font-size: 16pt; font-weight: bold;">SLS</td>
		  	</tr>
		  	<%
		  	int slsTotal = 0;
		  	for(Entity person : sls) {
		  		int amountDue = Benevolence.getAmountDueSLSFromEntity(person);
		  		int deductions = Deduction.getTotalDeductions(person);
		  		slsTotal += (amountDue - deductions);
		  		
		  	    String ssn="";
	               
	               try {
	            	   //this is why we don't name variables the same name as a class.... PS
	               	ssn = "***-**-" + org.faithfarm.dataobjects.Person.decryptSsn(person.getProperty("ssn").toString()).substring(5,9);
	               //ssn= "***-**-"+ssn.substring(5,9);
		 		   } catch (Exception e) { ssn="N/A"; }
	            
	           
		  		%>
		  		<tr>
		  			<td><%= Benevolence.getNameFromEntity(person) %></td>
		  			<td><%=ssn%></td>
		  			<td>$<%= amountDue %>.00</td>
		  			<td>$<%= deductions %>.00</td>
		  			<td>$<%= amountDue - deductions %>.00</td>
		  		</tr>
		  		<%
		  	}
		  	%>
		  	<tr>
		  		<td colspan="3">&nbsp;</td>
		  		<td><strong>Total</strong></td>
		  		<td>$<%= slsTotal %></td>
		  	</tr>
		  	<tr>
		  		<td colspan="3">&nbsp;</td>
		  		<td style="font-size:20pt;"><strong>Grand Total</strong></td>
		  		<td>$<%= residentTotal + slsTotal + omegaTotal %>.00</td>
		  	</tr>
	
		  </table>
			
	  	</br></br>	  		
	  		<a href="javascript:print();" style="text-decoration:none;"><img src="<%= request.getContextPath() %>/images/Printer-icon.png" border=0 height="15" width="15"></a>
  		</br></br>
	  	</div>
  		
<script type="text/javascript">
 $(window).load(function () {
   init();
 });
</script>
</body>
</html>