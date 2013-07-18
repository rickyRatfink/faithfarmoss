<jsp:include page="includes/header.jsp" flush="true"/>
<jsp:include page="includes/menu/index.jsp" flush="true"/>		
		<!----Main Content--->
		<td valign="top">
		 
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<b>Enterprise Resource Planning</b>&nbsp;<i>version 1.0.11</I><br/>
						<%if ("Fort Lauderdale".equals((String)session.getAttribute("farmBase"))) { %>							
							Faith Farm Ministries - Fort Lauderdale, Florida
						<% } %>
						<%if ("Boynton Beach".equals((String)session.getAttribute("farmBase"))) { %>							
							Faith Farm Ministries - Boynton Beach, Florida
						<% } %>
						<%if ("Okeechobee".equals((String)session.getAttribute("farmBase"))) { %>							
							Faith Farm Ministries - Okeechobee, Florida
						<% } %>
						<br><br>
						<div class="text4">
						<i>No System Updates for now</i>
						</br>
						</br>
						
						</div>
						</div>
									
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>

<jsp:include page="includes/footer.jsp"/>


