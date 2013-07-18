<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.faithfarm.dataobjects.Person" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%

    String required = "<font size='-2' color='red'>*</font>";
    
	ArrayList locations = (ArrayList)session.getAttribute("locations");
	if (locations==null) locations = new ArrayList();	
	
 	Person person = (Person)session.getAttribute("person");
		
%>


		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/intake-application.jpg">
						<br>
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						<br><br>
						
						<!----Application Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/intake">
						<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td><img src="<%=request.getContextPath()%>/images/titles/personal-info.jpg"/></td>
							</tr>
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
							               	 <td><input value="<%=person.getFirstName()%>" name="firstName" maxlength="30" size="30" class="entry"></td>
							               	 <td><input value="<%=person.getMiddleInitial()%>" name="middleInitial" maxlength="1" size="1" class="entry"></td>
							               	 <td><input value="<%=person.getLastName()%>"  name="lastName" maxlength="30" size="30" class="entry"></td>
							               	 <td>
							               	 	<select name="suffix" class="entry">
							               	 	    <option value=""></option>
							               	 		<option value="Jr.">Jr.</option>
							               	 		<option value="Sr.">Sr.</option>
							               	 		<option value="I">I</option>
							               	 		<option value="II">II</option>
							               	 		<option value="III">III</option>
							               	 		<option value="IV">IV</option>
							               	 	</select>
							               	 </td>			               	 
							               </tr> 
										   					               	
							             
							             
							           </table>
								       <div id="checkSpinner" style="height:30px;">&nbsp;</div>
								
								
									
								</td>
							</tr>
							<tr>
								<td class="field" >Address Line 1<%=required%></td>
							</tr>
							<tr>
								<td >
									<input value="<%=person.getAddress().getAddressLine1()%>" name="addressLine1" maxlength="80" size="80" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field" >Address Line 2</td>
							</tr>
							<tr>
								<td >
									<input value="<%=person.getAddress().getAddressLine2()%>" name="addressLine2" maxlength="80" size="80" class="entry">
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
							               	 <td><input value="<%=person.getAddress().getCity()%>" name="city" maxlength="30" size="30" class="entry"></td>
							               	 <td>
                                             	 <%	ArrayList ddl = (ArrayList)session.getAttribute("ddlStates"); %>
                                             		<select name="state" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) { %>
                                                              <option value="<%=ddl.get(j)%>" <% if (person.getAddress().getState().equals(ddl.get(j))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>
                                                   
							               	 </td>
							               	 <td><input value="<%=person.getAddress().getZipcode()%>" name="zipcode" maxlength="10" size="10" class="entry"></td>
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
							<!---
							<tr>
							     <td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							   	 	<tr>
										<td width="180" class="field" >Contact Phone</td>
									</tr>
									<tr>
										<td><%
											if (person.getAddress().getHomePhone().length()!=13) {
											%>
											(<input onKeyPress="return isNumberKey(event)" value="" name="phone_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="" name="phone_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="" name="phone_last4" maxlength="4" size="4" class="entry">
											<% } else { %>
											(<input onKeyPress="return isNumberKey(event)" value="<%=person.getAddress().getHomePhone().substring(1,4)%>" name="phone_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="<%=person.getAddress().getHomePhone().substring(5,8)%>" name="phone_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="<%=person.getAddress().getHomePhone().substring(9,13)%>" name="phone_last4" maxlength="4" size="4" class="entry">
											<% } %>
										</td>
									</tr>
								</table>
							    </td>
							</tr>
							 --->
							<tr>
							     <td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							   	 	<tr>
										<td width="180" class="field" >Social Security Number<%=required%></td>
									</tr>
									<tr>
										<td>
											<input onKeyPress="return isNumberKey(event)" value="<%=person.getSsn()%>" name="ssn" maxlength="9" size="9" class="entry">
										</td>
									</tr>
								</table>
								<table width="180" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td width="180" align="left" class="field">
												Do you have your social security card?<%=required%>
											</td>
										</tr>
										<tr>
											<td width="190" align="left" class="field">
												<select name="ssnCard" class="entry">
															<option value="">select</option>
															<option value="Yes"  <% if ("Yes".equals(person.getSsnCard())) {%>selected<%}%>>Yes</option>
															<option value="No"  <% if ("No".equals(person.getSsnCard())) {%>selected<%}%>>No</option>
												</select>
											</td>
										</tr>
									</table>		
							    </td>
							</tr>
							<tr>
								<td><br><br><img src="<%=request.getContextPath()%>/images/titles/referral-info.jpg"/></td>
							</tr>
							<tr>
							<td >
								 <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							   	 	<tr>
										<td width="160" class="field" >Referred By</td>
										<td width="160" class="field" >Contact Phone</td>
									</tr>
									<tr>
									<td >
										<input value="<%=person.getReferredBy()%>" name="referredBy" maxlength="80" size="50" class="entry">
									</td>
									<td>
										<%
											if (person.getReferralPhone().length()!=13) {
											%>
											(<input onKeyPress="return isNumberKey(event)" value="" name="ref_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="" name="ref_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="" name="ref_last4" maxlength="4" size="4" class="entry">
											<% } else { %>
											(<input onKeyPress="return isNumberKey(event)" value="<%=person.getReferralPhone().substring(1,4)%>" name="ref_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="<%=person.getReferralPhone().substring(5,8)%>" name="ref_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="<%=person.getReferralPhone().substring(9,13)%>" name="ref_last4" maxlength="4" size="4" class="entry">
											<% } %>
									</td>
									</tr>
								</table>
							</td>		
							</tr>
							
							
							<tr>
								<td  valign="bottom" ><br><br><img src="<%=request.getContextPath()%>/images/titles/emergency-info.jpg"/></td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							   		<tr>
										<td width="160" class="field" >Emergency Contact</td>
									</tr>
                                    <tr>
                                    	<td >
											<input value="<%=person.getEmergencyContact()%>" name="emergencyContact" maxlength="80" size="50" class="entry">
										</td>
                                    </tr>
                                    </table>
                                    <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							   		<tr>
                                    	<td width="120" class="field" >Emergency Phone</td>
										<td width="200" class="field" >Relationship</td>									
									</tr>
                                    <tr>
									
										<td>
											<%
											if (person.getEmergencyPhone().length()!=13) {
											%>
											(<input onKeyPress="return isNumberKey(event)" value="" name="emer_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="" name="emer_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="" name="emer_last4" maxlength="4" size="4" class="entry">
											<% } else { %>
											(<input onKeyPress="return isNumberKey(event)" value="<%=person.getEmergencyPhone().substring(1,4)%>" name="emer_areacode" maxlength="3" size="3" class="entry">)
											<input onKeyPress="return isNumberKey(event)" value="<%=person.getEmergencyPhone().substring(5,8)%>" name="emer_first3" maxlength="3" size="3" class="entry">&nbsp;-
											<input onKeyPress="return isNumberKey(event)" value="<%=person.getEmergencyPhone().substring(9,13)%>" name="emer_last4" maxlength="4" size="4" class="entry">
											<% } %>
										</td>
										 <td>
                                                    <select name="emergencyRelationship" class="entry">
                                                        <option value="">select one</option>
                                                        <option value="Aunt" <% if ("Aunt".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Aunt</option>
                                                        <option value="Boyfriend" <% if ("Boyfriend".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Boyfriend</option>
                                                        <option value="Brother" <% if ("Brother".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Brother</option>
                                                        <option value="Employer" <% if ("Employer".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Employer</option>
                                                        <option value="Ex-Husband" <% if ("Ex-Husband".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Ex-Husband</option>
                                                        <option value="Ex-Wife" <% if ("Ex-Wife".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Ex-Wife</option>
                                                        <option value="Father" <% if ("Father".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Father</option>
                                                        <option value="Friend" <% if ("Friend".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Friend</option>
                                                        <option value="Girlfiend" <% if ("Girlfiend".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Girlfiend</option>
                                                        <option value="Grandfather" <% if ("Grandfather".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Grandfather</option>
                                                        <option value="Grandmother" <% if ("Grandmother".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Grandmother</option>
                                                        <option value="Husband" <% if ("Husband".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Husband</option>
                                                        <option value="Mother" <% if ("Mother".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Mother</option>
                                                        <option value="Other" <% if ("Other".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Other</option>
                                                        <option value="Significant Other" <% if ("Significant Other".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Significant Other</option>
                                                    	<option value="Sister" <% if ("Sister".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Sister</option>
                                                        
                                                        <option value="Step-Father" <% if ("Step-Father".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Step-Father</option>
                                                        <option value="Step-Mother" <% if ("Step-Mother".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Step-Mother</option>
                                                        
                                                        
                                                        <option value="Uncle" <% if ("Uncle".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Uncle</option>
                                                        <option value="Wife" <% if ("Wife".equals(person.getEmergencyRelationship())) {%>selected<%}%>>Wife</option>
                                                    </select>
									      </td>			
									</tr>
								</td>
								</tr>
							</table>
							</td>
							</tr>
						
							<tr>
								<td height="60" valign="bottom" align="right">
									<input type="submit" name="action" value="Next" class="buttonNext">
								</td>
							</tr>
							
							</table>
						
						
						
						<input type="hidden" name="page" value="01"/>
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
<script type="text/javascript" src="/script/student_check.js"></script>
<script type="text/javascript" src="/script/spin.min.js"></script>

<jsp:include page="../includes/footer.jsp"/>


