<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/handlebars-1.0.0.beta.6.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/price.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/spin.min.js"></script>

<!----Main Content--->
<td>
  <table width="820" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td>
        <br>
        <!--img src="<%=request.getContextPath()%>/images/header_top.png"-->
      </td>
    </tr>

    <tr>
      <td class="content-body">
        <div class="content">
        	<h2 class="text1">Create Purchase Order</h2>
        	<div>&nbsp;</div>
        	<form action="/po?action=create_po_post" method="POST">
        	<input type="hidden" name="farm" value="<%= request.getAttribute("farmBase") %>"/>
        	<table width="100%" border="0" cellpadding="0" cellspacing="0">
        		<tr>
        			<td>
        				<table width="500" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse">
        					<tr>
        						<td class="edit-field-dark">Vendor Name</td>
        						<td class="edit-value-dark"><input type="text" name="vendor_name" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Vendor Company</td>
        						<td class="edit-value-white"><input type="text" name="vendor_company" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Vendor Address</td>
        						<td class="edit-value-dark"><input type="text" name="vendor_address" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Vendor City</td>
        						<td class="edit-value-white"><input type="text" name="vendor_city" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Vendor State</td>
        						<td class="edit-value-dark"><input type="text" name="vendor_state" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Vendor Zipcode</td>
        						<td class="edit-value-white"><input type="text" name="vendor_zip" maxlength="6" size="6" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Vendor Phone</td>
        						<td class="edit-value-dark"><input type="text" name="vendor_phone" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Ship To Name</td>
        						<td class="edit-value-white"><input type="text" name="shipto_name" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Ship To Address</td>
        						<td class="edit-value-dark"><input type="text" name="shipto_address" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Ship To City</td>
        						<td class="edit-value-white"><input type="text" name="shipto_city" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Ship To State</td>
        						<td class="edit-value-dark"><input type="text" name="shipto_state" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Ship To Zipcode</tds>
        						<td class="edit-value-white"><input type="text" name="shipto_zip" maxlength="6" size="6" class="entry"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Ship To Phone</td>
        						<td class="edit-value-dark"><input type="text" name="shipto_phone" maxlength="20" size="20" class="entry"/></td>
        					</tr>
        					<tr>
        						<td colspan="2"><div style="margin-top:10px;"><input type="submit" value="Create"/></div></td>
        					</tr>
        					
        				</table>
        			</td>
        		</tr>
        	</table>
        	</form>
        </div>
      </td>
    </tr>
  </table>
</td>
<jsp:include page="../includes/footer.jsp"/>
