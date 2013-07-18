<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>


		<!----Main Content--->
		<td>
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td class="breadcrumb">Home > Department Forms > New Furniture Bedding Only Order Form</td><td align="right"><a href="<%=request.getContextPath()%>/jsp/index.jsp">Logout</a>&nbsp;|&nbsp;<a href="">Help</a></td></tr>
			</table>	
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<div class="text1">
							New Furniture Department (Bedding Only) - Customer Order Entry Form
						</div>
						<br>
						<br>
						<!----Search Form ------->
						
						<form action="<%=request.getContextPath()%>/" name="item-search-form" id="item-search-form">
							<table width="70%" border=0 cellpadding=0 cellspacing=0>
							<tr>
								<td class="field" >Customer Name</td>
							</tr>
							<tr>
								<td >
									<input name="name" maxlength="60" size="60" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field" >Address</td>
							</tr>
							<tr>
								<td >
									<input name="address" maxlength="80" size="80" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field">City</td>
							</tr>
							<tr>
								<td>
									<input name="city" maxlength="30" size="30" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field">Zipcode</td>
							</tr>
							<tr>
								<td>
									<input name="zipcode" maxlength="10" size="10" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field" >Phone</td>								
							</tr>
							<tr>
								<td>
									(<input name="areacode" maxlength="3" size="3" class="entry">)
									<input name="first3" maxlength="3" size="3" class="entry">&nbsp;-
									<input name="last4" maxlength="4" size="4" class="entry">
								</td>
							</tr>
							<tr>
								<td class="field" >New Furniture Department</td>								
							</tr>
							<tr>
								<td>
									<select class="entry">
										<option value="">Fort Lauderdale, FL</option>
										<option value="">Boynton Beach, FL</option>
										<option value="">Okeechobee, FL</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="field" >Delivery Date</td>								
							</tr>
							
							<tr>
								<td>
									 <div><input name="date" class="tcal" value="" /></div>
								</td>
							</tr> 
							
							<tr>
								<td>
									<br><br>
									
							           <table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;">
							             
							                <tr>
							                 <td colspan="5" class="results-section">Customer Order</td>
							                </t>
							                <tr>
							                 <td width="20" class="results-heading">QUANTITY</td>
							                 <td width="150" class="results-heading">DESCRIPTION</td>
							                 <td width="30" class="results-heading">PLU#</td>
							                 <td width="40" class="results-heading">DEPT CODE</td>
							                 <td width="50" class="results-heading">PRICE</td>
							               </tr>
							               	
							               <% for (int i=0;i<5;i++) { %>
							               <tr>
							               	 <td class="results-bg"></td>
							               	 <td class="results-bg"></td>
							               	 <td class="results-bg"></td>
							               	 <td class="results-bg"></td>
							               	 <td class="results-bg"></td>
							               </tr> 
										   <% } %>
							               					               	
							             
							             <tbody id="item-list-tbody"></tbody>
							           </table>
			  							
			  					</td>
			  				</tr>		
							<tr>
								<td  height="60" valign="bottom" align="left">
									<a href="javascript:popitup('<%=request.getContextPath()%>/jsp/inventory/search.jsp');" style="text-decoration:none;"><img src="<%=request.getContextPath()%>/images/search.jpg"></a>
									<input type="submit" class="button" value="Add Item">
									<input type="submit" class="button" onclick="" value="Save Order">
									<input type="submit" class="button" onclick="" value="Print Order Form">
						  			<input type="reset" class="button" value="Clear">
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


