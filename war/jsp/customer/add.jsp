<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.faithfarm.dataobjects.Person" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
	Entity person = (Entity)request.getAttribute("Person");
	Entity address = (Entity)request.getAttribute("Address");
	
%>

		<!----Main Content--->
		<td  valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/customer-add.jpg">
						<br>
						
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/customer?entity=Customer" >
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td width="200" class="field">First Name<%=required%></td>
							                 <td width="100" class="field">Middle Initial</td>
							                 <td width="200" class="field">Last Name<%=required%></td>
							                 <td width="100" class="field">Prefix</td>
							               </tr>
							               	
							               <tr>
							               	 <td><input value="<%=person.getProperty("firstName")%>" name="firstName" maxlength="20" size="20" class="entry"></td>
							               	 <td><input value="<%=person.getProperty("middleInitial")%>" name="middleInitial" maxlength="1" size="1" class="entry"></td>
							               	 <td><input value="<%=person.getProperty("lastName")%>"  name="lastName" maxlength="30" size="30" class="entry"></td>
							               	 <td>
							               	 	<select name="suffix" class="entry">
							               	 	    <option value=""></option>
							               	 		<option value="Jr.">Jr.</option>
							               	 		<option value="Sr.">Sr.</option>
							               	 		<option value="I">I</option>
							               	 		<option value="II">II</option>
							               	 		<option value="III">III</option>
							               	 	</select>
							               	 </td>							               	 
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
							<tr>
								<td class="field" >Address Line 1<%=required%></td>
							</tr>
							<tr>
								<td >
									<input value="<%=address.getProperty("addressLine1")%>" name="addressLine1" maxlength="80" size="80" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field" >Address Line 2</td>
							</tr>
							<tr>
								<td >
									<input value="<%=address.getProperty("addressLine2")%>" name="addressLine2" maxlength="80" size="80" class="entry">
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
							               	 <td><input value="<%=address.getProperty("city")%>" name="city" maxlength="30" size="30" class="entry"></td>
							               	 <td>&nbsp;
							               	 		<%
							               	 		ArrayList ddl = (ArrayList) session.getAttribute("ddlStates");
							               	 		%>
							               	 		<select name="state" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(address.getProperty("state").toString())) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                  </select>    
							               	 </td>
							               	 <td><input value="<%=address.getProperty("zipcode")%>" name="zipcode" maxlength="10" size="10" class="entry"></td>
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
							
							<tr>
							     <td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							   	 	<tr>
										<td width="180" class="field" >Home Phone<%=required%></td>
										<td width="180" class="field" >Cell Phone</td>
										<td width="180" class="field" >Work Phone</td>								
									</tr>
									<tr>
										<td><%
											if (((String)address.getProperty("homePhone")).length()!=13) {
											%>
											(<input onKeyPress="return isNumberKey(event)" value="" name="home_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="" name="home_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="" name="home_last4" maxlength="4" size="4" class="entry">
											<% } else { %>
											(<input onKeyPress="return isNumberKey(event)" value="<%=((String)address.getProperty("homePhone")).substring(1,4)%>" name="home_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="<%=((String)address.getProperty("homePhone")).substring(5,8)%>" name="home_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="<%=((String)address.getProperty("homePhone")).substring(9,13)%>" name="home_last4" maxlength="4" size="4" class="entry">
											<% } %>
										</td>
										<td><%
											if (((String)address.getProperty("cellPhone")).length()!=13) {
											%>
											(<input onKeyPress="return isNumberKey(event)" value="" name="cell_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="" name="cell_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="" name="cell_last4" maxlength="4" size="4" class="entry">
											<% } else { %>
											(<input onKeyPress="return isNumberKey(event)" value="<%=((String)address.getProperty("cellPhone")).substring(1,4)%>" name="cell_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="<%=((String)address.getProperty("cellPhone")).substring(5,8)%>" name="cell_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="<%=((String)address.getProperty("cellPhone")).substring(9,13)%>" name="cell_last4" maxlength="4" size="4" class="entry">
											<% } %>
										</td>
										<td><%
											if (((String)address.getProperty("workPhone")).length()!=13) {
											%>
											(<input onKeyPress="return isNumberKey(event)" value="" name="work_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="" name="work_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="" name="work_last4" maxlength="4" size="4" class="entry">
											<% } else { %>
											(<input onKeyPress="return isNumberKey(event)" value="<%=((String)address.getProperty("workPhone")).substring(1,4)%>" name="work_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="<%=((String)address.getProperty("workPhone")).substring(5,8)%>" name="work_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="<%=((String)address.getProperty("workPhone")).substring(9,13)%>" name="work_last4" maxlength="4" size="4" class="entry">
											<% } %>
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
									<input value="<%=address.getProperty("email")%>" name="email" maxlength="80" size="50" class="entry">
								</td>
							</tr>
							
						
							<tr>
								<td  height="60" valign="bottom" align="left">
									<input type="submit" name="action" value="Save" class="buttonSave">
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


