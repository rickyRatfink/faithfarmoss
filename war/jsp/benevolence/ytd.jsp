<%@ page language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
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
				Calendar cal = Calendar.getInstance();
				cal.set(2012, 01, 01, 00, 00, 00);
				cal.set(Calendar.MILLISECOND, 0);
				List<Entity> results = (List<Entity>)request.getAttribute("results");
				SimpleDateFormat fromFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				List statuses = Arrays.asList("Active", "SLS", "Omega School");
				for(Entity person : results) {
					try {
						System.out.println("Person: " + person.getProperty("lastName") + ", " + person.getProperty("firstName"));
						String theirStatus = (String)person.getProperty("personStatus");
						if(statuses.contains(theirStatus)) {
							Date theirDate = fromFormat.parse((String)person.getProperty("entryDate"));
							if(theirDate.before(cal.getTime())) {
								%>
								<div><%= person.getProperty("lastName") %>, <%= person.getProperty("firstName") %> - <%= person.getProperty("ytdPaid") %></div>
								<%
							}
						}
					} catch(ParseException e) {}
				}
				
				%>
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


