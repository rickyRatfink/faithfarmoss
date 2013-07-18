<%@ page import="java.util.ArrayList" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%

	ArrayList locations = (ArrayList) session.getAttribute("locations");
	if (locations==null) 
		locations = new ArrayList();
		
	ArrayList transactions = (ArrayList) session.getAttribute("transactions");
	if (transactions==null) 
		transactions = new ArrayList();
%>
<%
	String required = "<font size='-2' color='red'>*</font>";
	
	String vendorName = request.getParameter("vendor"); if (vendorName==null) vendorName="";
    String location = request.getParameter("location"); if (location==null) location="";
    String itemName = request.getParameter("itemName"); if (itemName==null) itemName="";
    String itemNumber = request.getParameter("itemNumber"); if (itemNumber==null) itemNumber="";
    String qty1 = request.getParameter("qtyWarehouse"); if (qty1==null) qty1="0";
    String qty2 = request.getParameter("qtyFloor"); if (qty2==null) qty2="0";
    String desc = request.getParameter("description"); if (desc==null) desc="";
    String cost = request.getParameter("cost"); if (cost==null) cost="0.00";
    String price = request.getParameter("price"); if (price==null) price="0.00";
    String minLevel = request.getParameter("min_level"); if (minLevel==null) minLevel="";
    String status = request.getParameter("status"); if (status==null) status="";
    String transactionType = request.getParameter("transactionType"); if (transactionType==null) transactionType="";
    String farmLocation = (String)request.getSession().getAttribute("farm");

	
%>
		<!----Main Content--->
		<td valign="top" valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/inventory-add.jpg">
						<br>
						
						
						
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/inventory?entity=Inventory" name="customer-create-form" id="customer-create-form">
							<jsp:include page="../includes/errors.jsp" flush="true"/>
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td colspan="2" class="field">Vendor<%=required%></td>
							                </tr>
							               	
							               <tr>
							               	 <td colspan="2">
							               	 			<jsp:include page="../includes/vendors.jsp" flush="true"/>
							               	  </td>
							               	  							               	 
							               </tr> 
							               <tr>
												<td colspan="2" class="field">Inventory Location</td>
											</tr>
											<tr>
												<td colspan="2">
												<select name="location" class="entry">
														<option value="">select</option>
														<% for (int i=0;i<locations.size();i++){ %>
								               	 			<option value="<%=locations.get(i)%>"><%=locations.get(i)%></option>
							               	 		<% } %>
												</select>
												</td>
											</tr>
										    <tr>
							                 <td class="field">Item Number<%=required%></td>
							                 <td class="field">Price<%=required%></td>
							               </tr>
							               <tr>
							                  <td><input name="itemNumber" value="<%=itemNumber%>" maxlength="10" size="10" class="entry"></td>
							               	  <td><input value="<%=price%>" onkeypress="return isNumberKey(event)" name="price" maxlength="7" size="10" class="entry"></td>
							               </tr>
							               <tr>
							                 <td width="170" class="field" colspan="3">Item Name<%=required%></td>
							               </tr>
										   <tr>
										     <td colspan="3"><input name="itemName" value="<%=itemName%>" maxlength="30" size="30" class="entry"></td>
							               </tr>
							               <tr>
							                 <td width="170" class="field" colspan="3">Description</td>
							               </tr>
										   <tr>
										     <td colspan="3"><input value="<%=desc%>" name="description" maxlength="60" size="80" class="entry"></td>
							               </tr>	 					               	
							             
							                <tr>
							                 <td width="150" class="field">Quantity in Warehouse<%=required%></td>
							                 <td width="150" class="field">Quantity on Floor<%=required%></td>
							                 <td class="field">Cost<%=required%></td>
							               </tr>
							               	
							               <tr>
							               	  <td><input value="<%=qty1%>" onkeypress="return isNumberKey(event)" name="qtyWarehouse" maxlength="3" size="3" value="0" class="entry"></td>
							               	  <td><input value="<%=qty2%>" onkeypress="return isNumberKey(event)" name="qtyFloor" maxlength="3" size="3" value="0" class="entry"></td>
							               	  <td><input value="<%=cost%>" onkeypress="return isNumberKey(event)" name="cost" maxlength="7" size="10" class="entry"></td>
							               	  							               	 
							               </tr> 
							               
							                <tr>
							                 <td colspan="2" class="field">Transaction Type<%=required%></td>
							                </tr>
							               	
							               <tr>
							               	 <td colspan="2">
							               	 	<select name="transactionType" class="entry">
							               	 		<% for (int i=0;i<transactions.size();i++){ %>
								               	 			<option value="<%=transactions.get(i)%>"><%=transactions.get(i)%></option>
							               	 		<% } %>
							               	 	</select>
							               	  </td>
							               	  							               	 
							               </tr> 
								             
							           </table>
								
								
									
								</td>
							</tr>
							
							
						
							<tr>
								<td  height="60" valign="bottom" align="left">
									<input type="image" name="action"  value="Save" src="<%=request.getContextPath()%>/images/buttons/save.jpg" width="80" height="24">
						  		    <input type="hidden" name="action" value="Save"/>
						  		</td>
							</tr>
							
							</table>
						</form>			
						
						<!----End Form----------->
						</div>
									
			
		
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	

 

  	
  	</div>
</table>

<jsp:include page="../includes/footer.jsp"/>


