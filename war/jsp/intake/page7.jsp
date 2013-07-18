<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="org.faithfarm.dataobjects.Person" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	ArrayList locations = (ArrayList)session.getAttribute("locations");
	if (locations==null) locations = new ArrayList();	
	

		String required = "<font size='-2' color='red'>*</font>";
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
						<table width="80%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td><img src="<%=request.getContextPath()%>/images/titles/legal-info.jpg"/><br></td>
							</tr>
							<tr>
								<td >
									
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="300" align="left" class="field">
												Have you ever been sued?<%=required%>
											</td>
											<td valign="bottom">											
												<select name="legalQuestion1" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getLegalQuestion1())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getLegalQuestion1())) {%>selected"<%}%>>No</option>
												</select>
											</td>
											
										</tr>
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								  
									<tr>
											<td width="400" class="field">
												<i>If you answered yes to the question above please give a date	</i>											being taken?</i>
											</td>
										
											<td class="field">											
												<input name="legalQuestion1Date" value="<%=person.getPersonMisc().getLegalQuestion1Date()%>" size="10" maxlength="10" class="entry">
											</td>
											
										</tr>
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   	<tr>
											<td colspan="2" class="field">
												<i>Details</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="field">											
												<textarea name="legalQuestion1Details"><%=person.getPersonMisc().getLegalQuestion1Details()%></textarea>
											</td>
											
										</tr>
										
										</table>
										
										
															<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="300" align="left" class="field">
												Are you involved in any lawsuits?<%=required%>
											</td>
											<td valign="bottom">											
												<select name="legalQuestion2" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getLegalQuestion2())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getLegalQuestion2())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								  
									<tr>
											<td width="400" class="field">
												<i>If you answered yes to the question above please give a date	</i>											being taken?</i>
											</td>
										
											<td class="field">											
												<input name="legalQuestion2Date" value="<%=person.getPersonMisc().getLegalQuestion2Date()%>" size="10" maxlength="10" class="entry">
											</td>
											
										</tr>
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td colspan="2" class="field">
												<i>Details</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="field">											
												<textarea name="legalQuestion2Details"><%=person.getPersonMisc().getLegalQuestion2Details()%></textarea>
											</td>
											
										</tr>
										
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="300" align="left" class="field">
												Have you ever been convicted of a felony?<%=required%>
											</td>
											<td valign="bottom">											
												<select name="legalQuestion3" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getLegalQuestion3())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getLegalQuestion3())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
                                        <tr>
											<td colspan="2" class="field">
											<br>
											<i>If you answered yes to the question above , how many times?											
												<input name="legalQuestion3Quantity" value="<%=person.getPersonMisc().getLegalQuestion3Quantity()%>" maxlength="2" size="2" class="entry" />
											</td>
											
										</tr>
										<tr>
											<td colspan="2" class="field">
											<br>
											<i>If you answered yes to the question above please name them.											
												<textarea name="legalQuestion3Details"><%=person.getPersonMisc().getLegalQuestion3Details()%></textarea>
											</td>
											
										</tr>
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="300" align="left" class="field">
												Have you ever been convicted of a sexual offense?<%=required%>
											</td>
											<td valign="bottom">											
												<select name="legalQuestion4" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getLegalQuestion4())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getLegalQuestion4())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
										<tr>
											<td colspan="2" class="field">		
											<br>
											<i>If you answered yes to the question above please name them.										
												<textarea name="legalQuestion4Details"><%=person.getPersonMisc().getLegalQuestion4Details()%></textarea>
											</td>
											
										</tr>
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="300" align="left" class="field">
												Are you currently on probation?<%=required%>
											</td>
											<td valign="bottom">											
												<select name="probationFlag" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getProbationFlag())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getProbationFlag())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								  
									<tr>
											<td width="400" class="field">
												<i>If you answered yes to the question above please give a county/state	</i>											being taken?</i>
											</td>
									</tr>
									<tr>
											<td>											
												<input name="probationCounty" value="<%=person.getPersonMisc().getProbationCounty()%>" size="30" maxlength="30" class="entry">
												<%	ArrayList ddl = (ArrayList)session.getAttribute("ddlStates"); %>
                                             		<select name="probationState" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) { %>
                                                              <option value="<%=ddl.get(j)%>" <% if (person.getPersonMisc().getProbationState().equals(ddl.get(j))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>
											</td>
											
									</tr>
									</table>		
										
									<table width="100%" cellspacing="0" cellpadding="0" border="0" >
								  		<tr>
											<td width="200" class="field">
												<i>Probation Officer's Name</i>	
											</td>
											<td width="200" class="field">
												<i>Probation Officer's Phone Number</i>	
											</td>
										</tr>
										<tr>
											<td>											
												<input name="probationOfficer" value="<%=person.getPersonMisc().getProbationOfficer()%>" size="40" maxlength="40" class="entry">
											</td>
											<td>	
											<%
											if (person.getPersonMisc().getProbationOfficerPhone().length()!=13) {
											%>
												(<input onKeyPress="return isNumberKey(event)" value="" name="probationOfficerPhone_areacode" maxlength="3" size="3" class="entry">)
												<input onKeyPress="return isNumberKey(event)" value="" name="probationOfficerPhone_first3" maxlength="3" size="3" class="entry">&nbsp;-
												<input onKeyPress="return isNumberKey(event)" value="" name="probationOfficerPhone_last4" maxlength="4" size="4" class="entry">
											<% } else { %>
												(<input onKeyPress="return isNumberKey(event)" value="<%=person.getPersonMisc().getProbationOfficerPhone().substring(1,4)%>" name="probationOfficerPhone_areacode" maxlength="3" size="3" class="entry">)
												<input onKeyPress="return isNumberKey(event)" value="<%=person.getPersonMisc().getProbationOfficerPhone().substring(5,8)%>" name="probationOfficerPhone_first3" maxlength="3" size="3" class="entry">&nbsp;-
												<input onKeyPress="return isNumberKey(event)" value="<%=person.getPersonMisc().getProbationOfficerPhone().substring(9,13)%>" name="probationOfficerPhone_last4" maxlength="4" size="4" class="entry">
											<% } %>
											</td>											
										</tr>
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="300" align="left" class="field">
												Do you have any pending court dates?<%=required%>
											</td>
											<td valign="bottom">											
												<select name="pendingCourtDates" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getPendingCourtDates())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getPendingCourtDates())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="300" align="left" class="field">
												If you owe child support, enter the amount and jurisdiction.
											</td>
										</tr>
										<tr>
											<td valign="bottom">											
												 <input value="<%=person.getPersonMisc().getChildSupport()%>" name="childSupport" maxlength="60" size="60" class="entry">							               	
											</td>											
										</tr>
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="300" align="left" class="field">
												If you owe restitution, enter the amount and jurisdiction.
											</td>
										</tr>
										<tr>
											<td valign="bottom">											
												 <input value="<%=person.getPersonMisc().getRestitution()%>" name="restitution" maxlength="60" size="60" class="entry">							               	
											</td>											
										</tr>
									</table>
							</td>
							</tr>
												
							<tr>
								<td  width="50%" height="60" valign="bottom" align="left">
									<input type="submit" name="action" value="Back" class="buttonBack">
								</td>
								<td  width="50%" height="60" valign="bottom" align="right">
									<input type="submit" name="action" value="Next" class="buttonNext">
								</td>
							</tr>
							
							</table>
						
							
												
						<input type="hidden" name="page" value="07"/>
	
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


