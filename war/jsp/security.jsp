<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%
UserService userService = UserServiceFactory.getUserService(); 
User user = userService.getCurrentUser();



if (user != null)
  response.sendRedirect(userService.createLogoutURL(userService.createLoginURL("/jsp/main123.jsp")));
  else 
  response.sendRedirect(userService.createLoginURL(request.getRequestURI()));

   
%>