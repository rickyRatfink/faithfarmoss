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
				Entity fine = (Entity)request.getAttribute("fine");
				%>
				<div>Successfully edited fine for <strong><%= Benevolence.getNameFromEntity(person) %></strong>.</div>
				<div>New Amount: <strong><%= fine.getProperty("amount") %></strong></div>
				<div>New Reason: <strong><%= fine.getProperty("reason") %></strong></div>
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>
<script type="text/javascript">
$("#fine-select").change(function(e) {
	location.href = location.href + "&studentId=" + $(this).val();
});
$(document).ready(function() {
	$("input[name=amount]").focus();
});
</script>

<jsp:include page="../includes/footer.jsp"/>


