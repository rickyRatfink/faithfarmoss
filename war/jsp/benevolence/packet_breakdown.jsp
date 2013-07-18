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
<title>Faith Farm Ministries - Benevolence - Packet Breakdown Form</title>
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
 
          <%
          String farmBase = (String)session.getAttribute("farmBase");
          Date thisFriday = (Date)request.getAttribute("thisFriday");
          Date theFirst = (Date)request.getAttribute("theFirst");
          SimpleDateFormat toFormat = new SimpleDateFormat("MM/dd/yy");
          String weekEnding = toFormat.format(thisFriday);
          HashMap<Integer, Integer> breakdown = (HashMap<Integer, Integer>)request.getAttribute("breakdown");
          %>
	   	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tr>
          	<td width="50%" align="left">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/logo2.png" style="width:250px;"  /></td>
          	<td width="50%" align="right" valign="top">
          	<h2>
          		Packet Breakdown Form<br/>
          		Location: <%= farmBase %><br/>
          		Week Ending: <%= weekEnding %>
          	</h2>
          	</td>
          </tr>
          </table>
          
		  <table class="benevolence-table" style="width: 90%">
			<tr class="tableheader">
				<td>Packet Amount</td>
				<td>Number of Packets</td>
				<td>Twenties</td>
				<td>Tens</td>
				<td>Fives</td>
				<td>Ones</td>
			</tr>
			<%
			TreeSet<Integer> keys = new TreeSet<Integer>(breakdown.keySet());
			for(Integer key : keys) {
				Integer count = breakdown.get(key);
				PacketBreakdown currencyCount = new PacketBreakdown(key);
				%>
				<tr>
					<td>$<%= key %>.00</td>
					<td><%= count %></td>
					<td><%= currencyCount.getTwenties() %></td>
					<td><%= currencyCount.getTens() %></td>
					<td><%= currencyCount.getFives() %></td>
					<td><%= currencyCount.getOnes() %></td>
				</tr>
				<%
			}
			%>
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