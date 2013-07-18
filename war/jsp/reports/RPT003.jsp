<%@ page import="java.util.*"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Current Resident Report</title>
<link href="<%=request.getContextPath()%>/styles/report.css" rel="stylesheet" type="text/css" />

<script language="javascript" src='script/jquery-1.6.min.js'></script>
<script language="javascript" src='script/ajax.util.js'></script>

<script language="javascript">
	function printPage() {
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

		<table width="60%" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td width="50%" align="left">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/report-logo.jpg" /></td>
				<td width="50%" align="right" valign="top">
					<div style="font-size: 14px">
						<b>Resident Report</b>
					</div>
					<div style="font-size: 10px">
						Run on:&nbsp;&nbsp;<i><%=new java.util.Date()%></i>
					</div>
				</td>
			</tr>
		</table>
		<table width="60%" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td width="80" class="report-heading">Last Name</td>
				<td width="80" class="report-heading">First Name</td>
				<td width="60" class="report-heading">Entry Date</td>
				<td width="100" class="report-heading">Supervisor Name</td>
				<td width="100" class="report-heading">Bed</td>
			</tr>

			<%
				int index=0;
								Iterable<Entity> entities = (Iterable<Entity>)request.getAttribute("results");
								Map<String, Object> properties = null;
								for (Entity result : entities) {
									properties = result.getProperties();
								 
								String bedNumber=(String)properties.get("bedNumber");
								if (bedNumber==null)
									bedNumber="Not Assigned";
			%>
			<tr>
				<td class="reportField"><%=((String)properties.get("lastName")).toUpperCase()%></td>
				<td class="reportField"><%=((String)properties.get("firstName")).toUpperCase()%></td>
				<td class="reportField">
					<%
						String sDate = "";
						            try {
							       		sDate = properties.get("entryDate").toString();
								java.util.Date nDate = new java.util.Date(sDate);
							        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
							        	sDate = dateFormat.format(nDate);
							        } catch (Exception e) { sDate=""; }
					%> <%=sDate%></td>
				<td class="reportField"><%=properties.get("supervisor")%></td>
				<td class="reportField"><%=bedNumber%></td>
			</tr>

			<%
				}
			%>

		</table>

		</br>
		</br> <a href="javascript:print();" style="text-decoration: none;"><img src="<%=request.getContextPath()%>/images/Printer-icon.png" border=0 height="15" width="15"></a> </br>
		</br>
	</div>

	<script type="text/javascript">
		$(window).load(function() {
			init();
		});
	</script>
</body>
</html>