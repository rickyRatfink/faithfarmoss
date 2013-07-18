<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<jsp:include page="../includes/header.jsp"/>
<jsp:include page="../includes/menu/index.jsp" flush="true"/>


		<!----Main Content--->
		<td valign="top">
			<table width="820" border="0" cellpadding="0" cellspacing="0">
				<tr><td><br><!--img src="<%=request.getContextPath()%>/images/header_top.png"--></td></tr>
				<tr>
				<td class="content-body">
						<div class="content">
						<%
						String flash = (String) request.getAttribute("flash");
						List<Entity> residents = (List<Entity>) request.getAttribute("residents");
						if(residents == null) {
							residents = new ArrayList<Entity>();
						}
						if(flash != null && !flash.equals("")) {
							%>
							<div style="font-weight:bold;color:#ff0000"><%= flash %></div>
							<%
						}
						%>
						<div></div><h3>Select Resident</h3></div>
						<form method="post" action="/student?action=ResidencyLetterPost">
						<div style="width: 200px;">
							<select name="residentKey" style="width: 100%"><option value="">Select Resident</option>
							<%
							for(Entity resident : residents) {
								String residentKey = "";
								try {
									residentKey = KeyFactory.keyToString(resident.getKey());
									
								} catch(IllegalArgumentException e) {
									System.out.println("Illegal Argument: " + e.getMessage());
								}
								%>
								<option value="<%= residentKey %>"><%= resident.getProperty("lastName") + " " + resident.getProperty("firstName") %></option>
								<%
							}
							%>
							</select>
							<div style="float: right;">
								<input type="submit" value="Generate"/>
							</div>
						</div>
						

						</div>
									
				</td></tr>
				<tr><td><!--img src="<%=request.getContextPath()%>/images/header_btm.png"--></td></tr>
			</table>
		</td>
	</tr>	
	
</div>
</table>

<jsp:include page="../includes/footer.jsp"/>


