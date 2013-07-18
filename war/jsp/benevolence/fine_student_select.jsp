<%@ page import="java.util.ArrayList" %>
<%@ page import="org.faithfarm.dataobjects.Benevolence" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<jsp:include page="../includes/header.jsp" flush="true"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

		<!----Main Content--->
		<td valign="top">
		
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
					<div class="benevolence-header">Fine Resident</div>
					<div>&nbsp;</div>
					<div style="width:400px">
						<div style="flaot: left;display:inline;"><strong>Please Select a Resident:</strong></div>
						<div style="float: right;display:inline;">
							<%
							ArrayList<Entity> residents = (ArrayList<Entity>)request.getAttribute("finable");
							%>
							<select id="fine-select">
							<option value="">Select</option>
							<%
							for(int x = 0; x < residents.size(); x++) {
								Entity resident = residents.get(x);
								String name = Benevolence.getNameFromEntity(resident);
								Key key = resident.getKey();
								%>
								<option value="<%= key.getId() %>"><%= name %></option>
								<%
							}
							%>
							</select>
						</div>
						<div>&nbsp;</div>
						<div><a href="/benevolence/EditFines">View Pending Fines</a></div>
					</div>
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>
<script type="text/javascript">
$("#fine-select").change(function(e) {
	location.href = location.href + "?studentId=" + $(this).val();
});
</script>

<jsp:include page="../includes/footer.jsp"/>


