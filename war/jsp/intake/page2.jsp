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
								<td><img src="<%=request.getContextPath()%>/images/titles/personal-info.jpg"/></td>
							</tr>
							<tr>
								<td >
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
										   		<tr>
													<td width="110" class="field" >Date of Birth<%=required%></td>
													<td class="field" >Age<%=required%></td>
												</tr>
												<tr>
													<td><input value="<%=person.getDateOfBirth()%>" name="dob" maxlength="10" size="10" class="entry"></td>
													<td>	
														<select name="age" class="entry">
															<% for (int i=18;i<90;i++) { %>
															<option value="<%=i%>"<% if ((""+i).equals(person.getAge())) {%>selected<%}%>><%=i%></option>
															<% } %>
														</select>
													</td>
												</tr>
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
										   		<tr>
													<td width="90" class="field" >Height<%=required%></td>
													<td width="90" class="field" >Weight<%=required%></td>
													<td width="100" class="field" >Eye Color<%=required%></td>
													<td width="100" class="field" >Hair Color<%=required%></td>
												</tr>
												<tr>
													<td >
														<select name="height" class="entry">
															<option value="">select</option>
															<% for (int f=5;f<7;f++) 
																for (int i=0;i<12;i++) { %>
															<option value="<%=f%>'<%=i%>"<% if ((f+"'"+i).equals(person.getHeight())) {%>selected<%}%>><%=f%>'<%=i%></option>
															<% } %>
														</select>
													</td>
													<td>
														<select name="weight" class="entry">
															<option value="">select</option>
															<% for (int f=100;f<500;f++) { %>
																<option value="<%=f%>lbs"<% if ((f+"lbs").equals(person.getWeight())) {%>selected<%}%>><%=f%>lbs</option>
															<% } %>
														</select>
													</td>
													<td>	
														<select name="eyeColor" class="entry">
															<option value="">select</option>
															<option value="Blue"<% if ("Blue".equals(person.getEyeColor())) {%>selected<%}%>>Blue</option>
															<option value="Brown"<% if ("Brown".equals(person.getEyeColor())) {%>selected<%}%>>Brown</option>
															<option value="Green"<% if ("Breen".equals(person.getEyeColor())) {%>selected<%}%>>Green</option>
															<option value="Hazel"<% if ("Hazel".equals(person.getEyeColor())) {%>selected<%}%>>Hazel</option>
														</select>
													</td>
													<td>	
														<select name="hairColor" class="entry">
															<option value="">select</option>
															<option value="Brown"<% if ("Brown".equals(person.getHairColor())) {%>selected<%}%>>Brown</option>
                                                            <option value="Black"<% if ("Black".equals(person.getHairColor())) {%>selected<%}%>>Black</option>
                                                            <option value="Blonde"<% if ("Blonde".equals(person.getHairColor())) {%>selected<%}%>>Black</option>
                                                            <option value="Blonde/Strawberry"<% if ("Blonde/Strawberry".equals(person.getHairColor())) {%>selected<%}%>>Blonde/Strawberry</option>
                                                            <option value="Gray or Partially Gray"<% if ("Gray or Partially Gray".equals(person.getHairColor())) {%>selected<%}%>>Gray or Partially Gray</option>
                                                            <option value="Partly or Completely Bald"<% if ("Partly or Completely Bald".equals(person.getHairColor())) {%>selected<%}%>>Partly or Completely Bald</option>
                                                            <option value="Red/Auburn"<% if ("Red/Auburn".equals(person.getHairColor())) {%>selected<%}%>>Red/Auburn</option>
                                                            <option value="Sandy"<% if ("Sandy".equals(person.getHairColor())) {%>selected<%}%>>Sandy</option>
                                                            <option value="Salt & Pepper"<% if ("Salt & Pepper".equals(person.getHairColor())) {%>selected<%}%>>Salt & Pepper</option>
                                                            <option value="Shaved"<% if ("Shaved".equals(person.getHairColor())) {%>selected<%}%>>Shaved</option>
                                                            <option value="White"<% if ("White".equals(person.getHairColor())) {%>selected<%}%>>White</option>
                                                       </select>
													</td>
												</tr>
											</table>
											<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
									
												<tr>
													<td class="field" >Ethnicity<%=required%></td>
												</tr>
												<tr>
													<td>	
														<select name="ethnicity" class="entry">
														        <option value="">select</option>
															    <option value="American Indian/Alaskan Native" <% if ("American Indian/Alaskan Native".equals(person.getEthnicity())) {%>selected<%}%>>American Indian/Alaskan Native</option>
															    <option value="Asian/Pacific Islander" <% if ("Asian/Pacific Islander".equals(person.getEthnicity())) {%>selected<%}%>>Asian/Pacific Islander</option>
															    <option value="Black, not Hispanic" <% if ("Black, not Hispanic".equals(person.getEthnicity())) {%>selected<%}%>>Black, not Hispanic</option>
															    <option value="Hispanic" <% if ("Hispanic".equals(person.getEthnicity())) {%>selected<%}%>>Hispanic</option>
															    <option value="White, not Hispanic" <% if ("White, not Hispanic".equals(person.getEthnicity())) {%>selected<%}%>>White, not Hispanic</option>
														</select>
													</td>
												</tr>
											</table>
											<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
									
												<tr>
													<td class="field" >Marital Status<%=required%></td>
												</tr>
												<tr>
													<td>	
														<select name="maritalStatus" class="entry">
														        <option value="">select</option>
															    <option value="Never Married" <% if ("Never Married".equals(person.getMaritalStatus())) {%>selected<%}%>>Never Married</option>
															    <option value="Married" <% if ("Married".equals(person.getMaritalStatus())) {%>selected<%}%>>Married</option>
															    <option value="Divorced" <% if ("Divorced".equals(person.getMaritalStatus())) {%>selected<%}%>>Divorced</option>
															    <option value="Separated" <% if ("Separated".equals(person.getMaritalStatus())) {%>selected<%}%>>Separated</option>
															    <option value="Spouse Deceased" <% if ("Spouse Deceased".equals(person.getMaritalStatus())) {%>selected<%}%>>Spouse Deceased</option>
															    <option value="Lives with Girlfriend" <% if ("Lives with Girlfriend".equals(person.getMaritalStatus())) {%>selected<%}%>>Lives with Girlfriend</option>
															    <option value="Lives with Boyfriend" <% if ("Lives with Boyfriend".equals(person.getMaritalStatus())) {%>selected<%}%>>Lives with Boyfriend</option>
															    <option value="Remarried" <% if ("Remarried".equals(person.getMaritalStatus())) {%>selected<%}%>>Remarried</option>
															    <option value="Re-divorced" <% if ("Re-divorced".equals(person.getMaritalStatus())) {%>selected<%}%>>Re-divorced</option>
													    </select>
													</td>
												</tr>
											</table>
											
											<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
									
												<tr>
													<td class="field" >Where do you live?<%=required%></td>
												</tr>
												<tr>
													<td>	
														<select name="homeLocation" class="entry">
														        <option value="">select</option>
															    <option value="Own Home" <% if ("Own Home".equals(person.getHomeLocation())) {%>selected<%}%>>Own Home</option>
															    <option value="Parent's Home" <% if ("Parent's Home".equals(person.getHomeLocation())) {%>selected<%}%>>Parent's Home</option>
															    <option value="Friend's Home" <% if ("Friend's Home".equals(person.getHomeLocation())) {%>selected<%}%>>Friend's Home</option>
															    <option value="Halfway Home" <% if ("Halfway Home".equals(person.getHomeLocation())) {%>selected<%}%>>Halfway Home</option>
															    <option value="Hotel" <% if ("Hotel".equals(person.getHomeLocation())) {%>selected<%}%>>Hotel</option>
															    <option value="Rehab Program" <% if ("Rehab Program".equals(person.getHomeLocation())) {%>selected<%}%>>Rehab Program</option>
															    <option value="Vehicle" <% if ("Vehicle".equals(person.getHomeLocation())) {%>selected<%}%>>Vehicle</option>
															    <option value="On Street" <% if ("On Street".equals(person.getHomeLocation())) {%>selected<%}%>>On Street</option>
															    <option value="Other" <% if ("Other".equals(person.getHomeLocation())) {%>selected<%}%>>Other</option>
													    </select>
													</td>
												</tr>
											</table>
											<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
									
												<tr>
													<td class="field" width="150" >Highest Level of Education<%=required%></td>
													<td></td>
												</tr>
												<tr>
													<td>	
														<select name="education" class="entry">
														        <option value="">select</option>
															    <option value="Elementary" <% if ("Elementary".equals(person.getEducationLevel())) {%>selected<%}%>>Elementary</option>
															    <option value="High School" <% if ("High School".equals(person.getEducationLevel())) {%>selected<%}%>>High School</option>
															    <option value="GED" <% if ("GED".equals(person.getEducationLevel())) {%>selected<%}%>>GED</option>
															    <option value="Bachelor's Degree" <% if ("Bachelor's Degree".equals(person.getEducationLevel())) {%>selected<%}%>>Bachelor's Degree</option>
															    <option value="Master's Degree" <% if ("Master's Degree".equals(person.getEducationLevel())) {%>selected<%}%>>Master's Degree</option>
															    <option value="Doctorate Degree" <% if ("Doctorate Degree".equals(person.getEducationLevel())) {%>selected<%}%>>Doctorate Degree</option>
															    <option value="Associates Degree" <% if ("Associates Degree".equals(person.getEducationLevel())) {%>selected<%}%>>Associates Degree</option>
															    <option value="Vocational School" <% if ("Vocational School".equals(person.getEducationLevel())) {%>selected<%}%>>Vocational School</option>
													    </select>
													</td>
												</tr>
												<tr>
													<td align="left">
														<i>Did you graduate?<%=required %></i>
														<input type="radio" name="graduate" value="Yes" <% if ("Yes".equals(person.getGraduateFlag())) {%>checked="checked"<%}%>/>Yes
														<input type="radio" name="graduate" value="No" <% if ("No".equals(person.getGraduateFlag())) {%>checked="checked"<%}%> />No
													</td>
														
												</tr>
												<tr>
													<td align="left">
														<i>Do you have your transcripts?<%=required %></i>
														<input type="radio" name="transcripts" value="Yes" <% if ("Yes".equals(person.getTranscriptsFlag())) {%>checked="checked"<%}%>/>Yes
														<input type="radio" name="transcripts" value="No" <% if ("No".equals(person.getTranscriptsFlag())) {%>checked="checked"<%}%> />No
													</td>
														
												</tr>
											</table>
											
											<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
									
												<tr>
													<td class="field" width="150" >Can you read English?<%=required%></td>
													<td valign="bottom">	
														<input type="radio" name="readEnglish" value="Yes" <% if ("Yes".equals(person.getEnglishReadFlag())) {%>checked="checked"<%}%>/>Yes
														<input type="radio" name="readEnglish" value="No" <% if ("No".equals(person.getEnglishReadFlag())) {%>checked="checked"<%}%>/>No
													</td>
												</tr>
												
											</table>
											
											<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
									
												<tr>
													<td class="field" width="150" >Can you speak English?<%=required%></td>
													<td valign="bottom">	
														<input type="radio" name="speakEnglish" value="Yes" <% if ("Yes".equals(person.getEnglishSpeakFlag())) {%>checked="checked"<%}%>/>Yes
														<input type="radio" name="speakEnglish" value="No" <% if ("No".equals(person.getEnglishSpeakFlag())) {%>checked="checked"<%}%>/>No
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
						
						<input type="hidden" name="page" value="02"/>
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


