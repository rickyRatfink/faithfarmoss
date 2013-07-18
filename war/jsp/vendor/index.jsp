<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
%>
		<!----Main Content--->
		<td  valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td class="breadcrumb">Home > Store Functions > Vendor</td><td align="right"><jsp:include page="/jsp/includes/header_links.jsp" flush="true" /></td></tr>
			</table>	
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/vendor-search.jpg">XXX
						<br>
						
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/vendor" name="customer-search-form" id="customer-search-form">
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td width="200" class="field">Vendor Name</td>
							                 <td width="100" class="field">Vendor Number</td>
							               </tr>
							               	
							               <tr>
							               	 <td><input name="vendorName" maxlength="30" size="30" class="entry"></td>
							               	 <td><input name="vendorNumber" maxlength="10" size="10" class="entry"></td>
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
							
						
							<tr>
								<td  height="60" valign="bottom" align="left">
									<input type="image" name="action" value="Search" src="<%=request.getContextPath()%>/images/buttons/search.jpg" width="80" height="24">
						  			<input type="hidden" name="action" value="Search"/>
						  			<input type="hidden" name="entity=" value="Vendor"/>
						  		</td>
							</tr>
							
							</table>
									
						</form>			
						
						<!----End Form----------->
						</div>
									
				
		
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	

 

  	
  	</div>
</table>

  


<jsp:include page="../includes/footer.jsp"/>


