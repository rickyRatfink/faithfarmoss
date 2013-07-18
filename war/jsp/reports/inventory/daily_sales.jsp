<%@ page import="java.util.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.faithfarm.dataobjects.DailySalesReport" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
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
<title>Faith Farm Ministries - Daily Sales Report</title>
<link href="<%=request.getContextPath()%>/styles/report.css" rel="stylesheet" type="text/css" />
  
  <script language="javascript" src='script/jquery-1.6.min.js'></script>
  <script language="javascript" src='script/ajax.util.js'></script>

  	<script language="javascript">
	function printPage()
	{
	window.print();
	}
	
</script>

<style>

td { 
-webkit-print-color-adjust: exact;
}
.report-heading {
	 background: #bfbdbd;
	 border-color: #798c95;
     border-style: solid;
     border-width: 1px;
     font-size: 13px;
	 font-family: courier;
	 font-weight: bold;
	 color: #000000;	 
	 height: 15px;
	 padding: 0px 0px 0px 3px;
	 text-align:left;	
}
.report-field {
	 font-size: 13px;
	 font-family: courier;
	 font-weight: normal;
	 color: #000000;	 
	 padding: 0px 0px 0px 3px;
	 text-align:left;	
}
.report-money {
	 font-size: 13px;
	 font-family: courier;
	 font-weight: normal;
	 color: #000000;	 
	 padding: 0px 0px 0px 3px;
	 text-align:right;	
}

</style>

</head>
<body>
</style>
  <!-- content -->  
  
 <!-- home page content -->
 <!-- list container -->
 <div align="center">
 
	   	  <table width="90%" cellspacing="0" cellpadding="0" border="0">
          <tr>
          	<td width="50%" align="left">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/logo.png"  /></td>
          	<td width="50%" align="right" valign="top">
          		<div style="font-size:14px"><b>Daily Sales Report</b></br>
          		Sales Date:&nbsp;<%=request.getAttribute("salesDate")%></b></div>
          		<div style="font-size:10px">Run on:&nbsp;&nbsp;<i><%=new java.util.Date()%></i></div>
          	</td>
          </tr>
          </table>
           <table width="90%" cellspacing="0" cellpadding="0" border="0">
            		    <tr>
				                <td width="90" class="report-heading">Vendor</th>
				                <td width="140" class="report-heading">Item#</th>
				                <td width="150" class="report-heading">Item Name</th>
				                <td width="70" class="report-heading">Floor</th>
				                <td width="70" class="report-heading">Warehouse</th>
				                <td width="70" class="report-heading">Total</th>
				                <td width="70" class="report-heading">Par</th>
				                <td width="80" class="report-heading">1/W</th>
				                <td width="80" class="report-heading">1/F</th>
				                <td width="80" class="report-heading">1/RO</th>
				                <td width="80" class="report-heading">Qty Sold</th>
				                <td width="100" class="report-heading">Price</th>
				                <td width="100" class="report-heading">Sales</th>
				              </tr>	
           				
						<%  			
							int index=0; double price=0.00;
							ArrayList results = (ArrayList)request.getAttribute("results");
							for (int i=0;i<results.size();i++) {
								DailySalesReport report = (DailySalesReport)results.get(i);
								
							if ((i+1)%56==0) { %>
						      <tr><td colspan="9" height="50"></td></tr>		
				             <tr>
				                <td width="90" class="report-heading">Vendor</th>
				                <td width="40" class="report-heading">Item#</th>
				                <td width="150" class="report-heading">Item Name</th>
				                <td width="70" class="report-heading">Floor</th>
				                <td width="70" class="report-heading">Warehouse</th>
				                <td width="70" class="report-heading">Total</th>
				                <td width="70" class="report-heading">Par</th>
				                <td width="80" class="report-heading">1/W</th>
				                <td width="80" class="report-heading">1/F</th>
				                <td width="80" class="report-heading">1/RO</th>
				                <td width="80" class="report-heading">Qty Sold</th>
				                <td width="100" class="report-heading">Price</th>
				                <td width="100" class="report-heading">Sales</th>
				             </tr>	
							
							<% }								
							String bgcolor="bab8b8";
							if (i%2==0) bgcolor="FFFFFF";
							%>
							  <tr>
							   	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getVendor()%></td>
							  	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getItemNumber()%></td>
							  	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getItemName()%></td>
							  	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getFloorQuantity()%></td>
							  	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getWarehouseQuantity()%></td>
							  	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getTotalQuantity()%></td>
							  	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getPar()%></td>
							  	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getQty1W()%></td>
							  	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getQty1F()%></td>
							  	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getQty1RO()%></td>
							  	<td class="report-field" bgcolor="#<%=bgcolor%>"><%=report.getQuantitySold()%>
							  	<%if (report.getLocation()!=null&&!"null".equals(report.getLocation())) { %>&nbsp;(<%=report.getLocation()%>)<% }%></td>
							  	<td class="report-money" bgcolor="#<%=bgcolor%>"><fmt:formatNumber value="<%=report.getPrice()%>" type="currency" /></td>
							  	<td class="report-money" bgcolor="#<%=bgcolor%>"><fmt:formatNumber value="<%=report.getSalesPrice()%>" type="currency" /></td>
							  </tr>
						<% 
						price=price+Double.valueOf(report.getSalesPrice());
						} 
						%>
						<tr> <td height="20" colspan="13"></td></tr>
						<tr>
							 	<td colspan="12" align="right"><b>Total</b></td>
							 	<td align="right"><b><fmt:formatNumber value="<%=price%>" type="currency" /></b></td>
						</tr>
						<tr>
							 	<td colspan="12" align="right"><b>Tax</b></td>
							  	<td align="right"><b><fmt:formatNumber value="<%=price*.07%>" type="currency" /></b></td>
						</tr>
						<tr>
							 	<td colspan="12" align="right"><b>Total Sales</b></td>
							  	<td align="right"><b><fmt:formatNumber value="<%=price+(price*.07)%>" type="currency" /></b></td>
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