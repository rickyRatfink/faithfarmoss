<%@ page import="org.faithfarm.dataobjects.PurchaseOrder" %>
<%@ page import="java.util.List" %>
<jsp:include page="../includes/header.jsp"/>

<jsp:include page="../includes/menu/index.jsp" flush="true"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/handlebars-1.0.0.beta.6.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/price.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/spin.min.js"></script>
<%
List<PurchaseOrder> results = (List<PurchaseOrder>)request.getAttribute("results");
%>
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
        	<h2 class="text1">Purchase Orders</h2>
        	<div>&nbsp;</div>
        	<div><a href="/po?action=create_po">Create New PO</a></div>
        	<div>&nbsp;</div>
			<table width="900" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">

				<tr>
					<td class="grid">
						<table width="900" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">

							<tr>
								<td class="grid-heading">Search Results</td>
							</tr>
						</table>
						<table width="900" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse;">

							<tr>

								<td class="grid-columnHeading" width="120"><a href="#" style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;">PO #</a></td>
								<td class="grid-columnHeading" width="120"><a href="#" style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;">Vendor Company</a></td>
								<td class="grid-columnHeading" width="120"><a href="#" style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;">Vendor Contact</a></td>
								<td class="grid-columnHeading" width="120"><a href="#" style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;">Ship To Contact</a></td>
								<td class="grid-columnHeading" width="120"><a href="#" style="text-decoration: font-family:"CourierNew", Courier, monospace;font-size:medium;color:#5e82a6;">Actions</td>
							</tr>

							<%
							if(results.size() == 0) {
								%>
								<tr><td colspan="5"><strong>No purchase orders.</strong></td></tr>
								<%
							}
							for(PurchaseOrder purchaseOrder : results) {
								%>
								<tr>
									<td class="grid-results"><%= purchaseOrder.getPoNumber() %></td>
									<td class="grid-results"><%= purchaseOrder.getVendorCompany() %></td>
									<td class="grid-results"><%= purchaseOrder.getVendorName() %></td>
									<td class="grid-results"><%= purchaseOrder.getShiptoName() %></td>
									<td class="grid-results"><a href="/po?action=edit_po&po=<%= purchaseOrder.getKeyString() %>">Edit</a> | <a href="/po?action=print_po&po=<%= purchaseOrder.getKeyString() %>">Print</a></td>
								</tr>
									
								<%
							}
							%>

						</table>
					</td>
				</tr>
			</table>


				</div>
      </td>
    </tr>
  </table>
</td>
<jsp:include page="../includes/footer.jsp"/>





        