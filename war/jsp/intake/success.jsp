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
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						<br><br>
						
						<!----Application Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/intake">
						<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td colspan="2">
									
									
									<table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
									<tr>
										<td valign="center">
											<img src="<%=request.getContextPath()%>/images/success.jpg"/>
										</td>
										<td valign="top" align="left">
										   				<h4>
										   				<p>
										   				The intake has been successfully saved.
										   				</p>
										   				</h4>
										</td>
								    </tr>
																										
									</table>
									
									
								</td>
							</tr>
						
							<!---
							<tr>
								<td  width="50%" height="60"  align="left">
									<input type="submit" name="action" value="Back" class="buttonBack">
								</td>
								<td  width="50%" height="60"  align="right">
									<input type="submit" name="action" value="Save" class="buttonSave">
								</td>
							</tr>
							--->
							
							</table>
						
						<input type="hidden" name="page" value="09"/>
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


