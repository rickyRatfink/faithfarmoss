<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	ArrayList locations = (ArrayList)session.getAttribute("locations");
	if (locations==null) locations = new ArrayList();	
	

		String required = "<font size='-2' color='red'>*</font>";
    
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
						<!----Application Form ------->
						
						<form action="<%=request.getContextPath()%>/intake?page=1">
						<table width="80%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td><img src="<%=request.getContextPath()%>/images/titles/medical-info.jpg"/><br></td>
							</tr>
							<tr>
								<td >
									
									
									<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
								   		<tr>
											<td  width="210" align="left" class="field">
												Have you ever had a state claim for an industrial claim?
											</td>
											<td valign="bottom">											
												<select name="industrialClaim" class="entry">
																	<option value="">select</option>
																	<option value="brown">Yes</option>
																	<option value="blue">No</option>
												</select>
											</td>
											
										</tr>
										<tr>
											<td colspan="2" class="field">
												<i>If you answered yes to the question above please give a date												being taken?</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="field">											
												<input name="claimDate" value="" size="10" maxlength="10" class="entry">
											</td>
											
										</tr>
										
										<tr>
											<td colspan="2" class="field">
												<i>Reason</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="field">											
												<textarea name="reason"></textarea>
											</td>
											
										</tr>
										
										<tr>
											<td colspan="2" class="field">
												<i>Where</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="field">											
												<input name="claimLocation" value="" size="30" maxlength="30" class="entry">
											</td>
											
										</tr>
										
										<tr>
											<td colspan="2" class="field">
												<i>Employer</i>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="field">											
												<input name="employer" value="" size="30" maxlength="30" class="entry">
											</td>
											
										</tr>
										<tr>
											<td  width="210" align="left" class="field">
												Claim Status
											</td>
											<td valign="bottom">											
												<select name="industrialClaim" class="entry">
																	<option value="">select</option>
																	<option value="brown">Open</option>
																	<option value="blue">Closed</option>
												</select>
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
												<input type="checkbox" name="opt1" value="">Been treated for or told that you have any sickness or injury in the past 5 years?<br>
												<i>If you answered yes to the question above please provide details.</i><br>
												<textarea name="opt1Desc"></textarea><br><br>
											
												<input type="checkbox" name="opt2" value="">Consulted, been examined by or been treated by a doctor in the past 5 years?<br>
												<i>If you answered yes to the question above please provide details.</i><br>
												<textarea name="opt2Desc"></textarea><br><br>
											
												<input type="checkbox" name="opt3" value="">Been in a hospital, psychiatric hospital or other institution for diagnosis, treatment or
												operation in the past 5 years?<br>
												<i>If you answered yes to the question above please provide details.</i><br>
												<textarea name="opt3Desc"></textarea><br><br>
											
												<input type="checkbox" name="opt4" value="">Been advised to have any hospital, clinical, surgical or other treatment in the past 5 years?<br>
												<i>If you answered yes to the question above please provide details.</i><br>
												<textarea name="opt4Desc"></textarea><br><br>
											
												<input type="checkbox" name="opt5" value="">Had any prior injuries to your back that would affect your lifting, bending or twisting capabilities?<br>
												<i>If you answered yes to the question above please provide details.</i><br>
												<textarea name="opt5Desc"></textarea><br><br>
											
												
											</td>
											
											
										</tr>
										<tr>
											<td align="left" class="field">
												If you wear glasses or contact lenses please specify how often.&nbsp;&nbsp;
												<select name="refills" class="entry">
																	<option value="">select</option>
																	<option value="brown">All the time</option>
																	<option value="blue">Occasionally</option>
																	<option value="blue">Reading</option>
												</select>
											</td>
											
										</tr>
									</table>
									
								</td>
							</tr>
												
							<tr>
								<td  height="60" valign="bottom" align="right">
									<a href="javascript:document.forms[0].submit();" style="text-decoration:none;"><img src="<%=request.getContextPath()%>/images/buttons/next.jpg" border="0"></a>
									<input type="hidden" name="page" value="05"/>
						  		    <input type="hidden" name="action" value="Next"/>
						  			
						  		</td>
							</tr>
							
							
							
							</table>
						
							
							
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


