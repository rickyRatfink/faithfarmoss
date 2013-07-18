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
						<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td><img src="<%=request.getContextPath()%>/images/titles/abuse-info.jpg"/><br></td>
							</tr>
							<tr>
								<td >
									<br>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   	<tr>
								   		<td width="100" class="checkboxGridHeading">Drug</td>
								   		<td width="50" class="checkboxGridHeading"><b>Years Used</b></td>
								   		<td width="50" class="checkboxGridHeading"><b>Date Last Used</b></td>
								   		<td width="10" class="checkboxGridHeading"></td>
								   		<td width="100" class="checkboxGridHeading"><b>Drug</b></td>
								   		<td width="50" class="checkboxGridHeading"><b>Years Used</b></td>
								   		<td width="50" class="checkboxGridHeading"><b>Date Last Used</b></td>
								   	
								   	</tr>
								   	<tr>
								   		<td class="checkboxGrid">Alcohol</td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="alcoholYearsUsed" value="<%=person.getPersonMisc().getAlcoholYearsUsed()%>" size="2" maxlength="2"></td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="alcoholLastUsed" value="<%=person.getPersonMisc().getAlcoholLastUsed()%>" size="10" maxlength="10"></td>
								   		<td class="checkboxGrid"></td>
								   		<td class="checkboxGrid">Speed</td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="speedYearsUsed" value="<%=person.getPersonMisc().getSpeedYearsUsed()%>" size="2" maxlength="2"></td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="speedLastUsed" value="<%=person.getPersonMisc().getSpeedLastUsed()%>" size="10" maxlength="10"></td>
								   	</tr>
								   	
								   	<tr>
								   		<td class="checkboxGrid">Cocaine</td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="cocaineYearsUsed" value="<%=person.getPersonMisc().getCocaineYearsUsed()%>" size="2" maxlength="2"></td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="cocaineLastUsed" value="<%=person.getPersonMisc().getCocaineLastUsed()%>" size="10" maxlength="10"></td>
								   		<td class="checkboxGrid"></td>
								   		<td class="checkboxGrid">Heroin</td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="heroinYearsUsed" value="<%=person.getPersonMisc().getHeroinYearsUsed()%>" size="2" maxlength="2"></td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="heroinLastUsed" value="<%=person.getPersonMisc().getHeroinLastUsed()%>" size="10" maxlength="10"></td>
								   	</tr>
				
								   	<tr>
								   		<td class="checkboxGrid">Marijuana</td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="marijuanaYearsUsed" value="<%=person.getPersonMisc().getMarijuanaYearsUsed()%>" size="2" maxlength="2"></td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="marijuanaLastUsed" value="<%=person.getPersonMisc().getMarijuanaLastUsed()%>" size="10" maxlength="10"></td>
								   		<td class="checkboxGrid"></td>
								   		<td class="checkboxGrid">Xanax</td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="xanaxYearsUsed" value="<%=person.getPersonMisc().getXanaxYearsUsed()%>" size="2" maxlength="2"></td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="xanaxLastUsed" value="<%=person.getPersonMisc().getXanaxLastUsed()%>" size="10" maxlength="10"></td>
								   	</tr>
					
								   	<tr>
								   		<td class="checkboxGrid">Oxicoden</td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="oxicodenYearsUsed" value="<%=person.getPersonMisc().getOxicodenYearsUsed()%>" size="2" maxlength="2"></td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="oxicodenLastUsed" value="<%=person.getPersonMisc().getOxicodenLastUsed()%>" size="10" maxlength="10"></td>
								   		<td class="checkboxGrid"></td>
								   		<td class="checkboxGrid">Other</td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="otherYearsUsed" value="<%=person.getPersonMisc().getOtherYearsUsed()%>" size="2" maxlength="2"></td>
								   		<td class="checkboxGrid"><input class="gridEntry" name="otherLastUsed" value="<%=person.getPersonMisc().getOtherLastUsed()%>" size="10" maxlength="10"></td>
								   	</tr>
									
									<tr>
											<td colspan="7" class="field">		
											<br>
											<i>Describe other										
												<textarea name="otherDrug"><%=person.getPersonMisc().getOtherDrug()%></textarea>
											</td>
											
										</tr>
									</table>
									
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td align="left" class="field">
												Longest time sober in the last year?
											</td>
											<td align="left" class="field">
												Longest time sober in the past 3 years?
											</td>
											
										</tr>
										<tr>
											<td>											
												<input class="entry" name="sober1Years" value="<%=person.getPersonMisc().getSober1Years()%>" size=15" maxlength="15"/>
											</td>
											<td>											
												<input class="entry" name="sober3Years" value="<%=person.getPersonMisc().getSober3Years()%>" size=15" maxlength="15"/>
											</td>
										</tr>
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td align="left" class="field">
												Usage Pattern
											</td>
										</tr>
										<tr>
											<td>
												<select name="usagePattern" class="entry">
															<option value="">select</option>
															<option value="constantly" <% if ("constantly".equals(person.getPersonMisc().getUsagePattern())) {%>selected<%}%>>constantly</option>
															<option value="weekends" <% if ("weekends".equals(person.getPersonMisc().getUsagePattern())) {%>selected<%}%>>weekends</option>
															<option value="special occasions" <% if ("special occasions".equals(person.getPersonMisc().getUsagePattern())) {%>selected<%}%>>special occasions</option>
															<option value="when available" <% if ("when available".equals(person.getPersonMisc().getUsagePattern())) {%>selected<%}%>>when available</option>
															<option value="when things get tough" <% if ("when things get tough".equals(person.getPersonMisc().getUsagePattern())) {%>selected<%}%>>when things get tough</option>
															<option value="on a week/off a week" <% if ("on a week/off a week".equals(person.getPersonMisc().getUsagePattern())) {%>selected<%}%>>on a week/off a week</option>
															
												</select>
											</td>
												
										</tr>
										
										
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td width="150" align="left" class="field">
												Quantity used per week?
											</td>
											<td align="left" class="field">
												Quantity used per week 2 years ago?
											</td>
											
										</tr>
										<tr>
											<td>											
												<input class="entry" name="quantityPerWeek" value="<%=person.getPersonMisc().getQuantityPerWeek()%>" size=15" maxlength="15"/>
											</td>
											<td>											
												<input class="entry" name="quantity2Years" value="<%=person.getPersonMisc().getQuantity2Years()%>" size=15" maxlength="15"/>
											</td>
										</tr>
									</table>
	
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td align="left" class="field">
												Losses due to usage
											</td>
										</tr>
										<tr>
											<td>
												<select name="usageLosses" class="entry">
															<option value="">select</option>
															<option value="Arrests" <% if ("Arrests".equals(person.getPersonMisc().getUsageLosses())) {%>selected<%}%>>Arrests</option>
															<option value="DUIs" <% if ("DUIs".equals(person.getPersonMisc().getUsageLosses())) {%>selected<%}%>>DUIs</option>
															<option value="Friends" <% if ("Friends".equals(person.getPersonMisc().getUsageLosses())) {%>selected<%}%>>Friends</option>
															<option value="Health" <% if ("Health".equals(person.getPersonMisc().getUsageLosses())) {%>selected<%}%>>Health</option>
															<option value="Heavy Debt" <% if ("Heavy Debt".equals(person.getPersonMisc().getUsageLosses())) {%>selected<%}%>>Heavy Debt</option>
															<option value="Incarceration" <% if ("Incarceration".equals(person.getPersonMisc().getUsageLosses())) {%>selected<%}%>>Incarceration</option>
															<option value="Job" <% if ("Job".equals(person.getPersonMisc().getUsageLosses())) {%>selected<%}%>>Job</option>
															<option value="Marriage" <% if ("Marriage".equals(person.getPersonMisc().getUsageLosses())) {%>selected<%}%>>Marriage</option>
															<option value="Possessions" <% if ("Possessions".equals(person.getPersonMisc().getUsageLosses())) {%>selected<%}%>>Possessions</option>
												</select>
											</td>
												
										</tr>
									</table>
										
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td align="left" class="field">
												Physical effects of abuse
											</td>
										</tr>
										<tr>
											<td>
												<select name="abusePhysicalEffects" class="entry">
															<option value="">select</option>
															<option value="Motivational loss" <% if ("Motivational loss".equals(person.getPersonMisc().getAbusePhysicalEffects())) {%>selected<%}%>>Motivational loss</option>
															<option value="Shakes/Convulsions" <% if ("Shakes/Convulsions".equals(person.getPersonMisc().getAbusePhysicalEffects())) {%>selected<%}%>>Shakes/Convulsions</option>
															<option value="Memory Loss" <% if ("Memory Loss".equals(person.getPersonMisc().getAbusePhysicalEffects())) {%>selected<%}%>>Memory Loss</option>
															<option value="Incoherent Thinking" <% if ("Incoherent Thinking".equals(person.getPersonMisc().getAbusePhysicalEffects())) {%>selected<%}%>>Incoherent Thinking</option>
															<option value="Organ Problems" <% if ("Organ Problems".equals(person.getPersonMisc().getAbusePhysicalEffects())) {%>selected<%}%>>Organ Problems</option>
												</select>
											</td>
												
										</tr>
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td align="left" class="field">
												<i>Is your spouse addicted to drugs or alcohol?</i>
												<input type="radio" name="spouseAddicted" value="Yes" <% if ("Yes".equals(person.getPersonMisc().getSpouseAddicted())) {%>checked="checked"<%}%>/>Yes
												<input type="radio" name="spouseAddicted" value="No" <% if ("No".equals(person.getPersonMisc().getSpouseAddicted())) {%>checked="checked"<%}%>/>No														
											</td>
											
										</tr>
										<tr>
											<td align="left" class="field">
												<i>Are any of your family members addicted to drugs or alcohol?</i>
												<input type="radio" name="familyAddicted" value="Yes" <% if ("Yes".equals(person.getPersonMisc().getFamilyAddicted())) {%>checked="checked"<%}%>/>Yes
												<input type="radio" name="familyAddicted" value="No" <% if ("No".equals(person.getPersonMisc().getFamilyAddicted())) {%>checked="checked"<%}%>/>No														
											</td>
										</tr>
							</table>
						</td>
					</tr>
					<tr>				
						<td height="80" valign="bottom"><img src="<%=request.getContextPath()%>/images/titles/rehab-info.jpg"/><br><br></td>
					</tr>
					<tr><td>			
								    <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td align="left" class="field">
												<i>Have you ever attended Alcoholics Anonymous?</i>
												<input type="radio" name="attendAA" value="Yes"  <% if ("Yes".equals(person.getPersonMisc().getAttendAA())) {%>checked="checked"<%}%>/>Yes
												<input type="radio" name="attendAA" value="No"  <% if ("No".equals(person.getPersonMisc().getAttendAA())) {%>checked="checked"<%}%>/>No														
											</td>
											
										</tr>
										<tr>
											<td align="left" class="field">
												<i>Have you ever attended Narcotics Anonymous?</i>
												<input type="radio" name="attendNA" value="Yes"  <% if ("Yes".equals(person.getPersonMisc().getAttendNA())) {%>checked="checked"<%}%>/>Yes
												<input type="radio" name="attendNA" value="No"  <% if ("No".equals(person.getPersonMisc().getAttendNA())) {%>checked="checked"<%}%>/>No														
											</td>
										</tr>
										<tr>
											<td align="left" class="field">
												Years Attended
											</td>
										</tr>
										<tr>
											<td align="left">
												<input class="entry" name="yearsAttended" value="<%=person.getPersonMisc().getYearsAttended()%>"/>
											</td>
										</tr>
										</table>
									
										<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td align="left" class="field" colspan="2">
												<i>If you have attended another Faith Farm please specify which location and year attended.</i>
											</td>											
										</tr>
										<tr>
											<td align="left" class="field" width="120">
												Location
											</td>
											<td align="left" class="field">
												Year Attended
											</td>
										</tr>
										<tr>
											<td>
												<select name="previousFaithFarm" class="entry">
															<option value="">select</option>
															<option value="Boynton Beach" <% if ("Boynton Beach".equals(person.getPersonMisc().getPreviousFaithFarm())) {%>selected<%}%>>Boynton Beach</option>
															<option value="Fort Lauderdale" <% if ("Fort Lauderdale".equals(person.getPersonMisc().getPreviousFaithFarm())) {%>selected<%}%>>Fort Lauderdale</option>
															<option value="Okeechobee" <% if ("Okeechobee".equals(person.getPersonMisc().getPreviousFaithFarm())) {%>selected<%}%>>Okeechobee</option>
												</select>
											</td>
											<td>											
													<input class="entry" name="faithFarmYear" value="<%=person.getPersonMisc().getFaithFarmYear()%>"/>
											
											</td>												
										</tr>
									</table>
								
					</td></tr>
					
												
							<tr>
								<td  width="50%" height="60" valign="bottom" align="left">
									<input type="submit" name="action" value="Back" class="buttonBack">
								</td>
								<td  width="50%" height="60" valign="bottom" align="right">
									<input type="submit" name="action" value="Next" class="buttonNext">
								</td>
							</tr>
											
							</table>
						
							
												
						<input type="hidden" name="page" value="04"/>
	
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


