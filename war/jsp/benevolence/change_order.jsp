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
<%@ page import="org.faithfarm.dataobjects.PacketBreakdown" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Benevolence - Change Order Form</title>
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
          <%
          String farmBase = (String)session.getAttribute("farmBase");
          Date today = new Date();
          Date thisFriday = (Date)request.getAttribute("thisFriday");
          SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
          String weekEnding = toFormat.format(thisFriday);
          HashMap<Integer, Integer> breakdown = (HashMap<Integer, Integer>)request.getAttribute("breakdown");
          PacketBreakdown studentBreakdown = (PacketBreakdown)request.getAttribute("studentBreakdown");
          PacketBreakdown slsBreakdown = (PacketBreakdown)request.getAttribute("slsBreakdown");
          PacketBreakdown omegaBreakdown = (PacketBreakdown)request.getAttribute("omegaBreakdown");
          int totalTwenties = studentBreakdown.getTwenties() + slsBreakdown.getTwenties() + omegaBreakdown.getTwenties();
          int totalTens = studentBreakdown.getTens() + slsBreakdown.getTens() + omegaBreakdown.getTens();
          int totalFives = studentBreakdown.getFives() + slsBreakdown.getFives() + omegaBreakdown.getFives();
          int totalOnes = studentBreakdown.getOnes() + slsBreakdown.getOnes() + omegaBreakdown.getOnes();
          PacketBreakdown totalBreakdown = new PacketBreakdown(totalTwenties, totalTens, totalFives, totalOnes);
          int cashTotalStudents = (studentBreakdown.getTwenties() * 20) + (studentBreakdown.getTens() * 10) + (studentBreakdown.getFives() * 5) + studentBreakdown.getOnes();
          int cashTotalOmega = (omegaBreakdown.getTwenties() * 20) + (omegaBreakdown.getTens() * 10) + (omegaBreakdown.getFives() * 5) + omegaBreakdown.getOnes();
          int cashTotalSLS = (slsBreakdown.getTwenties() * 20) + (slsBreakdown.getTens() * 10) + (slsBreakdown.getFives() * 5) + slsBreakdown.getOnes();
          int cashGrandTotal = cashTotalStudents + cashTotalOmega + cashTotalSLS;
          %> 
  
 <!-- home page content -->
 <!-- list container -->
 <div align="center">
 
	   	  <table width="90%" cellspacing="0" cellpadding="0" border="0">
          <tr>
          	<td width="50%" align="left">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/logo2.png" width="250px"/></td>
          	<td width="50%" align="right" valign="top">
          		<div style="padding: 0 !important; margin: 0 !important;"><h2>Faith Farm Ministries<br/>Benevolence Change Order Form<br/>Location/NO: FLF/05</h2></div>
          	</td>
          </tr>
          </table>

          
		  <table class="benevolence-table changeorder" style="width: 90%">
		  	<tr>
		  		<td colspan="4" style="width: 50%;">Delivery Date: <%= Benevolence.getChangeOrderDeliveryDate() %></td><td colspan="4" style="width: 50%;">FUND TYPE: Student Benevolence</td>
		  	</tr>
		  	<tr>
		  		<td colspan="4" style="width: 50%; text-align:center;">Currency (Input Example $500-500)</td><td colspan="4" style="width: 50%; text-align:center;">Input Example ($500-50000)</td>
			</tr>
			<tr>
				<td>DESCRIPTION</td><td colspan="2">SYSTEM ENTRY</td><td>DOLLAR AMOUNT</td><td>DESCRIPTION</td><td colspan="2">SYSTEM ENTRY</td><td>DOLLAR AMOUNT</td>
			</tr>
			<tr>
				<td><strong>CURRENCY</strong></td><td colspan="2">1</td><td>&nbsp;</td><td><strong>COIN</strong></td><td colspan="2">2</td><td>&nbsp;</td>
			</tr>
			<tr>
				<td>ONES</td><td>1*</td><td><%= totalBreakdown.getOnes() %></td><td>$<%= totalBreakdown.getOnes() %>.00</td><td>PENNIES</td><td>1*</td><td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			<tr>
				<td>TWOS</td><td>2*</td><td>&nbsp;</td><td>&nbsp;</td><td>NICKLES</td><td>5*</td><td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			<tr>
				<td>FIVES</td><td>5*</td><td><%= totalBreakdown.getFives() * 5 %></td><td>$<%= totalBreakdown.getFives() * 5 %>.00</td><td>DIMES</td><td>10*</td><td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			<tr>
				<td>TENS</td><td>10*</td><td><%= totalBreakdown.getTens() * 10%></td><td>$<%= totalBreakdown.getTens() * 10%>.00</td><td>QUARTERS</td><td>25*</td><td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			<tr>
				<td>TWENTIES</td><td>20*</td><td><%= totalBreakdown.getTwenties() * 20 %></td><td>$<%= totalBreakdown.getTwenties() * 20 %>.00</td><td>HALF DOLLARS</td><td>50*</td><td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			<tr>
				<td>FIFTIES</td><td>50*</td><td>&nbsp;</td><td>&nbsp;</td><td>DOLLARS</td><td>100*</td><td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			<tr>
				<td>HUNDREDS</td><td>100*</td><td>&nbsp;</td><td>&nbsp;</td><td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="8" style="text-align: center; font-weight: bold; padding: 5px;">Denomination Breakdown by Class</td>
			</tr>
			<tr>
				<td>Denominations</td><td colspan="2">Twenties</td><td>Tens</td><td>Fives</td><td colspan="2">Ones</td><td>Totals</td>
			</tr>
			<tr>
				<td>Students</td><td colspan="2"><%= studentBreakdown.getTwenties() %></td><td><%= studentBreakdown.getTens() %></td><td><%= studentBreakdown.getFives() %></td><td colspan="2"><%= studentBreakdown.getOnes() %></td><td>$<%= cashTotalStudents %>.00</td>
			</tr>
			<tr>
				<td>Omega</td><td colspan="2"><%= omegaBreakdown.getTwenties() %></td><td><%= omegaBreakdown.getTens() %></td><td><%= omegaBreakdown.getFives() %></td><td colspan="2"><%= omegaBreakdown.getOnes() %></td><td>$<%= cashTotalOmega %>.00</td>
			</tr>
			<tr>
				<td>SLS</td><td colspan="2"><%= slsBreakdown.getTwenties() %></td><td><%= slsBreakdown.getTens() %></td><td><%= slsBreakdown.getFives() %></td><td colspan="2"><%= slsBreakdown.getOnes() %></td><td>$<%= cashTotalSLS %>.00</td>
			</tr>
			<tr>
				<td><strong>Total</strong></td><td colspan="2"><%= totalTwenties %></td><td><%= totalTens %></td><td><%= totalFives %></td><td colspan="2"><%= totalOnes %></td><td>$<%= cashGrandTotal %>.00</td>
			</tr>
			<tr>
				<td colspan="8" style="padding: 5px;">&nbsp;</td>
			</tr>
			<tr>
				<td>Total Currency</td><td colspan="2">$<%= cashGrandTotal %>.00</td><td colspan="2">&nbsp;</td><td colspan="2">Total Coins</td><td>$0.00</td>
			</tr>
			<tr>
				<td>Total Cash Order</td><td colspan="2">$<%= cashGrandTotal %>.00</td><td colspan="2">Approved By: &nbsp;&nbsp;&nbsp;&nbsp;</td><td colspan="3">Confirmation Number: &nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3">Requested By: &nbsp;&nbsp;&nbsp;&nbsp;</td><td colspan="2">Date:</td><td colspan="3">Ordered By: &nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3">Date of Request: <%= toFormat.format(today) %></td><td colspan="2">&nbsp;</td><td colspan="3">Date Ordered: &nbsp;&nbsp;&nbsp;&nbsp;</td>
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