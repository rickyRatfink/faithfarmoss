<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Operational Support System</title>

  <link href="<%=request.getContextPath()%>/styles/report.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/tcal.css" />
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/tcal.js"></script> 
  <script language="javascript" src='<%=request.getContextPath()%>/script/jquery-1.6.min.js'></script>
  <script language="javascript" src='<%=request.getContextPath()%>/script/ajax.util.js'></script>
  
<script language="javascript">
	function printPage()
	{
	window.print();
	}
	
</script>

</head>


<body background="#FFFFFF" onLoad="javascript:printPage();">


<%
		Entity e = (Entity)request.getAttribute("customer");	
		String firstName="";
		String lastName="";
		String addressLine1="";
		String city="";
		String zipcode="";
		String cell="";
		String home="";
		String work="";
		String cell1="",cell2="",cell3="";
		String work1="",work2="",work3="";
		String home1="",home2="",home3="";
																	
		if (e!=null) {
			firstName=(String)e.getProperty("firstName");
			lastName=(String)e.getProperty("lastName");
			addressLine1=(String)e.getProperty("addressLine1");
			city=(String)e.getProperty("city");
			zipcode=(String)e.getProperty("zipcode");
			cell=(String)e.getProperty("cell");
			home=(String)e.getProperty("home");
			work=(String)e.getProperty("work");
			
			if (home!=null&&home.length()==13) { 									                  
				home1=home.substring(1,4);
				home2=home.substring(5,8);
				home3=home.substring(9,13);
			}
			if (cell!=null&&cell.length()==13) {									                  
				cell1=cell.substring(1,4);
				cell2=cell.substring(5,8);
				cell3=cell.substring(9,13);
			}
			if (work!=null&&work.length()==13) { 									                  
				work1=work.substring(1,4);
				work2=work.substring(5,8);
				work3=work.substring(9,13);
			}
		}
		String deliveryDate=request.getParameter("date");
		if (deliveryDate==null) deliveryDate="";
		
		String item1=request.getParameter("item1");
		String item2=request.getParameter("item2");
		String item3=request.getParameter("item3");
		String item4=request.getParameter("item4");
		String item5=request.getParameter("item5");
		if (item1==null) item1="";
		if (item2==null) item2="";
		if (item3==null) item3="";
		if (item4==null) item4="";
		if (item5==null) item5="";
		
		String itemName1=(String)request.getAttribute("itemName1");
		String qty1=request.getParameter("qty1");
		String price1=(String)request.getAttribute("price1");
		if (itemName1==null) itemName1="";
		if (qty1==null) qty1="0";
		if (price1==null) price1="$ 0.00";
		
		String itemName2=(String)request.getAttribute("itemName2");
		String qty2=request.getParameter("qty2");
		String price2=(String)request.getAttribute("price2");
		if (itemName2==null) itemName2="";
		if (qty2==null) qty2="0";
		if (price2==null) price2="$ 0.00";
		
		String itemName3=(String)request.getAttribute("itemName3");
		String qty3=request.getParameter("qty3");
		String price3=(String)request.getAttribute("price3");
		if (itemName3==null) itemName3="";
		if (qty3==null) qty3="0";
		if (price3==null) price3="$ 0.00";
		
		String itemName4=(String)request.getAttribute("itemName4");
		String qty4=request.getParameter("qty4");
		String price4=(String)request.getAttribute("price4");
		if (itemName4==null) itemName4="";
		if (qty4==null) qty4="0";
		if (price4==null) price4="$ 0.00";
		
		String itemName5=(String)request.getAttribute("itemName5");
		String qty5=request.getParameter("qty5");
		String price5=(String)request.getAttribute("price5");
		if (itemName5==null) itemName5="";
		if (qty5==null) qty5="0";
		if (price5==null) price5="$ 0.00";
		
