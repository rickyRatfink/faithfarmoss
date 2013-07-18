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
						 <table>
				    	 <tr>
				    		<td class="text1">Person Quick Entry</td>
				    	 </tr>
				         </table>
						<br>
						<jsp:include page="../includes/errors.jsp" flush="true"/>
						<br><br>
						
						<!----Application Form ------->
						
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
										   				The person has been successfully saved.
										   				</p>
										   				</h4>
										</td>
								    </tr>
																										
									</table>
									
									
								</td>
							</tr>
						
						
							</table>
						
						
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


