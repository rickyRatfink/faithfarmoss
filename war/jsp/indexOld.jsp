<%@ page import="java.util.ArrayList" %>
<jsp:include page="includes/header.jsp"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<!----Menu--->
		<td align="center" width="391" valign="top">
			<table width="391" border="0" cellpadding="0" cellspacing="0">	
				<tr><td class="loginpanel">System Access</td></tr>
				
				<tr><td class="loginpanel-bg" align="left">
					<br>Please enter your username and password to access the system.
					<br>
					<%
						ArrayList messages = (ArrayList)request.getAttribute("messages");
						
						if (messages==null)
							messages = new ArrayList();
						
						if (messages.size()>0) {						
						%>
							<div style="color:#fe041c;font-size:11px;">
							<% for (int i=0;i<messages.size();i++) { %>
							  &nbsp;&nbsp;*&nbsp;<%=messages.get(i)%><br>
							<% } %>
							</font>
					    <% } %>
					<br>
				</tr></td>
				<tr>
				<td class="loginpanel-bg" align="center">
					<form action="/controller" method="POST">
				    <table border="0">
						<tr>
						<td align="left"><b>username</b><br><input  class="login" name="username" size=25 maxlength="18"></td>
						</tr>
						<tr>
						<td  align="left"><b>password</b><br><input type="password" name="password" size=25 maxlength="18" class="login"></td>
						</tr>
						<tr>
						<td height="30" valign="center">
						<br>
						
						  <input type="image" name="action"  value="Login" src="<%=request.getContextPath()%>/images/buttons/login.jpg">
						  <input type="hidden" name="entity" value="Login"/>
						</form>
						<br><br>
						<a href="">>>&nbsp;trouble signing in?</a>
					</td></tr>
					</table>
					
				</td></tr>		
				<!--
					<tr><td class="loginpanel-bg" ><b>username</b><br><input  class="login" maxlength="18"><br></td></tr>
					<tr><td class="loginpanel-bg" ><b>password</b><br><input type="password" maxlength="18" class="login"></td></tr>
				
						<tr><td class="loginpanel-bg" height="30" valign="center">
						<br>
						<form action="site1.html">
						  <input type="submit" class="button" value="Login">
						</form>
						<br><br>
						<a href="">>>&nbsp;trouble signing in?</a>
					</td></tr>
					-->
				<td><img src="<%=request.getContextPath()%>/images/login_btm.png"></td></tr>
				<tr><td class="menu-footer"></td></tr>	
				<tr><td height="5"></td></tr>
			</table>
		</td>
	</tr>

</table>
<table width="100%">
<tr>
		<td class="footer">
		Faith Farm Ministries, &copy; 2012
		</td>
	</tr>
</table>

