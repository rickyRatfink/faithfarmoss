<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<script language="javascript">
	function printPage()
	{
	window.print();
	}
	
</script>

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
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/nf-order.jpg">
						<br>
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/sales">
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
								 <table width="350" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td width="170" class="field">First Name</td>
							                 <td width="150" class="field">Last Name</td>
							                 <td width="30"></td>
							               </tr>
							               	
							               <tr>
							               	 <td><input name="firstName" value="<%=firstName%>" maxlength="20" size="20" class="entry"></td>
							               	 <td><input name="lastName" value="<%=lastName%>" maxlength="30" size="30" class="entry"></td>
							               	 <td>
							               	 	<input type="image" name="action" value="SearchCustomer" src="<%=request.getContextPath()%>/images/search.jpg" border="0"></td>
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
							<tr>
								<td class="field" >Address</td>
							</tr>
							<tr>
								<td >
									<input name="address" value="<%=addressLine1%>" maxlength="80" size="80" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field">City</td>
							</tr>
							<tr>
								<td>
									<input name="city" value="<%=city%>" maxlength="30" size="30" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field">Zipcode</td>
							</tr>
							<tr>
								<td>
									<input name="zipcode" value="<%=zipcode%>" maxlength="10" size="10" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field">Home Phone</td>
							</tr>
							<tr>
										<td>
											(<input name="home_areacode" value="<%=home1%>" maxlength="3" size="3" class="entry">)
											<input name="home_first3" value="<%=home2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="home_last4" value="<%=home3%>" maxlength="4" size="4" class="entry">
										</td>
							</tr>
							<tr>
								<td class="field">Cell Phone</td>
							</tr>
							<tr>
										<td>
											(<input name="cell_areacode" value="<%=cell1%>" maxlength="3" size="3" class="entry">)
											<input name="cell_first3" value="<%=cell2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="cell_last4" value="<%=cell3%>" maxlength="4" size="4" class="entry">
										</td>
							</tr>
							<tr>
								<td class="field">Work Phone</td>
							</tr>
							<tr>
										<td>
											(<input name="work_areacode" value="<%=work1%>" maxlength="3" size="3" class="entry">)
											<input name="work_first3" value="<%=work2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="work_last4" value="<%=work3%>" maxlength="4" size="4" class="entry">
										</td>
						    </tr>
							<tr>
								<td class="field" >New Furniture Department</td>								
							</tr>
							<tr>
								<td>
									<select class="entry">
										<option value="">Fort Lauderdale, FL</option>
										<option value="">Boynton Beach, FL</option>
										<option value="">Okeechobee, FL</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="field" >Delivery Date</td>								
							</tr>
							
							<tr>
								<td>
									 <div><input name="date" class="tcal" value="<%=deliveryDate%>" /></div>
								</td>
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
							               	 	<input name="item1" size="10" maxlength="10" value="<%=item1%>" class="entry">
							               	 	<input type="image" name="action" value="Find Item" src="<%=request.getContextPath()%>/images/search.jpg"></td>
							               	 </td>
							               	 <td class="order-bg" ><input name="qty1" size="2" maxlength="2" value="<%=qty1%>" class="entry"></td>
							               	 <td class="order-bg"><%=itemName1%></td>
							               	 <td class="order-bg"><%=price1%></td>
							               </tr> 
							               <% if (item1!=null&&item1.length()>0) { %>
							               <tr>
							               	 <td width="80" class="order-bg">
							               	 	<input name="item2" size="10" maxlength="10" value="<%=item2%>" class="entry">
							               	 	<input type="image" name="action" value="Find Item" src="<%=request.getContextPath()%>/images/search.jpg"></td>
							               	 </td>
							               	 <td class="order-bg" ><input name="qty2" size="2" maxlength="2" value="<%=qty2%>" class="entry"></td>
							               	 <td class="order-bg"><%=itemName2%></td>
							               	 <td class="order-bg"><%=price2%></td>
							               </tr> 
							               <% } %>
							               <% if (item2!=null&&item2.length()>0) { %>
							               <tr>
							               	 <td width="80" class="order-bg">
							               	 	<input name="item3" size="10" maxlength="10" value="<%=item3%>" class="entry">
							               	 	<input type="image" name="action" value="Find Item" src="<%=request.getContextPath()%>/images/search.jpg"></td>
							               	 </td>
							               	 <td class="order-bg" ><input name="qty3" size="2" maxlength="2" value="<%=qty3%>" class="entry"></td>
							               	 <td class="order-bg"><%=itemName3%></td>
							               	 <td class="order-bg"><%=price3%></td>
							               </tr> 
							               <% } %>
							               <% if (item3!=null&&item3.length()>0) { %>
							               <tr>
							               	 <td width="80" class="order-bg">
							               	 	<input name="item4" size="10" maxlength="10" value="<%=item4%>" class="entry">
							               	 	<input type="image" name="action" value="Find Item" src="<%=request.getContextPath()%>/images/search.jpg"></td>
							               	 </td>
							               	 <td class="order-bg" ><input name="qty4" size="2" maxlength="2" value="<%=qty4%>" class="entry"></td>
							               	 <td class="order-bg"><%=itemName4%></td>
							               	 <td class="order-bg"><%=price4%></td>
							               </tr> 
							               <% } %>
							               <% if (item4!=null&&item4.length()>0) { %>
							               <tr>
							               	 <td width="80" class="order-bg">
							               	 	<input name="item5" size="10" maxlength="10" value="<%=item5%>" class="entry">
							               	 	<input type="image" name="action" value="Find Item" src="<%=request.getContextPath()%>/images/search.jpg"></td>
							               	 </td>
							               	 <td class="order-bg" ><input name="qty5" size="2" maxlength="2" value="<%=qty5%>" class="entry"></td>
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
							<tr>
								<td  height="60" valign="bottom" align="left">
									<input type="image" name="action"  value="Save Order" src="<%=request.getContextPath()%>/images/buttons/save.jpg">
						  		    <input type="image" name="action"  value="Print Order Form" src="<%=request.getContextPath()%>/images/buttons/print.jpg">
						  		    <input type="image" name="action"  value="Clear" src="<%=request.getContextPath()%>/images/buttons/clear.jpg">
						  		</td>
							</tr>
							
							</table>
									
						</form>			
						
						<!----End Form----------->
						</div>
									
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>

<jsp:include page="../includes/footer.jsp"/>


