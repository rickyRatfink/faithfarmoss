<%@ page import="java.util.*" %>
<%@ page import="java.util.Map" %><%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - New Furniture Inventory Report</title>
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
          		<div style="font-size:14px"><b>New Furniture Inventory Report</b></div>
          		<div style="font-size:10px">Run on:&nbsp;&nbsp;<i><%=new java.util.Date()%></i></div>
          	</td>
          </tr>
          </table>
           <table width="90%" cellspacing="0" cellpadding="0" border="0">
            		    
           				
				          <tr>
				                <td width="100" class="report-heading">Vendor Name</th>
				                <td width="40" class="report-heading">Item#</th>
				                <td width="200" class="report-heading">Item Name</th>
				                <td width="80" class="report-heading">Floor Qty</th>
				                <td width="100" class="report-heading">Warehouse Qty</th>
				              </tr>
			<%  			
							int index=0;
							ArrayList entities = (ArrayList)request.getAttribute("results");
							Map<String, Object> properties = null;
							for (int i=0;i<entities.size();i++) {
								Entity result = (Entity)entities.get(i);
								properties = result.getProperties();
							  
							  //for (String key : properties.keySet()) { %>
							  <tr>
							   	<td class="report-field"><%=properties.get("vendor")%></td>
							  	<td class="report-field"><%=properties.get("itemNumber")%></td>
							  	<td class="report-field"><%=properties.get("itemName")%></td>
							  	<td class="report-field"><input type="text" size="2"/></td>
							  	<td class="report-field"><input type="text" size="2"/></td>
							  	
							  </tr>
						<% } %>
						
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