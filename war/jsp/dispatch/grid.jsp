<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="org.faithfarm.utilities.HTMLBuilder" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%
	//check user authentication via Google
	UserService userService = UserServiceFactory.getUserService(); 
	User user = userService.getCurrentUser();
	String userRole = (String)session.getAttribute(user.getNickname()+"_ROLE"); 
%>

<!DOCTYPE html>
<% ArrayList entities = (ArrayList)session.getAttribute("results"); %>


<link rel="stylesheet" type="text/css"
 href="<%=request.getContextPath()%>/styles/claro.css" />

<link rel="stylesheet" type="text/css"
 href="<%=request.getContextPath()%>/styles/Grid.css" />

 <link rel="stylesheet" type="text/css"
 href=""<%=request.getContextPath()%>/styles/claroGrid.css" />
 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="claro">
    <% 
    
    if (entities!=null) { 
    	int size=entities.size();
    	int height=42+(size*23);	
    	if (height>700) height=700;
    %>
    <div style="width: 950px; height: <%=height%>px; font-size:12px;">
        <table id="sar" dojoType="dojox.grid.DataGrid" data-dojo-props="escapeHTMLInData:false" formatterScope="myFormatters" >
            <thead>
            	
                <tr>
                	<th field="row" width="30">Row</th>
                    <th field="time" width="120">Confirmation</th>
                    <th field="cpu" width="100">Last Name</th>
                    <th field="user" width="100">First Name</th>
                    <th field="nice" width="80">Zipcode</th>
                    <th field="system" width="80" >Status</th>
                    <th field="truck" width="60" >Truck</th>
                    <th field="special" width="80" >Special</th>
                    <th field="iowait" width="90">Dispatch Date</th>
                    <th formatter="formatLink" field="edit" width="90">Action</th>
                    
                </tr>
                
            </thead>
        </table>
    </div>
	<% } %>
<!-----src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js" -->
<script type="text/javascript"
 src="<%=request.getContextPath()%>/script/dojo.xd.js" 
 
    djConfig="parseOnLoad:true"></script>

<script type="text/javascript">
    dojo.require("dojox.grid.DataGrid");
    dojo.require("dojo.data.ItemFileReadStore");
 
</script>

<script type="text/javascript">
dojo.ready(function() {
    var sarvar = {
        items: [ 
        		<% 
        		int row=0;
        		for (int i=0;i<entities.size();i++) {
            		Entity entity=(Entity)entities.get(i);
            		String special="No";
            		if (entity.getProperty("special")!=null)
            			special=entity.getProperty("special").toString();
            			String truck="N/A";
            			if (entity.getProperty("truck")!=null)
            				truck=entity.getProperty("truck").toString();
            	%>
            	{
            	  "row":"<%=row+1%>",
                  "time":"<%=entity.getProperty("confirmationNumber")%>",
                  "cpu":"<%=((String)entity.getProperty("lastName")).toUpperCase()%>",
                  "user":"<%=((String)entity.getProperty("firstName")).toUpperCase()%>",
                  "nice":"<%=entity.getProperty("zipcode")%>",
                  "system":"<%=entity.getProperty("status")%>",
                  "truck":"<%=truck%>",
                  "special":"<%=special%>",
                  "iowait":"<%=entity.getProperty("dispatchDate")%>",
                  "edit":"<%=KeyFactory.keyToString(entity.getKey())%>",
                  }
                <% row++; if (row<entities.size()) { %>,<%}
                } %>
               ],
        identifier: "time" 
    };

    var dataStore = new dojo.data.ItemFileReadStore({ data:sarvar });
    var grid = dijit.byId("sar");
    grid.setStore(dataStore);
  
});

var myFormatters = {
   formatLink : function a1(value, index) {
        return "<a href='/dispatch?dispatchKey="+value+"&action=Print'><img src='/images/buttons/printer.jpg'></a>&nbsp;<a href='/dispatch?dispatchKey="+value+"&action=Edit'><img src='/images/buttons/edit.jpg'></a><% if ("DispatchAdmin".equals(userRole)||"Administrator".equals(userRole)) { %>&nbsp;<a href='/dispatch?dispatchKey="+value+"&action=Delete'><img src='/images/buttons/delete.jpg'></a><% } %>";
       
   }   
  
};

</script>

</td>
</tr>
</table>
