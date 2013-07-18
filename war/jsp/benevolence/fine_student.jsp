<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.dataobjects.Benevolence" %>
<jsp:include page="../includes/header.jsp" flush="true"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>

		<!----Main Content--->
		<td valign="top">
		
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
				<%
				Entity person = (Entity)request.getAttribute("person");
				%>
					<div class="benevolence-header">Fine Student: <%= Benevolence.getNameFromEntity(person) %></div>
					<div>&nbsp;</div>
					<form action="/benevolence/FineStudent" method="POST">
					<input type="hidden" name="studentId" value="<%= request.getParameter("studentId") %>"/>
					<table cellpadding="0" cellspacing="0" border="0" width="20%">
						<tr>
							<td><strong>Amount:</strong></td>
							<td><div style="float: right"><input type="text" name="amount"/></div></td>
						</tr>
						<tr>
							<td><strong>Reason:</strong></td>
							<td><div style="float: right"><input type="text" name="reason"/></td>
						</tr>
						<tr><td colspan="2"><div style="float: right"><input type="submit" value="Fine"/></div></td></tr>
					</table>
					</form>
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
$(document).ready(function() {
	$("input[name=amount]").focus();
});
</script>

<jsp:include page="../includes/footer.jsp"/>


