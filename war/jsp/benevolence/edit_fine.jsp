<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.dataobjects.Benevolence" %>
<%@ page import="org.faithfarm.dataobjects.Deduction" %>
<jsp:include page="../includes/header.jsp" flush="true"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>
<%
	Entity deduction = (Entity)request.getAttribute("fine");
	Entity person = (Entity)request.getAttribute("person");
%>

		<!----Main Content--->
		<td valign="top">
		
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
					<div class="benevolence-header">Editing Fine: <%= Benevolence.getNameFromEntity(person) %></div>
					<div>&nbsp;</div>
					<form action="/benevolence/EditFine" method="POST">
					<input type="hidden" name="personId" value="<%= request.getParameter("personId") %>"/>
					<input type="hidden" name="fineId" value="<%= request.getParameter("fineId") %>"/>
					<table cellpadding="0" cellspacing="0">
						<tr><td><strong>Amount:</strong></td><td><input type="text" name="amount" value="<%= deduction.getProperty("amount") %>"/></td></tr>
						<tr><td><strong>Reason:</strong></td><td><input type="text" name="reason" value="<%= deduction.getProperty("reason") %>"/></td></tr>
						<tr><td colspan="2"><div style="float:right;display:inline"><input type="submit" value="Update"/></td></tr>
					</table>
					</form>
				
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>


<jsp:include page="../includes/footer.jsp"/>


