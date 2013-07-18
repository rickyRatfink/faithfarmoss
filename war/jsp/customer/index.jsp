<jsp:include page="../includes/header.jsp"/>


<jsp:include page="../includes/menu/index.jsp" flush="true"/>

<%
	String required = "<font size='-2' color='red'>*</font>";
%>
		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<img src="<%=request.getContextPath()%>/images/titles/customer-search.jpg">
						
						<br>
						<!----Search Form ------->
						
						<form method="POST" action="<%=request.getContextPath()%>/customer?entity=Customer" >
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							
							<tr>
								<td >
								 <table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td width="200" class="field">First Name</td>
							                 <td width="100" class="field">Middle Initial</td>
							                 <td width="200" class="field">Last Name</td>
							                 <td width="100" class="field">Prefix</td>
							               </tr>
							               	
							               <tr>
							               	 <td><input name="firstName" maxlength="30" size="30" class="entry"></td>
							               	 <td><input name="middleInitial" maxlength="1" size="1" class="entry"></td>
							               	 <td><input name="lastName" maxlength="30" size="30" class="entry"></td>
							               	 <td>
							               	 	<select name="suffix" class="entry">
							               	 	    <option value=""></option>
							               	 		<option value="">Jr.</option>
							               	 		<option value="">Sr.</option>
							               	 		<option value="">I</option>
							               	 		<option value="">II</option>
							               	 		<option value="">III</option>
							               	 	</select>							               	 
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
						
							<tr>
							   <td >
								 <table width="480" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                
							                <tr>
							                 <td width="200" class="field">City</td>
							                 <td width="150" class="field">State</td>
							                 <td width="160" class="field">Zipcode</td>
							               </tr>
							               	
							               <tr>
							               	 <td><input name="city" maxlength="30" size="30" class="entry"></td>
							               	 <td>
							               	 	<jsp:include page="../includes/states.jsp" flush="true" />
							               	 </td>
							               	 <td><input name="zipcode" maxlength="10" size="10" class="entry"></td>
							               </tr> 
										   					               	
							             
							             
							           </table>
								
								
									
								</td>
							</tr>
						
							<tr>
								<td  height="60" valign="bottom" align="left">
									<input type="image" name="action" value="Search" src="<%=request.getContextPath()%>/images/buttons/search.jpg" width="80" height="24">
						  			<input type="hidden" name="entity" value="Customer"/>
						  			<input type="hidden" name="action" value="Search"/>
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


