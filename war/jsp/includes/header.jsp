<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

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
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script src="https://maps.googleapis.com/maps/api/js?sensor=false&libraries=places"></script>
<!--  <script src="../../script/googlemap.js"></script> -->

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Faith Farm Ministries - Operational Support System</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/jsp/includes/menu/css/accordionmenu.css" type="text/css" media="screen" />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/default.css"  />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/tcal.css" />
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/tcal.js"></script>
  <!--<script language="javascript" src='//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js'></script>
  <script language="javascript" src='<%=request.getContextPath()%>/script/ajax.util.js'></script>
  -->
  <script type="text/javascript" src="<%= request.getContextPath() %>/script/json2.js"></script>
<script language="javascript">
  

 function moveOnMax(field,nextFieldID){
	  if(field.value.length >= field.maxLength){
	    document.getElementById(nextFieldID).focus();
	  }
	}

  function popitup(url) {
	newwindow=window.open(url,'name','resizable=no,scrollbars=0,height=140,width=600');
	if (window.focus) {newwindow.focus()}
	return false;
}


      function isNumberKey(evt)
      {
         var charCode = (evt.which) ? evt.which : event.keyCode
         if (charCode!=46 && (charCode > 31 && (charCode < 48 || charCode > 57)))
            return false;

         return true;
      }
 
    
	function warn() {
		alert('warn');
}

	
	
  </script>
</head>


<body>


<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><!--
		<td valign="top" align="left" height="94" background="<%=request.getContextPath()%>/images/header_banner_bg.png" bgcolor="#21232f">
			&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/farm_logo.png">
		</td>
		<td align="right" background="<%=request.getContextPath()%>/images/header_banner_bg2.png">
			
		</td>
		-->
		<td background="<%=request.getContextPath()%>/images/patterns/header.gif" align="left" width="50%">
			<img src="<%=request.getContextPath()%>/images/logo.png">
		</td>
		<td background="<%=request.getContextPath()%>/images/patterns/header.gif"  align="right" width="50%" valign="bottom">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td  class="userInformation">
        		<b>Google User:</b><%=username%>&nbsp;&nbsp;<br/><%=new java.util.Date()%></b>&nbsp;&nbsp;<br/>
        		<%=session.getAttribute("farmBase")%> Campus
					</td>
				</tr>
				<tr>
					<td align="right" valign="bottom">
						<img src="<%=request.getContextPath()%>/images/reach.gif">
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td height="16" bgcolor="#5870b3" colspan="2" align="right">
		<a href="<%=request.getContextPath()%>/controller" style="text-decoration:none;"><img src="<%=request.getContextPath()%>/images/buttons/home.jpg" border="0"></a><a href="<%=request.getContextPath()%>/jsp/help.jsp"><img src="<%=request.getContextPath()%>/images/buttons/support.jpg" border="0"></a><a href="<%=userService.createLogoutURL("/")%>"><img src="<%=request.getContextPath()%>/images/buttons/logout.jpg" border="0"></a>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#080a25" colspan="2"></td>
	</tr>

	<!---
    <tr>
    	<td colspan="2" align="right"  class="userInformation">
        	<b>Google User:</b><%=username%>/<%=new java.util.Date()%>
        </td>
    </tr>
    --->
    
</table>
	
