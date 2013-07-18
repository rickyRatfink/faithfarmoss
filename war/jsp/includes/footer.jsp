<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%
	//check user authentication via Google
	UserService userService = UserServiceFactory.getUserService(); 
	User user = userService.getCurrentUser();
%>

<table width="100%" border="0" cellpadding="0" cellspacing="0" >
<tr>
		<td width="191" bgcolor="#5870b3" height="80">
                		
       </td>
       <td align="right">
       		<img src="<%=request.getContextPath()%>/images/google.jpg" />
      	</td>
       <td class="footer">
        <a href="<%=request.getContextPath()%>/jsp/main.jsp" style="font:Tahoma, Geneva, sans-serif;color:#2e3a60;font-size:10px;">Home</a>&nbsp;|&nbsp;
        <a href="<%=request.getContextPath()%>/jsp/help.jsp" style="font:Tahoma, Geneva, sans-serif;color:#2e3a60;font-size:10px;">Support</a>&nbsp;|&nbsp;
        <a href="<%=userService.createLogoutURL("/")%>" style="font:Tahoma, Geneva, sans-serif;color:#2e3a60;font-size:10px;">Logout</a><br />
        &copy; 2012 Faith Farm Ministries, All Rights Reserved.<br />
   		</td>
	</tr>
</table>


