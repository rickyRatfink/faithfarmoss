<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
Inventory Display

<%

Iterable<Entity> entities = (Iterable<Entity>)session.getAttribute("items");

for (Entity result : entities) {
		  Map<String, Object> properties = result.getProperties();
		  out.println("properties size="+properties.size());
		  /*sb.append("{");
		  if (result.getKey().getName() == null)
			sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
		  else
			sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
			*/
		  for (String key : properties.keySet()) 
				out.println(key+"="+properties.get(key)+"<br>");
	  }


%>

