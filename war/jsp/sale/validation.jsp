<%@ page import="java.util.ArrayList" %>

<%
	ArrayList messages = (ArrayList)request.getAttribute("inventoryMessages");
	
	if (messages==null)
		messages = new ArrayList();
	
	if (messages.size()>0) {						
	%>
	<div style="font-size: 85%;
	 font-style: times-new-roman;
	 color: #000000;
	 font-weight: bold;	
	 border: 1px solid #c9a021;
	 border-radius: 8px;
	 width: 600px;
	 padding: 5px 5px 5px 15px;
	 background: #f1db99">
	 <table>
	 
	 <% for (int i=0;i<messages.size();i++) { %>
	 <tr>
	 	<td>
			<img src="<%=request.getContextPath()%>/images/warn.jpg"/>
		</td>
		<td><%=messages.get(i)%></td>
	</tr>
	<% } %>
	</table>
	</div>
	<% } %>

