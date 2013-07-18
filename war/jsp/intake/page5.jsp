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
								<td><img src="<%=request.getContextPath()%>/images/titles/medical-info.jpg"/><br></td>
							</tr>
							<tr>
								<td >
									
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="210" align="left" class="field">
												Describe your current health?
											</td>
											<td valign="bottom">											
												<select name="currentHealth" class="entry">
																	<option value="">select</option>
																	<option value="excellent" <% if ("excellent".equals(person.getPersonMisc().getCurrentHealth())) {%>selected<%}%>>Excellent</option>
																	<option value="good" <% if ("good".equals(person.getPersonMisc().getCurrentHealth())) {%>selected<%}%>>Good</option>
																	<option value="fair" <% if ("fair".equals(person.getPersonMisc().getCurrentHealth())) {%>selected<%}%>>Fair</option>
																	<option value="failing" <% if ("failing".equals(person.getPersonMisc().getCurrentHealth())) {%>selected<%}%>>Failing</option>
																	<option value="poor" <% if ("poor".equals(person.getPersonMisc().getCurrentHealth())) {%>selected<%}%>>Poor</option>
												</select>
											</td>
											
										</tr>
										<tr>
											<td  width="210" align="left" class="field">
												Are you currently taking any medication?
											</td>
											<td valign="bottom">											
												<select name="currentMedicationsFlag" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getCurrentMedicationsFlag())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getCurrentMedicationsFlag())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
										<tr>
											<td colspan="2" class="field">
												<i>If you answered yes to the question above please name the medications
												being taken?</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="field">											
												<textarea name="currentMedicationsDetails"><%=person.getPersonMisc().getCurrentMedicationsDetails()%></textarea>
											</td>
											
										</tr>
										<tr>
											<td align="left" class="field">
												Are you in need of medication?
											</td>
											<td valign="bottom">											
												<select name="needMedicationFlag" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getNeedMedicationFlag())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getNeedMedicationFlag())) {%>selected<%}%>>No</option>
												</select>
											</td>
										</tr>
										<tr>
											<td  width="210" align="left" class="field">
												Do you have enough for 30 days?
											</td>
											<td valign="bottom">											
												<select name="medicationSuppyFlag" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicationSuppyFlag())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getMedicationSuppyFlag())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
										<tr>
											<td colspan="2" class="field">
												<i>How do you get refills?</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="field">											
												<textarea name="refillDetails"><%=person.getPersonMisc().getRefillDetails()%></textarea>
											</td>
											
										</tr>
										<tr>
											<td  width="210" align="left" class="field">
												Do you have private health insurance?
											</td>
											<td valign="bottom">											
												<select name="privateHealthCoverage" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getPrivateHealthCoverage())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getPrivateHealthCoverage())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
										<tr>
											<td  width="210" align="left" class="field">
												Are you currently covered by W80 or W72?
											</td>
											<td valign="bottom">											
												<select name="govtHealthCoverage" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getGovtHealthCoverage())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getGovtHealthCoverage())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
										
										<tr>
											<td colspan="2" class="field">
											<br>
												If you have a doctor's appointment in the next 30 days specify when it is. <i>(MM/DD/YYYY)<i>&nbsp;
												<input name="doctorsAppointment" value="<%=person.getPersonMisc().getDoctorsAppointment()%>" maxlength="10" size="10" class="entry">
												<br><br>
											</td>
										</tr>
										</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
								   			<td class="instructions">
								   				<b>Check and answer all the following questions that apply to you.</b><br><br>
								   			</td>
								   		</tr>
										<tr>
											<td align="left" class="field">
												<input type="checkbox" name="medicalQuestion1Flag" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalQuestion1Flag())) {%>checked="checked"<%}%>>Been treated for or told that you have any sickness or injury in the past 5 years?<br>
												<i>If you answered yes to the question above please provide details.</i><br>
												<textarea name="medicalQuestion1Details"><%=person.getPersonMisc().getMedicalQuestion1Details()%></textarea><br><br>
											
												<input type="checkbox" name="medicalQuestion2Flag" value="Yes"  <%if ("Yes".equals(person.getPersonMisc().getMedicalQuestion2Flag())) {%>checked="checked"<%}%>>Consulted, been examined by or been treated by a doctor in the past 5 years?<br>
												<i>If you answered yes to the question above please provide details.</i><br>
												<textarea name="medicalQuestion2Details"><%=person.getPersonMisc().getMedicalQuestion2Details()%></textarea><br><br>
											
												<input type="checkbox" name="medicalQuestion3Flag" value="Yes"  <%if ("Yes".equals(person.getPersonMisc().getMedicalQuestion3Flag())) {%>checked="checked"<%}%>>Been in a hospital, psychiatric hospital or other institution for diagnosis, treatment or
												operation in the past 5 years?<br>
												<i>If you answered yes to the question above please provide details.</i><br>
												<textarea name="medicalQuestion3Details"><%=person.getPersonMisc().getMedicalQuestion3Details()%></textarea><br><br>
											
												<input type="checkbox" name="medicalQuestion4Flag" value="Yes"  <%if ("Yes".equals(person.getPersonMisc().getMedicalQuestion4Flag())) {%>checked="checked"<%}%>>Been advised to have any hospital, clinical, surgical or other treatment in the past 5 years?<br>
												<i>If you answered yes to the question above please provide details.</i><br>
												<textarea name="medicalQuestion4Details"><%=person.getPersonMisc().getMedicalQuestion4Details()%></textarea><br><br>
											
												<input type="checkbox" name="medicalQuestion5Flag" value="Yes"  <%if ("Yes".equals(person.getPersonMisc().getMedicalQuestion5Flag())) {%>checked="checked"<%}%>>Had any prior injuries to your back that would affect your lifting, bending or twisting capabilities?<br>
												<i>If you answered yes to the question above please provide details.</i><br>
												<textarea name="medicalQuestion5Details"><%=person.getPersonMisc().getMedicalQuestion5Details()%></textarea><br><br>
											
												
											</td>
											
											
										</tr>
										<tr>
											<td align="left" class="field">
												If you wear glasses or contact lenses please specify how often.&nbsp;&nbsp;
												<select name="glassesContactLenses" class="entry">
																	<option value="">select</option>
																	<option value="All the time" <%if ("All the time".equals(person.getPersonMisc().getGlassesContactLenses())) {%>selected<%}%>>All the time</option>
																	<option value="Occasionally" <%if ("Occasionally".equals(person.getPersonMisc().getGlassesContactLenses())) {%>selected<%}%>>Occasionally</option>
																	<option value="Reading" <%if ("Reading".equals(person.getPersonMisc().getGlassesContactLenses())) {%>selected<%}%>>Reading</option>
												</select>
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
						
							
												
						<input type="hidden" name="page" value="05"/>
	
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


