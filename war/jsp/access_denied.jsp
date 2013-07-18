<jsp:include page="includes/header.jsp"/>



		<jsp:include page="includes/menu/index.jsp" flush="true"/>
		
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body" align="left">
						<img src="<%=request.getContextPath()%>/images/access_denied.jpg" width="100" height="100">
				</td>
				<td valign="top"><br/><br/><br/>
					<b>
					You are not authorized to access this application.  Contact the system administrator for assistance.
					<b>
				</td>		
							
				</tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>

<jsp:include page="includes/footer.jsp"/>


