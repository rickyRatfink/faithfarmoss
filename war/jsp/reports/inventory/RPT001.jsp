<%@ page import="java.util.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
<title>Faith Farm Ministries - New Furniture Inventory Report</title>
  
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
	 background: #a4b0b5;
	 border-color: #798c95;
     border-style: solid;
     border-width: 1px;
     font-size: 17px;
	 font-style: courier;
	 font-weight: bold;
	 color: #000000;	 
	 height: 13px;
	 padding: 0px 0px 0px 3px;
	 text-align:left;	
}
.report-field {
	 font-size: 17px;
	 font-style: courier;
	 font-weight: normal;
	 color: #000000;	 
	 padding: 0px 0px 0px 3px;
	 text-align:left;	
}
.report-field-center {
	 font-size: 17px;
	 font-style: courier;
	 font-weight: normal;
	 color: #000000;	 
	 padding: 0px 0px 0px 3px;
	 text-align:center;	
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
          		<div style="font-size:21px"><b>New Furniture Inventory Report</b></div>
          		<div style="font-size:14px">Run on:&nbsp;&nbsp;<i><%=new java.util.Date()%></i></div>
          	</td>
          </tr>
          </table>
           <table width="90%" cellspacing="0" cellpadding="0" border="0">
            		    
           				
				          <tr>
				                <td width="70" class="report-heading">Vendor</th>
				                <td width="40" class="report-heading">Item#</th>
				                <td width="200" class="report-heading">Item Name</th>
				                <td width="50" class="report-heading">Cost</th>
				                <td width="80" class="report-heading">Floor Qty</th>
				                <td width="80" class="report-heading">Floor Cost</th>
				                <td width="100" class="report-heading">Warehouse Qty</th>
				                <td width="130" class="report-heading">Warehouse Cost</th>
				                <td width="80" class="report-heading">Total Qty</th>
				                <td width="80" class="report-heading">Total Cost</th>
				                <td width="130" class="report-heading">Date Added</th>
				          </tr>
			<%  			
							int index=0,row=0;
							Iterable<Entity> entities = (Iterable<Entity>)request.getAttribute("results");
							Map<String, Object> properties = null;
							Double total1=0.00, total2=0.00,total3=0.00;
							for (Entity result : entities) {
								row++;
								properties = result.getProperties();
							  	int qty1=0,qty2=0;
							  	
							  	if (properties.get("qtyFloor")!=null) qty1=Integer.parseInt(properties.get("qtyFloor")+"");
							  		else qty1=0;
							  	
							  	if (properties.get("qtyWarehouse")!=null) qty2=Integer.parseInt(properties.get("qtyWarehouse")+"");
							  		else qty2=0;
							  	
							  	int total = qty1+ qty2; 


							  	Double totalCost=null, cost=null;
							  	
							  	if (properties.get("cost")!=null) { 
							  	
							  	   totalCost = total *  Double.valueOf((String)properties.get("cost")).doubleValue(); 
							  	   cost = Double.valueOf((String)properties.get("cost")).doubleValue(); 
						  		}
							  	else { 
							  	
							  	   totalCost = new Double("0.00"); 
							  	   cost = new Double("0.00");
						  	 	}  
								
							  	total1 = total1 + (qty1*cost);
							  	total2 = total2 + (qty2*cost);
							  	total3 = total3 + totalCost;
							  	
							  	String bgcolor="bab8b8";
								if (row%2==0) bgcolor="FFFFFF";
								
								Date sDate=new Date("07/06/2012");
								if (properties.get("creationDate")!=null)
									sDate=new Date(properties.get("creationDate").toString());
									
								SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
		        				%>
							  	<c-rt:set var="now" value="<%=new java.util.Date()%>" />
							  	<tr>
							  	<td class="report-field" bgcolor="<%=bgcolor%>"><%=properties.get("vendor")%></td>
							  	<td class="report-field" bgcolor="<%=bgcolor%>"><%=properties.get("itemNumber")%></td>
							  	<td class="report-field" bgcolor="<%=bgcolor%>"><%=properties.get("itemName")%></td>
							  	<td class="report-field" bgcolor="<%=bgcolor%>">$<%=properties.get("cost")%></td>
							  	<td class="report-field-center" bgcolor="<%=bgcolor%>"><%=properties.get("qtyFloor")%></td>
							  	<td class="report-field" bgcolor="<%=bgcolor%>"><fmt:formatNumber value="<%=qty1*cost%>" type="currency" /></td>
							  	<td class="report-field-center" bgcolor="<%=bgcolor%>"><%=properties.get("qtyWarehouse")%></td>
							  	<td class="report-field" bgcolor="<%=bgcolor%>"><fmt:formatNumber value="<%=qty2*cost%>" type="currency" /></td>
							  	<td class="report-field" bgcolor="<%=bgcolor%>"><%=total%></td>
							  	<td class="report-field" bgcolor="<%=bgcolor%>"><fmt:formatNumber value="<%=totalCost%>" type="currency" /> </td>
						 		<td class="report-field" bgcolor="<%=bgcolor%>"><%=dateFormat.format(sDate)%></td>
						 	
							  </tr>
						<% } 
							  	%>
							<tr><td colspan="10" height="15"></td><tr>
				            <tr>
				              <td colspan="8" align="right"><b>Total Floor Cost</b></td>
				              <td></td>
				              <td align="left"><fmt:formatNumber value="<%=total1%>" type="currency" /></td>
				            </tr>
				             <tr>
				              <td colspan="8" align="right"><b>Total Warehouse Cost</b></td>
				               <td></td>
				               <td align="left"><fmt:formatNumber value="<%=total2%>" type="currency" /></td>
				            </tr>
				             <tr>
				              <td colspan="8" align="right"><b>Total Cost</b></td>
				               <td></td>
				               <td align="left"><fmt:formatNumber value="<%=total3%>" type="currency" /></td>
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