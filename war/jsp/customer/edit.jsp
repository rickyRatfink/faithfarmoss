<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.dataobjects.Person" %>
<%@ page import="java.util.ArrayList" %>
<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
	Entity Person = (Entity)request.getAttribute("Person");
	Entity Address = (Entity)request.getAttribute("Address");
	
%>
		<!----Main Content--->
		<td  valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/customer-edit.jpg">
						<br>
						
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/customer?entity=Customer" name="customer-create-form" id="customer-create-form">
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
							               	 <td><input name="firstName" value="<%=Person.getProperty("firstName")%>" maxlength="20" size="20" class="entry"></td>
							               	 <td><input name="middleInitial" value="<%=Person.getProperty("middleInitial")%>" maxlength="1" size="1" class="entry"></td>
							               	 <td><input name="lastName" value="<%=Person.getProperty("lastName")%>" maxlength="30" size="30" class="entry"></td>
							               	 <td>
							               	 	<select name="suffix" class="entry">
							               	 	    <option value=""></option>
							               	 		<option value="">Jr.</option>
							               	 		<option value="">Sr.</option>
							               	 		<option value="">I</option>
							               	 		<option value="">II</option>
							               	 		<option value="">III</option>
							               	 	</select>							               	 
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
							<tr>
								<td class="field" >Address Line 1<%=required%></td>
							</tr>
							<tr>
								<td >
									<input name="addressLine1" value="<%=Address.getProperty("addressLine1")%>" maxlength="80" size="80" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field" >Address Line 2</td>
							</tr>
							<tr>
								<td >
									<input name="addressLine2" value="<%=Address.getProperty("addressLine2")%>" maxlength="80" size="80" class="entry">
								</td>
							</tr>
							<tr>
							   <td >
								 <table width="480" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td width="190" class="field">City<%=required%></td>
							                 <td width="180" class="field">State<%=required%></td>
							                 <td width="160" class="field">Zipcode<%=required%></td>
							               </tr>
							               	
							               <tr>
							               	 <td><input name="city" value="<%=Address.getProperty("city")%>" maxlength="30" size="30" class="entry"></td>
							               	 <td>&nbsp;
							               	 		<%
							               	 		ArrayList ddl = (ArrayList) session.getAttribute("ddlStates");
							               	 		%>
							               	 		<select name="state" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) {%>
                                                              <option value="<%=ddl.get(j)%>" <% if (ddl.get(j).equals(Address.getProperty("state").toString())) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                  </select>    
							               	 </td>
							               	 <td><input name="zipcode" value="<%=Address.getProperty("zipcode")%>" maxlength="10" size="10" class="entry"></td>
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
									
									<%
									String cell=(String)Address.getProperty("cellPhone");
									String cell1="",cell2="",cell3="";
									
									String home=(String)Address.getProperty("homePhone");
									String home1="",home2="",home3="";
									
									String work=(String)Address.getProperty("workPhone");
									String work1="",work2="",work3="";
									
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
										
									%>
									<tr>
										<td>
											(<input name="home_areacode" value="<%=home1%>" maxlength="3" size="3" class="entry">)
											<input name="home_first3" value="<%=home2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="home_last4" value="<%=home3%>" maxlength="4" size="4" class="entry">
										</td>
										<td>
											(<input name="cell_areacode" value="<%=cell1%>" maxlength="3" size="3" class="entry">)
											<input name="cell_first3" value="<%=cell2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="cell_last4" value="<%=cell3%>" maxlength="4" size="4" class="entry">
										</td>
										<td>
											(<input name="work_areacode" value="<%=work1%>" maxlength="3" size="3" class="entry">)
											<input name="work_first3" value="<%=work2%>" maxlength="3" size="3" class="entry">&nbsp;-
											<input name="work_last4" value="<%=work3%>" maxlength="4" size="4" class="entry">
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
									<input name="email" value="<%=Address.getProperty("email")%>" maxlength="80" size="50" class="entry">
								</td>
							</tr>
							
						
							<tr>
								<td  height="60" valign="bottom" align="left">
									<input type="submit" name="action" value="Save Changes" class="buttonSave">
                                    <input type="hidden" name="id" value="<%=Person.getKey().getId()%>" />
                                    <input type="hidden" name="personKey" value="<%=KeyFactory.keyToString(Person.getKey())%>" />
                                      
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


