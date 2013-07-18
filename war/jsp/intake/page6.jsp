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
								<td>
								<img src="<%=request.getContextPath()%>/images/titles/medical-info.jpg"/>
								</td>
							</tr>
							<tr>
								<td >
									
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="300" align="left" class="field">
												Have you ever had a state claim for an industrial injury?<%=required%>
											</td>
											<td valign="bottom">											
												<select name="industrialInjuryFlag" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getIndustrialInjuryFlag())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getIndustrialInjuryFlag())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								  
									<tr>
											<td width="400" class="field">
												<i>If you answered yes to the question above please give a date												being taken?</i>
											</td>
										
											<td class="field">											
												<input name="industrialInjuryDate" value="<%=person.getPersonMisc().getIndustrialInjuryDate()%>" size="10" maxlength="10" class="entry">
											</td>
											
										</tr>
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   	<tr>
											<td colspan="2" class="field">
												<i>Reason</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" >											
												<textarea name="industrialInjuryDetails"><%=person.getPersonMisc().getIndustrialInjuryDetails()%></textarea>
											</td>
											
										</tr>
										
										<tr>
											<td colspan="2" class="field">
												<i>Where</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" >											
												<input name="industrialInjuryWhere" value="<%=person.getPersonMisc().getIndustrialInjuryWhere()%>" size="30" maxlength="30" class="entry">
											</td>
											
										</tr>
										
										<tr>
											<td colspan="2" class="field">
												<i>Employer</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" >											
												<input name="industrialInjuryEmployer" value="<%=person.getPersonMisc().getIndustrialInjuryEmployer()%>" size="30" maxlength="30" class="entry">
											</td>
											
										</tr>
									
										<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   	
										<tr>
											<td  width="90" align="left" class="field">
												Claim Status
											</td>
											<td valign="bottom">											
												<select name="industrialInjuryClaimStatus" class="entry">
																	<option value="">select</option>
																	<option value="open" <%if ("open".equals(person.getPersonMisc().getIndustrialInjuryClaimStatus())) {%>selected<%}%>>Open</option>
																	<option value="closed" <%if ("closed".equals(person.getPersonMisc().getIndustrialInjuryClaimStatus())) {%>selected<%}%>>Closed</option>
												</select>
											</td>
											
										</tr>
										
										</table>
										<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="250" align="left" class="field">
												Do you currently have a physical disability?<%=required%>
											</td>
											<td valign="bottom">											
												<select name="disabilityFlag" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getDisabilityFlag())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getDisabilityFlag())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
										
									    </table>
									    <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   	<tr>
											<td colspan="2" class="field">
												<i>If you answered yes to the question above, please explain.</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="field">											
												<textarea name="disabilityDetails"><%=person.getPersonMisc().getDisabilityDetails()%></textarea>
											</td>
											
										</tr>
										</table>
										
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								  
										<tr>
											<td width="150" class="field">
												Date of last examination?</i>
											</td>
										
											<td class="field">											
												<input name="examinationDate" value="<%=person.getPersonMisc().getExaminationDate()%>" size="10" maxlength="10" class="entry">
											</td>
											
										</tr>
									</table>
									
								   <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								  
										<tr>
											<td width="80" class="field">
												Physician Name
											</td>
										
											<td class="field">											
												<input name="physician" value="<%=person.getPersonMisc().getPhysician()%>" size="30" maxlength="30" class="entry">
											</td>
											
										</tr>
										<tr>
											<td class="field">
												Address
											</td>
										
											<td class="field">											
												<input name="address" value="<%=person.getPersonMisc().getAddress()%>" size="60" maxlength="60" class="entry">
											</td>
											
										</tr>
									</table>
									
									
									<br><br>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
								   			<td class="instructions">
								   				<b>Check and answer all the following questions that apply to you.</b><br><br>
								   			</td>
								   		</tr>
										<tr>
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="0">
								   		<tr>
											<td width="140" align="left" class="checkboxGrid">
												<b>Arthritis or Rheumatism</b>
										    </td>
										    <td width="100" align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt1" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt1())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt1" value="No"  <%if ("No".equals(person.getPersonMisc().getMedicalOpt1())) {%>checked="checked"<%}%>>N
											</td>
										    <td width="140" align="left" class="checkboxGrid">
												<b>Polio</b>
										    </td>
										     <td width="100" align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt2" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt2())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt2" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt2())) {%>checked="checked"<%}%>>N
											</td>
										    <td width="140" align="left" class="checkboxGrid">
												<b>Amputations</b>
										    </td>
										     <td width="100"  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt3" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt3())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt3" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt3())) {%>checked="checked"<%}%>>N
											</td>
										</tr>
								   		<tr>
											<td  align="left" class="checkboxGrid">
												<b>Dizziness or Fainting Spells</b>
										    </td>
										    <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt4" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt4())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt4" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt4())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Back Surgery</b>
										    </td>
										     <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt5" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt5())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt5" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt5())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Any Permanent Disabilities</b>
										    </td>
										     <td   align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt6" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt6())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt6" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt6())) {%>checked="checked"<%}%>>N
											</td>
										</tr>
									   	<tr>
											<td  align="left" class="checkboxGrid">
												<b>Head Injury</b>
										    </td>
										    <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt7" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt7())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt7" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt7())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Diabetes</b>
										    </td>
										     <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt8" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt8())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt8" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt8())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Hepatitis</b>
										    </td>
										     <td   align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt9" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt9())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt9" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt9())) {%>checked="checked"<%}%>>N
											</td>
										</tr>		
										<tr>
											<td  align="left" class="checkboxGrid">
												<b>High Blood Pressure</b>
										    </td>
										    <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt10" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt10())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt10" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt10())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Epilepsy</b>
										    </td>
										     <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt11" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt11())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt11" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt11())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Cancer</b>
										    </td>
										     <td   align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt12" value="Yes" <%if ("Yes".equals(person.getPersonMisc().getMedicalOpt12())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt12" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt12())) {%>checked="checked"<%}%>>N
											</td>
										</tr>	
										<tr>
											<td  align="left" class="checkboxGrid">
												<b>Kidney or Bladder Trouble</b>
										    </td>
										    <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt13" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt13())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt13" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt13())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Asthma</b>
										    </td>
										     <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt14" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt14())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt14" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt14())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>HIV</b>
										    </td>
										     <td   align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt15" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt15())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt15" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt15())) {%>checked="checked"<%}%>>N
											</td>
										</tr>
										<tr>
											<td  align="left" class="checkboxGrid">
												<b>Phlebitis</b>
										    </td>
										    <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt16" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt16())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt16" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt16())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Varicose Veins</b>
										    </td>
										     <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt17" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt17())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt17" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt17())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Heart Problems</b>
										    </td>
										     <td   align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt18" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt18())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt18" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt18())) {%>checked="checked"<%}%>>N
											</td>
										</tr>	
										<tr>
											<td  align="left" class="checkboxGrid">
												<b>Knee Injury</b>
										    </td>
										    <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt19" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt19())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt19" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt19())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Back Injury</b>
										    </td>
										     <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt20" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt20())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt20" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt20())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Hernia/Rupture</b>
										    </td>
										     <td   align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt21" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt21())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt21" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt21())) {%>checked="checked"<%}%>>N
											</td>
										</tr>
										<tr>
											<td  align="left" class="checkboxGrid">
												<b>AIDS</b>
										    </td>
										    <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt22" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt22())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt22" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt22())) {%>checked="checked"<%}%>>N
											</td>
										    <td  align="left" class="checkboxGrid">
												<b>Loss Of Hearing</b>
										    </td>
										     <td  align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt23" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt23())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt23" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt23())) {%>checked="checked"<%}%>>N
											</td>
										    <td align="left" class="checkboxGrid">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Which Side?
											</td>
											<td align="left" class="checkboxGrid">
												<input class="entry" value="<%=person.getPersonMisc().getHerniaSide()%>" name="herniaSide" size="10" maxlength="10"/>
										    </td>
										     
										</tr>	
										<tr>
											<td width="170" align="left" class="checkboxGrid">
												<b>Herpes</b>
										    </td>
										    <td width="100" align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt24" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt24())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt24" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt24())) {%>checked="checked"<%}%>>N
											</td>
										    <td width="170" align="left" class="checkboxGrid">
												<b>Loss Of Sight</b>
										    </td>
										     <td width="100" align="left" class="checkboxGrid">
												<input type="checkbox" name="medicalOpt25" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getMedicalOpt25())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="medicalOpt25" value="No" <%if ("No".equals(person.getPersonMisc().getMedicalOpt25())) {%>checked="checked"<%}%>>N
											</td>
										    <td align="left" class="checkboxGrid">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Was it operated on?
											</td>
											<td align="left" class="checkboxGrid">
												<input type="checkbox" name="herniaOperationFlag" value="Yes"<%if ("Yes".equals(person.getPersonMisc().getHerniaOperationFlag())) {%>checked="checked"<%}%>>Y
												<input type="checkbox" name="herniaOperationFlag" value="No" <%if ("No".equals(person.getPersonMisc().getHerniaOperationFlag())) {%>checked="checked"<%}%>>N
										    </td>
										     
										</tr>										
										<tr>
											<td colspan="6" class="field">
												<i>For any "Yes" answers above, explain details.</i>
											</td>
										</tr>
										<tr>
											<td colspan="6" class="field">											
												<textarea name="medicalConditionDetails"><%=person.getPersonMisc().getMedicalConditionDetails()%></textarea>
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
						
							
												
						<input type="hidden" name="page" value="06"/>
	
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


