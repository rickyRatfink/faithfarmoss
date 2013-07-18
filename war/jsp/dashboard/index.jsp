<jsp:include page="../includes/header.jsp" flush="true"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>		
		<!----Main Content--->
		<td valign="top">
		 
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">


					<table cellpadding="0" cellspacing="0" border="1">
						<tr>
							<td class="dashboard_h1">
								Resident Enrollment
							</td>
						</tr>
						<tr>
							<td class="dashboard_h2">
								November 2012
							</td>
						</tr>
						<tr>
							<td class="dashboard_content">
								<jsp:include page="enrollment.jsp" flush="true"/>
							</td>
						</tr>
					</table>
					<br>
					<table cellpadding="0" cellspacing="0" border="1">
						<tr>
							<td class="dashboard_h3">
								Projected/Actual Sales
							</td>
						</tr>
						<tr>
							<td class="dashboard_h4">
								2012
							</td>
						</tr>
						<tr>
							<td class="dashboard_content">
								<jsp:include page="forecast.jsp" flush="true"/>
							</td>
						</tr>
					</table>
				
				
				</td>
				
				
				</tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>

<jsp:include page="../includes/footer.jsp"/>


				