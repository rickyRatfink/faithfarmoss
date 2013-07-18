<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="java.util.ArrayList" %>

<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";

	
	ArrayList locations = (ArrayList) session.getAttribute("locations");
	if (locations==null) 
		locations = new ArrayList();
		
	ArrayList transactions = (ArrayList) session.getAttribute("transactions");
	if (transactions==null) 
		transactions = new ArrayList();

	
	Entity e = (Entity)request.getAttribute("entity");
					
%>

		<!----Main Content--->
		<td valign="top" valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/inventory-edit.jpg">
						<br>
						
						
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/inventory?entity=Inventory" name="customer-create-form" id="customer-create-form">
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td class="field">Vendor<%=required%></td>
							                 <td class="field">Item Number<%=required%></td>
							                 <td class="field">Price<%=required%></td>
							               </tr>
							               	
							               <tr>
							               	 <td>
							               	    <jsp:include page="../includes/vendors.jsp" flush="true"/>
							               	 </td>
							               	  <td><input name="itemNumber" value="<%=e.getProperty("itemNumber")%>" maxlength="10" size="10" class="entry"></td>
							               	  <td><input name="price" value="<%=e.getProperty("price")%>" onkeypress="return isNumberKey(event)" maxlength="7" size="10" class="entry"></td>
							               	  							               	 
							               </tr> 
							               <tr>
												<td colspan="2" class="field">Inventory Location</td>
											</tr>
											<tr>
												<td colspan="2">
												<select name="location" class="entry">
							               	 		<option value="">select</option>
							               	 		<% for (int i=0;i<locations.size();i++){ 
								               	 		if (e.getProperty("location").toString().equals((String)locations.get(i))) { %>
							               	 				<option value="<%=locations.get(i)%>" selected><%=locations.get(i)%></option>
							               	 			<% } else { %>
							               	 				<option value="<%=locations.get(i)%>"><%=locations.get(i)%></option>
							               	 			<% } %>
							               	 		<% } %>
							       			    </select>
							       			   
												</td>
											</tr>
							               <tr>
							                 <td width="170" class="field" colspan="3">Item Name<%=required%></td>
							               </tr>
										   <tr>
										     <td colspan="3"><input name="itemName" value="<%=e.getProperty("itemName")%>" maxlength="30" size="30" class="entry"></td>
							               </tr>
							               <tr>
							                 <td width="170" class="field" colspan="3">Description</td>
							               </tr>
										   <tr>
										     <td colspan="3"><input name="description" value="<%=e.getProperty("description")%>" maxlength="60" size="80" class="entry"></td>
							               </tr>	 					               	
							             
							                <tr>
							                 <td width="150" class="field">Quantity in Warehouse<%=required%></td>
							                 <td width="150" class="field">Quantity on Floor<%=required%></td>
							                 <td class="field">Cost<%=required%></td>
							               </tr>
							               	
							               <tr>
							               	  <td><input onkeypress="return isNumberKey(event)" name="qtyWarehouse" value="<%=e.getProperty("qtyWarehouse")%>" maxlength="3" size="3" class="entry"></td>
							               	  <td><input onkeypress="return isNumberKey(event)" name="qtyFloor" value="<%=e.getProperty("qtyFloor")%>" maxlength="3" size="3" class="entry"></td>
							               	  <td><input onkeypress="return isNumberKey(event)" name="cost" value="<%=e.getProperty("cost")%>" maxlength="7" size="10" class="entry"></td>
							               	  							               	 
							               </tr> 
								             
							                <tr>
							                 <td colspan="2" class="field">Transaction Type<%=required%></td>
							                </tr>
							               	
							               <tr>
							               	 <td colspan="2">
							               	 	<select name="transactionType" class="entry">
							               	 		<% for (int i=0;i<transactions.size();i++){ 
								               	 		if (e.getProperty("transactionType").toString().equals((String)transactions.get(i))) { %>
							               	 				<option value="<%=transactions.get(i)%>" selected><%=transactions.get(i)%></option>
							               	 			<% } else { %>
							               	 				<option value="<%=transactions.get(i)%>"><%=transactions.get(i)%></option>
							               	 			<% } %>
							               	 		<% } %>
							               	 	</select>
							               	  </td>
							               	  							               	 
							               </tr> 
								             
							           </table>
								
								
									
								</td>
							</tr>
							
							
						
							<tr>
								<td  height="60" valign="bottom" align="left">
									<input type="image" src="<%=request.getContextPath()%>/images/buttons/save.jpg" width="80" height="24">
						  		    <input type="hidden" name="action" value="Save Changes"/>
						  			<input type="hidden" name="key" value="<%=e.getKey()%>"/>
						  		</td>
							</tr>
							
							</table>
						<input type="hidden" name="name" value="123"/>			
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


