<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%
Entity purchase_order = (Entity)request.getAttribute("purchase_order");

%>
<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/handlebars-1.0.0.beta.6.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/price.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/spin.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/fb/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/script/fb/jquery.fancybox.css"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/edit_po.js"></script>
<script type="text/x-handlebars-template" id="items-template">
<table width="820" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<th>ITEM #</th><th>DESCRIPTION</th><th>QTY</th><th>UNIT PRICE</th><th>TOTAL</th><th>DELETE</th>
	</tr>
	{{#each items}}
	<tr>
		<td>{{item_num}}</td><td>{{description}}</td><td>{{quantity}}</td><td>{{formatCurrency price}}</td><td>{{formatCurrency total}}</td><td><a class="del-item" data-idx="{{idx}}" href="#">Delete</a></td>
	</tr>
	{{/each}}
	{{#unless items.length}}
	<tr>
		<td colspan="6">No items on this purchase order yet.</td>
	</tr>
	{{/unless}}
	<tr>
		<td colspan="6" style="text-align:center;padding:10px;font-size:12pt;"><a class="add-item" href="/po?action=add_item_form">Add Item</a></td>
	</tr>
	{{#if items.length}}
	<tr>
		<td colspan="3">&nbsp;</td><td><strong>Grand Total:</strong><td>{{formatCurrency total}}</td>
	</tr>
	{{/if}}
</table>
</script>

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
        	<h2 class="text1">Edit Purchase Order: #<%= purchase_order.getProperty("po_number") %></h2>
        	<div>&nbsp;</div>
        	<div style="font-size:10pt;"><a href="/po">&lt;-- Go Back</a></div>
        	<div>&nbsp;</div>
        	<%
        	Boolean isSave = (Boolean)request.getAttribute("isSave");
        	if(isSave) {
        		%>
        		<div style="font-weight:bold;color:#33FF66;">Changes Saved!</div>
        		<div>&nbsp;</div>
        		<%
        	}
        	%>
        	<form action="/po?action=edit_po&po=<%= request.getParameter("po") %>&save=1" method="POST">
        	<textarea id="items" name="items" style="display:none;">
			<%= purchase_order.getProperty("items") %>
			</textarea>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
        		<tr>
        			<td>
        				<table width="500" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse">
        					<tr>
        						<td class="edit-field-dark">Vendor Name</td>
        						<td class="edit-value-dark"><input type="text" name="vendor_name" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("vendor_name") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Vendor Company</td>
        						<td class="edit-value-white"><input type="text" name="vendor_company" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("vendor_company") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Vendor Address</td>
        						<td class="edit-value-dark"><input type="text" name="vendor_address" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("vendor_address") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Vendor City</td>
        						<td class="edit-value-white"><input type="text" name="vendor_city" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("vendor_city") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Vendor State</td>
        						<td class="edit-value-dark"><input type="text" name="vendor_state" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("vendor_state") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Vendor Zipcode</td>
        						<td class="edit-value-white"><input type="text" name="vendor_zip" maxlength="6" size="6" class="entry" value="<%= purchase_order.getProperty("vendor_zip") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Vendor Phone</td>
        						<td class="edit-value-dark"><input type="text" name="vendor_phone" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("vendor_phone") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Ship To Name</td>
        						<td class="edit-value-white"><input type="text" name="shipto_name" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("shipto_name") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Ship To Address</td>
        						<td class="edit-value-dark"><input type="text" name="shipto_address" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("shipto_address") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Ship To City</td>
        						<td class="edit-value-white"><input type="text" name="shipto_city" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("shipto_city") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Ship To State</td>
        						<td class="edit-value-dark"><input type="text" name="shipto_state" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("shipto_state") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-white">Ship To Zipcode</tds>
        						<td class="edit-value-white"><input type="text" name="shipto_zip" maxlength="6" size="6" class="entry" value="<%= purchase_order.getProperty("shipto_zip") %>"/></td>
        					</tr>
        					<tr>
        						<td class="edit-field-dark">Ship To Phone</td>
        						<td class="edit-value-dark"><input type="text" name="shipto_phone" maxlength="20" size="20" class="entry" value="<%= purchase_order.getProperty("shipto_phone") %>"/></td>
        					</tr>
        				</table>
        			</td>
        		</tr>
        	</table>
        	<div>&nbsp;</div>
        	<div id="items-div"></div>
        	<div>&nbsp;</div>
        	<div><input type="submit" value="Save Changes"/></div>
        	</form>
        	
        </div>
      </td>
    </tr>
  </table>
</td>
<jsp:include page="../includes/footer.jsp"/>
