<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>

<%
	//check user authentication via Google
	UserService userService = UserServiceFactory.getUserService(); 
	User user = userService.getCurrentUser();
	String username="";
	
	
	if (user!=null) {
		username=user.getNickname();
	}
	
	if (session.getAttribute("farm") == null) {
		response.sendRedirect("http://www.google.com");
	}
	Entity Customer=null;
	try {
	 Customer = (Entity)session.getAttribute("online_customer");
	 if (Customer==null)
		 Customer=new Entity("Person");
	} catch (Exception e) { Customer=new Entity("Person"); } 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries Thrift Store</title>
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jsp/onlinestore/style.css"  />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/tcal.css" />
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/tcal.js"></script>

<SCRIPT type="text/javascript">
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>
</HEAD>
<BODY onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">

	<div id="header">
		<a href="http://www.faithfarm.org" class="float"><img src="<%=request.getContextPath()%>/images/logo.png" alt="Faith Farm Ministries" /></a>																																																		
	  
	  <!-- 
	   <div class="topblock1">
			Currency:<br /><select><option>US Dollar</option></select>
		</div>
		
	    <div class="topblock2">
			Lanquage:<br />
		    <a href="#"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/flag1.gif" alt="" width="19" height="11" /></a>																																		
		    <a href="#"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/flag2.gif" alt="" width="19" height="11" /></a>
		    <a href="#"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/flag3.gif" alt="" width="19" height="11" /></a>
		    <a href="#"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/flag4.gif" alt="" width="19" height="11" /></a>
		    <a href="#"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/flag5.gif" alt="" width="19" height="11" /></a>
		    <a href="#"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/flag6.gif" alt="" width="19" height="11" /></a>
		</div>
		 
		<div class="topblock2">
		Welcome <%=Customer.getProperty("firstName") %> <%=Customer.getProperty("lastName") %>
			<img src="<%=request.getContextPath() %>/jsp/onlinestore/images/shopping.gif" alt="" width="24" height="24" class="shopping" />																																																																									
		 	<p>Shopping cart</p> <p><strong>0</strong> <span>items</span></p>
		</div>
		-->
		<ul id="menu">
			<li><img src="<%=request.getContextPath() %>/jsp/onlinestore/images/li.gif" alt="" width="19" height="29" /></li>
			<li><a href="<%=request.getContextPath()%>/jsp/onlinestore/index.jsp"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/but1_a.gif" alt="" width="90" height="29" /></a></li>
			<li><a href="<%=request.getContextPath()%>/jsp/onlinestore/newarrivals.jsp"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/but2.gif" alt="" width="129" height="29" /></a></li>
			<li><a href="<%=request.getContextPath()%>/jsp/onlinestore/specials.jsp"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/but3.gif" alt="" width="127" height="29" /></a></li>
			<li><a href="<%=request.getContextPath()%>/jsp/onlinestore/index.jsp"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/but4.gif" alt="" width="113" height="29" /></a></li>
			<li><a href="<%=request.getContextPath()%>/jsp/onlinestore/index.jsp"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/but5.gif" alt="" width="132" height="29" /></a></li>
			<li><a href="<%=request.getContextPath()%>/jsp/onlinestore/locations.jsp"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/but6.gif" alt="" width="105" height="29" /></a></li>
			<li><a href="<%=request.getContextPath()%>/jsp/onlinestore/faq.jsp"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/but7.gif" alt="" width="82" height="29" /></a></li>
			<li><a href="<%=request.getContextPath()%>/jsp/onlinestore/locations.jsp"><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/but8.gif" alt="" width="112" height="29" /></a></li>
			<li><img src="<%=request.getContextPath()%>/jsp/onlinestore/images/but9.gif" alt="" width="71" height="29" /></li>
		</ul>
		
	</div>
	
