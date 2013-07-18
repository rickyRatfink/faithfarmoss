<jsp:include page="../includes/header.jsp" />

<jsp:include page="../includes/menu/index.jsp" flush="true" />

<%
	String required = "<font size='-2' color='red'>*</font>";

	String name = request.getParameter("name");
	if (name == null)
		name = "";
	String representative = request.getParameter("representative");
	if (representative == null)
		representative = "";
	String addressLine1 = request.getParameter("addressLine1");
	if (addressLine1 == null)
		addressLine1 = "";
	String city = request.getParameter("city");
	if (city == null)
		city = "";
	String state = request.getParameter("state");
	if (state == null)
		state = "";
	String zipcode = request.getParameter("zipcode");
	if (zipcode == null)
		zipcode = "";
	String email = request.getParameter("email");
	if (email == null)
		email = "";
	String number = request.getParameter("number");
	if (number == null)
		number = "";
	String orgType = request.getParameter("orgType");
	if (orgType == null)
		orgType = "";

	String h1 = request.getParameter("phone_areacode");
	if (h1 == null)
		h1 = "";
	String h2 = request.getParameter("phone_first3");
	if (h2 == null)
		h2 = "";
	String h3 = request.getParameter("phone_last4");
	if (h3 == null)
		h3 = "";
	String phone = "(" + h1 + ")" + h2 + "-" + h3;
%>
<!----Main Content--->
<td valign="top">

	<table width="820" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><br> <!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td>
		</tr>
		<tr>
			<td class="content-body">
				<div class="content">
					<table>
						<tr>
							<td class="text1">Add Organization</td>
						</tr>
					</table>
					<jsp:include page="../includes/errors.jsp" flush="true" />

					<!----Search Form ------->

					<form method="POST" action="<%=request.getContextPath()%>/vendor">
						<table width="70%" border=0 cellpadding=0 cellspacing=0>

							<tr>
								<td>
									<table cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">


										<tr>
											<td width="170" class="field">Organization Number <%=required%>
											</td>
										</tr>
										<tr>
											<td><input value="<%=number%>" name="number" maxlength="10" size=10 " class="entry"></td>
										</tr>
										<tr>
											<td width="170" class="field">Organization Type <%=required%>
											</td>
										</tr>
										<tr>
											<td>
											<select name="orgType" class="entry">
													<option value=""></option>
													<option value="Civic Organization" <%if ("Civic Organization".equals(orgType)) {%> selected <%}%>>Civic Organization</option>
													<option value="Supplier" <%if ("Supplier".equals(orgType)) {%> selected <%}%>>Supplier</option>
													<option value="Vendor" <%if ("Vendor".equals(orgType)) {%> selected <%}%>>Vendor</option>
											</select>
											</td>
										</tr>
										<tr>
											<td width="180" class="field">Organization Name <%=required%>
											</td>
										</tr>
										<tr>
											<td><input value="<%=name%>" name="name" maxlength="60" size="60" class="entry"></td>

										</tr>



									</table>



								</td>
							</tr>
							<tr>
								<td class="field">Representative <%=required%>
								</td>
							</tr>
							<tr>
								<td><input value="<%=representative%>" name=representative maxlength="40" size="40" class="entry"></td>
							</tr>
							<tr>
								<td class="field">Address Line 1 <%=required%>
								</td>
							</tr>
							<tr>
								<td><input value="<%=addressLine1%>" name="addressLine1" maxlength="80" size="80" class="entry"></td>
							</tr>

							<tr>
								<td>
									<table width="480" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">


										<tr>
											<td width="190" class="field">City <%=required%>
											</td>
											<td width="150" class="field">State <%=required%>
											</td>
											<td width="160" class="field">Zipcode <%=required%>
											</td>
										</tr>

										<tr>
											<td><input value="<%=city%>" name="city" maxlength="30" size="30" class="entry"></td>
											<td><jsp:include page="../includes/states.jsp" flush="true" /></td>
											<td><input value="<%=zipcode%>" name="zipcode" maxlength="10" size="10" class="entry"></td>
										</tr>



									</table>



								</td>
							</tr>

							<tr>
								<td>
									<table cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">
										<tr>
											<td width="180" class="field">Phone <%=required%>
											</td>
										</tr>
										<tr>
											<td>( <input value="<%=h1%>" name="phone_areacode" maxlength="3" size="3" class="entry"> ) <input value="<%=h2%>" name="phone_first3" maxlength="3" size="3" class="entry">
												&nbsp;- <input value="<%=h3%>" name="phone_last4" maxlength="4" size="4" class="entry">
											</td>

										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td class="field">Email Address <%=required%>
								</td>
							</tr>
							<tr>
								<td><input value="<%=email%>" name="email" maxlength="80" size="50" class="entry"></td>
							</tr>


							<tr>
								<td height="60" valign="bottom" align="left"><input type="image" name="action" value="Save" src="<%=request.getContextPath()%>/images/buttons/save.jpg" width="80" height="24"> <input
									type="hidden" name="action" value="Save" /></td>
							</tr>

						</table>
					</form>

					<!----End Form----------->
				</div>
		<tr>
			<td>
				<!--img src="<%=request.getContextPath()%>/images/header_btm.png"-->
			</td>
		</tr>
	</table>
</td>
</tr>





</div>
</table>

<jsp:include page="../includes/footer.jsp" />


