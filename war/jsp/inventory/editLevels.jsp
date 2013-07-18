<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	ArrayList locations = (ArrayList)session.getAttribute("locations");
	if (locations==null) locations = new ArrayList();	
	

		
%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/inventory-search.jpg">
						
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						
						<!----Search Form ------->
						
						<form action="<%=request.getContextPath()%>/inventory?entity=Update" name="item-search-form" id="item-search-form">
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td class="field">Item Number</td>
							</tr>
							
							<tr>
								<td><input maxlength="10" size="10" name="itemNumber" class="entry"></td>
							</tr>
							<!--
							<tr>
								<td class="field">Item Name</td>
							</tr>
							<tr>
								<td><input maxlength="20" size="20" name="itemName" class="entry"></td>
							</tr>	
							<tr>
								<td class="field">Description</td>
							</tr>
							<tr>
								<td><input name="item" maxlength="40" size="40" name="description" class="entry"></td>
							</tr>
							
							<tr>
								<td class="field">Inventory Location</td>
							</tr>
							<tr>
								<td>
								<select name="location" class="entry">
											<option value="">select</option>
					               	 		<% for (int i=0;i<locations.size();i++){ %>
						               	 			<option value="<%=locations.get(i)%>"><%=locations.get(i)%></option>
					               	 		<% } %>
								</select>
								</td>
							</tr>
							-->
							<tr>
								<td colspan="2" height="60" valign="bottom" align="left">
								<input type="image" src="<%=request.getContextPath()%>/images/buttons/search.jpg" width="80" height="24">
						  			<input type="hidden" name="entity" value="Inventory Level"/>
						  			<input type="hidden" name="action" value="Retrieve"/></td>
							</tr>
							
							</table>
							<br>
							<br>
										
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


