<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
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
    	if (height>400) height=400;
    %>
    <div style="width: 850px; height: <%=height+5%>px; font-size:12px;">
        <table id="sar" dojoType="dojox.grid.DataGrid" data-dojo-props="escapeHTMLInData:false" formatterScope="myFormatters" >
            <thead>
            	
                <tr>
                	<th field="row" width="30">Row</th>
                    <th field="time" width="120">Order Number</th>
                    <th field="cpu" width="100">Last Name</th>
                    <th field="user" width="100">First Name</th>
                    <th field="nice" width="50">Zipcode</th>
                    <th field="system" width="50" >Delivery</th>
                    <th field="status" width="110" >Status</th>
                    <th field="iowait" width="90">Order Date</th>
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
            		//String orderDate=(String)entity.getProperty("salesOrderDate");
            		DateFormat formatter;
					formatter = new SimpleDateFormat("MM-dd-yyyy");
					
            	%>
            	{
            	  "row":"<%=row+1%>",
                  "time":"<%=entity.getProperty("orderNumber")%>",
                  "cpu":"<%=((String)entity.getProperty("lastName")).toUpperCase()%>",
                  "user":"<%=((String)entity.getProperty("firstName")).toUpperCase()%>",
                  "nice":"<%=entity.getProperty("zipcode")%>",
                  "system":"<%=entity.getProperty("delivery")%>",
                  "status":"<%=entity.getProperty("salesOrderStatus")%>",
                  "iowait":"<%=entity.getProperty("salesOrderDate")%>",
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
        return "<a href='/sales?salesOrderKey="+value+"&action=Print'><img src='/images/buttons/printer.jpg' title='Print'></a>&nbsp;<a href='/sales?salesOrderKey="+value+"&action=Edit'><img src='/images/buttons/edit.jpg' title='Edit'></a>&nbsp;<a href='/sales?salesOrderKey="+value+"&action=Delete'><img src='/images/buttons/delete.jpg' title='Delete'></a>";
       
   }   
  
};

</script>

</td>
</tr>
</table>
