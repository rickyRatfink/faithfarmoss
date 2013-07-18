<%@ page import="com.google.appengine.api.datastore.Entity" %>

	  <div id="right" class="column">
	  	<a href="#"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/banner2.jpg" alt="" width="237" height="216" /></a><br />
		<div class="rightblock">
			<% 
			Entity Customer=null;
			try {
			 Customer = (Entity)session.getAttribute("online_customer");
			} catch (Exception e) { Customer=null; } %>
			<% 
			if (Customer==null) {
				if (!"register".equals(request.getParameter("pg"))) { %>
				<img src="<%=request.getContextPath()%>/jsp/onlinestore/images/title4.gif" alt="" width="223" height="29" /><br />
				<div class="blocks">
					<img src="<%=request.getContextPath()%>/jsp/onlinestore/images/top_bg.gif" alt="" width="218" height="12" />
					<form method="POST" action="<%=request.getContextPath()%>/register">
						<p class="line"><span>Login:</span> <input type="text" name="uid" size="20" maxlength="20" class="login" /></p>
						<p class="line"><span>Password:</span> <input type="password" name="pwd" size="20" maxlength="20" class="login" /></p>
						<p class="line center">					
	                     <a href="<%=request.getContextPath() %>/sfreg" class="reg">Registration</a> | <a href="#" class="reg">Forgot password?</a></p>
						<div align="right"><input type="submit" name="action" value="Login" class="imageButtonStoreFrontLogin" >&nbsp;&nbsp;</div>
						
					</form>
					<img src="<%=request.getContextPath()%>/jsp/onlinestore/images/bot_bg.gif" alt="" width="218" height="10" /><br />
				</div>
				<% }
			}	
			%>
			<div class="blocks">
				<img src="<%=request.getContextPath()%>/jsp/onlinestore/images/top_bg.gif" alt="" width="218" height="12" />
				<div id="news">
					<img src="<%=request.getContextPath() %>/jsp/onlinestore/images/title5.gif" alt="" width="201" height="28" />
					<span class="date">October 2012</span>
					<p>Ribbon cutting ceremony commemorating the completion and opening of our dormitory renovations.</p>
					<!-- <a href="#" class="more">read more</a> -->
				</div>
				<img src="<%=request.getContextPath()%>/jsp/onlinestore/images/bot_bg.gif" alt="" width="218" height="10" /><br />
			</div>
		</div>
	  </div>