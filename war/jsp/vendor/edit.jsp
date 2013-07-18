<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
	Entity e = (Entity)session.getAttribute("entity");

%>
		<!----Main Content--->
		<td  valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<table>
				            <tr>
				              <td class="text1">
				                Edit Organization
				              </td>
				            </tr>
				          </table>
						
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/vendor" name="Vendor-create-form" id="Vendor-create-form">
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td width="170" class="field">Organization Number<%=required%></td>
							                </tr>
							                 <tr>
							               	 <td><input name="number" value="<%=e.getProperty("number")%>" maxlength="10" size="10" class="entry"></td>
							              	</tr>
							              	 <tr>
							                     <td width="170" class="field">
							                        Organization Type
							                        <%=required%>
							                        <% String orgType=(String)e.getProperty("orgType"); %>
							                      </td>
							                    </tr>
							                     <tr>
							                      <td>
							                        <select name="orgType" class="entry">
							                        	<option value=""></option>
							                        	<option value="Civic Organization" <% if ("Civic Organization".equals(orgType)) { %>selected<% } %>>Civic Organization</option>
							                        	<option value="Supplier" <% if ("Supplier".equals(orgType)) { %>selected<% } %>>Supplier</option>
							                        	<option value="Vendor" <% if ("Vendor".equals(orgType)) { %>selected<% } %>>Vendor</option>
							                        </select>
							                      </td>
							                     </tr>
							              	<tr>
							                 <td width="180" class="field">Organization Name<%=required%></td>
							               </tr>
							               <tr>
							               	 <td><input name="name" value="<%=e.getProperty("name")%>" maxlength="60" size="60" class="entry"></td>
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
							<tr>
								<td class="field" >Representative<%=required%></td>
							</tr>
							<tr>
								<td >
									<input name="representative" value="<%=e.getProperty("representative")%>" maxlength="40" size="40" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field" >Address Line 1<%=required%></td>
							</tr>
							<tr>
								<td >
									<input name="addressLine1" value="<%=e.getProperty("addressLine1")%>" maxlength="80" size="80" class="entry">
								</td>
							</tr>
							
							<tr>
							   <td >
								 <table width="480" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td width="190" class="field">City<%=required%></td>
							                 <td width="150" class="field">State<%=required%></td>
							                 <td width="160" class="field">Zipcode<%=required%></td>
							               </tr>
							               	
							               <tr>
							               	 <td><input name="city" value="<%=e.getProperty("city")%>" maxlength="30" size="30" class="entry"></td>
							               	 <td>
							               	 	<jsp:include page="../includes/states.jsp" flush="true" />
							               	 </td>
							               	 <td><input name="zipcode" value="<%=e.getProperty("zipcode")%>" maxlength="10" size="10" class="entry"></td>
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
							
							<tr>
							     <td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							   	 	<tr>
										<td width="180" class="field" >Phone<%=required%></td>
									</tr>
									
									<%
									String home=(String)e.getProperty("phone");
									String home1="",home2="",home3="";
									
									if (home!=null&&home.length()==13) { 									                  
										home1=home.substring(1,4);
										home2=home.substring(5,8);
										home3=home.substring(9,13);
									}
																			
									%>
									<tr>
										<td>
											(<input name="phone_areacode" value="<%=home1%>" maxlength="3" size="3" class="entry">)
											<input name="phone_first3" value="<%=home2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="phone_last4" value="<%=home3%>" maxlength="4" size="4" class="entry">
										</td>
										
								   </tr>
								</table>
							    </td>
							</tr>
							
							<tr>
								<td class="field" >Email Address<%=required%></td>
							</tr>
							<tr>
								<td >
									<input name="email" value="<%=e.getProperty("email")%>" maxlength="80" size="50" class="entry">
								</td>
							</tr>
							
						
							<tr>
								<td  height="60" valign="bottom" align="left">
									<input type="image" src="<%=request.getContextPath()%>/images/buttons/save.jpg" width="80" height="24">
						  		    <input type="hidden" name="action" value="Save Changes"/>
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


