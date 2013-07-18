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
								<td><img src="<%=request.getContextPath()%>/images/titles/personal-info.jpg"/><br></td>
							</tr>
							<tr>
								<td >
									
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td align="left" class="field">
												Are you a US Veteran?<%=required%>
											</td>
											<td align="left" class="field">
												Branch of Service
											</td>
											<td align="left" class="field">
												Highest Rank
											</td>
											<td align="left" class="field">
												Length of Service
											</td>
										</tr>
										<tr>
											<td>											
												<select name="veteranStatus" class="entry">
															<option value="">select</option>
															<option value="Yes" <% if ("Yes".equals(person.getPersonMisc().getVeteranStatus())) {%>selected<%}%>>Yes</option>
															<option value="No" <% if ("No".equals(person.getPersonMisc().getVeteranStatus())) {%>selected<%}%>>No</option>
												</select>
											</td>
											<td>											
												<select name="branchOfService" class="entry">
															<option value="">select</option>
															<option value="Air Force" <% if ("Air Force".equals(person.getPersonMisc().getBranchOfService())) {%>selected<%}%>>Air Force</option>
															<option value="Army" <% if ("Army".equals(person.getPersonMisc().getBranchOfService())) {%>selected<%}%>>Army</option>
															<option value="Coast Guard" <% if ("Coast Guard".equals(person.getPersonMisc().getBranchOfService())) {%>selected<%}%>>Coast Guard</option>
															<option value="Marines" <% if ("Marines".equals(person.getPersonMisc().getBranchOfService())) {%>selected<%}%>>Marines</option>
															<option value="Navy" <% if ("Navy".equals(person.getPersonMisc().getBranchOfService())) {%>selected<%}%>>Navy</option>
												</select>
											</td>
											<td>											
												<input name="rank" value="<%=person.getPersonMisc().getRank()%>" size="15" maxlength="15" class="entry">
											</td>
											<td>	
                                            	<input name="lengthOfService" value="<%=person.getPersonMisc().getLengthOfService()%>" size="25" maxlength="25" class="entry">										
											</td>
												
										</tr>
										
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   	<tr>
											<td width="270" align="left" class="field">
												Have you registered with the selective service?<%=required %>
											</td>
											<td valign="bottom">											
												<select name="selectiveService" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <%if ("Yes".equals(person.getPersonMisc().getSelectiveService())) {%>selected<%}%>>Yes</option>
																	<option value="No" <%if ("No".equals(person.getPersonMisc().getSelectiveService())) {%>selected<%}%>>No</option>
												</select>
											</td>
											
										</tr>
									</table>
									
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td align="left" class="field">
											</br>
												Check government benefits that you currently receive:</br>
												<i><% String benefit=person.getPersonMisc().getGovernmentBenefits();%>
												<input type="checkbox" name="benefit0" value="none" <% if (benefit.contains("none")) {%>checked<%}%>/>None
												<input type="checkbox" name="benefit1" value="disability" <% if (benefit.contains("disability")) {%>checked<%}%>/>Disability
												<input type="checkbox" name="benefit2" value="food stamp assistance" <% if (benefit.contains("food stamp assistance")) {%>checked<%}%>/>Food Stamp Assistance
												<input type="checkbox" name="benefit4" value="social security" <% if (benefit.contains("social security")) {%>checked<%}%>/>Social Security
												</br>
												<input type="checkbox" name="benefit5" value="unemployment" <% if (benefit.contains("unemployment")) {%>checked<%}%>/>Unemployment
												<input type="checkbox" name="benefit6" value="va" <% if (benefit.contains("va")) {%>checked<%}%>/>Veteran's Assistance
												<input type="checkbox" name="benefit7" value="worker's compensation" <% if (benefit.contains("worker's compensation")) {%>checked<%}%>/>Worker's Compensation
												</i>
												</br></br>
												
											
												
											</td>
												
										</tr>
									</table>
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td width="190" align="left" class="field">
												Do you have a valid driver's license?<%=required%>
											</td>
											<td width="120" align="left" class="field">
												State Issued
											</td>
											<td align="left" class="field">
												DL#
											</td>
											<td align="left" class="field">
												Expiration Date
											</td>
											
										</tr>
										<tr>
											<td >											
												<select name="licenseStatus" class="entry">
															<option value="">select</option>
															<option value="Yes"  <% if ("Yes".equals(person.getPersonMisc().getDriversLicenseStatus())) {%>selected<%}%>>Yes</option>
															<option value="No"  <% if ("No".equals(person.getPersonMisc().getDriversLicenseStatus())) {%>selected<%}%>>No</option>
												</select>
											</td>
											<td>
												 <%	ArrayList ddl = (ArrayList)session.getAttribute("ddlStates"); %>
                                             		<select name="driversLicenseState" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) { %>
                                                              <option value="<%=ddl.get(j)%>" <% if (person.getPersonMisc().getDriversLicenseState().equals(ddl.get(j))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>
											</td>
											<td>	
												<input name="licenseNumber" value="<%=person.getPersonMisc().getDriversLicenseNumber()%>" size="15" maxlength="15" class="entry">
											</td>
											<td>	
												<input name="driversLicenseExpirationDate" value="<%=person.getPersonMisc().getDriversLicenseExpirationDate()%>" size="12" maxlength="10" class="tcal">
											</td>
											
										</tr>
									</table>
									

									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td width="230" align="left" class="field">
												Do you have a copy of your birth certificate?<%=required%>
											</td>
											<td width="140" align="left" class="field">
												County of Birth
											</td>
											<td align="left" class="field">
												State of Birth
											</td>
										</tr>
										<tr>
											<td >											
												<select name="birthCertFlag" class="entry">
															<option value="">select</option>
															<option value="Yes"  <% if ("Yes".equals(person.getPersonMisc().getBirthCertFlag())) {%>selected<%}%>>Yes</option>
															<option value="No"  <% if ("No".equals(person.getPersonMisc().getBirthCertFlag())) {%>selected<%}%>>No</option>
												</select>
											</td>
											<td>	
												<input name="birthCertCounty" value="<%=person.getPersonMisc().getBirthCertCounty()%>" size="25" maxlength="25" class="entry">
											</td>
											<td>
												 <%	ddl = (ArrayList)session.getAttribute("ddlStates"); %>
                                             		<select name="birthCertState" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) { %>
                                                              <option value="<%=ddl.get(j)%>" <% if (person.getPersonMisc().getBirthCertState().equals(ddl.get(j))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>
											</td>
										</tr>
									</table>
									


									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td width="190" align="left" class="field">
												Do you have a valid state Id?<%=required%>
											</td>
											<td width="190" align="left" class="field">
												State
											</td>
											<td width="190" align="left" class="field">
												Expiration Date
											</td>
										</tr>
										<tr>
											<td width="190" align="left" class="field">
												<select name="stateIdentificationFlag" class="entry">
															<option value="">select</option>
															<option value="Yes"  <% if ("Yes".equals(person.getPersonMisc().getStateIdentificationFlag())) {%>selected<%}%>>Yes</option>
															<option value="No"  <% if ("No".equals(person.getPersonMisc().getStateIdentificationFlag())) {%>selected<%}%>>No</option>
												</select>
											</td>
										
											<td>
												 <%	 ddl = (ArrayList)session.getAttribute("ddlStates"); %>
                                             		<select name="stateIdentificationState" class="entry">
                                                    	<option value=""></option>
                                                        <% if (ddl!=null) {
                                                        	for (int j=0;j<ddl.size();j++) { %>
                                                              <option value="<%=ddl.get(j)%>" <% if (person.getPersonMisc().getStateIdentificationState().equals(ddl.get(j))) {%>selected<%}%>><%=ddl.get(j)%></option>
                                                           <% } %>
                                                        <% } %>
                                                    </select>
											</td>
										
											<td>	
												<input name="stateIdentificationExpirationDate" value="<%=person.getPersonMisc().getStateIdentificationExpirationDate()%>" size="12" maxlength="10" class="tcal">
											</td>
										</tr>
									</table>			
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td width="190" align="left" class="field">
												Do you have a library card?<%=required%>
											</td>
										</tr>
										<tr>
											<td width="190" align="left" class="field">
												<select name="libraryCard" class="entry">
															<option value="">select</option>
															<option value="Yes"  <% if ("Yes".equals(person.getPersonMisc().getLibraryCard())) {%>selected<%}%>>Yes</option>
															<option value="No"  <% if ("No".equals(person.getPersonMisc().getLibraryCard())) {%>selected<%}%>>No</option>
												</select>
											</td>
										</tr>
									</table>										
								</td>
							</tr>
							<tr>
								<td height="80" valign="bottom"><img src="<%=request.getContextPath()%>/images/titles/spiritual-info.jpg"/><br></td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
										   		<tr>
										   			<td class="field">											
														Religious Background<%=required%>
													</td>
										   		</tr>
												<tr>
										   			<td >											
														<select name="religion" class="entry">
															<option value="">select</option>
															<option value="none" <% if ("none".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>none</option>
															<option value="Christian" <% if ("Christian".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>Christian</option>
															<option value="Buddhist" <% if ("Buddhist".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>Buddhist</option>
															<option value="Catholic" <% if ("Catholic".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>Catholic</option>
															<option value="Hindu" <% if ("Hindu".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>Hindu</option>
															<option value="Jewish" <% if ("Jewish".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>Jewish</option>
															<option value="New Age" <% if ("New Age".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>New Age</option>
															<option value="Protestant" <% if ("Protestant".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>Protestant</option>
															<option value="Rastafarian" <% if ("Rastafarian".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>Rastafarian</option>
															<option value="Santaria" <% if ("Santaria".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>Santaria</option>
															<option value="Other" <% if ("Other".equals(person.getPersonMisc().getReligion())) {%>selected<%}%>>Other</option>
														</select>
													</td>
										   		</tr>										   		
												
												<tr>
													<td width="190" align="left" class="field">
														Describe your religious/spiritual experiences:
													</td>
												</tr>
												<tr>
													<td class="field">
														<textarea name="religousExperience"><%=person.getPersonMisc().getReligiousExperience()%></textarea>
													</td>
												</tr>
												
									</table>
								</td>
							</tr>				
							<tr>
								<td height="80" valign="bottom"><img src="<%=request.getContextPath()%>/images/titles/family-info.jpg"/><br></td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
										   		<tr>
										   			<td class="field">											
														Is your mother living?<%=required%>
													</td>
												</tr>
												<tr>
													<td>											
														<select name="motherLiving" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <% if ("Yes".equals(person.getPersonMisc().getMotherLiving())) {%>selected<%}%>>Yes</option>
																	<option value="No" <% if ("No".equals(person.getPersonMisc().getMotherLiving())) {%>selected<%}%>>No</option>
														</select>
													</td>
										   		</tr>
										   		<tr>
										   			<td class="field">											
														Is your father living?<%=required%>
													</td>
												</tr>
												<tr>
													<td>											
														<select name="fatherLiving" class="entry">
																	<option value="">select</option>
																	<option value="Yes" <% if ("Yes".equals(person.getPersonMisc().getFatherLiving())) {%>selected<%}%>>Yes</option>
																	<option value="No" <% if ("No".equals(person.getPersonMisc().getFatherLiving())) {%>selected<%}%>>No</option>
														</select>
													</td>
										   		</tr>
										   	</table>
										   	<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
										
										   		<tr>
										   			<td width="120" class="field">											
														Number of Brothers
													</td>
													<td width="120" class="field">											
														Number of Sisters
													</td>
													<td  width="320" class="field">											
														Number of Children
													</td>
												</tr>
												<tr>
													<td>	
														<input name="brothers" value="<%=person.getPersonMisc().getBrothers()%>" size="2" maxlength="2" class="entry">										
													</td>
													<td>	
														<input name="sisters" value="<%=person.getPersonMisc().getSisters()%>" size="2" maxlength="2" class="entry">										
													</td>
													<td>	
														<input name="children" value="<%=person.getPersonMisc().getChildren()%>" size="2" maxlength="2" class="entry">										
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
						
							
						
						<input type="hidden" name="page" value="03"/>
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


