
<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>


		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						
						
						<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td colspan="2">
									
									<br/><br/>
									<table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
									<tr>
										<td valign="center">
											<img src="<%=request.getContextPath()%>/images/success.jpg"/>
										</td>
										<td valign="top" align="left">
											<% if (!"Y".equals(request.getParameter("conf"))) { %>
										   				<p>
										   				The sales order has been successfully saved. 
										   				<br/>
										   				<b>Order# </b><%=request.getAttribute("orderNumber")%>
										   				</p>
										  <% } else { %>
										  				<p>
										   				The changes to the sales order form have been successfully updated. 
										   				</p>
										  <% } %>
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


