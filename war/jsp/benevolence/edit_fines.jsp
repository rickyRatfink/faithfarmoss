<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
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
				
					<table cellpadding="3" cellspacing="5">
						<tr style="font-weight:bold">
							<td>Name</td><td>Date</td><td>Amount</td><td>Reason</td><td>Edit</td><td>Delete</td>
						</tr>
						
							
					<%
					HashMap<Entity, Entity> theMap = (HashMap<Entity, Entity>) request.getAttribute("finesAndPersons");
					if(theMap.size() == 0) {
						%>
						<tr><td colspan="6">No pending fines.</td></tr>
						<%
					}
					for(Map.Entry<Entity, Entity> cursor : theMap.entrySet()) {
						
						Entity fine = cursor.getKey();
						Entity person = cursor.getValue();
						Key personKey = person.getKey();
						Key fineKey = fine.getKey();
						
						%>
						<tr>
							<td>
								<%= Benevolence.getNameFromEntity(person) %>
							<td>
								<%= fine.getProperty("date") %>
							</td>
							<td>
								$<%= fine.getProperty("amount") %>.00
							</td>
							<td>
								<%= fine.getProperty("reason") %>
							</td>
							<td>
								<a href="/benevolence/EditFine?fineId=<%= fineKey.getId() %>&personId=<%= personKey.getId() %>">Edit</a>
							</td>
							<td>
								<a href="/benevolence/DeleteFine?fineId=<%= fineKey.getId() %>&personId=<%= personKey.getId() %>">Delete</a>
							</td>
						</tr>
						<%
					}
					%>
					</table>
				
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>


<jsp:include page="../includes/footer.jsp"/>


