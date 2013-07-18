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
<!DOCTYPE html>
<% 
ArrayList entities = (ArrayList)session.getAttribute("results");
String action=request.getParameter("action");
System.out.println("action="+action);
%>


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
    	int height=42+(size*24);	
    	if (height>700) height=700;
    %>
    <div style="width: 740px; height: <%=height%>px; font-size:12px;">
        <table id="sar" dojoType="dojox.grid.DataGrid" data-dojo-props="escapeHTMLInData:false" formatterScope="myFormatters" >
            <thead>
            	
                <tr>
                	<th field="row" width="30">Row</th>
                    <th field="time" width="100">Last Name</th>
                    <th field="user" width="100">First Name</th>
                    <th field="nice" width="80">Status</th>
                    <th field="system" width="80" >Type</th>
                    <th field="birthDate" width="80" >Birth Date</th>
                    <th field="iowait" width="90">Entry Date</th>
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
            		System.out.println (entity.getProperty("lastName"));
            	%>
            	{
            	  "row":"<%=row+1%>",
                  "time":"<%=((String)entity.getProperty("lastName")).toUpperCase()%>",
                  "user":"<%=((String)entity.getProperty("firstName")).toUpperCase()%>",
                  "nice":"<%=entity.getProperty("personStatus")%>",
                  "system":"<%=entity.getProperty("personType")%>",
                  "birthDate":"<%=entity.getProperty("dateOfBirth")%>",
                  "iowait":"<%=entity.getProperty("entryDate")%>",
                  "edit":"<%=KeyFactory.keyToString(entity.getKey())%>"
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

<% if ("export".equals(action)) { %>
   formatLink : function a1(value, index) {
        return "<a href='<%=request.getContextPath()%>/student?personKey="+value+"&action=Export Intake'><img src='/images/buttons/accept.jpg'></a>";
   } 
  
<% } else if ("deny".equals(action)) { %>
   formatLink : function a1(value, index) {
        return "<a href='<%=request.getContextPath()%>/student?personKey="+value+"&action=Deny Admission'><img src='/images/buttons/reject.jpg'></a>";
   } 
  
<% } else if ("Card".equals(action)) { %>
   formatLink : function a1(value, index) {
        return "<a href='<%=request.getContextPath()%>/student?personKey="+value+"&action=Print Card'><img src='/images/buttons/printer.jpg'></a>";
   }
   
<% } else if ("Work".equals(action)) { %>
   formatLink : function a1(value, index) {
        return "<a href='<%=request.getContextPath()%>/student?personKey="+value+"&action=Assign Job'><img src='/images/buttons/assignjob.jpg'></a>";
   }
   
<% } else if ("Photo".equals(action)) { %>
   formatLink : function a1(value, index) {
        return "<a href='<%=request.getContextPath()%>/student?personKey="+value+"&action=Upload Photo'><img src='/images/buttons/uploadPhoto.jpg'></a>";
   }
   
<% } else if ("Profile".equals(action)) { %>
   formatLink : function a1(value, index) {
        return "<a href='<%=request.getContextPath()%>/student?personKey="+value+"&action=View'><img src='/images/buttons/view.jpg'></a>";
   }
<% } else { %>
   formatLink : function a1(value, index) {
        return "<a href='/student?personKey="+value+"&action=Edit'><img src='/images/buttons/edit.jpg'></a>&nbsp;<a href='/student?personKey="+value+"&action=Print'><img src='/images/buttons/printer.jpg'></a>&nbsp;<a href='/student?personKey="+value+"&action=Delete'><img src='/images/buttons/delete.jpg'></a>";
   } 

<% } %>
};

</script>

</td>
</tr>
</table>
