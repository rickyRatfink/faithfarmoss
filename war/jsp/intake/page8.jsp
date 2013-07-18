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
								<td><img src="<%=request.getContextPath()%>/images/titles/skills-title.jpg"/><br></td>
							</tr>
							<tr>
								<td >
									
							
									<br><br>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
								   			<td class="instructions">
								   				<b>Check and answer all the following questions that apply to you.</b>
								   			</td>
								   		</tr>
										<tr>
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="0">
								   		<tr>
											<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="officeWork"value="Yes" <%if("Yes".equals(person.getJobSkill().getOfficeWork())) {%>checked<%}%>>Office Work
										    </td>
											<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="electrician"value="Yes" <%if("Yes".equals(person.getJobSkill().getElectrician())) {%>checked<%}%>>Electrician
										    </td>
										    <td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="welder"value="Yes"<%if("Yes".equals(person.getJobSkill().getWelder())) {%>checked<%}%>>Welder
										    </td>
										    	<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="sales"value="Yes"<%if("Yes".equals(person.getJobSkill().getSales())) {%>checked<%}%>>Sales
										    </td>
										    <td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="plumber"value="Yes"<%if("Yes".equals(person.getJobSkill().getPlumber())) {%>checked<%}%>>Plumber
										    </td>   
										</tr>
										<tr>
											<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="mechanic"value="Yes"<%if("Yes".equals(person.getJobSkill().getMechanic())) {%>checked<%}%>>Mechanic
										    </td>
											<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="cook"value="Yes"<%if("Yes".equals(person.getJobSkill().getCook())) {%>checked<%}%>>Cook
										    </td>
										    <td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="carpenter"value="Yes"<%if("Yes".equals(person.getJobSkill().getCarpenter())) {%>checked<%}%>>Carpenter
										    </td>
										    	<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="applianceRepair"value="Yes"<%if("Yes".equals(person.getJobSkill().getApplianceRepair())) {%>checked<%}%>>Appliance Repair
										    </td>
										    <td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="kitchen"value="Yes"<%if("Yes".equals(person.getJobSkill().getKitchen())) {%>checked<%}%>>Kitchen(general)
										    </td>   
										</tr>
								   		<tr>
											<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="painter"value="Yes"<%if("Yes".equals(person.getJobSkill().getPainter())) {%>checked<%}%>>Painter
										    </td>
											<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="airConditioning"value="Yes"<%if("Yes".equals(person.getJobSkill().getAirConditioning())) {%>checked<%}%>>Air Conditioning
										    </td>
										    <td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="custodian"value="Yes"<%if("Yes".equals(person.getJobSkill().getCustodian())) {%>checked<%}%>>Custodian
										    </td>
										    	<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="truckDriver"value="Yes"<%if("Yes".equals(person.getJobSkill().getTruckDriver())) {%>checked<%}%>>Truck Driver
										    </td>
										    <td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="phoneRoom"value="Yes"<%if("Yes".equals(person.getJobSkill().getPhoneRoom())) {%>checked<%}%>>Phone Room
										    </td>   
										</tr>
								   		<tr>
											<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="upholstery"value="Yes"<%if("Yes".equals(person.getJobSkill().getUpholstery())) {%>checked<%}%>>Upholstery
										    </td>
											<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="radioTvRepair"value="Yes"<%if("Yes".equals(person.getJobSkill().getRadioTvRepair())) {%>checked<%}%>>Radio/TV Repair
										    </td>
										    <td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="landscaping"value="Yes"<%if("Yes".equals(person.getJobSkill().getLandscaping())) {%>checked<%}%>>Landscaping
										    </td>
										    	<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="forkLiftOperator"value="Yes"<%if("Yes".equals(person.getJobSkill().getForkLiftOperator())) {%>checked<%}%>>Fork Lift Operator
										    </td>
										    <td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="clothesSorter"value="Yes"<%if("Yes".equals(person.getJobSkill().getClothesSorter())) {%>checked<%}%>>Clothes Sorter
										    </td>   
										</tr>	
								   		<tr>
											<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="maintenance"value="Yes"<%if("Yes".equals(person.getJobSkill().getMaintenance())) {%>checked<%}%>>Maintenance
										    </td>
											<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="autoBodyRepair"value="Yes"<%if("Yes".equals(person.getJobSkill().getAutoBodyRepair())) {%>checked<%}%>>Auto Body Repair
										    </td>
										    <td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="warehousing"value="Yes"<%if("Yes".equals(person.getJobSkill().getWarehousing())) {%>checked<%}%>>Warehousing
										    </td>
										    	<td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="computerRepair"value="Yes"<%if("Yes".equals(person.getJobSkill().getComputerRepair())) {%>checked<%}%>>Computer Repair
										    </td>
										    <td width="120" align="left" class="checkboxGrid">
												<input type="checkbox" name="computerProgramming"value="Yes"<%if("Yes".equals(person.getJobSkill().getComputerProgramming())) {%>checked<%}%>>Computer Programming
											 </td>   
										</tr>																			
								   	</table>
								   	<br>
								   	<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
								   			<td class="instructions">
								   				<b>If you have other work experience than listed above, please describe:</b><br>
								   				<textarea name="otherSkill"><%=person.getJobSkill().getOtherSkill()%></textarea>
								   			</td>
								   		</tr>
										<tr>
									</table>
									<br>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
								   			<td colspan="4" class="instructions">
								   				<b>List your most recent employers below.<i>(Current or most recent first)</i></b><br>
								   			</td>
								   		</tr>
								   		<tr>
								   			<td width="120" class="checkboxGrid">Employer</td>
								   			<td width="120" class="checkboxGrid">Job Title</td>
								   			<td width="100" class="checkboxGrid">Start Date</td>
								   			<td width="100" class="checkboxGrid">End Date</td>
                                            <td width="100" class="checkboxGrid">Contact Name</td>
                                            <td width="100" class="checkboxGrid">Contact Number</td>
								   		</tr>
								   		<tr>
								   			<td class="checkboxGrid"><input name="employer1" value="<%=person.getJobSkill().getEmployer1()%>" maxlength="25" size="25" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="jobTitle1" value="<%=person.getJobSkill().getJobTitle1()%>" maxlength="25" size="25" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="beginDate1" value="<%=person.getJobSkill().getBeginDate1()%>" maxlength="10" size="10" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="endDate1" value="<%=person.getJobSkill().getEndDate1()%>" maxlength="10" size="10" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="contactName1" value="<%=person.getJobSkill().getContactName1()%>" maxlength="40" size="30" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="contactNumber1" value="<%=person.getJobSkill().getContactNumber1()%>" maxlength="13" size="13" class="gridEntry"></td>
								   		</tr>
								   		<tr>
								   			<td class="checkboxGrid"><input name="employer2" value="<%=person.getJobSkill().getEmployer2()%>" maxlength="25" size="25" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="jobTitle2" value="<%=person.getJobSkill().getJobTitle2()%>" maxlength="25" size="25" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="beginDate2" value="<%=person.getJobSkill().getBeginDate2()%>" maxlength="10" size="10" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="endDate2" value="<%=person.getJobSkill().getEndDate2()%>" maxlength="10" size="10" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="contactName2" value="<%=person.getJobSkill().getContactName2()%>" maxlength="40" size="30" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="contactNumber2" value="<%=person.getJobSkill().getContactNumber2()%>" maxlength="13" size="13" class="gridEntry"></td>
								   		</tr>
								   		<tr>
								   			<td class="checkboxGrid"><input name="employer3" value="<%=person.getJobSkill().getEmployer3()%>" maxlength="25" size="25" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="jobTitle3" value="<%=person.getJobSkill().getJobTitle3()%>" maxlength="25" size="25" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="beginDate3" value="<%=person.getJobSkill().getBeginDate3()%>" maxlength="10" size="10" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="endDate3" value="<%=person.getJobSkill().getEndDate3()%>" maxlength="10" size="10" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="contactName3" value="<%=person.getJobSkill().getContactName3()%>" maxlength="40" size="30" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="contactNumber3" value="<%=person.getJobSkill().getContactNumber3()%>" maxlength="13" size="13" class="gridEntry"></td>
								   		</tr>
								   		<tr>
								   			<td class="checkboxGrid"><input name="employer4" value="<%=person.getJobSkill().getEmployer4()%>" maxlength="25" size="25" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="jobTitle4" value="<%=person.getJobSkill().getJobTitle4()%>" maxlength="25" size="25" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="beginDate4" value="<%=person.getJobSkill().getBeginDate4()%>" maxlength="10" size="10" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="endDate4" value="<%=person.getJobSkill().getEndDate4()%>" maxlength="10" size="10" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="contactName4" value="<%=person.getJobSkill().getContactName4()%>" maxlength="40" size="30" class="gridEntry"></td>
								   			<td class="checkboxGrid"><input name="contactNumber4" value="<%=person.getJobSkill().getContactNumber4()%>" maxlength="13" size="13" class="gridEntry"></td>
								   		</tr>
								   		
										<tr>
									</table>
									
								</td>
							</tr>
												
							<tr>
								<td  width="50%" height="60" valign="bottom" align="right">
                               		<input type="submit" name="action" value="Back" class="buttonBack">
									<input type="submit" name="action" value="Next" class="buttonNext">
								</td>
							</tr>
									
							</table>
												
						<input type="hidden" name="page" value="08"/>
		
						
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

