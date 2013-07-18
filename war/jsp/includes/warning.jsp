<%
	String message = (String)request.getAttribute("message");
	
	if (message!=null) {						
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
	 <tr></tr>
		<td>
			<img src="<%=request.getContextPath()%>/images/warn.jpg"/>
		</td>
		<td><%=message%></td>
	</tr>
	</table>
	</div>
	</br>
<% } %>