%>

		<!----Main Content--->
		
		<td>
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td width="50"></td>
				<td >
						<div class="content">
						<div class="text1">
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								
								<td width="50%" align="left"><img src="<%=request.getContextPath()%>/images/order-logo.jpg"  /></td>
								<td width="50%" align="right"><img src="<%=request.getContextPath()%>/images/barcode.jpg" /></td>
							</tr>
							</table>
							
						</div>
						<br>
						<br>
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/controller?entity=NewFurnForm">
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								
								<td >
								 <table width="350" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							               
							               <tr>
							               	 <td><%=firstName%>&nbsp;<%=lastName%></td>
							               </tr> 
							               <tr>
							               	 <td><%=addressLine1%></td>
							               </tr> 
							               <tr>
							               	 <td><%=city%>,&nbsp;Florida&nbsp;&nbsp;<%=zipcode%>
							               	 	 <br><br>
							               	 </td>
							               </tr> 
										   <tr>
											 <td>Home Phone: <%=home%></td>
											</tr>	
											<tr>
											 <td>Cell Phone: <%=cell%></td>
											</tr>	
											<tr>
											 <td>Work Phone: <%=work%></td>
											</tr>					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
						
						
							<tr>
								<td>New Furniture Department: Fort Lauderdale, FL</td>								
							</tr>
							
							<tr>
								<td class="field" >Delivery Date: <%=deliveryDate%></td>								
							</tr>
							
							
							<tr>
								<td>
									<br><br>
									
							           <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                <tr>
							                 <td colspan="4" class="results-section">Customer Order</td>
							                </tr>
							                <tr>
							                 <td width="30" class="results-heading">ITEM#</td>
							                 <td width="20" class="results-heading">QUANTITY</td>
							                 <td width="200" class="results-heading">DESCRIPTION</td>
							                 <!--<td width="40" class="results-heading">DEPT CODE</td>-->
							                 <td width="50" class="results-heading">PRICE</td>
							               </tr>
							               	
							               
							               <tr>
							               	 <td width="80" class="order-bg">
							               	 	<%=item1%>
							               	 </td>
							               	 <td class="order-bg" ><%=qty1%></td>
							               	 <td class="order-bg"><%=itemName1%></td>
							               	 <td class="order-bg"><%=price1%></td>
							               </tr> 
							               <% if (item1!=null&&item1.length()>0) { %>
							                 <tr>
							               	 <td width="80" class="order-bg">
							               	 	<%=item2%>
							               	 </td>
							               	 <td class="order-bg" ><%=qty2%></td>
							               	 <td class="order-bg"><%=itemName2%></td>
							               	 <td class="order-bg"><%=price2%></td>
							               </tr> 
							               <% } %>
							               <% if (item2!=null&&item2.length()>0) { %>
							                 <tr>
							               	 <td width="80" class="order-bg">
							               	 	<%=item3%>
							               	 </td>
							               	 <td class="order-bg" ><%=qty3%></td>
							               	 <td class="order-bg"><%=itemName3%></td>
							               	 <td class="order-bg"><%=price3%></td>
							               </tr> 
							               <% } %>
							               <% if (item3!=null&&item3.length()>0) { %>
							                <tr>
							               	 <td width="80" class="order-bg">
							               	 	<%=item4%>
							               	 </td>
							               	 <td class="order-bg" ><%=qty4%></td>
							               	 <td class="order-bg"><%=itemName4%></td>
							               	 <td class="order-bg"><%=price4%></td>
							               </tr> 
							               <% } %>
							               <% if (item4!=null&&item4.length()>0) { %>
							                <tr>
							               	 <td width="80" class="order-bg">
							               	 	<%=item5%>
							               	 </td>
							               	 <td class="order-bg" ><%=qty5%></td>
							               	 <td class="order-bg"><%=itemName5%></td>
							               	 <td class="order-bg"><%=price5%></td>
							               </tr> 
							               <% } %>
										   
							               					               	
							             
							             <tr><td colspan="4" height="25"></td></tr>
							             
							              <tr>
							               	 <td></td>
							               	 <td></td>
							               	 <td align="right" class="total">Sub-Total</td>
							               	 <td align="right" class="total">$  0.00</td>
							               </tr> 
							               <tr>
							               	 <td></td>
							               	 <td></td>
							               	 <td align="right" class="total">Tax</td>
							               	 <td align="right" class="total">$  0.00</td>
							               </tr> 
							               <tr>
							               	 <td></td>
							               	 <td></td>
							               	 <td align="right" class="total"><b>Total</b></td>
							               	 <td align="right" class="total"><b>$  0.00</b></td>
							               </tr> 
							           </table>
			  							
			  					</td>
			  				</tr>		
							
							
							</table>
									
						</form>			
						
						<!----End Form----------->
						</div>
									
				</td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>

</body>

