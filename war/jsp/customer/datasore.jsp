<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
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
Customer Display
<%
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key customerKey = KeyFactory.createKey("Customer", "99");
    // Run an ancestor query to ensure we see the most up-to-date
    // view of the customers belonging to the selected Guestbook.
    Query query = new Query("Customer", customerKey);//.addSort("date", Query.SortDirection.DESCENDING);
    List<Entity> customers = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
    if (customers.isEmpty()) {
        %>
        No customers.</p>
        <%
    }
    else {
        %>
        <p>Customers:</p>
        <%
        for (Entity greeting : customers) { %>
            customer=<%=greeting.getProperty("firstName")%>
        <% } 
        
}%>