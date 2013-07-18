<%@ page import="java.util.ArrayList" %>

<%
	ArrayList messages = (ArrayList)request.getAttribute("messages");
	
	if (messages==null)
		messages = new ArrayList();
	
	if (messages.size()>0) {						
	%>
	<div class="errorMessage">
		<font color="#000000">Please correct the following errors before saving.</font><br>
		<% for (int i=0;i<messages.size();i++) { %>
		<img src="<%=request.getContextPath()%>/images/error.jpg"/>&nbsp;<%=messages.get(i)%><br>
		<%
		if (((String)messages.get(i)).indexOf("WARNING")>-1) { %>
			<input type="hidden" name="overwrite" value="Y"/>
		<% }
		} %>
	</div>
<% } %>

